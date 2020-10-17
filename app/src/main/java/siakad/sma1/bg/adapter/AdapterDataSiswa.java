package siakad.sma1.bg.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import siakad.sma1.bg.R;
import siakad.sma1.bg.model.DataModel;

public class AdapterDataSiswa extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModel> item;


    public interface OnAdd{
        public void OnAdddataClicked(DataModel dataModel);
    }

    public AdapterDataSiswa(Activity activity, List<DataModel> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView txt_id, txt_nama, txt_nis, txt_kelas, txt_jurusan, txt_ketabsensi;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view = convertView;
        final Holder holder = new Holder();

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_siswa, null);

        holder.txt_id = (TextView) convertView.findViewById(R.id.txt_id);
        holder.txt_nama = (TextView) convertView.findViewById(R.id.txt_nama);
        holder.txt_nis = (TextView) convertView.findViewById(R.id.txt_nis);
        holder.txt_kelas = (TextView) convertView.findViewById(R.id.txt_kelas);
        holder.txt_jurusan = (TextView) convertView.findViewById(R.id.txt_jurusan);
        holder.txt_ketabsensi = (TextView) convertView.findViewById(R.id.txt_ketabsensi);
        holder.txt_id.setText(item.get(position).getId());
        holder.txt_nama.setText(item.get(position).getNama());
        holder.txt_nis.setText(item.get(position).getNis());
        holder.txt_kelas.setText(item.get(position).getKelas());
        holder.txt_jurusan.setText(item.get(position).getJurusan());
        holder.txt_ketabsensi.setText(item.get(position).getKetabsensi());
        return convertView;
    }
}
