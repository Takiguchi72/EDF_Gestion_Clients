package org.btssio.edf_florian;

import classes.Agent;
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
import android.widget.Toast;
import static util.FonctionString.md5;;

public class AuthentificationActivity extends Activity implements OnClickListener{
	private EditText edtIdentifiant;
	private EditText edtMotDePasse;
	private Button btnAnnuler;
	private Button btnValider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentification);
		
		edtIdentifiant = (EditText) this.findViewById(R.id.authentificationAct_edtIdentifiant);
		edtMotDePasse  = (EditText) this.findViewById(R.id.authentificationAct_edtPasswd);
	
		btnAnnuler = (Button) findViewById(R.id.authentificationAct_btnAnnuler);
		btnAnnuler.setOnClickListener(this);
		
		btnValider = (Button) findViewById(R.id.authentificationAct_btnValider);
		btnValider.setOnClickListener(this);
	}//fin onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.authentification, menu);
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
		Log.d("Étape", "~ Click détecté...");
		switch (v.getId())
		{
		case R.id.authentificationAct_btnAnnuler:
			Log.d("Étape", "~ Clic sur le bouton Annuler");
			Intent returnIntent = new Intent();
			
			setResult(RESULT_CANCELED, returnIntent);
			
			finish();
			break;
		case R.id.authentificationAct_btnValider:
			Log.d("Étape", "~ Clic sur le bouton Valider");
			try {
				Log.d("Étape", "~ Vérification des zones de saisies");
				//On vérifie que les champs ne sont pas vides
				verifierChampsVides();
				
				//On va retourner les saisies à l'activité principale
				Intent resultIntent = new Intent();
				resultIntent.putExtra("identifiant", edtIdentifiant.getText().toString());
				resultIntent.putExtra("motDePasse", md5(edtMotDePasse.getText().toString()));
				setResult(RESULT_OK, resultIntent);
				//On termine cette activité
				finish();
			} catch (Exception e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}//fin catch
			break;
		}//fin switch
	}//fin onClick
	
	/**
	 * Vérifie que les deux zones de saisies ne sont pas vide
	 * @throws Lève une exception si l'un des champ est vide [Exception] 
	 */
	private void verifierChampsVides() throws Exception
	{
		String identifiant = edtIdentifiant.getText().toString();
		if(edtIdentifiant.getText().toString().equals(""))
		{
			Log.d("Étape", "~ Champ \"Identifiant\" vide");
			throw new Exception("Veuillez remplir le champ \"Identifiant\" !", new Throwable("emptyFieldError"));
		}//fin if
		
		if(edtMotDePasse.getText().toString().equals(""))
		{
			Log.d("Étape", "~ Champ \"Mot de passe\" vide");
			throw new Exception("Veuillez remplir le champ \"Mot de passe\" !", new Throwable("emptyFieldError"));
		}//fin if
	}//fin verifierChampsVides
}//fin classe
