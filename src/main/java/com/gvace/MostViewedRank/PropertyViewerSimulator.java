package com.gvace.MostViewedRank;

import java.util.Random;
import java.lang.Runnable;

public class PropertyViewerSimulator implements Runnable{
	private PropertyViewsManager pvm;
	Random random;
	public PropertyViewerSimulator(PropertyViewsManager pvm) {
		super();
		this.pvm = pvm;
		random = new Random();
	}

	public String randomID(){
		return ""+random.nextInt(10);
	}

	public void run(){
		while(true){
			pvm.propertyViewed(randomID());
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}