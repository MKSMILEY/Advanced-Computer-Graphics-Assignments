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

public class MovUCom extends AbstractAction {
	private static CamObj cam;
	
	public MovUCom(CamObj c) { 
		super("Move Camera Up"); 
		cam = c;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("MovUCom action called");
			cam.moveUp();
		}
		catch(NullPointerException err) {
			System.out.println("MovUCom NullPointerException Caught");
		}
	}
}
 