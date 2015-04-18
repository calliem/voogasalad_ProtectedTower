/**
 * @author Johnny Kumpf
 * @author Megan Gutter
 */
package authoringEnvironment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLWriter {

    private static XStream stream = new XStream(new DomDriver());

    /**
     * Specify a root directory, and create all directories necessary for that to exist.
     * Then, specify subfolders you want to create in that directory, and they will be created.
     * @param root The initial directory to create, and where you want the subdirectories created
     * @param newDirectories The list of subdirectories you want to create
     * @return The root directory where the subdirectories were created
     * Example: createDirectories("C:/Users/Johnny/JohnnysTowerGame", "new String[] {"Towers", "Units", "Maps"})
     * will create the folder JohnnysTowerGame, and inside that folder, create three folders: Towers, Units, and Maps
     */
    public static String createDirectories(String root, Collection<String> newDirectories){
        File f = new File(root);
        f.mkdirs();
        for(String s: newDirectories){
            new File(root + "/" + s).mkdirs();
        }
        return root;
    }
    /**
     * 
     * @param o
     *            Object to be written to a file in XML format
     * @param fileName
     *            Name of the resulting file
     * @param dir
     *            Directory leading to the resulting file
     * @return Exact location where the file was saved
     */
    public static String toXML(Object o, String fileName, String dir) {
        if (fileName.indexOf(".") == -1)
            fileName = fileName + ".xml";
        File partFile = new File(dir, fileName);
        System.out.println("dir: " + dir);
        System.out.println("filename: " + fileName);
        try {
            PrintStream out = new PrintStream(partFile);
            out.println(stream.toXML(o));
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // System.out.println(fileName + " saved at: " + dir);
        return dir + "/" + fileName;
    }

    /**
     * 
     * @param The
     *            object to be saved in XML format
     * @param fileName
     *            The name of the resulting file NOTE: a sub-directory
     *            \XML_Files will be created from the current directory, and the
     *            file will be saved their.
     * @return Directory where the file was saved
     */
    public static String toXML(Object o, String fileName) {
        String saveDir = System.getProperty("user.dir") + "\\XML_Files";
        new File(saveDir).mkdirs();
        return toXML(o, fileName, saveDir);
    }

    /**
     * 
     * @param o
     *            The object to be saved in XML format NOTE: \XML_Files
     *            directory will be created and used File name will be:
     *            ClassName_HashCode
     * @return Directory where the file was saved
     */
    public static String toXML(Object o) {
        String fileName = o.getClass().toString() + "_" + o.hashCode();
        return toXML(o, fileName);
    }

    /**
     * 
     * @param dir
     *            The directory from which you want to read an object
     * @return Object stored in the file at that directory
     */
    public static Object fromXML(String dir) {
        return stream.fromXML(new File(dir));
    }

    public static void deleteFile(String filePath){
        new File(filePath).delete();
    }
    
    public static Object fromXML(File f){
        return stream.fromXML(f);
    }
}
