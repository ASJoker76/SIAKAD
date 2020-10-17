package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import siakad.sma1.bg.app.AppController;

public class InsertNilaiActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    private TextView txt_nama,txt_username,txt_nameclass,txt_code;
    int success;
    ConnectivityManager conMgr;
    Intent intent;
    SharedPreferences sharedpreferences;
    Boolean session = false;

    private String url = Server.URL + "insert_nilai.php";

    private static final String TAG = UpdateAbsensiActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    public String nis,nama;


    private EditText editTextNis,txt_nilai;
    private EditText editTextNama;
    private TextView txt_nip;
    private TextView txt_namaguru;
    private TextView txt_matapel;
    private Spinner kethadir;

    private Button buttonUpdate;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_nilai);
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        Intent intent = getIntent();
        //String message = intent.getStringExtra(GuruActivity.EXTRA_MESSAGE);

        //mengambil nilai yang diteruskan dari class mainactivity
        txt_nip = (TextView)findViewById(R.id.txt_Nip);
        txt_namaguru = (TextView)findViewById(R.id.txt_Nama);
        txt_matapel = (TextView)findViewById(R.id.txt_matapel);

        //inisialisasi objek textview
        //textView.setText(message);

        txt_nip.setText(getIntent().getStringExtra("data1"));
        txt_namaguru.setText(getIntent().getStringExtra("data2"));
        txt_matapel.setText(getIntent().getStringExtra("data3"));

        nis = intent.getStringExtra("nis");
        nama = intent.getStringExtra("nama");

        editTextNis = (EditText) findViewById(R.id.editTextNis);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        txt_nilai = (EditText) findViewById(R.id.txt_nilai);
        kethadir = findViewById(R.id.editTextKet);

        buttonUpdate = (Button) findViewById(R.id.buttonAbsen);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        editTextNis.setText(nis);
        editTextNama.setText(nama);

    }

    private String getTanggal() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void updateEmployee(){
        String nis = editTextNis.getText().toString();
        String namasiswa = editTextNama.getText().toString();
        String nip = txt_nip.getText().toString();
        String nilai = txt_nilai.getText().toString();
        String matapel = txt_matapel.getText().toString();
        String kategori = String.valueOf(kethadir.getSelectedItem());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String tgl = dateFormat.format(date);
        String validasi = "null";

        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            checkAbsen(nis,namasiswa,nip,matapel,kategori,nilai);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkAbsen(final String nis, final String namasiswa, final String nip, final String matapel, final String kategori,final String nilai) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Dalam Proses ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Class Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();


                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

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

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis);
                params.put("nama", namasiswa);
                params.put("nip", nip);
                params.put("matapel", matapel);
                params.put("kategori", kategori);
                params.put("nilai", nilai);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    //@Override
    //public void onClick(View view) {
    //if(view == buttonUpdate){
    //updateEmployee();
    //}
    //}

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void btn_absen(View view) {
        updateEmployee();
    }
}

