package br.com.sd.p2p;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SpaceTimer {
	static boolean finish = false;
	
	public static void main(String[] args) throws IOException {	 
		int delay = 1000;
		int interval = 10000;
		Timer timer = new Timer();
		
		SpaceServer server = new SpaceServer();
				
		timer.scheduleAtFixedRate(new TimerTask() {
		        public void run() {
		        	System.out.println(new Date());
		        	if(!finish) {
		        		finish = true;
		        	}else{
		        		try {
							server.sendImageToClients();
							server.getClientImages();
							server.mergeClientImages();
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
		        		
		        		System.out.println("close");
		        		server.close();
		        		System.exit(0);
		        	}
		        }
		    }, delay, interval);
		
		server.run();
	}
}
