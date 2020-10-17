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

public class UpdateNipActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    Spinner txt_matpel;
    Button btn_update;
    private TextView txt_username,txt_nip, txt_id, txt_nama;
    String selectedItem;
    ConnectivityManager conMgr;

    ProgressDialog pDialog;
    int success;

    private String url = Server.URL + "update_nip.php";

    private static final String TAG = UpdateNipActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nip);

        btn_update = findViewById(R.id.btn_update);

        txt_nip = findViewById(R.id.txt_nip);
        txt_matpel = findViewById(R.id.txt_matpel);
        txt_nama = findViewById(R.id.txt_nama);

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
                getResources().getStringArray(R.array.matpel)
        );

        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);

        txt_matpel.setAdapter(adapter);

        txt_matpel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedItem = txt_matpel.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void btn_update_nip(View view) {
        String username = txt_username.getText().toString();
        String nama = txt_nama.getText().toString();
        String nip = txt_nip.getText().toString();
        String matpel = String.valueOf(txt_matpel.getSelectedItem());

        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            checkNis(username,nama,nip,matpel);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkNis(final String username,final String nama,final String nip,final String matpel) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Update Nip ...");
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

                        Intent intent = new Intent(UpdateNipActivity.this, LogoutActivity.class);
                        intent.putExtra(Server.TAG_ID, txt_id.getText().toString());
                        intent.putExtra(Server.TAG_NAMA, txt_nama.getText().toString());
                        intent.putExtra(Server.TAG_USERNAME, txt_username.getText().toString());
                        //intent.putExtra(Server.TAG_NIP, txt_nip.getText().toString());
                        //intent.putExtra(Server.TAG_MATPEL, String.valueOf(txt_matpel.getSelectedItem()));
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
                params.put("nip", nip);
                params.put("matpel", matpel);
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
