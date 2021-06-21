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

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class TestUDPClient {

	/**
	 * 
	 */
	public TestUDPClient(String host,int port) throws Exception{
		super();
		  DatagramPacket request = new DatagramPacket("UDP-Test".getBytes(), "UDP-Test".getBytes().length, InetAddress.getByName(host), port);
          DatagramSocket socket = new DatagramSocket();
          socket.send(request);
	}
	
	public static void main(String[] args){
		try {
			TestUDPClient udpClient= new TestUDPClient("localhost",2001);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
