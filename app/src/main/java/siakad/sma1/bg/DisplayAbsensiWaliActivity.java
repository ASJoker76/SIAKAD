package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import siakad.sma1.bg.adapter.AdapterAbsensiSiswa;
import siakad.sma1.bg.app.AppController;
import siakad.sma1.bg.model.DataModel;


public class DisplayAbsensiWaliActivity extends AppCompatActivity{
    ProgressDialog pDialog;
    List<DataModel> listData = new ArrayList<DataModel>();
    AdapterAbsensiSiswa adapter;

    ListView list_view;
    Intent intent;
    int success;

    public static final String url_cari = Server.URL + "cari_absen.php";
    public static final String url_hitung = Server.URL + "hitung_absen.php";
    public static final String url_alpa = Server.URL + "hitung_alpa.php";
    public static final String url_sakit = Server.URL + "hitung_sakit.php";
    public static final String url_izin = Server.URL + "hitung_izin.php";
    private static final String TAG = DisplayAbsensiActivity.class.getSimpleName();

    Button btn_logout;
    TextView txt_id, txt_nis, txt_nama,txt_kelas,txt_jurusan,txt_hadir,txt_alpa,txt_sakit,txt_izin;
    String id, nis,nama,kelas,jurusan,username;

    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";
    private static final String TAG_SUCCESS = "success";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_absensi_wali);

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_nis = (TextView) findViewById(R.id.txt_nis);
        txt_nama = (TextView) findViewById(R.id.txt_nama);
        txt_kelas = (TextView) findViewById(R.id.txt_kelas);
        txt_jurusan = (TextView) findViewById(R.id.txt_jurusan);
        //btn_logout = (Button) findViewById(R.id.btn_logout);

        txt_hadir = (TextView) findViewById(R.id.txt_hadir);
        txt_alpa = (TextView) findViewById(R.id.txt_alpa);
        txt_sakit = (TextView) findViewById(R.id.txt_sakit);
        txt_izin = (TextView) findViewById(R.id.txt_izin);

        id = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        nis = getIntent().getStringExtra(Server.TAG_NIS_ANAK);

        txt_id.setText("ID ="+id);
        txt_nis.setText(nis);
        //txt_nama.setText(nama);
        //txt_kelas.setText(kelas);
        //txt_jurusan.setText(jurusan);

        list_view = (ListView) findViewById(R.id.listView);

        adapter = new AdapterAbsensiSiswa(DisplayAbsensiWaliActivity.this, listData);
        list_view.setAdapter(adapter);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String tgl = formatter.format(date);
        cariData(nis);
        hitungabsen(nis);
        hitungalpa(nis);
        hitungsakit(nis);
        hitungizin(nis);
    }

    private void hitungizin(final String nis) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_izin, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        String izin = jObj.getString(Server.TAG_IZIN);
                        txt_izin.setText(izin);
                    }
                    else {

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void hitungsakit(final String nis) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_sakit, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        String sakit = jObj.getString(Server.TAG_SAKIT);
                        txt_sakit.setText(sakit);
                    }
                    else {

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void hitungalpa(final String nis) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_alpa, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        String alpa = jObj.getString(Server.TAG_ALPHA);
                        txt_alpa.setText(alpa);
                    }
                    else {

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void hitungabsen(final String nis) {
        StringRequest strReq = new StringRequest(Request.Method.POST, url_hitung, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        String jumlah = jObj.getString(Server.TAG_HADIR);
                        txt_hadir.setText(jumlah);
                    }
                    else {

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void cariData(final String keyword) {
        adapter.notifyDataSetChanged();
        pDialog = new ProgressDialog(DisplayAbsensiWaliActivity.this);
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
                            data.setNis(obj.getString(Server.TAG_NIS));
                            data.setNama_siswa(obj.getString(Server.TAG_NAMA_SISWA));
                            data.setNip(obj.getString(Server.TAG_NIP));
                            data.setNama_guru(obj.getString(Server.TAG_NAMA_GURU));
                            data.setMatapel(obj.getString(Server.TAG_MATAPEL));
                            data.setTgl(obj.getString(Server.TAG_TGL));
                            data.setKetabsen(obj.getString(Server.TAG_KETABSEN));
                            data.setValidasi(obj.getString(Server.TAG_VALIDASI));
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayAbsensiWaliActivity.this, WaliActivity.class);
        intent.putExtra(Server.TAG_ID, id);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIS_ANAK, nis);
        finish();
        startActivity(intent);
    }
}
