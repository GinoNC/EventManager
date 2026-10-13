package com.gino.eventmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEventName, tvEventDate, tvEventLocation, tvEventOrganiser, tvEventType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEventName = itemView.findViewById(R.id.tvEventName);
            tvEventDate = itemView.findViewById(R.id.tvEventDate);
            tvEventLocation = itemView.findViewById(R.id.tvEventLocation);
            tvEventOrganiser = itemView.findViewById(R.id.tvEventOrganiser);
            tvEventType = itemView.findViewById(R.id.tvEventType);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvEventName.setText(items.get(position).name);
        holder.tvEventDate.setText(items.get(position).date);
        holder.tvEventLocation.setText(items.get(position).location);
        holder.tvEventOrganiser.setText(items.get(position).organiser);
        holder.tvEventType.setText(items.get(position).type);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private ArrayList<EventModel> items = new ArrayList<>();

    public ItemAdapter() {
        items = GlobalData.evtList;
    }
}
