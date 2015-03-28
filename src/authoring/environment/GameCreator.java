package authoring.environment;

import java.io.File;
import java.util.ResourceBundle;
import java.util.Set;

public class GameCreator {
	
	private static final String userDataPackage = System.getProperty("user.dir").concat("\\src\\userData");
	public static final ResourceBundle paramLists = ResourceBundle.getBundle("resources/part_parameters");
	private static Set<String> dirsToBeCreated = paramLists.keySet();
	
	
	public static void createNewGameFolder(String gameName){
		String gameDir = userDataPackage.concat("\\").concat(gameName);
		new File(gameDir).mkdirs();
		for(String dir : dirsToBeCreated)
			new File(gameDir.concat("\\").concat(dir)).mkdirs();
	}


}
