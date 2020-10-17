package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import siakad.sma1.bg.adapter.Adapter;
import siakad.sma1.bg.app.AppController;
import siakad.sma1.bg.model.DataModel;


public class DeveloperActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    Button btn_logout;
    TextView txt_id, txt_username;
    String id_, username_;
    SharedPreferences sharedpreferences;

    ProgressDialog pDialog;
    List<DataModel> listData = new ArrayList<DataModel>();
    Adapter adapter;
    ListView list_view;

    public static final String url_data = Server.URL + "verifikasi.php";
    //public static final String url_cari = Server.URL + "Android/cari_siswa.php";
    private static final String TAG = DeveloperActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        id_ = getIntent().getStringExtra("idd");
        username_ = getIntent().getStringExtra("usernamed");


        txt_id.setText("ID : " + id_);
        txt_username.setText("USERNAME : " + username_);

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString("idd", null);
                editor.putString("usernamed", null);
                editor.putString(Server.TAG_ID, null);
                editor.putString(Server.TAG_USERNAME, null);
                editor.putString(Server.TAG_NAMA, null);
                editor.putString(Server.TAG_LEVEL, null);
                editor.putString(Server.TAG_NIS, null);
                editor.putString(Server.TAG_NIP, null);
                editor.commit();

                Intent intent = new Intent(DeveloperActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        list_view = (ListView) findViewById(R.id.listView);

        adapter = new Adapter(DeveloperActivity.this, listData);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(this);

        callData();
    }

    private void callData() {
        listData.clear();
        adapter.notifyDataSetChanged();


        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url_data, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataModel item = new DataModel();

                        item.setId(obj.getString(Server.TAG_ID));
                        item.setUsername(obj.getString(Server.TAG_USERNAME));
                        item.setVerifikasi(obj.getString(Server.TAG_VERIFIKASI));
                        listData.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(DeveloperActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, UpdateVerifikasiActivity.class);
        String ids = ((TextView)view.findViewById(R.id.txt_id)).getText().toString();
        String username = ((TextView)view.findViewById(R.id.txt_username)).getText().toString();
        String verifikasi = ((TextView)view.findViewById(R.id.txt_verifikasi)).getText().toString();

        intent.putExtra("idd", id_);
        intent.putExtra("usernamed", username_);
        intent.putExtra(Server.TAG_USERNAME, username);

        finish();
        startActivity(intent);
    }

    public void gantiwk(View view) {
        
    }
}
