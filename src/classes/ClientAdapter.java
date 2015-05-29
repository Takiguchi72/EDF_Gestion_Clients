package classes;

import java.util.List;
import org.btssio.edf_florian.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClientAdapter extends BaseAdapter {
	/* *********************
	 * 	A T T R I B U T S  *
	 ***********************/
	private List<Client> listeClients;
	private LayoutInflater layoutInflater;
	private class ViewHolder {
		TextView textViewIdentifiant;
		TextView textViewNom;
		TextView textViewPrenom;
		TextView textViewTelephone;
		TextView textViewAdresse;
		TextView textViewCodePostal;
		TextView textViewVille;
	}//fin ViewHolder
	
	/* *****************************
	 * 	C O N S T R U C T E U R S  *
	 *******************************/
	/**
	 * Instancie un objet de la classe ClientAdapter
	 * @param ctx [Contexte]
	 * @param La liste de clients [List<Client>]
	 */
	public ClientAdapter(Context ctx, List<Client> listeClients) {
		layoutInflater  	= LayoutInflater.from(ctx);
		this.listeClients	= listeClients;
	}//fin ClientAdapter

	/* *******************
	 *  M E T H O D E S  *
	 *********************/
	/**
	 * Ajoute un client à la liste
	 * @param Le nouveau client à ajouter [Client]
	 */
	public void add(Client unClient)
	{
		listeClients.add(unClient);
	}//fin add
	
	/**
	 * Retourne le nombre de clients dans la liste
	 * @return Taille de la liste [Integer]
	 */
	@Override
	public int getCount() {
		return listeClients.size();
	}

	/**
	 * Retourne un client depuis la liste, en fonction de son index dans la liste
	 * @return Le client [Object]
	 */
	@Override
	public Object getItem(int arg0) {
		return listeClients.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	/**
	 * Initialise le viewHolder à partir du client à la position correspondant au 1er paramètre, dans la liste de clients
	 * @param La position du client dans la liste [Integer]
	 * @param L'élément graphique qui va contenir les informations du client [View]
	 * @return La vue initialisée à partir des données du client [View]
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		//Si la vue passée en paramètres n'est pas instanciée, on va l'instancier à partir du fichier xml "vue_client_de_liste_clients"
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.vue_client_de_liste_clients, null);
			holder.textViewIdentifiant  = (TextView) convertView.findViewById(R.id.vueClient_txvIdentifiant);
			holder.textViewNom			= (TextView) convertView.findViewById(R.id.vueClient_txvNom);
			holder.textViewPrenom		= (TextView) convertView.findViewById(R.id.vueClient_txvPrenom);
			holder.textViewTelephone	= (TextView) convertView.findViewById(R.id.vueClient_txvTel);
			holder.textViewAdresse		= (TextView) convertView.findViewById(R.id.vueClient_txvAdresse);
			holder.textViewCodePostal	= (TextView) convertView.findViewById(R.id.vueClient_CodePostal);
			holder.textViewVille		= (TextView) convertView.findViewById(R.id.vueClient_Ville);
		}//fin if
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}//fin else
		
		//On remplit les TextView avec les données du client à la position correspondant au 1er paramètre, dans la liste de clients
		holder.textViewIdentifiant	.setText(listeClients.get(position).getIdentifiant());
		holder.textViewNom			.setText(listeClients.get(position).getNom());
		holder.textViewPrenom		.setText(listeClients.get(position).getPrenom());
		holder.textViewTelephone	.setText(formaterTelephone(listeClients.get(position).getTelephone()));
		holder.textViewAdresse		.setText(listeClients.get(position).getAdresse());
		holder.textViewCodePostal	.setText(listeClients.get(position).getCodePostal());
		holder.textViewVille		.setText(listeClients.get(position).getVille());
		convertView.setTag(holder);
		
		//On va colorer le fond de la vue en fonction de la position pour faire une vue grise sur deux
		if(position % 2 == 0){
			convertView.setBackgroundColor(Color.rgb(238, 233, 233));
		}//fin if
		else {
			convertView.setBackgroundColor(Color.rgb(250, 250, 250));
		}//fin else
		return convertView;
	}//fin getView
	
	/**
	 * Formate un numéro de téléphone, pour séparrer tous les 2 chiffres par un point
	 * 
	 * Exemple : 0123456789 => 01.23.45.67.89
	 * @param Le numéro de téléphone à formater [String]
	 * @return Le numéro de téléphone formaté [String]
	 */
	public String formaterTelephone(String numTel)
	{
		return String.format("%s.%s.%s.%s.%s",  numTel.substring(0, 2),
												numTel.substring(2, 4),
												numTel.substring(4, 6),
												numTel.substring(6, 8),
												numTel.substring(8, 10));
	}//fin formaterTelephone
}//fin classe