package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

import siakad.sma1.bg.adapter.AdapterNilaiLisan;
import siakad.sma1.bg.adapter.AdapterNilaiPenugasan;
import siakad.sma1.bg.adapter.AdapterNilaiTulisan;
import siakad.sma1.bg.app.AppController;
import siakad.sma1.bg.model.DataModel;

public class DisplayPPengetahuanActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    List<DataModel> listData = new ArrayList<DataModel>();
    List<DataModel> listData2 = new ArrayList<DataModel>();
    List<DataModel> listData3 = new ArrayList<DataModel>();
    AdapterNilaiLisan adapter;
    AdapterNilaiTulisan adapter2;
    AdapterNilaiPenugasan adapter3;

    String id, nis,nama,kelas,jurusan,username;
    TextView txt_id, txt_nis, txt_nama,txt_kelas,txt_jurusan;
    ListView list_view,list_view2,list_view3;
    Spinner txt_matpel;
    String selectedItem;

    public static final String url_cari = Server.URL + "penilaian_lisan.php";
    public static final String url_cari2 = Server.URL + "penilaian_tulisan.php";
    public static final String url_cari3 = Server.URL + "penilaian_penugasan.php";

    public static final String url_update = Server.URL + "penilaian_update_lisan.php";
    public static final String url_update2 = Server.URL + "penilaian_update_tulisan.php";
    public static final String url_update3 = Server.URL + "penilaian_update_penugasan.php";

    private static final String TAG = DisplayPPengetahuanActivity.class.getSimpleName();

    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";
    private static final String TAG_SUCCESS = "success";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_p_pengetahuan);

        id = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        nis = getIntent().getStringExtra(Server.TAG_NIS);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        kelas = getIntent().getStringExtra(Server.TAG_KELAS);
        jurusan = getIntent().getStringExtra(Server.TAG_JURUSAN);

        txt_nis = (TextView) findViewById(R.id.txt_nis);
        txt_nama = (TextView) findViewById(R.id.txt_nama);
        txt_kelas = (TextView) findViewById(R.id.txt_kelas);
        txt_jurusan = (TextView) findViewById(R.id.txt_jurusan);
        txt_matpel = (Spinner) findViewById(R.id.txt_matpel);

        txt_nis.setText(nis);
        txt_nama.setText(nama);
        txt_kelas.setText(kelas);
        txt_jurusan.setText(jurusan);

        list_view = (ListView) findViewById(R.id.listView);
        list_view2 = (ListView) findViewById(R.id.listView2);
        list_view3 = (ListView) findViewById(R.id.listView3);

        adapter = new AdapterNilaiLisan(DisplayPPengetahuanActivity.this, listData);
        adapter2 = new AdapterNilaiTulisan(DisplayPPengetahuanActivity.this, listData2);
        adapter3 = new AdapterNilaiPenugasan(DisplayPPengetahuanActivity.this, listData3);

        list_view.setAdapter(adapter);
        list_view2.setAdapter(adapter2);
        list_view3.setAdapter(adapter3);

        loadnilailisan(nis);
        loadnilaitulisan(nis);
        loadnilaipenulisan(nis);

        ArrayAdapter<String> adapter9 = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner_polos,
                getResources().getStringArray(R.array.matpel)


        );

        adapter9.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        txt_matpel.setAdapter(adapter9);

        txt_matpel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedItem = txt_matpel.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_LONG).show();
                ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.DEFAULT_BOLD);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void loadnilailisan(final String nis) {
        adapter.notifyDataSetChanged();

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
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void loadnilaitulisan(final String nis) {
        adapter2.notifyDataSetChanged();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_cari2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        listData2.clear();
                        adapter2.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            DataModel data = new DataModel();
                            data.setNilai(obj.getString(Server.TAG_NILAI));
                            listData2.add(data);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
                adapter2.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void loadnilaipenulisan(final String nis) {
        adapter3.notifyDataSetChanged();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_cari3, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        listData3.clear();
                        adapter3.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            DataModel data = new DataModel();
                            data.setNilai(obj.getString(Server.TAG_NILAI));
                            listData3.add(data);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
                adapter3.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void loadnilailisan2(final String nis,final String matpel) {
        listData.clear();
        adapter.notifyDataSetChanged();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {

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
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);
                params.put("matpel", matpel);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void loadnilaitulisan2(final String nis,final String matpel) {
        listData2.clear();
        adapter2.notifyDataSetChanged();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_update2, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        listData2.clear();
                        adapter2.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            DataModel data = new DataModel();
                            data.setNilai(obj.getString(Server.TAG_NILAI));
                            listData2.add(data);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
                adapter2.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);
                params.put("matpel", matpel);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void loadnilaipenulisan2(final String nis,final String matpel) {
        listData3.clear();
        adapter3.notifyDataSetChanged();

        StringRequest strReq = new StringRequest(Request.Method.POST, url_update3, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);

                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        listData3.clear();
                        adapter3.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            DataModel data = new DataModel();
                            data.setNilai(obj.getString(Server.TAG_NILAI));
                            listData3.add(data);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
                adapter3.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);
                params.put("matpel", matpel);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    /*
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayPPengetahuanActivity.this, DasbordNilaiSiswaActivity.class);
        intent.putExtra(Server.TAG_ID, id);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIS, nis);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_KELAS, kelas);
        intent.putExtra(Server.TAG_JURUSAN, jurusan);
        finish();
        startActivity(intent);
    }*/

    public void btnurut(View view) {
        final String matpel2 = txt_matpel.getSelectedItem().toString();

        loadnilailisan2(nis,matpel2);
        loadnilaitulisan2(nis,matpel2);
        loadnilaipenulisan2(nis,matpel2);
    }

    public void btnrenew(View view) {
        loadnilailisan(nis);
        loadnilaitulisan(nis);
        loadnilaipenulisan(nis);
    }
}
