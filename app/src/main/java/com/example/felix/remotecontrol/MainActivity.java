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

import java.math.BigInteger;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    Context context;
    ConsumerIrManager mCIR;

    TextView get_frequenz;
    EditText set_frequenz;

    TextView get_code;
    EditText set_code;

    int frequenz=0;
    String code ="";
    int button_pressed =0;

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
            button_pressed = Integer.parseInt(st_code);



        mCIR = (ConsumerIrManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
        if (!mCIR.hasIrEmitter()) {
            Log.e(TAG, "No IR Emitter found!\n");
            CharSequence text = "Kein IR-Emitter gefunden";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }else if(mCIR.hasIrEmitter()){

            String code = return_codes(button_pressed);

            int[] Array;
            Array = new int[100];
            int arry=2;

            //Startimpuls
            Array[0] = (9000);
            Array[1] = (4500);


            if(!code.isEmpty()&& code!=null) {
                String address = code.substring(0, 8);
                String address_inv = code.substring(8, 16);

                String message = code.substring(16, 24);
                String message_inv = code.substring(24);


                for (int i = 8; i > 0; i--) {
                    String help = address.substring(i - 1, i);
                    int help_int = Integer.parseInt(help);

                    if (help_int == 0) {
                        Array[arry] = (563);
                        arry += 1;
                        Array[arry] = (562);
                    } else if (help_int == 1) {
                        Array[arry] = (563);
                        arry += 1;
                        Array[arry] = (1687);
                    } else {
                        Log.e(TAG, "Invalid Binary Value");
                    }
                    arry += 1;
                }

                for (int i = 8; i > 0; i--) {
                    String help = address_inv.substring(i - 1, i);
                    int help_int = Integer.parseInt(help);

                    if (help_int == 0) {
                        Array[arry] = (563);
                        arry += 1;
                        Array[arry] = (562);
                    } else if (help_int == 1) {
                        Array[arry] = (563);
                        arry += 1;
                        Array[arry] = (1687);
                    } else {
                        Log.e(TAG, "Invalid Binary Value");
                    }
                    arry += 1;
                }

                for (int i = 8; i > 0; i--) {
                    String help = message.substring(i - 1, i);
                    int help_int = Integer.parseInt(help);

                    if (help_int == 0) {
                        Array[arry] = (563);
                        arry++;
                        Array[arry] = (562);
                    } else if (help_int == 1) {
                        Array[arry] = (563);
                        arry++;
                        Array[arry] = (1687);
                    } else {
                        Log.e(TAG, "Invalid Binary Value");
                    }
                    arry++;
                }

                for (int i = 8; i > 0; i--) {
                    String help = message_inv.substring(i - 1, i);
                    int help_int = Integer.parseInt(help);

                    if (help_int == 0) {
                        Array[arry] = (563);
                        arry++;
                        Array[arry] = (562);
                    } else if (help_int == 1) {
                        Array[arry] = (563);
                        arry++;
                        Array[arry] = (1687);
                    } else {
                        Log.e(TAG, "Invalid Binary Value");
                    }
                    arry++;
                }

                Array[arry] = (563);

                mCIR.transmit(frequenz, Array);


            }

            }else{
                CharSequence text3 = "Kein Code dazu hinterlegt";
                int duration = Toast.LENGTH_SHORT;
                Toast toast3 = Toast.makeText(getApplicationContext(), text3, duration);
                toast3.show();
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

    public String return_codes (int button_pressed){
        String int_code="";

        switch(button_pressed){
            case 0:
                //So richtig?
                //On
                int_code = Integer.toBinaryString(0xff827d);
                break;
            case 1:
                //Off
                int_code = Integer.toBinaryString(0xFF02FD);
                break;
            default:
                CharSequence text3 = "Kein Code dazu hinterlegt";
                int duration = Toast.LENGTH_SHORT;
                Toast toast3 = Toast.makeText(getApplicationContext(), text3, duration);
                toast3.show();
                break;



        }

        if(!int_code.isEmpty()&&int_code!=null) {
            //Führende Nullen addieren:
            int_code = "00000000" + int_code.substring(0);
            int show_log = int_code.length();
            Log.i(TAG, "Bitzahl Code:" + Integer.toString(show_log));


        }else{
            CharSequence text3 = "Kein Code dazu hinterlegt";
            int duration = Toast.LENGTH_SHORT;
            Toast toast3 = Toast.makeText(getApplicationContext(), text3, duration);
            toast3.show();
        }

        return int_code;
    }

}
