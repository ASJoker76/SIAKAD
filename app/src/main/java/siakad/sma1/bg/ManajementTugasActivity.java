package siakad.sma1.bg;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import siakad.sma1.bg.app.AppController;

public class ManajementTugasActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    private String upload_pdf = Server.URL + "uploadtugas.php?";
    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;
    String url = "https://www.google.com";
    public static String displayName;
    static public Uri uri;

    private String url2 = Server.URL + "uploadtugasnull.php";

    private static final String TAG = ManajementTugasActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";
    int success;

    public static final int notifikasi = 1;
    TextView txt_nip,txt_judul,txt_nama,txt_deskripsi,txt_path;
    Button btn_sent2,btn_sent;
    Spinner txt_kategori;
    String selectedItem;
    public static String idg,username,nip,matpel,nama,judul,deskripsi,kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajement_tugas);

        txt_judul = findViewById(R.id.txt_judul);
        txt_deskripsi = findViewById(R.id.txt_deskripsi);
        txt_path = findViewById(R.id.txt_path);

        btn_sent2 = findViewById(R.id.btn_sent2);
        btn_sent = findViewById(R.id.btn_sent);

        btn_sent.setVisibility(View.GONE);

        Intent intent = getIntent();
        idg = intent.getStringExtra(Server.TAG_ID);
        username = intent.getStringExtra(Server.TAG_USERNAME);
        nip = intent.getStringExtra(Server.TAG_NIP);
        nama = intent.getStringExtra(Server.TAG_NAMA);
        matpel = intent.getStringExtra(Server.TAG_MATPEL);

        txt_kategori = findViewById(R.id.txt_kategori);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner_blue,
                getResources().getStringArray(R.array.kategory_tugas)
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        txt_kategori.setAdapter(adapter);

        txt_kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedItem = txt_kategori.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_LONG).show();
                ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);
                ((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.DEFAULT_BOLD);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void munculkanNotifikasi(String s, String s1, Intent intent) {
        // membuat komponen peding intent
        PendingIntent pendingIntent = PendingIntent.getActivity(ManajementTugasActivity.this
                , notifikasi, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // membuat komponen notifikasi
        Notification.Builder builder = new Notification.Builder(ManajementTugasActivity.this);
        Notification notification;
        notification = builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(s)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(ManajementTugasActivity.this.getResources()
                        , R.mipmap.ic_launcher))
                .setContentText(s1)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) ManajementTugasActivity.this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifikasi, notification);
    }

    public void openfiles(View view) {
        try {
            final CharSequence[] options = {"PDF Files", "Word Files", "Cancel"};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ManajementTugasActivity.this);
            builder.setTitle("Select Option");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("PDF Files")) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/pdf");
                        startActivityForResult(intent,1);
                    } else if (options[item].equals("Word Files")) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/docx");
                        startActivityForResult(intent,2);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                uri = data.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();
                displayName = null;

                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = this.getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            Log.d("nameeeee>>>>  ", displayName);

                            txt_path.setText(displayName);
                            btn_sent2.setVisibility(View.GONE);
                            btn_sent.setVisibility(View.VISIBLE);
                            //uploadPDF(displayName,uri,nip,pertemuan);
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                    Log.d("nameeeee>>>>  ", displayName);
                }
            }
        }
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                uri = data.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();
                displayName = null;

                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = this.getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            Log.d("nameeeee>>>>  ", displayName);

                            txt_path.setText(displayName);
                            btn_sent2.setVisibility(View.GONE);
                            btn_sent.setVisibility(View.VISIBLE);
                            //uploadPDF(displayName,uri,nip,pertemuan);
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                    Log.d("nameeeee>>>>  ", displayName);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void sent(View view) {
        judul = txt_judul.getText().toString();
        deskripsi = txt_deskripsi.getText().toString();
        kategori = String.valueOf(txt_kategori.getSelectedItem());

        upload(displayName,uri,judul,deskripsi,kategori,nip);
    }

    private void upload(final String pdfname,final Uri pdffile,final String judul,final String deskripsi,final String kategori,final String nip) {
        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_pdf,
                    new Response.Listener<NetworkResponse>() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onResponse(NetworkResponse response) {
                            Log.d("ressssssoo",new String(response.data));
                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                jsonObject.toString().replace("\\\\","");

                                if (jsonObject.getString("status").equals("true")) {
                                    Log.d("come::: >>>  ","yessssss");
                                    arraylist = new ArrayList<HashMap<String, String>>();
                                    JSONArray dataArray = jsonObject.getJSONArray("data");


                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        url = dataobj.optString("pathToFile");
                                        //tv.setText(url);
                                        Intent intent = new Intent(getApplicationContext(), ManajementTugasActivity.class);
                                        // buat method untung menampilkan notifikasi dengan
                                        // mengirimkan data yang dikirim dari widget EditText
                                        munculkanNotifikasi(judul, deskripsi, intent);
                                        kosong();
                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    // params.put("tags", "ccccc");  add string parameters
                    params.put("judul", judul);
                    params.put("deskripsi", deskripsi);
                    params.put("nip", nip);
                    params.put("kategori", kategori);
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("filename", new DataPart(pdfname ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(ManajementTugasActivity.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public void kosong(){
        txt_judul.setText(null);
        txt_deskripsi.setText(null);
        txt_path.setText(null);
        uri = null ;
        displayName = null;
    }

    public void sent2(View view) {
        judul = txt_judul.getText().toString();
        deskripsi = txt_deskripsi.getText().toString();
        kategori = String.valueOf(txt_kategori.getSelectedItem());

        upload2(judul,deskripsi,kategori,nip);
    }

    private void upload2(final String judul,final String deskripsi,final String kategori,final String nip) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Buat Tugas ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully Register!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ManajementTugasActivity.class);
                        // buat method untung menampilkan notifikasi dengan
                        // mengirimkan data yang dikirim dari widget EditText
                        munculkanNotifikasi(judul, deskripsi, intent);
                        kosong();

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
                params.put("judul", judul);
                params.put("deskripsi", deskripsi);
                params.put("nip", nip);
                params.put("kategori", kategori);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManajementTugasActivity.this, PertemuanTugasActivity.class);
        intent.putExtra(Server.TAG_ID, idg);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIP, nip);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_MATPEL, matpel);
        finish();
        startActivity(intent);
    }
}
