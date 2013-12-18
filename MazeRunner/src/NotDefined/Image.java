package NotDefined;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;


public class Image 
{
	public static byte[] loadImage(String filename){
		BufferedImage image = null;
		byte[] imgRGBA = null;
		
		try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) 
        {
        	System.err.println(e);
        }
        
        
        WritableRaster raster = Raster.createInterleavedRaster( DataBuffer.TYPE_BYTE, image.getWidth(),
        						image.getHeight(), 4, null );
        ComponentColorModel colorModel =
        		new ComponentColorModel( ColorSpace.getInstance(ColorSpace.CS_sRGB),
        					 new int[] {8,8,8,8},
        					 true,
        					 false,
        					 ComponentColorModel.TRANSLUCENT,
        					 DataBuffer.TYPE_BYTE );
        BufferedImage bufImg =
        		new BufferedImage (colorModel, // color model
        				   raster,
        				   false, // isRasterPremultiplied
        				   null); // properties
        Graphics2D g = bufImg.createGraphics();
        AffineTransform gt = new AffineTransform();
        gt.translate (0, image.getHeight());
        gt.scale (1, -1d);
        g.transform ( gt );
        g.drawImage ( image, null, null );
        // Retrieve underlying byte array (imgBuf) 
        // from bufImg.
        DataBufferByte imgBuf =  (DataBufferByte)raster.getDataBuffer();
        imgRGBA = imgBuf.getData();
        g.dispose();
        return imgRGBA;
	}
	
	public static void drawImage(GL gl, int posx, int posy, int width,int height, byte[] img){
		gl.glRasterPos2i(posx, posy );
    	gl.glDrawPixels(width, height, 
    			 GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, 
    			 ByteBuffer.wrap(img));

		
	}
	
}
