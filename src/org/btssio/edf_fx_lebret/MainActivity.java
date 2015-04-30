package org.btssio.edf_fx_lebret;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import dao.DbAdapter;

public class MainActivity extends Activity implements OnClickListener{
	private ImageButton imgbtnClients;
	private Button btnInit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imgbtnClients = (ImageButton) findViewById(R.id.clients);
		imgbtnClients.setOnClickListener(this);
		
		btnInit = (Button) findViewById(R.id.btnInitBdd);
		btnInit.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.clients :
			Log.d("etape", "Click détecté sur l'image Clients");
			Intent theIntent = new Intent(this, AfficheListeClientsActivity.class);
			this.startActivity(theIntent);
			break;
			
		case R.id.btnInitBdd:
			DbAdapter bdd = new DbAdapter(this);
	 		bdd.open();
	 		try{
	 			bdd.viderLaTable();
	 			Toast.makeText(this, "La table Client a été vidée", Toast.LENGTH_SHORT).show();
	 		} catch (Exception ex) {
	 			Log.d("Étape", "~ Impossible de vider la table Client !");
	 			Toast.makeText(this, "Impossible de vider la table Client !", Toast.LENGTH_SHORT).show();
	 		}//fin catch
	 		
	 		try {
	 			bdd.insererDesClients();
		 		Toast.makeText(this, "Réinitialisation de la bdd terminée !", Toast.LENGTH_SHORT).show();
	 		} catch (Exception ex) {
	 			Log.d("Étape", "~ Impossible d'insérer les nouveaux clients !");
	 			Toast.makeText(this, "Impossible d'insérer les nouveaux clients !", Toast.LENGTH_SHORT).show();
	 		}//fin catch
	 		break;
		}//fin switch
	}//fin onClick
}
