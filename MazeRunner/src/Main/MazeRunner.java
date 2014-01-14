package Main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import javax.swing.JFrame;

import MenuButtons.Button;
import MenuButtons.Knop;
import movingobjects.CompanionCube;
import movingobjects.CustomMazeObject;
import movingobjects.Lifeform;
import movingobjects.MazeObject;
import movingobjects.Peter;
import movingobjects.Projectile;
import movingobjects.Randy;
import movingobjects.Smart;
import movingobjects.Smarto;
import movingobjects.Smartw;
import movingobjects.VisibleObject;
import HUD.Clock;
import HUD.HealthBar;
import leveleditor.Image;
import Main.Menu.SelectWin;
import Maze.Maze;
import NotDefined.SchuifMuur;
import NotDefined.Sound;
import Player.Camera;
import Player.Player;
import Player.UserInput;
import Score.Score;
import Score.SubmitWindow;

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
	 * *			Local variables					*
	 * **********************************************
	 */
	private GLCanvas canvas;

	private int screenWidth = 1000, screenHeight = 1000; // Screen size.
	private ArrayList<VisibleObject> visibleObjects; // A list of objects that
	// will be displayed on
	// screen.
	private ArrayList<Projectile> projectiles;
	private ArrayList<Lifeform> lifeforms;
	private ArrayList<Sound> SoundPeter = new ArrayList<Sound>();
	private ArrayList<Sound> SoundRandy = new ArrayList<Sound>();
	private Player player; // The player object.
	private Camera camera; // The camera object.
	private UserInput input; // The user input object that controls the player.
	private Maze maze; // The maze.
	private long previousTime = Calendar.getInstance().getTimeInMillis(); // Used
	// to
	// calculate
	// elapsed
	// time

	private int endx = 23;
	private int endz = 20;
	private int difficulty;
	// startup hulp booleans
	private boolean start = true;
	private boolean end = false;
	private boolean GameOver = false;
	private boolean submit = false;
	private boolean init = true;
	private boolean loading = true;

	private boolean textrue = true;
	private boolean GOtrue = true;
	private boolean Eindtrue = true;

	private Score score;
	private boolean calculatescore = false;
	
	// Ingame seconden tellen
	private int miliseconds = 0;

	private Clock clock = new Clock();

	private byte[] E = Image.loadImage("E.png");

	private int[] coordT,coordTa,coordTo = new int[4];

	private byte[] PauzeImage = Image.loadImage("Pauze.png");
	private byte[] PauzeResumeClick = Image.loadImage("ResumeClick.png");
	private byte[] PauzeResumeHover = Image.loadImage("Resumehover.png");
	private byte[] PauzeExitClick = Image.loadImage("ExitClick.png");
	private byte[] PauzeExitHover = Image.loadImage("PauzeExitHover.png");
	private byte[] gameOver = Image.loadImage("GameOver.png");
	private byte[] gameOverHover = Image.loadImage("GameOverHover.png");
	private byte[] gameOverClick = Image.loadImage("GameOverClick.png");
	private byte[] gameComplete = Image.loadImage("GameComplete.png");
	private byte[] gameSubmitHover = Image.loadImage("SubmitHover.png");
	private byte[] gameSubmitClick = Image.loadImage("SubmitClick.png");
	private byte[] gameBackClick = Image.loadImage("BackClick.png");
	private byte[] gameBackHover = Image.loadImage("BackHover.png");
	private byte[] Continue = Image.loadImage("Continue.png");
	private byte[] ContinueClick = Image.loadImage("ContinueClick.png");
	private byte[] ContinueHover = Image.loadImage("ContinueHover.png");
	private byte[] HighScore = Image.loadImage("HighScore.png");
	private byte[] HighScoreClick = Image.loadImage("HighScoreClick.png");
	private byte[] HighScoreHover = Image.loadImage("HighScoreHover.png");
	
	private Sound GameOverSound = new Sound("birddood.wav");
	private Sound GameWonSound = new Sound("win.wav");

	private int numberOfEnemies = 3;





	/*
	 * **********************************************
	 * * Initialization methods * **********************************************
	/*
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
	public MazeRunner(int difficulty) {
		// Make a new window.
		super("MazeRunner");
		System.out.println("Difficulty: "+ difficulty);
		this.difficulty = difficulty;
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


		SchuifMuur SM = new SchuifMuur(5,5,maze);
		visibleObjects.add(SM);

		CompanionCube(numberOfEnemies,1.5, 1);

//			    Peter peter = new Peter(player.locationX, 0, player.locationZ);
//			    lifeforms.add(peter);

		coordT = Maze.CoordTrap(Maze.maze);
		Trap tr1 = new Trap((float) coordT[0], (float) coordT[1]);
		Trap tr2 = new Trap((float) coordT[2], (float) coordT[3]);
		visibleObjects.add(tr1);
		visibleObjects.add(tr2);
		coordTa = Maze.CoordTrapaf(Maze.maze);
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
	 * *		OpenGL event handlers				*
	 * **********************************************
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

	public void CompanionCube(int num, double size, int level) {
		System.out.println("enemies op level: " + level);
		
		lifeforms = new ArrayList<Lifeform>();
		SoundPeter = new ArrayList<Sound>();
		
		int j,k,count = 0;
	
		if(level == 3){
		    k = 1;
			j = 0;
		}else if(level == 2){
			j = 1;
			k = 0;
		}else{
			j = 0;
			k = 0;
		}
		
					for (int i = 0; i < num; i++) {

						double X = player.locationX;
						double Z = player.locationZ;

						boolean GO = true;

						while (GO) {
							X = (Math.random()+k) * 22 * maze.SQUARE_SIZE;
							Z = (Math.random()+j) * 22 * maze.SQUARE_SIZE;

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

						Randy R = new Randy(X, 0, Z);
						lifeforms.add(R);
						Sound Rs = new Sound("randy.wav");
						SoundRandy.add(Rs);
						SoundRandy.get(count).playloop();
						SoundRandy.get(count).setGain(-70);
						count = count + 1;
						
					}
				
			
		
	}

	public void init(GLAutoDrawable drawable) {
		drawable.setGL(new DebugGL(drawable.getGL())); // We set the OpenGL
		// pipeline to Debugging
		// mode.

		GL gl = drawable.getGL();
		GLU glu = new GLU();


		gl.glClearColor(0, 0, 0, 0);								// Set the background color.

		// Now we set up our viewpoint.
		gl.glMatrixMode( GL.GL_PROJECTION );						// We'll use orthogonal projection.
		gl.glLoadIdentity();										// Reset the current matrix.
		glu.gluPerspective( 60, screenWidth, screenHeight, 200);	// Set up the parameters for perspective viewing.
		gl.glMatrixMode( GL.GL_MODELVIEW );

		// Enable back-face culling.
		gl.glCullFace( GL.GL_BACK );
		gl.glEnable( GL.GL_CULL_FACE );

		// Enable Z-buffering.
		gl.glEnable( GL.GL_DEPTH_TEST );

		// Set and enable the lighting.
		float lightPosition[] = { 0.0f, 0.0f/*50.0f*/, 0.0f, 1.0f }; 			// High up in the sky!
		float lightColour[] = { 1.0f, 1.0f, 1.0f, 0.0f };				// White light!
		gl.glLightfv( GL.GL_LIGHT0, GL.GL_POSITION, lightPosition, 0 );	// Note that we're setting Light0.
		gl.glLightfv( GL.GL_LIGHT0, GL.GL_AMBIENT, lightColour, 0);
		gl.glEnable( GL.GL_LIGHTING );
		gl.glEnable( GL.GL_LIGHT0 );

		// Set the shading model.
		gl.glShadeModel( GL.GL_SMOOTH );

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
		for (int i = 0; i < lifeforms.size();i++){
			if(lifeforms.get(i) instanceof Peter){
				Peter pet = (Peter)lifeforms.get(i);
				pet.Lichaam.addTexture(maze.lichaam);
				pet.Haar.addTexture(maze.haar);
				pet.Frame.addTexture(maze.frame);
				pet.Ogen.addTexture(maze.Wit);
				pet.Shirt.addTexture(maze.Wit);
				pet.Riem.addTexture(maze.frame);
				pet.Broek.addTexture(maze.Groen);
				pet.Schoenen.addTexture(maze.schoenen);
				pet.Bier.addTexture(maze.bier);
				pet.Glas.addTexture(maze.glas);
				pet.Buckle.addTexture(maze.frame);
			}
		}
		
		for (int i = 0; i < lifeforms.size();i++){
			if(lifeforms.get(i) instanceof Randy){
				Randy ran = (Randy)lifeforms.get(i);
				ran.Lichaam.addTexture(maze.lichaam);
				ran.Haar.addTexture(maze.rhaar);
				ran.Gitaar.addTexture(maze.guitar);
				ran.Ogen.addTexture(maze.Wit);
				ran.Onderbroek.addTexture(maze.onderbroek);
				ran.Band.addTexture(maze.frame);
				ran.Binnenkant.addTexture(maze.binnenkant);
				ran.Schoenen.addTexture(maze.rschoenen);
				ran.Pupil.addTexture(maze.frame);
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
		if(start && !end){
			this.setCursor(Cursor.getDefaultCursor());
			input.pauze = true;
			StartScherm(drawable);
		}


		if((!input.getPauze() && !start && !end && !GameOver)){

			this.setCursor(this.getToolkit().createCustomCursor(
					new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
					"null"));

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
		if(input.getPauze() && !start && !end && !GameOver){

			this.setCursor(Cursor.getDefaultCursor());


			ButtonGodMode();

			// Start PauzeMenu
			Pauzemenu(drawable);

			input.waspauzed = true;

		}

		//Is player at endpoint?
		if(end)
		{
			if (Eindtrue){
				StopPeterRandySound();
				GameWonSound.playloop();
				Eindtrue = false;
			}
			this.setCursor(Cursor.getDefaultCursor());
			input.pauze = true;
			input.waspauzed = true;
			EindScherm(drawable);
		}

		if(GameOver)
		{
			if(GOtrue){
				GameOverSound.playloop();
				GOtrue = false;
			}
			this.setCursor(Cursor.getDefaultCursor());
			input.pauze = true;
			GameOverScherm(drawable);
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
	 * *				Methods						*
	 * **********************************************
	 */

	/**
	 * updateMovement(int) updates the position of all objects that need moving.
	 * This includes rudimentary collision checking and collision reaction.
	 */
	private void StopPeterRandySound(){
		for(int i = 0; i<SoundPeter.size();i++){
			SoundPeter.get(i).stop();
		}
		for(int i = 0; i<SoundRandy.size();i++){
			SoundRandy.get(i).stop();
		}
	}
	
	private void StartPeterRandySound(){
		for(int i = 0; i<SoundPeter.size();i++){
			SoundPeter.get(i).playloop();
		}
		for(int i = 0; i<SoundRandy.size();i++){
			SoundRandy.get(i).playloop();
		}
	}
	
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

			if(visibleObjects.get(i) instanceof Trap){
				Trap tr = (Trap)visibleObjects.get(i);
				if(tr.transport){
					if (maze.convertToGridX(player.getLocationZ())>22){
						coordTo[2] = (int) maze.convertToGridX(player.getLocationX());
						coordTo[3] = (int) maze.convertToGridZ(player.getLocationZ());
						player.setLocationX(maze.convertFromGridX(coordTa[0])+0.5*maze.SQUARE_SIZE);
						player.setLocationZ(maze.convertFromGridZ(coordTa[1])+0.5*maze.SQUARE_SIZE);
						tr.transport = false;
						
						CompanionCube(numberOfEnemies,1.5,3);
					}
					else{
						coordTo[0] = (int) maze.convertToGridX(player.getLocationX());
						coordTo[1] = (int) maze.convertToGridZ(player.getLocationZ());
						player.setLocationX(maze.convertFromGridX(coordTa[2])+0.5*maze.SQUARE_SIZE);
						player.setLocationZ(maze.convertFromGridZ(coordTa[3])+0.5*maze.SQUARE_SIZE);
						tr.transport = false;
						CompanionCube(numberOfEnemies,1.5,2);
					}
				}
			}

			if(visibleObjects.get(i) instanceof Trapaf){
				Trapaf tra = (Trapaf) visibleObjects.get(i);
				if(tra.transport){
					if (maze.convertToGridX(player.getLocationZ())>22){
						player.setLocationX(maze.convertFromGridX(coordTo[0])+0.5*maze.SQUARE_SIZE);
						player.setLocationZ(maze.convertFromGridZ(coordTo[1])+0.5*maze.SQUARE_SIZE);
						tra.transport = false;
						CompanionCube(numberOfEnemies,1.5,1);
					}
					else{
						player.setLocationX(maze.convertFromGridX(coordTo[2])+0.5*maze.SQUARE_SIZE);
						player.setLocationZ(maze.convertFromGridZ(coordTo[3])+0.5*maze.SQUARE_SIZE);
						tra.transport = false;
						CompanionCube(numberOfEnemies,1.5,2);
					}
				}
			}

			if(visibleObjects.get(i) instanceof Smart){

				Smart so = (Smart) visibleObjects.get(i);
				if(so.destroy){
					visibleObjects.remove(i);
				}
			}

		}

		visibleObjects.set(0, maze);


		for(int i = 0; i < projectiles.size(); i ++){

			projectiles.get(i).update(deltaTime, maze);

			for(int j = 0; j < lifeforms.size(); j++){
				if(lifeforms.get(j).isHit(projectiles.get(i))){
					projectiles.get(i).setDestroy(true);
				}
			}


			
			if(projectiles.get(i).getDestroy()){

				projectiles.remove(i);
			}
			
		
		}
		for (int i = 0; i < lifeforms.size(); i++) {
			lifeforms.get(i).update(deltaTime, maze, player);

			boolean collide = false;

			for(int j = 0; j < lifeforms.size(); j++){
				if( i != j){
					if(lifeforms.get(i).Collide(lifeforms.get(j))){
						collide = true;
					}
				}
			}

			if(collide){
				lifeforms.get(i).stepBack(deltaTime);
			}

			if(lifeforms.get(i).getPlayerHit()){
				player.Hit();
			}

			if(lifeforms.get(i).getSight()){
				for(int k = 0; k < lifeforms.size(); k++){
					if(i != k){
						lifeforms.get(k).SetPlayerLocation(lifeforms.get(i).getPlayerLocation());
					}
				}
			}
		}
		int soundintp = 0;
		for(int i = 0; i<lifeforms.size();i++){
			if(lifeforms.get(i) instanceof Peter){
				Peter pet = (Peter)lifeforms.get(i);
				double dX = pet.locationX - player.locationX;
				double dZ = pet.locationZ - player.locationZ;
				double dLength = Math.sqrt(Math.pow(dX,2)+Math.pow(dZ,2));
				SoundPeter.get(soundintp).setGain((float)(-2*dLength));
				soundintp = soundintp+1;
			}
		}
		
		int soundintr = 0;
		for(int i = 0; i<lifeforms.size();i++){
			if(lifeforms.get(i) instanceof Randy){
				Randy ran = (Randy)lifeforms.get(i);
				double dX = ran.locationX - player.locationX;
				double dZ = ran.locationZ - player.locationZ;
				double dLength = Math.sqrt(Math.pow(dX,2)+Math.pow(dZ,2));
				SoundRandy.get(soundintr).setGain((float)(-2*dLength));
				soundintr = soundintp+1;
			}
		}
		// If the player is at the endpoint, the games stops, in display wordt bepaald wat er getoon wordt.
		if(maze.convertToGridX(player.locationX) == endx && maze.convertToGridZ(player.locationZ) == endz){
			end = true;
			calculatescore = true;
		}
		// If the player has no healthpoints left, the game-over screen is displayed in display
		if(player.hp < 1)
		{
			GameOver = true;
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

	private void rectOnScreen(GL gl, float x, float y, float height, float width){
		height = height / 2.0f;
		width = width / 2.0f;

		gl.glBegin(GL.GL_QUADS);

		gl.glVertex2f(x - width,y + height);
		gl.glVertex2f(x - width,y - height);

		gl.glVertex2f(x + width, y - height);
		gl.glVertex2f(x + width, y + height);
		gl.glEnd();
	}

	private void StartScherm(GLAutoDrawable drawable){
		GL gl = drawable.getGL();

		switchTo2D(drawable);


		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		Image.drawImage(gl, screenWidth/2 - 295, screenHeight/2 - 295, 591, 590, Continue);



		Knop knopContinue = new Knop(screenWidth/2 - 295 + 307, screenHeight/2 - 295 + 303, screenWidth/2 - 295 + 438, screenHeight/2 - 295 + 277);
	

		if(knopContinue.inKnop(input.CurrentX, input.CurrentY)){
			Image.drawImage(gl, screenWidth/2 - 295, screenHeight/2 - 295, 591, 590, ContinueHover);
		}

		if(knopContinue.inKnop(input.PressedX, input.PressedY)){
			Image.drawImage(gl, screenWidth/2 - 295, screenHeight/2 - 295, 591, 590, ContinueClick);
		}

			if(knopContinue.inKnop(input.ReleaseX, input.ReleaseY) && knopContinue.inKnop(input.WasPressedX, input.WasPressedY)){
				start = false;
				input.pauze = false;
				init = true;
			}
		

		switchTo3D(drawable);  

		input.waspauzed = true;
		input.mouseReleasedUsed();
	}

	private void GameOverScherm(GLAutoDrawable drawable){
		GL gl = drawable.getGL();
		StopPeterRandySound();
		switchTo2D(drawable);

		Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, gameOver);

		// Draw PauzeMenuButtons

		Knop knopExit = new Knop(screenWidth/2 - 950/2 + 255, screenHeight/2 - 950/2 + 636, screenWidth/2 - 950/2 + 775, screenHeight/2 - 950/2 + 592);
		

		if(knopExit.inKnop(input.CurrentX, input.CurrentY)){
			Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, gameOverHover);
		}

		if(knopExit.inKnop(input.PressedX, input.PressedY)){
			Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, gameOverClick);
		}

		switchTo3D(drawable);


		if(knopExit.inKnop(input.ReleaseX, input.ReleaseY) && knopExit.inKnop(input.WasPressedX, input.WasPressedY)){
			ButtonExit();
		}
		input.mouseReleasedUsed();
	}

	private void EindScherm(GLAutoDrawable drawable){
		GL gl = drawable.getGL();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		switchTo2D(drawable);

		Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, gameComplete);

		if(submit)
		{
			ViewHighScores(drawable);
		}
		else
		{	
			
			if(calculatescore){
			int TimeInSeconds = clock.minutes*60 + clock.seconds;
			score = new Score();
			score.calculateNewScore(player.hp, TimeInSeconds);
			calculatescore = false;
			}
			score.drawScore(gl, screenWidth, screenHeight);

			Knop knopSubmit = new Knop(screenWidth/2 - 950/2 + 283, screenHeight/2 - 950/2 + 551, screenWidth/2 - 950/2 + 751, screenHeight/2 - 950/2 + 495);
			Knop knopExit = new Knop(screenWidth/2 - 950/2 + 255, screenHeight/2 - 950/2 + 636, screenWidth/2 - 950/2 + 775, screenHeight/2 - 950/2 + 592);
			

			if(knopExit.inKnop(input.CurrentX, input.CurrentY)){
				Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, gameBackHover);
			}else if(knopSubmit.inKnop(input.CurrentX, input.CurrentY)){
				Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, gameSubmitHover);
			}

			if(knopExit.inKnop(input.PressedX, input.PressedY)){
				Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, gameBackClick);
			}else if(knopSubmit.inKnop(input.PressedX, input.PressedY)){
				Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, gameSubmitClick);
			}

			switchTo3D(drawable);


			if(knopSubmit.inKnop(input.ReleaseX, input.ReleaseY) && knopSubmit.inKnop(input.WasPressedX, input.WasPressedY)){
			input.waspauzed = true;
				SubmitWindow YS = new SubmitWindow(score);
				SubmitScore(YS, drawable);
				submit = true;
				calculatescore = true;
			}else if(knopExit.inKnop(input.ReleaseX, input.ReleaseY) && knopExit.inKnop(input.WasPressedX, input.WasPressedY)){
				ButtonExit();
			}
			input.mouseReleasedUsed();
		}
	}

	private void ViewHighScores(GLAutoDrawable drawable){

		GL gl = drawable.getGL();
		
		if(calculatescore){
			score = new Score();
			calculatescore = false;
		}

		Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, HighScore);

		Knop knopExit = new Knop(screenWidth/2 - 950/2 + 217, screenHeight/2 - 950/2 + 637, screenWidth/2 - 950/2 + 733, screenHeight/2 - 950/2 + 594);

		if(knopExit.inKnop(input.CurrentX, input.CurrentY)){
			Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, HighScoreHover);
		}
		if(knopExit.inKnop(input.PressedX, input.PressedY)){
			Image.drawImage(gl, screenWidth/2 - 950/2, screenHeight/2 - 950/2, 950, 950, HighScoreClick);
		}	
		
		score.drawHighScores(gl, screenWidth, screenHeight);
		
		switchTo3D(drawable);
		input.waspauzed = true;
		if(knopExit.inKnop(input.ReleaseX, input.ReleaseY) && knopExit.inKnop(input.WasPressedX, input.WasPressedY)){
			dispose();
			GameWonSound.stop();
			new Menu();
		}

	}

	private void SubmitScore(SubmitWindow YS, GLAutoDrawable drawable) {
		YS.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		YS.setSize(150,250);
		YS.setVisible(true);
		YS.setTitle("Submit");
	}

	private void Ingame(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		GLU glu = new GLU();

		// Calculating time since last frame.
		Calendar now = Calendar.getInstance();
		long currentTime = now.getTimeInMillis();

		int deltaTime = (int)(currentTime - previousTime);

		if(deltaTime > 1000){

			deltaTime = 16;
		}

		previousTime = currentTime;

		if(input.getWaspauzed() == true){
			deltaTime = 0;
			input.waspauzed = false;
		}

		// Seconden tellen
		miliseconds = miliseconds + deltaTime;

		if(miliseconds > 999){
			clock.seconds = clock.seconds +1;
			miliseconds = miliseconds - 1000;
		}



		if(init){
			System.out.println("setting player normal");

			input.xd = input.screenWidth/2;
			input.yd = input.screenHeight/2;
			player.setHorAngle(90);
			player.setVerAngle(0);
		}

		// Update any movement since last frame.
		updateMovement(deltaTime);

		updateCamera();

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
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

		if(!loading)
			init = false;
		else
			loading = false;

	}

	private void Pauzemenu(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();

		switchTo2D(drawable);

		// Draw PauzeMenu
		//		gl.glColor3f(1f, 0f, 0f);
		//
		//		rectOnScreen(gl,screenWidth/2.0f,screenHeight/2.0f, screenHeight/1.5f, screenWidth/1.5f);
		//
		//		gl.glColor3f(0.35f, 0.35f, 0.35f);
		//
		//		rectOnScreen(gl,screenWidth/2.0f,screenHeight/2.0f, (float)0.95*screenHeight/1.5f, (float)0.95*screenWidth/1.5f);

		Image.drawImage(gl, screenWidth/2 - 295, screenHeight/2 - 295, 591, 590, PauzeImage);

		// Draw PauzeMenuButtons

		Knop knopResume = new Knop(screenWidth/2 - 295 + 152, screenHeight/2 - 295 + 162, screenWidth/2 - 295 + 269, screenHeight/2 - 295 + 134);
		Knop knopExit = new Knop(screenWidth/2 - 295 + 173, screenHeight/2 - 295 + 256, screenWidth/2 - 295 + 234, screenHeight/2 - 295 + 226);

		if(knopResume.inKnop(input.CurrentX, input.CurrentY)){
			Image.drawImage(gl, screenWidth/2 - 295, screenHeight/2 - 295, 591, 590, PauzeResumeHover);
		}else if(knopExit.inKnop(input.CurrentX, input.CurrentY)){
			Image.drawImage(gl, screenWidth/2 - 295, screenHeight/2 - 295, 591, 590, PauzeExitHover);
		}

		if(knopResume.inKnop(input.PressedX, input.PressedY)){
			Image.drawImage(gl, screenWidth/2 - 295, screenHeight/2 - 295, 591, 590, PauzeResumeClick);
		}else if(knopExit.inKnop(input.PressedX, input.PressedY)){
			Image.drawImage(gl, screenWidth/2 - 295, screenHeight/2 - 295, 591, 590, PauzeExitClick);
		}

		//		Button button1 = new Button(gl, screenWidth, screenHeight, 1, "Resume");
		//		Button button2 = new Button(gl, screenWidth, screenHeight, 2, "Not implemented");
		//		Button button3 = new Button(gl, screenWidth, screenHeight, 3, "Switch GodMode");
		//		Button button4 = new Button(gl, screenWidth, screenHeight, 4, "Exit to main menu");
		//
		//		button1.NegIfIn(input.CurrentX, input.CurrentY);
		//		button2.NegIfIn(input.CurrentX,  input.CurrentY);
		//		button3.NegIfIn(input.CurrentX,  input.CurrentY);
		//		button4.NegIfIn(input.CurrentX, input.CurrentY);
		//
		//		button1.PresIfIn(input.PressedX, input.PressedY);
		//		button2.PresIfIn(input.PressedX, input.PressedY);
		//		button3.PresIfIn(input.PressedX, input.PressedY);
		//		button4.PresIfIn(input.PressedX, input.PressedY);

		switchTo3D(drawable);  

		// Checking if button is pressed

		if(knopResume.inKnop(input.ReleaseX, input.ReleaseY) && knopResume.inKnop(input.WasPressedX, input.WasPressedY)){
			ButtonResume();
		}else if(knopExit.inKnop(input.ReleaseX, input.ReleaseY) && knopExit.inKnop(input.WasPressedX, input.WasPressedY)){

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
			Image.drawImage(gl, screenWidth/2 - 100/2, screenHeight/2 - 100/2, 100, 100, E);


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

	private void switchTo3D(GLAutoDrawable drawable)
	{
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

	private void ButtonExit(){
		dispose();   // exit MazeRunner
		GameOverSound.stop();
		new Menu();  // start Menu
	}

	private void ButtonGodMode(){

		if(player.getGodMode()){

			player.locationX = 20 * maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2;
			player.locationY = maze.SQUARE_SIZE / 2;
			player.locationZ = 1 * maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2;
			player.setVerAngle(0);
			player.setHorAngle(90);

			player.setGodMode(false);
		}

	}

	/*
	 * **********************************************
	 * * Main * **********************************************
=======

	/*
	 * **********************************************
	 * *				  Main						*
	 * **********************************************
>>>>>>> cfd49c3c9a9e32f8d7e9ef9205e67e234446947f
	 */
	/**
	 * Program entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create and run MazeRunner.
		new MazeRunner(1);	//easy
	}
}