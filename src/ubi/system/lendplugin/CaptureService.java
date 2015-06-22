package ubi.system.lendplugin;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CaptureService extends Service{
	private 				VideoCapture	videoCapture;
	
	private	final		Mat					image 	= new Mat();
	
	public CaptureService(VideoCapture videoCapture) {
		super();
		this.videoCapture = videoCapture;
	}

	@Override
	protected Task createTask() {
		return new Task() {
			@Override
			protected Mat call() throws Exception {
				if(videoCapture.isOpened()){
					videoCapture.read(image);
					if(!image.empty()){
						return image;
					}
				}
				return null;
			}
		};
	}

}
