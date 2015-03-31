package authoring.environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLWriter {
	
	private static XStream stream = new XStream(new DomDriver());
	
	/**
	 * 
	 * @param o Object to be written to a file in XML format
	 * @param fileName Name of the resulting file
	 * @param dir Directory leading to the resulting file
	 */
	public static void toXML(Object o, String fileName, String dir){
		if(!fileName.substring(fileName.length() - 4, fileName.length()).equals(".xml"))
			fileName = fileName + ".xml";
		File partFile = new File(dir, fileName);
		try {
			PrintStream out = new PrintStream(partFile);
			out.println(stream.toXML(o));
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void toXML(Object o, String fileName){
		String saveDir = System.getProperty("user.dir") + "\\XML_Files";
		new File(saveDir).mkdirs();
		toXML(o, fileName, saveDir);
	}
	
	public static void toXML(Object o){
		String fileName = o.getClass().toString() + "_" + o.hashCode();
		toXML(o, fileName);
	}
	
	public static Object fromXML(File f){
		return stream.fromXML(f);
	}
	
	public static Object fromXML(String dir){
		return stream.fromXML(new File(dir));
	}
	
	/*
	//from stack overflow
		private static String readFile(String fileName) throws IOException {
		    BufferedReader reader = new BufferedReader(new FileReader (fileName));
		    String         line = null;
		    StringBuilder  xmlText = new StringBuilder();
		    String         ls = System.getProperty("line.separator");

		    while((line = reader.readLine()) != null) {
		        xmlText.append(line).append(ls);
		    }
		    reader.close();
		    return xmlText.toString();
		}
		*/

}
