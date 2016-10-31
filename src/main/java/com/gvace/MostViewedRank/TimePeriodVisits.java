package com.gvace.MostViewedRank;
public class TimePeriodVisits{
	private long timePeriod;
	private int count;
	public TimePeriodVisits(long timePeriod, int count) {
		//System.out.println("timePeriod created "+timePeriod);
		this.timePeriod = timePeriod;
		this.count = count;
	}
	public void increaseVisit() {
		count++;
	}
	@Override
	public int hashCode(){
		return (int)timePeriod;
	}
	@Override
	public boolean equals(Object o){
		if(o!=null && o.getClass()==this.getClass()){
			return ((TimePeriodVisits)o).timePeriod==timePeriod;
		}
		return false;
	}
	public long getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(long timePeriod) {
		this.timePeriod = timePeriod;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}