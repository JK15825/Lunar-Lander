import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Game 
{
	private static final String TITLE = "Luna Lander";
	private static final int CANVAS_WIDTH = 960;
	private static final int CANVAS_HEIGHT = 720;
	private static final int FPS = 30;
	
	public Game()
	{
		GLCanvas canvas = new GameCanvas();
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
	
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() 
			{
				new Game();
			}
			
		});
	}
	
	

}
