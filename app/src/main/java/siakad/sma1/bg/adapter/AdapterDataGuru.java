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

public class AdapterDataGuru extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModel> item;


    public interface OnAdd{
        public void OnAdddataClicked(DataModel dataModel);
    }

    public AdapterDataGuru(Activity activity, List<DataModel> item) {
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
        TextView txt_id, txt_nama, txt_nip, txt_matpel;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view = convertView;
        final Holder holder = new Holder();

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_guru, null);

        holder.txt_id = (TextView) convertView.findViewById(R.id.txt_id);
        holder.txt_nama = (TextView) convertView.findViewById(R.id.txt_nama);
        holder.txt_nip = (TextView) convertView.findViewById(R.id.txt_nip);
        holder.txt_matpel = (TextView) convertView.findViewById(R.id.txt_matpel);
        holder.txt_id.setText(item.get(position).getId());
        holder.txt_nama.setText(item.get(position).getNama());
        holder.txt_nip.setText(item.get(position).getNip());
        holder.txt_matpel.setText(item.get(position).getMatpel());
        return convertView;
    }
}
