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
	 * Initialise un nouvel agent ayant tous ses attributs vides
	 */
	public Agent() {
		identifiant = "";
		motDePasse  = "";
		nom			= "";
		prenom		= "";
	}//fin Agent

	/**
	 * Initialise un nouvel agent à partir de paramètres
	 * @param L'identifiant de l'agent [String]
	 * @param Le mot de passe de l'agent (Chiffré en md5) [String]
	 * @param Le nom de l'agent [String]
	 * @param Le prénom de l'agent [String]
	 */
	public Agent(String identifiant, String motDePasse, String nom, String prenom)
	{
		this.identifiant = identifiant;
		this.motDePasse  = motDePasse;
		this.nom		 = nom;
		this.prenom		 = prenom;
	}//fin Agent
	
	/* *******************
	 *  M E T H O D E S  *
	 *********************/
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
	/**
	 * Retourne l'identifiant de l'agent
	 * @return L'identifiant [String]
	 */
	public String getIdentifiant() {
		return identifiant;
	}

	/**
	 * Modifie l'identifiant de l'agent en lui affectant la valeur du paramètre
	 * @param Le nouvel identifiant de l'agent [String]
	 */
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * Retourne le mot de passe de l'agent (Chiffré en md5)
	 * @return Mot de passe chiffré en md5 [String]
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * Modifie le mot de passe de l'agent en lui affectant la valeur du paramètre
	 * @param Le nouveau mot de passe [String]
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	/**
	 * Retourne le nom de l'agent [String]
	 * @return Le nom [String]
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Modifie le nom de l'agent en lui affectant la valeur du paramètre
	 * @param Le nouveau nom [String]
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne le prénom de l'agent [String]
	 * @return Le prénom [String]
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Modifie le prénom de l'agent en lui affectant la valeur du paramètre
	 * @param Le nouveau prénom [String]
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}//fin classe