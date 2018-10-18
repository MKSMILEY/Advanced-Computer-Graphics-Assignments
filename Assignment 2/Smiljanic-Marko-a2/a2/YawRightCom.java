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