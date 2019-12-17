package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.Room.EntityEvents;
import com.example.finalproject.Room.ModelEvents;
import com.example.finalproject.Room.RwAdapterEvents;

import java.util.ArrayList;

interface IlistviewListener {
    void dataToFragment();
}

public class ListViewFragment extends Fragment implements IlistviewListener {

    private RwAdapterEvents rwAdapterEvents;
    private IlistviewListener mListener;
    private ModelEvents modelEvents;
    ArrayList<EntityEvents> list;
    ListView listView;

    public ListViewFragment(){
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
        rwAdapterEvents = new RwAdapterEvents(getContext(), list);
        listView = v.findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EntityEvents entEvent = list.get(i);
                String toastText;
                if(entEvent.pricedescription == null)
                    toastText = "Kuvaus: " + entEvent.shortdescription;
                else
                    toastText = "info_url: " + entEvent.pricedescription + ", kuvaus: " + entEvent.shortdescription;

                Toast.makeText(getContext(), toastText, Toast.LENGTH_LONG).show();
            }
        });

        listView.setAdapter(rwAdapterEvents);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
        recyclerView = getView().findViewById(R.id.recycler_view);

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(rwAdapterEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        */

        /*
         RecyclerView Esimerkki
        // NameDate RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        final RwAdapterEvents adapter = new RwAdapterEvents(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Views
        listView = findViewById(R.id.listView);*/

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (this instanceof IlistviewListener) {
            mListener = this;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement IlistviewListener");
        }
    }

    public void dataToFragment () {
        modelEvents = new ViewModelProvider(this).get(ModelEvents.class);
        modelEvents.showData(rwAdapterEvents);
    }

}

