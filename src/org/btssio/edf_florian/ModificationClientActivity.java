package org.btssio.edf_florian;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import classes.Client;
import dao.ClientDAO;

public class ModificationClientActivity extends Activity implements OnClickListener{
	protected TextView  txvIdentifiant, txvIdentite, 	txvTelephone,
						txvAdresse, 	txvCodePostal, 	txvVille, 
						txvCompteur, 	txvAncienReleve,txvDateAncienReleve;
	protected EditText edtReleve, edtSituation;
	protected Button btnAfficherSignature, btnGeoloc, btnValider, btnAnnuler;
	protected Client clientActuel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modification_client);
		
		//On récupère le client grâce à son id passé par ListeClientActivity
		ClientDAO clientAccess = new ClientDAO() {
			/**
			 * Initialise l'objet "clientActuel" à partir des valeurs retournées par le web service, puis initialise l'activité
			 */
			@Override
			public void onTacheTerminee(Client resultat) {
				clientActuel = resultat;
				//On initialise les composants graphiques à partir du client récupéré
				initialiserActivite();
			}//fin onTacheTerminee
			@Override
			public void onTacheTerminee(ArrayList<Client> resultat) {
			}
		};
		
		//On récupère le client à partir de l'identifiant passé par ListeClientActivity
		Log.d("Étape", "~ Récupération du client depuis la bdd");
		clientAccess.getClientById(this.getIntent().getExtras().getString("identifiant"));
	}//fin onCreate
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modification_client, menu);
		return true;
	}
	
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
	
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ *
			 * Bouton AFFICHER LA SIGNATURE *
			 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
			case R.id.modifCli_btnAfficherSignature:
				Log.d("Étape", "~ Clic sur le bouton Afficher la signature détecté");
				Intent intentAffichSignature = new Intent(this, AfficherSignatureActivity.class);
				intentAffichSignature.putExtra("signature", clientActuel.getSignatureBase64());
				startActivity(intentAffichSignature);
				break;
			/* ~~~~~~~~~~~~~~ *
			 * Bouton GÉOLOC. *
			 * ~~~~~~~~~~~~~~ */
			case R.id.modifCli_btnGeoloc:
				Log.d("Étape", "~ Clic sur le bouton Géoloc.");
				Intent intentGeolocalisation = new Intent(this, GeolocalisationActivity.class);
				intentGeolocalisation.putExtra("adresseClient", clientActuel.getAdresse() + "," + clientActuel.getCodePostal() + " " + clientActuel.getVille() + " France");
				startActivity(intentGeolocalisation);
				break;
			/* ~~~~~~~~~~~~~~ *
			 * Bouton VALIDER *
			 * ~~~~~~~~~~~~~~ */
			case R.id.modifCli_btnValider:
				Log.d("Étape", "~ Clic sur le bouton Valider");
				enregistrerModifications();
				break;
			/* ~~~~~~~~~~~~~~ *
			 * Bouton ANNULER *
			 * ~~~~~~~~~~~~~~ */
			case R.id.modifCli_btnAnnuler:
				Log.d("Étape", "~ Clic sur le bouton Annuler");
				finish();
				break;
		}//fin switch
	}//fin onClick
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		//Si le code retour est bien RESULT_OK, on va procéder à l'enregistrement des modifications DANS LA BDD
		if(resultCode == RESULT_OK)
		{
			clientActuel.setSignatureBase64(data.getExtras().getString("signatureBase64"));
			
			ClientDAO clientAccess = new ClientDAO() {
				
				@Override
				public void onTacheTerminee(Client resultat) {
					Toast.makeText(ModificationClientActivity.this, "Modifications enregistrées !", Toast.LENGTH_SHORT).show();
					ModificationClientActivity.this.finish();
				}
				
				@Override
				public void onTacheTerminee(ArrayList<Client> resultat) {
				}
			};
			
			Toast.makeText(this, "Enregistrement des modifications en cours...", Toast.LENGTH_SHORT).show();
			Log.d("Valeur", "Signature Size : " + clientActuel.getSignatureBase64().length());
			clientAccess.setClient(clientActuel);
		}//fin if
	}//fin onActivityResult
	
	/**
	 * Initialise tous les composants graphiques et affiche les données du client dans les zones de textes de l'activité
	 */
	public void initialiserActivite()
	{
		//Initialisation des composants graphiques à partir de leur vue XML
		txvIdentifiant 		 = (TextView) findViewById(R.id.modifCli_txvIdentifiantValeur);
		txvIdentite			 = (TextView) findViewById(R.id.modifCli_txvIdentiteValeur);
		txvTelephone		 = (TextView) findViewById(R.id.modifCli_txvTelephoneValeur);
		txvAdresse			 = (TextView) findViewById(R.id.modifCli_txvAdresseValeur);
		txvCodePostal		 = (TextView) findViewById(R.id.modifCli_txvCodePostalValeur);
		txvVille			 = (TextView) findViewById(R.id.modifCli_txvVilleValeur);
		txvCompteur			 = (TextView) findViewById(R.id.modifCli_txvCompteurValeur);
		txvAncienReleve		 = (TextView) findViewById(R.id.modifCli_txvAncienReleveValeur);
		txvDateAncienReleve  = (TextView) findViewById(R.id.modifCli_txvDateAncienReleveValeur);
		edtReleve			 = (EditText) findViewById(R.id.modifCli_edtReleve);
		edtSituation		 = (EditText) findViewById(R.id.modifCli_edtSituation);
		btnAfficherSignature = (Button) findViewById(R.id.modifCli_btnAfficherSignature);
		btnGeoloc			 = (Button) findViewById(R.id.modifCli_btnGeoloc);
		btnValider			 = (Button) findViewById(R.id.modifCli_btnValider);
		btnAnnuler			 = (Button) findViewById(R.id.modifCli_btnAnnuler);
		
		//On ajoute des OnClickListener aux boutons de l'activité
		btnAfficherSignature.setOnClickListener(this);
		btnGeoloc.setOnClickListener(this);
		btnValider.setOnClickListener(this);
		btnAnnuler.setOnClickListener(this);
		
		//On va afficher les données du client dans les textViews
		txvIdentifiant.setText(clientActuel.getIdentifiant());
		txvIdentite.setText(clientActuel.getNom() + " " + clientActuel.getPrenom());
		txvTelephone.setText(clientActuel.getTelephone());
		txvAdresse.setText(clientActuel.getAdresse());
		txvCodePostal.setText(clientActuel.getCodePostal());
		txvVille.setText(clientActuel.getVille());
		txvCompteur.setText(clientActuel.getIdCompteur());
		txvAncienReleve.setText(clientActuel.getAncienReleve().toString());
		txvDateAncienReleve.setText(clientActuel.getDateAncienReleve());
		
		// /!\ Lorsque la signature est vide en base de données, la méthode getSignatureBase64() retourne la chaine "null" et non la valeur `null`
		// D'où le test qui suit
		Log.d("Valeur", "Signature : " + clientActuel.getSignatureBase64() + " ; " + clientActuel.getSignatureBase64().getClass());
		Log.d("Valeur", "Taille signature : " + clientActuel.getSignatureBase64().toString().length() + " ; " + clientActuel.getSignatureBase64().equals("null"));
		if(clientActuel.getSignatureBase64().equals("null"))
		{
			btnAfficherSignature.setEnabled(false);
		}//fin if
	}//fin initialiserActivite
	
	/**
	 * Effectue les contrôles de saisie avant de modifier le client dans la BDD
	 * @throws Lève une exception si une des saisies est incorrecte ou que la modification du client a échoué [Exception]
	 */
	protected void enregistrerModifications()
	{
		try {
			Log.d("Étape", "~ Vérification des champs vides");
			checkChampsVides();
			
			Log.d("Étape", "~ Vérification du relevé en fonction de la situation");
			//On va tester si le relevé est bien supérieur à l'ancien, excepté si la situation est 1, 5 ou 6
			if(Integer.valueOf(edtSituation.getText().toString()) != 1 && Integer.valueOf(edtSituation.getText().toString()) != 5 && Integer.valueOf(edtSituation.getText().toString()) != 6)
			{
				if(Double.valueOf(edtReleve.getText().toString()) < clientActuel.getAncienReleve())
				{
					throw new Exception("En vue de la situation du client, son nouveau relevé ne peut être inférieur à l'ancien !", new Throwable("releveInferieur"));
				}//fin if
			}//fin if
			
			//S'il n'y a pas d'erreurs de saisie, on effectue les modifications
			clientActuel.setDernierReleve(Double.valueOf(edtReleve.getText().toString()));
			clientActuel.setDateDernierReleve(Client.getDateDuJour());
			clientActuel.setSituation(Integer.valueOf(edtSituation.getText().toString()));
			
			//On lance l'activité permettant de faire signer le client
			Intent intentFaireSigner = new Intent(this, FaireSignerActivity.class);
			intentFaireSigner.putExtra("identifiant", clientActuel.getIdentifiant());
			intentFaireSigner.putExtra("detailClient", "Client : " + clientActuel.getIdentifiant() + " - " + clientActuel.getNom() + " " + clientActuel.getPrenom());
			intentFaireSigner.putExtra("detailCompteur", "Compteur : " + clientActuel.getIdCompteur() + " - Relevé : " + clientActuel.getDernierReleve() + " - Date : " + clientActuel.getDateDernierReleve());
			startActivityForResult(intentFaireSigner, 0);
		} catch (Exception e) {
			Log.d("Étape", "~ Exception : " + e.getMessage());
			Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
		}//fin catch
	}//fin enregistrerModifications
	
	/**
	 * Vérifie que les zones de saisies ne sont pas vides. Si elles le sont, une exception est levée.
	 * @throws Lève une exception si l'un des champ est vide [Exception]
	 */
	protected void checkChampsVides() throws Exception
	{
		//Champ "Relevé"
		if(edtReleve.getText().toString().equals(""))
		{
			Log.d("Étape", "~ Champ \"Relevé\" vide");
			throw new Exception("Veuillez remplir le champ \"Relevé\" !", new Throwable("emptyFieldError"));
		}//fin if
		
		//Champ "Situation"
		if(edtSituation.getText().toString().equals(""))
		{
			Log.d("Étape", "~ Champ \"Situation\" vide");
			throw new Exception("Veuillez remplir le champ \"Situation\" !", new Throwable("emptyFieldError"));
		}//fin if
	}//fin checkChampsVides
}//fin classe