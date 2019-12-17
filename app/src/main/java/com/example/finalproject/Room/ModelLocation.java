package com.example.finalproject.Room;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class ModelLocation extends AndroidViewModel {
    private RepositoryLocation mRepository;

    public ModelLocation(@NonNull Application application) {
        super(application);
        this.mRepository = new RepositoryLocation(application);
    }

    public void insert(EntityLocation location) {
        mRepository.insert(location);
    }

    public void getAllLocations() {mRepository.getAllLocations();}

    public List<EntityLocation> getLocation(String id_location) {
        List<EntityLocation> loca = mRepository.getLocation(id_location);
        return loca;
    }

    @SuppressLint("StaticFieldLeak")
    public void showLocations(final RwAdapterLocations adapter) {
        new AsyncTask<Void, Void, List<EntityLocation>>() {
            @Override
            protected List<EntityLocation> doInBackground(Void... params) {
                //runs on background thread
                return mRepository.getAllLocations();
            }

            @Override
            protected void onPostExecute(List<EntityLocation> items) {
                //runs on main thread
                adapter.setListItems((ArrayList<EntityLocation>)items);
            }
        }.execute();
    }
}