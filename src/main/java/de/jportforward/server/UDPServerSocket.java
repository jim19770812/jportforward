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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import de.jportforward.client.DestinationDefiniton;
import de.jportforward.client.UDPClient;

public class UDPServerSocket extends Server implements Runnable {


	private DatagramSocket socket;
	private Thread thread = null;
	public UDPServerSocket(DestinationDefiniton dest, int port)
			throws IOException {
		super(dest, port);
		socket = new DatagramSocket(port);
	}

	public void startServing() throws IOException {
		thread = new Thread(this);
		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		UDPClient client = null;
		InetAddress adress = null;
		try {
			client = new UDPClient(getDest());
			adress = InetAddress.getByName(client.getHost());
		} catch (Exception e1) {
			System.out.println("Cannot connect to UDP Destination on " + getDest().toString());
			return;
		}
		byte[] buffer = new byte[65536];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);	
		long pause = CalcThroughput.calc(buffer.length,getDest());
		while (true) {
			try {
				packet.setData(buffer);
				packet.setLength(buffer.length);
				socket.receive(packet);
				
				
				packet.setPort(client.getPort());
				packet.setAddress(adress);
				// send packet
				client.getClientSocket().send(packet);				
				if (pause != -1){
					try{
						Thread.sleep(pause);
					}
					catch(Throwable t){}
				}
				
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}

}