package org.btssio.edf_florian;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AfficherSignatureActivity extends Activity implements OnClickListener{
	/* *********************
	 * 	A T T R I B U T S  *
	 ***********************/
	protected LinearLayout linearLayout;
	protected Button btnRetour;
	protected Signature signature;
	public class Signature extends View {
		// variables nécessaire au dessin
		private Canvas canvas;
		private Bitmap bitmap;
		
		/**
		 * Initialise la signature en fonction des paramètres
		 * @param L'activité utilisant la signature [Context]
		 * @param attrs
		 */
		public Signature(Context context, AttributeSet attrs) {
			super(context, attrs);
			this.setBackgroundColor(Color.WHITE);
		}//fin signature

		//gestion du dessin
		/**
		 * Affiche le dessin passé en paramètres
		 * @param Le canvas contenant l'image à afficher [Canvas]
		 */
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawBitmap(bitmap, 0, 0, null);
		}//fin onDraw
		
		/**
		 * Décode la signature qui est sous forme de chaîne codée en base 64 JPEG, en image pour pouvoir l'afficher à l'écran
		 * @param La signature encodée [String]
		 */
		public void dessine(String encodedString) {
			try {
				byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
				bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,encodeByte.length);
				bitmap = bitmap.copy(bitmap.getConfig(), true);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "error dessine",
							Toast.LENGTH_LONG).show();
			}
			canvas = new Canvas(bitmap);
			this.draw(canvas);
		}//fin dessine
	}//fin Signature
	
	/* *******************
	 *  M E T H O D E S  *
	 *********************/
	/**
	 * Initialise l'activité
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afficher_signature);
		
		Log.d("Étape", "~ Création de la signature");
		signature = new Signature(this, null);
		
		Log.d("Étape", "~ Initialisation des composants graphiques de l'activité");
		linearLayout = (LinearLayout) this.findViewById(R.id.affichSign_lytSignaure);
		btnRetour = (Button) this.findViewById(R.id.affichSign_btnRetour);
		btnRetour.setOnClickListener(this);
		
		Log.d("Étape", "~ Affectation de la signature au layout");
		//On affecte la signature au layout
		linearLayout.addView(signature, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		Log.d("Étape", "~ Affichage de la signature");
		//Puis on affiche la signature
		signature.dessine(this.getIntent().getExtras().getString("signature"));
	}//fin onCreate

	/**
	 * Initialise le menu de l'activité
	 * @param Le menu permettant d'initialiser celui de l'activité [Menu]
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.afficher_signature, menu);
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
		//Si l'utilisateur a cliqué sur le bouton "Retour"
		if(v.getId() == R.id.affichSign_btnRetour)
		{
			Log.d("Étape", "~ Clic sur le bouton Retour détecté !");
			//On termine l'activité
			finish();
		}//fin if
	}//fin onClick
}//fin classe
