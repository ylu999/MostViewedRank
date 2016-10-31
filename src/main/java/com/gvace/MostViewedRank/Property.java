package com.gvace.MostViewedRank;

import java.util.Deque;
import java.util.LinkedList;

public class Property{
	String id;
	int count;
	int prevCount = -1;
	Deque<TimePeriodVisits> times;
	public Property(String id) {
		super();
		this.id = id;
		times = new LinkedList<TimePeriodVisits>();
	}
	public synchronized void visitOnce(long visitTimePeriod){
		TimePeriodVisits timePeriodVisits = times.peekFirst();
		if(timePeriodVisits==null){
			timePeriodVisits = new TimePeriodVisits(visitTimePeriod,1);
			times.addFirst(timePeriodVisits);
		}
		else {
			timePeriodVisits.increaseVisit();
		}
		prevCount = count;
		count++;
		//System.out.println("Property{"+id+"} visited");
	}
	public synchronized void clear(long timePeriod){
		TimePeriodVisits timePeriodVisits = times.peekLast();
		//if(timePeriodVisits==null)return;
		while(timePeriodVisits!=null&&timePeriodVisits.getTimePeriod()<=timePeriod){
			prevCount = count;
			count = count-timePeriodVisits.getCount();
			times.pollLast();
			timePeriodVisits = times.peekLast();
			System.out.println("Property{"+id+"} CLEARED");
		}
	}
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	@Override
	public boolean equals(Object o){
		if(o!=null && o.getClass()==this.getClass()){
			return ((Property)o).id.equals(id);
		}
		return false;
	}
	public String getId() {
		return id;
	}
	public int getCount() {
		return count;
	}
	public int getPrevCount() {
		return prevCount;
	}
	public Deque<TimePeriodVisits> getTimes() {
		return times;
	}
}