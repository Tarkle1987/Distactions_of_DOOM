import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;







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
 * @author Kang
 * 
 */
public class Menu extends Frame implements GLEventListener, MouseListener, MouseMotionListener {
	static final long serialVersionUID = 7526471155622776147L;

	// Screen size.
	private int screenWidth = 600, screenHeight = 600;
	private float buttonHeight = screenHeight / 15.0f;
	private float buttonWidth = screenWidth / 2.5f;
	private float buttonSpace = buttonHeight + buttonHeight / 4.0f;
	
	private int CurrentX = 0;
	private int CurrentY = 0;
	private int ReleaseX = 0;
	private int ReleaseY = 0;
	private int PressedX = 0;
	private int PressedY = 0;
	private int WasPressedX = 0;
	private int WasPressedY = 0;
	
	private int Difficulty = 0;
	
	private Sound bird = new Sound("bird.wav");

	// A GLCanvas is a component that can be added to a frame. The drawing
	// happens on this component.
	private GLCanvas canvas;
	
	private static final byte Menu = 0;
	private static final byte Settings = 1;
	private static final byte Level_Editor = 2;
	private byte mode = Menu;

	private LevelEditor LE;


	/**
	 * When instantiating, a GLCanvas is added for us to play with. An animator
	 * is created to continuously render the canvas.
	 */
	public Menu() {
		super("Menu");



		// Set the desired size and background color of the frame
		setSize(screenWidth, screenHeight);
		//setBackground(Color.white);
		setBackground(new Color(0.95f, 0.95f, 0.95f));

		// When the "X" close button is called, the application should exit.
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// The OpenGL capabilities should be set before initializing the
		// GLCanvas. We use double buffering and hardware acceleration.
		GLCapabilities caps = new GLCapabilities();
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);

		// Create a GLCanvas with the specified capabilities and add it to this
		// frame. Now, we have a canvas to draw on using JOGL.
		canvas = new GLCanvas(caps);
		add(canvas);

		// Set the canvas' GL event listener to be this class. Doing so gives
		// this class control over what is rendered on the GL canvas.
		canvas.addGLEventListener(this);

		// Also add this class as mouse listener, allowing this class to react
		// to mouse events that happen inside the GLCanvas.
		canvas.addMouseListener(this);
		
		canvas.addMouseMotionListener(this);

		// An Animator is a JOGL help class that can be used to make sure our
		// GLCanvas is continuously being re-rendered. The animator is run on a
		// separate thread from the main thread.
		Animator anim = new Animator(canvas);
		anim.start();

		// With everything set up, the frame can now be displayed to the user.
		setVisible(true);
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
		gl.glClearColor(0f, 0f, 0f, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		
		switch(mode){
		
		case(Menu) :
			
			MenuScreen(gl);
			break;
		case(Settings) :
			SettingScreen(gl);
			break;
		case(Level_Editor) :
			LE.display(drawable);
			LE.reshape(drawable, this.getX(), this.getY(), screenWidth, screenHeight);
			if(LE.dispose){
				mode = Menu;
				//this.setSize(600, 600);
			}
			break;
		case(3):
			gl.glClearColor(0f, 0f, 0f, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT);

			
			Clock clock = new Clock();
			clock.minutes = 12;
			clock.seconds = 34;
			clock.display(gl, screenWidth, screenHeight);
			break;

			
		}
		
		
		

		// Draw a figure based on the current draw mode and user input
		//drawFigure(gl);

		// Flush the OpenGL buffer, outputting the result to the screen.
		gl.glFlush();
	}

	public void MenuScreen(GL gl){
		
		Button button1 = new Button(gl, screenWidth, screenHeight, 2, "Start Game");
		Button button2 = new Button(gl, screenWidth, screenHeight, 3, "Settings");
		Button button3 = new Button(gl, screenWidth, screenHeight, 4, "Level Editor");
		Button button4 = new Button(gl, screenWidth, screenHeight, 5, "Exit Game");
		Button sound = new Button(gl, screenWidth, screenHeight, 6, "sound");
		Button gain = new Button(gl, screenWidth, screenHeight, 7, "gain");
		
		button1.NegIfIn(CurrentX,CurrentY);
		button2.NegIfIn(CurrentX,CurrentY);
		button3.NegIfIn(CurrentX,CurrentY);
		button4.NegIfIn(CurrentX,CurrentY);
		
		button1.PresIfIn(PressedX, PressedY);
		button2.PresIfIn(PressedX, PressedY);
		button3.PresIfIn(PressedX, PressedY);
		button4.PresIfIn(PressedX, PressedY);
		
		
		if(button1.CursorInButton(ReleaseX, ReleaseY) && button1.CursorInButton(WasPressedX, WasPressedY)){
			MenuButton1();
			
		}else if(button2.CursorInButton(ReleaseX, ReleaseY) && button2.CursorInButton(WasPressedX, WasPressedY)){
			MenuButton2();
		}else if(button3.CursorInButton(ReleaseX, ReleaseY) && button3.CursorInButton(WasPressedX, WasPressedY)){
			MenuButton3();
		}else if(button4.CursorInButton(ReleaseX, ReleaseY) && button4.CursorInButton(WasPressedX, WasPressedY)){
			MenuButton4();
		}else if(sound.CursorInButton(ReleaseX, ReleaseY) && sound.CursorInButton(WasPressedX, WasPressedY)){
			bird.playloop();
			bird.setGain(1.0f);
		
		}else if(gain.CursorInButton(ReleaseX, ReleaseY) && gain.CursorInButton(WasPressedX, WasPressedY)){
			bird.IncreaseGain();
		}
		
		// resetting used values
		ReleaseX = 0;ReleaseY = 0;


	}
	public void SettingScreen(GL gl){
		
		Button button1 = new Button(gl, screenWidth, screenHeight, 5, "Back");
		RadioGroup radio1 = new RadioGroup(gl, screenWidth, screenHeight, 2, "Difficulty");
		radio1.addButton(0,"Easy");
		radio1.addButton(1, "Medium");
		radio1.addButton(2, "Hard");
		Difficulty = radio1.drawGroup(CurrentX,CurrentY,PressedX,PressedY, Difficulty);
		
		
		
		button1.NegIfIn(CurrentX,CurrentY);
		
		button1.PresIfIn(PressedX, PressedY);
		
		if(button1.CursorInButton(ReleaseX, ReleaseY) && button1.CursorInButton(WasPressedX, WasPressedY)){
			SettingsButton1();
		}
		
		// resetting used values
		ReleaseX = 0;ReleaseY = 0;
	
	}
	
		private void drawText(GL gl, String text, int x, int y)
	{
		int length = text.length();
		GLUT glut = new GLUT();
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glRasterPos2i(x, y); // raster position in 2D
		for(int i=0; i<length; i++)
		{
			glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, text.charAt(i)); // generation of characters in our text with 9 by 15 GLU font
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
	
	private void rectOnScreen(GL gl, float x, float y, float height, float width){
		height = height / 2.0f;
		width = width / 2.0f;
		
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(x + width,y + height);
		gl.glVertex2f(x + width, y - height);
		gl.glVertex2f(x - width,y - height);
		gl.glVertex2f(x - width,y + height);
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

		
		if(LE != null){
			LE.reshape(drawable, x, y, width, height);
		}
		// Set the new screen size and adjusting the viewport
		screenWidth = width;
		screenHeight = height;
		buttonHeight = screenHeight / 15.0f;
		buttonWidth = screenWidth / 2.5f;
		buttonSpace = buttonHeight + buttonHeight / 4.0f;
		gl.glViewport(0, 0, screenWidth, screenHeight);

		// Update the projection to an orthogonal projection using the new screen size
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);
	}

	public void MenuButton1(){
		SelectWin YS = new SelectWin();
		YS.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		YS.setSize(150,250);
		YS.setVisible(true);
		YS.setTitle("Choose");
		
		dispose();
		
	}
	public void MenuButton2(){
		mode = Settings;
	}
	public void MenuButton3(){
		LE = new LevelEditor(this);
		mode = Level_Editor;
	}
	public void MenuButton4(){
		System.out.println("Exit");
		System.exit(0);
	}
	
	public void SettingsButton1(){
		mode = Menu;
	}
	
	/**
	 * A function defined in MouseListener. Is called when the pointer is in the GLCanvas, and a mouse button is released.
	 */
	public void mouseReleased(MouseEvent me) {
		
		
		if(mode != Level_Editor){
		this.ReleaseX = me.getX();
		this.ReleaseY = me.getY();
		
		WasPressedX = PressedX;
		WasPressedY = PressedY;
		
		PressedX = 0;
		PressedY = 0;
		}
		
		if(mode == Level_Editor && LE != null){
			LE.mouseReleased(me);
		}
		
	}
	


	public void mouseDragged(MouseEvent arg0){
		
	}
	public void mouseMoved(MouseEvent arg0) {
		
		this.CurrentX = arg0.getX();
		this.CurrentY = arg0.getY();

		
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

		
		this.PressedX = arg0.getX();
		this.PressedY = arg0.getY();


	}
	//JFrame for Selecting Maze
		public class SelectWin extends JFrame{
			private JButton submit2;
			private JTextField Maze1;
			private JLabel SN;
			private JLabel wat;
			
			public SelectWin(){
				Container pane = this.getContentPane();
				BoxLayout layout = new BoxLayout(pane,BoxLayout.Y_AXIS);
				setLayout(layout);
				SN = new JLabel("Select a Maze: ");
				pane.add(SN);
				wat = new JLabel("");
				pane.add(wat);
				Maze1 = new JTextField(15);
				pane.add(Maze1);
				submit2 = new JButton("Submit");
				pane.add(submit2);
				
				Mazescont temp = Mazescont.read("Mazes.txt");
				String output = "<html>";
				for (int i = 0; i<temp.getArray().size();i++){
					output = output + temp.getMazes(i).getNaam()+ "<br>";
					if (i==temp.getArray().size()-1){
						output = output + "<html>";
					}
				}
				wat.setText(output);
				
				eventsub2 s1 = new eventsub2();
				submit2.addActionListener(s1);
			}
			public class eventsub2 implements ActionListener {
				public void actionPerformed(ActionEvent s1){
					Mazescont temp = Mazescont.read("Mazes.txt");
					String stemp = Maze1.getText();
					boolean status = false;
					for (int i = 0; i<temp.getArray().size(); i++){
						if (temp.getMazes(i).getNaam().equals(stemp)){
							Maze.setMaze(temp.getMazes(i));
							dispose();
							status = true;
							new MazeRunner();
							
						}
					}
					if (!status){
						Yousuck YS = new Yousuck();
						YS.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						YS.setSize(200,100);
						YS.setVisible(true);
						YS.setTitle("ERROR");
					}
					
					
				}
			}
		}
		public class Yousuck extends JFrame{
			private JLabel reply;
			
			public Yousuck(){
				setLayout(new FlowLayout());
				reply = new JLabel("Maze not in list");
				add(reply);
			}
		}
}