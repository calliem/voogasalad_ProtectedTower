// This entire file is part of my masterpiece.
// Qian Wang

package util.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


/**
 * This utility class provides ways to load files into data structures.
 * 
 * @author Qian Wang
 *
 */
public class PropertiesFileLoader {

    /**
     * Loads the contents of a properties file to a list and returns the list. Each line of the
     * specified file will be a separate entry in a list. The order of the list mirrors that of the
     * file.
     * 
     * @param fileLocation String location of the file to load
     * @return List<String> with each line of the file as an element in the list
     */
    public static List<String> loadToList (String fileLocation) {
        List<String> list = new ArrayList<>();
        list = (List<String>) loadToCollection(fileLocation, list);
        return list;
    }

    /**
     * Loads the contents of a properties file to a set and returns the set. Each line of the
     * specified file will be a separate entry in a set.
     * 
     * @param fileLocation String location of the file to load
     * @return Set<String> with each line of the file as an element in the set
     */
    public static Set<String> loadToSet (String fileLocation) {
        Set<String> set = new HashSet<>();
        set = (Set<String>) loadToCollection(fileLocation, set);
        return set;
    }

    /**
     * Helper method to retrieve the contents of a file and add them to some specified Collection.
     * Data is read as strings.
     * 
     * @param fileLocation String location of the file to load
     * @param collection Collection object with which to store data
     * @return Collection<String> object where each element contains one line of data
     */
    private static Collection<String> loadToCollection (String fileLocation,
                                                        Collection<String> collection) {
        Scanner s;
        try {
            s = new Scanner(new File(fileLocation));
            while (s.hasNextLine()) {
                collection.add(s.nextLine());
            }
            s.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("File was not found");
        }
        return collection;
    }
}
