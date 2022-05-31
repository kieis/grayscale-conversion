package br.com.sd.p2p;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import br.com.sd.model.FileManager;
import br.com.sd.model.MergeImage;
import br.com.sd.model.Rect;
import br.com.sd.pdi.GrayScaleConversion;

public class SpaceServer {
	public final static String PATH_FILE = "D:\\Eclipse\\SD\\eclipse-workspace\\p2d-image-grayscale\\images\\";;
	public final static String FILE_TO_SEND = PATH_FILE + "main_img.jpg";
	public final static int SERVER_PORT = 45233;
	
	private ServerSocket servidor = null;
	private ArrayList<Socket> clientSockets = new ArrayList<Socket>();
	private ArrayList<GrayScaleConversion> imagesToMerge = new ArrayList<GrayScaleConversion>();
	private boolean bImageSent = false;

	public int getClientCount() {
		return clientSockets.size() + 1;
	}
	
	public boolean isImageSent() {
		return bImageSent;
	}

	private void setImageSent(boolean bImageSent) {
		this.bImageSent = bImageSent;
	}

	public SpaceServer() {
		try {
			servidor = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			System.out.println(">> Server Started");
			while (true) {
				Socket cliente = servidor.accept();
				System.out.println("Client connected: " + getClientCount() + 
						", IP: " + cliente.getInetAddress().getHostAddress() +
						", Port: " + cliente.getPort());
				clientSockets.add(cliente);
			}			
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	private void sendObjectToClient(Socket socket, Object object) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
	}
	
	public void sendImageToClients() throws IOException {
		if(isImageSent()) {
			return;
		}
		
		byte[] imageArray = FileManager.readFile(FILE_TO_SEND);	
		GrayScaleConversion myImage = new GrayScaleConversion(PATH_FILE + "finalImg.jpg", imageArray);
		ArrayList<GrayScaleConversion> clientImages = new ArrayList<GrayScaleConversion>();
		
		Rect tmp = new Rect(0, 0, 0, 0);
		Rect lastImg = new Rect(0, 0, 0, 0);
		
		for(int i = 1; i < getClientCount(); i++) {
			if(i < getClientCount() - 1) {
				tmp.setX(lastImg.getX() + lastImg.getWidth());
				tmp.setY(lastImg.getY());
				tmp.setWidth(myImage.getImageRect().getWidth() / (getClientCount() - 1));
				tmp.setHeight(myImage.getImageRect().getHeight());
				
				lastImg = new Rect(tmp);			
				
				GrayScaleConversion curClient = new GrayScaleConversion(PATH_FILE + "gray" + i + ".jpg", imageArray);
				curClient.setWorkSpaceRect(lastImg);
				clientImages.add(curClient);
			}else {
				tmp.setX(lastImg.getX() + lastImg.getWidth());
				tmp.setY(lastImg.getY());
				tmp.setWidth(myImage.getImageRect().getWidth() - (lastImg.getWidth() + lastImg.getX()));
				tmp.setHeight(myImage.getImageRect().getHeight());
				
				lastImg = new Rect(tmp);
				
				GrayScaleConversion curClient = new GrayScaleConversion(PATH_FILE + "gray" + i + ".jpg", imageArray);
				curClient.setWorkSpaceRect(lastImg);
				clientImages.add(curClient);
			}	
		}	
		
		System.out.println("\n>> Image Queue Size: " + clientImages.size());
		
		for(int i = 0; i < clientImages.size(); i++) {
			System.out.println("Sending image to client: " + (i + 1));
			sendObjectToClient(clientSockets.get(i), clientImages.get(i));
		}
		
		setImageSent(true);
	}
	
	public void getClientImages() throws IOException, ClassNotFoundException {
		if(!isImageSent()) {
			return;
		}
		
		for(int i = 0; i < clientSockets.size(); i++) {		
	        InputStream inputStream = clientSockets.get(i).getInputStream();
	        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			Object objReceived = objectInputStream.readObject();
	        System.out.println("Server Received An Object [" + objReceived + "] from: " + clientSockets.get(i));
			
	        if (objReceived instanceof GrayScaleConversion) {
	        	GrayScaleConversion conversion = (GrayScaleConversion)objReceived;
	        	conversion.setImageBuffer(conversion.getImageArray());
	        	conversion.saveOnDisk();
	        	imagesToMerge.add(conversion);
	        }
	        
			clientSockets.get(i).close();
		}
	}
	
	public void mergeClientImages() throws IOException {
		if(imagesToMerge.isEmpty()) {
			return;
		}
		
        System.out.println("\n>> Merging Images"); 
        MergeImage mergImg = new MergeImage(PATH_FILE + "mergedImg.jpg", imagesToMerge);
        mergImg.run();
	}
	
	public void close() {
		try {
			servidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
