package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import siakad.sma1.bg.adapter.AdapterDataSiswa;
import siakad.sma1.bg.app.AppController;
import siakad.sma1.bg.model.DataModel;

public class DasbordNilaiGuruActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    ProgressDialog pDialog;
    List<DataModel> listData = new ArrayList<DataModel>();
    AdapterDataSiswa adapter;

    ListView list_view;
    Intent intent;

    public static final String url_data = Server.URL + "data_siswa.php";
    public static final String url_cari = Server.URL + "cari_siswa.php";
    private static final String TAG = InsertAbsensiActivity.class.getSimpleName();

    Button btn_logout;
    TextView txt_id, txt_nip,txt_nama,txt_matapel;
    Spinner cbkelas, cbjurusan;
    String id, nip,nama,matpel,username;
    SharedPreferences sharedpreferences;

    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasbord_nilai_guru);

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_nip = (TextView) findViewById(R.id.txt_nip);
        txt_nama = (TextView) findViewById(R.id.txt_nama);
        txt_matapel = (TextView) findViewById(R.id.txt_matapel);

        cbkelas = (Spinner) findViewById(R.id.cbkelas);
        cbjurusan = (Spinner) findViewById(R.id.cbjurusan);

        btn_logout = (Button) findViewById(R.id.btn_logout);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        nip = getIntent().getStringExtra(Server.TAG_NIP);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        matpel = getIntent().getStringExtra(Server.TAG_MATPEL);

        txt_id.setText(id);
        txt_nip.setText("Nip :" + nip);
        txt_nama.setText(nama);
        txt_matapel.setText(matpel);

        list_view = (ListView) findViewById(R.id.listView);

        adapter = new AdapterDataSiswa(DasbordNilaiGuruActivity.this, listData);
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
                        item.setNis(obj.getString(Server.TAG_NIS));
                        item.setKelas(obj.getString(Server.TAG_KELAS));
                        item.setJurusan(obj.getString(Server.TAG_JURUSAN));
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
                Toast.makeText(DasbordNilaiGuruActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }


    public void btncari(View view) {
        final String kelas = cbkelas.getSelectedItem().toString();
        final String jurusan = cbjurusan.getSelectedItem().toString();
        cariData(kelas,jurusan);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, InsertNilaiActivity.class);

        String nis = ((TextView)view.findViewById(R.id.txt_nis)).getText().toString();
        String nama2 = ((TextView)view.findViewById(R.id.txt_nama)).getText().toString();

        intent.putExtra(Server.TAG_NIS,nis);
        intent.putExtra(Server.TAG_NAMA,nama2);
        intent.putExtra("data1", nip);
        intent.putExtra("data2", nama);
        intent.putExtra("data3", matpel);

        startActivity(intent);
    }

    private void cariData(final String keyword,final String keyword2) {
        listData.clear();
        adapter.notifyDataSetChanged();

        pDialog = new ProgressDialog(DasbordNilaiGuruActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_cari, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        listData.clear();
                        adapter.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            DataModel data = new DataModel();
                            data.setId(obj.getString(Server.TAG_ID));
                            data.setNama(obj.getString(Server.TAG_NAMA));
                            data.setNis(obj.getString(Server.TAG_NIS));
                            data.setKelas(obj.getString(Server.TAG_KELAS));
                            data.setJurusan(obj.getString(Server.TAG_JURUSAN));
                            listData.add(data);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);
                params.put("keyword2", keyword2);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DasbordNilaiGuruActivity.this, GuruActivity.class);
        intent.putExtra(Server.TAG_ID, id);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIP, nip);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_MATPEL, matpel);
        finish();
        startActivity(intent);
    }
}
