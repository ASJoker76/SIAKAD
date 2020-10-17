package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import siakad.sma1.bg.app.AppController;

public class ManajementDisplayUpdateSiswaActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    String idg,nis,nama,kelas,jurusan,username,nip,matpel,kategori,judul,namas,file,kesimpulan,nilai;
    TextView txt_judul,txt_deskripsi,txt_nip,txt_kategori,txt_file,txt_nis,txt_nama_siswa,txt_tugas,txt_path,txt_jkesimpulan,txt_kesimpulan,txt_nilai;

    int success;
    private String url = Server.URL + "update_tugas_siswa.php";

    private static final String TAG = ManajementDisplayUpdateSiswaActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajement_display_update_siswa);

        idg = getIntent().getStringExtra(Server.TAG_ID);
        nip = getIntent().getStringExtra(Server.TAG_NIP);

        judul = getIntent().getStringExtra("judul");
        kategori = getIntent().getStringExtra("kategori");
        nis = getIntent().getStringExtra("nis");
        namas = getIntent().getStringExtra("namas");
        file = getIntent().getStringExtra("file");
        kesimpulan = getIntent().getStringExtra("kesimpulan");
        nilai = getIntent().getStringExtra("nilai");

        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        matpel = getIntent().getStringExtra(Server.TAG_MATPEL);

        txt_nip = (TextView) findViewById(R.id.txt_nip);
        txt_judul = (TextView) findViewById(R.id.txt_judul);
        txt_kategori = (TextView) findViewById(R.id.txt_kategori);
        txt_file = (TextView) findViewById(R.id.txt_file);
        txt_kesimpulan = (TextView) findViewById(R.id.txt_kesimpulan);
        txt_nilai = (EditText) findViewById(R.id.txt_nilai);

        txt_nis = (TextView) findViewById(R.id.txt_nis);
        txt_nama_siswa = (TextView) findViewById(R.id.txt_nama_siswa);

        txt_nip.setText(nip);
        txt_judul.setText(judul);
        txt_kategori.setText(kategori);
        txt_nis.setText(nis);
        txt_nama_siswa.setText(namas);
        txt_file.setText(file);
        txt_kesimpulan.setText(kesimpulan);
        txt_nilai.setText(nilai);


    }

    public void buka(View view) {
        Intent intent3 = new Intent(ManajementDisplayUpdateSiswaActivity.this, DisplayDokumentSiswaActivity.class);
        intent3.putExtra("file", txt_file.getText().toString());
        //finish();
        startActivity(intent3);
    }

    public void updatetugas(View view) {
        String nilai2 = txt_nilai.getText().toString();

        upload(judul,kategori,nip,nis,namas,matpel,nilai2);
    }

    private void upload(final String judul,final String kategori,final String nip,final String nis,final String nama,final String matpel,final String nilai2) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Update Nilai ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Update Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Update!", jObj.toString());

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
                params.put("nip", nip);
                params.put("judul", judul);
                params.put("kategori", kategori);
                params.put("nis", nis);
                params.put("nama", nama);
                params.put("matpel", matpel);
                params.put("nilai", nilai2);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManajementDisplayUpdateSiswaActivity.this, ManajementDisplayListSiswaActivity.class);
        intent.putExtra(Server.TAG_ID, idg);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIP, nip);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_MATPEL, matpel);
        intent.putExtra("kategori",kategori);
        intent.putExtra("judul",judul);

        finish();
        startActivity(intent);
    }
}
