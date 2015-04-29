package util.misc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetHandler {

	/**
	 * because String[]'s don't have .contains
	 * 
	 * @param s
	 *            array to be converted to List<String>
	 * @return s in List form
	 */
	public static List<String> listFromArray(String[] s) {
		List<String> l = new ArrayList<String>();
		for (String word : s)
			l.add(word);
		return l;
	}

	/**
	 * Takes a set of strings and makes them all lowercase
	 * 
	 * @param s
	 *            the set of strings to make lowercase
	 * @return the set will all characters lowercase
	 */
	public static Set<String> setToLowerCase(Set<String> s) {
		Set<String> lower = new HashSet<String>();
		for (String word : s)
			lower.add(word.toLowerCase());
		return lower;
	}
	
	public static Set<String> setFromList(List<String> s){
	    Set<String> toReturn = new HashSet<String>();
	    for(String entry : s)
	        toReturn.add(entry);
	    return toReturn;
	}

	/**
	 * gets rid off all characters before a '.', including the '.', in each
	 * element of the list
	 * 
	 * @param toTrim
	 *            the list to be trimmed
	 * @return the trimmed list
	 */
	public static List<String> trimBeforeDot(List<String> toTrim) {
		List<String> trimmed = new ArrayList<String>();
		for (int i = 0; i < toTrim.size(); i++) {
			String current = toTrim.get(i);
			trimmed.add(current.substring(current.indexOf(".") + 1,
					current.length()));
		}
		return trimmed;
	}
}
