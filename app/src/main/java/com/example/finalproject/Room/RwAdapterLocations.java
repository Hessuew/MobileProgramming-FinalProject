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

public class RwAdapterLocations extends ArrayAdapter {
    private ArrayList<EntityLocation> rowItems;

    public RwAdapterLocations(@NonNull Context context, ArrayList<EntityLocation> entityListItems) {
        super(context, 0, entityListItems);
        this.rowItems = entityListItems;
    }

    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        // Get the item to be displayed
        final EntityLocation rowItem = rowItems.get(position);

        // IF null then inflate the layout. Otherwise just update layout contents.
        if (convertView == null) {
            // Get the custom layout and inflate it
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_locationlistviewitem, parent, false);
        }

        // Get the customLayout components
        TextView name = convertView.findViewById(R.id.locationTextView);

        // Set content
        name.setText(rowItem.location);

        return convertView;
    }

    public void setListItems(ArrayList<EntityLocation> listItems) {
        rowItems.clear();
        rowItems.addAll(listItems);
        notifyDataSetChanged();
    }

    public void addItem(ArrayList<EntityLocation> listItems) {
        rowItems.addAll(listItems);
        notifyDataSetChanged();
    }
}
