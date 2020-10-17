package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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

import siakad.sma1.bg.adapter.AdapterNilaiSiswa;
import siakad.sma1.bg.app.AppController;
import siakad.sma1.bg.model.DataModel;

public class DisplayNilaiGuruActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    List<DataModel> listData = new ArrayList<DataModel>();
    AdapterNilaiSiswa adapter;

    ListView list_view;
    String id, nip,nama,matpel,username;
    public static final String url_cari = Server.URL + "cari_nilai_siswa.php";

    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";
    private static final String TAG_SUCCESS = "success";

    private static final String TAG = DisplayNilaiGuruActivity.class.getSimpleName();

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_nilai_guru);
        list_view = (ListView) findViewById(R.id.listView);

        id = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        nip = getIntent().getStringExtra(Server.TAG_NIP);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        matpel = getIntent().getStringExtra(Server.TAG_MATPEL);

        adapter = new AdapterNilaiSiswa(DisplayNilaiGuruActivity.this, listData);
        list_view.setAdapter(adapter);

        cariData(matpel);
    }

    private void cariData(final String keyword) {
        adapter.notifyDataSetChanged();
        pDialog = new ProgressDialog(DisplayNilaiGuruActivity.this);
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
                            data.setKategori(obj.getString(Server.TAG_KATEGORI));
                            data.setNis(obj.getString(Server.TAG_NIS));
                            data.setNama(obj.getString(Server.TAG_NAMA));
                            data.setKelas(obj.getString(Server.TAG_KELAS));
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
                params.put("keyword", keyword);
                //params.put("keyword2", keyword2);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    public void btnurut(View view) {
    }

    public void btnrenew(View view) {
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayNilaiGuruActivity.this, GuruActivity.class);
        intent.putExtra(Server.TAG_ID, id);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIP, nip);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_MATPEL, matpel);
        finish();
        startActivity(intent);
    }
}
