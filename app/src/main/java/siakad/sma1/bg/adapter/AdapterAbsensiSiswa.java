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

public class AdapterAbsensiSiswa extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModel> item;


    public interface OnAdd{
        public void OnAdddataClicked(DataModel dataModel);
    }

    public AdapterAbsensiSiswa(Activity activity, List<DataModel> item) {
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
        TextView txt_id, txt_nis, txt_nama_siswa, txt_nip ,txt_nama_guru, txt_matapel, txt_tgl, txt_ketabsen, txt_validasi;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view = convertView;
        final Holder holder = new Holder();

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_absen, null);

            holder.txt_id = (TextView) convertView.findViewById(R.id.txt_id);
            holder.txt_nis = (TextView) convertView.findViewById(R.id.txt_nis);
            holder.txt_nama_siswa = (TextView) convertView.findViewById(R.id.txt_nama_siswa);
            holder.txt_nip = (TextView) convertView.findViewById(R.id.txt_nip);
            holder.txt_nama_guru = (TextView) convertView.findViewById(R.id.txt_nama_guru);
            holder.txt_matapel = (TextView) convertView.findViewById(R.id.txt_matapel);
            holder.txt_tgl = (TextView) convertView.findViewById(R.id.txt_tgl);
            holder.txt_ketabsen = (TextView) convertView.findViewById(R.id.txt_ketabsen);
            holder.txt_validasi = (TextView) convertView.findViewById(R.id.txt_validasi);
            holder.txt_id.setText(item.get(position).getId());
            holder.txt_nis.setText(item.get(position).getNis());
            holder.txt_nama_siswa.setText(item.get(position).getNama_siswa());
            holder.txt_nip.setText(item.get(position).getNip());
            holder.txt_nama_guru.setText(item.get(position).getNama_guru());
            holder.txt_matapel.setText(item.get(position).getMatapel());
            holder.txt_tgl.setText(item.get(position).getTgl());
            holder.txt_ketabsen.setText(item.get(position).getKetabsen());
            holder.txt_validasi.setText(item.get(position).getValidasi());

        return convertView;
    }
}