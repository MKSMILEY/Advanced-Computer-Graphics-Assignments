/*
Marko Smiljanic
CSc 155 Section 02
Due Date: October 15th
*/
package a2;

import graphicslib3D.*;
import java.io.*;
import java.nio.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class MovLCom extends AbstractAction {
	private static CamObj cam;
	
	public MovLCom(CamObj c) { 
		super("Move Camera Left"); 
		cam = c;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("MovLCom action called");
			cam.moveLeft();
		}
		catch(NullPointerException err) {
			System.out.println("MovLCom NullPointerException Caught");
		}
	}
}