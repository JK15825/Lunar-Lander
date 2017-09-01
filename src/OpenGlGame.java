import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

public class OpenGlGame extends GLCanvas implements GLEventListener
{
	private static final String TITLE = "Luna Lander";
	private static final int CANVAS_WIDTH = 960;
	private static final int CANVAS_HEIGHT = 720;
	private static final int FPS = 30;
	private GLU glu;
	private float zDepth;
	
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() 
			{
				GLCanvas canvas = new OpenGlGame();
				canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
				
				final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
				
				final JFrame frame = new JFrame();
				frame.getContentPane().add(canvas);
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e)
					{
						new Thread() {
		                     @Override
		                     public void run() {
		                        if (animator.isStarted()) 
		                        	animator.stop();
		                        System.exit(0);
		                     }
		                  }.start();
					}
				});
				
				frame.setTitle(TITLE);
				frame.pack();
				frame.setVisible(true);
				animator.start();
			}
			
		});
	}
	
	public OpenGlGame()
	{
		this.addGLEventListener(this);
	}

	@Override
	public void init(GLAutoDrawable drawable) 
	{
		GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
		gl.glClearColor(0f,0f,0f,0f);
		gl.glClearDepth(10f);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		gl.glShadeModel(GL2.GL_SMOOTH);
		
	}
	
	@Override
	public void display(GLAutoDrawable drawable) 
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		gl.glTranslated(0f, 0f,-6.0f);
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glVertex3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 0.0f);
        gl.glEnd();
	}
	
	@Override
	public void dispose(GLAutoDrawable arg0) 
	{
	
	}
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	{
		GL2 gl = drawable.getGL().getGL2();
		if(height == 0)
			height = 1;
		float aspect = (float)width/height;
		gl.glViewport(0,0,width,height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0, aspect, .1, 100.0);
		
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
	}

}
