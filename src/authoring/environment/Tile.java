package authoring.environment;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//this may not be necessary

//extends Node to allow tiles to be placed into a group
public class Tile extends ImageView{
	
	//private ImageView myImage;
	private boolean isPath;
	private double mySize;
	private Image myImage;
//	private static Image myPathImage = new Image("/resources/red_square.png"); //is this done properly if all tiles will have the same image for a path?
	
	
	public Tile(double tileSize) {
		mySize = tileSize;
		//myImage = new ImageView(new Image("/resources/white_square.png"));
		setImage(new Image("/resources/white_square.png"));
		setFitWidth(tileSize);
		setFitHeight(tileSize);
		isPath = false;
	}
	
	/*public void setImage(ImageView image) {
		myImage = image;
	}*/
	
	//this may not be necessary if the 2D array will update itself
	public void setSize(double size){
		mySize = size;
		setFitWidth(size);
		setFitHeight(size);
	}
	
	//active refers to if it is selected as part of a path
	public void setActiveTile(){
		setOpacity(0.2); //change image entirely 
		setStyle("-fx-base: #3c3c3c;");
		isPath = true;
	}
	
	public void setInactiveTile(){
		//myImage.setOpacity(1);
		setStyle(""); //will this properly remove all style elements
		isPath = false;
	}
	
	public boolean isActivePath(){
		return isPath;
	}

}
