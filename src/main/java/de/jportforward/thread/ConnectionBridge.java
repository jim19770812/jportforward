/*
 * JPortForward, Forward local TCP or UDP Ports to other hosts Copyright (C)
 * 2007 Matthias Schuhmann <schuhmannm@gmx.de>
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.jportforward.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;

import de.jportforward.client.DestinationDefiniton;
import de.jportforward.server.CalcThroughput;

public class ConnectionBridge extends Thread {

	protected final InputStream istream;

	protected final OutputStream ostream;

	protected boolean active;

	protected int throughput;
	
	protected DestinationDefiniton def;

	public ConnectionBridge(InputStream istream, OutputStream ostream,
			String description,DestinationDefiniton def) {

		super(description);

		if (istream == null)
			throw new NullPointerException("null istream argument");

		if (ostream == null)
			throw new NullPointerException("null ostream argument");

		this.istream = istream;
		this.ostream = ostream;
		if (def != null){
			this.throughput = def.getThroughput();
		}	
		this.def = def;
		active = true;

		start();
	}

	/**
	 * Request asynchronous termination of the stream bridge
	 */
	public void terminate() {
		active = false;
		interrupt();
	}

	/**
	 * Thread copying bytes from the input stream and writing them to the output
	 * stream until the end of file.
	 */
	public void run() {

		try {
			CalcThroughput.inc(def);
			byte[] buffer = null;
			if (def != null && def.getThroughput() == -1){
				buffer = new byte[4096];
			}
			else{
				buffer = new byte[128];
			}
			int size = 0;			
			while ((active) && (size != -1)) {
				if (size > 0) {
					ostream.write(buffer, 0, size);
					ostream.flush();
				}

				try {
					size = istream.read(buffer);
				} catch (InterruptedIOException e) {
					size = e.bytesTransferred;
				}
				long pause = CalcThroughput.calc(buffer.length,def);
				if (pause != -1){
					try{
						Thread.sleep(pause);
					}
					catch(Throwable t){}
				}
			}
		} catch (IOException e) {
			// expected
			//System.err.println(getName() + ": " + e.getClass().getName() + ":
			// " + e.getMessage());
		} catch (Throwable e) {
			// unexpected
			e.printStackTrace();
		} finally {
			try {
				//System.out.println("Closing ostream " + getName());
				ostream.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}

			try {
				//System.out.println("Closing istream " + getName());
				istream.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			CalcThroughput.dec(def);
		}
	}
}