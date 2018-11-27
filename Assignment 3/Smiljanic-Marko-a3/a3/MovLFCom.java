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

public class MovLFCom extends AbstractAction {
	private static Starter st;
	
	public MovLFCom(Starter starter) {
		super("Move Light Forward");
		st = starter;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("MovLFCom action called");
			st.addLight(0.0f, 0.0f, -0.1f);
		}
		catch(NullPointerException err) {
			System.out.println("MovLFCom NullPointerException Caught");
		}
	}
}