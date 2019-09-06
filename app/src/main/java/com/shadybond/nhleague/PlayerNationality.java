package com.shadybond.nhleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shadybond.nhleague.Adapters.PlayerNationalityRVAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlayerNationality extends AppCompatActivity {

    Integer idPerson;
    RecyclerView recyclerView;
    PlayerNationalityRVAdapter adapter;
    ArrayList<String> urlImage,nationalityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.idPerson = intent.getExtras().getInt("id");
        urlImage = new ArrayList<>();
        nationalityName = new ArrayList<>();
        volleyPlayerRequest("https://statsapi.web.nhl.com/api/v1//people/");
        setContentView(R.layout.activity_player_nationality);
        initRecyclerView();
    }
    public void volleyPlayerRequest(String url){
        // Get this Integer from Context
        url = url + this.idPerson.toString();
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("people");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject teamDetails = jsonArray.getJSONObject(i);
                                String name = teamDetails.getString("nationality");
                                nationalityName.add(name);

                                /* This switch case can be used to manually setup any flag image, as it is not given by the api itself
                                    I set a custom image to show that image is fetched from a URL

                                * switch(name){
                                *   case 'CAD':
                                *       urlImage.add("Canadian Flag Image URL");
                                *       break;
                                *   case 'USA':
                                *       urlImage.add("Canadian Flag Image URL");
                                 *      break;
                                 *  default:
                                * }
                                * */

                                urlImage.add("https://i.imgur.com/TSSDczr.png");
                            }
                            adapter.setNationalityData(urlImage,nationalityName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error Fetching Nationality", "Error: " + error.getMessage());
            }
        });

        // Adding JsonObject request to request queue
        VolleyRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,"Fetch Nationality of Player");
    }

    public void initRecyclerView (){
        this.recyclerView = findViewById(R.id.recyclerViewNationality);
        this.adapter = new PlayerNationalityRVAdapter(urlImage,nationalityName,this);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
