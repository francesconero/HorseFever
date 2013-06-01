package it.polimi.ingegneriaDelSoftware2013.horseFever_jacopo.iamele_francesco.nero.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton che racchiude tutte le proprietà caricate dai vari file di configurazione.
 * Si può accedere alle varie classi di proprietà ({@link Properties}) tramite dei getter pubblici.
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
		loadProperties("src/main/resources/config/net-config.xml", netProperties);
		loadProperties("src/main/resources/config/server-config.xml", serverProperties);
		loadProperties("src/main/resources/config/client-config.xml", clientProperties);
		loadProperties("src/main/resources/config/gioco-config.xml", giocoProperties);
	}

	private void loadProperties(String file, Properties properties) {
		FileInputStream fis = null;	
		
		try {
			fis = new FileInputStream(file);			
		} catch (FileNotFoundException e) {
			if(e!=null){
				e.printStackTrace();
				System.exit(-1);
			}
		}
		
		try {
			properties.loadFromXML(fis);
		} catch (IOException e) {
			e.printStackTrace();
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					System.exit(-1);
				}
			}
			System.exit(-1);
		}
		
		if(fis!=null){
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	public static Configurazioni getInstance(){
		return CONFIGURAZIONI;
	}

	/**
	 * @return le proprietà riguardanti solo il server
	 */
	public Properties getServerProperties() {
		return this.serverProperties;
	}
	
	/**
	 * @return le proprietà riguardanti solo il client
	 */
	public Properties getClientProperties() {
		return this.clientProperties;
	}
	
	/**
	 * @return le proprietà generali riguardanti il networking del gioco
	 */
	public Properties getNetProperties() {
		return this.netProperties;
	}
	
	/**
	 * @return le proprietà dello svolgimento del gioco Horse Fever
	 */
	public Properties getGiocoProperties() {
		return this.giocoProperties ;
	}

}
