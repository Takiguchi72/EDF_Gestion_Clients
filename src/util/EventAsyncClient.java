package util;

import java.util.ArrayList;
import classes.Client;

public interface EventAsyncClient {
	public void onTacheTerminee(ArrayList<Client> resultat);
	public void onTacheTerminee(Client resultat);
}//fin interface