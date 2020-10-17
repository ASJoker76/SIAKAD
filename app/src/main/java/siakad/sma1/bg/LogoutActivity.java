package siakad.sma1.bg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LogoutActivity extends AppCompatActivity {

    Button btn_logout;
    TextView txt_id, txt_username,TextView1;
    String id, username, nis, nama, kelas, jurusan,nis_anak;
    SharedPreferences sharedpreferences;


    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        btn_logout = (Button) findViewById(R.id.btn_logout);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        nis = getIntent().getStringExtra(Server.TAG_NIS);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        kelas = getIntent().getStringExtra(Server.TAG_KELAS);
        jurusan = getIntent().getStringExtra(Server.TAG_JURUSAN);
//        nis_anak = getIntent().getStringExtra(Server.TAG_NIS_ANAK);


        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(Server.TAG_ID, null);
                editor.putString(Server.TAG_USERNAME, null);
                editor.putString(Server.TAG_NAMA, null);
                editor.putString(Server.TAG_LEVEL, null);
                editor.putString(Server.TAG_NIS, null);
                editor.putString(Server.TAG_NIP, null);
                editor.putString(Server.TAG_KELAS, null);
                editor.putString(Server.TAG_JURUSAN, null);
                editor.putString(Server.TAG_NIS_ANAK, null);
                editor.commit();

                Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
