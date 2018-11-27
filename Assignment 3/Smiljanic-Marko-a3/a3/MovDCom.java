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

public class MovDCom extends AbstractAction {
	private static CamObj cam;
	
	public MovDCom(CamObj c) { 
		super("Move Camera Down"); 
		cam = c;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("MovDCom action called");
			cam.moveDown();
		}
		catch(NullPointerException err) {
			System.out.println("MovDCom NullPointerException Caught");
		}
	}
}
 