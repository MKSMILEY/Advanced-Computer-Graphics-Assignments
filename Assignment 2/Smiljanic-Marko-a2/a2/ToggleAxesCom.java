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

public class ToggleAxesCom extends AbstractAction {
	private static Starter st;
	
	public ToggleAxesCom(Starter s) { 
		super("Toggle Axes View"); 
		st = s;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("ToggleAxesCom action called");
			st.toggleAxes();
		}
		catch(NullPointerException err) {
			System.out.println("ToggleAxesCom NullPointerException Caught");
		}
	}
}