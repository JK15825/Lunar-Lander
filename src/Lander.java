import java.awt.*;
import java.awt.geom.AffineTransform;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Lander
{
    private final double SHIP_ACCEL = .3;
    private double vertVelocity;
    private double horzVelocity;
    private float dir;
    private int x;
    private int y;
    
    private boolean thrust;
    private boolean rotateLeft;
    private boolean rotateRight;
    
    public Lander(int x, int y, float dir)
    {
        this.x = x;
        this.y = y;
        this.dir = dir;
        
        vertVelocity = 0;
        horzVelocity = 0;
    }
    
    public void update()
    {
        if(rotateLeft)
        {
            dir += Math.PI/64;
            if(dir > Math.PI)
                dir = (float)Math.PI;
        }
        
        if(rotateRight)
        {
            dir -= Math.PI/64;
            if(dir < 0)
                dir = 0;
        }
            
        if(thrust)
        {
            vertVelocity -= Math.sin(dir) * SHIP_ACCEL;
            horzVelocity += Math.cos(dir) * SHIP_ACCEL;
        }
        vertVelocity += .05;
            
        x += horzVelocity;
        y += vertVelocity;
    }
    
    public void draw(GLAutoDrawable drawable)
    {
    	GL2 gl = drawable.getGL().getGL2();
    	
    	gl.glPushMatrix();
    	gl.glLoadIdentity(); 
    	
    	//gl.glRotatef(dir, 1, 0, 0);
    	//gl.glRotatef(dir, 0, 1, 0);

    	gl.glTranslatef(x, -y, 0);
    	
    	
    	//System.out.printf("Rotation: %.2f\n",dir);
    	
    	gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(1.0f, 0.0f, .0f);   // Red
        gl.glVertex2f(-20f, -20f);
        gl.glColor3f(0.0f, 1.0f, 0.0f);   // Green
        gl.glVertex2f(0.0f, 20f);
        gl.glColor3f(0.0f, 0.0f, 1.0f);   // Blue
        gl.glVertex2f(20f, -20f);
        
        gl.glPopMatrix();      
        
        /*drawCharacters(g, "HORZ: " + horzVelocity, 0, 16, 6);
        drawCharacters(g, "VERT: " + vertVelocity, 0, 24, 6);
        if(dir != 0)
        {
            drawCharacters(g, "DIR: " + (dir + "").substring(0,4), 0, 32, 6);
        }
        else
        {
            drawCharacters(g, "DIR: 0", 0, 32, 6);
        }
        //
        
        drawCharacters(g, "X: " + x, 0, 40, 6);
        drawCharacters(g, "Y: " + y, 0, 48, 6);
        
        
        
        AffineTransform old = g.getTransform();
        g.setColor(Color.WHITE);
        g.translate(x,y);
        g.rotate(-(dir - Math.PI/2));
        if(thrust)
        {
            g.drawImage(Resources.ROCKET_FIRE,-8,-8,null);
        }
        else
            g.drawImage(Resources.ROCKET,-8,-8,null);
        
        //g.drawRect(-8,-8,16,16);
        g.setTransform(old);
        //g.translate(x,y);*/
    }
    
    public void toggleThrust(boolean t)
    {
        thrust = t;
    }
    
    public void toggleRotateLeft(boolean l)
    {
        rotateLeft = l;
    }
    
    public void toggleRotateRight(boolean r)
    {
        rotateRight = r;
    }
    
    
    
    // oh my god this is ugly. it works tho!
    public void drawCharacters(Graphics2D g2, String toWrite, int x, int y, int increment)
    {
        toWrite = toWrite.toLowerCase();
        for(char character: toWrite.toCharArray())
        {
             switch(character)
             {
                 case 'a': g2.drawImage(Resources.CHARACTERS[0],x,y,null); x +=6; break;
                 case 'b': g2.drawImage(Resources.CHARACTERS[1],x,y,null); x +=6; break;
                 case 'c': g2.drawImage(Resources.CHARACTERS[2],x,y,null); x +=6; break;
                 case 'd': g2.drawImage(Resources.CHARACTERS[3],x,y,null); x +=6; break;
                 case 'e': g2.drawImage(Resources.CHARACTERS[4],x,y,null); x +=6; break;
                 case 'f': g2.drawImage(Resources.CHARACTERS[5],x,y,null); x +=6; break;
                 case 'g': g2.drawImage(Resources.CHARACTERS[6],x,y,null); x +=6; break;
                 case 'h': g2.drawImage(Resources.CHARACTERS[7],x,y,null); x +=6; break;
                 case 'i': g2.drawImage(Resources.CHARACTERS[8],x,y,null); x +=6; break;
                 case 'j': g2.drawImage(Resources.CHARACTERS[9],x,y,null); x +=6; break;
                 case 'k': g2.drawImage(Resources.CHARACTERS[10],x,y,null); x +=6; break;
                 case 'l': g2.drawImage(Resources.CHARACTERS[11],x,y,null); x +=6; break;
                 case 'm': g2.drawImage(Resources.CHARACTERS[12],x,y,null); x +=6; break;
                 case 'n': g2.drawImage(Resources.CHARACTERS[13],x,y,null); x +=6; break;
                 case 'o': g2.drawImage(Resources.CHARACTERS[14],x,y,null); x +=6; break;
                 case 'p': g2.drawImage(Resources.CHARACTERS[15],x,y,null); x +=6; break;
                 case 'q': g2.drawImage(Resources.CHARACTERS[16],x,y,null); x +=6; break;
                 case 'r': g2.drawImage(Resources.CHARACTERS[17],x,y,null); x +=6; break;
                 case 's': g2.drawImage(Resources.CHARACTERS[18],x,y,null); x +=6; break;
                 case 't': g2.drawImage(Resources.CHARACTERS[19],x,y,null); x +=6; break;
                 case 'u': g2.drawImage(Resources.CHARACTERS[20],x,y,null); x +=6; break;
                 case 'v': g2.drawImage(Resources.CHARACTERS[21],x,y,null); x +=6; break;
                 case 'w': g2.drawImage(Resources.CHARACTERS[22],x,y,null); x +=6; break;
                 case 'x': g2.drawImage(Resources.CHARACTERS[23],x,y,null); x +=6; break;
                 case 'y': g2.drawImage(Resources.CHARACTERS[24],x,y,null); x +=6; break;
                 case 'z': g2.drawImage(Resources.CHARACTERS[25],x,y,null); x +=6; break;
                 case '.': g2.drawImage(Resources.CHARACTERS[38],x,y,null); x +=6; break;
                 case ' ': g2.drawImage(Resources.CHARACTERS[39],x,y,null); x +=6; break;
                 case ':': g2.drawImage(Resources.CHARACTERS[36],x,y,null); x +=6; break;
                 case '0': g2.drawImage(Resources.CHARACTERS[26],x,y,null); x +=6; break;
                 case '1': g2.drawImage(Resources.CHARACTERS[27],x,y,null); x +=6; break;
                 case '2': g2.drawImage(Resources.CHARACTERS[28],x,y,null); x +=6; break;
                 case '3': g2.drawImage(Resources.CHARACTERS[29],x,y,null); x +=6; break;
                 case '4': g2.drawImage(Resources.CHARACTERS[30],x,y,null); x +=6; break;
                 case '5': g2.drawImage(Resources.CHARACTERS[31],x,y,null); x +=6; break;
                 case '6': g2.drawImage(Resources.CHARACTERS[32],x,y,null); x +=6; break;
                 case '7': g2.drawImage(Resources.CHARACTERS[33],x,y,null); x +=6; break;
                 case '8': g2.drawImage(Resources.CHARACTERS[34],x,y,null); x +=6; break;
                 case '9': g2.drawImage(Resources.CHARACTERS[35],x,y,null); x +=6; break;
                 case '-': g2.drawImage(Resources.CHARACTERS[37],x,y,null); x +=6; break;
             }
        }
    }
}
