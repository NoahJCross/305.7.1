package com.example.a71;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class LANDFViewAdapter extends RecyclerView.Adapter<LANDFViewAdapter.ViewHolder> {

    private List<LostItem> listItems;
    private Context context;

    public LANDFViewAdapter(List<LostItem> lostItems, Context context){
        this.listItems = lostItems;
        this.context = context;
    }

    @NonNull
    @Override
    public LANDFViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.lost_and_found_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LANDFViewAdapter.ViewHolder holder, int position) {
        String lOrF = listItems.get(position).getFound() == 0 ? "Lost: " : "Found: ";
        holder.lostItemNameTextView.setText(lOrF + listItems.get(position).getLostItemName());
        holder.lostItemNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                long itemId = listItems.get(holder.getAdapterPosition()).getId();
                intent.putExtra("item_id", itemId);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() { return listItems.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lostItemNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lostItemNameTextView = itemView.findViewById(R.id.lostItemNameTextView);

        }
    }
}
