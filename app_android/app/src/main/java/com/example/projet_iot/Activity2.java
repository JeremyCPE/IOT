
package com.example.projet_iot;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Activity2 extends AppCompatActivity {
    private String ip;
    private int port;
    private SenderTask st;
    //private InetAddress ia;
    //private DatagramSocket UDPSocket;
    //private InetAddress address;
    public Handler mHandler;


    String JSON_STRING = " {" +
            "  \"temperature\": ?," +
            "  \"light\": ?," +
            "  \"humidity\": ?" +
            "} ";
    String temp, light, hum;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        this.ip = getIntent().getStringExtra("IP_ADDR");
        this.port = getIntent().getIntExtra("PORT",8080);
        final Button firstDownButton = findViewById(R.id.firstDownButton);
        final Button secondUpButton = findViewById(R.id.secondUpButton);
        final Button secondDownButton = findViewById(R.id.secondDownButton);
        final Button thirdUpButton = findViewById(R.id.thirdUpButton);

        final TextView firstText = findViewById(R.id.firstText);
        final TextView secondText = findViewById(R.id.secondText);
        final TextView thirdText = findViewById(R.id.thirdText);

        final TextView printTemp =  findViewById(R.id.printTemp);
        final TextView printHumidity =  findViewById(R.id.printHumidity);
        final TextView printLuminosity =  findViewById(R.id.printLuminosity);

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


        //TODO : DEPLACER LE JSON DANS LE onDataReceived(Message msg) (si elle marche)
        try {
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(JSON_STRING);
            // get carac from JSON
            temp = obj.getString("temperature");
            light = obj.getString("light");
            hum = obj.getString("humidity");
            // set in TextView's
            printTemp.setText("Temp.: "+temp + "°C");
            printLuminosity.setText("Lum.: "+light + "Lux");
            printHumidity.setText("Hum.:"+hum + "%");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        st = new SenderTask();
        (new Thread(){
            public void run(){
                try {
                    Looper.prepare();
                    mHandler = new Handler() {
                        public void handleMessage(Message msg) {
                            // process incoming messages here
                            // this will run in non-ui/background thread
                            Log.d("deb_network", msg.toString());
                        }
                    };
                    (new ReceiverTask(port,ip,mHandler)).run();

                    Looper.loop();
                } catch (Exception e){
                    Log.d("network", e.toString());
                }
            }
            }).start();
        /*try {
            ia = InetAddress.getByName(this.ip);

            (new Thread(){
                public void run(){
                    try {
                        byte[] reset = "(0)".getBytes();
                        UDPSocket = new DatagramSocket();
                        (new ReceiverTask(UDPSocket, port)).execute();
                        UDPSocket.send(new DatagramPacket(reset, reset.length, ia, port));
                        Log.d( "action", "reset");

                    } catch (IOException e) {
                        Log.d("network", e.toString());
                    }
                }
            }).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
    }

    public void onDataReceived(Message msg){
        Toast msg_usr;
        msg_usr = Toast.makeText(this.getApplicationContext(),msg.toString(),Toast.LENGTH_SHORT);
        msg_usr.show();
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



        String txt1 =  tv1.getText().toString().substring(0,1);
        String txt2 =  tv2.getText().toString().substring(0,1);
        String txt3 =  tv3.getText().toString().substring(0,1);
        sendData(txt1+txt2+txt3);
        //sendData("getValues()");
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
                msg_usr = Toast.makeText(this.getApplicationContext(),getIntent().getStringExtra("IP_ADDR")+":"+getIntent().getIntExtra("PORT",8080),Toast.LENGTH_LONG);
                msg_usr.show();
                this.st.UDPSend(data,s_ia,s_port);

            } catch (Exception e) {
                msg_usr = Toast.makeText(this.getApplicationContext(),"AAAAH "+e.getMessage(),Toast.LENGTH_LONG);
                msg_usr.show();
                e.printStackTrace();
            }

        } catch (UnknownHostException e) {
            msg_usr = Toast.makeText(this.getApplicationContext(),"BBBBBH "+e.getMessage(),Toast.LENGTH_LONG);
            msg_usr.show();
            e.printStackTrace();
        }

    }
}
