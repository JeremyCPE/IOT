package com.example.projet_iot;

import android.os.AsyncTask;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ReceiverTask extends AsyncTask<Void, String, Void> {
    private static InetAddress address;             // Structure Java décrivant une adresse résolue
    private static int port;
    private DatagramSocket UDPSocket;


    public ReceiverTask(String ip_addr, int port) {
        byte[] biteAdress = ip_addr.getBytes();
        try {
            this.address = InetAddress.getByAddress(biteAdress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while(true){ // Boucle d'attente active de
            //messagesString msgRecu= ... ;
            // attente d'un message sur le réseau
            //publishProgress(msgRecu); // Publication asychrone du résultat}
        }
    }
    protected void onProgressUpdate(String... data) {
        //displayInputs(data[0]);
    }
}
