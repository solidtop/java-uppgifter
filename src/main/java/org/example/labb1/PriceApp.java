package org.example.labb1;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.net.URL;
import java.util.*;

public class PriceApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Price> prices = new ArrayList<>();
        prices.add(new Price(0, 1, 100));
        prices.add(new Price(0, 1, 50));
        prices.add(new Price(0, 1, 20));
        prices.add(new Price(0, 1, 10));
        prices.add(new Price(0, 1, 200));

        while (true) {
            printOptions();
            System.out.print("Välj: ");
            String input = scanner.nextLine();
            InputResponse res = runOption(input, prices, scanner);
            if (res.getStatus() == Status.ERROR) {
                System.out.println(res.getMessage());
            } else if (res.getStatus() == Status.END) {
                System.out.println(res.getMessage());
                break;
            }

            triggerPrompt(scanner);
        }

        scanner.close();
    }

    static InputResponse runOption(String input, ArrayList<Price> prices, Scanner scanner) {
        if (input.isEmpty()) {
            return new InputResponse(Status.ERROR, "Var god välj ett alternativ");
        }

        if (input.equalsIgnoreCase("e")) {
            return new InputResponse(Status.END, "Programmet avslutas");
        }

        if (!input.equals("1") && prices.size() < 4) {
            return new InputResponse(Status.ERROR, "Det finns inte tillräckligt med priser att hantera");
        }

        switch (input) {
            case "1" -> {
                printHeader("Inmatning");
                System.out.println("Mata in elpriset (öre) per KWh för all timmar på dygnet");
                for (int i = 0; i < 24; i++) {
                    try {
                        String hours = Util.formatHours(i, i + 1);
                        System.out.print("Pris för timmor " + hours + ":");
                        int price = scanner.nextInt();
                        prices.add(new Price(i, i + 1, price));
                    } catch (InputMismatchException exception) {
                        System.out.println("Var god skriv ett pris i hela ören");
                    }
                }
                scanner.nextLine();
            }

            case "2" -> {
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

            case "3" -> {
                printHeader("Sortera");
                prices.sort(Comparator.comparingInt(Price::getPrice));
                for (Price price : prices) {
                    System.out.println(price);
                    printBreak();
                }
            }

            case "4" -> {
                printHeader("Bästa Laddningstid (4)");
                prices.sort(Comparator.comparingInt(Price::getPrice));
                System.out.println("De 4 billigaste timmarna är mellan "
                        + prices.get(0).getHourFrom() + " och "
                        + prices.get(3).getHourTo());
                Price price = getAveragePrice(prices);
                System.out.println("Medelpriset under dessa timmar är: " + price.getPriceString());
            }

            case "5" -> {
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
                } catch (CsvValidationException | IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Priserna laddades framgångsrikt");
                }
            }

            default -> {
                return new InputResponse(Status.ERROR, input + " finns inte som alternativ");
            }
        }

        printFooter();
        return new InputResponse(Status.SUCCESS);
    }

    static void printOptions() {
        System.out.println("Elpriser");
        System.out.println("========");
        System.out.println("1. Inmatning");
        System.out.println("2. Min, Max och Medel");
        System.out.println("3. Sortera");
        System.out.println("4. Bästa Laddningstid (4h)");
        System.out.println("5. Läs in priser från fil");
        System.out.println("e. Avsluta");
    }

    static void printHeader(String title) {
        System.out.println(Util.ANSI_GREEN + "------------------------------");
        System.out.println("       " + title + "       ");
        System.out.println("------------------------------" + Util.ANSI_RESET);
    }

    static void printFooter() {
        System.out.println(Util.ANSI_GREEN + "------------------------------" + Util.ANSI_RESET);
    }

    static void printBreak() {
        System.out.println("------------");
    }

    static Price getLowestPrice(ArrayList<Price> prices) {
        return prices.stream().min(Comparator.comparingInt(Price::getPrice)).orElseThrow(NoSuchElementException::new);
    }

    static Price getHighestPrice(ArrayList<Price> prices) {
        return prices.stream()
                .max(Comparator.comparingInt(Price::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }

    static Price getAveragePrice(ArrayList<Price> prices) {
        int averagePrice = (int) prices.stream().mapToInt(Price::getPrice).average().orElse(0);
        return new Price(0, 23, averagePrice);
    }

    static void triggerPrompt(Scanner scanner) {
        System.out.println(Util.ANSI_YELLOW + "Tryck på valfri knapp för att fortsätta" + Util.ANSI_RESET);
        scanner.nextLine();
    }
}
