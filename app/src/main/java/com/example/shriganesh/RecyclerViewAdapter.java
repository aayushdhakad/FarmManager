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

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        private Context context ;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.nameTextView = (TextView) itemView.findViewById(R.id.name);
            this.context = itemView.getContext();
            itemView.setOnClickListener(this);
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
                                    Field field = fields.get(position);
                                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            db.daoLV().delete(field);
                                        }
                                    });
                                    fields.remove(position);
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
        @Override
        public void onClick(View view) {
            int position = getAbsoluteAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                Field field = fields.get(position);
                // We can access the data within the views
//                Toast.makeText(context, nameTextView.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,Information.class);
                context.startActivity(intent);
            }
        }


    }
    private List<Field> fields;

    // Pass in the contact array into the constructor
    public RecyclerViewAdapter(List<Field> mFields) {
        fields = mFields;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.row_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Field field = fields.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(field.getName());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return fields.size();
    }
}
