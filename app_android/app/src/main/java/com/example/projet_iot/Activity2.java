
package com.example.projet_iot;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {
    private String ip;
    private int port;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //this.ip = ip;
        //this.port = port;
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

        diffuseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendData(firstText,secondText,thirdText);
            }
        });
    }

    private void changeText(TextView firstText, TextView secondText) {
        CharSequence tampon = firstText.getText();
        firstText.setText(secondText.getText());
        secondText.setText(tampon);
    }

    public void sendData(TextView firstText, TextView secondText, TextView thirdText) {
        //TODO : IMPLEMENT ALGORITHM TO SEND DATA

      String txt1 =  firstText.getText().toString().substring(0,1);
      String txt2 =  secondText.getText().toString().substring(0,1);
      String txt3 =  thirdText.getText().toString().substring(0,1);
        String tabToSend[] = {txt1,txt2,txt3};
        Log.d("Test",txt1 + "\n" + txt2 + "\n" + txt3 );
    }
}
