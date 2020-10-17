package siakad.sma1.bg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuruActivity extends AppCompatActivity {

    Button btn_logout;
    TextView txt_id, txt_username,TextView1,txt_matpel;
    String id, username, nip,nama,matpel;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);
        TextView1 = (TextView) findViewById(R.id.TextView1);
        txt_matpel = (TextView) findViewById(R.id.txt_matpel);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        nip = getIntent().getStringExtra(Server.TAG_NIP);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        matpel = getIntent().getStringExtra(Server.TAG_MATPEL);

        TextView1.setText("GURU = " + nama);
        txt_id.setText("NIP : " + nip);
        txt_matpel.setText(matpel);
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
                editor.putString(Server.TAG_NAMA, null);
                editor.putString(Server.TAG_LEVEL, null);
                editor.putString(Server.TAG_NIS, null);
                editor.putString(Server.TAG_NIP, null);
                editor.putString(Server.TAG_MATPEL, null);
                editor.commit();

                Intent intent = new Intent(GuruActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        switch (nip) {
            case "null":
                Intent intent = new Intent(GuruActivity.this, UpdateNipActivity.class);
                intent.putExtra(Server.TAG_ID, id);
                intent.putExtra(Server.TAG_USERNAME, username);
                intent.putExtra(Server.TAG_NIP, nip);
                finish();
                startActivity(intent);
                break;
            default:
                //Toast.makeText(getApplicationContext(), nip, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void pindah(View view) {
        switch (view.getId()) {
            case R.id.btn_labsensi:
                Intent intent = new Intent(GuruActivity.this, InsertAbsensiActivity.class);
                intent.putExtra(Server.TAG_ID, id);
                intent.putExtra(Server.TAG_USERNAME, username);
                intent.putExtra(Server.TAG_NIP, nip);
                intent.putExtra(Server.TAG_NAMA, nama);
                intent.putExtra(Server.TAG_MATPEL, matpel);
                finish();
                startActivity(intent);
                break;
            case R.id.btn_lmatpel:
                Intent intent2 = new Intent(GuruActivity.this, PertemuanMatpelActivity.class);
                intent2.putExtra(Server.TAG_ID, id);
                intent2.putExtra(Server.TAG_USERNAME, username);
                intent2.putExtra(Server.TAG_NIP, nip);
                intent2.putExtra(Server.TAG_NAMA, nama);
                intent2.putExtra(Server.TAG_MATPEL, matpel);
                finish();
                startActivity(intent2);
                break;
            case R.id.btn_lnilai:
                Intent intent3 = new Intent(GuruActivity.this, DisplayNilaiGuruActivity.class);
                intent3.putExtra(Server.TAG_ID, id);
                intent3.putExtra(Server.TAG_USERNAME, username);
                intent3.putExtra(Server.TAG_NIP, nip);
                intent3.putExtra(Server.TAG_NAMA, nama);
                intent3.putExtra(Server.TAG_MATPEL, matpel);
                finish();
                startActivity(intent3);
                break;
            case R.id.btn_ltugas:
                Intent intent4 = new Intent(GuruActivity.this, PertemuanTugasActivity.class);
                intent4.putExtra(Server.TAG_ID, id);
                intent4.putExtra(Server.TAG_USERNAME, username);
                intent4.putExtra(Server.TAG_NIP, nip);
                intent4.putExtra(Server.TAG_NAMA, nama);
                intent4.putExtra(Server.TAG_MATPEL, matpel);
                finish();
                startActivity(intent4);
                break;
        }
    }
}