package com.example.ead2projectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    Button btn_teams;
    ListView teamlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_teams = findViewById(R.id.teams);
        teamlist = findViewById(R.id.teamlist);

        btn_teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Instantiate the RequestQueue
                RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
                String url = "https://ead2projectapi20220421170132.azurewebsites.net/api/Teams/";


                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String teamID = "";
                        JSONObject teamInfo = new JSONObject();
                        ArrayList<String> allteams = new ArrayList<>();
                        try {
                            for (int i = 0; i < 7; i++){
                                teamInfo = response.getJSONObject(i);
                                teamID = "";
                                teamID = teamInfo.getString("players") + " " + teamInfo.getString("teamname") +  " " + teamInfo.getString("league");
                                allteams.add(teamID);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity2.this, android.R.layout.simple_list_item_1, allteams);
                        teamlist.setAdapter(arrayAdapter);
                        //Toast.makeText(MainActivity2.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this, "Error getting json for teams", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);
            }

        });
    }

    public void nextActivity(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}