package com.example.finalproject.Room;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

public class ModelEvents extends AndroidViewModel {
    private RepositoryEvents mRepository;

    public ModelEvents(@NonNull Application application) {
        super(application);
        this.mRepository = new RepositoryEvents(application);
    }


    public void insert(EntityEvents events) {
        mRepository.insert(events);
    }

    public void delete(EntityEvents events) {
        mRepository.delete(events);
    }

    @SuppressLint("StaticFieldLeak")
    public void showData(final RwAdapterEvents adapter) {
        new AsyncTask<Void, Void, List<EntityEvents>>() {
            @Override
            protected List<EntityEvents> doInBackground(Void... params) {
                //runs on background thread
                return mRepository.getAllEvents();
            }

            @Override
            protected void onPostExecute(List<EntityEvents> items) {
                //runs on main thread
                adapter.setListItems((ArrayList<EntityEvents>)items);
            }
        }.execute();
    }
}
