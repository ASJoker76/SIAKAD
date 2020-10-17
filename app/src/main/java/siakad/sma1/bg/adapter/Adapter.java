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

public class Adapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataModel> item;


    public interface OnAdd{
        public void OnAdddataClicked(DataModel dataModel);
    }

    public Adapter(Activity activity, List<DataModel> item) {
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
        TextView txt_id, txt_username, txt_verifikasi;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view = convertView;
        final Holder holder = new Holder();

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_verifikasi, null);

            holder.txt_id = (TextView) convertView.findViewById(R.id.txt_id);
            holder.txt_username = (TextView) convertView.findViewById(R.id.txt_username);
            holder.txt_verifikasi = (TextView) convertView.findViewById(R.id.txt_verifikasi);

            holder.txt_id.setText(item.get(position).getId());
            holder.txt_username.setText(item.get(position).getUsername());
            holder.txt_verifikasi.setText(item.get(position).getVerifikasi());


        return convertView;
    }
}