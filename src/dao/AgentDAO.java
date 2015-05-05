package dao;

import org.json.JSONException;
import org.json.JSONObject;
import util.AccessHTTP;
import util.EventAsyncAgent;
import android.util.Log;
import classes.Agent;

public abstract class AgentDAO implements EventAsyncAgent{
	/**
	 * Constructeur par défaut
	 */
	public AgentDAO() {
	}//fin AgentDAO
	
	/**
	 * Récupère l'agent correspondant à celui passé en paramètres
	 * @param L'agent à récuperer [Agent]
	 */
	public void getAgent(String identifiant, String motDePasse)
	{
		AccessHTTP objAsyncTask = new AccessHTTP() {
			@Override
			protected void onPostExecute(Long result)
			{
				onTacheTerminee(jsonStringToAgent(this.ret));
			}//fin onPostExecute
		};
		
		//On renseigne les paramètres qui seront envoyés au web service
		objAsyncTask.addParam("identifiant", identifiant);
		objAsyncTask.addParam("motDePasse", motDePasse);
		
		//On exécute la requête
		objAsyncTask.execute("http://" + DonneesConnexion.getServeur() + DonneesConnexion.getChemin() + "identificationAgent.php");
	}//fin getAgent
	
	/**
	 * Retourne un objet Agent correspondant à la chaine JSON passée en paramètres
	 * @param La chaîne JSON contenant les données de  l'agent [String]
	 * @return L'agent initialisé à partir des données de la chaîne [Agent]
	 */
	protected Agent jsonStringToAgent(String jsonString)
	{
		Log.d("Étape","~ Conversion de la chaîne JSON en un Agent");
		
		Agent agentRecupere = new Agent();
		
		try {
			if(jsonString.substring(0, 6).equals("Erreur"))
			{
				throw new Exception();
			}
			
			//On transforme la chaîne encodée en JSON, en un objet JSON
			JSONObject objJson = new JSONObject(jsonString);
			
			agentRecupere = new Agent(  objJson.getString("identifiant"),
										objJson.getString("motdepasse"),
										objJson.getString("nom"),
										objJson.getString("prenom"));
		} catch (JSONException e) {
			Log.d("Étape","~ Impossible de décoder la chaîne JSON !");
		} catch (Exception e) {
			Log.d("Étape","~ Le web service a retourné une erreur !");
		}//fin catch
		return agentRecupere;
	}//fin jsonStringToAgent
}//fin classe