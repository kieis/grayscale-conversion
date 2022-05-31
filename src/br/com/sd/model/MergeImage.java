package br.com.sd.model;

import java.io.IOException;
import java.util.ArrayList;

import br.com.sd.pdi.GrayScaleConversion;

public class MergeImage {
	private ArrayList<GrayScaleConversion> conversionImagesList;
	private GrayScaleConversion outputConversion;
	private String output;

	public ArrayList<GrayScaleConversion> getConversionImagesList() {
		return conversionImagesList;
	}
	
	public void setConversionImagesList(ArrayList<GrayScaleConversion> conversionImagesList) {
		this.conversionImagesList = conversionImagesList;
	}
	
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void run() throws IOException {
		if(conversionImagesList.isEmpty()) {
			System.out.println("No image to merge!");
			return;
		}

		outputConversion = new GrayScaleConversion(getOutput(), conversionImagesList.get(0).getImageArray());
		outputConversion.setWorkSpaceRect(new Rect(0,0,0,0));

		for(GrayScaleConversion current : conversionImagesList) {
			for (int currentX = current.getWorkSpaceRect().getX(); currentX < current.getWorkSpaceRect().getX() + current.getWorkSpaceRect().getWidth(); currentX++) {
				for (int currentY = current.getWorkSpaceRect().getY(); currentY < current.getWorkSpaceRect().getY() + current.getWorkSpaceRect().getHeight(); currentY++) {	
					int currentRGB = current.getImage().getRGB(currentX, currentY);
					outputConversion.getImage().setRGB(currentX, currentY, currentRGB);
				}
			}
		}
		
		System.out.println(outputConversion.getOutput());
		outputConversion.saveOnDisk();
	}

	public MergeImage(String output, ArrayList<GrayScaleConversion> list) {
		setConversionImagesList(list);
		setOutput(output);
	}
	
	public MergeImage(String output) {
		setConversionImagesList(new ArrayList<GrayScaleConversion>());
		setOutput(output);
	}
}
