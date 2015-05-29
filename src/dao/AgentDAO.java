package dao;

import org.json.JSONException;
import org.json.JSONObject;
import util.AccessHTTP;
import util.EventAsyncAgent;
import android.util.Log;
import classes.Agent;

public abstract class AgentDAO implements EventAsyncAgent{
	/* *****************************
	 * 	C O N S T R U C T E U R S  *
	 *******************************/
	/**
	 * Instancie l'AgentDAO
	 */
	public AgentDAO() {
	}//fin AgentDAO
	
	/* *******************
	 *  M E T H O D E S  *
	 *********************/
	/**
	 * Récupère l'agent en bdd correspondant à celui passé en paramètres
	 * @param L'agent à récuperer [Agent]
	 */
	public void getAgent(String identifiant, String motDePasse)
	{
		AccessHTTP objAsyncTask = new AccessHTTP() {
			/**
			 * Convertit l'agent retourné par le webService (qui est sous forme de chaîne JSON) en un objet Agent après l'exécution du thread
			 */
			@Override
			protected void onPostExecute(Long result)
			{
				Log.d("Étape", "~ onPostExecute AgentDAO");
				//Une fois que le web service a retourné des valeurs, on va les convertir en un objet de classe Agent
				onTacheTerminee(jsonStringToAgent(this.ret));
			}//fin onPostExecute
		};
		
		Log.d("Étape", "~ Ajout de paramètres à la requête destinée au web service");
		//On renseigne les paramètres qui seront envoyés au web service
		objAsyncTask.addParam("identifiant", identifiant);
		objAsyncTask.addParam("motDePasse", motDePasse);
		
		Log.d("Étape", "~ Envoi de la requête au web service retournant l'agent correspondant aux ID envoyés");
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
		
		//On créer un agent "vide"
		Agent agentRecupere = new Agent();
		
		try {
			//Si le web service a retourné une erreur, la chaine JSON contiendra "Erreur", donc on lève une exception
			if(jsonString.substring(0, 6).equals("Erreur"))
			{
				throw new Exception();
			}//fin if
			
			//On transforme la chaîne encodée en JSON, en un objet JSON
			JSONObject objJson = new JSONObject(jsonString);
			
			//On réinstancie l'agent créé, grâce aux données de l'objet JSON
			agentRecupere = new Agent(  objJson.getString("identifiant"),
										objJson.getString("motdepasse"),
										objJson.getString("nom"),
										objJson.getString("prenom"));
		} catch (JSONException e) {
			Log.d("Erreur","~ Impossible de décoder la chaîne JSON !");
		} catch (Exception e) {
			Log.d("Erreur","~ Le web service a retourné une erreur !");
		}//fin catch
		return agentRecupere;
	}//fin jsonStringToAgent
}//fin classe