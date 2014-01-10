package leveleditor;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Main.Menu;
import Maze.Mazescont;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

/**
 * A frame for us to draw on using OpenGL.
 * 
 * @author 
 * 
 */
public class LevelEditor implements GLEventListener, MouseListener {
	static final long serialVersionUID = 1;

	// Screen size.
	public  int screenWidth = 1210;
	public int Width = 1210;

	public int screenHeight = 505;
	public int Height = 505;

	private  int rast = 20;
	private int rastwidth = screenHeight-100;
	private int rastspace = 50;
	private int buttonpos = rastwidth+100;
	private int[][] Mazeconf = new int[rast+2][rast+2];
	private int[][] Mazeconf2 = new int[rast+2][rast+2];
	private int[][] Mazeconf3 = new int[rast+2][rast+2];
	private int[][] SaveMaze = new int[22][22];
	private int unit = rastwidth/rast;
	private float buttonSize = unit *2;
	private byte[] vloer18 = Image.loadImage("vloer18.jpg");
	private byte[] vloer36 = Image.loadImage("vloer36.jpg");
	private byte[] muur18 = Image.loadImage("muur18.jpg");
	private byte[] muur36 = Image.loadImage("muur36.jpg");
	private byte[] trap18 = Image.loadImage("trapaf18.jpg");
	private byte[] trap36 = Image.loadImage("trapaf36.jpg");
	private byte[] smart18 = Image.loadImage("smart18.jpg");
	private byte[] smart36 = Image.loadImage("smart36.jpg");
	private byte[] trapop18 = Image.loadImage("trapop18.jpg");
	private byte[] trapop36 = Image.loadImage("trapop36.jpg");
	private byte[] deur18 = Image.loadImage("deur18.jpg");
	private byte[] deur36 = Image.loadImage("deur36.jpg");
	private byte[] background = Image.loadImage("achtergrond.png");

	protected Texture muurText, floorText, smartText, trapText, trapopText;
	// A GLCanvas is a component that can be added to a frame. The drawing
	// happens on this component.
	private GLCanvas canvas;

	private static final byte DM_VLOER = 0;
	private static final byte DM_MUUR = 1;
	private static final byte DM_TRAP = 2;
	private static final byte DM_SMART = 3;
	private static final byte DM_DEUR = 4;
	private byte drawMode = DM_VLOER;

	private ArrayList<Point2D.Float> points;
	private ArrayList<int[]> Mazes;

	private Menu that;

	public boolean dispose = false;

	/**
	 * When instantiating, a GLCanvas is added for us to play with. An animator
	 * is created to continuously render the canvas.
	 */
	public LevelEditor(final Menu that) {
		//		super("MinorProject");
		this.that = that;

		for (int i = 0; i<Mazeconf.length; i++){
			for (int j = 0; j<Mazeconf.length; j++){
				Mazeconf[i][j]=0;
				Mazeconf2[i][j]=0;
				Mazeconf3[i][j]=0;
			}
		}

		drawVertical1();
		drawHorizontal1();
		drawVertical2();
		drawHorizontal2();
		drawVertical3();
		drawHorizontal3();
		for (int i = 0; i<Mazeconf.length;i++){
			Mazeconf[0][i]=1;
			Mazeconf[Mazeconf.length-1][i]= 1;
			Mazeconf[i][0]=1;
			Mazeconf[i][Mazeconf.length-1]= 1;
			Mazeconf2[0][i]=1;
			Mazeconf2[Mazeconf.length-1][i]= 1;
			Mazeconf2[i][0]=1;
			Mazeconf2[i][Mazeconf.length-1]= 1;
			Mazeconf3[0][i]=1;
			Mazeconf3[Mazeconf.length-1][i]= 1;
			Mazeconf3[i][0]=1;
			Mazeconf3[i][Mazeconf.length-1]= 1;
		}

		Mazeconf3[1][20]=5;
		Mazeconf[20][1]=4;
		points = new ArrayList<Point2D.Float>();

		// Set the desired size and background color of the frame
		that.setSize(screenWidth, screenHeight);
		//setBackground(Color.white);
		that.setBackground(new Color(0.95f, 0.95f, 0.95f));

		// When the "X" close button is called, the application should exit.
		that.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				that.dispose();
			}
		});

		// The OpenGL capabilities should be set before initializing the
		// GLCanvas. We use double buffering and hardware acceleration.
		//		GLCapabilities caps = new GLCapabilities();
		//		caps.setDoubleBuffered(true);
		//		caps.setHardwareAccelerated(true);
		//
		//		// Create a GLCanvas with the specified capabilities and add it to this
		//		// frame. Now, we have a canvas to draw on using JOGL.
		//		canvas = new GLCanvas(caps);
		//		add(canvas);
		//
		//		// Set the canvas' GL event listener to be this class. Doing so gives
		//		// this class control over what is rendered on the GL canvas.
		//		canvas.addGLEventListener(this);
		//
		//		// Also add this class as mouse listener, allowing this class to react
		//		// to mouse events that happen inside the GLCanvas.
		//		canvas.addMouseListener(this);
		//
		//		// An Animator is a JOGL help class that can be used to make sure our
		//		// GLCanvas is continuously being re-rendered. The animator is run on a
		//		// separate thread from the main thread.
		//		Animator anim = new Animator(canvas);
		//		anim.start();
		//
		//		// With everything set up, the frame can now be displayed to the user.
		//		setVisible(true);

	}

	private void drawVertical1(){
		for (int k = 2; k<Mazeconf.length-2;k++){
			Mazeconf[k][2]=1;
		}
		for (int k = 0; k<Mazeconf.length-6;k++){
			Mazeconf[k][4]=1;
		}
		for (int k = 0; k<Mazeconf.length-6;k++){
			Mazeconf[k][6]=1;
			Mazeconf[8][6]=0;
		}
		for (int k = 2; k<Mazeconf.length-16;k++){
			Mazeconf[k][8]=1;
		}
		for (int k = 0; k<Mazeconf.length-14;k++){
			Mazeconf[k][12]=1;
		}
		for (int k = 2; k<Mazeconf.length-12;k++){
			Mazeconf[k][14]=1;
		}
		for (int k = 0; k<Mazeconf.length-13;k++){
			Mazeconf[k][16]=1;
		}
		for (int k = 10; k<Mazeconf.length-8;k++){
			Mazeconf[k][17]=1;
		}
		for (int k = 4; k<Mazeconf.length-15;k++){
			Mazeconf[k][18]=1;
		}
		for (int k = 6; k<Mazeconf.length-13;k++){
			Mazeconf[k][19]=1;
		}
	}

	private void drawHorizontal1(){
		for (int j = 3;j<Mazeconf.length-2;j++){
			Mazeconf[19][j]=1;
		}
		for (int j = 4;j<Mazeconf.length;j++){
			Mazeconf[17][j]=1;
		}
		for (int j = 6;j<Mazeconf.length-2;j++){
			Mazeconf[15][j]=1;
		}
		for (int j = 8;j<Mazeconf.length-2;j++){
			Mazeconf[13][j]=1;
		}
		for (int j = 8;j<Mazeconf.length-4;j++){
			Mazeconf[11][j]=1;
			Mazeconf[11][13]=0;
		}
		for (int j = 18;j<Mazeconf.length;j++){
			Mazeconf[10][j]=1;
		}
		for (int j = 8;j<Mazeconf.length-7;j++){
			Mazeconf[9][j]=1;
		}
		for (int j = 17;j<Mazeconf.length-2;j++){
			Mazeconf[8][j]=1;
		}
		for (int j = 8;j<Mazeconf.length-9;j++){
			Mazeconf[7][j]=1;
		}
		for (int j = 8;j<Mazeconf.length-11;j++){
			Mazeconf[5][j]=1;
		}
		for (int j = 8;j<Mazeconf.length-9;j++){
			Mazeconf[2][j]=1;
		}
		for (int j = 18;j<Mazeconf.length;j++){
			Mazeconf[2][j]=1;
		}


		Mazeconf[12][8]=1;
		Mazeconf[4][10]=1;
		Mazeconf[12][19]=1;
		Mazeconf[4][19]=1;

	}

	private void drawVertical2(){
		for (int k = 2; k<Mazeconf2.length-2;k++){
			Mazeconf2[k][2]=1;
		}
		for (int k = 0; k<Mazeconf2.length-6;k++){
			Mazeconf2[k][4]=1;
		}
		for (int k = 0; k<Mazeconf2.length-6;k++){
			Mazeconf2[k][6]=1;
			Mazeconf2[8][6]=0;
		}
		for (int k = 2; k<Mazeconf2.length-16;k++){
			Mazeconf2[k][8]=1;
		}
		for (int k = 0; k<Mazeconf2.length-14;k++){
			Mazeconf2[k][12]=1;
		}
		for (int k = 2; k<Mazeconf2.length-12;k++){
			Mazeconf2[k][14]=1;
		}
		for (int k = 0; k<Mazeconf2.length-13;k++){
			Mazeconf2[k][16]=1;
		}
		for (int k = 10; k<Mazeconf2.length-8;k++){
			Mazeconf2[k][17]=1;
		}
		for (int k = 4; k<Mazeconf2.length-15;k++){
			Mazeconf2[k][18]=1;
		}
		for (int k = 6; k<Mazeconf2.length-13;k++){
			Mazeconf2[k][19]=1;
		}
	}

	private void drawHorizontal2(){
		for (int j = 3;j<Mazeconf2.length-2;j++){
			Mazeconf2[19][j]=1;
		}
		for (int j = 4;j<Mazeconf2.length;j++){
			Mazeconf2[17][j]=1;
		}
		for (int j = 6;j<Mazeconf2.length-2;j++){
			Mazeconf2[15][j]=1;
		}
		for (int j = 8;j<Mazeconf2.length-2;j++){
			Mazeconf2[13][j]=1;
		}
		for (int j = 8;j<Mazeconf2.length-4;j++){
			Mazeconf2[11][j]=1;
			Mazeconf2[11][13]=0;
		}
		for (int j = 18;j<Mazeconf2.length;j++){
			Mazeconf2[10][j]=1;
		}
		for (int j = 8;j<Mazeconf2.length-7;j++){
			Mazeconf2[9][j]=1;
		}
		for (int j = 17;j<Mazeconf2.length-2;j++){
			Mazeconf2[8][j]=1;
		}
		for (int j = 8;j<Mazeconf2.length-9;j++){
			Mazeconf2[7][j]=1;
		}
		for (int j = 8;j<Mazeconf2.length-11;j++){
			Mazeconf2[5][j]=1;
		}
		for (int j = 8;j<Mazeconf2.length-9;j++){
			Mazeconf2[2][j]=1;
		}
		for (int j = 18;j<Mazeconf2.length;j++){
			Mazeconf2[2][j]=1;
		}


		Mazeconf2[12][8]=1;
		Mazeconf2[4][10]=1;
		Mazeconf2[12][19]=1;
		Mazeconf2[4][19]=1;

	}

	private void drawVertical3(){
		for (int k = 2; k<Mazeconf3.length-2;k++){
			Mazeconf3[k][2]=1;
		}
		for (int k = 0; k<Mazeconf3.length-6;k++){
			Mazeconf3[k][4]=1;
		}
		for (int k = 0; k<Mazeconf3.length-6;k++){
			Mazeconf3[k][6]=1;
			Mazeconf3[8][6]=0;
		}
		for (int k = 2; k<Mazeconf3.length-16;k++){
			Mazeconf3[k][8]=1;
		}
		for (int k = 0; k<Mazeconf3.length-14;k++){
			Mazeconf3[k][12]=1;
		}
		for (int k = 2; k<Mazeconf3.length-12;k++){
			Mazeconf3[k][14]=1;
		}
		for (int k = 0; k<Mazeconf3.length-13;k++){
			Mazeconf3[k][16]=1;
		}
		for (int k = 10; k<Mazeconf3.length-8;k++){
			Mazeconf3[k][17]=1;
		}
		for (int k = 4; k<Mazeconf3.length-15;k++){
			Mazeconf3[k][18]=1;
		}
		for (int k = 6; k<Mazeconf3.length-13;k++){
			Mazeconf3[k][19]=1;
		}
	}

	private void drawHorizontal3(){
		for (int j = 3;j<Mazeconf3.length-2;j++){
			Mazeconf3[19][j]=1;
		}
		for (int j = 4;j<Mazeconf3.length;j++){
			Mazeconf3[17][j]=1;
		}
		for (int j = 6;j<Mazeconf3.length-2;j++){
			Mazeconf3[15][j]=1;
		}
		for (int j = 8;j<Mazeconf3.length-2;j++){
			Mazeconf3[13][j]=1;
		}
		for (int j = 8;j<Mazeconf3.length-4;j++){
			Mazeconf3[11][j]=1;
			Mazeconf3[11][13]=0;
		}
		for (int j = 18;j<Mazeconf3.length;j++){
			Mazeconf3[10][j]=1;
		}
		for (int j = 8;j<Mazeconf3.length-7;j++){
			Mazeconf3[9][j]=1;
		}
		for (int j = 17;j<Mazeconf3.length-2;j++){
			Mazeconf3[8][j]=1;
		}
		for (int j = 8;j<Mazeconf3.length-9;j++){
			Mazeconf3[7][j]=1;
		}
		for (int j = 8;j<Mazeconf3.length-11;j++){
			Mazeconf3[5][j]=1;
		}
		for (int j = 8;j<Mazeconf3.length-9;j++){
			Mazeconf3[2][j]=1;
		}
		for (int j = 18;j<Mazeconf3.length;j++){
			Mazeconf3[2][j]=1;
		}


		Mazeconf3[12][8]=1;
		Mazeconf3[4][10]=1;
		Mazeconf3[12][19]=1;
		Mazeconf3[4][19]=1;

	}

	@Override
	/**
	 * A function defined in GLEventListener. It is called once, when the frame containing the GLCanvas 
	 * becomes visible. In this assignment, there is no moving ´camera´, so the view and projection can 
	 * be set at initialization. 
	 */
	public void init(GLAutoDrawable drawable) {
		// Retrieve the OpenGL handle, this allows us to use OpenGL calls.
		GL gl = drawable.getGL();

		// Set the matrix mode to GL_PROJECTION, allowing us to manipulate the
		// projection matrix
		gl.glMatrixMode(GL.GL_PROJECTION);

		// Always reset the matrix before performing transformations, otherwise
		// those transformations will stack with previous transformations!
		gl.glLoadIdentity();

		/*
		 * glOrtho performs an "orthogonal projection" transformation on the
		 * active matrix. In this case, a simple 2D projection is performed,
		 * matching the viewing frustum to the screen size.
		 */
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);

		// Set the matrix mode to GL_MODELVIEW, allowing us to manipulate the
		// model-view matrix.
		gl.glMatrixMode(GL.GL_MODELVIEW);

		// We leave the model view matrix as the identity matrix. As a result,
		// we view the world 'looking forward' from the origin.
		gl.glLoadIdentity();

		// We have a simple 2D application, so we do not need to check for depth
		// when rendering.
		gl.glDisable(GL.GL_DEPTH_TEST);
	}

	@Override
	/**
	 * A function defined in GLEventListener. This function is called many times per second and should 
	 * contain the rendering code.
	 */
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		// clear the screen.
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		//Draw the background image
		Image.drawImage(gl, 0, 0, 1210, 474, background);
		// Draw the buttons.
		drawButtons(gl);

		// Draw a figure based on the current draw mode and user input
		drawFigure(gl);
		//Draw Maze
		drawMaze(gl);
		//Draw Raster
		drawRaster(gl);
		//		gl.glColor3f(0.5f, 0.5f, 0f);
		//		boxOnScreen(gl, 10*unit, 10*unit+1, unit);
		// Flush the OpenGL buffer, outputting the result to the screen.
		gl.glFlush();

	}

	private void drawText(GL gl, String text, int x, int y)
	{
		int length = text.length();
		GLUT glut = new GLUT();
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glRasterPos2i(x, y); // raster position in 2D
		for(int i=0; i<length; i++)
		{
			glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_12, text.charAt(i)); // generation of characters in our text with 9 by 15 GLU font
		}
	}

	private void drawMaze(GL gl){
		for (int i = 1; i<Mazeconf.length-1;i++){
			for (int j = 1;j<Mazeconf.length-1;j++){
				switch (Mazeconf[j][i]){
				case 0:
					//					gl.glColor3f(1f, 1f,1f);
					//					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit,(rast-j)*unit,18,18, vloer18);
					break;
				case 1:
					//					gl.glColor3f(0.0f, 0.0f, 0.0f);
					//					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit,(rast-j)*unit,18,18, muur18);
					break;
				case 2:
					//					gl.glColor3f(0.5f, 0.5f, 0.0f);
					//					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit,(rast-j)*unit,18,18, trap18);
					break;
				case 3:
					//					gl.glColor3f(0.5f, 0.0f, 0.5f);
					//					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit,(rast-j)*unit,18,18, smart18);
					break;
				case 4:
					gl.glColor3f(0.5f, 0.5f, 0.5f);
					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					break;
				case 5:
					gl.glColor3f(0.75f, 0f, 0.5f);
					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					break;
				case 6:
					//					gl.glColor3f(0.5f, 0.5f, 0.5f);
					//					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit,(rast-j)*unit,18,18, trapop18);
					break;
				case 7:
					//					gl.glColor3f(0.5f, 0.0f, 0.5f);
					//					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit,(rast-j)*unit,18,18, deur18);
					break;
				}
			}
		}
		for (int i = 1; i<Mazeconf2.length-1;i++){
			for (int j = 1;j<Mazeconf2.length-1;j++){
				switch (Mazeconf2[j][i]){
				case 0:
					//					gl.glColor3f(1f, 1f,1f);
					//					boxOnScreen(gl, (rast-i)*unit+rastwidth+rastspace, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+rastwidth+rastspace,(rast-j)*unit,18,18, vloer18);
					break;
				case 1:
					//					gl.glColor3f(0.0f, 0.0f, 0.0f);
					//					boxOnScreen(gl, (rast-i)*unit+rastwidth+rastspace, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+rastwidth+rastspace,(rast-j)*unit,18,18, muur18);
					break;
				case 2:
					//					gl.glColor3f(0.5f, 0.5f, 0.0f);
					//					boxOnScreen(gl, (rast-i)*unit+rastwidth+rastspace, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+rastwidth+rastspace,(rast-j)*unit,18,18, trap18);
					break;
				case 3:
					//					gl.glColor3f(0.5f, 0.0f, 0.5f);
					//					boxOnScreen(gl, (rast-i)*unit+rastwidth+rastspace, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+rastwidth+rastspace,(rast-j)*unit,18,18, smart18);
					break;
				case 4:
					gl.glColor3f(0.5f, 0.5f, 0.5f);
					boxOnScreen(gl, (rast-i)*unit+rastwidth+rastspace, (rast-j)*unit, unit);
					break;
				case 5:
					gl.glColor3f(0.75f, 0f, 0.5f);
					boxOnScreen(gl, (rast-i)*unit+rastwidth+rastspace, (rast-j)*unit, unit);
					break;
				case 6:
					//					gl.glColor3f(0.5f, 0.5f, 0.5f);
					//					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+rastwidth+rastspace,(rast-j)*unit,18,18, trapop18);
					break;
				case 7:
					//					gl.glColor3f(0.5f, 0.0f, 0.5f);
					//					boxOnScreen(gl, (rast-i)*unit+rastwidth+rastspace,(rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+rastwidth+rastspace,(rast-j)*unit,18,18, deur18);
					break;
				}
			}
		}
		for (int i = 1; i<Mazeconf3.length-1;i++){
			for (int j = 1;j<Mazeconf3.length-1;j++){
				switch (Mazeconf3[j][i]){
				case 0:
					//					gl.glColor3f(1f, 1f,1f);
					//					boxOnScreen(gl, (rast-i)*unit+2*(rastwidth+rastspace), (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+2*(rastwidth+rastspace),(rast-j)*unit,18,18, vloer18);
					break;
				case 1:
					//					gl.glColor3f(0.0f, 0.0f, 0.0f);
					//					boxOnScreen(gl, (rast-i)*unit+2*(rastwidth+rastspace), (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+2*(rastwidth+rastspace),(rast-j)*unit,18,18, muur18);
					break;
				case 2:
					//					gl.glColor3f(0.5f, 0.5f, 0.0f);
					//					boxOnScreen(gl, (rast-i)*unit+2*(rastwidth+rastspace), (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+2*(rastwidth+rastspace),(rast-j)*unit,18,18, trap18);
					break;
				case 3:
					//					gl.glColor3f(0.5f, 0.0f, 0.5f);
					//					boxOnScreen(gl, (rast-i)*unit+2*(rastwidth+rastspace), (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+2*(rastwidth+rastspace),(rast-j)*unit,18,18, smart18);
					break;
				case 4:
					gl.glColor3f(0.5f, 0.5f, 0.5f);
					boxOnScreen(gl, (rast-i)*unit+2*(rastwidth+rastspace), (rast-j)*unit, unit);
					break;
				case 5:
					gl.glColor3f(0.75f, 0f, 0.5f);
					boxOnScreen(gl, (rast-i)*unit+2*(rastwidth+rastspace), (rast-j)*unit, unit);
					break;
				case 6:
					//					gl.glColor3f(0.5f, 0.5f, 0.5f);
					//					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					Image.drawImage(gl, (rast-i)*unit+2*(rastwidth+rastspace),(rast-j)*unit,18,18, trapop18);
					break;
				case 7:
					Image.drawImage(gl, (rast-i)*unit+2*(rastwidth+rastspace),(rast-j)*unit,18,18, deur18);
					break;
				}
			}
		}
		//		gl.glColor3f(0.5f, 0f, 0f);
		//		boxOnScreen(gl, (10)*unit, (10)*unit+1, unit);
	}


	private void drawRaster(GL gl){
		gl.glColor3f(0f, 0f, 0f);
		gl.glLineWidth(1);
		//level 1
		drawText(gl,"Level 1",  0,(int) (rastwidth+10));
		for (int i = 0; i<=rast; i++){
			lineOnScreen(gl, i*unit, 0,i*unit, rastwidth-11);
			lineOnScreen(gl, 0, i*unit,rastwidth-12, i*unit);
		}
		//level 2
		drawText(gl,"Level 2",  (int) rastwidth+rastspace,(int) (rastwidth+10));
		for (int i = 0; i<=rast; i++){
			lineOnScreen(gl, i*unit+rastwidth+rastspace, 0,i*unit+rastwidth+rastspace, rastwidth-11);
			lineOnScreen(gl, rastwidth+rastspace, i*unit,2*rastwidth+rastspace-12, i*unit);
		}
		//level 3
		drawText(gl,"Level 3",  (int) 2*(rastwidth+rastspace),(int) (rastwidth+10));
		for (int i = 0; i<=rast; i++){
			lineOnScreen(gl, i*unit+2*rastwidth+2*rastspace, 0,i*unit+2*rastwidth+2*rastspace, rastwidth-11);
			lineOnScreen(gl, 2*rastwidth+2*rastspace, i*unit,3*rastwidth+2*rastspace-12, i*unit);
		}
	}
	/**
	 * A method that draws the top left buttons on the screen.
	 * 
	 * @param gl
	 */
	private void drawButtons(GL gl) {
		// Editor boxes

		// Vloer
		//		gl.glColor3f(1f, 1f, 1f);
		//		boxOnScreen(gl, 0,buttonpos-buttonSize, buttonSize);
		Image.drawImage(gl, 0,(int)(buttonpos-buttonSize),36,36, vloer36);
		drawText(gl,"Add Floor",  (int) (buttonSize+10),(int) (buttonpos-0.5*buttonSize));
		// Muur
		//		gl.glColor3f(0.0f, 0.0f, 0.0f);
		//		boxOnScreen(gl, buttonSize+200, buttonpos-buttonSize, buttonSize);
		Image.drawImage(gl, (int) buttonSize+100,(int)(buttonpos-buttonSize),36,36, muur36);
		drawText(gl,"Add Wall", (int) (2*buttonSize+110),(int) (buttonpos-0.5*buttonSize));
		// Trap
		//		gl.glColor3f(0.5f, 0.5f, 0.0f);
		//		boxOnScreen(gl, 2*buttonSize+400, buttonpos-buttonSize, buttonSize);
		Image.drawImage(gl, (int) (2*buttonSize+200),(int)(buttonpos-buttonSize),36,36, trap36);
		drawText(gl,"Add Stairs", (int) (3*buttonSize+210),(int) (buttonpos-0.5*buttonSize));
		//Deur
		//		gl.glColor3f(0.5f, 0.0f, 0.5f);
		//		boxOnScreen(gl, 3*buttonSize+300, buttonpos-buttonSize, buttonSize);
		Image.drawImage(gl, (int) (3*buttonSize+300),(int)(buttonpos-buttonSize),36,36, deur36);
		drawText(gl,"Add Door", (int) (4*buttonSize+310),(int) (buttonpos-0.5*buttonSize));
		// Smart
		//		gl.glColor3f(0.5f, 0.0f, 0.5f);
		//		boxOnScreen(gl, 3*buttonSize+600, buttonpos-buttonSize, buttonSize);
		Image.drawImage(gl, (int) (4*buttonSize+400),(int)(buttonpos-buttonSize),36,36, smart36);
		drawText(gl,"Add Pill", (int) (5*buttonSize+410),(int) (buttonpos-0.5*buttonSize));
		// Save
		gl.glColor3f(0f, 1.0f, 0.5f);
		boxOnScreen(gl, 5*buttonSize+500, buttonpos-buttonSize, buttonSize);
		drawText(gl,"Save Maze", (int) (6*buttonSize+510),(int) (buttonpos-0.5*buttonSize));
		//Exit
		gl.glColor3f(0.1f, 0.1f, 0.1f);
		boxOnScreen(gl, 6*buttonSize+600, buttonpos-buttonSize, buttonSize);
		// Draw a cross on top of exit box
		gl.glLineWidth(3);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		lineOnScreen(gl, 6*buttonSize+600,buttonpos, 7*buttonSize+600,buttonpos-buttonSize);
		lineOnScreen(gl, 6*buttonSize+600,buttonpos-buttonSize, 7*buttonSize+600,buttonpos);
		drawText(gl,"Exit LevelEditor", (int) (7*buttonSize+610),(int) (buttonpos-0.5*buttonSize));

		//		// Draw a point on top of the first box
		//		gl.glPointSize(5.0f);
		//		gl.glColor3f(1.0f, 1.0f, 1.0f);
		//		pointOnScreen(gl, buttonSize / 2.0f, screenHeight - buttonSize / 2.0f);
		//
		//		// Draw a line on top of the second box.
		//		gl.glLineWidth(3);
		//		gl.glColor3f(1.0f, 1.0f, 1.0f);
		//		lineOnScreen(gl, buttonSize + 4.0f, screenHeight - 4.0f,
		//				2 * buttonSize - 4.0f, screenHeight - buttonSize + 4.0f);
		//		
		//		// Draw a triangle on top of the third box
		//		gl.glLineWidth(3);
		//		gl.glColor3f(1.0f, 1.0f, 1.0f);
		//		lineOnScreen(gl, 2*buttonSize + 0.5f*buttonSize, screenHeight - 8.0f,
		//				3 * buttonSize - 8.0f, screenHeight - buttonSize + 8.0f);
		//		lineOnScreen(gl, 3 * buttonSize - 8.0f, screenHeight - buttonSize + 8.0f,
		//				2 * buttonSize + 8.0f, screenHeight - buttonSize + 8.0f);
		//		lineOnScreen(gl, 2 * buttonSize + 8.0f, screenHeight - buttonSize + 8.0f,
		//				2*buttonSize + 0.5f*buttonSize, screenHeight - 8.0f);
	}


	private boolean isBegin(int X, int Y){
		return (Mazeconf[Mazeconf.length-Y-2][Mazeconf.length-X-2]==4);
	}

	private boolean isEind(int X, int Y){
		return (Mazeconf3[Mazeconf3.length-Y-2][Mazeconf3.length-X-2]==5);
	}

	private boolean isEindTrap(int X, int Y){
		return (Mazeconf[Mazeconf.length-Y-2][Mazeconf3.length-X-2]==6);
	}

	private boolean isEindTrap2(int X, int Y){
		return (Mazeconf2[Mazeconf2.length-Y-2][Mazeconf2.length-X-2]==6);
	}

	private boolean isEindTrap3(int X, int Y){
		return (Mazeconf3[Mazeconf3.length-Y-2][Mazeconf3.length-X-2]==6);
	}

	private boolean isTrap(int X, int Y){
		return (Mazeconf[Mazeconf.length-Y-2][Mazeconf3.length-X-2]==2);
	}

	private boolean isTrap2(int X, int Y){
		return (Mazeconf2[Mazeconf2.length-Y-2][Mazeconf2.length-X-2]==2);
	}

	private int[] bevat(int v){

		int contains = 0, i1 = 0, j1 = 0;
		for (int i = 0; i<Mazeconf.length; i++){
			for (int j = 0; j<Mazeconf.length;j++){
				if(Mazeconf[i][j]==v){
					i1= i; j1= j; contains = 1;
				}
			}
		}
		return new int[] {contains, i1, j1};
	}

	private int[] bevat2(int v){

		int contains = 0, i1 = 0, j1 = 0;
		for (int i = 0; i<Mazeconf2.length; i++){
			for (int j = 0; j<Mazeconf2.length;j++){
				if(Mazeconf2[i][j]==v){
					i1= i; j1= j; contains = 1;
				}
			}
		}
		return new int[] {contains, i1, j1};
	}

	private int[] bevat3(int v){

		int contains = 0, i1 = 0, j1 = 0;
		for (int i = 0; i<Mazeconf3.length; i++){
			for (int j = 0; j<Mazeconf3.length;j++){
				if(Mazeconf3[i][j]==v){
					i1= i; j1= j; contains = 1;
				}
			}
		}
		return new int[] {contains, i1, j1};
	}
	/**
	 * A method that draws a figure, when the user has inputted enough points
	 * for the current draw mode.
	 * 
	 * @param gl
	 */
	private void drawFigure(GL gl) {
		// Set line and point size, and set color to black.
		gl.glLineWidth(1);
		gl.glPointSize(10.0f);
		gl.glColor3f(0.0f, 0.0f, 0.0f);

		Point2D.Float p1, p2, p3;
		switch (drawMode) {
		case DM_VLOER:
			if (points.size() >= 1)	{
				if (points.get(0).y<rastwidth){
					if (points.get(0).x<=rastwidth){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastX = (((int) p1.x)/(unit));
						int rastY = (((int) p1.y)/(unit));
						if (!isBegin(rastX,rastY)&&!isTrap(rastX,rastY)){
							Mazeconf[Mazeconf.length-rastY-2][Mazeconf.length-rastX-2]=0;
						}
					}
					else if (points.get(0).x<=2*rastwidth+rastspace&&points.get(0).x>=rastwidth+rastspace){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastX2 = ((int) ((p1.x-(rastwidth+rastspace))/(unit)));
						int rastY2 = (((int) p1.y)/(unit));
						if (!isEindTrap2(rastX2,rastY2)&&!isTrap2(rastX2,rastY2)){
							Mazeconf2[Mazeconf2.length-rastY2-2][Mazeconf2.length-rastX2-2]=0;
						}

					}
					else if (points.get(0).x<=3*rastwidth+2*rastspace&&points.get(0).x>=2*rastwidth+2*rastspace){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastX3 = ((int) ((p1.x-(2*rastwidth+2*rastspace))/(unit)));
						int rastY3 = (((int) p1.y)/(unit));
						if (!isEind(rastX3,rastY3)&&!isEindTrap3(rastX3,rastY3)){
							Mazeconf3[Mazeconf3.length-rastY3-2][Mazeconf3.length-rastX3-2]=0;
						}
					}
				}
			}
			points.clear();
			break;
		case DM_MUUR:			
			if (points.size() >= 1)	{
				if (points.get(0).y<rastwidth){
					if (points.get(0).x<=rastwidth){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastXm = (((int) p1.x)/(unit));
						int rastYm = (((int) p1.y)/(unit));
						if (!isBegin(rastXm,rastYm)&&!isTrap(rastXm,rastYm)){
							Mazeconf[Mazeconf.length-rastYm-2][Mazeconf.length-rastXm-2]=1;
						}
					}
					else if (points.get(0).x<=2*rastwidth+rastspace&&points.get(0).x>=rastwidth+rastspace){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastXm2 = ((int) ((p1.x-(rastwidth+rastspace))/(unit)));
						int rastYm2 = (((int) p1.y)/(unit));
						if(!isEindTrap2(rastXm2,rastYm2)&&!isTrap2(rastXm2,rastYm2)){
							Mazeconf2[Mazeconf2.length-rastYm2-2][Mazeconf2.length-rastXm2-2]=1;
						}

					}
					else if (points.get(0).x<=3*rastwidth+2*rastspace&&points.get(0).x>=2*rastwidth+2*rastspace){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastXm3 = (((int) (p1.x-(2*rastwidth+2*rastspace))/(unit)));
						int rastYm3 = (((int) p1.y)/(unit));
						if (!isEind(rastXm3,rastYm3)&&!isEindTrap3(rastXm3,rastYm3)){
							Mazeconf3[Mazeconf3.length-rastYm3-2][Mazeconf3.length-rastXm3-2]=1;
						}
					}
				}
			}
			points.clear();
			break;
		case DM_TRAP:
			if (points.size() >= 1)	{
				if (points.get(0).y<rastwidth){
					if (points.get(0).x<=rastwidth){
						if (bevat(2)[0]==1){
							Mazeconf[bevat(2)[1]][bevat(2)[2]]=0;
						}
						if (bevat2(6)[0]==1){
							Mazeconf2[bevat2(6)[1]][bevat2(6)[2]]=0;
						}
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastXt = (((int) p1.x)/(unit));
						int rastYt = (((int) p1.y)/(unit));
						if (!isBegin(rastXt,rastYt)){
							Mazeconf[Mazeconf.length-rastYt-2][Mazeconf.length-rastXt-2]=2;
							Mazeconf2[Mazeconf2.length-rastYt-2][Mazeconf2.length-rastXt-2]=6;
						}
					}
					else if (points.get(0).x<=2*rastwidth+rastspace&&points.get(0).x>=rastwidth+rastspace){
						if (bevat2(2)[0]==1){
							Mazeconf2[bevat2(2)[1]][bevat2(2)[2]]=0;
						}
						if (bevat3(6)[0]==1){
							Mazeconf3[bevat3(6)[1]][bevat3(6)[2]]=0;
						}
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastXt2 = ((int) ((p1.x-(rastwidth+rastspace))/(unit)));
						int rastYt2 = (((int) p1.y)/(unit));
						if (!isBegin(rastXt2,rastYt2)&&!isEindTrap2(rastXt2,rastYt2)&&!isEind(rastXt2,rastYt2)){
							Mazeconf2[Mazeconf2.length-rastYt2-2][Mazeconf2.length-rastXt2-2]=2;
							Mazeconf3[Mazeconf3.length-rastYt2-2][Mazeconf3.length-rastXt2-2]=6;
						}
					}
				}
			}
			points.clear();
			break;
		case DM_DEUR:
			if (points.size() >= 1)	{
				if (points.get(0).y<rastwidth){
					if (points.get(0).x<=rastwidth){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastX = (((int) p1.x)/(unit));
						int rastY = (((int) p1.y)/(unit));
						if (!isBegin(rastX,rastY)&&!isTrap(rastX,rastY)){
							Mazeconf[Mazeconf.length-rastY-2][Mazeconf.length-rastX-2]=7;
						}
					}
					else if (points.get(0).x<=2*rastwidth+rastspace&&points.get(0).x>=rastwidth+rastspace){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastX2 = ((int) ((p1.x-(rastwidth+rastspace))/(unit)));
						int rastY2 = (((int) p1.y)/(unit));
						if (!isEindTrap2(rastX2,rastY2)&&!isTrap2(rastX2,rastY2)){
							Mazeconf2[Mazeconf2.length-rastY2-2][Mazeconf2.length-rastX2-2]=7;
						}

					}
					else if (points.get(0).x<=3*rastwidth+2*rastspace&&points.get(0).x>=2*rastwidth+2*rastspace){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastX3 = ((int) ((p1.x-(2*rastwidth+2*rastspace))/(unit)));
						int rastY3 = (((int) p1.y)/(unit));
						if (!isEind(rastX3,rastY3)&&!isEindTrap3(rastX3,rastY3)){
							Mazeconf3[Mazeconf3.length-rastY3-2][Mazeconf3.length-rastX3-2]=7;
						}
					}
				}
			}
			points.clear();
			break;
		case DM_SMART:
			if (points.size() >= 1)	{
				if (points.get(0).y<rastwidth){
					if (points.get(0).x<=rastwidth){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastXs = (((int) p1.x)/(unit));
						int rastYs = (((int) p1.y)/(unit));
						if (!isBegin(rastXs,rastYs)&&!isTrap(rastXs,rastYs)){
							Mazeconf[Mazeconf.length-rastYs-2][Mazeconf.length-rastXs-2]=3;
						}
					}
					else if (points.get(0).x<=2*rastwidth+rastspace&&points.get(0).x>=rastwidth+rastspace){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastXs2 = ((int) ((p1.x-(rastwidth+rastspace))/(unit)));
						int rastYs2 = (((int) p1.y)/(unit));
						if (!isEindTrap2(rastXs2,rastYs2)&&!isTrap2(rastXs2,rastYs2)){
							Mazeconf2[Mazeconf2.length-rastYs2-2][Mazeconf2.length-rastXs2-2]=3;
						}

					}
					else if (points.get(0).x<=3*rastwidth+2*rastspace&&points.get(0).x>=2*rastwidth+2*rastspace){
						// If the draw mode is "point" and the user has supplied at
						// least one point, draw that point.
						p1 = points.get(0);
						int rastXs3 = (((int) (p1.x-(2*rastwidth+2*rastspace))/(unit)));
						int rastYs3 = (((int) p1.y)/(unit));
						if (!isEind(rastXs3,rastYs3)&&!isEindTrap3(rastXs3,rastYs3)){
							Mazeconf3[Mazeconf3.length-rastYs3-2][Mazeconf3.length-rastXs3-2]=3;
						}
					}
				}
			}
			points.clear();
			break;
		}
	}


	/**
	 * Help method that uses GL calls to draw a point.
	 */
	private void pointOnScreen(GL gl, float x, float y) {
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2f(x, y);
		gl.glEnd();
	}

	/**
	 * Help method that uses GL calls to draw a line.
	 */
	private void lineOnScreen(GL gl, float x1, float y1, float x2, float y2) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(x1, y1);
		gl.glVertex2f(x2, y2);
		gl.glEnd();
	}

	/**
	 * Help method that uses GL calls to draw a square
	 */
	private void boxOnScreen(GL gl, float x, float y, float size) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x + size, y);
		gl.glVertex2f(x + size, y + size);
		gl.glVertex2f(x, y + size);
		gl.glEnd();
	}

	@Override
	/**
	 * A function defined in GLEventListener. This function is called when there is a change in certain 
	 * external display settings. 
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		// Not needed.
	}



	@Override
	/**
	 * A function defined in GLEventListener. This function is called when the GLCanvas is resized or moved. 
	 * Since the canvas fills the frame, this event also triggers whenever the frame is resized or moved.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();

		// Set the new screen size and adjusting the viewport
		screenWidth = width;
		screenHeight = height;
		rastwidth = height-100;
		buttonpos = rastwidth+100;
		unit = rastwidth/rast;
		buttonSize = unit *2;
		gl.glViewport(0, 0, screenWidth, screenHeight);

		// Update the projection to an orthogonal projection using the new screen size
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);
	}

	@Override
	/**
	 * A function defined in MouseListener. Is called when the pointer is in the GLCanvas, and a mouse button is released.
	 */
	public void mouseReleased(MouseEvent me) {
		// Check if the coordinates correspond to any of the top left buttons
		boolean buttonPressed = false;
		if (me.getY()<buttonSize){
			if (me.getX() < buttonSize) {
				// The first button is clicked
				drawMode = DM_VLOER;
				System.out.println("Draw mode: DRAW_VLOER");
				buttonPressed = true;
			}else if (me.getX() > buttonSize+100&& me.getX()<buttonSize+100+buttonSize) {
				// The second button is clicked
				points.clear();
				drawMode = DM_MUUR;
				System.out.println("Draw mode: DRAW_MUUR");
				buttonPressed = true;
			}else if (me.getX() > 2 * buttonSize+200&& me.getX()<2 * buttonSize+200+buttonSize) {
				// The third button is clicked
				points.clear();
				drawMode = DM_TRAP;
				System.out.println("Draw mode: DRAW_TRAP");
				buttonPressed = true;
			}
			else if (me.getX() > 3 * buttonSize+300&& me.getX()<3 * buttonSize+300+buttonSize) {
				// The fourth button is clicked
				points.clear();
				drawMode = DM_DEUR;
				System.out.println("Draw mode: DRAW_DEUR");
				buttonPressed = true;
			}
			else if (me.getX() > 4 * buttonSize+400&& me.getX()<4 * buttonSize+400+buttonSize) {
				// The fifth button is clicked
				points.clear();
				drawMode = DM_SMART;
				System.out.println("Draw mode: DRAW_SMART");
				buttonPressed = true;
			}
			else if (me.getX() > 5 * buttonSize+500&& me.getX()<5 * buttonSize+500+buttonSize) {
				SaveWin SW = new SaveWin();
				SW.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				SW.setSize(300,150);
				SW.setVisible(true);
				SW.setTitle("Save");
				System.out.println("SAVE");
				//save
			}
			else if (me.getX() > 6 * buttonSize+600&& me.getX()<6 * buttonSize+600+buttonSize) {
				dispose = true;
				//exit
			}
			System.out.println(drawMode);

		}

		// Only register a new point, if the click did not hit any button
		if (!buttonPressed) {

			if (drawMode == DM_VLOER && points.size() >= 1) {
				// If we're drawing points and one point was stored, reset the points list
				points.clear();
			}if (drawMode == DM_MUUR && points.size() >= 1) {
				// If we're drawing lines and two points were already stored, reset the points list
				points.clear();
			}if (drawMode == DM_TRAP && points.size() >= 1) {
				// If we're drawing lines and two points were already stored, reset the points list
				points.clear();
			}if (drawMode == DM_DEUR && points.size() >= 1) {
				// If we're drawing lines and two points were already stored, reset the points list
				points.clear();
			}
			else if (drawMode == DM_SMART && points.size() >= 1) {
				// If we're drawing Koch and three points were already stored, reset the points list
				points.clear();
			}

			// Add a new point to the points list.
			points.add(new Point2D.Float(me.getX(), screenHeight - me.getY()));
			String Mazeres = "";
			for (int i = 0; i<Mazeconf.length; i++){
				for (int j = 0; j<Mazeconf.length; j++){
					Mazeres = Mazeres + Mazeconf[i][j] + ", ";
					if (j==Mazeconf.length-1){
						Mazeres = Mazeres + "}\n";
					}
				}
			}

		}
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// Not needed.
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Not needed.

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Not needed.

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Not needed.

	}
	//JFrame for Saving
	public class SaveWin extends JFrame{
		private JButton submit1;
		private JTextField Savename;
		private JLabel SN;

		public SaveWin(){
			Container pane = this.getContentPane();
			BoxLayout layout = new BoxLayout(pane,BoxLayout.Y_AXIS);
			setLayout(layout);
			SN = new JLabel("Save as: ");
			pane.add(SN);
			Savename = new JTextField(20);
			pane.add(Savename);
			submit1 = new JButton("Submit");
			pane.add(submit1);

			eventsub1 s1 = new eventsub1();
			submit1.addActionListener(s1);
		}
		public class eventsub1 implements ActionListener {
			public void actionPerformed(ActionEvent s1){
				String naamtemp = Savename.getText();

				Mazescont temp = Mazescont.read("Mazes.txt");
				for (int i = 0; i<temp.getArray().size();i++)
				{
					if(temp.getMazes(i).getNaam().equals(naamtemp))
					{
						naamtemp = naamtemp +"1";
					}
				}

				int[][] m = Mazeconf;
				int[][] m2 = Mazeconf2;
				int[][] m3 = Mazeconf3;
				Maze.Mazes tempMaze = new Maze.Mazes(m,m2,m3,naamtemp);
				tempMaze.addtofile("Mazes.txt");
				dispose();
			}
		}
	}




	public static void main(String[] arg0){
		//		int[][] maze =
		//		{{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		//		{1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		//		{1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
		//		{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		//		{1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		//		{1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		//		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
		//		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
		//		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }};
	}
}

