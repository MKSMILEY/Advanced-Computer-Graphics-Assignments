/*
Marko Smiljanic
CSc 155 Section 02
Due Date: November 27th
*/
package a3;

import graphicslib3D.*;
import java.io.*;
import java.nio.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class MovLRCom extends AbstractAction {
	private static Starter st;
	
	public MovLRCom(Starter starter) {
		super("Move Light Right");
		st = starter;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("MovLRCom action called");
			st.addLight(0.1f, 0.0f, 0.0f);
		}
		catch(NullPointerException err) {
			System.out.println("MovLRCom NullPointerException Caught");
		}
	}
}