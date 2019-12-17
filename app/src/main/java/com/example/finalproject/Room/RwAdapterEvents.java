package com.example.finalproject.Room;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class RwAdapterEvents extends ArrayAdapter {
    private ArrayList<EntityEvents> rowItems;

    public RwAdapterEvents(@NonNull Context context, ArrayList<EntityEvents> entityListItems) {
        super(context, 0, entityListItems);
        this.rowItems = entityListItems;
    }

    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // Get the item to be displayed
        final EntityEvents rowItem = rowItems.get(position);

        // IF null then inflate the layout. Otherwise just update layout contents.
        if (convertView == null) {
            // Get the custom layout and inflate it
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_listviewitem, parent, false);
        }

        // Get the customLayout components
        TextView name = convertView.findViewById(R.id.textView1);
        TextView date = convertView.findViewById(R.id.textView2);
        TextView location = convertView.findViewById(R.id.textView3);
        TextView description = convertView.findViewById(R.id.textView4);

        // Set content
        name.setText(rowItem.name);
        date.setText(rowItem.date);
        location.setText(rowItem.is_free);
        description.setText(rowItem.price);

        return convertView;
    }

    public void setListItems(ArrayList<EntityEvents> listItems) {
        rowItems.clear();
        rowItems.addAll(listItems);
        notifyDataSetChanged();
    }

    public void addItem(ArrayList<EntityEvents> listItems) {
        rowItems.addAll(listItems);
        notifyDataSetChanged();
    }
}
