package org.example.java1.labb1;

public class Price implements Comparable<Price> {
    private final int hourFrom;
    private final int hourTo;
    private final int price;

    public Price(int hourFrom, int hourTo, int price) {
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
        this.price = price;
    }

    public int getHourFrom() {
        return hourFrom;
    }

    public int getHourTo() {
        return hourTo;
    }

    public String getHours() {
        return Utils.formatHours(hourFrom, hourTo);
    }

    public int getPrice() {
        return price;
    }

    public String getPriceString() {
        return price + " öre / kWh";
    }

    @Override
    public int compareTo(Price o) {
        return Integer.compare(price, o.getPrice());
    }

    @Override
    public String toString() {
        return "Timmor: " + getHours()
                + "\nPris: " + getPriceString();
    }
}
