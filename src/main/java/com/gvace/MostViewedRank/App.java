package com.gvace.MostViewedRank;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
	public static void main(String[] args){
		ExecutorService executor = Executors.newFixedThreadPool(5);


		PropertyViewsManager pvm = new PropertyViewsManager();
		PropertyViewerSimulator pvs = new PropertyViewerSimulator(pvm);

        executor.execute(pvm);
        executor.execute(pvs);
        
        while(true){
	        try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        List<String> results = pvm.getCurrentMostPopular(100);
	        for(String s:results)System.out.print(s+",");
	        System.out.println();
        }
	}
}
