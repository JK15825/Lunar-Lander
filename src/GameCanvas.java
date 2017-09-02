import com.jogamp.opengl.GL2;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

public class GameCanvas implements GLEventListener
{
	private GLU glu = new GLU();
	private static final String TITLE = "Luna Lander";
	private static final int CANVAS_WIDTH = 960;
	private static final int CANVAS_HEIGHT = 720;
	private static final int FPS = 30;
	private Lander lander;
	
	public GameCanvas()
	{
		
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
	    GLCapabilities caps = new GLCapabilities(profile);
	     
	    caps.setDepthBits(16);
	    
		GLCanvas canvas = new GLCanvas(caps);
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
		bindKeys(frame);
		lander = new Lander(1,1,0);
		
		canvas.addGLEventListener(this);
		
		frame.setTitle(TITLE);
		frame.pack();
		frame.setVisible(true);
		animator.start();
	}
	
	private void bindKeys(JFrame frame)
	{
		JRootPane root = frame.getRootPane();
		root.getInputMap(JRootPane.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed W"), "pressedUp");
		root.getInputMap(JRootPane.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"), "releasedUp");
		
		root.getActionMap().put("pressedUp", new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				lander.toggleThrust(true);
				//System.out.println("Pressed Up");
			}
		});
		root.getActionMap().put("releasedUp", new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				lander.toggleThrust(false);
				//System.out.println("Released Up");
			}
		});
		
		root.getInputMap(JRootPane.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed A"), "pressedLeft");
		root.getInputMap(JRootPane.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), "releasedLeft");
		
		root.getActionMap().put("pressedLeft", new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				lander.toggleRotateLeft(true);
				//System.out.println("Pressed Up");
			}
		});
		root.getActionMap().put("releasedLeft", new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				lander.toggleRotateLeft(false);
				//System.out.println("Released Up");
			}
		});
		
		root.getInputMap(JRootPane.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("pressed D"), "pressedRight");
		root.getInputMap(JRootPane.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), "releasedRight");
		
		root.getActionMap().put("pressedRight", new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				lander.toggleRotateRight(true);
				//System.out.println("Pressed Up");
			}
		});
		root.getActionMap().put("releasedRight", new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				lander.toggleRotateRight(false);
				//System.out.println("Released Up");
			}
		});
	}

	@Override
	public void init(GLAutoDrawable drawable) 
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0f,0f,0f,0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		
		//gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		//gl.glShadeModel(GL2.GL_SMOOTH);
		
	}
	
	@Override
	public void display(GLAutoDrawable drawable) 
	{
		render(drawable);
		update();
	}
	
	private void render(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		lander.draw(drawable);
		
		gl.glEnd();
	}
	
	private void update()
	{
		lander.update();
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
		//glu.gluPerspective(45.0, aspect, 1, 20.0);
		
		gl.glOrtho(-width/2, width/2, -height/2, height/2, -1, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		//System.out.printf("Why am I in here %.2f",aspect);
	}
	
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() 
			{
				new GameCanvas();
			}
			
		});
	}
}
