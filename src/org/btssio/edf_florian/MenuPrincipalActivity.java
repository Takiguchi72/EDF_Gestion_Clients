package org.btssio.edf_florian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import classes.Agent;

public class MenuPrincipalActivity extends Activity implements OnClickListener {
	/* *********************
	 * 	A T T R I B U T S  *
	 ***********************/
	protected Agent agentConnecte;
	protected TextView txvIdentifiant;
	protected TextView txvNom;
	protected TextView txvPrenom;
	protected ImageButton imgBtnClients;
	
	/* *******************
	 *  M E T H O D E S  *
	 *********************/
	/**
	 * Initialise l'activité
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		
		//On instancie un objet Agent à partir des données envoyés par "MainActivity"
		agentConnecte = new Agent(  this.getIntent().getExtras().getString("identifiant"),
									this.getIntent().getExtras().getString("motDePasse"),
									this.getIntent().getExtras().getString("nom"),
									this.getIntent().getExtras().getString("prenom"));
		
		//On initialise les composants graphiques
		txvIdentifiant = (TextView) findViewById(R.id.menuPrincAct_txvIdentifiant);
		txvIdentifiant.setText(agentConnecte.getIdentifiant());
		
		txvNom = (TextView) findViewById(R.id.menuPrincAct_txvNom);
		txvNom.setText(agentConnecte.getNom());
		
		txvPrenom = (TextView) findViewById(R.id.menuPrincAct_txvPrenom);
		txvPrenom.setText(agentConnecte.getPrenom());
		
		imgBtnClients = (ImageButton) findViewById(R.id.menuPrincAct_imgBtnClients);
		imgBtnClients.setOnClickListener(this);
	}//fin onCreate

	/**
	 * Initialise le menu de l'activité
	 * @param Le menu permettant d'initialiser celui de l'activité [Menu]
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_principal, menu);
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
	 * Gère le clic de l'utilisateur sur les éléments de la vue
	 * @param L'élément sur lequel l'agent a cliqué
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			/* ~~~~~~~~~~~~ *
			 * Image CLIENT *
			 * ~~~~~~~~~~~~ */
			case R.id.menuPrincAct_imgBtnClients:
				Intent intentListeClients = new Intent(this, ListeClientActivity.class);
				startActivity(intentListeClients);
				break;
		}//fin switch
	}//fin onClick
}//fin classe
