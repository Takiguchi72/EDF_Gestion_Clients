package dao;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import util.EventAsyncClient;
import util.AccessHTTP;
import android.util.Log;
import classes.Client;

public abstract class ClientDAO implements EventAsyncClient {
	/**
	 * Constructeur par défaut
	 */
	public ClientDAO() {
	}//fin ClientDAO
	
	/**
	 * Récupère tous les clients depuis la bdd
	 */
	public void getClients()
	{
		AccessHTTP objAsyncTask = new AccessHTTP(){
			/**
			 * Convertit la liste de clients retournée par le webService qui est en JSON en objets Client après l'exécution du thread
			 */
			@Override
			protected void onPostExecute(Long result)
			{
				Log.d("Étape", "~ onPostExecute ClientDAO");
				onTacheTerminee(jsonStringToClientArrayList(this.ret));
			}//fin onPostExecute
		};
		objAsyncTask.execute("http://" + DonneesConnexion.getServeur() + DonneesConnexion.getChemin() + "getTousLesClients.php");
	}//fin getClients
	
	/**
	 * Récupère le client correspondant à l'identifiant passé en paramètre
	 * @param L'identifiant du client [Integer]
	 */
	public void getClientById(String identifiant)
	{
		AccessHTTP requeteHttp = new AccessHTTP(){
			/**
			 * Convertit le client retourné par le webService qui est en JSON en objet Client après l'exécution du thread
			 */
			@Override
			protected void onPostExecute(Long result) {
				Log.d("log","onPostExecute ClientDAO");
				onTacheTerminee(jsonStringToClient(this.ret));
			}
		}; 
		requeteHttp.addParam("identifiant", identifiant);
		requeteHttp.execute("http://" + DonneesConnexion.getServeur() + DonneesConnexion.getChemin() + "getClientById.php");
	}//fin getClientById
	
	/**
	 * Enregistre les modifications apportées au client (passé en paramètre) dans la bdd
	 * @param Le client à modifier [Client]
	 */
	public void setClient(Client leClientAModifier)
	{
		AccessHTTP requeteHttp = new AccessHTTP() {
			@Override
			protected void onPostExecute(Long result) {
				Log.d("log","onPostExecute ClientDAO");
				onTacheTerminee(jsonStringToClient(this.ret));
			}//fin onPostExecute
		};
		
		requeteHttp.addParam("identifiant", 	 leClientAModifier.getIdentifiant());
		requeteHttp.addParam("ancienReleve", 	 String.valueOf(leClientAModifier.getAncienReleve()));
		requeteHttp.addParam("dateAncienReleve", leClientAModifier.getDateAncienReleve());
		requeteHttp.addParam("situation", 		 String.valueOf(leClientAModifier.getSituation()));
		requeteHttp.addParam("signatureBase64",  leClientAModifier.getSignatureBase64());
		
		requeteHttp.execute("http://" + DonneesConnexion.getServeur() + DonneesConnexion.getChemin() + "setClient.php");
	}//fin setClient
	
	/**
	 * Retourne la liste de clients correspondant à la chaîne encodée en JSON
	 * @param La chaîne JSON contenant les données des clients [String]
	 * @return La liste des Clients [ArrayList<Client>]
	 */
	private ArrayList<Client> jsonStringToClientArrayList(String jsonString)
	{
		Log.d("Étape","~ Conversion d'un résultat JSON en liste de Clients");
		
		//On créer une liste vide pour y ajouter des clients par la suite
		ArrayList<Client> listeClient = new ArrayList<Client>();
		
		try {
			//On transforme la chaîne encodée en JSON, en une variable de type tableau JSON
			JSONArray tabJson = new JSONArray(jsonString);
			
			//Pour chaque ligne du tableau JSON, on va ajouter un client à la liste à partir des données du tableau
			for(int i = 0 ; i < tabJson.length() ; i++)
			{
				listeClient.add(new Client( tabJson.getJSONObject(i).getString("identifiant"),
											tabJson.getJSONObject(i).getString("nom"),
											tabJson.getJSONObject(i).getString("prenom"),
											tabJson.getJSONObject(i).getString("adresse"),
											tabJson.getJSONObject(i).getString("cp"),
											tabJson.getJSONObject(i).getString("ville"),
											tabJson.getJSONObject(i).getString("tel"),
											tabJson.getJSONObject(i).getString("idcompteur"),
											tabJson.getJSONObject(i).getString("dateancienreleve"),
											Double.parseDouble(tabJson.getJSONObject(i).getString("ancienreleve")),
											tabJson.getJSONObject(i).getString("signaturebase64")));
			}//fin for
		} catch (JSONException e){
			Log.d("log","pb decodage JSON");
		}//fin catch
		return listeClient;
	}//fin jsonStringToClientArrayList
	
	/**
	 * Retourne un objet Client correspondant à la chaîne JSON passée en paramètres
	 * @param La chaîne JSON contenant les données du client [String]
	 * @return Le client initialisé à partir des données de la chaîne [Client]
	 */
	private Client jsonStringToClient(String jsonString)
	{
		Log.d("Étape","~ Conversion de la chaîne JSON en un Client");
		
		//On créer un client "vide", pour le réinitialiser plus tard à partir des données contenues dans la chaîne encodée en JSON
		Client leClient = new Client();
		
		try {
			//On transforme la chaîne encodée en JSON, en un objet JSON
			JSONObject objJson = new JSONObject(jsonString);
			
			//On réinitialise le client à partir des données dans l'objet JSON
			leClient = new Client(  objJson.getString("identifiant"),
									objJson.getString("nom"),
									objJson.getString("prenom"),
									objJson.getString("adresse"),
									objJson.getString("cp"),
									objJson.getString("ville"),
									objJson.getString("tel"),
									objJson.getString("idcompteur"),
									objJson.getString("dateancienreleve"),
									Double.parseDouble(objJson.getString("ancienreleve")),
									objJson.getString("signaturebase64"));
		} catch (JSONException e){
			Log.d("Étape","~ Impossible de décoder la chaîne JSON !");
		}//fin catch
		return leClient;
	}//fin jsonStringToClient
}//fin classe