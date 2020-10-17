package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import siakad.sma1.bg.adapter.AdapterTugasSiswaLast;
import siakad.sma1.bg.app.AppController;
import siakad.sma1.bg.model.DataModel;

public class ManajementDisplayListSiswaActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    SharedPreferences sharedpreferences;

    ProgressDialog pDialog;
    List<DataModel> listData = new ArrayList<DataModel>();
    AdapterTugasSiswaLast adapter;
    ListView list_view;

    String tag_json_obj = "json_obj_req";
    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";

    public static final String url_data = Server.URL + "tugas_upload_siswa.php";
    private static final String TAG = ManajementDisplayListSiswaActivity.class.getSimpleName();

    String idg,nis,nama,kelas,jurusan,username,nip,matpel,kategori,judul,deskripsi,file;
    TextView txt_id,txt_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajement_display_list_siswa);

        idg = getIntent().getStringExtra(Server.TAG_ID);
        nip = getIntent().getStringExtra(Server.TAG_NIP);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        matpel = getIntent().getStringExtra(Server.TAG_MATPEL);

        kategori = getIntent().getStringExtra("kategori");
        judul = getIntent().getStringExtra("judul");

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);

        txt_id.setText("NIP : " + nip);
        txt_username.setText("NAMA : " + nama);

        list_view = (ListView) findViewById(R.id.listView);

        adapter = new AdapterTugasSiswaLast(ManajementDisplayListSiswaActivity.this, listData);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(this);

        callData(nip,judul,kategori);
    }

    private void callData(final String nip,final String judul,final String kategori) {
        listData.clear();
        adapter.notifyDataSetChanged();


        pDialog = new ProgressDialog(ManajementDisplayListSiswaActivity.this);
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
                            data.setNis(obj.getString(Server.TAG_NIS));
                            data.setNama(obj.getString(Server.TAG_NAMA));
                            data.setFile(obj.getString(Server.TAG_FILE));
                            data.setKesimpulan(obj.getString(Server.TAG_KESIMPULAN));
                            data.setNilai(obj.getString(Server.TAG_NILAI));
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
                params.put("judul", judul);
                params.put("kategori", kategori);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ManajementDisplayUpdateSiswaActivity.class);

        String judul = ((TextView)view.findViewById(R.id.txt_judul)).getText().toString();
        String kategori = ((TextView)view.findViewById(R.id.txt_kategori)).getText().toString();
        String nis = ((TextView)view.findViewById(R.id.txt_nis)).getText().toString();
        String namas = ((TextView)view.findViewById(R.id.txt_nama)).getText().toString();
        String file = ((TextView)view.findViewById(R.id.txt_file)).getText().toString();
        String kesimpulan = ((TextView)view.findViewById(R.id.txt_kesimpulan)).getText().toString();
        String nilai = ((TextView)view.findViewById(R.id.txt_nilai)).getText().toString();

        intent.putExtra(Server.TAG_ID,idg);
        intent.putExtra(Server.TAG_NIP, nip);

        intent.putExtra("judul", judul);
        intent.putExtra("kategori", kategori);
        intent.putExtra("nis", nis);
        intent.putExtra("namas", namas);
        intent.putExtra("file", file);
        intent.putExtra("kesimpulan", kesimpulan);
        intent.putExtra("nilai", nilai);

        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_MATPEL, matpel);

        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManajementDisplayListSiswaActivity.this, DisplayTugasActivity.class);
        intent.putExtra(Server.TAG_ID, idg);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIP, nip);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_MATPEL, matpel);
        finish();
        startActivity(intent);
    }
}
