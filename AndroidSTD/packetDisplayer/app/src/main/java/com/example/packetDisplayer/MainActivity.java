package com.example.packetDisplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ListView list;
    static List<PacketCapture> packetCaptureList = new ArrayList<>();

    private static final int ITEMS_PER_PAGE = 50;
    private int currentPage = 1;


    String endpointUrl = "http://10.3.122.89:8080/packetCaptures";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchPacketCaptures();
    }


    public void onNextButtonClick(View view) {
        int totalPages = (int) Math.ceil((double) packetCaptureList.size() / ITEMS_PER_PAGE);
        if (currentPage < totalPages) {
            currentPage++;
            updateListView();
        }else{
            Toast.makeText(this, "No more items left !", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackButtonClick(View view) {
        if (currentPage > 1) {
            currentPage--;
            updateListView();
        }else{
            Toast.makeText(this, "No more items left !", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateListView() {
        int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, packetCaptureList.size());

        List<PacketCapture> subList = packetCaptureList.subList(startIndex, endIndex);

        PacketCaptureAdapter adapter = new PacketCaptureAdapter(MainActivity.this, subList);
        list.setAdapter(adapter);
    }

    @SuppressLint("StaticFieldLeak")
    private void fetchPacketCaptures() {
        new AsyncTask<String, Void, List<PacketCapture>> () {
            @Override
            protected List<PacketCapture> doInBackground(String... strings) {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(endpointUrl)
                        .get()
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBodyString = response.body().string(); // Read the response body once and store it
                    if (response.isSuccessful()) {
                        Log.println(Log.VERBOSE,"Reponse","Response: " + responseBodyString);
                        return parsePacketCaptures(responseBodyString);
                    } else {
                        // Handle the error response here
                        System.out.println("Error response: " + response.code() + " " + response.message());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(List<PacketCapture> packetCaptures) {
                if (packetCaptures != null) {
                    packetCaptureList.addAll(packetCaptures);
                    int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
                    int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, packetCaptureList.size());
                    // Get a sublist of items based on the current page
                    List<PacketCapture> subList = packetCaptureList.subList(startIndex, endIndex);

                    list=(ListView)findViewById(R.id.list);
                    TextView textView = findViewById(R.id.totaltItems);
                    PacketCaptureAdapter adapter1 = new PacketCaptureAdapter(MainActivity.this, subList);
                    list.setAdapter(adapter1);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, PacketDetailsActivity.class);

                            // Pass the selected PacketCapture details to the second activity
                            intent.putExtra("packetDetails", packetCaptureList.get(position).getId());

                            // Start the second activity
                            startActivity(intent);
                        }
                    });
                    textView.setText("Total Items: "+String.valueOf(packetCaptures.size()));
                }
            }
        }.execute();
    }

    private List<PacketCapture> parsePacketCaptures(String response) throws JSONException {
        List<PacketCapture> packetCaptures = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonCapture = jsonArray.getJSONObject(i);
            int id = jsonCapture.getInt("id");
            String packetCaptureName = jsonCapture.getString("packetCaptureName");
            String uploadDate = jsonCapture.getString("uploadDate");

            PacketCapture packetCapture = new PacketCapture(id, packetCaptureName, uploadDate);
            packetCaptures.add(packetCapture);
        }
        return packetCaptures;
    }




}