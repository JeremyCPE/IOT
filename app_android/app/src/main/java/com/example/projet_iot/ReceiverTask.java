package com.example.projet_iot;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ReceiverTask extends AsyncTask<Void, byte[], Void> {
    private DatagramSocket UDPSocket;
    private final String IP = "";
    private final int PORT;
    private InetAddress address;
    private TextView tv;

    ReceiverTask(DatagramSocket socket, int port, TextView tv){
        this.UDPSocket = socket;
        this.PORT = port;
        this.tv = tv;
    }

    @Override
    protected Void doInBackground(Void... rien) {
        while(true){
            byte[] data = new byte [256];
            Log.d("action","LaunchDoInBackground");
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                UDPSocket.receive(packet);

            } catch (IOException e) {
                Log.d("error",e.toString());
                e.printStackTrace();
            }
            int size = packet.getLength();
            data = packet.getData();
            String s_data = new String(data, 0, size);
            try {
                JSONObject j = new JSONObject(s_data);
                changeTextViewValue(j);
            }catch (org.json.JSONException e){
                System.out.println(e);
            }
            Log.d("data", s_data);

            publishProgress(java.util.Arrays.copyOf(data, size));

        }
    }

    protected  void onProgressUpdate(byte[]... data){
        Log.println(Log.ASSERT, "progress", data.toString());
    }

    protected void changeTextViewValue(JSONObject jo){

        this.tv.setText(jo.toString());
    }
}
