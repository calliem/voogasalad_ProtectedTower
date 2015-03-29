package authoring.environment;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//this may not be necessary

public class Tile {
	
	private ImageView myImage;
	private boolean isPath;
	private double mySize;
	
	public Tile(ImageView image, double tileSize) {
		mySize = tileSize;
		myImage = new ImageView(new Image("/resources/white_square.png"));
		myImage.setFitWidth(tileSize);
		myImage.setFitHeight(tileSize);
		isPath = false;
	}
	
	public void setImage(ImageView image) {
		myImage = image;
	}
	
	//this may not be necessary if the 2D array will update itself
	public void setSize(double size){
		mySize = size;
		myImage.setFitWidth(size);
		myImage.setFitHeight(size);
	}
	
	public void setActivePath(){
		myImage.setOpacity(0.2);
		isPath = true;
	}
	
	public void setInactivePath(){
		myImage.setOpacity(1);
		isPath = false;
	}
	
	public boolean isActivePath(){
		return isPath;
	}

}
