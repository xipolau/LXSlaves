package com.slavery.anabivirtual.servidao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

public class OldLisbonActivity extends Activity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("ENTROU", "ASDA");
        //CharSequence text = "ENTROU!";
        //int duration = Toast.LENGTH_SHORT;
        //Context context = getApplicationContext();
        //Toast.makeText(context, text, duration).show();



        super.onCreate(savedInstanceState);
        //Image
        setContentView(com.slavery.anabivirtual.servidao.R.layout.old_lisbon);

        //Sound
        Context context = getApplicationContext();
        mediaPlayer = MediaPlayer.create(context, com.slavery.anabivirtual.servidao.R.raw.rui1);
        mediaPlayer.start();
   }

    // This example shows an Activity, but you would use the same approach if
    // you were subclassing a View.
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                text = "Action was DOWN";
                Toast.makeText(context, text, duration).show();
                Log.d("OnTouchEvent: ", "Action was DOWN");

                return true;
            case (MotionEvent.ACTION_MOVE):
                text = "Action was MOVE";
                Toast.makeText(context, text, duration).show();
                Log.d("OnTouchEvent: ", "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                text = "Action was UP";
                Toast.makeText(context, text, duration).show();
                Log.d("OnTouchEvent: ", "Action was UP");
                activateHomeScreen();
                return true;
            case (MotionEvent.ACTION_CANCEL):
                text = "Action was CANCEL";
                Toast.makeText(context, text, duration).show();
                Log.d("OnTouchEvent: ", "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                text = "Action was OUTSIDE";
                Toast.makeText(context, text, duration).show();
                Log.d("OnTouchEvent: ", "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    void activateHomeScreen()
    {        Intent intent = new Intent(OldLisbonActivity.this,
            MapsActivity.class);
        mediaPlayer.stop();
        startActivity(intent);
    }
}//eo class
