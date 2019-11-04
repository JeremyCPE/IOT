package com.example.projet_iot;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public final String IP_ADDR = "com.example.projet_iot.IP_ADDR";
    public final String PORT = "com.example.projet_iot.PORT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean isIPBlocOk(String ip_bloc){
        boolean isOk = false;
        int int_ip_bloc;
        try{
            int_ip_bloc = Integer.parseInt(ip_bloc);
            if((int_ip_bloc>=0)&&(int_ip_bloc<=255)){
                isOk = true;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return isOk;
    }

    public boolean isIPPortOk(String port){
        boolean isOk = false;
        int int_port;
        try{
            int_port = Integer.parseInt(port);
            if((int_port>=0)&&(int_port<=65535)){//port min et max
                isOk = true;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return isOk;
    }

    public void connectToServer(View view) {
        String ip_address = "";
        Toast msg_usr;
        int ip_1,ip_2,ip_3,ip_4;
        int port = 0;
        EditText et_ip1, et_ip2,et_ip3,et_ip4,et_port;
        boolean isConfigOk = true;

        et_ip1 = (EditText)findViewById(R.id.ip_1);
        et_ip2 = (EditText)findViewById(R.id.ip_2);
        et_ip3 = (EditText)findViewById(R.id.ip_3);
        et_ip4 = (EditText)findViewById(R.id.ip_4);
        et_port = (EditText)findViewById(R.id.port);

        if(!isIPBlocOk(et_ip1.getText().toString())){
            isConfigOk = false;
            msg_usr = Toast.makeText(this.getApplicationContext(),"Le 1er bloc IP comporte une erreur",Toast.LENGTH_LONG);
            msg_usr.show();
        }

        if(!isIPBlocOk(et_ip2.getText().toString())){
            isConfigOk = false;
            msg_usr = Toast.makeText(this.getApplicationContext(),"Le 2ème bloc IP comporte une erreur",Toast.LENGTH_LONG);
            msg_usr.show();
        }

        if(!isIPBlocOk(et_ip3.getText().toString())){
            isConfigOk = false;
            msg_usr = Toast.makeText(this.getApplicationContext(),"Le 3ème bloc IP comporte une erreur",Toast.LENGTH_LONG);
            msg_usr.show();
        }

        if(!isIPBlocOk(et_ip4.getText().toString())){
            isConfigOk = false;
            msg_usr = Toast.makeText(this.getApplicationContext(),"Le 4ème bloc IP comporte une erreur",Toast.LENGTH_LONG);
            msg_usr.show();
        }

        if(isConfigOk){
            ip_address = et_ip1.getText().toString().trim() + "." + et_ip2.getText().toString().trim() + "." + et_ip3.getText().toString().trim() + "." + et_ip4.getText().toString().trim();
            System.out.println(ip_address);
        }

        if(!isIPPortOk(et_port.getText().toString())){
            isConfigOk = false;
            msg_usr = Toast.makeText(this.getApplicationContext(),"Le port comporte une erreur",Toast.LENGTH_LONG);
            msg_usr.show();
        } else {
            port = Integer.parseInt(et_port.getText().toString());
        }

        if(isConfigOk){
            /*
            Intent iotControlActivity = new Intent(this,classname.class);
            iotControlActivity.putExtra(IP_ADDR,ip_address);
            iotControlActivity.putExtra(PORT,port);
            startActivity(iotControlActivity);
            */
            msg_usr = Toast.makeText(this.getApplicationContext(),"Lancement de l'activité de contrôle",Toast.LENGTH_LONG);
            msg_usr.show();
        }
    }
}