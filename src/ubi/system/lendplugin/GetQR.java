package ubi.system.lendplugin;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Mat;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

public class GetQR {
	private	Mat						image;
	private	QRCodeReader	qrReader;
	
	public GetQR(Mat image){
		this.image = image;
		qrReader		= new QRCodeReader();
	}
	
	private BinaryBitmap MatToBinaryBitmap(Mat matBGR) {
	    int	width = matBGR.width();
	    int	height = matBGR.height();
	    int	channels = matBGR.channels();
	    byte[] sourcePixels = new byte[width * height * channels];
	    matBGR.get(0, 0, sourcePixels);
	    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
	    Binarizer binarizer = new HybridBinarizer(new BufferedImageLuminanceSource(image));
	    return new BinaryBitmap(binarizer);
	}
	
	public String decodeQR(){
		BinaryBitmap bitmap = MatToBinaryBitmap(image);
		try{
			Result qr = qrReader.decode(bitmap);
			return qr.getText();
		}catch(NotFoundException | ChecksumException | FormatException ex){
			return null;			//例外内容 : ex.toString();
		}
	}
}
