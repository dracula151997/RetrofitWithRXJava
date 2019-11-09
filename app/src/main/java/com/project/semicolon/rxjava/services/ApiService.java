package com.project.semicolon.rxjava.services;

import com.project.semicolon.rxjava.services.model.price.PriceResponse;
import com.project.semicolon.rxjava.services.model.ticktes.TicketsResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("airline-tickets.php")
    Single<List<TicketsResponse>> searchTickets(@Query("from")String from,
                                                @Query("to")String to);

    @GET("airline-tickets-price.php")
    Single<PriceResponse> ticketsPrice(@Query("flight_number")String flightNumber,
                                       @Query("from")String from, @Query("to") String to);


}
