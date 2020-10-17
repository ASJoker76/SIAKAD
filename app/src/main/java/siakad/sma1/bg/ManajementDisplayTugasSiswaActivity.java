package siakad.sma1.bg;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ManajementDisplayTugasSiswaActivity extends AppCompatActivity {

    public static String ids,idg,kategori,file,judul,deskripsi, nis,nama,kelas,jurusan,username,nip,kesimpulan,matpel;
    TextView txt_judul,txt_deskripsi,txt_nip,txt_kategori,txt_file,txt_nis,txt_nama_siswa,txt_tugas,txt_path,txt_jkesimpulan,txt_kesimpulan;
    ImageView txt_view;
    Button btn_buka;
    ImageButton btn_open;
    private File destination = null;
    private String imgPath = null;
    private String selectedFilePath;
    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;

    ProgressDialog pDialog;
    private String upload_pdf = Server.URL + "uploadtugaslast.php?";
    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;
    String url = "https://www.google.com";
    public static String displayName;
    static public Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajement_display_tugas_siswa);

        ids = getIntent().getStringExtra(Server.TAG_ID);
        username = getIntent().getStringExtra(Server.TAG_USERNAME);
        nis = getIntent().getStringExtra(Server.TAG_NIS);
        nama = getIntent().getStringExtra(Server.TAG_NAMA);

        idg = getIntent().getStringExtra("idg");
        nip = getIntent().getStringExtra("nip");
        matpel = getIntent().getStringExtra("matpel");
        kategori = getIntent().getStringExtra("kategori");
        judul = getIntent().getStringExtra("judul");
        deskripsi = getIntent().getStringExtra("deskripsi");
        file = getIntent().getStringExtra("file");

        txt_judul = (TextView) findViewById(R.id.txt_judul);
        txt_deskripsi = (TextView) findViewById(R.id.txt_deskripsi);
        txt_nip = (TextView) findViewById(R.id.txt_nip);
        txt_kategori = (TextView) findViewById(R.id.txt_kategori);
        txt_file = (TextView) findViewById(R.id.txt_file);


        txt_nis = (TextView) findViewById(R.id.txt_nis);
        txt_nama_siswa = (TextView) findViewById(R.id.txt_nama_siswa);

        txt_judul.setText(judul);
        txt_deskripsi.setText(deskripsi);
        txt_nip.setText(nip);
        txt_kategori.setText(kategori);
        txt_file.setText(file);

        txt_nis.setText(nis);
        txt_nama_siswa.setText(nama);

        btn_buka = (Button) findViewById(R.id.btn_buka);
        txt_tugas = (TextView) findViewById(R.id.txt_tugas);
        txt_path = (TextView) findViewById(R.id.txt_path);
        btn_open = (ImageButton) findViewById(R.id.btn_open);

        txt_jkesimpulan = (TextView) findViewById(R.id.txt_jkesimpulan);
        txt_kesimpulan = (TextView) findViewById(R.id.txt_kesimpulan);

        txt_view = (ImageView) findViewById(R.id.imageView);

        String file2 = txt_file.getText().toString();
        String kategori2 = txt_kategori.getText().toString();
        loadfile(file2);
        loadkategori(kategori2);
    }

    private void loadkategori(String kategori2) {
        if (kategori2.equals("Unjuk Kerja/Praktek")) {
            txt_tugas.setVisibility(View.VISIBLE);
            txt_path.setVisibility(View.VISIBLE);
            btn_open.setVisibility(View.VISIBLE);
            txt_jkesimpulan.setVisibility(View.VISIBLE);
            txt_kesimpulan.setVisibility(View.VISIBLE);
        } else if (kategori2.equals("Portofolio")) {
            txt_tugas.setVisibility(View.VISIBLE);
            txt_path.setVisibility(View.VISIBLE);
            btn_open.setVisibility(View.VISIBLE);
        } else if (kategori2.equals("Tulisan")) {
            txt_tugas.setVisibility(View.VISIBLE);
            txt_path.setVisibility(View.VISIBLE);
            btn_open.setVisibility(View.VISIBLE);
        } else if (kategori2.equals("Lisan")) {
            txt_tugas.setVisibility(View.VISIBLE);
            txt_path.setVisibility(View.VISIBLE);
            btn_open.setVisibility(View.VISIBLE);
        } else if (kategori2.equals("Penugasan")) {
            txt_tugas.setVisibility(View.VISIBLE);
            txt_path.setVisibility(View.VISIBLE);
            btn_open.setVisibility(View.VISIBLE);
        } else if (kategori2.equals("Proyek")) {
            txt_tugas.setVisibility(View.VISIBLE);
            txt_path.setVisibility(View.VISIBLE);
            btn_open.setVisibility(View.VISIBLE);
        } else if (kategori2.equals("Produk")) {
            txt_tugas.setVisibility(View.VISIBLE);
            txt_path.setVisibility(View.VISIBLE);
            btn_open.setVisibility(View.VISIBLE);
        }
    }

    private void loadfile(String file2) {
        if (file2.equals("") || file2.length() == 0) {
            btn_buka.setVisibility(View.GONE);
        }
    }

    public void buka(View view) {
        Intent intent3 = new Intent(ManajementDisplayTugasSiswaActivity.this, DisplayDokumentGuruActivity.class);
        intent3.putExtra("file", txt_file.getText().toString());
        //finish();
        startActivity(intent3);
    }

    public void openfiles(View view) {
        try {
            final CharSequence[] options = {"PDF Files", "Kamera", "Picture", "Cancel"};
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ManajementDisplayTugasSiswaActivity.this);
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
                    } else if (options[item].equals("Kamera")) {
                        dialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 2);
                    } else if (options[item].equals("Gallery")) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 3);
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
        else if (requestCode == 3){
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

    public void kirimtugas(View view) {
        judul = txt_judul.getText().toString();
        kategori = txt_kategori.getText().toString();
        kesimpulan = txt_kesimpulan.getText().toString();
        String path = txt_path.getText().toString();
        String newName = path.substring(path.lastIndexOf(".") + 1, path.length());

        if ( judul.equals("") || judul.length() == 0
                || kategori.equals("") || kategori.length() == 0) {

            new CustomToast().Show_Toast(getApplication(), view,
                    "Tidak Boleh Kosong.");
        }
        else {
            if (newName.equals("pdf")) {
                if( uri.equals("")
                        || displayName.equals("") || displayName.isEmpty() ) {
                    new CustomToast().Show_Toast(getApplication(), view,
                            "Pdf File Belum dimasukan.");
                }
                else{
                    upload(displayName, uri, judul, kategori, nip, nis, nama, kesimpulan,matpel);
                    kosong();
                }
            }
            else if (newName.equals("jpg")) {
                new CustomToast2().Show_Toast(getApplication(), view,
                        "Fiture Belum Tersedia ada");
            }
            else if (newName.equals("jpeg")) {
                new CustomToast2().Show_Toast(getApplication(), view,
                        "Fiture Belum Tersedia ada");
            }
            else{
                new CustomToast().Show_Toast(getApplication(), view,
                        "Tidak Boleh Kosong.");
            }
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

    private void upload(final String pdfname,final Uri pdffile,final String judul,final String kategori,final String nip,final String nis,final String nama,final String kesimpulan,final String matpel) {
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
                    params.put("nip", nip);
                    params.put("kategori", kategori);
                    params.put("nis", nis);
                    params.put("nama", nama);
                    params.put("kesimpulan", kesimpulan);
                    params.put("matpel", matpel);
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
            rQueue = Volley.newRequestQueue(ManajementDisplayTugasSiswaActivity.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void kosong(){
        txt_path.setText(null);
        uri = null ;
        displayName = null;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManajementDisplayTugasSiswaActivity.this, ManajementTugasSiswaActivity.class);
        intent.putExtra(Server.TAG_ID, ids);
        intent.putExtra(Server.TAG_USERNAME, username);
        intent.putExtra(Server.TAG_NIS, nis);
        intent.putExtra(Server.TAG_NAMA, nama);
        intent.putExtra(Server.TAG_KELAS, kelas);
        intent.putExtra(Server.TAG_JURUSAN, jurusan);
        intent.putExtra("nip", nip);
        intent.putExtra("matpel",matpel);
        finish();
        startActivity(intent);
    }
}
