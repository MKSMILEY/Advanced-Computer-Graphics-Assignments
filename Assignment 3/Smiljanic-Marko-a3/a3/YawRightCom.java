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

public class YawRightCom extends AbstractAction {
	private static CamObj cam;
	
	public YawRightCom(CamObj c) { 
		super("Yaw Camera Right"); 
		cam = c;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("YawRightCom action called");
			cam.yawRight();
		}
		catch(NullPointerException err) {
			System.out.println("YawRightCom NullPointerException Caught");
		}
	}
}