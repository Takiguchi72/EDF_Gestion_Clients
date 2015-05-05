package org.btssio.edf_florian;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import classes.Client;
import dao.ClientDAO;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FaireSignerActivity extends Activity implements OnClickListener{
	protected LinearLayout linearLayout;
	protected Button btnAnnuler;
	protected Button btnEffacer;
	protected Button btnValider;
	protected Signature signature;
	
	public class Signature extends View {
		private Paint paint = new Paint();
		private Path path = new Path();// collection de l'ensemble des points sauvegardés lors des mouvements du doigt
		private Canvas canvas;
		private Bitmap bitmap;
		private String lig1, lig2;

		/**
		 * Constructeur par défaut
		 * @param context
		 * @param attrs
		 * @param Le récapitulatif du client. Ex: "Client : cli1 Nom Prénom" [String]
		 * @param Le récapitulatif du relevé du client. Ex: "Compteur n°0123456789 - Relevé : 1234.56 - Date : 01/02/03" [String]
		 */
		public Signature(Context context, AttributeSet attrs, String lig1, String lig2) {
			super(context, attrs);
			this.setBackgroundColor(Color.WHITE);
			paint.setAntiAlias(true); // empêche le scintillement gourmand en cpu et mémoire 
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(5f); //taille de la grosseur du trait en pixel
			paint.setTextSize(20);// taille du texte pur afficher les lignes
			this.lig1 = lig1;
			this.lig2 = lig2;
		}//fin constructeur

		/**
		 * Affiche le dessin en fonction du tracé de l'utilisateur
		 * @param Le canvas sur lequel l'utilisateur dessine [Canvas]
		 */
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			
			//On affiche le dessin en fonction du tracé de l'utilisateur
			paint.setStyle(Paint.Style.FILL);
			canvas.drawText(lig1, 20, 30, paint);
			canvas.drawText(lig2, 20, 60, paint);
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawPath(path, paint);
		}//fin onDraw
		
		/**
		 * Gère les évènements générés par le doigt sur la zone de dessin
		 * @param L'évènement capté
		 */
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			//On rend accessible le bouton Sauvegarder
			btnValider.setEnabled(true);
			
			//On récupère les coordonnées du doigt sur le dessin
			float eventX = event.getX();
			float eventY = event.getY();
			
			//En fonction de l'action (touche l'écran ; bouge le doigt ; relève le doigt ; etc...)
			switch (event.getAction()) {
				// Touche l'écran
				case MotionEvent.ACTION_DOWN:
					path.moveTo(eventX, eventY);
					return true;
				// Bouge le doigt
				case MotionEvent.ACTION_MOVE:
					path.lineTo(eventX, eventY);
					break;
				// Relève le doigt
				case MotionEvent.ACTION_UP:
					//On ne fait rien
					break;
				default:
					return false;
			}//fin switch
			invalidate();
			return true;
		}//fin onTouchEvent
		
		/**
		 * Réinitialise la zone de dessin et désactive le bouton "Sauvegarder"
		 */
		public void reinitialiser()
		{
			path.reset();
			invalidate();
			btnValider.setEnabled(false);
		}//fin reinitialiser
		
		/**
		 * Retourne la signature sous la forme d'une chaîne de caractère correspondant à l'encodage en JPEG de la signature
		 * @return La signature encodé en JPEG sous forme de chaîne de caractères [String]
		 */
		public String save()
		{
			String vretour;
			
			//Si le bitmap est vide, on va le créer en spécifiant ses dimensions
			if (bitmap == null)
			{
				bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(),
							Bitmap.Config.RGB_565);
			}//fin if
			
			try {
				//On récupère la signature dessinée
				canvas = new Canvas(bitmap);
				this.draw(canvas);
				
				//On spécifie l'image en JPEG, et on l'encore en base 64 
				ByteArrayOutputStream ByteStream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ByteStream);
				byte[] b = ByteStream.toByteArray();
				vretour = Base64.encodeToString(b, Base64.DEFAULT);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Erreur Sauvegarde", Toast.LENGTH_LONG).show();
				vretour = null;
			}//fin catch
			
			Log.d("Signature", vretour);
			return vretour;
		}//fin save
	}//fin Signature
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faire_signer);
		
		String detailClient   = this.getIntent().getExtras().getString("detailClient");
		String detailCompteur = this.getIntent().getExtras().getString("detailCompteur");
		
		Log.d("Étape", "~ Création de la signature");
		signature = new Signature(this, null, detailClient, detailCompteur);

		Log.d("Étape", "~ Initialisation des composants graphiques de l'activité");
		linearLayout = (LinearLayout) findViewById(R.id.faireSignAct_lytSignature);

		//On va ajouter un OnClickListener aux boutons
		btnAnnuler = (Button) findViewById(R.id.faireSignAct_btnAnnuler);
		btnAnnuler.setOnClickListener(this);

		btnEffacer = (Button) findViewById(R.id.faireSignAct_btnEffacer);
		btnEffacer.setOnClickListener(this);

		btnValider = (Button) findViewById(R.id.faireSignAct_btnValider);
		btnValider.setOnClickListener(this);

		//On désactive le bouton Valider
		btnValider.setEnabled(false);

		linearLayout.addView(signature, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}//fin onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.faire_signer, menu);
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
		Log.d("Étape", "~ Click sur un bouton");
		switch(v.getId())
		{
			case R.id.faireSignAct_btnAnnuler:
				Log.d("Étape", "~ Click sur Annuler détecté");
				finish();
				break;
			case R.id.faireSignAct_btnEffacer:
				Log.d("Étape", "~ Click sur Effacer détecté");
				signature.reinitialiser();
				break;
			case R.id.faireSignAct_btnValider:
				Log.d("Étape", "~ Click sur Valider détecté");
				//On retourne la signature qui a été tracée par le client, à l'activité ModificationClient
				Intent returnIntent = new Intent();
				
				returnIntent.putExtra("signatureBase64", signature.save());
				//On indique à l'activité appelante que l'activité s'est effectuée correctement
				setResult(RESULT_OK,returnIntent);
				//On termine l'activité ModificationClient
				finish();
				break;
		}//fin switch
	}//fin onClick
}//fin classe