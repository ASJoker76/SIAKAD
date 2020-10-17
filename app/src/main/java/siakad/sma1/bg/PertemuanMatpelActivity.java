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

public class PertemuanMatpelActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    TextView txt_username,txt_nama,txt_atas;
    public static String idg,username,nip,matpel,nama,pertemuan;
    ArrayAdapter<String> adapter;
    String e[] = {"Minggu 1","Minggu 2","Minggu 3","Minggu 4","Minggu 5","Minggu 6","Minggu 7","Minggu 8"
            ,"Minggu 9","Minggu 10","Minggu 11","Minggu 12","Minggu 13","Minggu 14","Minggu 15","Minggu 16","Minggu 17","Minggu 18"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertemuan_matpel);
        listView = (ListView) findViewById(R.id.list_view);
        txt_atas = (TextView) findViewById(R.id.txt_atas);

        adapter  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, e);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        Intent intent = getIntent();
        idg = intent.getStringExtra(Server.TAG_ID);
        username = intent.getStringExtra(Server.TAG_USERNAME);
        nip = intent.getStringExtra(Server.TAG_NIP);
        nama = intent.getStringExtra(Server.TAG_NAMA);
        matpel = intent.getStringExtra(Server.TAG_MATPEL);
        //code = intent.getStringExtra(Server.TAG_CODE);
        txt_atas.setText(" "+matpel+" "+nama);
        //startActivity(intent);
        //txt_username.setText(getIntent().getStringExtra("username_client"));
        //txt_nama.setText(getIntent().getStringExtra("nama"));
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, adapter.getItem(position), Toast.LENGTH_SHORT).show();

        pertemuan = e[position];
        Intent intent = new Intent(this, ManajementMatpelActivity.class);
        intent.putExtra(Server.TAG_ID,idg);
        intent.putExtra(Server.TAG_NIP,nip);
        intent.putExtra(Server.TAG_NAMA,nama);
        intent.putExtra(Server.TAG_USERNAME,username);
        intent.putExtra(Server.TAG_MATPEL,matpel);
        intent.putExtra(Server.TAG_PERTEMUAN, pertemuan);
        //intent.putExtra("username_client", txt_username.getText().toString());
        //intent.putExtra("nama", txt_nama.getText().toString());
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PertemuanMatpelActivity.this, GuruActivity.class);
        intent.putExtra(Server.TAG_ID, idg);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIP, nip);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_MATPEL, matpel);
        finish();
        startActivity(intent);
    }
}
