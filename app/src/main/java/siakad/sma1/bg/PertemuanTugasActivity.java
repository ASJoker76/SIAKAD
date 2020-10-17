package siakad.sma1.bg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PertemuanTugasActivity extends AppCompatActivity{


    TextView txt_username,txt_nama,txt_atas;
    public static String idg,username,nip,matpel,nama,pertemuan;
    ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertemuan_tugas);

        txt_atas = (TextView) findViewById(R.id.txt_atas);

        Intent intent = getIntent();
        idg = intent.getStringExtra(Server.TAG_ID);
        username = intent.getStringExtra(Server.TAG_USERNAME);
        nip = intent.getStringExtra(Server.TAG_NIP);
        nama = intent.getStringExtra(Server.TAG_NAMA);
        matpel = intent.getStringExtra(Server.TAG_MATPEL);

        txt_atas.setText(" "+matpel+" "+nama);

    }


    public void lihat(View view) {
        Intent intent = new Intent(this, DisplayTugasActivity.class);
        intent.putExtra(Server.TAG_ID,idg);
        intent.putExtra(Server.TAG_NIP,nip);
        intent.putExtra(Server.TAG_NAMA,nama);
        intent.putExtra(Server.TAG_USERNAME,username);
        intent.putExtra(Server.TAG_MATPEL,matpel);
        //intent.putExtra(Server.TAG_PERTEMUAN, pertemuan);
        finish();
        startActivity(intent);
    }

    public void create(View view) {
        Intent intent = new Intent(this, ManajementTugasActivity.class);
        intent.putExtra(Server.TAG_ID,idg);
        intent.putExtra(Server.TAG_NIP,nip);
        intent.putExtra(Server.TAG_NAMA,nama);
        intent.putExtra(Server.TAG_USERNAME,username);
        intent.putExtra(Server.TAG_MATPEL,matpel);
        //intent.putExtra(Server.TAG_PERTEMUAN, pertemuan);
        finish();
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PertemuanTugasActivity.this, GuruActivity.class);
        intent.putExtra(Server.TAG_ID, idg);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIP, nip);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_MATPEL, matpel);
        finish();
        startActivity(intent);
    }

    public void nilai(View view) {
        Intent intent3 = new Intent(PertemuanTugasActivity.this, DasbordNilaiGuruActivity.class);
        intent3.putExtra(Server.TAG_ID, idg);
        intent3.putExtra(Server.TAG_USERNAME, username);
        intent3.putExtra(Server.TAG_NIP, nip);
        intent3.putExtra(Server.TAG_NAMA, nama);
        intent3.putExtra(Server.TAG_MATPEL, matpel);
        finish();
        startActivity(intent3);
    }
}
