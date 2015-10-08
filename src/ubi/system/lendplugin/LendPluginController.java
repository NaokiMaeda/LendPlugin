package ubi.system.lendplugin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LendPluginController implements Initializable{
	@FXML	private	ImageView	cameraImage;
	@FXML	private	Label		qrResultLabel;
	@FXML	private	Label		felicaResultLabel;
	@FXML	private	Button		executeButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("../../../noimage.Jpg"));
		setCameraImage(image);
		executeButton.setOnAction(new ExecuteButtonEventListener());
	}

	public void setCameraImage(Object object){
		cameraImage.setImage((Image) object);
	}
	
	public void setQRResult(String msg){
		qrResultLabel.setText(msg);
	}
	
	public void setFelicaResult(String msg){
		felicaResultLabel.setText(msg);
	}
}
