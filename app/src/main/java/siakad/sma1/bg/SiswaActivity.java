package siakad.sma1.bg;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static siakad.sma1.bg.ManajementTugasActivity.notifikasi;

public class SiswaActivity extends AppCompatActivity {

    Button btn_logout;
    TextView txt_id, txt_username,TextView1;
    String id, username, nis, nama, kelas, jurusan;
    SharedPreferences sharedpreferences;


    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siswa);

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);
        TextView1 = (TextView) findViewById(R.id.TextView1);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        nis = getIntent().getStringExtra(Server.TAG_NIS);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        kelas = getIntent().getStringExtra(Server.TAG_KELAS);
        jurusan = getIntent().getStringExtra(Server.TAG_JURUSAN);

        TextView1.setText("SISWA = " + nama);
        txt_id.setText("NIS : " + nis);
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
                editor.putString(Server.TAG_KELAS, null);
                editor.putString(Server.TAG_JURUSAN, null);
                editor.commit();

                Intent intent = new Intent(SiswaActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        switch (nis) {
            case "null":
                Intent intent = new Intent(SiswaActivity.this, UpdateNisActivity.class);
                intent.putExtra(Server.TAG_ID, id);
                intent.putExtra(Server.TAG_USERNAME, username);
                intent.putExtra(Server.TAG_NIS, nis);
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
                Intent intent = new Intent(SiswaActivity.this, DisplayAbsensiActivity.class);
                intent.putExtra(Server.TAG_ID, id);
                intent.putExtra(Server.TAG_USERNAME, username);
                intent.putExtra(Server.TAG_NIS, nis);
                intent.putExtra(Server.TAG_NAMA, nama);
                intent.putExtra(Server.TAG_KELAS, kelas);
                intent.putExtra(Server.TAG_JURUSAN, jurusan);
                finish();
                startActivity(intent);
                break;
            case R.id.btn_lmatpel:
                Intent intent2 = new Intent(SiswaActivity.this, ListGuruActivity.class);
                intent2.putExtra(Server.TAG_ID, id);
                intent2.putExtra(Server.TAG_USERNAME, username);
                intent2.putExtra(Server.TAG_NIS, nis);
                intent2.putExtra(Server.TAG_NAMA, nama);
                intent2.putExtra(Server.TAG_KELAS, kelas);
                intent2.putExtra(Server.TAG_JURUSAN, jurusan);
                finish();
                startActivity(intent2);
                break;
            case R.id.btn_lnilai:
                Intent intent3 = new Intent(SiswaActivity.this, DasbordNilaiSiswaActivity.class);
                intent3.putExtra(Server.TAG_ID, id);
                intent3.putExtra(Server.TAG_USERNAME, username);
                intent3.putExtra(Server.TAG_NIS, nis);
                intent3.putExtra(Server.TAG_NAMA, nama);
                intent3.putExtra(Server.TAG_KELAS, kelas);
                intent3.putExtra(Server.TAG_JURUSAN, jurusan);
                finish();
                startActivity(intent3);
                break;
            case R.id.btn_ltugas:
                Intent intent4 = new Intent(SiswaActivity.this, ListTugasSiswaActivity.class);
                intent4.putExtra(Server.TAG_ID, id);
                intent4.putExtra(Server.TAG_USERNAME, username);
                intent4.putExtra(Server.TAG_NIS, nis);
                intent4.putExtra(Server.TAG_NAMA, nama);
                intent4.putExtra(Server.TAG_KELAS, kelas);
                intent4.putExtra(Server.TAG_JURUSAN, jurusan);
                finish();
                startActivity(intent4);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void munculkanNotifikasi(String s, String s1, Intent intent) {
        // membuat komponen peding intent
        PendingIntent pendingIntent = PendingIntent.getActivity(SiswaActivity.this
                , notifikasi, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // membuat komponen notifikasi
        Notification.Builder builder = new Notification.Builder(SiswaActivity.this);
        Notification notification;
        notification = builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(s)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(SiswaActivity.this.getResources()
                        , R.mipmap.ic_launcher))
                .setContentText(s1)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) SiswaActivity.this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifikasi, notification);
    }
}