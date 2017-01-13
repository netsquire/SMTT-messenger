package mtt.smtt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesManagement {
	
	Properties properties;
	
	public PropertiesManagement(String propFileName) {
		properties = new Properties();		
		try {
			//InputStream input = getClass().getClassLoader().getResourceAsStream(propFileName); //new FileInputStream(propFileName); 			
			//System.out.println("   ...trying to open: " + propFileName);
			InputStream input = new FileInputStream(propFileName); 
			//File file = new File(propFileName);
			
			properties.load(input);
			dump();
			
		} catch ( IOException | NullPointerException e) { 
			System.out.println("   Version: 0.0.1" );
			System.out.println("   Failed to open: " + propFileName);
			//e.printStackTrace();	
			}
	}
	
	private Properties getProperties(){
		return properties;
	}
	
	public String get(String key){
		String ret = getProperties().get(key).toString();
		//System.out.println("          requested key -> " + key + " [ " + ret + " ]");
		return ret;
	}
	
	public int getInteger(String key){
		int ret = Integer.parseInt( getProperties().get(key).toString() );
		//System.out.println("          requested key -> " + key + " [ " + ret + " ]");
		return ret;
	}
	
	public void dump() throws IOException{
		dump(properties);
	}
	
	public void dump(Properties props) throws IOException {		
		System.out.println("---- ");
		Iterator<Object> it = props.keySet().iterator();
		while(it.hasNext()){
			Object key = it.next();
			//System.out.println(key + " == " + props.get(key));
		}
		//System.out.println("---- ");
	}
	
	/*
	void testFillUp(String propFileName) throws IOException {
				Properties properties = new Properties();						
				properties.setProperty("Hello", "World");
				properties.setProperty("Michael", "Dumpkompf");
				properties.setProperty("Ioseph", "Scweik");
				properties.setProperty("Peace", "World");
				
				Writer output = new PrintWriter(propFileName);
				properties.store(output, "Everything is fine!");				
				System.out.println("---- ");		
		}
	*/
}
