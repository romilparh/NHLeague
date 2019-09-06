package com.shadybond.nhleague;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shadybond.nhleague.Adapters.TeamPlayersRVAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeamPlayers extends AppCompatActivity {

    Integer idTeam;
    HashMap<Integer,String> playersNameData,playersJerseyNumberData, playersPositionData,sortedPlayersNameData,sortedPlayersJerseyNumberData, sortedPlayerPositionData;
    ArrayList <String> playersNameDataList, playersJerseyNumberDataList, playersPositionDataList, jerseySorted,nameSorted, positionSorted;
    ArrayList <Integer> idPlayer, idPlayerSorted;
    RecyclerView recyclerView;
    TeamPlayersRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.playersNameData = new HashMap<>();
        this.playersJerseyNumberData = new HashMap<>();
        this.playersPositionData = new HashMap<>();
        this.playersJerseyNumberDataList = new ArrayList<>();
        this.playersNameDataList = new ArrayList<>();
        this.playersPositionDataList = new ArrayList<>();
        this.idPlayer = new ArrayList<>();

        // For Sorted Data according to Sort Function
        this.sortedPlayersJerseyNumberData = new HashMap<>();
        this.sortedPlayersNameData = new HashMap<>();
        this.sortedPlayerPositionData = new HashMap<>();

        Intent intent = getIntent();
        this.idTeam = intent.getExtras().getInt("id");

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.layout_action_bar);
        View view =getSupportActionBar().getCustomView();

        ImageButton imageButtonNameSort= (ImageButton)view.findViewById(R.id.filter_by_name);

        imageButtonNameSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sort By Name",Toast.LENGTH_LONG).show();
                sortByName();
            }
        });

        ImageButton imageButtonNumberSort= (ImageButton)view.findViewById(R.id.filter_by_number);

        imageButtonNumberSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sort By Jersey",Toast.LENGTH_LONG).show();
                sortByJerseyNumber();
            }
        });

        ImageButton imageButtonLeftWing= (ImageButton)view.findViewById(R.id.filter_by_left_wing);

        imageButtonLeftWing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sort By Left Wing",Toast.LENGTH_LONG).show();
                filterByPosition("Left Wing");
            }
        });

        ImageButton imageButtonRightWing= (ImageButton)view.findViewById(R.id.filter_by_right_wing);

        imageButtonRightWing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sort By Right Wing",Toast.LENGTH_LONG).show();
                filterByPosition("Right Wing");
            }
        });

        ImageButton imageButtonCenter= (ImageButton)view.findViewById(R.id.filter_by_center);

        imageButtonCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sort By Center",Toast.LENGTH_LONG).show();
                filterByPosition("Center");
            }
        });

        ImageButton imageButtonDefenseman= (ImageButton)view.findViewById(R.id.filter_by_defenseman);

        imageButtonDefenseman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sort By Defense",Toast.LENGTH_LONG).show();
                filterByPosition("Defenseman");
            }
        });

        ImageButton imageButtonGoalie= (ImageButton)view.findViewById(R.id.filter_by_goalie);

        imageButtonGoalie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sort By Goalie",Toast.LENGTH_LONG).show();
                filterByPosition("Goalie");
            }
        });

        ImageButton imageButtonAll= (ImageButton)view.findViewById(R.id.show_all);

        imageButtonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Sort By Jersey",Toast.LENGTH_LONG).show();
                adapter.setPlayersData(playersNameDataList,playersJerseyNumberDataList,playersPositionDataList,idPlayer);
            }
        });

        setContentView(R.layout.activity_team_players);

        this.volleyTeamPlayersRequest("https://statsapi.web.nhl.com/api/v1/teams/");

        this.initRecyclerView();
    }

    // Sort Function through String Value
    public LinkedHashMap<Integer, String> sortHashMapByValues(HashMap<Integer, String> passedMap, boolean isInt) {
        ArrayList<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
        ArrayList<String> mapValues = new ArrayList<>(passedMap.values());
        if(isInt == true) {
            ArrayList<Integer> mapValuesInteger = getIntegerArray(mapValues);
            Collections.sort(mapValuesInteger);
            mapValues.clear();
            for (int i = 0; i < mapValuesInteger.size(); i++) {
                mapValues.add(String.valueOf(mapValuesInteger.get(i)));
            }
        }
        else {
            Collections.sort(mapValues);
        }
        Collections.sort(mapKeys);

        LinkedHashMap<Integer, String> sortedMap =
                new LinkedHashMap<>();

        Iterator<String> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            String val = valueIt.next();
            Iterator<Integer> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Integer key = keyIt.next();
                String comp1 = passedMap.get(key);
                String comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    private ArrayList<Integer> getIntegerArray(ArrayList<String> stringArray) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(String stringValue : stringArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Integer.parseInt(stringValue));
            } catch(NumberFormatException nfe) {
                //System.out.println("Could not parse " + nfe);
                Log.w("NumberFormat", "Parsing failed! " + stringValue + " can not be an integer");
            }
        }
        return result;
    }

    // Query to fetch  and save data
    public void volleyTeamPlayersRequest(String url){
        url = url +String.valueOf(this.idTeam)+ "/roster";
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("roster");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject roster = jsonArray.getJSONObject(i);
                                JSONObject person = roster.getJSONObject("person");
                                JSONObject position = roster.getJSONObject("position");

                                String jerseyNumber,positionName;
                                try{
                                    jerseyNumber = roster.getString("jerseyNumber");
                                }catch (Exception e){
                                    jerseyNumber = "-1";
                                }
                                positionName = position.getString("name");
                                Integer id = new Integer(person.getInt("id"));
                                String name = person.getString("fullName");


                                playersNameData.put(id,name);
                                playersJerseyNumberData.put(id,jerseyNumber);
                                playersPositionData.put(id,positionName);

                            }
                            playersNameDataList = new ArrayList<>(playersNameData.values());
                            idPlayer = new ArrayList<>(playersNameData.keySet());
                            playersJerseyNumberDataList = new ArrayList<>(playersJerseyNumberData.values());
                            playersPositionDataList = new ArrayList<>(playersPositionData.values());
                            adapter.setPlayersData(playersNameDataList,playersJerseyNumberDataList,playersPositionDataList,idPlayer);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error Fetching Team Players", "Error: " + error.getMessage());
            }
        });

        // Adding JsonObject request to request queue
        VolleyRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,"Fetch Teams Players: ID, Name, position");
    }

    public void clearData(){
        try{
            this.sortedPlayerPositionData.clear();
            this.sortedPlayersJerseyNumberData.clear();
            this.sortedPlayersNameData.clear();
            this.nameSorted.removeAll(this.nameSorted);
            this.jerseySorted.removeAll(this.jerseySorted);
            this.positionSorted.removeAll(this.positionSorted);
        } catch (Exception e){

        }
    }

    // Sort By Name function
    public void sortByName(){
        // Delete All previous data in sorted array lists
        this.clearData();

        // Sort and Add new Data
            // use Name HashMap sort
        this.sortedPlayersNameData = this.sortHashMapByValues(this.playersNameData,false);
        this.nameSorted = new ArrayList<>(this.sortedPlayersNameData.values());
        this.idPlayerSorted = new ArrayList<>(this.sortedPlayersNameData.keySet());

            // sort jersey and position
        this.jerseySorted = this.sortArrayList(this.sortedPlayersNameData,this.playersJerseyNumberData);
        this.positionSorted = this.sortArrayList(this.sortedPlayersNameData,this.playersPositionData);

        // Push Data to RecyclerView
        adapter.setPlayersData(this.nameSorted,this.jerseySorted,this.positionSorted,this.idPlayerSorted);
    }

    // Sort By Jersey Number

    public void sortByJerseyNumber(){
        // Delete All previous data in sorted array lists
        this.clearData();

        // Sort and Add new Data
            // use jersey hashmap sort
        this.sortedPlayersJerseyNumberData = this.sortHashMapByValues(this.playersJerseyNumberData,true);
        this.jerseySorted = new ArrayList<>(this.sortedPlayersJerseyNumberData.values());
        this.idPlayerSorted = new ArrayList<>(this.sortedPlayersJerseyNumberData.keySet());

            // sort name and position
        this.nameSorted = this.sortArrayList(this.sortedPlayersJerseyNumberData,this.playersNameData);
        this.positionSorted = this.sortArrayList(this.sortedPlayersJerseyNumberData,this.playersPositionData);

        // Push Data to RecyclerView
        adapter.setPlayersData(this.nameSorted,this.jerseySorted,this.positionSorted,this.idPlayerSorted);
    }

    public void filterByPosition(String position){
        // Delete All previous data in sorted array lists and Hash Maps
        this.clearData();

        // Sort and Add new Data
        // use position hashmap sort

        HashMap<Integer, String> object = new HashMap<Integer, String>();

        for (Map.Entry<Integer, String> entry : this.playersPositionData.entrySet()) {
            if (entry.getValue().equals(position)) {
                object.put(entry.getKey(), entry.getValue());
            }
        }

        this.positionSorted = new ArrayList<>(object.values());
        this.idPlayerSorted = new ArrayList<>(object.keySet());

        // sort name and jersey
        this.nameSorted = this.sortArrayList(object,this.playersNameData);
        this.jerseySorted = this.sortArrayList(object,this.playersJerseyNumberData);

        // Push Data to RecyclerView
        adapter.setPlayersData(this.nameSorted,this.jerseySorted,this.positionSorted,this.idPlayerSorted);
    }

    public ArrayList<String> sortArrayList(HashMap<Integer,String> sortedHashMap, HashMap<Integer,String> dataToBeSortedHashMap){
        ArrayList<String> sortedArrayList = new ArrayList<>();
        ArrayList<Integer> sortedHashMapSets = new ArrayList(sortedHashMap.keySet());

        for(int i=0;i<sortedHashMap.size();i++){
            sortedArrayList.add(dataToBeSortedHashMap.get(sortedHashMapSets.get(i)));
        }
        return sortedArrayList;
    }

    public ArrayList<String> returnPositions(HashMap<Integer,String> positionHashMap){
        LinkedHashSet<String> hashSet = new LinkedHashSet<>();
        hashSet.addAll(new ArrayList(positionHashMap.values()));
        return new ArrayList(hashSet);
    }

    public void initRecyclerView(){
        this.recyclerView = findViewById(R.id.playerActivityRecyclerView);
        this.adapter = new TeamPlayersRVAdapter(this.playersNameDataList, this.playersJerseyNumberDataList, this.playersPositionDataList,this.idPlayer,this);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
