
package com.example.projet_iot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Activity2 extends AppCompatActivity {
    private String ip;
    private int port;
    private SenderTask st;
    private InetAddress ia;
    private DatagramSocket UDPSocket;
    private InetAddress address;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        this.ip = getIntent().getStringExtra("IP_ADDR");
        this.port = getIntent().getIntExtra("PORT",8080);
        //this.ip = "192.168.1.12";
        //this.port = 10000;
        final Button firstDownButton = findViewById(R.id.firstDownButton);
        final Button secondUpButton = findViewById(R.id.secondUpButton);
        final Button secondDownButton = findViewById(R.id.secondDownButton);
        final Button thirdUpButton = findViewById(R.id.thirdUpButton);
        final Button diffuseButton = findViewById(R.id.diffuseButton);
        final TextView firstText = findViewById(R.id.firstText);
        final TextView secondText = findViewById(R.id.secondText);
        final TextView thirdText = findViewById(R.id.thirdText);

        firstDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeText(firstText, secondText);
            }
        });
        secondDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeText(secondText, thirdText);
            }
        });
        secondUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeText(secondText, firstText);
            }
        });
        thirdUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeText(thirdText, secondText);
            }
        });

        /*diffuseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

        receiveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });*/
        st = new SenderTask();
        try {
            ia = InetAddress.getByName(this.ip);

            (new Thread(){
                public void run() {
                    try {
                        byte[] reset = "(0)".getBytes();
                        UDPSocket = new DatagramSocket();
                        (new ReceiverTask(UDPSocket, port,(TextView)findViewById(R.id.firstPrint))).execute();
                        UDPSocket.send(new DatagramPacket(reset, reset.length, ia, port));
                        Log.d( "action", "reset");

                    } catch (IOException e) {
                        Log.d("network", e.toString());
                    }
                    while (true) {
                        sendData("getValues()");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*this.ip = this.getIntent().getStringExtra("IP_ADDR");
        this.port = this.getIntent().getIntExtra("PORT",8080);
        Toast msg_usr;
        msg_usr = Toast.makeText(this.getApplicationContext(),this.ip+":"+this.port,Toast.LENGTH_LONG);
        msg_usr.show();*/
    }

    private void changeText(TextView firstText, TextView secondText) {
        CharSequence tampon = firstText.getText();
        firstText.setText(secondText.getText());
        secondText.setText(tampon);
    }

    public void testEnvoyerVersServeur(View view){
        TextView tv1 = (TextView) findViewById(R.id.firstText);
        TextView tv2 = (TextView) findViewById(R.id.secondText);
        TextView tv3 = (TextView) findViewById(R.id.thirdText);

        //sendData();

        String txt1 =  tv1.getText().toString().substring(0,1);
        String txt2 =  tv2.getText().toString().substring(0,1);
        String txt3 =  tv3.getText().toString().substring(0,1);

        sendData(txt1+txt2+txt3);
    }

    public void sendData(String data) {
        //TODO : IMPLEMENT ALGORITHM TO SEND DATA
        //String tabToSend[] = {txt1,txt2,txt3};
        //Log.d("Test",txt1 + "\n" + txt2 + "\n" + txt3 );
        Toast msg_usr;
        try {
            final InetAddress s_ia = InetAddress.getByName(this.ip);//InetAddress.getByName(this.getIntent().getStringExtra("IP_ADDR"));

            try {
                final int s_port = this.port;//this.getIntent().getIntExtra("PORT",8080);
               // msg_usr = Toast.makeText(this.getApplicationContext(),getIntent().getStringExtra("IP_ADDR")+":"+getIntent().getIntExtra("PORT",8080),Toast.LENGTH_LONG);
               // msg_usr.show();
                Log.println(Log.ASSERT, "sendData", "envoi de "+data);
                this.st.UDPSend(data,s_ia,s_port);

            } catch (Exception e) {
              //  msg_usr = Toast.makeText(this.getApplicationContext(),"AAAAH "+e.getMessage(),Toast.LENGTH_LONG);
              // msg_usr.show();
                e.printStackTrace();
            }

        } catch (UnknownHostException e) {
           // msg_usr = Toast.makeText(this.getApplicationContext(),"BBBBBH "+e.getMessage(),Toast.LENGTH_LONG);
           // msg_usr.show();
            e.printStackTrace();
        }

    }
}
