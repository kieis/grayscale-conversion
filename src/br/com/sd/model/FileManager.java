package br.com.sd.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileManager {
	public static byte[] readFile(String path) throws IOException {
		File myFile = new File(path);
		byte[] mybytearray = new byte[(int) myFile.length()];
		FileInputStream fis = new FileInputStream(myFile);
		
		BufferedInputStream bis = new BufferedInputStream(fis);
		bis.read(mybytearray, 0, mybytearray.length);
		
		fis.close();
		bis.close();
		
		return mybytearray;
	}
}
