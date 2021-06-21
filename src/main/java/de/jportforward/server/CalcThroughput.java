/*
 * Created on 12.12.2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package de.jportforward.server;

import java.util.HashMap;

import de.jportforward.client.DestinationDefiniton;

/**
 * @author rzvr8x
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CalcThroughput {
	
	
	private static HashMap mapConnections = new HashMap();
	
	public static void inc(DestinationDefiniton def){
		if (def == null || def.getThroughput() == -1){
			return;
		}
		Float i = get(def);
		i = new Float(i.floatValue()+1.0f);
		put(def,i);
	}
	
	public static void dec(DestinationDefiniton def){
		if (def == null || def.getThroughput() == -1){
			return;
		}
		Float i = get(def);
		i = new Float(i.intValue()-1.0f);
		put(def,i);
	}
	
	
	private static Float get(DestinationDefiniton def){
		Float i =(Float) mapConnections.get(def.getThroughputKey());
		if (i == null){
			i = new Float(0);
			mapConnections.put(def.getThroughputKey(),i);
		}
		return i;
	}
	
	private synchronized static void put(DestinationDefiniton def,Float i){
		mapConnections.put(def.getThroughputKey(),i);
	}
	
	/**
	 * 
	 */
	public CalcThroughput() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Calculate pause in milliseconds 
	 * @param bufferSize
	 * @param limitInKB
	 * @return
	 */
	public static long calc(long bufferBytes,DestinationDefiniton def){		
		if (def == null || def.getThroughput() == -1){
			return -1;
		}
			
		float cons = get(def).floatValue();
		float multiplicator = 1100.0f / cons;
		
		float limit = (float)def.getThroughput() * multiplicator;
		float size =  (float)bufferBytes;
		//x * size = limit
		float x = limit / size;
		float pause = 1000.0f/x;
		
		//System.out.println("Connections:"+cons+ " Pause:" +pause);
		return (long)pause;
 		
	}

	public static void main(String[] args) {
	}
}
