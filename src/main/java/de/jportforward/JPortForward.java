/*
 *  JPortForward, Forward local TCP or UDP Ports to other hosts 
 *  Copyright (C) 2007  Matthias Schuhmann <schuhmannm@gmx.de>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 */


package de.jportforward;

import java.io.IOException;
import java.util.HashMap;

import de.jportforward.client.DestinationDefiniton;
import de.jportforward.server.TCPServerSocket;
import de.jportforward.server.UDPServerSocket;

public class JPortForward {
	
	// --------------- Const -------------------
	private static String KEY_LTCP = "-ltcp";
	private static String KEY_LUDP = "-ludp";
	private static String KEY_DTCP = "-dtcp";
	private static String KEY_DUDP = "-dudp";
	private static String KEY_DEST = "-dest";
	private static String KEY_LIMIT = "-limit";
	// ---------------- end of Const -----------
	
	private static String destination;
	private static int[] ltcp;
	private static int[] ludp;
	private static int[] dtcp;
	private static int[] dudp;
	private static int limit = -1;
	/**
	 * 
	 */
	public JPortForward() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private static int[] split(String s,String sep) throws Exception{
		String[] sA = s.split(",");
		int[] ret=new int[sA.length];
		
		for (int i = 0; i < sA.length; i++){
			int aPort = Integer.parseInt(sA[i]);
			ret[i] = aPort;
		}
		
		return ret;
	}
	
	private static int[] getArray(String sFrom,String sTo) throws Exception{
		int from = Integer.parseInt(sFrom);
		int to = Integer.parseInt(sTo);
		int[] ret = new int[to-from+1];
		for (int i=0; i < ret.length;i++){
			ret[i] = from+i;
		}
		return ret;
	}
	
	private static int[] getPorts(String key,HashMap mapOpts){
		
		try {
			String ports = (String)mapOpts.get(key);
			if (ports == null){
				return null;
			}
			
			//is list or range?
			int index = ports.indexOf(":");
			if ( index != -1){
				String from = ports.substring(0,index);
				String to = ports.substring(index+1,ports.length());
				int[] range = getArray(from,to);
				return range;
			}
			else{
				index = ports.indexOf(",");
				if (index != -1){
					int[] range = split(ports,",");
					return range;
				}
				else{
					// single port
					int aPort=Integer.parseInt(ports);
					return new int[]{aPort};
				}	
			}
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		
	}
	
	/*
	 * Read options
	 */			
	private static boolean getOpts(String[] args){
		
		boolean error = false;
		HashMap mapOpts = null;
		try {
			mapOpts = new HashMap();
			for (int i = 0; i < args.length; i=i+2){
				// first one should be a key so stepping of 2 should work
				String key = args[i];
				String value = args[i+1];			
				mapOpts.put(key,value);			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			error = true;
		}
		
		ltcp = getPorts(KEY_LTCP,mapOpts);
		ludp = getPorts(KEY_LUDP,mapOpts);
		dtcp = getPorts(KEY_DTCP,mapOpts);
		dudp = getPorts(KEY_DUDP,mapOpts);
		
		destination = (String)mapOpts.get(KEY_DEST);
		
		if (destination == null){
			error = true;
		}
		
		String sLimit = (String)mapOpts.get(KEY_LIMIT);
		if (sLimit != null){
			try{
				limit = Integer.parseInt(sLimit);
			}
			catch(Exception ex){
				error = true;
			}
		}
		
		if (!error){
			error = !check(ltcp,dtcp);
		}
		
		if (!error){
			error = !check(ludp,dudp);
		}
		
		if (error){
			printUsage();
		}
		else{
			System.out.println("FORWARDING:");
			if (ltcp != null){
				System.out.println("LOCAL TCP: " +(String)mapOpts.get(KEY_LTCP) + " to " + destination + " "+(String)mapOpts.get(KEY_DTCP));								
			}
			if (ludp != null){
				System.out.println("LOCAL UDP: " +(String)mapOpts.get(KEY_LUDP) + " to " + destination + " "+(String)mapOpts.get(KEY_DUDP));
			}
			if (limit != -1){
				System.out.println("Traffic/port limited to " + limit + " Kb");
			}
		}
		return error;
	}
	
	private String checkNull(String s){
		if (s == null){
			return "";
		}
		return s;
	}
	
	private static boolean check(int[] a, int[]b){
		if (a == null && b != null){
			return false;
		}
		
		if (a != null && b == null){
			return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		
		try {
			
			if (getOpts(args)){
				System.exit(1);
			}
			
			// --------------------------- TCP Ports ----------------------------------
			int s = -1;
			int d = -1;
			if (ltcp != null){
				for (int i = 0; i < ltcp.length; i++){
					s = ltcp[i];
					if (dtcp.length == 1){
						d = dtcp[0];
					}
					else{
						d = dtcp[i];
					}
					
					DestinationDefiniton dest = new DestinationDefiniton(DestinationDefiniton.TYPE_TCP,destination,d,limit,s);
					TCPServerSocket source = new TCPServerSocket(dest,s);
					source.startServing();
				}	
			}
			
			// ----------------------------- udp -----------------------------------
			if (ludp != null){
				for (int i = 0; i < ludp.length; i++){
					s = ludp[i];
					if (dudp.length == 1){
						d = dudp[0];
					}
					else{
						d = dudp[i];
					}
					DestinationDefiniton destUDP = new DestinationDefiniton(DestinationDefiniton.TYPE_UDP,destination,d,limit,s);
					UDPServerSocket sourceUDP = new UDPServerSocket(destUDP,s);
					sourceUDP.startServing();
				}	
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void printUsage(){
		StringBuffer usage = new StringBuffer("\n[v0.9.2 JPortForward by Matthias Schuhmann]\n");
		usage.append("Usage:\n");
		usage.append("java de.jportforward.JPortForward [OPTIONS]\n");
		usage.append("Options:\n");
		usage.append(" 	      -ltcp		local tcp-port-range to forward\n");
		usage.append(" 	      -ludp		local udp-port-range to forward\n");
		usage.append(" 	      -dest		destination host\n");
		usage.append(" 	      -dtcp		tcp-port-range on dest. host\n");
		usage.append(" 	      -dudp		udp-port-range on dest. host\n");
		usage.append(" 	      -limit	limit of traffic for each port in Kb\n");
		usage.append("\n");
		usage.append("Example Options:\n");
		usage.append("1. Forward local TCP/UDP Port 90 to www.xyz.com TCP/UDP 80\n");				
		usage.append("		-ltcp 90 -ludp 90 -dest www.xyz.com -dtcp 80 -dudp 80\n\n");
		usage.append("2. Forward local TCP Ports 90,100,150 to www.xyz.com TCP 91,101,151\n");				
		usage.append("		-ltcp 90,100,150 -dest www.xyz.com -dtcp 91,101,151 \n\n");
		usage.append("3. Forward local TCP Ports 90-100 www.xyz.com TCP 100-110\n");				
		usage.append("		-ltcp 90:100 -dest www.xyz.com -dtcp 100:110 \n\n");
		usage.append("Cheers!");
		System.out.println(usage);
	}
	
}
