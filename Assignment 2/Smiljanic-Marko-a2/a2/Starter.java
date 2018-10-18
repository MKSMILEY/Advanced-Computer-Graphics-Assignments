/*
Marko Smiljanic
CSc 155 Section 02
Due Date: October 15th
*/
package a2;

import graphicslib3D.*;
import graphicslib3D.GLSLUtils.*;
import graphicslib3D.shape.*;
import java.io.*;
import java.nio.*;
import javax.swing.*;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.*;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GLContext;

public class Starter extends JFrame implements GLEventListener {
	private GLCanvas myCanvas;
	private int rendering_program;
	private int vao[] = new int[1];
	private int vbo[] = new int[15];
	
	private GLSLUtils util = new GLSLUtils();
	
	private int sunTexture;
	private int moonTexture;
	private int planet1Texture;
	private int planet2Texture;
	private int ringTexture;
	private int redTexture;
	private int greenTexture;
	private int blueTexture;
	
	CamObj cam = new CamObj(0.0f, 0.0f, 20.0f);
	DiamondObj sun;
	SphereObj sphere;
	TorusObj torus;
	Vector3D V;
	LineObj xAxis;
	LineObj yAxis;
	LineObj zAxis;
	
	private float moveAmt = 0.05f;
	private boolean axes = true;
	private MatrixStack mvStack = new MatrixStack(20);
	
	public Starter() {
		setTitle("Assigment 2");
		setSize(800, 800);
		myCanvas = new GLCanvas();
		myCanvas.addGLEventListener(this);
		getContentPane().add(myCanvas);
		
		JComponent contentPane = (JComponent) this.getContentPane();
		int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap = contentPane.getInputMap(mapName);
		
		KeyStroke wKey = KeyStroke.getKeyStroke('w');
		imap.put(wKey, "forward");

		KeyStroke aKey = KeyStroke.getKeyStroke('a');
		imap.put(aKey, "left");
		
		KeyStroke sKey = KeyStroke.getKeyStroke('s');
		imap.put(sKey, "back");
				
		KeyStroke dKey = KeyStroke.getKeyStroke('d');
		imap.put(dKey, "right");
		
		KeyStroke eKey = KeyStroke.getKeyStroke('e');
		imap.put(eKey, "down");
		
		KeyStroke qKey = KeyStroke.getKeyStroke('q');
		imap.put(qKey, "up");
		
		KeyStroke upKey = KeyStroke.getKeyStroke("UP");
		imap.put(upKey, "pitch up");
		
		KeyStroke downKey = KeyStroke.getKeyStroke("DOWN");
		imap.put(downKey, "pitch down");
		
		KeyStroke leftKey = KeyStroke.getKeyStroke("LEFT");
		imap.put(leftKey, "yaw left");
		
		KeyStroke rightKey = KeyStroke.getKeyStroke("RIGHT");
		imap.put(rightKey, "yaw right");
		
		KeyStroke spaceKey = KeyStroke.getKeyStroke("SPACE");
		imap.put(spaceKey, "draw lines");
		
		
		ActionMap amap = contentPane.getActionMap();
		
		MovLCom moveL = new MovLCom(cam);
		amap.put("left", moveL);
		
		MovRCom moveR = new MovRCom(cam);
		amap.put("right", moveR);
		
		MovFCom moveF = new MovFCom(cam);
		amap.put("forward", moveF);
		
		MovBCom moveB = new MovBCom(cam);
		amap.put("back", moveB);
		
		MovUCom moveU = new MovUCom(cam);
		amap.put("up", moveU);
		
		MovDCom moveD = new MovDCom(cam);
		amap.put("down", moveD);
		
		PitchDownCom pitchD = new PitchDownCom(cam);
		amap.put("pitch down", pitchD);
		
		PitchUpCom pitchU = new PitchUpCom(cam);
		amap.put("pitch up", pitchU);
		
		YawLeftCom yawL = new YawLeftCom(cam);
		amap.put("yaw left", yawL);
		
		YawRightCom yawR = new YawRightCom(cam);
		amap.put("yaw right", yawR);
		
		ToggleAxesCom togA = new ToggleAxesCom(this);
		amap.put("draw lines", togA);
		
		this.requestFocus();
		
		this.setVisible(true);
		FPSAnimator animator = new FPSAnimator(myCanvas, 50);
		animator.start();
	}
	
	public void display(GLAutoDrawable drawable) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glClear(GL_DEPTH_BUFFER_BIT);
		float bkg[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		FloatBuffer bkgBuffer = Buffers.newDirectFloatBuffer(bkg);
		gl.glClearBufferfv(GL_COLOR, 0, bkgBuffer);

		gl.glClear(GL_DEPTH_BUFFER_BIT);
		
		gl.glUseProgram(rendering_program);
		
		int mv_loc = gl.glGetUniformLocation(rendering_program, "mv_matrix");
		int proj_loc = gl.glGetUniformLocation(rendering_program, "proj_matrix");
		
		float aspect = (float) myCanvas.getWidth() / (float) myCanvas.getHeight();
		Matrix3D pMat = perspective(60.0f, aspect, 0.1f, 1000.0f);
		
		mvStack.pushMatrix(); //push the view matrix onto the stack
		mvStack.multMatrix(cam.getView());
		double amt = (double)(System.currentTimeMillis())/1000.0;
		
		
		gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);
		gl.glEnable(GL_DEPTH_TEST);
		

		if(axes) {
			//-------------------------------- Draw xAxis
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[9]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
		
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[10]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
		
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, redTexture);
		
			gl.glDrawArrays(GL_LINES, 0, 2);
			
			//-------------------------------- Draw yAxis
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[11]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
		
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[12]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
		
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, greenTexture);
		
			gl.glDrawArrays(GL_LINES, 0, 2);
			
			//-------------------------------- Draw zAxis
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[13]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
		
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[14]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
		
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, blueTexture);
		
			gl.glDrawArrays(GL_LINES, 0, 2);
		}
		
		
		//----------------------------------- Sun Object
		mvStack.pushMatrix(); //push sun translation
		mvStack.translate(sun.getPoint().getX(), sun.getPoint().getY(), sun.getPoint().getZ());
		
		
		
		mvStack.pushMatrix(); //push sun rotation
		mvStack.rotate((System.currentTimeMillis())/10.0, 0.0, 1.0, 0.0);
		gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, sunTexture);
		gl.glDrawArrays(GL_TRIANGLES, 0, 24);
		mvStack.popMatrix(); // pop sun rotation
		
		//--------------------------------- First Planet Object
		mvStack.pushMatrix(); //push planet1 translation
		mvStack.translate(Math.sin(amt)*4.0f, 0.0f, Math.cos(amt)*4.0f);
		mvStack.pushMatrix(); //push planet1 rotation
		mvStack.rotate((System.currentTimeMillis())/10.0,0.0,1.0,0.0);
		gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, planet1Texture);
		int numVerts = sphere.getIndices().length;
		gl.glDrawArrays(GL_TRIANGLES, 0, numVerts);
		mvStack.popMatrix(); //pop planet1 rotation
		
		//--------------------------------- First Planet Moon Object
		mvStack.pushMatrix(); //push moon
		mvStack.translate(0.0f, Math.sin(amt)*2.0f, Math.cos(amt)*2.0f);
		mvStack.rotate((System.currentTimeMillis())/10.0,0.0,1.0,0.0);
		mvStack.scale(0.25, 0.25, 0.25);
		gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, moonTexture);
		gl.glDrawArrays(GL_TRIANGLES, 0, numVerts);
		mvStack.popMatrix(); //pop moon
		mvStack.popMatrix(); //pop planet1 translation
		
		//-------------------------------- Second Planet Object
		mvStack.pushMatrix(); //push planet2 translation
		mvStack.translate(Math.sin(amt/2.0f)*8.0f, 0.0f, Math.cos(amt/2.0f)*8.0f);
		mvStack.pushMatrix();
		mvStack.scale(0.75f, 0.75f, 0.75f);
		mvStack.pushMatrix(); //push planet2 rotation
		mvStack.rotate((System.currentTimeMillis())/10.0,0.0,1.0,0.0);
		gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, planet2Texture);
		gl.glDrawArrays(GL_TRIANGLES, 0, numVerts);
		mvStack.popMatrix(); //pop planet2 rotation
		mvStack.popMatrix(); //pop planet2 scale
		
		//-------------------------------- Second Planet Moon Object
		mvStack.pushMatrix(); //push moon
		mvStack.translate(0.0f, 0.0f, 0.0f);
		mvStack.rotate((System.currentTimeMillis())/10.0, (System.currentTimeMillis())/10.0, 1.0, 0.0);
		gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[5]);
		gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(0);
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[6]);
		gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		gl.glEnableVertexAttribArray(1);
		gl.glActiveTexture(GL_TEXTURE0);
		gl.glBindTexture(GL_TEXTURE_2D, ringTexture);
		int numIndices = torus.getIndices().length;
		gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo[8]);
		gl.glDrawElements(GL_TRIANGLES, numIndices, GL_UNSIGNED_INT, 0);
		
		mvStack.popMatrix(); //pop moon
		mvStack.popMatrix(); //pop planet2 translation
		mvStack.popMatrix(); //pop sun translation
		mvStack.popMatrix(); //pop view matrix
	}
	
	public void init(GLAutoDrawable drawable) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		rendering_program = createShaderProgram();

		//TODO add other inits

		sun = new DiamondObj(0.0f, 0.0f, 0.0f);
		sphere = new SphereObj(0.0f, 0.0f, 0.0f, 48);
		torus = new TorusObj(0.0f, 0.0f, 0.0f, 1.5f, 0.1f, 96);
		xAxis = new LineObj(0.0f, 0.0f, 0.0f, 0);
		yAxis = new LineObj(0.0f, 0.0f, 0.0f, 1);
		zAxis = new LineObj(0.0f, 0.0f, 0.0f, 2);
		
		setupVertices();
		
		sunTexture = loadTexture("sunmap.jpg");
		moonTexture = loadTexture("moonmap.jpg");
		planet1Texture = loadTexture("earthmap.jpg");
		planet2Texture = loadTexture("marsmap.jpg");
		ringTexture = loadTexture("ringmap.jpg");
		redTexture = loadTexture("redmap.jpg");
		greenTexture = loadTexture("greenmap.jpg");
		blueTexture = loadTexture("bluemap.jpg");
	}
	
	private void setupVertices() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		Vertex3D[] vertices = sphere.getVertices();
		int[] indices = sphere.getIndices();
		
		float[] pvalues = new float[indices.length*3];
		float[] tvalues = new float[indices.length*2];
		float[] nvalues = new float[indices.length*3];
		
		for (int i=0; i<indices.length; i++)
		{	pvalues[i*3] = (float) (vertices[indices[i]]).getX();
			pvalues[i*3+1] = (float) (vertices[indices[i]]).getY();
			pvalues[i*3+2] = (float) (vertices[indices[i]]).getZ();
			tvalues[i*2] = (float) (vertices[indices[i]]).getS();
			tvalues[i*2+1] = (float) (vertices[indices[i]]).getT();
			nvalues[i*3] = (float) (vertices[indices[i]]).getNormalX();
			nvalues[i*3+1]= (float)(vertices[indices[i]]).getNormalY();
			nvalues[i*3+2]=(float) (vertices[indices[i]]).getNormalZ();
		}
		float[] texture_coordinates =
		{	0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
		};
		
		float[] xVertices = xAxis.getVertices();
		float[] x_coordinates = {	0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f };
		
		float[] yVertices = yAxis.getVertices();
		float[] y_coordinates = {	0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f };
		
		float[] zVertices = zAxis.getVertices();
		float[] z_coordinates = {	0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f };
		
		
		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);
		gl.glGenBuffers(vbo.length, vbo, 0);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
		FloatBuffer sunBuf = Buffers.newDirectFloatBuffer(sun.getVertices());
		gl.glBufferData(GL_ARRAY_BUFFER, sunBuf.limit()*4, sunBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
		FloatBuffer sunTexBuf = Buffers.newDirectFloatBuffer(texture_coordinates);
		gl.glBufferData(GL_ARRAY_BUFFER, sunTexBuf.limit()*4, sunTexBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
		FloatBuffer vertBuf = Buffers.newDirectFloatBuffer(pvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, vertBuf.limit()*4, vertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
		FloatBuffer texBuf = Buffers.newDirectFloatBuffer(tvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, texBuf.limit()*4, texBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[4]);
		FloatBuffer norBuf = Buffers.newDirectFloatBuffer(nvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, norBuf.limit()*4, norBuf, GL_STATIC_DRAW);
		
		Vertex3D[] tVertices = torus.getVertices();
		int[] tIndices = torus.getIndices();
		
		float[] tpvalues = new float[tVertices.length*3];
		float[] ttvalues = new float[tVertices.length*2];
		float[] tnvalues = new float[tVertices.length*3];
		
		for (int i=0; i<tVertices.length; i++)
		{	tpvalues[i*3] = (float) (tVertices[i]).getX();
			tpvalues[i*3+1] = (float) (tVertices[i]).getY();
			tpvalues[i*3+2] = (float) (tVertices[i]).getZ();
			ttvalues[i*2] = (float) (tVertices[i]).getS();
			ttvalues[i*2+1] = (float) (tVertices[i]).getT();
			tnvalues[i*3] = (float) (tVertices[i]).getNormalX();
			tnvalues[i*3+1]= (float)(tVertices[i]).getNormalY();
			tnvalues[i*3+2]=(float) (tVertices[i]).getNormalZ();
		}
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[5]);
		FloatBuffer tVertBuf = Buffers.newDirectFloatBuffer(tpvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, tVertBuf.limit()*4, tVertBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[6]);
		FloatBuffer tTexBuf = Buffers.newDirectFloatBuffer(ttvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, tTexBuf.limit()*4, tTexBuf, GL_STATIC_DRAW);

		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[7]);
		FloatBuffer tNorBuf = Buffers.newDirectFloatBuffer(tnvalues);
		gl.glBufferData(GL_ARRAY_BUFFER, tNorBuf.limit()*4, tNorBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo[8]);  // indices
		IntBuffer idxBuf = Buffers.newDirectIntBuffer(tIndices);
		gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, idxBuf.limit()*4, idxBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[9]);
		FloatBuffer xVertBuf = Buffers.newDirectFloatBuffer(xVertices);
		gl.glBufferData(GL_ARRAY_BUFFER, xVertBuf.limit()*4, xVertBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[10]);
		FloatBuffer xTexBuf = Buffers.newDirectFloatBuffer(x_coordinates);
		gl.glBufferData(GL_ARRAY_BUFFER, xTexBuf.limit()*4, xTexBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[11]);
		FloatBuffer yVertBuf = Buffers.newDirectFloatBuffer(yVertices);
		gl.glBufferData(GL_ARRAY_BUFFER, yVertBuf.limit()*4, yVertBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[12]);
		FloatBuffer yTexBuf = Buffers.newDirectFloatBuffer(y_coordinates);
		gl.glBufferData(GL_ARRAY_BUFFER, yTexBuf.limit()*4, yTexBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[13]);
		FloatBuffer zVertBuf = Buffers.newDirectFloatBuffer(zVertices);
		gl.glBufferData(GL_ARRAY_BUFFER, zVertBuf.limit()*4, zVertBuf, GL_STATIC_DRAW);
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[14]);
		FloatBuffer zTexBuf = Buffers.newDirectFloatBuffer(z_coordinates);
		gl.glBufferData(GL_ARRAY_BUFFER, zTexBuf.limit()*4, zTexBuf, GL_STATIC_DRAW);
	
	}
	
	public void toggleAxes() {
		axes = !axes;
	}
	
	private Matrix3D perspective(float fovy, float aspect, float n, float f) {
		float q = 1.0f / ((float) Math.tan(Math.toRadians(0.5f * fovy)));
		float A = q / aspect;
		float B = (n + f) / (n - f);
		float C = (2.0f * n * f) / (n - f);
		Matrix3D r = new Matrix3D();
		r.setElementAt(0, 0, A);
		r.setElementAt(1, 1, q);
		r.setElementAt(2, 2, B);
		r.setElementAt(3, 2, -1.0f);
		r.setElementAt(2, 3, C);
		r.setElementAt(3, 3, 0.0f);
		return r;
	}
	
	public static void main(String[] args) {new Starter();}
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	public void dispose(GLAutoDrawable drawable) {}
	
	private int createShaderProgram() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		String vshaderSource[] = util.readShaderSource("a2/vert.shader");
		String fshaderSource[] = util.readShaderSource("a2/frag.shader");
		
		int vShader = gl.glCreateShader(GL_VERTEX_SHADER);
		int fShader = gl.glCreateShader(GL_FRAGMENT_SHADER);
		
		gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);
		gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0);
		
		gl.glCompileShader(vShader);
		gl.glCompileShader(fShader);
		
		int vfprogram = gl.glCreateProgram();
		gl.glAttachShader(vfprogram, vShader);
		gl.glAttachShader(vfprogram, fShader);
		gl.glLinkProgram(vfprogram);
		return vfprogram;
	}
	
		private int loadTexture(String textureFileName)
	{	GL4 gl = (GL4) GLContext.getCurrentGL();
		BufferedImage textureImage = getBufferedImage(textureFileName);
		byte[ ] imgRGBA = getRGBAPixelData(textureImage);
		ByteBuffer rgbaBuffer = Buffers.newDirectByteBuffer(imgRGBA);
		
		int[ ] textureIDs = new int[1];				// array to hold generated texture IDs
		gl.glGenTextures(1, textureIDs, 0);
		int textureID = textureIDs[0];				// ID for the 0th texture object
		gl.glBindTexture(GL_TEXTURE_2D, textureID);	// specifies the currently active 2D texture
		gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,	// MIPMAP Level, number of color components
			textureImage.getWidth(), textureImage.getHeight(), 0,	// image size, border (ignored)
			GL_RGBA, GL_UNSIGNED_BYTE,				// pixel format and data type
			rgbaBuffer);							// buffer holding texture data
		
		// build a mipmap and use anisotropic filtering if available
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
		
		if (gl.isExtensionAvailable("GL_EXT_texture_filter_anisotropic"))
		{	float anisoset[] = new float[1];
			gl.glGetFloatv(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, anisoset, 0);
			gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAX_ANISOTROPY_EXT, anisoset[0]);
		}	
		return textureID;
	}
	
	private BufferedImage getBufferedImage(String fileName)
	{	BufferedImage img;
		try { img = ImageIO.read(new File(fileName)); }
		catch (IOException e)
		{	System.err.println("Error reading '" + fileName + '"'); throw new RuntimeException(e); }
		return img;
	}
	
	private byte[ ] getRGBAPixelData(BufferedImage img)
	{	byte[ ] imgRGBA;
		int height = img.getHeight(null);
		int width = img.getWidth(null);
		
		WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, width, height, 4, null);
		ComponentColorModel colorModel = new ComponentColorModel(
			ColorSpace.getInstance(ColorSpace.CS_sRGB),
			new int[ ] { 8, 8, 8, 8 }, true, false, // bits, has Alpha, isAlphaPreMultiplied
			ComponentColorModel.TRANSLUCENT, 	// transparency
			DataBuffer.TYPE_BYTE); 			// data transfer type
		BufferedImage newImage = new BufferedImage(colorModel, raster, false, null);
		
		// use an affine transform to flip the image to conform to OpenGL orientation.
		// In Java the origin is at the upper left of the window.
		// In OpenGL the origin is at the lower left of the canvas.
		AffineTransform gt = new AffineTransform();
		gt.translate(0, height);
		gt.scale(1, -1d);
		
		Graphics2D g = newImage.createGraphics();
		g.transform(gt);
		g.drawImage(img, null, null);
		g.dispose();
		
		DataBufferByte dataBuf = (DataBufferByte) raster.getDataBuffer();
		imgRGBA = dataBuf.getData();
		return imgRGBA;
	}
}