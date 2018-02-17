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

            int codes = return_codes(button_pressed);
                int[] Array;
                Array = new int[200];

                //TODO: Umwandlung von binären Codes in int Array
            Array[0] = (9000);
            Array[1] = (4500);




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

    public int return_codes (int button_pressed){
        String int_code="";
        switch(button_pressed){
            case 0:
                //So richtig?
                //On
                int_code = String.valueOf(0xFF827D);
                break;
            case 1:
                //Off
                int_code = String.valueOf(0xFF02FD);
                break;
            default:
                CharSequence text3 = "Kein Code dazu hinterlegt";
                int duration = Toast.LENGTH_SHORT;
                Toast toast3 = Toast.makeText(getApplicationContext(), text3, duration);
                toast3.show();

        }

        if(!int_code.isEmpty()) {
            String bin_string = hexToBin(int_code);
            int code_bin = Integer.parseInt(bin_string);

        }
        String bin_string = hexToBin(int_code);
        int code_bin = Integer.parseInt(bin_string);
        return code_bin;
    }

    private String hexToBin(String hex){
        String bin = "";
        String binFragment = "";
        int iHex;
        hex = hex.trim();
        hex = hex.replaceFirst("0x", "");

        for(int i = 0; i < hex.length(); i++){
            iHex = Integer.parseInt(""+hex.charAt(i),16);
            binFragment = Integer.toBinaryString(iHex);

            while(binFragment.length() < 4){
                binFragment = "0" + binFragment;
            }
            bin += binFragment;
        }
        return bin;
    }
}
