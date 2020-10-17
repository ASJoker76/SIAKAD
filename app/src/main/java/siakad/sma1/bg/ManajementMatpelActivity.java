package siakad.sma1.bg;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import siakad.sma1.bg.app.AppController;


public class ManajementMatpelActivity extends AppCompatActivity {


    private TextView tv;
    private String upload_pdf = Server.URL + "uploadpdf.php?";
    private String upload_video = Server.URL + "uploadvideo.php?";
    private String upload_word = Server.URL + "uploadword.php?";
    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;
    String url = "https://www.google.com";
    public static String displayName;
    static public Uri uri;

    private File destination = null;
    private String imgPath = null;
    private String selectedFilePath;
    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;
    TextView txt_nip,txt_nama,txt_matpel,txt_pertemuan,txt_path;
    Button btn_create_modul;

    ImageView btn_foto,btn_video,btn_docx,btn_pdf,txt_view;
    public static String idg,username,nip,matpel,nama,pertemuan;
    int success;

    private String createmodul = Server.URL + "buat_modul.php";
    private String caridata = Server.URL + "cari_modul.php";
    private String updatefoto = Server.URL + "update_foto.php";
    private String updatepdf = Server.URL + "update_pdf.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";
    ConnectivityManager conMgr;

    private static final String TAG = ManajementMatpelActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajement_matpel);

        AllowRunTimePermission();
        
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
        idg = intent.getStringExtra(Server.TAG_ID);
        username = intent.getStringExtra(Server.TAG_USERNAME);
        nip = intent.getStringExtra(Server.TAG_NIP);
        nama = intent.getStringExtra(Server.TAG_NAMA);
        matpel = intent.getStringExtra(Server.TAG_MATPEL);
        pertemuan = intent.getStringExtra(Server.TAG_PERTEMUAN);

        txt_nip = (TextView) findViewById(R.id.txt_nip);
        txt_nama = (TextView) findViewById(R.id.txt_nama);
        txt_matpel = (TextView) findViewById(R.id.txt_matpel);
        txt_pertemuan = (TextView) findViewById(R.id.txt_pertemuan);

        txt_path = (TextView) findViewById(R.id.txt_path);

        btn_create_modul = (Button) findViewById(R.id.btn_create_modul);
        txt_view = (ImageView) findViewById(R.id.imageView);

        tv = findViewById(R.id.tv);

        btn_foto = (ImageView) findViewById(R.id.btn_foto);
        btn_video = (ImageView) findViewById(R.id.btn_video);
        btn_docx = (ImageView) findViewById(R.id.btn_docx);
        btn_pdf = (ImageView) findViewById(R.id.btn_pdf);

        txt_nip.setText(nip);
        txt_nama.setText(nama);
        txt_matpel.setText(matpel);
        txt_pertemuan.setText(pertemuan);

        caridata(nip,nama,matpel,pertemuan);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }

    private void AllowRunTimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ManajementMatpelActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            Toast.makeText(ManajementMatpelActivity.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(ManajementMatpelActivity.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
    }


    public void pindah(View view) {
        switch (view.getId()) {
            case R.id.btn_create_modul:
                createmodul(nip,nama,matpel,pertemuan);
                break;
            case R.id.btn_foto:
                bukafoto();
                break;
            case R.id.btn_docx:
                bukaword();
                break;
            case R.id.btn_pdf:
                bukapdf();
                break;
            case R.id.btn_video:
                bukavideo();
                break;
            case R.id.btn_upload:
                String path = txt_path.getText().toString();
                String newName = path.substring(path.lastIndexOf(".") + 1, path.length());
                if (path.equals("") || path.length() == 0) {

                    new CustomToast().Show_Toast(getApplication(), view,
                            "Pilih untuk di upload.");
                }
                else {
                    if (newName.equals("pdf")) {
                        uploadPDF(displayName,uri,nip,pertemuan);
                    } else if (newName.equals("png")) {
                        uploadImage();
                    } else if (newName.equals("jpg")) {
                        uploadImage();
                    } else if (newName.equals("jpeg")) {
                        uploadImage();
                    } else if(newName.equals("mp4")){
                        uploadVideo(displayName,uri,nip,pertemuan);
                    } else if (newName.equals("doc")) {
                        uploadWord(displayName,uri,nip,pertemuan);
                    } else if (newName.equals("docx")) {
                        uploadWord(displayName,uri,nip,pertemuan);
                    }

                }
                break;
            case R.id.btn_bersihkan:
                kosong();
                break;
        }
    }

    private void bukapdf() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent,1);
    }

    private void bukavideo() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent,2);
    }

    private void bukaword() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/docx");
        startActivityForResult(intent,3);
    }

    private void bukafoto() {
        try {
            final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ManajementMatpelActivity.this);
            builder.setTitle("Select Option");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        dialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 4);
                    } else if (options[item].equals("Choose From Gallery")) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 5);
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


    private void uploadPDF(final String pdfname, Uri pdffile,final String nip, final String pertemuan){

        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_pdf,
                    new Response.Listener<NetworkResponse>() {
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
                                        tv.setText(url);
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
                    params.put("nip", nip);
                    params.put("pertemuan", pertemuan);
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
            rQueue = Volley.newRequestQueue(ManajementMatpelActivity.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void uploadVideo(final String pdfname, Uri pdffile,final String nip, final String pertemuan){

        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_video,
                    new Response.Listener<NetworkResponse>() {
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
                                        tv.setText(url);
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
                    params.put("nip", nip);
                    params.put("pertemuan", pertemuan);
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
            rQueue = Volley.newRequestQueue(ManajementMatpelActivity.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void uploadWord(final String pdfname, Uri pdffile,final String nip, final String pertemuan){

        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_word,
                    new Response.Listener<NetworkResponse>() {
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
                                        tv.setText(url);
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
                    params.put("nip", nip);
                    params.put("pertemuan", pertemuan);
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
            rQueue = Volley.newRequestQueue(ManajementMatpelActivity.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
                displayName = String.valueOf(Calendar.getInstance().getTimeInMillis() + ".mp4");
                Log.d("ooooooo", displayName);
                //uploadPDF(displayName,uri);
                txt_path.setText(displayName);
            }
        }
        else if (requestCode == 3) {
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
        else if (requestCode == 4) {
            if (resultCode == RESULT_OK) {
                try {
                    uri = data.getData();

                    bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                    Log.e("Activity", "Pick from Camera::>>> ");

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    destination = new File(Environment.getExternalStorageDirectory() + "/" +
                            getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    imgPath = destination.getAbsolutePath();
                    //imageView.setImageBitmap(bitmap);
                    setToImageView(getResizedBitmap(bitmap, 1024));
                    txt_path.setText(imgPath);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == 5){
            if (resultCode == RESULT_OK) {
                try {
                    uri = data.getData();
                    //mengambil fambar dari Gallery
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                    setToImageView(getResizedBitmap(bitmap, 1024));
                    selectedFilePath = getPath(uri);
                    if (selectedFilePath != null) {
                        txt_path.setText(selectedFilePath);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public String getPath(Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(uri, projection, null, null, null);

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        txt_view.setImageBitmap(decoded);
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void uploadImage() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading Image...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, updatefoto,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());
                                Toast.makeText(ManajementMatpelActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                                kosong();
                            } else {
                                Toast.makeText(ManajementMatpelActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                        //menampilkan toast
                        Toast.makeText(ManajementMatpelActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put(Server.TAG_GAMBAR, getStringImage(decoded));
                params.put(Server.TAG_PATH, txt_path.getText().toString().trim());
                params.put(Server.TAG_NIP, nip);
                params.put(Server.TAG_PERTEMUAN, pertemuan);

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void createmodul(final String nip,final String nama,final String matpel,final String pertemuan) {
        final ProgressDialog loading = ProgressDialog.show(this, "Create Modul...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, createmodul,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());
                                Toast.makeText(ManajementMatpelActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                                Intent intent2 = new Intent(ManajementMatpelActivity.this, PertemuanMatpelActivity.class);
                                intent2.putExtra(Server.TAG_ID, idg);
                                intent2.putExtra(Server.TAG_USERNAME, username);
                                intent2.putExtra(Server.TAG_NIP, nip);
                                intent2.putExtra(Server.TAG_NAMA, nama);
                                intent2.putExtra(Server.TAG_MATPEL, matpel);
                                finish();
                                startActivity(intent2);
                            } else {
                                Toast.makeText(ManajementMatpelActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                        //menampilkan toast
                        Toast.makeText(ManajementMatpelActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters

                Map<String, String> params = new HashMap<String, String>();
                //menambah parameter yang di kirim ke web servis
                params.put("nip", nip);
                params.put("nama", nama);
                params.put("matpel", matpel);
                params.put("pertemuan", pertemuan);

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void caridata(final String nip,final String nama,final String matpel,final String pertemuan) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, caridata,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 0) {
                                Log.e("v Add", jObj.toString());
                                Toast.makeText(ManajementMatpelActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                                btn_create_modul.setVisibility(View.VISIBLE);
                                btn_docx.setVisibility(View.GONE);
                                btn_foto.setVisibility(View.GONE);
                                btn_video.setVisibility(View.GONE);
                                btn_pdf.setVisibility(View.GONE);
                            } else if(success == 1) {
                                Log.e("v Add", jObj.toString());
                                Toast.makeText(ManajementMatpelActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                                btn_create_modul.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        //menampilkan toast
                        Toast.makeText(ManajementMatpelActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters

                Map<String, String> params = new HashMap<String, String>();
                //menambah parameter yang di kirim ke web servis
                params.put("nip", nip);
                params.put("nama",nama);
                params.put("matpel",matpel);
                params.put("pertemuan",pertemuan);

                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void kosong() {
        txt_view.setImageResource(0);
        txt_path.setText(null);
        uri = null ;
        displayName = null;
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(ManajementMatpelActivity.this,"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(ManajementMatpelActivity.this,"Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
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

    public void cekupload(View view) {
        Intent intent = new Intent(this, CekBahanAjarActivity.class);

        intent.putExtra("idg", idg);
        intent.putExtra("namag", nama);
        intent.putExtra("nip", nip);
        intent.putExtra("matpel", matpel);

        //intent.putExtra(Server.TAG_NAMA, nama);
        //intent.putExtra(Server.TAG_NIS, nis);
        intent.putExtra(Server.TAG_PERTEMUAN, pertemuan);
        startActivity(intent);
    }
}
