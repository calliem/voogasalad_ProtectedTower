package util.misc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetHandler {

	/**
     * because String[]'s don't have .contains
     * @param s array to be converted to List<String>
     * @return s in List form
     */
    public static List<String> listFromArray(String[] s){
        List<String> l = new ArrayList<String>();
        for(String word : s)
            l.add(word);
        return l;
    }
    
    /**
     * Takes a set of strings and makes them all lowercase
     * @param s the set of strings to make lowercase
     * @return the set will all characters lowercase
     */
    public static Set<String> setToLowerCase(Set<String> s){
    	Set<String> lower = new HashSet<String>();
    	for(String word : s)
    		lower.add(word.toLowerCase());
    	return lower;
    }
}
