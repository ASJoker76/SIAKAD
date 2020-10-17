package siakad.sma1.bg;

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

public class ManajementModulActivity extends AppCompatActivity {

    private static final String TAG = ManajementModulActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    int success;

    String tag_json_obj = "json_obj_req";

    private String UPLOAD_URL3 = Server.URL + "load_file.php";

    public static String idg,nama,namag, nip,nis, pertemuan,matpel;
    private EditText txt_foto,txt_video,txt_word,txt_pdf;
    TextView txt_nip,txt_nama,txt_matpel,txt_pertemuan,txt_nis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajement_modul);

        txt_foto = (EditText) findViewById(R.id.txt_foto);
        txt_video = (EditText) findViewById(R.id.txt_video);
        txt_word = (EditText) findViewById(R.id.txt_word);
        txt_pdf = (EditText) findViewById(R.id.txt_pdf);



        idg = getIntent().getStringExtra("idg");
        namag = getIntent().getStringExtra("namag");
        nip = getIntent().getStringExtra("nip");
        matpel = getIntent().getStringExtra("matpel");

        nama = getIntent().getStringExtra(Server.TAG_NAMA);
        nis = getIntent().getStringExtra(Server.TAG_NIS);
        pertemuan = getIntent().getStringExtra(Server.TAG_PERTEMUAN);

        txt_nis = (TextView) findViewById(R.id.txt_nis);
        txt_nip = (TextView) findViewById(R.id.txt_nip);
        txt_nama = (TextView) findViewById(R.id.txt_nama);
        txt_matpel = (TextView) findViewById(R.id.txt_matpel);
        txt_pertemuan = (TextView) findViewById(R.id.txt_pertemuan);

        //txt_nis.setText(nis);
        txt_nip.setText(nip);
        txt_nama.setText(nama);
        txt_matpel.setText(matpel);
        txt_pertemuan.setText(pertemuan);

        caridata(nip,matpel,pertemuan);
    }

    private void caridata(final String nip,final String matpel,final String pertemuan) {
        StringRequest strReq = new StringRequest(Request.Method.POST, UPLOAD_URL3, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        String gambar5 = jObj.getString("gambar");
                        String video5 = jObj.getString("video");
                        String word5 = jObj.getString("word");
                        String pdf5 = jObj.getString("pdf");

                        txt_foto.setText(gambar5);
                        txt_video.setText(video5);
                        txt_word.setText(word5);
                        txt_pdf.setText(pdf5);
                        Log.e("Successfully Login!", jObj.toString());
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
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

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nip", nip);
                params.put("matpel", matpel);
                params.put("pertemuan", pertemuan);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

    }

    public void pindah(View view) {
        switch (view.getId()) {
            case R.id.btn_foto:
                Intent intent = new Intent(ManajementModulActivity.this, DisplayFotoActivity.class);
                intent.putExtra("file", txt_foto.getText().toString());
                //finish();
                startActivity(intent);
                break;
            case R.id.btn_docx:
                Intent intent2 = new Intent(ManajementModulActivity.this, DisplayDokumentActivity.class);
                intent2.putExtra("file", txt_word.getText().toString());
                //finish();
                startActivity(intent2);
                break;
            case R.id.btn_pdf:
                Intent intent3 = new Intent(ManajementModulActivity.this, DisplayDokumentActivity.class);
                intent3.putExtra("file", txt_pdf.getText().toString());
                //finish();
                startActivity(intent3);
                break;
            case R.id.btn_video:
                Intent intent4 = new Intent(ManajementModulActivity.this, DisplayVideoActivity.class);
                intent4.putExtra("file", txt_video.getText().toString());
                //finish();
                startActivity(intent4);
                break;
        }
    }
}
