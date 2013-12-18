package Player;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.*;

import javax.media.opengl.GLCanvas;



/**
 * The UserInput class is an extension of the Control class. It also implements three 
 * interfaces, each providing handler methods for the different kinds of user input.
 * <p>
 * For making the assignment, only some of these handler methods are needed for the 
 * desired functionality. The rest can effectively be left empty (i.e. the methods 
 * under 'Unused event handlers').  
 * <p>
 * Note: because of how java is designed, it is not possible for the game window to
 * react to user input if it does not have focus. The user must first click the window 
 * (or alt-tab or something) before further events, such as keyboard presses, will 
 * function.
 * 
 * @author Mattijs Driel
 *
 */
public class UserInput extends Control 
		implements MouseListener, MouseMotionListener, KeyListener
{
	public int xd, yd, xp, yp;
	public boolean view_right, view_left;
	public double boundx = 0;
	public double boundy = 0;
	public int screenWidth;
	public int screenHeight;
	public boolean schiet = false;
	public boolean geschoten = false;
	private boolean hpchanged = false;

	public int thisX = 0;
	public int thisY = 0;



	// TODO: Add fields to help calculate mouse movement
	
	/**
	 * UserInput constructor.
	 * <p>
	 * To make the new UserInput instance able to receive input, listeners 
	 * need to be added to a GLCanvas.
	 * 
	 * @param canvas The GLCanvas to which to add the listeners.
	 */
	public UserInput(GLCanvas canvas)
	{
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addKeyListener(this);
	}
	
	/*
	 * **********************************************
	 * *				Updating					*
	 * **********************************************
	 */

	@Override
	public void update()
	{
		xp = screenWidth/2;
		yp = screenHeight/2;
		
		if(xd-xp != 0)
		{
			
			dX = -2*(xp-xd);
	
		}
		else
			if(view_left == true)
				if(view_right == true)
					dX = 0;
				else
					dX = -1.5;
			else
				if(view_right == true)
					dX = 1.5;
				else
					dX = 0;
	
		dY = -2*(yp-yd);

		// TODO: Set dX and dY to values corresponding to mouse movement
		
		
		mouseReset();


	}

	/*
	 * **********************************************
	 * *		Input event handlers				*
	 * **********************************************
	 */

	@Override
	public void mousePressed(MouseEvent event)
	{

		if(pauze){
			this.PressedX = event.getX();
			this.PressedY = event.getY();
		}else if(!geschoten){
			schiet = true;
			
		}
		
		geschoten = true;
	
	}

	@Override
	public void mouseDragged(MouseEvent event)
	{	
		if(!pauze){
			xd = event.getX();
			yd = event.getY();
		}
		// TODO: Detect mouse movement while the mouse button is down
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		
//		System.out.println(event.getExtendedKeyCode());
		
		switch(event.getExtendedKeyCode()){
		case 87: forward = true;	// 'w'
		break;
		case 65: left = true;		// 'a'
		break;
		case 83: back = true;		// 's'
		break;
		case 68: right = true;		// 'd'
		break;
		case 69: view_right = true;	// 'e'
		break;
		case 81: view_left = true;	// 'q'
		break;
		case 32: space = true;    // 'space;
		break;
		case 27: SwitchPauze();	  // 'escape'
		break;
		case 67: up = true;		  // 'c'
		break;
		case 86: down = true;	  // 'v'
		break;
		case 70: 				  // 'f'
			if(!hpchanged){
				hpdown = true;
				hpchanged = true;
			}
		break;
		case 71:   				  // 'g' 
			if(!hpchanged){
				hpup = true;
				hpchanged = true;
			}
		break;
		case 66:				  // 'b'
			// not programmed
			break;
		case 16:				  // 'shift'
			sprint = true;
			break;
			
		
		default: break;
		}
		
	
		
	

		// TODO: Set forward, back, left and right to corresponding key presses
	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		switch(event.getExtendedKeyCode()){
		case 87: forward = false;	// 'w'
		break;
		case 65: left = false;		// 'a'
		break;
		case 83: back = false;		// 's'
		break;
		case 68: right = false;			// 'd'
		break;
		case 69: view_right = false;	// 'e'
		break;
		case 81: view_left = false;		// 'q'
		break;
		case 32: space = false;    // 'space; 
		break;
		case 67: up = false;	  // 'c'
		break;
		case 86: down = false;	  // 'v'
		break;
		case 70: hpchanged = false; // 'f'
		break;
		case 71: hpchanged = false; // 'g'
		break;
		case 66:				  // 'b'
			// not programmed
			break;
		case 16:				  // 'shift'
			sprint = false;
			break;
			
		default: break;
		}
		

	}

	/*
	 * **********************************************
	 * *		Unused event handlers				*
	 * **********************************************
	 */
	
	@Override
	public void mouseMoved(MouseEvent event)
	{
		
		
		CurrentX = event.getX();
		CurrentY = event.getY();
		
	if(!pauze){
		xd = event.getX();
		yd = event.getY();
	}
		
		
		
		

	}

	@Override
	public void keyTyped(KeyEvent event)
	{
	}

	@Override
	public void mouseClicked(MouseEvent event)
	{
	}

	@Override
	public void mouseEntered(MouseEvent event)
	{
	}

	@Override
	public void mouseExited(MouseEvent event)
	{
	}

	@Override
	public void mouseReleased(MouseEvent event)
	{
		
		
		ReleaseX = event.getX();
		ReleaseY = event.getY();
		
		WasPressedX = PressedX;
		WasPressedY = PressedY;
		
		PressedX = 0;
		PressedY = 0;
		
		geschoten = false;
	}
	
	public void mouseOutBorder(int screenWidth,int screenHeight){
	
		int border = 50;
	        
		if(CurrentX > (screenWidth - border) || CurrentX < (border) || CurrentY > (screenHeight - border) || CurrentY < (border)){
			mouseReset();
		}
	}
	
	public void mouseReset(){
		
	
		
		  try {
    			Robot robot = new Robot();

    			robot.mouseMove(screenWidth/2 + (int)boundx/2 + thisX, screenHeight/2 + (int)(boundy-boundx/2) + thisY);

   	
    		} catch (AWTException e) {
    		
    			e.printStackTrace();
    		}
		  

	}
	



}
