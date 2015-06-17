/*******************************************************************************
 * Copyright (c) 2005 Jean-Michel Lemieux, Jeff McAffer and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Hyperbola is an RCP application developed for the book
 *     Eclipse Rich Client Platform - 
 *         Designing, Coding, and Packaging Java Applications
 * See http://eclipsercp.org
 *
 * Contributors:
 *     Jean-Michel Lemieux and Jeff McAffer - initial API and implementation
 *******************************************************************************/
package smack.testing;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication, MessageListener {

	public Object start(IApplicationContext context) throws Exception {
		XMPPConnection con = new XMPPConnection("eclipsercp.org");
		try {
	      con.connect();
	      con.login("reader", "secret",
	          Long.toString(System.currentTimeMillis()));
	      Chat chat = con.getChatManager().createChat("eliza@eclipsercp.org", this);
	      chat.sendMessage("Hi There!");
	      Thread.sleep(5000);
	    } catch (XMPPException e) {
	      e.printStackTrace();
	    } finally {
	    	con.disconnect();
	    }
	    return IApplication.EXIT_OK;
	}

	public void stop() {
		// TODO Auto-generated method stub
	}

	public void processMessage(Chat chat, Message message) {
		System.out.println("Returned message: "
		          + (message == null ? "<timed out>" : message.getBody()));
	}
}
