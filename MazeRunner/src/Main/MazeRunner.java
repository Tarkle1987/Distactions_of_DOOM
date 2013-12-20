package Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import MenuButtons.Button;
import movingobjects.Beer;
import movingobjects.CompanionCube;
import movingobjects.CustomMazeObject;
import movingobjects.Lifeform;
import movingobjects.MazeObject;
import movingobjects.Peter;
import movingobjects.Projectile;
import movingobjects.Smart;
import movingobjects.Smarto;
import movingobjects.Smartw;
import movingobjects.VisibleObject;
import HUD.Clock;
import HUD.HealthBar;
import leveleditor.Image;
import Maze.Maze;
import NotDefined.SchuifMuur;
import NotDefined.Sound;
import Player.Camera;
import Player.Player;
import Player.UserInput;

import com.sun.opengl.util.*;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import stationairyobjects.Trap;
import stationairyobjects.Trapaf;

/**
 * MazeRunner is the base class of the game, functioning as the view controller
 * and game logic manager.
 * <p>
 * Functioning as the window containing everything, it initializes both JOGL,
 * the game objects and the game logic needed for MazeRunner.
 * <p>
 * For more information on JOGL, visit <a
 * href="http://jogamp.org/wiki/index.php/Main_Page">this page</a> for general
 * information, and <a
 * href="https://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/">this
 * page</a> for the specification of the API.
 * 
 * @author Bruno Scheele, revised by Mattijs Driel
 * 
 */
public class MazeRunner extends Frame implements GLEventListener {
	static final long serialVersionUID = 7526471155622776147L;

	/*
	 * **********************************************
	 * * Local variables * **********************************************
	 */
	private GLCanvas canvas;

	private int screenWidth = 1000, screenHeight = 1000; // Screen size.
	private ArrayList<VisibleObject> visibleObjects; // A list of objects that
														// will be displayed on
														// screen.
	private ArrayList<Projectile> projectiles;
	private ArrayList<Lifeform> lifeforms;
	private Player player; // The player object.
	private Camera camera; // The camera object.
	private UserInput input; // The user input object that controls the player.
	private Maze maze; // The maze.
	private long previousTime = Calendar.getInstance().getTimeInMillis(); // Used
																			// to
																			// calculate
																			// elapsed
																			// time.

	// startup hulp booleans
	private boolean start = true;
	private boolean init = true;
	private boolean loading = true;

	private boolean textrue = true;
	private CompanionCube c1;
	private MazeObject Trap, Smart, Smarto,
			Smartw;
	private Beer b1, b2, b3, b4, b5;

	// Ingame seconden tellen
	private int miliseconds = 0;
	
	private Clock clock = new Clock();
	
	private byte[] E = Image.loadImage("E.png");
	

	/*
	 * **********************************************
	 * * Initialization methods * **********************************************
	 */
	/**
	 * Initializes the complete MazeRunner game.
	 * <p>
	 * MazeRunner extends Java AWT Frame, to function as the window. It creats a
	 * canvas on itself where JOGL will be able to paint the OpenGL graphics. It
	 * then initializes all game components and initializes JOGL, giving it the
	 * proper settings to accurately display MazeRunner. Finally, it adds itself
	 * as the OpenGL event listener, to be able to function as the view
	 * controller.
	 */
	public MazeRunner() {
		// Make a new window.
		super("MazeRunner");

		// Let's change the window to our liking.
		setSize(screenWidth, screenHeight);
		setBackground(Color.white);

		// The window also has to close when we want to.
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				System.exit(0); // End the program

			}
		});

		initJOGL(); // Initialize JOGL.
		initObjects(); // Initialize all the objects!

		// Set the frame to visible. This automatically calls upon OpenGL to
		// prevent a blank screen.
		setVisible(true);

	}

	/**
	 * initJOGL() sets up JOGL to work properly.
	 * <p>
	 * It sets the capabilities we want for MazeRunner, and uses these to create
	 * the GLCanvas upon which MazeRunner will actually display our screen. To
	 * indicate to OpenGL that is has to enter a continuous loop, it uses an
	 * Animator, which is part of the JOGL api.
	 */
	private void initJOGL() {
		// First, we set up JOGL. We start with the default settings.
		GLCapabilities caps = new GLCapabilities();
		// Then we make sure that JOGL is hardware accelerated and uses double
		// buffering.
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);

		// Now we add the canvas, where OpenGL will actually draw for us. We'll
		// use settings we've just defined.
		canvas = new GLCanvas(caps);
		add(canvas);

		/*
		 * We need to add a GLEventListener to interpret OpenGL events for us.
		 * Since MazeRunner implements GLEventListener, this means that we add
		 * the necesary init(), display(), displayChanged() and reshape()
		 * methods to this class. These will be called when we are ready to
		 * perform the OpenGL phases of MazeRunner.
		 */
		canvas.addGLEventListener(this);

		/*
		 * We need to create an internal thread that instructs OpenGL to
		 * continuously repaint itself. The Animator class handles that for
		 * JOGL.
		 */
		Animator anim = new Animator(canvas);
		anim.start();

		canvas.setVisible(true);
	}

	/**
	 * initializeObjects() creates all the objects needed for the game to start
	 * normally.
	 * <p>
	 * This includes the following:
	 * <ul>
	 * <li>the default Maze
	 * <li>the Player
	 * <li>the Camera
	 * <li>the User input
	 * </ul>
	 * <p>
	 * Remember that every object that should be visible on the screen, should
	 * be added to the visualObjects list of MazeRunner through the add method,
	 * so it will be displayed automagically.
	 */

	private void initObjects() {
		// We define an ArrayList of VisibleObjects to store all the objects
		// that need to be
		// displayed by MazeRunner.
		visibleObjects = new ArrayList<VisibleObject>();
		projectiles = new ArrayList<Projectile>();
		lifeforms = new ArrayList<Lifeform>();
		// We define an ArrayList of Tiles to store all the current positions of
		// the gameobjects
		// objectPositions = new ArrayList<Tile>();

		// Add the maze that we will be using.
		maze = new Maze();
		visibleObjects.add(maze);

		visibleObjects.set(0, maze);

		// Initialize the player.
		input = new UserInput(canvas);
		
		
		player = new Player( 20 * maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2, 	// x-position
							 maze.SQUARE_SIZE / 2,							// y-position
							 1 * maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2, 	// z-position
							 90, 0 );										// horizontal and vertical angle

	    camera = new Camera( player.getLocationX(), player.getLocationY(), player.getLocationZ(), 
		             player.getHorAngle(), player.getVerAngle() );
			
	
			    /*
	     * Start positions for the game objects. Be aware: for the player the start position must two times be set..

	  */  


//	    CompanionCube c1 = new CompanionCube(player.locationX,  0,  player.locationZ, 1.5);
//		CompanionCube c1 = new CompanionCube(103,  0,  53, 1.5);
//	    lifeforms.add(c1);
//	    CompanionCube c2 = new CompanionCube(103,  0,  72, 1.5);
//		lifeforms.add(c2);
//		 CompanionCube c3 = new CompanionCube(83,  0,  72, 1.5);
//		lifeforms.add(c3);
		
	    SchuifMuur SM = new SchuifMuur(5,5,maze);
	    visibleObjects.add(SM);
	    
		CompanionCube(1,1.5);
	    
//	    Peter peter = new Peter(player.locationX, 0, player.locationZ);
//	    lifeforms.add(peter);

		int[] coordT = Maze.CoordTrap(Maze.maze);
		Trap tr1 = new Trap((float) coordT[0], (float) coordT[1]);
		Trap tr2 = new Trap((float) coordT[2], (float) coordT[3]);
		visibleObjects.add(tr1);
		visibleObjects.add(tr2);
		int[] coordTa = Maze.CoordTrapaf(Maze.maze);
		Trapaf tra1 = new Trapaf((float) coordTa[0], (float) coordTa[1]);
		Trapaf tra2 = new Trapaf((float) coordTa[2], (float) coordTa[3]);
		visibleObjects.add(tra1);
		visibleObjects.add(tra2);
		int[] coordS = Maze.CoordSmart(Maze.maze);
		for (int i = 0; i < coordS[0]; i++) {
			Smart Sm = new Smart((float) coordS[1 + i * 2],
					(float) coordS[2 + i * 2]);
			visibleObjects.add(Sm);

		}

		int[] coordSM = Maze.CoordSchuifMuur(Maze.maze);
		for(int i = 0; i < coordSM[0]; i++){
			SchuifMuur schuifmuur = new SchuifMuur(coordSM[1+i*2],coordSM[2+i*2], maze);
			visibleObjects.add(schuifmuur);
		}
		//this.setUndecorated(true);
		player.setControl(input);

		input.screenWidth = screenWidth;
		input.screenHeight = screenHeight;
		input.mouseReset();

	}

	/*
	 * **********************************************
	 * * OpenGL event handlers * **********************************************
	 */

	/**
	 * init(GLAutodrawable) is called to initialize the OpenGL context, giving
	 * it the proper parameters for viewing.
	 * <p>
	 * Implemented through GLEventListener. It sets up most of the OpenGL
	 * settings for the viewing, as well as the general lighting.
	 * <p>
	 * It is <b>very important</b> to realize that there should be no drawing at
	 * all in this method.
	 */

	public void CompanionCube(int num, double size) {
		for (int i = 0; i < num; i++) {
			double X = player.locationX;
			double Z = player.locationZ;

			boolean GO = true;
			while (GO) {
				X = Math.random() * 21 * maze.SQUARE_SIZE;
				Z = Math.random() * 21 * maze.SQUARE_SIZE;

				if (!(maze.isWall(X + (size * Math.sqrt(2)) / 2, Z)
						|| maze.isWall(X - (size * Math.sqrt(2)) / 2, Z)
						|| maze.isWall(X, Z + (size * Math.sqrt(2)) / 2)
						|| maze.isWall(X, Z - (size * Math.sqrt(2)) / 2)
						|| maze.isWall(X + (size * Math.sqrt(2)) / 2, Z
								+ (size * Math.sqrt(2)) / 2)
						|| maze.isWall(X + (size * Math.sqrt(2)) / 2, Z
								- (size * Math.sqrt(2)) / 2)
						|| maze.isWall(X - (size * Math.sqrt(2)) / 2, Z
								+ (size * Math.sqrt(2)) / 2) || maze.isWall(X
						- (size * Math.sqrt(2)) / 2, Z - (size * Math.sqrt(2))
						/ 2))) {

					GO = false;
				}
			}

			CompanionCube CC = new CompanionCube(X, 0, Z, size);
			lifeforms.add(CC);
		}
	}

	public void init(GLAutoDrawable drawable) {
		drawable.setGL(new DebugGL(drawable.getGL())); // We set the OpenGL
														// pipeline to Debugging
														// mode.

		GL gl = drawable.getGL();
		GLU glu = new GLU();

		gl.glClearColor(0, 0, 0, 0); // Set the background color.

		// Now we set up our viewpoint.
		gl.glMatrixMode(GL.GL_PROJECTION); // We'll use orthogonal projection.
		gl.glLoadIdentity(); // Reset the current matrix.
		glu.gluPerspective(60, screenWidth, screenHeight, 200); // Set up the
																// parameters
																// for
																// perspective
																// viewing.
		gl.glMatrixMode(GL.GL_MODELVIEW);

		// Enable back-face culling.
		gl.glCullFace(GL.GL_BACK);
		gl.glEnable(GL.GL_CULL_FACE);

		// Enable Z-buffering.
		gl.glEnable(GL.GL_DEPTH_TEST);

		// Set and enable the lighting.
		float lightPosition[] = { 0.0f, 0.0f/* 50.0f */, 0.0f, 1.0f }; // High
																		// up in
																		// the
																		// sky!
		float lightColour[] = { 1.0f, 1.0f, 1.0f, 0.0f }; // White light!
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition, 0); // Note
																		// that
																		// we're
																		// setting
																		// Light0.
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, lightColour, 0);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);

		// Set the shading model.
		gl.glShadeModel(GL.GL_SMOOTH);

		// Loading textures
		System.out.println("Loading textures");

		if (textrue) {

			maze.textures();
			textureAdd(gl);
			textrue = false;
		}

	}

	private void textureAdd(GL gl) {
		for (int i = 0; i < visibleObjects.size(); i++) {

			if(visibleObjects.get(i) instanceof Trap){
				Trap tr = (Trap)visibleObjects.get(i);
				tr.Kaft1.addTexture(maze.Blauw);
				tr.Kaft2.addTexture(maze.Groen);
				tr.Kaft3.addTexture(maze.Rood);
				tr.Papier.addTexture(maze.Wit);
				
				
			}
		}
	}

	/**
	 * display(GLAutoDrawable) is called upon whenever OpenGL is ready to draw a
	 * new frame and handles all of the drawing.
	 * <p>
	 * Implemented through GLEventListener. In order to draw everything needed,
	 * it iterates through MazeRunners' list of visibleObjects. For each
	 * visibleObject, this method calls the object's display(GL) function, which
	 * specifies how that object should be drawn. The object is passed a
	 * reference of the GL context, so it knows where to draw.
	 */
	public void display(GLAutoDrawable drawable) {
		input.thisX = this.getX();
		input.thisY = this.getY();

		// StartScherm
		if (start) {
			this.setCursor(Cursor.getDefaultCursor());
			input.pauze = true;
			StartScherm(drawable);
		}

		if ((!input.getPauze() && !start)) {

			this.setCursor(this.getToolkit().createCustomCursor(
					new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
					new Point(0, 0), "null"));

			if (input.schiet) {
				Projectile book = new Projectile(player.locationX,
						player.locationY, player.locationZ,
						player.getHorAngle(), player.getVerAngle());
				projectiles.add(book);
				
				Sound bookthrow = new Sound("BookThrow.wav");
				bookthrow.play();
				bookthrow.setGain(-10);
				input.schiet = false;
			}

			Ingame(drawable);
			HUD(drawable);

		}
		// Pauzing te game progress
		if (input.getPauze() && !start) {

			this.setCursor(Cursor.getDefaultCursor());

			// Start PauzeMenu
			Pauzemenu(drawable);

			input.waspauzed = true;

		}

	}

	/**
	 * displayChanged(GLAutoDrawable, boolean, boolean) is called upon whenever
	 * the display mode changes.
	 * <p>
	 * Implemented through GLEventListener. Seeing as this does not happen very
	 * often, we leave this unimplemented.
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		// GL gl = drawable.getGL();
	}

	/**
	 * reshape(GLAutoDrawable, int, int, int, int, int) is called upon whenever
	 * the viewport changes shape, to update the viewport setting accordingly.
	 * <p>
	 * Implemented through GLEventListener. This mainly happens when the window
	 * changes size, thus changin the canvas (and the viewport that OpenGL
	 * associates with it). It adjust the projection matrix to accomodate the
	 * new shape.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();
		GLU glu = new GLU();

		// Setting the new screen size and adjusting the viewport.
		screenWidth = width;
		screenHeight = height;

		input.boundx = this.getBounds().getWidth() - width;
		input.boundy = this.getBounds().getHeight() - height;
		input.screenWidth = screenWidth;
		input.screenHeight = screenHeight;

		gl.glViewport(0, 0, screenWidth, screenHeight);

		// Set the new projection matrix.
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(60, screenWidth / screenHeight, .1, 200);
		gl.glMatrixMode(GL.GL_MODELVIEW);

	}

	/*
	 * **********************************************
	 * * Methods * **********************************************
	 */

	/**
	 * updateMovement(int) updates the position of all objects that need moving.
	 * This includes rudimentary collision checking and collision reaction.
	 */
	private void updateMovement(int deltaTime) {
		player.update(deltaTime, maze);
		/*
		 * Update position of the objects to next Steps. ObjectPositions is
		 * given to the objects, so that they can look for other objects, and
		 * avoid them. The one that is first called, 'wins'.
		 */

		for (int i = 0; i < visibleObjects.size(); i++) {

			visibleObjects.get(i).update(deltaTime, maze, visibleObjects ,player);
			
			if(visibleObjects.get(i) instanceof SchuifMuur){
				SchuifMuur SM = (SchuifMuur)visibleObjects.get(i);
				if(SM.open){
					maze.maze[SM.x][SM.z]= 0; 
				}else if(!SM.open){
					maze.maze[SM.x][SM.z]= 7; 
				}
				
				
			}
			
			if(visibleObjects.get(i) instanceof Smart){

				Smart so = (Smart) visibleObjects.get(i);
				if (so.destroy) {
					visibleObjects.remove(i);
				}
			}

		}
		
		visibleObjects.set(0, maze);
		
		
		for(int i = 0; i < projectiles.size(); i ++){

			projectiles.get(i).update(deltaTime, maze);

			for (int j = 0; j < lifeforms.size(); j++) {
				if (lifeforms.get(j).isHit(projectiles.get(i))) {
					projectiles.get(i).setDestroy(true);
				}
			}

			if (projectiles.get(i).getDestroy()) {
				projectiles.remove(i);
			}
		}
		for (int i = 0; i < lifeforms.size(); i++) {
			lifeforms.get(i).update(deltaTime, maze, player);

			boolean collide = false;

			for (int j = 0; j < lifeforms.size(); j++) {
				if (i != j) {
					if (lifeforms.get(i).Collide(lifeforms.get(j))) {
						collide = true;
					}
				}
			}

			if (collide) {
				lifeforms.get(i).stepBack(deltaTime);
			}

			if (lifeforms.get(i).getPlayerHit()) {
				player.Hit();
			}

			if (lifeforms.get(i).getSight()) {
				for (int k = 0; k < lifeforms.size(); k++) {
					if (i != k) {
						lifeforms.get(k).SetPlayerLocation(
								lifeforms.get(i).getPlayerLocation());
					}
				}
			}	
		}
		
//		if(input.action){
//			for(int i = 0; i < maze.maze.length; i ++){
//				for(int j = 0; j < maze.maze.length; j++){
//					if(maze.maze[i][j] == 1){
//						maze.maze[i][j] = 0;
//					}
//				}
//			}
//			
//			visibleObjects.set(0, maze);
//		}

	}

	/**
	 * updateCamera() updates the camera position and orientation.
	 * <p>
	 * This is done by copying the locations from the Player, since MazeRunner
	 * runs on a first person view.
	 */
	private void updateCamera() {
		camera.setLocationX(player.getLocationX());
		camera.setLocationY(player.getLocationY());
		camera.setLocationZ(player.getLocationZ());
		camera.setHorAngle(player.getHorAngle());
		camera.setVerAngle(player.getVerAngle());
		camera.calculateVRP();
	}

	private void rectOnScreen(GL gl, float x, float y, float height, float width) {
		height = height / 2.0f;
		width = width / 2.0f;

		gl.glBegin(GL.GL_QUADS);

		gl.glVertex2f(x - width, y + height);
		gl.glVertex2f(x - width, y - height);
		gl.glVertex2f(x + width, y - height);
		gl.glVertex2f(x + width, y + height);
		gl.glEnd();
	}

	private void StartScherm(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		switchTo2D(drawable);

		// Draw PauzeMenu
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		gl.glColor3f(1f, 0f, 0f);

		rectOnScreen(gl, screenWidth / 2.0f, screenHeight / 2.0f,
				screenHeight / 1.5f, screenWidth / 1.5f);

		gl.glColor3f(0.35f, 0.35f, 0.35f);

		rectOnScreen(gl, screenWidth / 2.0f, screenHeight / 2.0f, (float) 0.95
				* screenHeight / 1.5f, (float) 0.95 * screenWidth / 1.5f);

		if (true/* !loading */) {
			Button button = new Button(gl, screenWidth, screenHeight, 5,
					"Continue");
			button.NegIfIn(input.CurrentX, input.CurrentY);
			button.PresIfIn(input.PressedX, input.PressedY);

			if (button.CursorInButton(input.ReleaseX, input.ReleaseY)
					&& button.CursorInButton(input.WasPressedX,
							input.WasPressedY)) {
				start = false;
				input.pauze = false;
				init = true;
			}
		}

		switchTo3D(drawable);

		input.waspauzed = true;
		input.mouseReleasedUsed();
	}

	private void Ingame(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		GLU glu = new GLU();

		// Calculating time since last frame.
		Calendar now = Calendar.getInstance();
		long currentTime = now.getTimeInMillis();

		int deltaTime = (int) (currentTime - previousTime);

		if (deltaTime > 1000) {
			deltaTime = 16;
		}

		previousTime = currentTime;

		if (input.getWaspauzed() == true) {
			deltaTime = 0;
			input.waspauzed = false;
		}

		// Seconden tellen
		miliseconds = miliseconds + deltaTime;

		if (miliseconds > 999) {
			clock.seconds = clock.seconds + 1;
			miliseconds = miliseconds - 1000;
		}

		if (init) {
			System.out.println("setting player normal");

			input.xd = input.screenWidth / 2;
			input.yd = input.screenHeight / 2;
			player.setHorAngle(90);
			player.setVerAngle(0);
		}

		// Update any movement since last frame.
		updateMovement(deltaTime);

		updateCamera();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		glu.gluLookAt(camera.getLocationX(), camera.getLocationY(),
				camera.getLocationZ(), camera.getVrpX(), camera.getVrpY(),
				camera.getVrpZ(), camera.getVuvX(), camera.getVuvY(),
				camera.getVuvZ());

		// Display all the visible objects of MazeRunner.
		for (Iterator<VisibleObject> it = visibleObjects.iterator(); it
				.hasNext();) {
			it.next().display(gl);
		}
		for (Iterator<Projectile> it = projectiles.iterator(); it.hasNext();) {
			it.next().display(gl);
		}
		for (Iterator<Lifeform> it = lifeforms.iterator(); it.hasNext();) {
			it.next().display(gl);
		}

		gl.glLoadIdentity();
		// Flush the OpenGL buffer.
		gl.glFlush();

		if (!loading)
			init = false;
		else
			loading = false;

	}

	private void Pauzemenu(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		switchTo2D(drawable);

		// Draw PauzeMenu
		gl.glColor3f(1f, 0f, 0f);

		rectOnScreen(gl, screenWidth / 2.0f, screenHeight / 2.0f,
				screenHeight / 1.5f, screenWidth / 1.5f);

		gl.glColor3f(0.35f, 0.35f, 0.35f);

		rectOnScreen(gl, screenWidth / 2.0f, screenHeight / 2.0f, (float) 0.95
				* screenHeight / 1.5f, (float) 0.95 * screenWidth / 1.5f);

		// Draw PauzeMenuButtons
		Button button1 = new Button(gl, screenWidth, screenHeight, 1, "Resume");
		Button button2 = new Button(gl, screenWidth, screenHeight, 2,
				"Not implemented");
		Button button3 = new Button(gl, screenWidth, screenHeight, 3,
				"Switch GodMode");
		Button button4 = new Button(gl, screenWidth, screenHeight, 4,
				"Exit to main menu");

		button1.NegIfIn(input.CurrentX, input.CurrentY);
		button2.NegIfIn(input.CurrentX, input.CurrentY);
		button3.NegIfIn(input.CurrentX, input.CurrentY);
		button4.NegIfIn(input.CurrentX, input.CurrentY);

		button1.PresIfIn(input.PressedX, input.PressedY);
		button2.PresIfIn(input.PressedX, input.PressedY);
		button3.PresIfIn(input.PressedX, input.PressedY);
		button4.PresIfIn(input.PressedX, input.PressedY);

		switchTo3D(drawable);

		// Checking if button is pressed
		if (button1.CursorInButton(input.ReleaseX, input.ReleaseY)
				&& button1.CursorInButton(input.WasPressedX, input.WasPressedY)) {
			ButtonResume();
		} else if (button2.CursorInButton(input.ReleaseX, input.ReleaseY)
				&& button2.CursorInButton(input.WasPressedX, input.WasPressedY)) {

		} else if (button3.CursorInButton(input.ReleaseX, input.ReleaseY)
				&& button3.CursorInButton(input.WasPressedX, input.WasPressedY)) {
			ButtonGodMode();
		} else if (button4.CursorInButton(input.ReleaseX, input.ReleaseY)
				&& button4.CursorInButton(input.WasPressedX, input.WasPressedY)) {
			ButtonExit();
		}

		input.mouseReleasedUsed();
	}

	private void HUD(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		switchTo2D(drawable);

		HealthBar bar = new HealthBar(screenHeight, player.hp);
		bar.display(gl);

		clock.display(gl, screenWidth, screenHeight);
		
		boolean drawE = false;
		
		for(int i = 0; i < visibleObjects.size(); i++){
			if(visibleObjects.get(i) instanceof SchuifMuur){
				SchuifMuur SM = (SchuifMuur)visibleObjects.get(i);
				
				if(SM.inrange)
					drawE = true;
			}
		}
		
		for(int i = 0; i < visibleObjects.size(); i++){
			if(visibleObjects.get(i) instanceof Trap){
				Trap tr = (Trap)visibleObjects.get(i);
				
				if(tr.inrange)
					drawE = true;
			}
		}
		
		for(int i = 0; i < visibleObjects.size(); i++){
			if(visibleObjects.get(i) instanceof Trapaf){
				Trapaf traf = (Trapaf)visibleObjects.get(i);
				
				if(traf.inrange)
					drawE = true;
			}
		}
		
		if(drawE)
			Image.drawImage(gl, screenWidth/2 - 127/2, screenHeight/2 - 119/2, 127, 119, E);
		

		switchTo3D(drawable);

	}

	private void switchTo2D(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glOrtho(0, drawable.getWidth(), 0, drawable.getHeight(), -1, 1);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glLoadIdentity();
	}

	private void switchTo3D(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();
		gl.glMatrixMode(GL.GL_MODELVIEW);
	}

	private void ButtonResume() {
		input.SwitchPauze();
	}

	private void ButtonExit() {
		dispose(); // exit MazeRunner

		new Menu(); // start Menu
	}

	private void ButtonGodMode() {

		if (player.getGodMode()) {

			player.locationX = 20 * maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2;
			player.locationY = maze.SQUARE_SIZE / 2;
			player.locationZ = 1 * maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2;
			player.setVerAngle(0);
			player.setHorAngle(90);

			player.setGodMode(false);
		} else if (!player.getGodMode()) {

			player.setGodMode(true);
		}
	}

	/*
	 * **********************************************
	 * * Main * **********************************************
	 */
	/**
	 * Program entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create and run MazeRunner.
		new MazeRunner();
	}
}