package classes;

public class Agent {
	/* *********************
	 * 	A T T R I B U T S  *
	 ***********************/
	protected String identifiant;
	protected String motDePasse;
	protected String nom;
	protected String prenom;
	
	/* *****************************
	 * 	C O N S T R U C T E U R S  *
	 *******************************/
	/**
	 * Initialise un nouvel agent ayant tous ses attributs initialisés avec la valeur `null`
	 */
	public Agent() {
		identifiant = "";
		motDePasse  = "";
		nom			= "";
		prenom		= "";
	}//fin Agent

	/**
	 * Initialise un nouvel agent à partir de paramètres
	 * @param identifiant [String]
	 * @param motDePasse [String]
	 */
	public Agent(String identifiant, String motDePasse, String nom, String prenom)
	{
		this.identifiant = identifiant;
		this.motDePasse  = motDePasse;
		this.nom		 = nom;
		this.prenom		 = prenom;
	}//fin Agent
	
	/**
	 * Retourne une chaine contenant les données de l'agent
	 * @return La chaine contenant les données de l'agent [String]
	 */
	public String toString()
	{
		return identifiant + " " + nom + " " + prenom;
	}//fin toString
	
	/* *********************************
	 * 	G E T T E R S / S E T T E R S  *
	 ***********************************/
	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
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
}//fin classe