package movingobjects;

import java.util.ArrayList;

import javax.media.opengl.GL;

import Maze.Maze;

import com.sun.opengl.util.GLUT;


public class Projectile extends GameObject {

	private double horAngle;
	private double verAngle;
	private double speed;
	private double size;
	
	protected double dX;
	protected double dY;
	protected double dZ;
	protected boolean destroy = false;
	
	public Projectile(double x, double y, double z, double horAngle, double verAngle){
		super(x, y, z);	
		this.horAngle = horAngle;
		this.verAngle = verAngle;
		

		setLocationY(getLocationY() + 5  * Math.sin(Math.toRadians(verAngle)));
		
		
		this.speed = 0.1;
		this.size = 0.5;
		
		dX = Math.sin(Math.toRadians(horAngle));
		dY = Math.cos(Math.toRadians(horAngle));
		dZ = Math.sin(Math.toRadians(verAngle));
		
	}
	
	public void update(int deltaTime, Maze maze)
	{
		
		setLocationX(getLocationX() - speed * deltaTime * dX);
		setLocationZ(getLocationZ() - speed * deltaTime * dY);
		setLocationY(getLocationY() + speed * deltaTime * dZ);
		
		double x = locationX;
		double z = locationZ;
	
		if (maze.isWall(x+.5,z)||maze.isWall(x-.5,z)||maze.isWall(x,z+.5)||maze.isWall(x,z-.5)||
		maze.isWall(x+.5,z+.5)||maze.isWall(x+.5, z-.5)||maze.isWall(x-.5,z+.5)||maze.isWall(x-.5,z-.5))
		{
			destroy = true;
		
		}
		

	}
	

	public void display(GL gl) {
		// TODO Auto-generated method stub
		GLUT glut = new GLUT();
		
		float CubeColor[] = { 1f, 0.627f, 0f, 1f };
		gl.glMaterialfv( GL.GL_FRONT, GL.GL_DIFFUSE, CubeColor, 0);
		
		gl.glPushMatrix();
		gl.glTranslated(locationX,locationY,locationZ);
		gl.glRotated(horAngle -90, 0, 1, 0);
		gl.glTranslated(0, 0, size/2);
		glut.glutSolidCube( (float) size);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslated(locationX,locationY,locationZ);
		gl.glRotated(horAngle -90, 0, 1, 0);
		gl.glTranslated(0, 0, -size/2);
		glut.glutSolidCube( (float) size);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslated(locationX,locationY,locationZ);
		gl.glRotated(horAngle -90, 0, 1, 0);
		gl.glTranslated(0, size, size/2);
		glut.glutSolidCube( (float) size);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslated(locationX,locationY,locationZ);
		gl.glRotated(horAngle -90, 0, 1, 0);
		gl.glTranslated(0, size, -size/2);
		glut.glutSolidCube( (float) size);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslated(locationX,locationY,locationZ);
		gl.glRotated(horAngle -90, 0, 1, 0);
		gl.glTranslated(0,-size, size/2);
		glut.glutSolidCube( (float) size);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		gl.glTranslated(locationX,locationY,locationZ);
		gl.glRotated(horAngle -90, 0, 1, 0);
		gl.glTranslated(0, -size, -size/2);
		glut.glutSolidCube( (float) size);
		gl.glPopMatrix();
	}
	
	public double getHorAngle(){
		return horAngle;
	}
	public double getVerAngle(){
		return verAngle;
	}
	public void setHorAngle(double angle){
		this.horAngle = angle;
	}
	public void setVerAngle(double angle){
		this.verAngle = angle;
	}
	public boolean getDestroy() 
	{
		return this.destroy;
	}	
	public void setDestroy(boolean set) 
	{
		this.destroy = set;
	}


	
	
}