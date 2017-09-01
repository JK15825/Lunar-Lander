import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

public class GameCanvas extends GLCanvas implements GLEventListener
{
	private GLU glu;
	private float angle = .1f;
	public GameCanvas()
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
		render(drawable);
		update();
	}
	
	private void render(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		float sin = (float)Math.sin(angle);
	    float cos = (float)Math.cos(angle);
	    gl.glBegin(GL2.GL_TRIANGLES);
        gl.glColor3f(1.0f, 0.0f, 0.0f);   // Red
        gl.glVertex2d(-cos, -cos);
        gl.glColor3f(0.0f, 1.0f, 0.0f);   // Green
        gl.glVertex2d(0.0f, cos);
        gl.glColor3f(0.0f, 0.0f, 1.0f);   // Blue
        gl.glVertex2d(sin, -sin);
        gl.glEnd();
	}
	
	private void update()
	{
		angle += .1;
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
