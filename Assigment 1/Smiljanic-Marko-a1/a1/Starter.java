/*
Marko Smiljanic
CSc 155 Section 02
Due Date: September 27th
*/
package a1;

import java.nio.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GLContext;
import com.jogamp.common.nio.Buffers;

import com.jogamp.opengl.util.*;
import graphicslib3D.GLSLUtils.*;
import graphicslib3D.*;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Starter extends JFrame implements GLEventListener, MouseWheelListener{	
	private GLCanvas myCanvas;
	private int rendering_program;
	private int vao[] = new int[1];
	private GLSLUtils util = new GLSLUtils();
	
	private float xMover = 0.0f;
	private float yMover = 0.0f;
	private float xInc = 0.01f;
	private float yInc = 0.01f;
	private float tempx;
	private float tempy;
	
	//TODO add boolean that tells if the mouse wheel has been spun and if so
	//we must multiply the x and y by the desired amount
	private float size = 0.25f;
	private boolean scrollUp, scrollDown = false;
	
	private float x1 = size;
	private float x2 = -size;
	private float x3 = 0.0f;
		
	private float y1 = -size;
	private float y2 = -size;
	private float y3 = size;
	
	private double a, b;
	private double t = 0.0;
	
	private int grad = 0;
	
	private int mov = 0;
	private int prevMov = 0;

	public Starter() {	
		setTitle("Smiljanic-Marko-a1");
		setSize(600,400);
		setLocation(200,200);
		myCanvas = new GLCanvas();
		myCanvas.addGLEventListener(this);
		getContentPane().add(myCanvas);
		
		JPanel topPanel = new JPanel();
		this.add(topPanel, BorderLayout.NORTH);
		JButton movButton = new JButton ("Swap Movement");
		ChangeMov chMov = new ChangeMov();
		chMov.target(this);
		movButton.setAction(chMov);
		topPanel.add(movButton);
		
		JButton cirButton = new JButton ("Circular Movement");
		CircularMov circMov = new CircularMov();
		circMov.target(this);
		cirButton.setAction(circMov);
		topPanel.add(cirButton);
		
		JComponent contentPane = (JComponent) this.getContentPane();
		int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap = contentPane.getInputMap(mapName);
		KeyStroke cKey = KeyStroke.getKeyStroke('c');
		imap.put(cKey, "color");
		ActionMap amap = contentPane.getActionMap();
		ChangeGrad chGrad = new ChangeGrad();
		chGrad.target(this);
		amap.put("color", chGrad);
		this.requestFocus();
		
		this.addMouseWheelListener(this);
		
		setVisible(true);
		FPSAnimator animator = new FPSAnimator(myCanvas, 30);
		animator.start();
	}

	public void display(GLAutoDrawable drawable) {	
		GL4 gl = (GL4) GLContext.getCurrentGL();
		gl.glUseProgram(rendering_program);
		
		float bkg[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		FloatBuffer bkgBuffer = Buffers.newDirectFloatBuffer(bkg);
		gl.glClearBufferfv(GL_COLOR, 0, bkgBuffer);
		
		//Changing size from scrolling
		if(scrollDown) {
			x1 += 0.01;
			x2 -= 0.01;
			y1 -= 0.01;
			y2 -= 0.01;
			y3 += 0.01;
			
			if(size < 0.5f) {
				size += .01f;
			}
			
			scrollDown = false;
		}
		else if(scrollUp) {
			x1 -= 0.01;
			x2 += 0.01;
			y1 += 0.01;
			y2 += 0.01;
			y3 -= 0.01;
			
			if(size  > 0.0f) {
				size -= .01f;
			}
			
			scrollUp = false;
		}
		
		//Horizontal Movement
		if(mov == 0){
			xMover += xInc;
			if (xMover > 1.0f - size) xInc = -0.01f;
			if (xMover < -1.0f + size) xInc = 0.01f;
			x1 += xInc;
			x2 += xInc;
			x3 += xInc;
			if(x1 > 1.0f) {
				xMover = x1;
			}
			else if(x2 < -1.0f) {
				xMover = x2;
			}
		}
		
		//Vertical movement
		else if(mov == 1) {
			yMover += yInc;
			if (yMover > 1.0f - size) yInc = -0.01f;
			if (yMover < -1.0f + size) yInc = 0.01f;
			y1 += yInc;
			y2 += yInc;
			y3 += yInc;
			if(y3 > 1.0f) {
				yMover = y3;			}
			else if(y1 < -1.0f) {
				yMover = y1;
			}
		}
		
		//Circular Movement
		else if( mov == -1 && !scrollDown && !scrollUp){
			a = (double) x1 - size;
			b = (double) y1 + size; 
			x1 = (float) (Math.sqrt((a*a) + (b*b))*Math.cos(t%(2.0*Math.PI)) + size);
			y1 = (float) (Math.sqrt((a*a) + (b*b))*Math.sin(t%(2.0*Math.PI)) - size);
			
			a = (double) x2 + size;
			b = (double) y2 + size;
			x2 = (float) (Math.sqrt((a*a) + (b*b))*Math.cos(t%(2.0*Math.PI)) - size);
			y2 = (float) (Math.sqrt((a*a) + (b*b))*Math.sin(t%(2.0*Math.PI)) - size);
			
			a = (float) x3;
			b = (float) y3 - size;
			x3 = (float) (Math.sqrt((a*a) + (b*b))*Math.cos(t%(2.0*Math.PI)));
			y3 = (float) (Math.sqrt((a*a) + (b*b))*Math.sin(t%(2.0*Math.PI)) + size);
			t+= 0.05;
		}
		
		//Create Uniforms for shader files
		int x1_loc = gl.glGetUniformLocation(rendering_program, "x1");
		gl.glProgramUniform1f(rendering_program, x1_loc, x1);
		int x2_loc = gl.glGetUniformLocation(rendering_program, "x2");
		gl.glProgramUniform1f(rendering_program, x2_loc, x2);
		int x3_loc = gl.glGetUniformLocation(rendering_program, "x3");
		gl.glProgramUniform1f(rendering_program, x3_loc, x3);
		
		int y1_loc = gl.glGetUniformLocation(rendering_program, "y1");
		gl.glProgramUniform1f(rendering_program, y1_loc, y1);
		int y2_loc = gl.glGetUniformLocation(rendering_program, "y2");
		gl.glProgramUniform1f(rendering_program, y2_loc, y2);
		int y3_loc = gl.glGetUniformLocation(rendering_program, "y3");
		gl.glProgramUniform1f(rendering_program, y3_loc, y3);
		
		int grad_loc = gl.glGetUniformLocation(rendering_program, "grad");
		gl.glProgramUniform1i(rendering_program, grad_loc, grad);
		
		gl.glDrawArrays(GL_TRIANGLES, 0, 3);
	}
	
	public void init(GLAutoDrawable drawable) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		rendering_program = createShaderProgram();
		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);
		
		System.out.println("OpenGL version: " + gl.glGetString(GL_VERSION));
		System.out.println("JOGL version: " + Package.getPackage("com.jogamp.opengl").getSpecificationVersion());
		System.out.println("Java version: " + System.getProperty("java.version"));
	}
	
	private int createShaderProgram() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] vertCompiled = new int[1];
		int[] fragCompiled = new int[1];
		int[] linked = new int[1];
		
		String vshaderSource[] = util.readShaderSource("a1/vert.shader");
		String fshaderSource[] = util.readShaderSource("a1/frag.shader");
		int lengths[];
		
		int vShader = gl.glCreateShader(GL_VERTEX_SHADER);
		gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);		
		gl.glCompileShader(vShader);
		
		checkOpenGLError();
		gl.glGetShaderiv(vShader, GL_COMPILE_STATUS, vertCompiled, 0);
		if(vertCompiled[0] == 1)
		{
			System.out.println("vertex compilation successful");
		}
		else {
			System.out.println("vertex compilation failed");
			printShaderLog(vShader);
		}
		
		int fShader = gl.glCreateShader(GL_FRAGMENT_SHADER);
		gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0);
		gl.glCompileShader(fShader);
		
		checkOpenGLError();
		gl.glGetShaderiv(fShader, GL_COMPILE_STATUS, fragCompiled, 0);
		if(fragCompiled[0] == 1)
		{
			System.out.println("fragment compilation successful");
		}
		else {
			System.out.println("fragment compilation failed");
			printShaderLog(fShader);
		}
		
		int vfprogram = gl.glCreateProgram();
		gl.glAttachShader(vfprogram, vShader);
		gl.glAttachShader(vfprogram, fShader);
		gl.glLinkProgram(vfprogram);
		
		checkOpenGLError();
		gl.glGetProgramiv(vfprogram, GL_LINK_STATUS, linked, 0);
		if(linked[0] == 1)
		{
			System.out.println("linking successful");
		}
		else {
			System.out.println("linking failed");
			printProgramLog(vfprogram);
		}
		return vfprogram;
	}
	
	public static void main(String[] args){ new Starter();	}
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {	};
	public void dispose(GLAutoDrawable drawable) {	};
	
	private void printShaderLog(int shader) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] len = new int[1];
		int[] chWrittn= new int[1];
		byte[] log = null;
		
		//determine the length of the shader compilation log
		gl.glGetShaderiv(shader, GL_INFO_LOG_LENGTH, len, 0);
		if (len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetShaderInfoLog(shader, len[0], chWrittn, 0, log, 0);
			System.out.println("Shader Info Log: ");
			for(int i = 0; i < log.length; i++) {
				System.out.print((char) log[i]);
			}
		}
	}
	
	void printProgramLog(int prog) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		int[] len = new int[1];
		int[] chWrittn = new int[1];
		byte[] log = null;
		
		//determine the length of the program compilation log
		gl.glGetProgramiv(prog, GL_INFO_LOG_LENGTH, len, 0);
		if(len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetProgramInfoLog(prog, len[0], chWrittn, 0, log, 0);
			System.out.println("Program Info Log: ");
			for(int i = 0; i < log.length; i++) {
				System.out.println((char) log[i]);
			}
		}
	}
	
	boolean checkOpenGLError() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		boolean foundError = false;
		GLU glu = new GLU();
		int glErr = gl.glGetError();
		while(glErr != GL_NO_ERROR) {
			System.err.println("glError: " + glu.gluErrorString(glErr));
			foundError = true;
			glErr = gl.glGetError();
		}
		return foundError;
	}
	
	public void swapMov() {
		if(mov == 0) {
			mov = 1;
		}
		else if(mov == 1) {
			mov = 0;
		}
		else {
			mov = prevMov;
		}
	}
	
	public void cirMov() {
		if(mov != -1) {
			prevMov = mov;
			mov = -1;
			double a = (double) (x1 - size);
			double b = (double) (y1 + size);
			double temp = b/a;
			if (a > 0.0 && b >= 0.0) {
				t = Math.atan(temp);
			}
			else if(a <= 0.0 && b > 0.0) {
				t = Math.PI + Math.atan(temp);
			}
			else if( a < 0.0 && b <= 0.0) {
				t = Math.PI + Math.atan(temp);
			}
			else {
				t = 2.0*Math.PI + Math.atan(temp);
			}
			tempx = x1;
			tempy = y1;
		}
		else {
			mov = prevMov;
			float finalx = x1 - tempx;
			float finaly = y1 - tempy;
			xMover += finalx;
			yMover += finaly;
		}
	}
	
	public void changeGrad() {
		if(grad == 0) {
			grad = 1;
		}
		else {
			grad = 0;
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		float change = e.getWheelRotation();
		System.out.println("mouseWheelMoved rotation: " + change);
		if(change == 1.0f) {
			scrollUp = true;
		}
		else if(change == -1.0f){
			scrollDown = true;
		}
		change = 0.0f;
		System.out.println("size: " + size);
	}
}