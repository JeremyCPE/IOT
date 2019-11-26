package com.example.projet_iot;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ReceiverTask extends AsyncTask<Void, byte[], Void> {
    private DatagramSocket UDPSocket;
    //private final String IP = "";
    private final int PORT;
    private final InetAddress address;
    private Handler parentHandler;

    ReceiverTask(int port, String address, Handler srcHandler) throws UnknownHostException, SocketException {
        this.UDPSocket = new DatagramSocket();
        this.PORT = port;
        this.address = InetAddress.getByName(address);
        this.parentHandler=srcHandler;
    }

    public void run() {
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
            Log.d("data","Data = " + data);
            if (data.length>0){
                Message msgToSend = new Message();
                msgToSend.obj = data.toString();
                parentHandler.sendMessage(msgToSend);
            }
            publishProgress(java.util.Arrays.copyOf(data, size));

        }
    }

    protected  void onProgessUpdate(byte[]... data){
        Log.println(Log.ASSERT, "action", data.toString());
    }
}