package br.com.sd.pdi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import br.com.sd.model.ImageUtils;
import br.com.sd.model.Rect;

public class GrayScaleConversion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private transient BufferedImage image;
	private Rect imageRect;
	private Rect workSpaceRect;
	private String output;
	private byte[] imageArray;
	
	public void setImageBuffer(byte[] input) {
		try {
		setImageArray(input);
		setImage(ImageIO.read(new ByteArrayInputStream(getImageArray())));
		setImageRect(new Rect(getImage().getMinX(), getImage().getMinY(),
				              getImage().getWidth(), getImage().getHeight()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}

	private void setImage(BufferedImage image) {
		this.image = image;
	}

	public Rect getImageRect() {
		return imageRect;
	}
	
	public byte[] getImageArray() {
		return imageArray;
	}

	public void setImageArray(byte[] imageArray) {
		this.imageArray = imageArray;
	}

	private void setImageRect(Rect imageRect) {
		this.imageRect = imageRect;
	}

	public Rect getWorkSpaceRect() {
		return workSpaceRect;
	}

	public void setWorkSpaceRect(Rect workSpaceRect) {
		this.workSpaceRect = workSpaceRect;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	public void run() throws IOException {
		if(getImage() == null) {
			System.out.println("Image Buffer is empty.");
			return;
		}
		
		if(output.isEmpty()) {
			System.out.println("Missing Output File.");
			return;
		}
		
		if(getWorkSpaceRect().isEmpty()) {
			System.out.println("WorkSpace Rect is Empty.");
			return;
		}
		
		for (int i = getWorkSpaceRect().getX(); i < getWorkSpaceRect().getX() + getWorkSpaceRect().getWidth(); i++) {
			for (int j = getWorkSpaceRect().getY(); j < getWorkSpaceRect().getY() + getWorkSpaceRect().getHeight(); j++) {
				Color pixelColor = new Color(getImage().getRGB(i, j));
				
				int red = pixelColor.getRed();
				int green = pixelColor.getGreen();
				int blue = pixelColor.getBlue();
	
				int gray = (red + green + blue) / 3;
				
				Color newColor = new Color(gray, gray, gray);
				
				getImage().setRGB(i, j, newColor.getRGB());
			}
		}
		
		//Update Gray
		setImageArray(ImageUtils.toByteArray(getImage(), "jpg"));
	}
	
	public void saveOnDisk() {
		try {
			File ouptutFile = new File(getOutput());
			ImageIO.write(getImage(), "jpg", ouptutFile);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public GrayScaleConversion(String output, byte[] input) {
		setOutput(output);
		setImageBuffer(input);
	}
}
