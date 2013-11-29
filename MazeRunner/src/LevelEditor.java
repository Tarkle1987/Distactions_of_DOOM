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

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;

/**
 * A frame for us to draw on using OpenGL.
 * 
 * @author 
 * 
 */
public class LevelEditor implements GLEventListener, MouseListener {
	static final long serialVersionUID = 1;

	// Screen size.
	private  int screenWidth = 1200, screenHeight = 945;
	private  int rast = 20;
	private int rastwidth = screenHeight-45;
	private int buttonpos = rastwidth+50;
	private int[][] Mazeconf = new int[rast+2][rast+2];
	private int[][] SaveMaze = new int[22][22];
	private int unit = rastwidth/rast;
	private float buttonSize = unit *2;
	private byte[] vloer45 = Image.loadImage("Vloer45.jpg");
	private byte[] vloer90 = Image.loadImage("Vloer90.jpg");
	private byte[] muur45 = Image.loadImage("muur45.jpg");
	private byte[] muur90 = Image.loadImage("muur90.jpg");
	private byte[] trap45 = Image.loadImage("Trap45.jpg");
	private byte[] trap90 = Image.loadImage("Trap90.jpg");
	private byte[] smart45 = Image.loadImage("Smart45.jpg");
	private byte[] smart90 = Image.loadImage("Smart90.jpg");
	// A GLCanvas is a component that can be added to a frame. The drawing
	// happens on this component.
	private GLCanvas canvas;

	private static final byte DM_VLOER = 0;
	private static final byte DM_MUUR = 1;
	private static final byte DM_TRAP = 2;
	private static final byte DM_SMART = 3;
	private byte drawMode = DM_VLOER;

	private ArrayList<Point2D.Float> points;
	private ArrayList<int[]> Mazes;
	
	private Menu that;
	
	protected boolean dispose = false;

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
			}
		}

		drawVertical();
		drawHorizontal();
		System.out.println(unit);
		for (int i = 0; i<Mazeconf.length;i++){
			Mazeconf[0][i]=1;
			Mazeconf[Mazeconf.length-1][i]= 1;
			Mazeconf[i][0]=1;
			Mazeconf[i][Mazeconf.length-1]= 1;
		}

				Mazeconf[1][20]=5;
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

	private void drawVertical(){
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

	private void drawHorizontal(){
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

		// Set the clear color and clear the screen.
		gl.glClearColor(0.85f, 0.85f, 0.85f, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

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
			glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, text.charAt(i)); // generation of characters in our text with 9 by 15 GLU font
		}
	}

	private void drawMaze(GL gl){
		for (int i = 1; i<Mazeconf.length-1;i++){
			for (int j = 1;j<Mazeconf.length-1;j++){
				switch (Mazeconf[j][i]){
				case 0:
					Image.drawImage(gl, (rast-i)*unit,(rast-j)*unit,45,45, vloer45);
					break;
				case 1:
					Image.drawImage(gl, (rast-i)*unit,(rast-j)*unit,45,45, muur45);
					break;
				case 2:
					Image.drawImage(gl, (rast-i)*unit,(rast-j)*unit,45,45, trap45);
					break;
				case 3:
					Image.drawImage(gl, (rast-i)*unit,(rast-j)*unit,45,45, smart45);
					break;
				case 4:
					gl.glColor3f(0.5f, 0.5f, 0.5f);
					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
					break;
				case 5:
					gl.glColor3f(0.75f, 0f, 0.5f);
					boxOnScreen(gl, (rast-i)*unit, (rast-j)*unit, unit);
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
		for (int i = 0; i<=rast; i++){
			lineOnScreen(gl, i*unit, 0,i*unit, rastwidth);
			lineOnScreen(gl, 0, i*unit,rastwidth, i*unit);
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
		Image.drawImage(gl, buttonpos,(int) (screenHeight - buttonSize),90,90, vloer90);
		drawText(gl,"Add Floor", (int) (buttonpos+buttonSize+10),(int) (screenHeight - 0.5*buttonSize));
		// Muur
		Image.drawImage(gl, buttonpos,(int) (screenHeight - 2*buttonSize),90,90, muur90);
		drawText(gl,"Add Wall", (int) (buttonpos+buttonSize+10),(int) (screenHeight - 1.5*buttonSize));
		// Trap
		Image.drawImage(gl, buttonpos,(int) (screenHeight - 3*buttonSize),90,90, trap90);
		drawText(gl,"Add Stairs", (int) (buttonpos+buttonSize+10),(int) (screenHeight - 2.5*buttonSize));
		// Smart
		Image.drawImage(gl, buttonpos,(int) (screenHeight - 4*buttonSize),90,90, smart90);
		drawText(gl,"Add Smartdrug", (int) (buttonpos+buttonSize+10),(int) (screenHeight - 3.5*buttonSize));
		// Save
		gl.glColor3f(0f, 1.0f, 0.5f);
		boxOnScreen(gl, buttonpos, screenHeight - 5*buttonSize, buttonSize);
		drawText(gl,"Save Maze", (int) (buttonpos+buttonSize+10),(int) (screenHeight - 4.5*buttonSize));
		//Exit
		gl.glColor3f(0.1f, 0.1f, 0.1f);
		boxOnScreen(gl, buttonpos, screenHeight - 6*buttonSize, buttonSize);
		// Draw a cross on top of exit box
		gl.glLineWidth(3);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		lineOnScreen(gl, buttonpos,screenHeight - 5*buttonSize, buttonpos+buttonSize,screenHeight - 6*buttonSize);
		lineOnScreen(gl, buttonpos,screenHeight - 6*buttonSize, buttonpos+buttonSize,screenHeight - 5*buttonSize);
		drawText(gl,"Exit LevelEditor", (int) (buttonpos+buttonSize+10),(int) (screenHeight - 5.5*buttonSize));

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


	private boolean isBed(int X, int Y){
		return (Mazeconf[Mazeconf.length-Y-2][X+1]==4);
	}

	private boolean isEind(int X, int Y){
		return (Mazeconf[Mazeconf.length-Y-2][X+1]==5);
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
				if (points.get(0).x<=rastwidth){
					// If the draw mode is "point" and the user has supplied at
					// least one point, draw that point.
					p1 = points.get(0);
					int rastX = (((int) p1.x)/(unit));
					int rastY = (((int) p1.y)/(unit));
					if (!isBed(rastX,rastY)&&!isEind(rastX,rastY)){
						Mazeconf[Mazeconf.length-rastY-2][Mazeconf.length-rastX-2]=0;
					}
					//					System.out.println(unit);
					//					System.out.println(rastX + " " + rastY + " " + unit);
					//					System.out.println(Mazeconf[Mazeconf.length-rastY-2][rast-rastX]);
					//					drawMaze(gl);
					//					drawRaster(gl);
				}
			}
			points.clear();
			break;
		case DM_MUUR:
			if (points.size() >= 1) {
				if (points.get(0).x<=rastwidth){
					// If the draw mode is "line" and the user has supplied at least
					// two points, draw a line between those points
					p1 = points.get(0);
					int rastXm = (((int) p1.x)/(unit));
					int rastYm = (((int) p1.y)/(unit));
					if (!isBed(rastXm,rastYm)&&!isEind(rastXm,rastYm)){
						Mazeconf[Mazeconf.length-rastYm-2][Mazeconf.length-rastXm-2]=1;
						//					System.out.println(unit);
						//					System.out.println(rastXm + " " + rastYm);
						//					System.out.println(Mazeconf[Mazeconf.length-rastYm-2][rast-rastXm]);
						//					drawMaze(gl);
						//					drawRaster(gl);
					}
				}
			}
			points.clear();
			break;
		case DM_TRAP:
			if (points.size() >= 1) {
				if (points.get(0).x<=rastwidth){
					if (bevat(2)[0]==1){
						Mazeconf[bevat(2)[1]][bevat(2)[2]]=0;
					}
					p1 = points.get(0);
					int rastXt = (((int) p1.x)/(unit));
					int rastYt = (((int) p1.y)/(unit));
					if (!isBed(rastXt,rastYt)&&!isEind(rastXt,rastYt)){
						Mazeconf[Mazeconf.length-rastYt-2][Mazeconf.length-rastXt-2]=2;
					}
					//					System.out.println(unit);
					//					System.out.println(rastXt + " " + rastYt);
					//					System.out.println(Mazeconf[Mazeconf.length-rastYt-2][rast-rastXt]);
					//					drawMaze(gl);
					//					drawRaster(gl);

				}
			}
			points.clear();
			break;
		case DM_SMART:
			if (points.size() >= 1) {
				if (points.get(0).x<=rastwidth){
					if (bevat(3)[0]==1){
						Mazeconf[bevat(3)[1]][bevat(3)[2]]=0;
					}
					p1 = points.get(0);
					int rastXs = (((int) p1.x)/(unit));
					int rastYs = (((int) p1.y)/(unit));
					if (!isBed(rastXs,rastYs)&&!isEind(rastXs,rastYs)){
						Mazeconf[Mazeconf.length-rastYs-2][Mazeconf.length-rastXs-2]=3;
					}
					//					System.out.println(unit);
					//					System.out.println(rastXs + " " + rastYs);
					//					System.out.println(Mazeconf[Mazeconf.length-rastYs-2][rast-rastXs]);
					//					drawMaze(gl);
					//					drawRaster(gl);
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
		rastwidth = height;
		buttonpos = rastwidth+50;
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
		if (me.getX()>buttonpos && me.getX() < buttonpos + buttonSize) {
			if (me.getY() < buttonSize) {
				// The first button is clicked
				drawMode = DM_VLOER;
				System.out.println("Draw mode: DRAW_VLOER");
				buttonPressed = true;
			}else if (me.getY() < 2 * buttonSize) {
				// The second button is clicked
				points.clear();
				drawMode = DM_MUUR;
				System.out.println("Draw mode: DRAW_MUUR");
				buttonPressed = true;
			}else if (me.getY() < 3 * buttonSize) {
				// The third button is clicked
				points.clear();
				drawMode = DM_TRAP;
				System.out.println("Draw mode: DRAW_TRAP");
				buttonPressed = true;
			}
			else if (me.getY() < 4 * buttonSize) {
				// The third button is clicked
				points.clear();
				drawMode = DM_SMART;
				System.out.println("Draw mode: DRAW_SMART");
				buttonPressed = true;
			}
			else if (me.getY() < 5 * buttonSize) {
				SaveWin SW = new SaveWin();
				SW.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				SW.setSize(300,150);
				SW.setVisible(true);
				SW.setTitle("Save");
				System.out.println("SAVE");
				//save
			}
			else if (me.getY() < 6 * buttonSize) {
				dispose = true;
				//exit
			}

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
			}
			else if (drawMode == DM_SMART && points.size() >= 1) {
				// If we're drawing Koch and three points were already stored, reset the points list
				points.clear();
			}

			// Add a new point to the points list.
			points.add(new Point2D.Float(me.getX(), screenHeight - me.getY()));
			System.out.println(points.get(0).getX() + ", "+ points.get(0).getY());
			String Mazeres = "";
			for (int i = 0; i<Mazeconf.length; i++){
				for (int j = 0; j<Mazeconf.length; j++){
					Mazeres = Mazeres + Mazeconf[i][j] + ", ";
					if (j==Mazeconf.length-1){
						Mazeres = Mazeres + "}\n";
					}
				}
			}

			System.out.println(Mazeres);
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
				Mazes tempMaze = new Mazes(m,naamtemp);
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

