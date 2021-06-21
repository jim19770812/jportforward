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
import java.net.Socket;

public class TCPClient extends Client{

	/**
	 * 
	 */
	private Socket clientSocket;
	
	
	public TCPClient(DestinationDefiniton dest) throws IOException{
		super(dest.getHost(),dest.getPort(),dest.getThroughput());
		clientSocket = new Socket(getHost(),getPort());
	}
	public TCPClient(String host,int port) throws IOException{
		super(host,port,-1);
		clientSocket = new Socket(host,port);		
		// TODO Auto-generated constructor stub
	}

	public OutputStream getOutputStream() throws IOException{
		return clientSocket.getOutputStream();
	}
	
	public InputStream getInputStream()throws IOException{
		return clientSocket.getInputStream();
	}
	
}
