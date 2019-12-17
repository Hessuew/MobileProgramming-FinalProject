package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.Room.EntityEvents;
import com.example.finalproject.Room.EntityLocation;
import com.example.finalproject.Room.Location;
import com.example.finalproject.Room.ModelEvents;
import com.example.finalproject.Room.ModelLocation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ButtonsFragment.IbuttonsListener {

    private ModelLocation modelLocation;
    private ModelEvents modelEvents;
    private EntityLocation loc;
    private EntityEvents ev;
    final String locationUrl = "http://api.hel.fi/linkedevents/v1/place/";
    final String eventsUrl = "http://api.hel.fi/linkedevents/v1/search/?type=event&q=";
    private ArrayList<String> arr = new ArrayList<String>();
    public static Bundle myBundle = new Bundle();
    private Context context;
    final String ilmainen = "ilmainen";
    final String maksullinen = "maksullinen";
    // Layouts
    LinearLayout mainLayout;
    // Views
    Button buttonFind;
    TextView editTextSearch;
    ListView listView;
    // List
    Toast loading;
    // Database
    // Request HTTP
    private RequestQueue requestQueue;

    // Internet connection status
    private ConnectivityManager connMan;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = Toast.makeText(getApplicationContext(), "Loading..", Toast.LENGTH_LONG);

        if (context == null) {
            context = getApplicationContext();
        }

        ev = new EntityEvents();
        loc = new EntityLocation();


        modelLocation = new ViewModelProvider(this).get(ModelLocation.class);
        modelEvents = new ViewModelProvider(this).get(ModelEvents.class);
        //modelLocation.showLocations(locationAdapter);


        fetchLocations(locationUrl);
    }

    private void fetchLocations(String locationUrl) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, locationUrl, null,
                response -> {
                    try {
                        getDataFromResponse1(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> getToast("Error fetching data: " + error.getMessage()).show());
                        //Toast.makeText(MainActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);
    }

    public void getDataFromResponse1 (JSONObject response) throws JSONException {

        // Deserialisoitu tapa hakea JSON-taulukko
        /* TARKISTA
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type listantyyppi = new TypeToken<ArrayList<NimiJaPvm>>(){}.getType();
        ArrayList<NimiJaPvm> lista;

        lista = gson.fromJson(response.toString(), listantyyppi);
*/
        String nextUrl = response.getJSONObject("meta").getString("next");
        String location = "";
            JSONArray result = response.getJSONArray("data");
            for (int i = 0; i < result.length(); i++) {
                JSONObject o = result.getJSONObject(i);
                    if(o.has("address_locality")){
                        if(!o.getString("address_locality").equals("null")){
                            if(o.getJSONObject("address_locality").has("fi"))
                            location = o.getJSONObject("address_locality").getString("fi");
                        }
                    }
                        if (!arr.contains(location) && location.length() > 1) {
                            arr.add(location);

                            loc.id_location = o.getString("id");
                            loc.location = location;

                            JSONObject oStreetAddress = o.getJSONObject("street_address");
                            loc.address = oStreetAddress.getString("fi");
                            modelLocation.insert(loc);
                        }

            }
            if (!nextUrl.equals(null))
                fetchLocations(nextUrl);


        ListViewFragment2 listViewFragment2 = (ListViewFragment2)
                getSupportFragmentManager().findFragmentById(R.id.fragmentListView2);
        listViewFragment2.dataToFragment();
        //Toast.makeText(getApplicationContext(), "Locations loaded", Toast.LENGTH_SHORT).show();




                        /*
                if (o.has("divisions")) {
                    for (int j = 0; j < 4; j++) {
                        JSONObject oDivisions = o.getJSONArray("divisions").getJSONObject(j);
                        String divisionType = oDivisions.getString("type");
                        if (divisionType.equals("district")) {
                            loc.location = o.getJSONArray("divisions").getJSONObject(j).getJSONObject("name").getString("fi");
                        }
                    }


                if (o.has("divisions")) {
                    if(o.getJSONArray("divisions").length() > 0){
                        loc.location = o.getJSONArray("divisions").getJSONObject(0).getJSONObject("name").getString("fi");
                    }
                 */
    }

    private JsonObjectRequest fetchEvents(CharSequence choosedDate, CharSequence choosedLocation, String baseUrl) {
        // Exits the program IF search is empty
        if (choosedLocation.length() < 1) {
            this.finish();
        }

        String requestUrl;
        if(choosedDate.length() > 1 && choosedLocation.length() > 1 && choosedDate.toString() != "Date" ) {
            requestUrl = baseUrl + choosedLocation + "&start=" + choosedDate;
        } else if (choosedDate.length() < 1 && choosedLocation.length() > 1)
            requestUrl = baseUrl + choosedLocation;
        else
            requestUrl = baseUrl;

        loading.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                response -> {
                    try {
                        addEventsToDaoEvents(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                },
                error ->getToast("Error fetching data: " + error.getMessage()).show());
        //Toast.makeText(MainActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show());
        return jsonObjectRequest;
    }

    private void addEventsToDaoEvents (JSONObject response) throws JSONException, ParseException {

        String nextUrl = null;
        nextUrl = response.getJSONObject("meta").getString("next");

        JSONArray result = response.getJSONArray("data");

        if (result != null && result.length() > 0) {
            modelEvents.delete(ev);
            for (int i = 0; i < result.length(); i++) {
                JSONObject o = result.getJSONObject(i);
                //String e = o.getString("data"); //TARKISTA onko oikein
                JSONObject oName = o.getJSONObject("name");
                if(oName.has("fi"))
                    ev.name = oName.getString("fi");
                else
                    ev.name = oName.getString("sv");

                ev.date = o.getString("start_time");

                JSONArray oOffers = o.getJSONArray("offers");
                int asd = o.getJSONArray("offers").length();
                if (o.getJSONArray("offers").length() > 0 ){
                    String is_free = o.getJSONArray("offers").getJSONObject(0).getString("is_free");
                    if (is_free.equals("false")) {
                        ev.is_free = maksullinen;
                        if (!o.getJSONArray("offers").getJSONObject(0).getString("price").equals("null"))
                        ev.price = o.getJSONArray("offers").getJSONObject(0).getString("price");
                        else {
                            ev.price = o.getJSONArray("offers").getJSONObject(0).getString("description");
                            ev.pricedescription = o.getJSONArray("offers").getJSONObject(0).getJSONObject("info_url").getString("fi");
                        }
                    } else{
                        ev.is_free = ilmainen;
                        ev.price = "0";
                    }
                }
/*
                JSONObject oLocation = o.getJSONObject("location");
                String locationid = oLocation.getString("@id");
                locationid = locationid.substring(locationid.indexOf("tprek"), locationid.length()-1);
                count1.setValue(modelLocation.getLocation(locationid));
                ev.location = count1.getValue().toString();


 */
                JSONObject oDescription = o.getJSONObject("short_description");
                if(oDescription.has("fi"))
                    ev.shortdescription = oDescription.getString("fi");
                else
                    ev.shortdescription = oDescription.getString("sv");

                modelEvents.insert(ev);
            }
            if (nextUrl.length() > 5)
                addRequestToQueue(fetchEvents("", "", nextUrl));

            getToast("Events loaded");

            ListViewFragment listViewFragment = (ListViewFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragmentListView);
            listViewFragment.dataToFragment();
            // Toast.makeText(getApplicationContext(), "Events loaded", Toast.LENGTH_SHORT).show();
        } else{
            getToast("No events were found with these search conditions").show();
        }
    }
    private void addRequestToQueue(Request request) {
        // IF there is no internet connection. Prompt user and don't add query to queue.
        if (!testInternet(context)) {
            getToast("Internet connection not available!");
            hideKeyboard();
            //Toast.makeText(getApplicationContext(), "Internet connection not available!", Toast.LENGTH_LONG).show();
            return;
        }
        requestQueue.add(request);
    }
/*
    private void clearSearch() { //TARKISTA
        editTextSearch.setText("");
        editTextSearch.clearFocus();
        hideKeyboard();
    }


 */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean testInternet(Context context) {

        final Network[] allNetworks;
        connMan = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        allNetworks = connMan.getAllNetworks();
        return (allNetworks != null);
    }
    /*
    private boolean checkInternetConnection() {
        // Return true IF there is a active network with internet connection.
        NetworkInfo network = connMan.getActiveNetworkInfo();
        return network != null && network.isConnectedOrConnecting();
    }


     */
    // for interface
    @Override
    public void onGetDataClicked() {
        EdittextFragment EdittextFragment = (EdittextFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentEdittext);
        EdittextFragment.LocationtoEdittext();

        String choosedDate = MainActivity.myBundle.get("choosedDate").toString();


        if(choosedDate == "Date")
            choosedDate = "";
        String choosedLocation = MainActivity.myBundle.get("location").toString();
        if (choosedLocation == "")
            getToast("Choose location");

        addRequestToQueue(fetchEvents(choosedDate, choosedLocation, eventsUrl));
    }

    Toast getToast(String text) {
        return Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
    }
}
