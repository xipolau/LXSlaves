package com.slavery.anabivirtual.servidao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import java.io.IOException;

public class StoryActivity extends Activity {

    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initialStage = true;
    private static int length=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

               Log.d("ENTROU", "StoryActiviy");
                super.onCreate(savedInstanceState);
        //Image
        setContentView(com.slavery.anabivirtual.servidao.R.layout.activity_story);

        //Sound
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);




        new Player().execute("https://www.ssaurel.com/tmp/mymusic.mp3");

        if(length!=0 )
            resume();


          /*      if (!playPause) {
                    if (initialStage) {
                        new Player().execute("https://www.ssaurel.com/tmp/mymusic.mp3");
                    } else {
                        if (!mediaPlayer.isPlaying())
                           resume();
                    }
                    playPause = true;
                } else {
                    if (mediaPlayer.isPlaying()) {
                        pause();
                    }
                    playPause = false;
                }
                */
    }

    public void pause(){
        mediaPlayer.pause();
        length=mediaPlayer.getCurrentPosition();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
        //    mediaPlayer.reset();
        //    mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        length=mediaPlayer.getCurrentPosition();
        if (mediaPlayer != null) mediaPlayer.release();
    }

    public void resume(){

        if (mediaPlayer != null){
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
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
    {        Intent intent = new Intent(StoryActivity.this,
            MapsActivity.class);
        mediaPlayer.stop();
        startActivity(intent);
    }



//Inner class-----------------------------------------------------------------------------
    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;

                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e("StoryActivity", e.getMessage());
                prepared = false;
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Buffering...");
            progressDialog.show();
        }

    }

//--------------------------------------------------------------------------

}//eo class


