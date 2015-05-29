package org.btssio.edf_florian;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import classes.Client;
import classes.ClientAdapter;
import dao.ClientDAO;

public class ListeClientActivity extends Activity implements OnItemClickListener {
	/* **********************************
	 * A T T R I B U T S
	 * ******************************* */
	protected ListView listView;
	protected List<Client> listeClients;
	protected ClientDAO clientAccess;
	
	/* *******************
	 *  M E T H O D E S  *
	 *********************/
	/**
	 * Initialise l'activité
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_liste_client);
		
		//On initialise la liste de client
		listeClients = new ArrayList<Client>();
		
		clientAccess = new ClientDAO() {			
			/**
			 * Récupère les clients de la bdd, et initialise la listView avec ceux-ci
			 */
			@Override
			public void onTacheTerminee(ArrayList<Client> resultat) {
				Log.d("Étape", "~ Initialisation de la liste de clients");
				//On va alimenter la liste de clients à partir de celle retournée par le web service
				listeClients = resultat;
				
				Log.d("Étape", "~ Initialisation du ClientAdapter");
				//On va initialiser le composant graphique à partir des clients de la liste
				ClientAdapter clientAdapter = new ClientAdapter(ListeClientActivity.this, listeClients);
				
				Log.d("Étape", "~ Initialisation de la listView");
				listView = (ListView) findViewById(R.id.listeClient_lvClients);
				listView.setOnItemClickListener(ListeClientActivity.this);
				
				Log.d("Étape", "~ Ajout du ClientAdapter à la listView");
				listView.setAdapter(clientAdapter);
			}//fin onTacheTerminee
			
			@Override
			public void onTacheTerminee(Client resultat) {
			}
		};
		
		//On récupère les clients de la bdd et on va initialiser les composants graphiques de l'activité
		clientAccess.getClients();
	}//fin onCreate

	/**
	 * Initialise le menu de l'activité
	 * @param Le menu permettant d'initialiser celui de l'activité [Menu]
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.liste_client, menu);
		return true;
	}//fin onCreateOptionsMenu

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
	 * Réinitialise la liste de clients à chaque retour d'activité
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.d("Étape", "~ Réinitialisation de la liste des clients et la vue correspondante");
		listeClients.clear();
		clientAccess.getClients();
	}//fin onActivityResult
	
	/**
	 * Gère le clic sur les clients
	 * @param AdapterView<?> a
	 * @param L'élément sur lequel l'utilisateur a cliqué [View]
	 * @param L'index de l'élément cliqué dans la liste [int]
	 * @param long id
	 */
	@Override
	public void onItemClick(AdapterView<?> a, View v, int position, long id)
	{
		Log.d("Étape", "~ Clic sur le " + position + "° item de la ListView");
		Intent intentModificationClient = new Intent(this, ModificationClientActivity.class);
		//On passe à l'activité "ModificationClient" l'identifiant du client correspondant à celui sur lequel l'agent aura cliqué dans la listView
		intentModificationClient.putExtra("identifiant", listeClients.get(position).getIdentifiant());
		//On démarre l'activité
		startActivityForResult(intentModificationClient, 0);
	}//fin onItemClick
}//fin classe