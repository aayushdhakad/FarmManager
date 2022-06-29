package com.example.shriganesh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntityAdapter extends
        RecyclerView.Adapter<EntityAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView countTextView;
        private Context context ;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.nameTextView = (TextView) itemView.findViewById(R.id.entity_name);
            this.countTextView = (TextView) itemView.findViewById(R.id.count);
            this.context = itemView.getContext();

            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override

                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(true);
                    builder.setTitle("Title");
                    builder.setMessage("Message");
                    builder.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Database db = Database.getInstance(context);
                                    DaoLV daoLV = db.daoLV();
                                    int position = getAbsoluteAdapterPosition();
                                    LVField entity = entities.get(position);
                                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            db.daoLV().delete(entity);
                                        }
                                    });

                                    entities.remove(position);
                                    notifyItemRemoved(position);
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }

            });
        }



    }
    private List<LVField> entities;

    // Pass in the contact array into the constructor
    public EntityAdapter(List<LVField> mEntities) {
        entities = mEntities;
    }

    @Override
    public EntityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_entity, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(EntityAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        LVField field = entities.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(field.getName());
        TextView textView1 = holder.countTextView;
        textView1.setText(field.getCount());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        if(entities!=null)
        return entities.size();
        else
            return 0;
    }
}
