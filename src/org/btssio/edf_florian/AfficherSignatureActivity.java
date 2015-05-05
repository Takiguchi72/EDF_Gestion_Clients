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
	protected LinearLayout linearLayout;
	protected Button btnRetour;
	protected Signature signature;
	
	public class Signature extends View {
		// variables nécessaire au dessin
		private Canvas canvas;
		private Bitmap bitmap;
		
		public Signature(Context context, AttributeSet attrs) {
			super(context, attrs);
			this.setBackgroundColor(Color.WHITE);
		}//fin signature

		//gestion du dessin
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
				byte[] encodeByte = Base64
							.decode(encodedString, Base64.DEFAULT);
				bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
						encodeByte.length);
				bitmap = bitmap.copy(bitmap.getConfig(), true);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "error dessine",
							Toast.LENGTH_LONG).show();
			}
			canvas = new Canvas(bitmap);
			this.draw(canvas);
		}//fin dessine
	}//fin Signature
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afficher_signature);
		
		Log.d("Étape", "~ Création de la signature");
		signature = new Signature(this, null);
		
		//On initialise les composants de l'activité
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.afficher_signature, menu);
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
		//Si l'utilisateur a cliqué sur le bouton "Retour"
		if(v.getId() == R.id.affichSign_btnRetour)
		{
			Log.d("Étape", "~ Clic sur le bouton Retour détecté !");
			//On termine l'activité
			finish();
		}//fin if
	}//fin onClick
}//fin classe
