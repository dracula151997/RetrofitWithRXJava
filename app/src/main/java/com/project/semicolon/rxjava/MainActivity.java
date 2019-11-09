package com.project.semicolon.rxjava;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.semicolon.rxjava.databinding.MainActivityBind;
import com.project.semicolon.rxjava.services.ApiClient;
import com.project.semicolon.rxjava.services.ApiService;
import com.project.semicolon.rxjava.services.model.price.PriceResponse;
import com.project.semicolon.rxjava.services.model.ticktes.TicketsResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements
        FlightsRecyclerAdapter.OnTicketSelected {

    private ApiService service;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MainActivityBind binding;
    private FlightsRecyclerAdapter adapter;
    private List<TicketsResponse> ticketsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        service = ApiClient.getClient().create(ApiService.class);

        /**
         * You can notice replay() operator which is used to make
         * an Observable emits the data on new subscriptions without
         * executing the logic again.
         * In this case, the list of tickets will be emitted without making the HTTP call again.
         * Without the replay method,
         * you can notice the fetch tickets HTTP call get executed multiple times.
         */
        ConnectableObservable<List<TicketsResponse>> ticketsObservable =
                getTickets("DEL", "HYD").replay();

        ticketsList = new ArrayList<>();

        //TODO 1 Run this code first and show how flatMap() operator works.


        adapter = new FlightsRecyclerAdapter(this);
        adapter.setOnTicketSelected(this);

        initRecyclerView();
        /**
         * Fetching all tickets first
         * Observable emits List<TicketResponse> at once.
         * All the items will be added to RecyclerView directly without price and number of seats.
         */
        compositeDisposable.add(ticketsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<TicketsResponse>>() {
                    @Override
                    public void onNext(List<TicketsResponse> tickets) {
                        ticketsList.clear();
                        ticketsList.addAll(tickets);
                        adapter.setTicketList(ticketsList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this,
                                e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                }));


        //TODO  2- comment this code
        /**
         * In this subscription, first flatMap() is used to convert list of tickets
         * to individual ticket emissions.
         * Second flatMap() is chained to execute the getPrice() method on each ticket emissions
         * which fetches the price and available seats.
         */
        compositeDisposable.add(ticketsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /**
                 * Converting List<TicketResponse> emission to single Ticket emissions
                 * */
                .flatMap(new Function<List<TicketsResponse>, ObservableSource<TicketsResponse>>() {
                    @Override
                    public ObservableSource<TicketsResponse> apply(List<TicketsResponse> ticketsList) throws Exception {
                        return Observable.fromIterable(ticketsList);
                    }
                })
                /**
                 * Fetching price on each Ticket emission
                 * */
                .flatMap(new Function<TicketsResponse, ObservableSource<TicketsResponse>>() {
                    @Override
                    public ObservableSource<TicketsResponse> apply(TicketsResponse ticketsResponse) throws Exception {
                        return getPrice(ticketsResponse);
                    }
                })
                .subscribeWith(new DisposableObserver<TicketsResponse>() {
                    @Override
                    public void onNext(TicketsResponse ticket) {
                        int position = ticketsList.indexOf(ticket);

                        /**
                         * Ticket not found in the Tickets list
                         */
                        if (position == -1)
                            return;

                        ticketsList.set(position, ticket);
                        adapter.notifyItemChanged(position);


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

        //TODO 3- uncomment this gist and show how the concatMap() operator work.
       /* compositeDisposable.add(ticketsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<TicketsResponse>, ObservableSource<TicketsResponse>>() {
                    @Override
                    public ObservableSource<TicketsResponse> apply(List<TicketsResponse> ticketsList) throws Exception {
                        return Observable.fromIterable(ticketsList);
                    }
                }).concatMap(new Function<TicketsResponse, ObservableSource<TicketsResponse>>() {
                    @Override
                    public ObservableSource<TicketsResponse> apply(TicketsResponse tickets) throws Exception {
                        return getPrice(tickets);
                    }
                }).subscribeWith(new DisposableObserver<TicketsResponse>() {
                    @Override
                    public void onNext(TicketsResponse ticket) {
                        int position = ticketsList.indexOf(ticket);

                        *//**
                         * Ticket not found in the Tickets list
                         *//*
                        if (position == -1)
                            return;

                        ticketsList.set(position, ticket);
                        adapter.notifyItemChanged(position);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                }));*/


        ticketsObservable.connect();

        //TODO 4- Read this https://proandroiddev.com/exploring-rxjava-in-android-operators-for-transforming-observables-367c22d86677
        //TODO 5- What the difference between concatMap() and flatMap()?



    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.ticketsRecycler.setLayoutManager(layoutManager);

        binding.ticketsRecycler.setAdapter(adapter);
        binding.ticketsRecycler.setHasFixedSize(true);
    }

    /**
     * Making Retrofit call to fetch all tickets.
     */
    public Observable<List<TicketsResponse>> getTickets(String from, String to) {
        return service.searchTickets(from, to)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }

    /**
     * Making Retrofit call to fetch single ticket price.
     * get price HTTP call returns Price Object, but
     * map() operator is used to change the return type to Ticket.
     */
    public Observable<TicketsResponse> getPrice(final TicketsResponse ticket) {
        return service.ticketsPrice(ticket.getFlightNumber(), ticket.getFrom(), ticket.getTo())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<PriceResponse, TicketsResponse>() {
                    @Override
                    public TicketsResponse apply(PriceResponse price) throws Exception {
                        ticket.setPrice(price);
                        return ticket;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
        binding.unbind();
    }

    @Override
    public void onTicketSelect(TicketsResponse ticket) {
        Toast.makeText(this, "Flight number: " +
                ticket.getFlightNumber(), Toast.LENGTH_SHORT).show();

    }
}
