package com.example.packetDisplayer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PacketCaptureAdapter extends ArrayAdapter<PacketCapture> {
    private final Activity context;
    private final List<PacketCapture> packetCaptures;

    public PacketCaptureAdapter(Activity context, List<PacketCapture> packetCaptures) {
        super(context, R.layout.packet,packetCaptures);
        this.context = context;
        this.packetCaptures = packetCaptures;
    }

    @Override
    @NonNull
    public View getView(int position, View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.packet, null,true);

        TextView idText = rowView.findViewById(R.id.id);
        TextView packetCaptureNameText = rowView.findViewById(R.id.packetCaptureName);
        TextView uploadDateText = rowView.findViewById(R.id.uploadDate);

        PacketCapture currentCapture = packetCaptures.get(position);

        idText.setText(String.valueOf("ID: "+currentCapture.getId())); // Adjust as needed
        packetCaptureNameText.setText("Packet Capture Name: "+ String.valueOf(currentCapture.getPacketCaptureName()));
        uploadDateText.setText("Upload Date: "+String.valueOf(currentCapture.getUploadDate()));
        // Set other views with corresponding data from the PacketCapture object
        return rowView;
    }
}
