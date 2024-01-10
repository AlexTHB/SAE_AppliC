package com.example.packetDisplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PacketDetailsActivity extends AppCompatActivity {
    ListView list;
    static  List<Packet> packetCaptureList = new ArrayList<>();

    private static final int ITEMS_PER_PAGE = 50;
    private int currentPage = 1;

    String endpointUrl = "http://10.3.122.89:8080/packets/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packate);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("packetDetails")) {
            // Retrieve the selected Packet details
            String selectedPacket = String.valueOf(intent.getSerializableExtra("packetDetails"));
            fetchPacketCaptures(selectedPacket);
        }
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

        List<Packet> subList = packetCaptureList.subList(startIndex, endIndex);

        PacketAdapter adapter = new PacketAdapter(PacketDetailsActivity.this, subList);
        list.setAdapter(adapter);
    }

    @SuppressLint("StaticFieldLeak")
    private void fetchPacketCaptures(String id) {
        new AsyncTask<String, Void, List<Packet>>() {
            @Override
            protected List<Packet> doInBackground(String... strings) {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(endpointUrl+id)
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
            protected void onPostExecute(List<Packet> packetCaptures) {
                if (packetCaptures != null) {
                    packetCaptureList.addAll(packetCaptures);
                    int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
                    int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, packetCaptureList.size());
                    // Get a sublist of items based on the current page
                    List<Packet> subList = packetCaptureList.subList(startIndex, endIndex);
                    list=(ListView)findViewById(R.id.packetList);
                    TextView textView = findViewById(R.id.totaltItems);
                    textView.setText("Total Items: "+String.valueOf(packetCaptures.size()));
                    PacketAdapter adapter1 = new PacketAdapter(PacketDetailsActivity.this, subList);;
                    list.setAdapter(adapter1);
                }
            }
        }.execute();
    }

    private List<Packet> parsePacketCaptures(String response) throws JSONException {
        List<Packet> packetCaptures = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonCapture = jsonArray.getJSONObject(i);
            String dstIP = jsonCapture.getString("dstIP");
            String dstMac = jsonCapture.getString("dstMac");
            int dstPort = jsonCapture.getInt("dstPort");
            String etherType = jsonCapture.getString("etherType");
            int id = jsonCapture.getInt("id");
            String packetDate = jsonCapture.getString("packetDate");
            int packetNum = jsonCapture.getInt("packetNum");
            int size = jsonCapture.getInt("size");
            String srcIP = jsonCapture.getString("srcIP");
            String srcMac = jsonCapture.getString("srcMac");
            int srcPort = jsonCapture.getInt("srcPort");
            String protocol = jsonCapture.getString("protocol");

            Packet packetCapture = new Packet( id,
             packetNum,
             packetDate,
             dstMac,
             srcMac,
             etherType,
             srcIP,
             dstIP,
             srcPort,
             dstPort,
             protocol,
             size);
            packetCaptures.add(packetCapture);
        }
        return packetCaptures;
    }

}
