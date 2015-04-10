package authoringEnvironment.setting;

import imageselectorTEMP.GraphicFileChooser;
import java.io.File;

import protectedtower.Main;

/**
 * 
 * @author Kevin He
 * @author Johnny
 *
 */
public class FileNameSetting extends Setting {
	private GraphicFileChooser spriteFileChooser;

	public FileNameSetting(String paramName, String defaultVal) {
		super(paramName, defaultVal);
	}

	@Override
	protected void setupInteractionLayout() {
		spriteFileChooser = new GraphicFileChooser("Select a File...", null);
		// spriteFileChooser.setAdditionalOptions(true);
		spriteFileChooser.addExtensionFilter("xml");

		this.getChildren().add(spriteFileChooser);
	}

	@Override
	public boolean parseField() {
		dataAsString = spriteFileChooser.getSelectedFileNameProperty()
				.getValue();
		if (dataAsString == null) {
			displayErrorAlert("Please choose a file!");
			return false;
		}
		hideErrorAlert();
		return true;
	}

	@Override
	public boolean processData() {
		return parseField();
	}

	public String getParameterValue() {
		return dataAsString;
	}

	@Override
	public void displaySavedValue() {

	}
}
