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

package de.jportforward.client;

public class DestinationDefiniton {

	/**
	 * 
	 */
	public static String TYPE_UDP = "UDP";
	public static String TYPE_TCP = "TCP";
	private String host;
	private int port;
	private int throughput = -1;
	private String type;
	private int localport;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getThroughput() {
		return throughput;
	}
	public void setThroughput(int throughput) {
		this.throughput = throughput;
	}
	public DestinationDefiniton(String type,String host,int port,int throughput,int localport) {
		super();
		this.host = host;
		this.port = port;
		this.throughput = throughput;
		this.type = type;
		this.localport = localport;
		// TODO Auto-generated constructor stub
	}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public String toString(){
		return type + ":"+ host + ":" + port;
	}
	
	public String getThroughputKey(){
		return type + ":"+ host + ":" + localport;
	}
}

