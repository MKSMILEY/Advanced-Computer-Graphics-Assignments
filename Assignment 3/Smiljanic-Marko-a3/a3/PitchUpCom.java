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

public class PitchUpCom extends AbstractAction {
	private static CamObj cam;
	
	public PitchUpCom(CamObj c) { 
		super("Pitch Camera Up"); 
		cam = c;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("PitchUpCom action called");
			cam.pitchUp();
		}
		catch(NullPointerException err) {
			System.out.println("PitchUpCom NullPointerException Caught");
		}
	}
}