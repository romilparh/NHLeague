package com.shadybond.nhleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shadybond.nhleague.Adapters.MainActivityRecyclerViewAdapter;
import com.shadybond.nhleague.models.TeamsModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    ArrayList <TeamsModel> teamsData;
    RecyclerView recyclerView;
    MainActivityRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.teamsData = new ArrayList<>();
        setContentView(R.layout.activity_main);
        this.volleyTeamsRequest("https://statsapi.web.nhl.com/api/v1/teams/");
        this.initRecyclerView();
    }

    public void volleyTeamsRequest(String url){
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("teams");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject teamDetails = jsonArray.getJSONObject(i);
                                Integer id = new Integer(teamDetails.getInt("id"));
                                String name = teamDetails.getString("name");

                                /* This switch case can be used to manually setup any team image, as it is not given by the api itself
                                    I set a custom image to show that image is fetched from a URL

                                * switch(1){
                                *   case "1":
                                *       teamsData.add(new TeamsModel(id,name,"New Jersey Devils Image URL"));
                                *       break;
                                 *  default:
                                * }
                                * */

                                // Wait here
                                teamsData.add(new TeamsModel(id,name,"https://i.imgur.com/TSSDczr.png"));
                            }
                            adapter.setTeamsData(teamsData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error Fetching Teams", "Error: " + error.getMessage());
            }
        });

        // Adding JsonObject request to request queue
        VolleyRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,"Fetch Teams: ID and Name");
    }

    public void initRecyclerView (){
        this.recyclerView = findViewById(R.id.mainActivityRecyclerView);
        this.adapter = new MainActivityRecyclerViewAdapter(teamsData,this);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
