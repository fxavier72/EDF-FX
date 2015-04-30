package org.btssio.edf_fx_lebret;

import java.util.List;

import classes.Clients;
import dao.ClientsAdapter;
import dao.DbAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AfficheListeClientsActivity extends Activity implements OnItemClickListener  {
	private ListView listView;
	private List<Clients> listeClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_affiche_liste_clients);

		//récupération des clients depuis la bdd
		DbAdapter bdd = new DbAdapter(this);
		bdd.open();
		listeClient = bdd.getListClients();
		bdd.close();
		
		//Création de la listView à partir des clients de la bdd
		listView = (ListView)findViewById(R.id.listViewClients);
		ClientsAdapter clientAdapter = new ClientsAdapter(this, listeClient);
		listView.setAdapter(clientAdapter);
		
		listView.setOnItemClickListener(this) ;
		listView.setAdapter(clientAdapter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.affiche_liste_clients, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		Intent theIntent = new Intent(this, ModifierClient.class);
		
		theIntent.putExtra("identifiant", listeClient.get(position).getIdentifiant());
		
		this.startActivityForResult(theIntent, 0);
	}
}
