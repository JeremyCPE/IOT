package com.example.projet_iot;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ReceiverTask extends AsyncTask<Void, byte[], Void> {
    private DatagramSocket UDPSocket;
    private final String IP = "";
    private final int PORT;
    private InetAddress address;

    ReceiverTask(DatagramSocket socket, int port){
        this.UDPSocket = socket;
        this.PORT = port;
    }

    @Override
    protected Void doInBackground(Void... rien) {
        Toast msg_usr;
        while(true){
            byte[] data = new byte [256];
            Log.d("action","LaunchDoInBackground");
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                UDPSocket.receive(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
            int size = packet.getLength();
            data = packet.getData();
            if (data.length>0){
                //System.out.println(data);
                Log.d("data","Data = " + data);
            }
            publishProgress(java.util.Arrays.copyOf(data, size));

        }
    }

    protected  void onProgessUpdate(byte[]... data){
        Log.println(Log.ASSERT, "action", data.toString());
    }
}