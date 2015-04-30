package dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import classes.Clients;

public class DbAdapter {
	static final int VERSION_BDD = 1;
    static final String TABLE_CLIENTS = "table_clients";
    static final String COL_ID = "_id";
    static final int NUM_COL_ID = 0;
    static final String COL_NOM = "NOM";
    static final int NUM_COL_NOM = 1;
    static final String COL_PRENOM = "PRENOM";
    static final int NUM_COL_PRENOM = 2;
    static final String COL_ADRESSE = "ADRESSE";
    static final int NUM_COL_ADRESSE = 3;
    static final String COL_CP = "CP";
    static final int NUM_COL_CP = 4;
    static final String COL_VILLE = "VILLE";
    static final int NUM_COL_VILLE = 5;
    static final String COL_TEL = "TEL";
    static final int NUM_COL_TEL = 6;
    static final String COL_IDCOMPTEUR = "IDCOMPTEUR";
    static final int NUM_COL_IDCOMPTEUR = 7;
    static final String COL_DATEANCIENRELEVE = "DATEANCIENRELEVE";
    static final int NUM_COL_DATEANCIENRELEVE = 8;
    static final String COL_ANCIENRELEVE = "ANCIENRELEVE";
    static final int NUM_COL_ANCIENRELEVE = 9;
    static final String COL_DATEDERNIERRELEVE = "DATEDERNIERRELEVE";
    static final int NUM_COL_DATEDERNIERRELEVE = 10;
    static final String COL_DERNIERRELEVE = "DERNIERRELEVE";
    static final int NUM_COL_DERNIERRELEVE = 11;
    static final String COL_SIGNATUREBASE64 = "SIGNATUREBASE64";
    static final int NUM_COL_SIGNATUREBASE64 = 12;
    static final String COL_SITUATION = "SITUATION";
    static final int NUM_COL_SITUATION = 13;
    private static final String NOM_BDD = "clients.db";
    private CreateDbClients dbClients;
    private Context context;
    private SQLiteDatabase db;

    public DbAdapter(Context context) {
        this.context = context;
        dbClients = new CreateDbClients(context, NOM_BDD, null, VERSION_BDD);
    }

    public DbAdapter open() {
        db = dbClients.getWritableDatabase();
        return this;
    }

    public DbAdapter close() {
        db.close();
        return null;
    }

    public long insererClient(Clients client) {
        ContentValues values = new ContentValues();

        values.put(COL_ID, client.getIdentifiant());
        values.put(COL_NOM, client.getNom());
        values.put(COL_PRENOM, client.getPrenom());
        values.put(COL_ADRESSE, client.getAdresse());
        values.put(COL_CP, client.getCodePostal());
        values.put(COL_VILLE, client.getVille());
        values.put(COL_TEL, client.getTelephone());
        values.put(COL_IDCOMPTEUR, client.getIdCompteur());
        values.put(COL_DATEANCIENRELEVE, client.getDateAncienReleve());
        values.put(COL_ANCIENRELEVE, client.getAncienReleve());
        values.put(COL_DATEDERNIERRELEVE, client.getDateDernierReleve());
        values.put(COL_DERNIERRELEVE, client.getDernierReleve());
        values.put(COL_SIGNATUREBASE64, client.getSignatureBase64());
        values.put(COL_SITUATION, client.getSituation());

        return db.insert(TABLE_CLIENTS, null, values);
    }

    public Clients getClientWithId(String identifiant) {
        Cursor c = db.query(TABLE_CLIENTS, new String[]{COL_ID, COL_NOM, COL_PRENOM, COL_ADRESSE,
                COL_CP, COL_VILLE, COL_TEL, COL_IDCOMPTEUR,
                COL_DATEANCIENRELEVE, COL_ANCIENRELEVE,
                COL_DATEDERNIERRELEVE, COL_DERNIERRELEVE,
                COL_SIGNATUREBASE64, COL_SITUATION}, COL_ID + " LIKE '" + identifiant + "'", null, null, null, null);
        return cursorToClient(c);
    }

    private Clients cursorToClient(Cursor c) {
        if (c.getCount() == 0) {
            return null;
        }

        c.moveToFirst();

        Clients client = new Clients();

        client.setIdentifiant(c.getString(NUM_COL_ID));
        client.setNom(c.getString(NUM_COL_NOM));
        client.setPrenom(c.getString(NUM_COL_PRENOM));
        client.setAdresse(c.getString(NUM_COL_ADRESSE));
        client.setCodePostal(c.getString(NUM_COL_CP));
        client.setVille(c.getString(NUM_COL_VILLE));
        client.setTelephone(c.getString(NUM_COL_TEL));
        client.setIdCompteur(c.getString(NUM_COL_IDCOMPTEUR));
        client.setDateAncienReleve(c.getString(NUM_COL_DATEANCIENRELEVE));
        client.setAncienReleve(c.getDouble(NUM_COL_ANCIENRELEVE));
        client.setDateDernierReleve(c.getString(NUM_COL_DATEDERNIERRELEVE));
        client.setDernierReleve(c.getDouble(NUM_COL_DERNIERRELEVE));
        client.setSignatureBase64(c.getString(NUM_COL_SIGNATUREBASE64));
        client.setSituation(c.getInt(NUM_COL_SITUATION));

        c.close();

        return client;
    }

    public int updateClient(String identifiant, Clients client) {
        ContentValues values = new ContentValues();

        values.put(COL_ID, client.getIdentifiant());
        values.put(COL_NOM, client.getNom());
        values.put(COL_PRENOM, client.getPrenom());
        values.put(COL_ADRESSE, client.getAdresse());
        values.put(COL_CP, client.getCodePostal());
        values.put(COL_VILLE, client.getVille());
        values.put(COL_TEL, client.getTelephone());
        values.put(COL_IDCOMPTEUR, client.getIdCompteur());
        values.put(COL_DATEANCIENRELEVE, client.getDateAncienReleve());
        values.put(COL_ANCIENRELEVE, client.getAncienReleve());
        values.put(COL_DATEDERNIERRELEVE, client.getDateDernierReleve());
        values.put(COL_DERNIERRELEVE, client.getDernierReleve());
        values.put(COL_SIGNATUREBASE64, client.getSignatureBase64());
        values.put(COL_SITUATION, client.getSituation());

        return db.update(TABLE_CLIENTS, values, COL_ID + " = '" + identifiant + "'", null);
    }

    public int removeClientWithID(String identifiant) {
        return db.delete(TABLE_CLIENTS, COL_ID + " = '" + identifiant + "'", null);
    }

    public List<Clients> getListClients() {
        List<Clients> listeDesClients = new ArrayList<Clients>();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CLIENTS, null);

        if (c.getCount() == 0) {
            return listeDesClients;
        }

        c.moveToFirst();

        do {
            listeDesClients.add(new Clients(c.getString(NUM_COL_ID),
                            c.getString(NUM_COL_NOM),
                            c.getString(NUM_COL_PRENOM),
                            c.getString(NUM_COL_ADRESSE),
                            c.getString(NUM_COL_CP),
                            c.getString(NUM_COL_VILLE),
                            c.getString(NUM_COL_TEL),
                            c.getString(NUM_COL_IDCOMPTEUR),
                            c.getString(NUM_COL_DATEANCIENRELEVE),
                            c.getDouble(NUM_COL_ANCIENRELEVE)
                    )
            );
        } while (c.moveToNext());

        c.close();
        return listeDesClients;
    }

    public Cursor getData() {
        return db.rawQuery("SELECT * FROM TABLE_CLIENTS", null);
    }
    
    /**
	 * Insère des clients dans la base
	 */
	public void insererDesClients()
	{
		//Insertion de clients
		//(Les données ont été récupérées sur internet, sur un site générant des identités aléatoires)
		// => http://fr.fakenamegenerator.com/
		Log.d("Étape", "~ Insertion des clients dans la base");
		Log.d("Étape", "~ 		1er client");
		insererClient(new Clients(	"cli1", 			"FOUCAULT", 
									"Royce",  			"49 rue Isambard", 
									"97234",  			"FORT-DE-FRANCE", 
									"0123456789", 		"19950055123", 
									"01/02/14",  		3461.35));
		Log.d("Étape", "~ 		2eme client");
		insererClient(new Clients( 	"cli2",				"ÉTOILE", 
									"Nouel",  			"45 rue Marie De Médicis", 
									"59400",  			"CAMBRAI", 
									"0123456798",  		"19950055124", 
									"30/04/14",  		2147.84));
		Log.d("Étape", "~ 		3eme client");
		insererClient(new Clients( 	"cli3", 			"SAUVÉ", 
									"Clarimunda",  		"23 Avenue Marhum", 
									"64100",  			"BAYONNE", 
									"0123456789",  		"19950055130", 
									"21/07/14",  		1264.92));
		Log.d("Étape", "~ 		4eme client");
		insererClient(new Clients( 	"cli4", 			"COLLIN", 
									"Stéphanie",  		"La Plaine Saint-Denis", 
									"93210",  			"SAINT-DENIS", 
									"0126448784",  		"19950055125", 
									"16/08/14",  		2463.5));
		Log.d("Étape", "~ 		5eme client");
		insererClient(new Clients( 	"cli5", 			"BOILEAU", 
									"Olivier",  		"13 Avenue François Cevert", 
									"72700",  			"ALLONNES", 
									"0174821550",  		"19950055129", 
									"13/07/14",  		1801.43));
		Log.d("Étape", "~ 		6eme client");
		insererClient(new Clients( 	"cli6", 			"MENARD", 
									"Émmelyne",  		"7 rue Goya", 
									"72000",  			"LE MANS", 
									"0147614572",  		"19950055126", 
									"07/10/14",  		2973.81));
		Log.d("Étape", "~ 		7eme client");
		insererClient(new Clients( 	"cli7", 			"NEUFVILLE", 
									"Gabriel",  		"3 Avenue de Verdun", 
									"20090",  			"AJACCIO", 
									"0163458701",  		"19950055127", 
									"20/07/14",  		945.6));
		Log.d("Étape", "~ 		8eme client");
		insererClient(new Clients( 	"cli8", 			"DESJARDINS", 
									"Aurélie",  		"7 rue Polignais", 
									"42000",  			"SAINT-ÉTIENNE", 
									"0142087569",  		"19950055128", 
									"06/02/14",  		1851.47));
		Log.d("Étape", "~ Insertion des clients terminée !");
	}//fin insererLes5Clients
	
	/**
	 * Supprime tous les tuples de la table Clients
	 * 
	 * À utiliser UNIQUEMENT pour nettoyer la base des insertions inutiles (lors de tests par exemple)
	 */
	public void viderLaTable()
	{
		Log.d("Étape", "~ Suppression des données de la table Clients");
		db.delete(TABLE_CLIENTS, null, null);
	}//fin viderLaTable
}
