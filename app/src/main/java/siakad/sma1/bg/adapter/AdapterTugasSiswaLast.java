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

public class AdapterTugasSiswaLast extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModel> item;


    public interface OnAdd{
        public void OnAdddataClicked(DataModel dataModel);
    }

    public AdapterTugasSiswaLast(Activity activity, List<DataModel> item) {
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
        TextView txt_id, txt_nip, txt_kategori, txt_judul, txt_nis, txt_nama, txt_file,txt_kesimpulan,txt_nilai;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view = convertView;
        final Holder holder = new Holder();

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_tugas_siswa_last, null);

        holder.txt_id = (TextView) convertView.findViewById(R.id.txt_id);
        holder.txt_nip = (TextView) convertView.findViewById(R.id.txt_nip);
        holder.txt_kategori = (TextView) convertView.findViewById(R.id.txt_kategori);
        holder.txt_judul = (TextView) convertView.findViewById(R.id.txt_judul);
        holder.txt_nis = (TextView) convertView.findViewById(R.id.txt_nis);
        holder.txt_nama = (TextView) convertView.findViewById(R.id.txt_nama);
        holder.txt_file = (TextView) convertView.findViewById(R.id.txt_file);
        holder.txt_kesimpulan = (TextView) convertView.findViewById(R.id.txt_kesimpulan);
        holder.txt_nilai = (TextView) convertView.findViewById(R.id.txt_nilai);
        holder.txt_id.setText(item.get(position).getId());
        holder.txt_nip.setText(item.get(position).getNip());
        holder.txt_kategori.setText(item.get(position).getKategori());
        holder.txt_judul.setText(item.get(position).getJudul());
        holder.txt_nis.setText(item.get(position).getNis());
        holder.txt_nama.setText(item.get(position).getNama());
        holder.txt_file.setText(item.get(position).getFile());
        holder.txt_kesimpulan.setText(item.get(position).getKesimpulan());
        holder.txt_nilai.setText(item.get(position).getNilai());
        return convertView;
    }
}
