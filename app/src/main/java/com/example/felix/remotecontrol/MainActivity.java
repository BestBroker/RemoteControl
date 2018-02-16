package com.example.felix.remotecontrol;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    Context context;
    ConsumerIrManager mCIR;

    TextView get_frequenz;
    EditText set_frequenz;

    TextView get_code;
    EditText set_code;

    int frequenz=0;
    int code=0;

    Button senden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();

       set_frequenz = findViewById(R.id.eingabe_frequenz);
       set_code = findViewById(R.id.eingabe_code);

       senden = findViewById(R.id.senden);
       senden.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.KITKAT)
           @Override
           public void onClick(View view) {
               send_codes_app();
           }
       });

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public void send_codes_app (){

        String st_frequenz = set_frequenz.getText().toString();
        if(st_frequenz!= null && !st_frequenz.isEmpty()){
            frequenz = Integer.parseInt(st_frequenz);


        String st_code = set_code.getText().toString();
        if(st_code!= null && !st_code.isEmpty()){
            frequenz = Integer.parseInt(st_frequenz);



        mCIR = (ConsumerIrManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
        if (!mCIR.hasIrEmitter()) {
            Log.e(TAG, "No IR Emitter found!\n");
            CharSequence text = "Kein IR-Emitter gefunden";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }else if(mCIR.hasIrEmitter()){

                int d = st_code.length();
                int[] Array = new int[d];

                for (int i = 0; i < st_code.length(); i++) {
                    Array[i] = Integer.parseInt(st_code.substring(i));
                }
                mCIR.transmit(frequenz, Array);
            }

        }else{
            CharSequence text2 = "Code eingeben";
            int duration = Toast.LENGTH_SHORT;
            Toast toast2 = Toast.makeText(getApplicationContext(), text2, duration);
            toast2.show();

        }

    }else {
            CharSequence text1 = "Frequenz festlegen";
            int duration = Toast.LENGTH_SHORT;
            Toast toast1 = Toast.makeText(getApplicationContext(), text1, duration);
            toast1.show();
        }

    }


}
