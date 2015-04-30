package classes;

public class Clients {
	// ATTRIBUTS
    private String identifiant,
            nom, prenom, adresse, codePostal,
            ville, telephone, idCompteur,
            dateAncienReleve, dateDernierReleve,
            signatureBase64;
    private double ancienReleve;
    private double dernierReleve;
    private int situation;

    /**
     * Constructeur par défaut
     */
    public Clients() {
        this.dateDernierReleve = new java.text.SimpleDateFormat("dd/MM/yy").format(new java.util.Date());
        this.signatureBase64 = "";
        this.dernierReleve = 0;
        this.situation = 0;
    }

    /**
     * @param identifiant      [String]
     * @param nom              [String]
     * @param prenom           [String]
     * @param adresse          [String]
     * @param codePostal       [String]
     * @param ville            [String]
     * @param telephone        [String]
     * @param idCompteur       [String]
     * @param dateAncienReleve [String]
     * @param ancienReleve     [Double]
     */
    public Clients(String identifiant, String nom, String prenom, String adresse, String codePostal, String ville, String telephone, String idCompteur, String dateAncienReleve, Double ancienReleve) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.telephone = telephone;
        this.idCompteur = idCompteur;
        this.dateAncienReleve = dateAncienReleve;
        this.ancienReleve = ancienReleve;
        this.dernierReleve = 0.0;
        this.dateDernierReleve = new java.text.SimpleDateFormat("dd/MM/yy").format(new java.util.Date());
        this.signatureBase64 = "";
        this.situation = 0;
    }

    // ----------------------------------
// ACCESSEURS
    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdCompteur() {
        return idCompteur;
    }

    public void setIdCompteur(String idCompteur) {
        this.idCompteur = idCompteur;
    }

    public String getDateAncienReleve() {
        return dateAncienReleve;
    }

    public void setDateAncienReleve(String dateAncienReleve) {
        this.dateAncienReleve = dateAncienReleve;
    }

    public String getDateDernierReleve() {
        return dateDernierReleve;
    }

    public void setDateDernierReleve(String dateDernierReleve) {
        this.dateDernierReleve = dateDernierReleve;
    }

    public String getSignatureBase64() {
        return signatureBase64;
    }

    public void setSignatureBase64(String signatureBase64) {
        this.signatureBase64 = signatureBase64;
    }

    public Double getAncienReleve() {
        return ancienReleve;
    }

    public void setAncienReleve(Double ancienReleve) {
        this.ancienReleve = ancienReleve;
    }

    public Double getDernierReleve() {
        return dernierReleve;
    }

    public void setDernierReleve(Double dernierReleve) {
        this.dernierReleve = dernierReleve;
    }
//-----------------------------------
// CONSTRUCTEURS

    public int getSituation() {
        return situation;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }
}
