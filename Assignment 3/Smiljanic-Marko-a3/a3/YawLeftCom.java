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

public class YawLeftCom extends AbstractAction {
	private static CamObj cam;
	
	public YawLeftCom(CamObj c) { 
		super("Yaw Camera Up"); 
		cam = c;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("YawLeftCom action called");
			cam.yawLeft();
		}
		catch(NullPointerException err) {
			System.out.println("YawLeftCom NullPointerException Caught");
		}
	}
}