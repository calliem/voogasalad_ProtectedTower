package authoringEnvironment.objects;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * Creates a visual tile object and its actions when selected or deselected
 * @author Callie Mao
 *
 */
public class Tile extends Rectangle {

	// private ImageView myImage;
	private boolean isPath;
	private boolean isSelected;
	private double myTileSize;
	private int myColNum;
	private int myRowNum;
	private ArrayList<String> myTags;
	private Color myColor;

	// will have the same image for a path?
	// TODO: create a text box to set grid size and a slider to set tile size
	// that would only allow numbers in correct increments that would fit

	public Tile(double tileSize, int rowNum, int colNum) {
		myTileSize = tileSize;
		myColNum = colNum;
		myRowNum = rowNum;
		// setImage(new Image("/resources/white_square.png"));
		// setFitWidth(tileSize);
		// setFitHeight(tileSize);
		setFill(Color.WHITE);
		setWidth(tileSize);
		setHeight(tileSize);

		setTranslateX(colNum * tileSize);
		setTranslateY(rowNum * tileSize);
		
		isPath = false;
		isSelected = false;
	}
	
	public void addTag(String tag){
		myTags.add(tag);
	}
	
	//should only be able to remove already existing tags
	public void removeTag(String tag){
		myTags.remove(tag);
	}

	/*
	 * public void setImage(ImageView image) { myImage = image; }
	 */

	
	public void setTileSizeDynamically(double size) {
		setTileSize(size);
		setTranslateX(myColNum*size);
		setTranslateY(myRowNum*size);
	}
	// this may not be necessary if the 2D array will update itself
	private void setTileSize(double size) {
		setWidth(size);
		setHeight(size);
		myTileSize = size;
	}

	//selection stuff is all for pathing. Need separate methods for updating the tile
	// active refers to if it is selected as part of a path
	public void select() {
		if (!isSelected){
			setOpacity(0.2); // change image entirely
		}
		else{
			setOpacity(1);
		}
		isSelected = !isSelected;
	}
	
	//not sure if this below method belongs here
	public void dragSelect(){
		select();
		//TODO: make this work
	}

	/*public void setInactiveTile() {
		// myImage.setOpacity(1);
		setStyle(""); // will this properly remove all style elements
		isPath = false;
	}*/
	
	public boolean isSelected(){
		return isSelected;
	}

		//TODO: not sure if need below method
	public boolean isActivePath() {
		return isPath;
	}
	


}
