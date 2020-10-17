package siakad.sma1.bg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WaliActivity extends AppCompatActivity {

    Button btn_logout;
    TextView txt_id, txt_username;
    static String id, username,nis_anak,level,nama;
    SharedPreferences sharedpreferences;

    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wali);

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        level = getIntent().getStringExtra(Server.TAG_LEVEL);
        nis_anak = getIntent().getStringExtra(Server.TAG_NIS_ANAK);



        txt_id.setText("NIS ANAK : " + nis_anak);
        txt_username.setText("USERNAME : " + username);

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(Server.TAG_ID, null);
                editor.putString(Server.TAG_USERNAME, null);
                editor.putString(Server.TAG_LEVEL, null);
                editor.putString(Server.TAG_NIS_ANAK, null);
                editor.commit();

                Intent intent = new Intent(WaliActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        switch (nis_anak) {
            case "null":
                Intent intent = new Intent(WaliActivity.this, UpdateNisAnakActivity.class);
                intent.putExtra(Server.TAG_ID, id);
                intent.putExtra(Server.TAG_USERNAME, username);
                intent.putExtra(Server.TAG_NIS_ANAK, nis_anak);
                finish();
                startActivity(intent);
                break;
            default:
                //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void pindah(View view) {
        switch (view.getId()) {
            case R.id.btn_labsensi:
                Intent intent = new Intent(WaliActivity.this, DisplayAbsensiWaliActivity.class);
                intent.putExtra(Server.TAG_ID, id);
                intent.putExtra(Server.TAG_USERNAME, username);
                intent.putExtra(Server.TAG_NIS_ANAK, nis_anak);
                finish();
                startActivity(intent);
                break;
            case R.id.btn_lnilai:
                Intent intent2 = new Intent(WaliActivity.this, DasbordNilaiWaliActivity.class);
                intent2.putExtra(Server.TAG_ID, id);
                intent2.putExtra(Server.TAG_USERNAME, username);
                intent2.putExtra(Server.TAG_NIS_ANAK, nis_anak);
                finish();
                startActivity(intent2);
                break;
        }
    }
}