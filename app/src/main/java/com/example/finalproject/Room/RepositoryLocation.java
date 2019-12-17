package com.example.finalproject.Room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class RepositoryLocation {
    private DaoLocation daoLocation;

    public RepositoryLocation(Application app) {
        Database db = Database.getDatabase(app);
        daoLocation = db.daoLocation();
    }

    List<EntityLocation> getLocation(String id_location) {
        return daoLocation.getLocation(id_location);
    }

    List<EntityLocation> getAllLocations() {
        return daoLocation.getAllLocations();
    }

    public void insert(EntityLocation entityLocation) {
        new insertAsyncTask(daoLocation).execute(entityLocation);
    }

    private static class insertAsyncTask extends AsyncTask<EntityLocation, Void, Void> {

        private DaoLocation mAsyncTaskDao;

        insertAsyncTask(DaoLocation dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final EntityLocation... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}

