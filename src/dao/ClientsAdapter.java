package dao;

import java.util.List;

import org.btssio.edf_fx_lebret.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import classes.Clients;

public class ClientsAdapter extends BaseAdapter {
	private List<Clients> listClients;
    private LayoutInflater layoutInflater;

    public ClientsAdapter(Context context, List<Clients> vListClient) {
        layoutInflater = LayoutInflater.from(context);
        listClients = vListClient;
    }

    @Override
    public int getCount() {
        return this.listClients.size();
    }

    @Override
    public Object getItem(int position) {
        return listClients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.vueclients, null);
            holder.vueIdentifiant = (TextView) convertView.findViewById(R.id.vueIdentifiant);
            holder.vuePrenom = (TextView) convertView.findViewById(R.id.vuePrenom);
            holder.vueNom = (TextView) convertView.findViewById(R.id.vueNom);
            holder.vueTelephone = (TextView) convertView.findViewById(R.id.vueTel);
            holder.vueAdresse = (TextView) convertView.findViewById(R.id.vueAdresse);
            holder.vueCp = (TextView) convertView.findViewById(R.id.vueCp);
            holder.vueVille = (TextView) convertView.findViewById(R.id.vueVille);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.vueIdentifiant.setText(listClients.get(position).getIdentifiant());
        holder.vuePrenom.setText(listClients.get(position).getPrenom());
        holder.vueNom.setText(listClients.get(position).getNom());
        holder.vueTelephone.setText(listClients.get(position).getTelephone());
        holder.vueAdresse.setText(listClients.get(position).getAdresse());
        holder.vueCp.setText(listClients.get(position).getCodePostal());
        holder.vueVille.setText(listClients.get(position).getVille());

        if(position % 2 == 0){
			convertView.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else {
			convertView.setBackgroundColor(Color.rgb(255, 255, 255));
		}//fin else

        return convertView;
    }

    private class ViewHolder {
    	TextView vueIdentifiant;
        TextView vueNom;
        TextView vuePrenom;
        TextView vueTelephone;
        TextView vueAdresse;
        TextView vueCp;
        TextView vueVille;
    }
}
