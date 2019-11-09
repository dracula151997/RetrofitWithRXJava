package com.project.semicolon.rxjava.services.model.ticktes;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.project.semicolon.rxjava.services.model.price.PriceResponse;

public class TicketsResponse {

    @SerializedName("duration")
    private String duration;

    @SerializedName("instructions")
    private String instructions;

    @SerializedName("arrival")
    private String arrival;

    @SerializedName("flight_number")
    private String flightNumber;

    @SerializedName("from")
    private String from;

    @SerializedName("to")
    private String to;

    @SerializedName("departure")
    private String departure;

    @SerializedName("stops")
    private int stops;

    @SerializedName("airline")
    private Airline airline;

    private PriceResponse price;

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getArrival() {
        return arrival + " DEST";
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
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

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDeparture() {
        return departure + " DEP";
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public int getStops() {
        return stops;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setPrice(PriceResponse price) {
        this.price = price;
    }

    public PriceResponse getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return
                "TicketsResponse{" +
                        "duration = '" + duration + '\'' +
                        ",instructions = '" + instructions + '\'' +
                        ",arrival = '" + arrival + '\'' +
                        ",flight_number = '" + flightNumber + '\'' +
                        ",from = '" + from + '\'' +
                        ",to = '" + to + '\'' +
                        ",departure = '" + departure + '\'' +
                        ",stops = '" + stops + '\'' +
                        ",airline = '" + airline + '\'' +
                        "}";
    }

    public String stop() {
        String stop;
        if (stops == 1) {
            stop = stops + " Stop";
        } else {
            stop = stops + " Stops";
        }
        return stop;

    }

    public String setInstruction() {
        String text = duration + ", " + flightNumber;
        if (!TextUtils.isEmpty(instructions)) {
            text = text + ", " + instructions;
        }

        return text;
    }

    public boolean isPriceExist(){
        return price != null;
    }

    public String setDestination(){
        return from + "Dest";
    }
}