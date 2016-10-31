package com.gvace.MostViewedRank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;


import java.util.List;

public class PropertyViewsManager implements MostViewed, Runnable {
	private static final long EXPIRATION_TIME_SECOND = 15;
	// count
	TreeMap<Integer, Set<Property>> rankMap = new TreeMap<Integer, Set<Property>>();
	// id
	Map<String, Property> propertiesMap = new HashMap<String, Property>();
	// timePeriod
	Map<Long, Set<Property>> timeMap = new HashMap<Long, Set<Property>>();
	Queue<Long> timePeriods = new LinkedList<Long>();

	public void propertyViewed(String zpid) {
		Property p = propertiesMap.get(zpid);
		if (p == null) {
			p = new Property(zpid);
			propertiesMap.put(zpid, p);
		}
		else{
			System.out.println("Found zpid:"+zpid);
		}
		long visitTimePeriod = System.currentTimeMillis() / 1000;
		p.visitOnce(visitTimePeriod);
		updateRankMap(p);
		updateTimeMap(visitTimePeriod, p);
	}
	
	private void updateTimeMap(long visitTimePeriod, Property p) {
		Set<Property> ps = timeMap.get(visitTimePeriod);
		if (ps == null) {
			ps = new HashSet<Property>();
			ps.add(p);
			timeMap.put(visitTimePeriod, ps);
			timePeriods.add(visitTimePeriod);
		} else
			ps.add(p);
	}

	public synchronized List<String> getCurrentMostPopular(int count) {
		Set<Integer> counts = rankMap.descendingKeySet();
		Iterator<Integer> iterator = counts.iterator();
		List<String> results = new ArrayList<String>();
		int i=0;
		while(iterator.hasNext()){
			Integer countKey = iterator.next();
			Set<Property> ps = rankMap.get(countKey);
			for(Property p: ps){
				results.add(p.getId()+"["+countKey+"]");
				i++;
				if(i==count) return results;
			}
		}
		return results;
	}

	public void updateRankMap(Property p) {
		Set<Property> set = rankMap.get(p.getPrevCount());
		if (set != null)set.remove(p);
		if(p.getCount()==0)return;
		Set<Property> newSet = rankMap.get(p.getCount());
		if (newSet == null) {
			newSet = new HashSet<Property>();
			newSet.add(p);
			rankMap.put(p.getCount(), newSet);
		} else {
			newSet.add(p);
		}
		// System.out.println("Property{"+p.getId()+"} UPDATED");
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				// clear expired 10 seconds
				clear(System.currentTimeMillis() / 1000 - EXPIRATION_TIME_SECOND);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void clear(long timePeriod) {
		Long oldTimePeriod = timePeriods.peek();
		while (oldTimePeriod != null) {
			if( oldTimePeriod > timePeriod) break;
			Set<Property> set = timeMap.get(oldTimePeriod);
			int i = 0;
			for (Property p : set) {
				p.clear(oldTimePeriod);
				updateRankMap(p);
				i++;
			}
			timeMap.remove(oldTimePeriod);
			timePeriods.poll();
			System.out.println("Manager clear " + timePeriod + " count: " + i);
			oldTimePeriod = timePeriods.peek();
		}
		System.out.println(timeMap.size() +" VS "+timePeriods.size());
	}
}
