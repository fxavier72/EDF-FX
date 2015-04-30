package org.btssio.edf_fx_lebret;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import classes.Clients;
import dao.DbAdapter;

public class ModifierClient extends Activity implements OnClickListener {
	private Clients leClient;
	private int indexClientDansListe;
	private TextView textViewIdentifiant;
	private TextView textViewIdentite;
	private TextView textViewTelephone;
	private TextView textViewAdresse;
	private TextView textViewCp;
	private TextView textViewVille;
	private TextView textViewCompteur;
	private TextView textViewAncienReleve;
	private TextView textViewDateAncienReleve;
	private EditText editTextReleve;
	private EditText editTextDateReleve;
	private EditText editTextSituation;
	private DbAdapter bdd;
	private Button btnOk;
	private Button btnAnnuler;
	private Button btnGeoloc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modifier_client);
		
		DbAdapter bdd = new DbAdapter(this).open();
		leClient = bdd.getClientWithId(this.getIntent().getExtras().getString("identifiant"));
		bdd.close();
		
		Log.d("Étape", "~ Création du client à partir des extras"); 
		//Initialisation du client à partir des paramètres qu'a envoyé l'activité "AfficheListeClient" avant de lancer cette activité
		leClient = new Clients(	this.getIntent().getExtras().getString("identifiant"),
								this.getIntent().getExtras().getString("nom"),
								this.getIntent().getExtras().getString("prenom"),
								this.getIntent().getExtras().getString("adresse"),
								this.getIntent().getExtras().getString("codePostal"),
								this.getIntent().getExtras().getString("ville"),
								this.getIntent().getExtras().getString("telephone"),
								this.getIntent().getExtras().getString("idCompteur"),
								this.getIntent().getExtras().getString("dateAncienReleve"),
								this.getIntent().getExtras().getDouble("ancienReleve"));
		leClient.setDateDernierReleve(	this.getIntent().getExtras().getString("dateDernierReleve"));
		leClient.setSignatureBase64(	this.getIntent().getExtras().getString("signatureBase64"));
		leClient.setDernierReleve(		this.getIntent().getExtras().getDouble("dernierReleve"));
		leClient.setSituation(			this.getIntent().getExtras().getInt("situation"));
		
		Log.d("Étape", "~ Initialisation de l'activité");
		//On va remplir les "TextView"s grâce au client
		initialiserActivite();
		
	}

	/**
	 * Gère les clics sur les différents éléments du menu
	 * @param L'élément du menu sur lequel l'utilisateur a cliqué [MenuItem]
	 */
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
	}//fin onOptionsItemSelected
	
	/**
	 * Gère les clics sur les différents boutons de l'application
	 * @param L'élément sur lequel l'utilisateur a cliqué [View]
	 */
	@Override
	public void onClick(View v) {
		Log.d("Étape", "~ Click sur un bouton");
		//On détecte sur quel bouton l'utilisateur a cliqué
		switch (v.getId())
		{
			/* ~~~~~~~~~~~ *
			 *  Bouton OK  *
			 * ~~~~~~~~~~~ */
			case R.id.btnOk:
				Log.d("Étape", "~ Click sur Ok détecté");
				try {
					//On enregistre les modifications dans la bdd
					enregistrerModifications();
					Toast.makeText(this, "Modifications enregistrées !",Toast.LENGTH_SHORT).show();
					
					//On prépare le retour de l'activité
					Intent returnIntent = new Intent();
					
					//On renseigne l'index (du client dans la liste) qui sera retourné pour pouvoir mettre à jour la liste des clients dans AfficheListeClient
					returnIntent.putExtra("indexClient", indexClientDansListe);
					
					//On indique à l'activité appelante qu'on va lui retourner des données, et donc que l'activité s'est bien passée
					setResult(RESULT_OK,returnIntent);
					
					//On termine l'activité ModificationClient
					finish();
				} catch (Exception ex) {
					Toast.makeText(this, ex.getMessage(),Toast.LENGTH_SHORT).show();
				}//fin catch
				break;
			/* ~~~~~~~~~~~~~~~~ *
			 *  Bouton Annuler  *
			 * ~~~~~~~~~~~~~~~~ */
			case R.id.:
				Log.d("Étape", "~ Click sur Annuler détecté");
				finish(); //On termine l'activité ModificationClient
				break;
			/* ~~~~~~~~~~~~~~~~ *
			 *  Bouton Géoloc.  *
			 * ~~~~~~~~~~~~~~~~ */
			case R.id.btnGeoloc:
				Log.d("Étape", "~ Click sur Géoloc. détecté");
				
				//On va lancer l'activité de géolocalisation
				Intent theIntent = new Intent(this, ActivityGeolocalisation.class);
				
				//On passe en paramètre l'identifiant du client actuel pour pouvoir récupérer son adresse par la suite
				theIntent.putExtra("identifiant", leClient.getIdentifiant());
				this.startActivity(theIntent); //Lancement de l'activité Geolocalisation
				break;
		}//fin switch
	}//fin onClick
	
	/**
	 * Effectue les contrôles de saisie avant de modifier le client dans la BDD
	 * @throws Lève une exception si une des saisies est incorrecte ou que la modification du client a échoué [Exception]
	 */
	public void enregistrerModifications() throws Exception
	{
		//On vérifie le bon format des données saisies
		checkEditText();
		
		//On va modifier le client dans la base de données
		Log.d("Étape", "~ Modification du client dans la base");
		try {
			bdd = new DbAdapter(this);
			bdd.open();
			//On met à jour le client à partir de l'objet qui a été modifié en fonctions des données saisies par l'utilisateur
			bdd.updateClient(leClient.getIdentifiant(), leClient);
			bdd.close();
		} catch (Exception ex) {
			Log.d("Étape", "~ Echec de la modification du client dans la BDD");
			throw new Exception("Echec de la modification du client dans la BDD", new Throwable("echecModifError"));
		}//fin catch
		Log.d("Étape", "~ Modification réussie");
	}//fin save
	
	/**
	 * Effectue les contrôles de saisie nécessaires avant la modification du client
	 * @throws Lève une Exception si une des saisies est incorrecte [Exception]
	 */
	private void checkEditText() throws Exception
	{
		Log.d("Étape", "~ Vérification des champs vides");
		//On vérifie qu'il n'y ai pas de champ vide
		checkEditTextVide();
		
		//Création de variables qui contiendront les saisies "cast"ées si elles sont correctes
		Double doubleReleve = 0.0;
		int intSituation = 0;
		
		Log.d("Étape", "~ Vérification du champ \"Relevé\"");
		//On vérifie que la valeur saisie dans editTextReleve peut être cast en Double
		try {
			doubleReleve = Double.valueOf(editTextReleve.getText().toString());
		} catch (Exception ex) {
			Log.d("Étape", "~ Relevé incorrect");
			throw new Exception("Veuillez saisir un nombre décimal dans le champ \"Relevé\"", new Throwable("castError"));
		}//fin catch
		
		
		//On vérifie que la saisie correspond à un nombre entier
		try {
			intSituation = Integer.valueOf(editTextSituation.getText().toString());
			//On vérifie que la situation saisie n'est pas 0
			if (intSituation == 0)
			{
				Log.d("Étape", "~ Situation incorrecte");
				throw new Exception("La situation ne doit pas être 0", new Throwable("situation0Error"));
			}//fin if
		} catch (Exception ex) {
			Log.d("Étape", "~ Situation incorrecte");
			throw new Exception("Veuillez saisir un nombre entier dans le champ \"Situation\"", new Throwable("castError"));
		}//fin catch
		
		Log.d("Étape", "~ Modification du client");
		//On modifie le client s'il n'y a pas eu d'exception de levée
		leClient.setAncienReleve(doubleReleve);
		leClient.setDateAncienReleve(editTextDateReleve.getText().toString());
		leClient.setSituation(intSituation);
	}//fin checkEditText
	
	/**
	 * Vérifie si les zones de saisies sont vide.
	 * 
	 * Dans le cas où l'une d'entre-elles serait vide, une exception est levée.
	 * @throws Une exception "emptyFieldError" [Exception]
	 */
	private void checkEditTextVide() throws Exception
	{
		//Si le champ "Relevé" est vide
		if (editTextReleve.getText().equals(""))
		{
			Log.d("Étape", "~ Champ \"Relevé\" vide");
			throw new Exception("Veuillez remplir le champ \"Relevé\" !", new Throwable("emptyFieldError"));
		}//fin if
			
		//Si le champ "Date" est vide
		if (editTextDateReleve.getText().equals(""))
		{
			Log.d("Étape", "~ Champ \"Date\" vide");
			throw new Exception("Veuillez remplir le champ \"Date\" !", new Throwable("emptyFieldError"));
		}//fin if
			
		//Si le champ "Situation" est vide
		if (editTextSituation.getText().equals(""))
		{
			Log.d("Étape", "~ Champ \"Situation\" vide");
			throw new Exception("Veuillez remplir le champ \"Situation\" !", new Throwable("emptyFieldError"));
		}//fin if
	}//fin checkEditTextVide
	
	/**
	 * Initialise les "TextView"s et les "EditText"s en fonction des vues xml,
	 * et modifie leur valeur en fonction du client.
	 */
	public void initialiserActivite()
	{	
		//On va initialiser les composants graphiques de l'activité
		textViewIdentifiant			= (TextView) this.findViewById(R.id.txtIdentifiantValue);
		textViewIdentite			= (TextView) this.findViewById(R.id.txtIdentiteValue);
		textViewTelephone			= (TextView) this.findViewById(R.id.txtTelephone);
		textViewAdresse				= (TextView) this.findViewById(R.id.txtAdresse);
		textViewCp					= (TextView) this.findViewById(R.id.txtCp);
		textViewVille				= (TextView) this.findViewById(R.id.txtVille);
		textViewCompteur			= (TextView) this.findViewById(R.id.txtCompteurValue);
		textViewAncienReleve		= (TextView) this.findViewById(R.id.txtAncienReleveValue);
		textViewDateAncienReleve	= (TextView) this.findViewById(R.id.txtDateAncienReleveValue);
		editTextReleve				= (EditText) this.findViewById(R.id.editReleve);
		editTextDateReleve			= (EditText) this.findViewById(R.id.editDateReleve);
		editTextSituation			= (EditText) this.findViewById(R.id.editSituation);
		
		//On va remplir les zones de texte à partir des éléments du client
		textViewIdentifiant			.setText(leClient.getIdentifiant());
		textViewIdentite			.setText(leClient.getNom() + " " + leClient.getPrenom());
		textViewTelephone			.setText(leClient.getTelephone());
		textViewAdresse				.setText(leClient.getAdresse());
		textViewCp					.setText(leClient.getCodePostal());
		textViewVille				.setText(leClient.getVille());
		textViewCompteur			.setText(leClient.getIdCompteur());
		textViewAncienReleve		.setText(leClient.getAncienReleve().toString());
		textViewDateAncienReleve	.setText(leClient.getDateAncienReleve());
		
		//On déclare les boutons
		btnOk = (Button) this.findViewById(R.id.btnOk);
		btnOk.setOnClickListener(this);
		
		btnAnnuler = (Button) this.findViewById(R.id.btnAnnuler);
		btnAnnuler.setOnClickListener(this);
		
		btnGeoloc = (Button) this.findViewById(R.id.btnGeoloc);
		btnGeoloc.setOnClickListener(this);
	}//fin initialiserActivite
}
