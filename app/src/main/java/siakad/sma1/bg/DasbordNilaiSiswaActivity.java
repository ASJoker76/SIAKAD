package siakad.sma1.bg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DasbordNilaiSiswaActivity extends AppCompatActivity {

    String id, nis,nama,kelas,jurusan,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasbord_nilai);

        id = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        nis = getIntent().getStringExtra(Server.TAG_NIS);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        kelas = getIntent().getStringExtra(Server.TAG_KELAS);
        jurusan = getIntent().getStringExtra(Server.TAG_JURUSAN);
    }

    public void ppengetahuan(View view) {
        Intent intent3 = new Intent(DasbordNilaiSiswaActivity.this, DisplayPPengetahuanActivity.class);
        intent3.putExtra(Server.TAG_ID, id);
        intent3.putExtra(Server.TAG_USERNAME, username);
        intent3.putExtra(Server.TAG_NIS, nis);
        intent3.putExtra(Server.TAG_NAMA, nama);
        intent3.putExtra(Server.TAG_KELAS, kelas);
        intent3.putExtra(Server.TAG_JURUSAN, jurusan);
        //finish();
        startActivity(intent3);
    }

    public void pketerampilan(View view) {
        Intent intent3 = new Intent(DasbordNilaiSiswaActivity.this, DisplayPKeterampilanActivity.class);
        intent3.putExtra(Server.TAG_ID, id);
        intent3.putExtra(Server.TAG_USERNAME, username);
        intent3.putExtra(Server.TAG_NIS, nis);
        intent3.putExtra(Server.TAG_NAMA, nama);
        intent3.putExtra(Server.TAG_KELAS, kelas);
        intent3.putExtra(Server.TAG_JURUSAN, jurusan);
        //finish();
        startActivity(intent3);
    }

    public void psikap(View view) {
        Intent intent3 = new Intent(DasbordNilaiSiswaActivity.this, DisplayPSikapActivity.class);
        intent3.putExtra(Server.TAG_ID, id);
        intent3.putExtra(Server.TAG_USERNAME, username);
        intent3.putExtra(Server.TAG_NIS, nis);
        intent3.putExtra(Server.TAG_NAMA, nama);
        intent3.putExtra(Server.TAG_KELAS, kelas);
        intent3.putExtra(Server.TAG_JURUSAN, jurusan);
        //finish();
        startActivity(intent3);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DasbordNilaiSiswaActivity.this, SiswaActivity.class);
        intent.putExtra(Server.TAG_ID, id);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIS, nis);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_KELAS, kelas);
        intent.putExtra(Server.TAG_JURUSAN, jurusan);
        finish();
        startActivity(intent);
    }
}
