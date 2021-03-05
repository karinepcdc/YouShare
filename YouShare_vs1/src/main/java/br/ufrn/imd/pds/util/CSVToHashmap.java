package br.ufrn.imd.pds.util;

import java.util.List;

import br.ufrn.imd.pds.business.Item;
import br.ufrn.imd.pds.business.User;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class CSVToHashmap {
	private userHashmap userCSV;
	private List<User> listUsers;
	private itemHashmap itemCSV;
	private List<Item> listItems;
	
	
	public CSVToHashmap() {
		userCSV = new userHashmap();
		listUsers = CSVHandler.csvToUser();
		
		itemCSV = new itemHashmap();
		listItems = CSVHandler.csvToItem();
	}
	
	public void createUserHashmapFromCSV( int minLength ) {
		System.out.println("Criando HashMap de boatos.csv...");
		HashMap<String,Noticia> temp = new HashMap<String,Noticia>();
		processarTextos(minLength);
		
		for ( Noticia n : listaNoticias ) {
			try {
				temp.put( SHAConverter.stringToSha1( n.getTextoProcessado() ), n );
			} catch ( NoSuchAlgorithmException e ) {
				e.printStackTrace();
			}
		}
		
		boatosCSV.setMapaNoticias(temp);
		System.out.println("HashMap boatosCSV criado com sucesso!");
	}	
	
	public void processarTextos( Integer minLength ) {
		for ( Noticia n : listaNoticias ) {
			n.setTextoProcessado( StringProcessor.processString( n.getConteudo(), minLength ) );
		}
	}
	
	public void imprimeBoatosCSV() {
		for ( String key : boatosCSV.getMapaNoticias().keySet() ) {
		    Noticia value = boatosCSV.getMapaNoticias().get(key);
		    System.out.println( "CHAVE:" + key + ", VALOR: " + value.getTextoProcessado() );
		}
	}

}
