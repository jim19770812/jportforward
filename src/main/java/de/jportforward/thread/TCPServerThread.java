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

package de.jportforward.thread;


import java.net.*;
import java.io.*;

import de.jportforward.client.DestinationDefiniton;
import de.jportforward.client.TCPClient;

public class TCPServerThread extends Thread {
	private Socket socket = null;
	private DestinationDefiniton dest;
	public TCPServerThread(Socket socket,DestinationDefiniton dest) {
		super("TCPServerThread");
		this.socket = socket;
		this.dest = dest;
	}

	public void run() {

		try {
			boolean debug = false;
			if (debug){
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket
						.getInputStream()));
	
				String inputLine, outputLine;
				
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
				}
				out.close();
				in.close();
				socket.close();
			}
			else{
				TCPClient client = new TCPClient(dest);
				ConnectionBridge bridgeAB = new ConnectionBridge(socket.getInputStream(),client.getOutputStream(),"A->B",null); 
				ConnectionBridge bridgeBA = new ConnectionBridge(client.getInputStream(),socket.getOutputStream(),"B->A",dest);
				
			}
			
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}