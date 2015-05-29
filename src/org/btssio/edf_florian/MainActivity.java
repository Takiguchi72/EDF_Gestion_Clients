package org.btssio.edf_florian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import classes.Agent;
import dao.AgentDAO;

public class MainActivity extends Activity implements OnClickListener {
	/* *********************
	 * 	A T T R I B U T S  *
	 ***********************/
	protected ImageButton imgBtnSeConnecter;
	protected Agent agentConnecte;
	
	/* *******************
	 *  M E T H O D E S  *
	 *********************/
	/**
	 * Initialise l'activité
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imgBtnSeConnecter = (ImageButton) findViewById(R.id.mainActivity_imgBtnCadena);
		imgBtnSeConnecter.setOnClickListener(this);
		
		//On initialise l'objet agentConnecte pour la suite
		agentConnecte = new Agent();
	}//fin onCreate

	/**
	 * Initialise le menu de l'activité
	 * @param Le menu permettant d'initialiser celui de l'activité [Menu]
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	}

	/**
	 * Gère les clics sur les différents boutons de l'application
	 * @param L'élément sur lequel l'utilisateur a cliqué [View]
	 */
	@Override
	public void onClick(View v) {
		Log.d("Étape", "~ Click détecté...");
		switch (v.getId())
		{
		case R.id.mainActivity_imgBtnCadena:
			Log.d("Étape", "~ Clic sur l'image Se Connecter");
			Intent intentAuthentification = new Intent(this, AuthentificationActivity.class);
			startActivityForResult(intentAuthentification, 0);
			break;
		}//fin switch
	}//fin onClick
	
	/**
	 * Vérifie les identifiants de l'agent au retour de l'activité "Authentification".
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.d("Étape", "~ Retour de l'activité lancée par MainActivity");
		//Si l'activité a retourné RESULT_OK, on va procéder à l'authentification
		if(resultCode == RESULT_OK)
		{
			Log.d("Étape", "~ resultCode == RESULT_OK");
			Toast.makeText(this, "Authentification en cours...", Toast.LENGTH_SHORT).show();
			
			AgentDAO accessAgent = new AgentDAO() {
				/**
				 * Vérifie que l'authentification a réussi. Si oui, on lance l'activité "Menu principal", si non, on relance l'activité "Authentification"
				 */
				@Override
				public void onTacheTerminee(Agent resultat) {
					//Si le nom et le prénom de l'agent vaut `null` c'est que l'authentification a échoué
					if(resultat.getNom().equals("") && resultat.getPrenom().equals(""))
					{
						Log.d("Étape", "~ Identifiant ou mot de passe incorrect !");
						Toast.makeText(MainActivity.this, "Identifiant ou mot de passe incorrect !", Toast.LENGTH_LONG).show();
						
						Intent intentAuthentification = new Intent(MainActivity.this, AuthentificationActivity.class);
						startActivityForResult(intentAuthentification, 0);
					}//fin if
					//Si l'authentification a réussi
					else
					{
						Log.d("Étape", "~ Authentification réussie !");
						//On va initialiser l'agent connecté
						agentConnecte = new Agent(  resultat.getIdentifiant(),
													resultat.getMotDePasse(),
													resultat.getNom(),
													resultat.getPrenom());		
						Toast.makeText(MainActivity.this, "Authentification réussie !", Toast.LENGTH_SHORT).show();
						
						//On lance le menu principal de l'agent
						Intent intentMenuPrincipal = new Intent(MainActivity.this, MenuPrincipalActivity.class);
						
						intentMenuPrincipal.putExtra("identifiant", agentConnecte.getIdentifiant());
						intentMenuPrincipal.putExtra("motDePasse", agentConnecte.getMotDePasse());
						intentMenuPrincipal.putExtra("nom", agentConnecte.getNom());
						intentMenuPrincipal.putExtra("prenom", agentConnecte.getPrenom());
						
						startActivity(intentMenuPrincipal);
					}//fin else
				}//fin onTacheTerminee
			};
			
			Log.d("Étape", "~ Récupération de l'agent");
			accessAgent.getAgent(data.getExtras().getString("identifiant"), data.getExtras().getString("motDePasse"));
		}//fin if
		else if(resultCode ==  RESULT_CANCELED)
		{
			Log.d("Étape", "~ Activity annulée");
			Toast.makeText(MainActivity.this, "Authentification annulée", Toast.LENGTH_SHORT).show();
		}//fin else
	}//fin onActivityResult
}//fin classe