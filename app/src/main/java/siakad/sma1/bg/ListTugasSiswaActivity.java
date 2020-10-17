package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import siakad.sma1.bg.adapter.AdapterDataGuru;
import siakad.sma1.bg.app.AppController;
import siakad.sma1.bg.model.DataModel;

public class ListTugasSiswaActivity extends AppCompatActivity implements ListView.OnItemClickListener{

        Button btn_logout;
        TextView txt_id, txt_username;
        String idg, namag;
        SharedPreferences sharedpreferences;

        ProgressDialog pDialog;
        List<DataModel> listData = new ArrayList<DataModel>();
        AdapterDataGuru adapter;
        ListView list_view;

        public static final String url_data = Server.URL + "data_guru.php";
        private static final String TAG = ListGuruActivity.class.getSimpleName();
        String ids, nis,nama,kelas,jurusan,username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tugas_siswa);
        ids = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        nis = getIntent().getStringExtra(Server.TAG_NIS);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        kelas = getIntent().getStringExtra(Server.TAG_KELAS);
        jurusan = getIntent().getStringExtra(Server.TAG_JURUSAN);

        list_view = (ListView) findViewById(R.id.listView);
        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);

        txt_id.setText("NIS : " + nis);
        txt_username.setText("NAMA : " + nama);

        adapter = new AdapterDataGuru(ListTugasSiswaActivity.this, listData);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(this);

        callData();
    }

    private void callData() {
        listData.clear();
        adapter.notifyDataSetChanged();


        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url_data, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataModel item = new DataModel();

                        item.setId(obj.getString(Server.TAG_ID));
                        item.setNama(obj.getString(Server.TAG_NAMA));
                        item.setNip(obj.getString(Server.TAG_NIP));
                        item.setMatpel(obj.getString(Server.TAG_MATPEL));
                        listData.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(ListTugasSiswaActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ManajementTugasSiswaActivity.class);

        String idg = ((TextView)view.findViewById(R.id.txt_id)).getText().toString();
        String namag = ((TextView)view.findViewById(R.id.txt_nama)).getText().toString();
        String nip = ((TextView)view.findViewById(R.id.txt_nip)).getText().toString();
        String matpel = ((TextView)view.findViewById(R.id.txt_matpel)).getText().toString();

        intent.putExtra("idg", idg);
        intent.putExtra("namag", namag);
        intent.putExtra("nip", nip);
        intent.putExtra("matpel", matpel);

        intent.putExtra(Server.TAG_ID, ids);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_NIS, nis);

        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListTugasSiswaActivity.this, SiswaActivity.class);
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
