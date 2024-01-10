package com.example.packetDisplayer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PacketAdapter extends ArrayAdapter<Packet> {
    private final Activity context;
    private final List<Packet> packetCaptures;

    public PacketAdapter(Activity context, List<Packet> packetCaptures) {
        super(context, R.layout.packetdetails,packetCaptures);
        this.context = context;
        this.packetCaptures = packetCaptures;
    }

    @Override
    @NonNull
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.packetdetails, null,true);

        TextView idText = rowView.findViewById(R.id.idTextView);
        TextView packetNumText = rowView.findViewById(R.id.packetNumTextView);
        TextView packetDateText = rowView.findViewById(R.id.packetDateTextView);
        TextView dstMacText = rowView.findViewById(R.id.dstMacTextView);
        TextView srcMacText = rowView.findViewById(R.id.srcMacTextView);
        TextView etherTypeText = rowView.findViewById(R.id.etherTypeTextView);
        TextView srcIPText = rowView.findViewById(R.id.srcIPTextView);
        TextView dstIPText = rowView.findViewById(R.id.dstIPTextView);
        TextView srcPortText = rowView.findViewById(R.id.srcPortTextView);
        TextView dstPortText = rowView.findViewById(R.id.dstPortTextView);
        TextView transportProtocolText = rowView.findViewById(R.id.transportProtocolTextView);
//        TextView applicationProtocolText = rowView.findViewById(R.id.applicationProtocolTextView);
        TextView sizeText = rowView.findViewById(R.id.sizeTextView);

        Packet currentPacket = packetCaptures.get(position);

        idText.setText("Id : "+ String.valueOf(currentPacket.getId()));
        packetNumText.setText("Packet Number: " +String.valueOf(currentPacket.getPacketNum()));
        packetDateText.setText("Packet Date: " +currentPacket.getPacketDate());
        dstMacText.setText("Destination MAC: " +currentPacket.getDstMac());
        srcMacText.setText("Source MAC: " +currentPacket.getSrcMac());
        etherTypeText.setText("Ether Type: " + currentPacket.getEtherType());
        srcIPText.setText("Source IP: " + currentPacket.getSrcIP());
        dstIPText.setText("Destination IP: " + currentPacket.getDstIP());
        srcPortText.setText("Source Port: " + String.valueOf(currentPacket.getSrcPort()));
        dstPortText.setText("Destination Port: " + String.valueOf(currentPacket.getDstPort()));
        transportProtocolText.setText("Protocol: " + currentPacket.getProtocol());
        sizeText.setText(String.valueOf("Size: " + currentPacket.getSize()));
        // Set other views with corresponding data from the PacketCapture object
        return rowView;
    }
}