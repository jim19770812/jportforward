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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public abstract class Client {

	/**
	 * @return Returns the host.
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host The host to set.
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * 
	 */	
	
	private int port;
	private String host;
	private int throughput;
	
	public Client(String host,int port,int throughput) {
		super();		
		this.port = port;
		this.host = host;
		this.throughput = throughput;
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	public abstract OutputStream getOutputStream()throws IOException;
	public abstract InputStream getInputStream()throws IOException;
	public int getThroughput() {
		return throughput;
	}
	public void setThroughput(int throughput) {
		this.throughput = throughput;
	}
}
