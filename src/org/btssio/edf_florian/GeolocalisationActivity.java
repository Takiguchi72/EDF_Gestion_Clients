package org.btssio.edf_florian;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class GeolocalisationActivity extends Activity implements LocationListener{
	protected GoogleMap googleMap;
	protected LocationManager locationManager;
	protected String provider, adresseClient;
	protected LatLng positionClient, positionAgent;
	protected boolean   reussiGeolocalisationAgent = false,
						reussiGeolocalisationClient = false;
	protected LatLngBounds.Builder builder = new LatLngBounds.Builder();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geolocalisation);
		
		//Récupération de l'adresse du client
		adresseClient = this.getIntent().getExtras().getString("adresseClient");
		
		//On récupère la position de l'utilisateur via la géolocalisation
		recupPositionAgent();
		
		//On récupère la position du client
		recupPositionClient();
		
		//On affiche la carte avec le pin correspondant à la posititon de l'agent et le pin du client
		afficheCarte();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.geolocalisation, menu);
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
	
	/**
	 * Récupère la position géolocalisée de l'utilisateur via le système de géolocalisation et met à jour "reussiGeolocalisationAgent"
	 */
	public void recupPositionAgent()
	{
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		/*
		 * Divers critères de selection du provider
		 * criteria.setPowerRequirement(Criteria.POWER_LOW);
		 * criteria.setAccuracy(Criteria.ACCURACY_FINE);
		 * criteria.setCostAllowed(true);
		 * criteria.setSpeedRequired(false);
		 */
		
		//On récupère le meilleur fournisseur de géolocalisation
		provider = locationManager.getBestProvider(criteria, false);
		if (provider == null || provider.equals(""))
		{
			Log.d("Étape", "~ Echec de l'utilisation du service de géolocalisation (Est-il activé ?)");
			Toast.makeText(this, "Erreur",Toast.LENGTH_LONG).show();
		}//fin if
	}//fin recupPositionAgent
	
	/**
	 * Détermine la position du client à partir de son adresse et met à jour "reussiGeolocalisationClient"
	 */
	public void recupPositionClient()
	{
		//Récupération de la position de l'agent à partir du service de géolocalisation
		locationManager.requestLocationUpdates(provider, 20000, 0, this);
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null)
		{
			positionAgent = new LatLng(location.getLatitude(),location.getLongitude());
			reussiGeolocalisationAgent = true;
		}//fin if
		else
		{
			Log.d("Étape", "~ Service de géolocalisation désactivé");
			Toast.makeText(this, "Veuillez activer le service de géolocalisation svp !",Toast.LENGTH_LONG).show();
		}//fin else
		
		//On génère la localisation du client à partir de son adresse
		Geocoder fwdGeocoder = new Geocoder(this, Locale.FRANCE);
		List<Address> locations = null;
		try {
			locations = fwdGeocoder.getFromLocationName(adresseClient, 10);
		} catch (IOException e) {
			Log.d("Étape", "~ Services de données mobiles désactivé");
			Toast.makeText(this, "Veuillez activer le services de données mobiles svp !",Toast.LENGTH_LONG).show();
		}//fin catch
		
		//Si on a pas réussi à récupérer la localisation du client, on affiche qu'il est inconnu sur la carte
		if ((locations == null) || (locations.isEmpty()))
		{
			//"Adresse client inconnu !"
			Log.d("Étape", "~ Localisation du client impossible");
			Toast.makeText(this, "Impossible de localiser le client !",Toast.LENGTH_LONG).show();
		}//fin if
		//Sinon, on va enregistrer sa localisation
		else
		{
			Log.d("Étape", "~ Localisation du client !");
			positionClient = new LatLng(locations.get(0).getLatitude(),locations.get(0).getLongitude());
			reussiGeolocalisationClient = true;
		}//fin else
	}//fin recupPositionClient
	
	/**
	 * Affiche la carte en positionnant les marqueurs de l'utilisateur et du client
	 */
	public void afficheCarte()
	{
		//On récupère la carte
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		//On y affiche le marqueur du client
		if (reussiGeolocalisationClient)
		{
			googleMap.addMarker(new MarkerOptions().position(positionClient).title("Client").snippet("Point de rendez vous prochain client")
									.icon(BitmapDescriptorFactory.fromResource(R.drawable.grnpushpin)));
			builder.include(positionClient);
		}//fin if
		
		//On y affiche le marqueur de l'utilisateur
		if (reussiGeolocalisationAgent) {
			googleMap.addMarker(new MarkerOptions().position(positionAgent).title("Ma position"));
			builder.include(positionAgent);
		}//fin if
		
		//On zoom sur la position des deux clients
		if (reussiGeolocalisationClient && reussiGeolocalisationAgent) {
			googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),
																	 this.getResources().getDisplayMetrics().widthPixels,
																	 this.getResources().getDisplayMetrics().heightPixels
																	 ,210));
		}//fin if
	}//fin afficherCarte
	
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}//fin classe