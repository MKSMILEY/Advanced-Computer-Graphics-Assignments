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

public class ToggleLightCom extends AbstractAction {
	private static Starter st;
	
	public ToggleLightCom(Starter s) { 
		super("Toggle Positional Light"); 
		st = s;
	}
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("ToggleLightCom action called");
			st.toggleLight();
		}
		catch(NullPointerException err) {
			System.out.println("ToggleLightCom NullPointerException Caught");
		}
	}
}