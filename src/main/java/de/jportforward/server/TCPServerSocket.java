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
import java.net.ServerSocket;

import de.jportforward.client.DestinationDefiniton;
import de.jportforward.thread.TCPServerThread;


public class TCPServerSocket extends Server implements Runnable{

	/**
	 *  
	 */

	private ServerSocket socket;
	private Thread thread;
	public TCPServerSocket(DestinationDefiniton dest, int port) throws IOException {
		super(dest, port);
		socket = new ServerSocket(port);
	}

	public void startServing() throws IOException{
		thread = new Thread(this);
		thread.setName("TCP");
		thread.start();
		
	}

	
	public void run() {
		// TODO Auto-generated method stub
		while (true){
		    try {
				new TCPServerThread(socket.accept(),getDest()).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}    
	}


}