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

package de.jportforward.server;

import java.io.IOException;

import de.jportforward.client.DestinationDefiniton;

public abstract class Server {

	
	private int port;
	private DestinationDefiniton dest;
	public Server(DestinationDefiniton dest,int port) {
		super();
		this.port = port;
		this.dest = dest;
		// TODO Auto-generated constructor stub
	}

	public DestinationDefiniton getDest() {
		return dest;
	}
	public void setDest(DestinationDefiniton dest) {
		this.dest = dest;
	}
	public int getPort() {
		return port;
	}
	/**
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	public abstract void startServing() throws IOException;

}
