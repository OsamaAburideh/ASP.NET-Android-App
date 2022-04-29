package com.example.ead2projectapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btn_players;
    ListView playerlist;
    Button btn_searchPlayers;
    EditText playerNameInput;
    TextView playerview;
    Button translatebtn;
    String Lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_players = findViewById(R.id.players);
        playerlist = findViewById(R.id.playerlist);
        btn_searchPlayers = findViewById(R.id.searchPlayer);
        playerNameInput = findViewById(R.id.playerNameInput);
        playerview = findViewById(R.id.playerview);

        //button to get all player name and position
        btn_players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Instantiate the RequestQueue
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://ead2projectapi20220421170132.azurewebsites.net/api/Players/";


                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String playerID = "";
                        JSONObject playerInfo = new JSONObject();
                        ArrayList<String> allPlayers = new ArrayList<>();
                        try {
                            for (int i = 0; i < 8; i++){
                                playerInfo = response.getJSONObject(i);
                                playerID = "";
                                playerID = playerInfo.getString("playername") + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t " + playerInfo.getString("position");
                                allPlayers.add(playerID);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, allPlayers);
                        playerlist.setAdapter(arrayAdapter);
                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Invalid Player Name", Toast.LENGTH_LONG).show();
                    }
                });

                queue.add(request);
            }

        });

        //Search Player by Name
        btn_searchPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Instantiate the RequestQueue
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://ead2projectapi20220421170132.azurewebsites.net/api/Players/" + playerNameInput.getText().toString();

                //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String playerID = "";
                        try {
                            playerID = "Age: " +response.getString("age") + "\nPosition: " + response.getString("position");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        playerview.setText(playerID);
                        //Toast.makeText(MainActivity.this, playerID.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error getting json for players", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(request);
            }
        });


        translatebtn = findViewById(R.id.translatebtn);
        Lng = Locale.getDefault().getLanguage().toLowerCase(Locale.ROOT);

        //When clicked translate to Arabic or English
        translatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Lng.equals("en")){
                    Lng = "ar";
                    Locale locale = new Locale(Lng);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    recreate();
                    return;
                }
                if(Lng.equals("ar")){
                    Lng = "en";
                    Locale locale = new Locale(Lng);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    recreate();
                    return;
                }
            }
        });
    }

    public void nextActivity(View v){
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
    }

}