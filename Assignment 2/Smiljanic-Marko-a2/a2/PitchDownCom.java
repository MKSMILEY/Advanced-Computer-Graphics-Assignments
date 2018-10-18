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

public class PitchDownCom extends AbstractAction {
	private static CamObj cam;
	
	public PitchDownCom(CamObj c) { 
		super("Pitch Camera Down"); 
		cam = c;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("PitchDownCom action called");
			cam.pitchDown();
		}
		catch(NullPointerException err) {
			System.out.println("PitchDownCom NullPointerException Caught");
		}
	}
}