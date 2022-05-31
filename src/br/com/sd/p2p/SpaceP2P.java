package br.com.sd.p2p;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import br.com.sd.pdi.GrayScaleConversion;

public class SpaceP2P {
	public static void main(String[] args) throws ClassNotFoundException {	
		try {
			Socket cliente = new Socket("localhost", SpaceServer.SERVER_PORT);
			
			//Get the image object
	        InputStream inputStream = cliente.getInputStream();
	        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			Object objReceived = objectInputStream.readObject();
	        System.out.println("Received [" + objReceived + "] from: " + cliente);
			
	        if (objReceived instanceof GrayScaleConversion) {
	        	GrayScaleConversion conversion = (GrayScaleConversion)objReceived;
	        	conversion.setImageBuffer(conversion.getImageArray());

	        	System.out.println("~ Reading Received Info");
	        	System.out.println(conversion.getWorkSpaceRect().getX());
	        	System.out.println(conversion.getWorkSpaceRect().getY());
	        	System.out.println(conversion.getWorkSpaceRect().getWidth());
	        	System.out.println(conversion.getWorkSpaceRect().getHeight());
	        	conversion.run();
	        	
		        //Send the image object
	            OutputStream outputStream = cliente.getOutputStream();
	            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
	            objectOutputStream.writeObject(conversion);  
	        }
	        
			cliente.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
