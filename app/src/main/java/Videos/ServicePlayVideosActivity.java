package Videos;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.android2.kampuskannekt.R;

public class ServicePlayVideosActivity extends AppCompatActivity {


    VideoView playVideoView;
    private int position = 0;

    private ProgressDialog progressDialog;

    private  String VIDEO_URL;
    private MediaController mediaControls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_play_videos);


        VIDEO_URL = getIntent().getExtras().getString("Video_URL");


        if (mediaControls == null) {

            mediaControls = new MediaController(ServicePlayVideosActivity.this);

        }



        //initialize the VideoView

        playVideoView=(VideoView)findViewById(R.id.playVideoView);



        // create a progress bar while the video file is loading

        progressDialog = new ProgressDialog(ServicePlayVideosActivity.this);

        // set a title for the progress bar

        progressDialog.setTitle("New Videos");

        // set a message for the progress bar

        progressDialog.setMessage("Loading...");

        //set the progress bar not cancelable on users' touch

        progressDialog.setCancelable(false);

        // show the progress bar

        progressDialog.show();



        try {

            //set the media controller in the VideoView

            playVideoView.setMediaController(mediaControls);



            //set the uri of the video to be played

            playVideoView.setVideoURI(Uri.parse(VIDEO_URL));



        } catch (Exception e) {

            Log.e("Error", e.getMessage());

            e.printStackTrace();

        }



        playVideoView.requestFocus();

        //we also set an setOnPreparedListener in order to know when the video file is ready for playback

        playVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {



            public void onPrepared(MediaPlayer mediaPlayer) {

                // close the progress bar and play the video

                progressDialog.dismiss();

                //if we have a position on savedInstanceState, the video playback should start from here

                playVideoView.seekTo(position);

                if (position == 0) {

                    playVideoView.start();

                } else {

                    //if we come from a resumed activity, video playback will be paused

                    playVideoView.pause();

                }

            }

        });



    }


    @Override

    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        //we use onSaveInstanceState in order to store the video playback position for orientation change

        savedInstanceState.putInt("Position", playVideoView.getCurrentPosition());

        playVideoView.pause();

    }




    @Override

    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        //we use onRestoreInstanceState in order to play the video playback from the stored position

        position = savedInstanceState.getInt("Position");

        playVideoView.seekTo(position);

    }


}



