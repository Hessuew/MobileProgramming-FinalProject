package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Room.EntityEvents;
import com.example.finalproject.Room.EntityLocation;
import com.example.finalproject.Room.ModelEvents;
import com.example.finalproject.Room.ModelLocation;
import com.example.finalproject.Room.RwAdapterEvents;
import com.example.finalproject.Room.RwAdapterLocations;

import java.util.ArrayList;

interface IlistviewListener2 {
    void dataToFragment();
}

public class ListViewFragment2 extends Fragment implements IlistviewListener2 {

    private RwAdapterLocations rwAdapterLocations;
    private IlistviewListener2 mListener;
    private ModelLocation modelLocation;
    ArrayList<EntityLocation> list;
    ListView listView;

    public ListViewFragment2(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout v = (ConstraintLayout) inflater.inflate(R.layout.fragment_listview, container, false);

        list = new ArrayList<>();
        rwAdapterLocations = new RwAdapterLocations(getContext(), list);
        listView = v.findViewById(R.id.listView);

        listView.setAdapter(rwAdapterLocations);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (this instanceof IlistviewListener2) {
            mListener = this;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement IlistviewListener");
        }
    }

    public void dataToFragment () {

        modelLocation = new ViewModelProvider(this).get(ModelLocation.class);
        modelLocation.showLocations(rwAdapterLocations);
    }

}

