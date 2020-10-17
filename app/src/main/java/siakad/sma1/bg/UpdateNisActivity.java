package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import siakad.sma1.bg.app.AppController;

public class UpdateNisActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    Spinner txt_kelas,txt_jurusan;
    Button btn_update;
    private TextView txt_username,txt_nis, txt_id, txt_nama;
    String selectedItem;
    ConnectivityManager conMgr;

    ProgressDialog pDialog;
    int success;

    private String url = Server.URL + "update_nis.php";

    private static final String TAG = UpdateNisActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nis);


        btn_update = findViewById(R.id.btn_update);
        txt_nis = findViewById(R.id.txt_nis);
        txt_nama = findViewById(R.id.txt_nama);
        txt_kelas = findViewById(R.id.txt_kelas);
        txt_jurusan = findViewById(R.id.txt_jurusan);
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

        txt_id = (TextView)findViewById(R.id.txt_id);
        txt_username = (TextView)findViewById(R.id.txt_username);

        txt_id.setText(getIntent().getStringExtra("id"));
        txt_username.setText(getIntent().getStringExtra("username"));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner_blue,
                getResources().getStringArray(R.array.kelas)
        );
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner_blue,
                getResources().getStringArray(R.array.jurusan)
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        adapter2.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        txt_kelas.setAdapter(adapter);
        txt_jurusan.setAdapter(adapter2);
        txt_kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedItem = txt_kelas.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        txt_jurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedItem = txt_jurusan.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void btn_update_nis(View view) {
        String username = txt_username.getText().toString();
        String nama = txt_nama.getText().toString();
        String nis = txt_nis.getText().toString();
        String kelas = String.valueOf(txt_kelas.getSelectedItem());
        String jurusan = String.valueOf(txt_jurusan.getSelectedItem());

        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            checkNis(username,nama,nis,kelas,jurusan);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkNis(final String username,final String nama,final String nis,final String kelas,final String jurusan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Update Nis ...");
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

                        Log.e("Successfully!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(UpdateNisActivity.this, LogoutActivity.class);
                        intent.putExtra(Server.TAG_ID, txt_id.getText().toString());
                        intent.putExtra(Server.TAG_NAMA, txt_nama.getText().toString());
                        intent.putExtra(Server.TAG_USERNAME, txt_username.getText().toString());
                        //intent.putExtra(Server.TAG_NIS, txt_nis.getText().toString());
                        finish();
                        startActivity(intent);
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
                params.put("username", username);
                params.put("nama", nama);
                params.put("nis", nis);
                params.put("kelas", kelas);
                params.put("jurusan", jurusan);
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
}
