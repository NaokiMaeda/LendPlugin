package ubi.system.lendplugin;

import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import java.io.ByteArrayInputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import ubi.system.plugin.Plugin;

public class LendPlugin implements Plugin{
	//UI関係
	private	FXMLLoader					loader;
	private	GridPane						content;
	private	LendPluginController	controller;
	
	//Webカメラ関係
	private	VideoCapture				videoCapture;
	private	CaptureService			captureService;
	private	Mat								image;
	private	MatOfByte					buf;	
	private	String							qrResult;					//QRコード読み取り結果
	
	//Felica関係
	private	FelicaReader				felicaReader;
	private	FelicaService				felicaService;
	private	String							felicaResult;				//Felica内容

	public LendPlugin() throws IOException{
		loader 			= new FXMLLoader(getClass().getResource("LendFrame.fxml"));
		content		= loader.load();
		controller		= (LendPluginController)loader.getController();	
		qrResult		= "";
		felicaResult	= "";
	}
	
	@SuppressWarnings("unchecked")
	public void startPlugin() throws FelicaException{
		buf = new MatOfByte();
		
		videoCapture = new VideoCapture(0);
		captureService = new CaptureService(videoCapture);
		captureService.start();													//キャプチャー開始
		
		felicaReader		= new FelicaReader();
		felicaService		= new FelicaService(felicaReader);
		felicaService.start();
		
		captureService.setOnSucceeded((event) ->{
			if(captureService.getValue() != null){
				image = (Mat) captureService.getValue();
				Highgui.imencode(".jpg" , image , buf);
				controller.setCameraImage(new Image(new ByteArrayInputStream(buf.toArray())));
				GetQR getQR = new GetQR(image);
				qrResult = getQR.decodeQR();
				if(qrResult != null)	controller.setQRResult(getQR.decodeQR());
			}
			captureService.restart();
		});
		
		felicaService.setOnSucceeded((event) -> {
			if(felicaService.getValue() != null){
				if(!felicaResult.equals(felicaService.getValue())){
					felicaResult = (String)felicaService.getValue();
					controller.setFelicaResult(felicaResult);
				}
			}
			felicaService.restart();
		});
		
	}
	
	@Override
	public Node getContent() {
		return content;
	}
	
}
