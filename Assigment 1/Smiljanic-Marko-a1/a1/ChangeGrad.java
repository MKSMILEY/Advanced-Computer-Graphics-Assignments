/*
Marko Smiljanic
CSc 155 Section 02
Due Date: September 27th
*/
package a1;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChangeGrad extends AbstractAction {
	private static Starter st;
	
	public static void target(Starter sta) {
		if(st == null) {
			st = sta;
		}
	}
	
	public ChangeGrad() { super("Change Gradient"); }
	
	public void actionPerformed (ActionEvent e) {
		try {
			System.out.println("ChangeGrad action called");
			st.changeGrad();
		}
		catch (NullPointerException err) {
			System.out.println("ChangeGrad NullPointerException Caught");
		}
	}
}