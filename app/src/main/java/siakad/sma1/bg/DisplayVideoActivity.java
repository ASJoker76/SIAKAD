package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayVideoActivity extends AppCompatActivity {

    public static String file;
    ProgressDialog pDialog;
    VideoView videoView;
    MediaController mediaController;
    Uri video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_video);

        videoView = (VideoView) findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));
        file = getIntent().getStringExtra("file");
        videoStream(file);

    }

    private void videoStream(String videonew) {
        // membuat progressbar
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Buffering ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        try {
            // Memulai MediaController
            mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            // Video URL
            video = Uri.parse(Server.URL_UPLOAD + videonew);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Menutup pDialog dan play video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoView.start();
            }
        });
    }
}
