
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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {
    private String ip;
    private int port;
    private SenderTask st;
    //private InetAddress ia;
    private DatagramSocket UDPSocket;
    private InetAddress address;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        this.ip = getIntent().getStringExtra("IP_ADDR");
        this.port = getIntent().getIntExtra("PORT",8080);
        final Button firstDownButton = findViewById(R.id.firstDownButton);
        final Button secondUpButton = findViewById(R.id.secondUpButton);
        final Button secondDownButton = findViewById(R.id.secondDownButton);
        final Button thirdUpButton = findViewById(R.id.thirdUpButton);
        final Button diffuseButton = findViewById(R.id.diffuseButton);
        final Button receiveButton = findViewById(R.id.receiveButton);
        final TextView firstText = findViewById(R.id.firstText);
        final TextView secondText = findViewById(R.id.secondText);
        final TextView thirdText = findViewById(R.id.thirdText);
        final ArrayList<TextView> arrayTextView = new ArrayList<>();
        arrayTextView.add(firstText);
        arrayTextView.add(secondText);
        arrayTextView.add(thirdText);

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

        diffuseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendData("HLT");
            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendData("getValues()");
            }
        });

        st = new SenderTask();
/*        (new Thread(){
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
                    (new ReceiverTask(port,ip)).run();

                    Looper.loop();
                } catch (Exception e){
                    Log.d("network", e.toString());
                }
            }
            }).start();*/
            //ia = InetAddress.getByName(this.ip);

            (new Thread(){
                public void run(){
                    try {
                        (new ReceiverTask(7070, arrayTextView)).execute();
                    } catch (IOException e) {
                        Log.d("network", e.toString());
                    }
                }
            }).start();
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

        //sendData(txt1+txt2+txt3);

        String txt1 =  tv1.getText().toString().substring(0,1);
        String txt2 =  tv2.getText().toString().substring(0,1);
        String txt3 =  tv3.getText().toString().substring(0,1);

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
                Log.println(Log.ASSERT, "sendData", "envoi de "+data);
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
