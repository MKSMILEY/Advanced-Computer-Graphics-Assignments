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

public class MovBCom extends AbstractAction {
	private static CamObj cam;
	
	public MovBCom(CamObj c) { 
		super("Move Camera Back"); 
		cam = c;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("MovBCom action called");
			cam.moveBack();
		}
		catch(NullPointerException err) {
			System.out.println("MovBCom NullPointerException Caught");
		}
	}
}