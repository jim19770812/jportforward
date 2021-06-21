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

package de.jportforward.test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TestUDPServer {

	/**
	 * 
	 */
	public TestUDPServer(int port) {
		try {
			System.out.println("TestUDPServer listening on Port " + port);
	        byte[] inbuf = new byte[65536]; // default size
	        DatagramSocket socket = new DatagramSocket(port);
	        while(true){
		        // Wait for packet
		        DatagramPacket packet = new DatagramPacket(inbuf, inbuf.length);
		        socket.receive(packet);
		        String s = new String(packet.getData());
		        System.out.println("TestUDPServer listening on Port " + port +" received:"+s);
		        // Data is now in inbuf
		        int numBytesReceived = packet.getLength();
	        }    
	    } catch (SocketException e) {
	    } catch (IOException e) {
	    }
	}
	
	public static void main(String[] args){
		TestUDPServer udpServer = new TestUDPServer(2501); 
	}
}
