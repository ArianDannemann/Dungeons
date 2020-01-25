package org.Dungeons;

import java.util.Map;

public class Selector {
	public Object selectRandomObjectFromWeightedList(Map<?, Double> list) {
		double totalWeight = 0.0d;
		for (Object object : list.keySet())
		{
		    totalWeight += list.get(object).doubleValue();
		}
		double random = Math.random() * totalWeight;
		Object randomObject = null;
		for (Object object : list.keySet()) {
			random -= list.get(object).doubleValue();
		    if (random <= 0.0d)
		    {
		    	randomObject = object;
		        break;
		    }
		}
		return randomObject;
	}
}
