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


public class LoginActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_register, btn_login;
    EditText txt_username, txt_password;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "login.php";

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, username, level, verifikasi, nis, nip,nama,matpel,kelas,jurusan,nis_anak;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);

        // Cek session login jika TRUE maka langsung buka SiswaActivity
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(Server.TAG_ID, null);
        username = sharedpreferences.getString(Server.TAG_USERNAME, null);
        nama = sharedpreferences.getString(Server.TAG_NAMA, null);
        verifikasi = sharedpreferences.getString(Server.TAG_VERIFIKASI, null);
        level = sharedpreferences.getString(Server.TAG_LEVEL, null);
        nis = sharedpreferences.getString(Server.TAG_NIS, null);
        nip = sharedpreferences.getString(Server.TAG_NIP, null);
        matpel = sharedpreferences.getString(Server.TAG_MATPEL, null);
        kelas = sharedpreferences.getString(Server.TAG_KELAS, null);
        jurusan = sharedpreferences.getString(Server.TAG_JURUSAN, null);
        nis_anak = sharedpreferences.getString(Server.TAG_NIS_ANAK, null);

        if (session) {
            String x = level;
            switch (x) {

                case "Siswa":
                    // Memanggil main activity
                    Intent intent = new Intent(LoginActivity.this, SiswaActivity.class);
                    intent.putExtra(Server.TAG_ID, id);
                    intent.putExtra(Server.TAG_NAMA, nama);
                    intent.putExtra(Server.TAG_USERNAME, username);
                    intent.putExtra(Server.TAG_LEVEL, level);
                    intent.putExtra(Server.TAG_NIS, nis);
                    intent.putExtra(Server.TAG_KELAS, kelas);
                    intent.putExtra(Server.TAG_JURUSAN, jurusan);
                    finish();
                    startActivity(intent);
                    break;
                case "Guru":
                    // Memanggil main activity
                    Intent intent2 = new Intent(LoginActivity.this, GuruActivity.class);
                    intent2.putExtra(Server.TAG_ID, id);
                    intent2.putExtra(Server.TAG_NAMA, nama);
                    intent2.putExtra(Server.TAG_USERNAME, username);
                    intent2.putExtra(Server.TAG_LEVEL, level);
                    intent2.putExtra(Server.TAG_NIP, nip);
                    intent2.putExtra(Server.TAG_MATPEL, matpel);
                    finish();
                    startActivity(intent2);
                    break;
                case "Orang Tua Murid":
                    // Memanggil main activity
                    Intent intent3 = new Intent(LoginActivity.this, WaliActivity.class);
                    intent3.putExtra(Server.TAG_ID, id);
                    intent3.putExtra(Server.TAG_USERNAME, username);
                    intent3.putExtra(Server.TAG_LEVEL, level);
                    intent3.putExtra(Server.TAG_NIS_ANAK, nis_anak);
                    finish();
                    startActivity(intent3);
                    break;
                case "Developer":
                    // Memanggil main activity
                    Intent intent4 = new Intent(LoginActivity.this, DeveloperActivity.class);
                    intent4.putExtra("idd", id);
                    intent4.putExtra("usernamed", username);
                    intent4.putExtra(Server.TAG_LEVEL, level);
                    finish();
                    startActivity(intent4);
                    break;
                default:
                    //Toast.makeText(getApplicationContext(), level, Toast.LENGTH_LONG).show();
            }
        }


        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                // mengecek kolom yang kosong
                if (username.trim().length() > 0 && password.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(username, password);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    private void checkLogin(final String username, final String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        String nis_anak = jObj.getString(Server.TAG_NIS_ANAK);
                        String jurusan = jObj.getString(Server.TAG_JURUSAN);
                        String kelas = jObj.getString(Server.TAG_KELAS);
                        String nip = jObj.getString(Server.TAG_NIP);
                        String nis = jObj.getString(Server.TAG_NIS);
                        String matpel = jObj.getString(Server.TAG_MATPEL);
                        String verifikasi = jObj.getString(Server.TAG_VERIFIKASI);
                        String level = jObj.getString(Server.TAG_LEVEL);
                        String nama = jObj.getString(Server.TAG_NAMA);
                        String username = jObj.getString(Server.TAG_USERNAME);
                        String id = jObj.getString(Server.TAG_ID);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(Server.TAG_ID, id);
                        editor.putString(Server.TAG_USERNAME, username);
                        editor.putString(Server.TAG_NAMA, nama);
                        editor.putString(Server.TAG_LEVEL, level);
                        editor.putString(Server.TAG_VERIFIKASI, verifikasi);
                        editor.putString(Server.TAG_MATPEL, matpel);
                        editor.putString(Server.TAG_NIS, nis);
                        editor.putString(Server.TAG_NIP, nip);
                        editor.putString(Server.TAG_KELAS, kelas);
                        editor.putString(Server.TAG_JURUSAN, jurusan);
                        editor.putString(Server.TAG_NIS_ANAK, nis_anak);
                        editor.commit();




                        switch (verifikasi) {
                            case "Aktif":
                                //Toast.makeText(getApplicationContext(), "Aktif", Toast.LENGTH_LONG).show();
                                switch (level) {
                                    case "Siswa":
                                        // Memanggil main activity
                                        Intent intent = new Intent(LoginActivity.this, SiswaActivity.class);
                                        intent.putExtra(Server.TAG_ID, id);
                                        intent.putExtra(Server.TAG_NAMA, nama);
                                        intent.putExtra(Server.TAG_USERNAME, username);
                                        intent.putExtra(Server.TAG_LEVEL, level);
                                        intent.putExtra(Server.TAG_NIS, nis);
                                        intent.putExtra(Server.TAG_KELAS, kelas);
                                        intent.putExtra(Server.TAG_JURUSAN, jurusan);
                                        finish();
                                        startActivity(intent);
                                        break;
                                    case "Guru":
                                        // Memanggil main activity
                                        Intent intent2 = new Intent(LoginActivity.this, GuruActivity.class);
                                        intent2.putExtra(Server.TAG_ID, id);
                                        intent2.putExtra(Server.TAG_NAMA, nama);
                                        intent2.putExtra(Server.TAG_USERNAME, username);
                                        intent2.putExtra(Server.TAG_LEVEL, level);
                                        intent2.putExtra(Server.TAG_NIP, nip);
                                        intent2.putExtra(Server.TAG_MATPEL, matpel);
                                        finish();
                                        startActivity(intent2);
                                        break;
                                    case "Orang Tua Murid":
                                        // Memanggil main activity
                                        Intent intent3 = new Intent(LoginActivity.this, WaliActivity.class);
                                        intent3.putExtra(Server.TAG_ID, id);
                                        intent3.putExtra(Server.TAG_USERNAME, username);
                                        intent3.putExtra(Server.TAG_LEVEL, level);
                                        intent3.putExtra(Server.TAG_NIS_ANAK, nis_anak);
                                        finish();
                                        startActivity(intent3);
                                        break;
                                    case "Developer":
                                        // Memanggil main activity
                                        Intent intent4 = new Intent(LoginActivity.this, DeveloperActivity.class);
                                        intent4.putExtra("idd", id);
                                        intent4.putExtra("usernamed", username);
                                        intent4.putExtra(Server.TAG_LEVEL, level);
                                        finish();
                                        startActivity(intent4);
                                        break;
                                    default:
                                        //Toast.makeText(getApplicationContext(), level, Toast.LENGTH_LONG).show();
                                        break;
                                }
                                break;
                            case "Pasif":
                                Toast.makeText(getApplicationContext(), "Pasif", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Belum terverifikasi", Toast.LENGTH_LONG).show();
                                break;
                        }
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
                params.put("password", password);

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