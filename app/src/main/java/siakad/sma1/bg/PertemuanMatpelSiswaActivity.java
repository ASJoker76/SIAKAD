package siakad.sma1.bg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PertemuanMatpelSiswaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    TextView txt_username,txt_nama,txt_atas;
    public static String idg,namag,nip,matpel,nama,pertemuan,nis;
    String ids,kelas,jurusan,username;
    ArrayAdapter<String> adapter;
    String e[] = {"Minggu 1","Minggu 2","Minggu 3","Minggu 4","Minggu 5","Minggu 6","Minggu 7","Minggu 8"
            ,"Minggu 9","Minggu 10","Minggu 11","Minggu 12","Minggu 13","Minggu 14","Minggu 15","Minggu 16","Minggu 17","Minggu 18"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertemuan_matpel_siswa);

        listView = (ListView) findViewById(R.id.list_view);
        txt_atas = (TextView) findViewById(R.id.txt_atas);

        adapter  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, e);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        Intent intent = getIntent();

        idg = intent.getStringExtra("idg");
        namag = intent.getStringExtra("namag");
        nip = intent.getStringExtra("nip");
        matpel = intent.getStringExtra("matpel");

        ids = intent.getStringExtra(Server.TAG_ID);
        username = intent.getStringExtra(Server.TAG_USERNAME);
        nis = intent.getStringExtra(Server.TAG_NIS);
        nama = intent.getStringExtra(Server.TAG_NAMA);
        kelas = intent.getStringExtra(Server.TAG_KELAS);
        jurusan = intent.getStringExtra(Server.TAG_JURUSAN);




        txt_atas.setText(" "+matpel+" "+namag);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, adapter.getItem(position), Toast.LENGTH_SHORT).show();

        pertemuan = e[position];
        Intent intent = new Intent(this, ManajementModulActivity.class);

        intent.putExtra("idg", idg);
        intent.putExtra("namag", namag);
        intent.putExtra("nip", nip);
        intent.putExtra("matpel", matpel);

        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_NIS, nis);
        intent.putExtra(Server.TAG_PERTEMUAN, pertemuan);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PertemuanMatpelSiswaActivity.this, ListGuruActivity.class);
        intent.putExtra(Server.TAG_ID, ids);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIS, nis);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_KELAS, kelas);
        intent.putExtra(Server.TAG_JURUSAN, jurusan);
        finish();
        startActivity(intent);
    }
}
