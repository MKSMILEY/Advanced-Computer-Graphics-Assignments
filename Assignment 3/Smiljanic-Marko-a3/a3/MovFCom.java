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

public class MovFCom extends AbstractAction {
	private static CamObj cam;
	
	public MovFCom(CamObj c) { 
		super("Move Camera Forward");
		cam = c;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("MovFCom action called");
			cam.moveForward();
		}
		catch(NullPointerException err) {
			System.out.println("MovFCom NullPointerException Caught");
		}
	}
}