package classes;

public class Client {
	/* *********************
	 * 	A T T R I B U T S  *
	 ***********************/
	//Données ne pouvant être modifiées
	private String 	identifiant, 
					nom, 
					prenom, 
					adresse, 
					codePostal, 
					ville,
					telephone, 
					idCompteur, 
					dateAncienReleve;
	private Double 	ancienReleve;
	//Données pouvant être modifiées
	private String 	dateDernierReleve, 
					signatureBase64;
	private Double 	dernierReleve;
	private int 	situation;
	
	/* *****************************
	 * 	C O N S T R U C T E U R S  *
	 *******************************/
	/**
	 * Initialise un nouveau Client ayant tous ses attributs vides
	 */
	public Client()
	{
		//Affectation des Strings
		identifiant 		= 
		nom 				=
		prenom 				= 
		adresse				= 
		codePostal			=
		ville 				=
		telephone			=
		idCompteur			=
		dateAncienReleve	=
		dateDernierReleve	=
		signatureBase64		= "";
		//Affectation des Doubles
		ancienReleve		= 
		dernierReleve		= 0.0;
		//Affectation des Integers
		situation			= 0;
	}//fin Client
	
	/**
	 * Initialise un nouveau Client à partir de paramètres. Les attributs modifiables seront initialisés à { 0 ; 0.0 ; "" }.
	 * @param L'identifiant [String]
	 * @param Le nom [String]
	 * @param Le prenom [String]
	 * @param L'adresse [String]
	 * @param Le code postal [String]
	 * @param La ville [String]
	 * @param Le numéro de téléphone [String]
	 * @param L'id du Compteur [String]
	 * @param La date de l'ancien releve [String]
	 * @param Le montant de l'ancien releve [Double]
	 */
	public Client(String identifiant, String nom, String prenom,
			String adresse, String codePostal, String ville, String telephone,
			String idCompteur, String dateAncienReleve, Double ancienReleve, String signatureBase64) 
	{
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
		dernierReleve = 0.0;
		dateDernierReleve = getDateDuJour();
		this.signatureBase64=signatureBase64;
		situation = 0;
	}//fin Client(...)
	
	/* *******************
	 *  M E T H O D E S  *
	 *********************/
	/**
	 * Retourne la date du jour
	 * @return Date au format "dd/MM/yy" [String]
	 */
	public static String getDateDuJour()
	{
		//Récupération de la date du jour
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat( "dd/MM/yy" );	//Récupération de la date au format "dd/MM/yy"
		java.util.Date date = new java.util.Date();
		return formater.format(date);
	}//fin getDateDuJour
	
	/**
	 * Retourne une chaine contenant les informations du client
	 * @return Les caractéristiques du client [String]
	 */
	public String toString()
	{
		return "Client #" 					+ identifiant
				+ "\n - Nom : " 			+ nom
				+ "\n - Prénom : "			+ prenom
				+ "\n - Adresse : "			+ adresse + ", " + codePostal + " " + ville
				+ "\n - Compteur : n°" + idCompteur
				+ "\n\tMontant de l'ancien relevé : " + ancienReleve + " - effectué le " + dateAncienReleve
				+ "\n\tMontant du dernier relevé : " + dernierReleve + " - effectué le " + dateDernierReleve
				+ "\n - Situation : " + situation;
	}//fin toString()
	
	/* *********************************
	 * 	G E T T E R S / S E T T E R S  *
	 ***********************************/
	/**
	 * Retourne l'identifiant du client
	 * @return L'identifiant [String]
	 */
	public String getIdentifiant() {
		return identifiant;
	}
	
	/**
	 * Modifie l'identifiant du client en lui affectant la valeur du paramètre
	 * @param Le nouvel identifiant du client [String]
	 */
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	
	/**
	 * Retourne le nom du client
	 * @return Le nom [String]
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Modifie le nom du client en lui affectant la valeur du paramètre
	 * @param Le nouveau nom du client [String]
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Retourne le prénom du client
	 * @return Le prénom [String]
	 */
	public String getPrenom() {
		return prenom;
	}
	
	/**
	 * Modifie le prénom du client en lui affectant la valeur du paramètre
	 * @param Le nouveau prénom du client [String]
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	/**
	 * Retourne l'adresse du client
	 * @return L'adresse [String]
	 */
	public String getAdresse() {
		return adresse;
	}
	
	/**
	 * Modifie l'adresse du client en lui affectant la valeur du paramètre
	 * @param La nouvelle adresse du client [String]
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	/**
	 * Retourne le code postal du client
	 * @return Le code postal [String]
	 */
	public String getCodePostal() {
		return codePostal;
	}
	
	/**
	 * Modifie le code postal du client en lui affectant la valeur du paramètre
	 * @param Le nouveau code postal du client [String]
	 */
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	
	/**
	 * Retourne la ville du client
	 * @return Le code postal [String]
	 */
	public String getVille() {
		return ville;
	}
	
	/**
	 * Modifie la ville du client en lui affectant la valeur du paramètre
	 * @param La nouvelle ville du client [String]
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	/**
	 * Retourne le numéro de téléphone du client
	 * @return Le numéro de téléphone [String]
	 */
	public String getTelephone() {
		return telephone;
	}
	
	/**
	 * Modifie le numéro de téléphone du client en lui affectant la valeur du paramètre
	 * @param Le nouveau numéro de téléphone du client [String]
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
	
	
	
	
	/**
	 * Retourne l'id du compteur du client
	 * @return L'id du compteur [String]
	 */
	public String getIdCompteur() {
		return idCompteur;
	}
	
	/**
	 * Modifie l'id du compteur du client en lui affectant la valeur du paramètre
	 * @param Le nouvel id du compteur du client [String]
	 */
	public void setIdCompteur(String idCompteur) {
		this.idCompteur = idCompteur;
	}
	
	/**
	 * Retourne la date de l'ancien relevé de compteur du client
	 * @return La date de l'ancien relevé de compteur [String]
	 */
	public String getDateAncienReleve() {
		return dateAncienReleve;
	}
	
	/**
	 * Modifie la date de l'ancien relevé de compteur du client en lui affectant la valeur du paramètre
	 * @param La nouvelle date de l'ancien relevé de compteur du client [String]
	 */
	public void setDateAncienReleve(String dateAncienReleve) {
		this.dateAncienReleve = dateAncienReleve;
	}
	
	/**
	 * Retourne la date du dernier relevé de compteur du client
	 * @return La date du dernier relevé de compteur [String]
	 */
	public String getDateDernierReleve() {
		return dateDernierReleve;
	}
	
	/**
	 * Modifie la date du dernier relevé de compteur du client en lui affectant la valeur du paramètre
	 * @param La nouvelle date du dernier relevé de compteur du client [String]
	 */
	public void setDateDernierReleve(String dateDernierReleve) {
		this.dateDernierReleve = dateDernierReleve;
	}
	
	/**
	 * Retourne la signature du client
	 * @return La signature [String]
	 */
	public String getSignatureBase64() {
		return signatureBase64;
	}
	
	/**
	 * Modifie la signature du client en lui affectant la valeur du paramètre
	 * @param La nouvelle signature du client [String]
	 */
	public void setSignatureBase64(String signatureBase64) {
		this.signatureBase64 = signatureBase64;
	}
	
	/**
	 * Retourne le montant de l'ancien relevé de compteur du client
	 * @return Le montant de l'ancien relevé de compteur [String]
	 */
	public Double getAncienReleve() {
		return ancienReleve;
	}
	
	/**
	 * Modifie le montant de l'ancien relevé de compteur du client en lui affectant la valeur du paramètre
	 * @param Le nouveau montant de l'ancien relevé de compteur du client [String]
	 */
	public void setAncienReleve(Double ancienReleve) {
		this.ancienReleve = ancienReleve;
	}
	
	/**
	 * Retourne le montant du dernier relevé de compteur du client
	 * @return Le montant du dernier relevé de compteur [String]
	 */
	public Double getDernierReleve() {
		return dernierReleve;
	}
	
	/**
	 * Modifie le montant du dernier relevé de compteur du client en lui affectant la valeur du paramètre
	 * @param Le nouveau montant du dernier relevé de compteur du client [String]
	 */
	public void setDernierReleve(Double dernierReleve) {
		this.dernierReleve = dernierReleve;
	}
	
	/**
	 * Retourne la situation du client
	 * @return La situation [String]
	 */
	public int getSituation() {
		return situation;
	}
	
	/**
	 * Modifie la situation du client en lui affectant la valeur du paramètre
	 * @param La nouvelle situation du client [String]
	 */
	public void setSituation(int situation) {
		this.situation = situation;
	}
}//fin classe