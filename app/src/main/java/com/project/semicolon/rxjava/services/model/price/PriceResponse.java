package com.project.semicolon.rxjava.services.model.price;

import com.google.gson.annotations.SerializedName;

public class PriceResponse {

    @SerializedName("price")
    private int price;

    @SerializedName("flight_number")
    private String flightNumber;

    @SerializedName("currency")
    private String currency;

    @SerializedName("from")
    private String from;

    @SerializedName("to")
    private String to;

    @SerializedName("seats")
    private int seats;

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        return
                "PriceResponse{" +
                        "price = '" + price + '\'' +
                        ",flight_number = '" + flightNumber + '\'' +
                        ",currency = '" + currency + '\'' +
                        ",from = '" + from + '\'' +
                        ",to = '" + to + '\'' +
                        ",seats = '" + seats + '\'' +
                        "}";
    }

    public String seats() {
        return seats + " Seats";
    }

    public String price() {
        return "$ " + price;
    }
}