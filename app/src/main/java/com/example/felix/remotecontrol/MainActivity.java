package com.example.felix.remotecontrol;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    Context context;
    ConsumerIrManager mCIR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void send_codes_app (View view){

        mCIR = (ConsumerIrManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
        if (!mCIR.hasIrEmitter()) {
            Log.e(TAG, "No IR Emitter found!\n");
            CharSequence text = "Kein IREmitter gefunden";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
        int [] Array;
        Array = new int[150];
        //TODO: Was sind die richtigen Codes?
        int protocol = 0x222;
        String pro = Integer.toString(protocol);
        for(int i  =0; i<pro.length(); i++){
            int j=i+1;
        Array[i]=Integer.parseInt(pro.substring(i,j));
        }
        mCIR.transmit(20000000,Array);


    }

}
