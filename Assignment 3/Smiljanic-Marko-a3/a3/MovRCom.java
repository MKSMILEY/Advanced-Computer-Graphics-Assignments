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

public class MovRCom extends AbstractAction {
	private static CamObj cam;
	
	public MovRCom(CamObj c) { 
		super("Move Camera Right"); 
		cam = c;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("MovRCom action called");
			cam.moveRight();
		}
		catch(NullPointerException err) {
			System.out.println("MovRCom NullPointerException Caught");
		}
	}
}
 