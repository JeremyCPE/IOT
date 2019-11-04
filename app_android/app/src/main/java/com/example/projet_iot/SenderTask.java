package com.example.projet_iot;
import java.io.IOException;
import java.lang.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SenderTask {

    public SenderTask() {

    }

    public void UDPSend(final String dataAEnvoyer, final InetAddress ip_addr, final int port){
        (new Thread(){
            @Override
            public void run() {

                byte[] data = dataAEnvoyer.getBytes();

                DatagramPacket packet = new DatagramPacket(data,data.length,ip_addr,port);
                DatagramSocket UDPSocket;
                try {
                    UDPSocket = new DatagramSocket();
                    UDPSocket.send(packet);
                } catch (SocketException ex) {
                    ex.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
