package siakad.sma1.bg;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DisplayFotoActivity extends AppCompatActivity {

    private ImageView imageView;
    public static String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_foto);

        imageView = (ImageView) findViewById(R.id.imageView);
        photo = getIntent().getStringExtra("file");
        Picasso.with(this).load(Server.URL_UPLOAD + photo).into(imageView);
    }
}
