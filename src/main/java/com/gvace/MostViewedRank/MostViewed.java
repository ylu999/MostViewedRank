package com.gvace.MostViewedRank;

import java.util.List;

public interface MostViewed {
	//Every user view of a property calls this:
	void propertyViewed(String zpid);
	//Anytime we want the top 'count' properties, we can call this:
	//which returns a list of zpids
	List<String> getCurrentMostPopular(int count);
}
