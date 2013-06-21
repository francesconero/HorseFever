package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton che racchiude tutte le proprieta' caricate dai vari file di configurazione.
 * Si può accedere alle varie classi di proprieta' ({@link Properties}) tramite dei getter pubblici.
 * 
 * @author Francesco
 *
 */
public final class Configurazioni {

	private static final Configurazioni CONFIGURAZIONI = new Configurazioni();
	private Properties netProperties = new Properties();
	private Properties serverProperties = new Properties();
	private Properties clientProperties = new Properties();
	private Properties giocoProperties = new Properties();

	private Configurazioni() {
		loadProperties("resources/config/net-config.xml", netProperties);
		loadProperties("resources/config/server-config.xml", serverProperties);
		loadProperties("resources/config/client-config.xml", clientProperties);
		loadProperties("resources/config/gioco-config.xml", giocoProperties);
	}

	private void loadProperties(String file, Properties properties) {
		FileInputStream fis = null;	
		
		try {
			fis = new FileInputStream(file);			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		try {
			properties.loadFromXML(fis);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		if(fis!=null){
			try {
				fis.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static Configurazioni getInstance(){
		return CONFIGURAZIONI;
	}

	/**
	 * @return le proprieta' riguardanti solo il server
	 */
	public Properties getServerProperties() {
		return this.serverProperties;
	}
	
	/**
	 * @return le proprieta' riguardanti solo il client
	 */
	public Properties getClientProperties() {
		return this.clientProperties;
	}
	
	/**
	 * @return le proprieta' generali riguardanti il networking del gioco
	 */
	public Properties getNetProperties() {
		return this.netProperties;
	}
	
	/**
	 * @return le proprieta' dello svolgimento del gioco Horse Fever
	 */
	public Properties getGiocoProperties() {
		return this.giocoProperties ;
	}

}
