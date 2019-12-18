package com.example.finalproject.Room;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class RepositoryEvents {
    private DaoEvents daoEvents;

    public RepositoryEvents(Application app) {
        Database db = Database.getDatabase(app);
        daoEvents = db.daoEvents();
    }

    List<EntityEvents> getAllEvents() {
        return (List<EntityEvents>)daoEvents.getAllEvents();
    }

    public void insert(EntityEvents entityEvents) {
        new insertAsyncTask(daoEvents).execute(entityEvents);
    }

    public void deleteAll() {
        new deleteAsyncTask(daoEvents).execute();
    }

    private static class insertAsyncTask extends AsyncTask<EntityEvents, Void, Void> {
        private DaoEvents mAsyncTaskDao;

        insertAsyncTask(DaoEvents dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(EntityEvents... entityEvents) {
            mAsyncTaskDao.insert(entityEvents[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<EntityEvents, Void, Void> {
        private DaoEvents mAsyncTaskDao;

        deleteAsyncTask(DaoEvents dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(EntityEvents... entityEvents) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
