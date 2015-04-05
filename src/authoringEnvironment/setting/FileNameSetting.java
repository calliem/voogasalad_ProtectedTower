package authoringEnvironment.setting;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 
 * @author Johnny
 *
 */
public class FileNameSetting extends Setting{

	public FileNameSetting(String paramName, String defaultVal){
		super(paramName, defaultVal);
	}

	public boolean parseField(){
		return new File(getDataAsString()).isFile();
	}

	public String getParameterValue(){
		return getDataAsString();
	}
}
