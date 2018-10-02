/*
Marko Smiljanic
CSc 155 Section 02
Due Date: September 27th
*/
package a1;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChangeMov extends AbstractAction {
	private static Starter st;
	
	public static void target(Starter sta) {
		if(st == null) {
			st = sta;
		}
	}
	
	public ChangeMov() { super("Change Movement"); }
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("ChangeMov action called");
			st.swapMov();
		}
		catch (NullPointerException err) {
			System.out.println("ChangeMov NullPointerException Caught");
		}
	}
}