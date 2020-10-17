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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import siakad.sma1.bg.adapter.AdapterTugasSiswa;
import siakad.sma1.bg.app.AppController;
import siakad.sma1.bg.model.DataModel;

public class DisplayTugasActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    Button btn_logout;
    TextView txt_id, txt_username;
    String idg, namag;
    SharedPreferences sharedpreferences;

    ProgressDialog pDialog;
    List<DataModel> listData = new ArrayList<DataModel>();
    AdapterTugasSiswa adapter;
    ListView list_view;

    String tag_json_obj = "json_obj_req";
    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";

    public static final String url_data = Server.URL + "tugas_siswa.php";
    private static final String TAG = ManajementTugasSiswaActivity.class.getSimpleName();
    String nis,nama,kelas,jurusan,username,nip,matpel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tugas);

        idg = getIntent().getStringExtra(Server.TAG_ID);
        nip = getIntent().getStringExtra(Server.TAG_NIP);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        matpel = getIntent().getStringExtra(Server.TAG_MATPEL);

        list_view = (ListView) findViewById(R.id.listView);
        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);

        txt_id.setText("NIP : " + nip);
        txt_username.setText("NAMA : " + nama);


        adapter = new AdapterTugasSiswa(DisplayTugasActivity.this, listData);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(this);

        callData(nip);
    }

    private void callData(final String nip) {
        listData.clear();
        adapter.notifyDataSetChanged();


        pDialog = new ProgressDialog(DisplayTugasActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_data, new Response.Listener<String>() {

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
                            data.setNip(obj.getString(Server.TAG_NIP));
                            data.setKategori(obj.getString(Server.TAG_KATEGORI));
                            data.setJudul(obj.getString(Server.TAG_JUDUL));
                            data.setDeskripsi(obj.getString(Server.TAG_DESKRIPSI));
                            data.setFile(obj.getString(Server.TAG_FILE));
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
                params.put("nip", nip);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ManajementDisplayListSiswaActivity.class);

        String kategori = ((TextView)view.findViewById(R.id.txt_kategori)).getText().toString();
        String judul = ((TextView)view.findViewById(R.id.txt_judul)).getText().toString();
        String deskripsi = ((TextView)view.findViewById(R.id.txt_deskripsi)).getText().toString();
        String file = ((TextView)view.findViewById(R.id.txt_file)).getText().toString();

        intent.putExtra(Server.TAG_ID,idg);
        intent.putExtra(Server.TAG_NIP, nip);

        intent.putExtra("kategori", kategori);
        intent.putExtra("judul", judul);
        intent.putExtra("deskripsi", deskripsi);
        intent.putExtra("file", file);

        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_MATPEL, matpel);

        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayTugasActivity.this, PertemuanTugasActivity.class);
        intent.putExtra(Server.TAG_ID, idg);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIP, nip);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_MATPEL, matpel);
        finish();
        startActivity(intent);
    }
}
