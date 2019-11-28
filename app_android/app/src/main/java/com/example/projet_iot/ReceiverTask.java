package com.example.projet_iot;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class ReceiverTask extends AsyncTask<Void, byte[], Void> {
    private DatagramSocket UDPSocket;
    //private final String IP = "";
    private final int PORT;
    private ArrayList<TextView> altv;


    ReceiverTask(int port, ArrayList<TextView> altv) throws UnknownHostException, SocketException {
        this.UDPSocket = new DatagramSocket(port);
        this.PORT = port;
        this.altv = altv;
    }

/*    public void run() {
        //Looper.prepare();

        try {
            byte[] reset = "(0)".getBytes();
            //UDPSocket = new DatagramSocket();
            //(new ReceiverTask(this.UDPSocket, this.PORT)).execute();
            UDPSocket.send(new DatagramPacket(reset, reset.length, this.address, this.PORT));
            Log.d( "action", "reset");
            doInBackground();

        } catch (IOException e) {
            Log.d("network", e.toString());
        }

        //Looper.loop();
    }*/

    @Override
    protected Void doInBackground(Void... rien) {
        byte[] data = new byte [1024];
        while(true){

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

    protected void changeTextViewValue(JSONObject obj) throws JSONException {
            // get carac from JSON
        String temp = obj.getString("temperature");
        String hum = obj.getString("humidity");
        String light = obj.getString("light");
            // set in TextView's
        altv.get(0).setText("Température: "+temp + "°C");
        altv.get(1).setText("Humidity:"+hum + "%");
        altv.get(2).setText("Luminosité: "+light + "Lux");



    }
}
