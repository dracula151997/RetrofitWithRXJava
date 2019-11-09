package com.project.semicolon.rxjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.semicolon.rxjava.databinding.TicketsListItemBind;
import com.project.semicolon.rxjava.services.model.ticktes.TicketsResponse;

import java.util.List;

public class FlightsRecyclerAdapter extends
        RecyclerView.Adapter<FlightsRecyclerAdapter.ViewHolder> {
    private List<TicketsResponse> ticketList;
    private Context context;
    private OnTicketSelected onTicketSelected;

    public FlightsRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setTicketList(List<TicketsResponse> ticketList) {
        this.ticketList = ticketList;
        notifyDataSetChanged();
    }

    public void setOnTicketSelected(OnTicketSelected onTicketSelected) {
        this.onTicketSelected = onTicketSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TicketsListItemBind binding = TicketsListItemBind
                .inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.binding.setTickets(ticketList.get(position));
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTicketSelected != null)
                    onTicketSelected.onTicketSelect(ticketList.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        if (ticketList == null)
            return 0;

        return ticketList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TicketsListItemBind binding;

        public ViewHolder(@NonNull TicketsListItemBind binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnTicketSelected {
        void onTicketSelect(TicketsResponse ticket);
    }
}
