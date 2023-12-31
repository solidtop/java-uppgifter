package org.example.java1.labb1;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class PriceApp {

    public static void main(String[] args) {
        PriceApp app = new PriceApp();
        app.run();
    }

    private final Scanner scanner;
    private final List<Price> prices;

    public PriceApp() {
        scanner = new Scanner(System.in);
        prices = new ArrayList<>();
    }

    public void run() {
        while (true) {
            printOptions();
            System.out.print("Välj: ");
            String input = scanner.nextLine();
            InputResponse res = selectOption(input);

            if (!res.getMessage().isEmpty()) {
                System.out.println(res.getMessage());
            }

            if (res.getStatus() == Status.END) {
                break;
            }

            triggerPrompt();
        }
    }

    private InputResponse selectOption(String input) {
        if (input.isEmpty()) {
            return new InputResponse(Status.ERROR, "Var god välj ett alternativ");
        }

        if (input.equalsIgnoreCase("e")) {
            return new InputResponse(Status.END, "Programmet avslutas");
        }

        if (!(input.equals("1") || input.equals("5")) && prices.size() < 4) {
            return new InputResponse(Status.ERROR, "Det finns inte tillräckligt med priser att hantera");
        }

        switch (input) {
            case "1" -> inputPrices();
            case "2" -> calculateMinMaxAverage();
            case "3" -> sortPrices();
            case "4" -> calculateBestChargingTime();
            case "5" -> loadPricesFromCSV();
            default -> {
                return new InputResponse(Status.ERROR, input + " finns inte som alternativ");
            }
        }

        printFooter();
        return new InputResponse(Status.SUCCESS);
    }

    private void printOptions() {
        System.out.println("Elpriser");
        System.out.println("========");
        System.out.println("1. Inmatning");
        System.out.println("2. Min, Max och Medel");
        System.out.println("3. Sortera");
        System.out.println("4. Bästa Laddningstid (4h)");
        System.out.println("5. Läs in priser från fil");
        System.out.println("e. Avsluta");
    }

    private void printHeader(String title) {
        System.out.println(Utils.ANSI_GREEN + "------------------------------");
        System.out.println("       " + title + "       ");
        System.out.println("------------------------------" + Utils.ANSI_RESET);
    }

    private void printFooter() {
        System.out.println(Utils.ANSI_GREEN + "------------------------------" + Utils.ANSI_RESET);
    }

    private void printBreak() {
        System.out.println("------------");
    }

    private Price getLowestPrice(List<Price> prices) {
        return prices.stream().min(Comparator.comparingInt(Price::getPrice)).orElseThrow(NoSuchElementException::new);
    }

    private Price getHighestPrice(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparingInt(Price::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }

    private Price getAveragePrice(List<Price> prices) {
        int averagePrice = (int) prices.stream().mapToInt(Price::getPrice).average().orElse(0);
        return new Price(0, 23, averagePrice);
    }

    private void triggerPrompt() {
        System.out.println(Utils.ANSI_YELLOW + "Tryck på valfri knapp för att fortsätta" + Utils.ANSI_RESET);
        scanner.nextLine();
    }

    private void inputPrices() {
        printHeader("Inmatning");
        System.out.println("Mata in elpriset (öre) per KWh för alla timmar på dygnet");
        for (int i = 0; i < 24; i++) {
            try {
                String hours = Utils.formatHours(i, i + 1);
                System.out.print("Pris för timmar " + hours + ":");
                int price = scanner.nextInt();
                prices.add(new Price(i, i + 1, price));
            } catch (InputMismatchException e) {
                InputResponse res = new InputResponse(Status.ERROR, "Var god skriv ett pris i hela ören");
                System.out.println(res.getMessage());
                scanner.nextLine();
                i--;
            }
        }
        scanner.nextLine();
    }

    private void calculateMinMaxAverage() {
        printHeader("Min, Max och Medel");
        Price lowestPrice = getLowestPrice(prices);
        Price highestPrice = getHighestPrice(prices);
        Price averagePrice = getAveragePrice(prices);

        printBreak();
        System.out.println("Lägsta priset");
        printBreak();
        System.out.println(lowestPrice);
        printBreak();

        System.out.println("Högsta priset");
        printBreak();
        System.out.println(highestPrice);
        printBreak();

        System.out.println("Medel priset");
        printBreak();
        System.out.println(averagePrice);
        printBreak();
    }

    private void sortPrices() {
        printHeader("Sortera");
        prices.sort(null);
        for (Price price : prices) {
            System.out.println(price);
            printBreak();
        }
    }

    private void calculateBestChargingTime() {
        printHeader("Bästa Laddningstid (4h)");

        int bestStartHour = 0;
        int bestTotalPrice = 0;
        for (int i = 0; i < prices.size() - 4; i++) {
            int totalPrice = 0;
            for (int j = 0; j < 4; j++) {
                totalPrice += prices.get(i + j).getPrice();

                if (totalPrice < bestTotalPrice || bestTotalPrice == 0) {
                    bestTotalPrice = totalPrice;
                    bestStartHour = i;
                }
            }
        }

        System.out.println("Bästa tiden att börja ladda är vid: " + Utils.formatHour(bestStartHour));
        int averagePrice = bestTotalPrice / 4;
        System.out.println("Medelpriset under dessa 4 timmar är: " + averagePrice + " öre");
    }

    private void loadPricesFromCSV() {
        prices.clear();
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader("src/main/resources/priser.csv"))
                    .withSkipLines(1)
                    .build();
            String[] row;

            while ((row = reader.readNext()) != null) {
                int hourFrom = Integer.parseInt(row[0]);
                int hourTo = Integer.parseInt(row[1]);
                int price = Integer.parseInt(row[2]);
                prices.add(new Price(hourFrom, hourTo, price));
            }
            reader.close();
            System.out.println("Priserna laddades framgångsrikt");
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
    }
}
