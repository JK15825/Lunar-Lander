import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
public class Game extends JPanel implements Runnable 
{
    private boolean gameStarted;
    private JFrame frame;
    private BufferedImage buffer;
    private boolean running = false;
    private Thread t;
    private boolean gameOver = false;
    private int frames;
    private int updates;
    
    private Lander lander;
    private Terrain luna;
    public Game()
    {
        this.setSize(960,720);
        
        frame = new JFrame("Game");
        
        frame.setLayout(new BorderLayout());
        frame.add(this,BorderLayout.CENTER);
        assignBindings();
        gameStarted = false;
        
        frames = 0;
        updates = 0;
        
        frame.setSize(960,720);
        
        this.setFocusable(true);       
        buffer = new BufferedImage(944,704,BufferedImage.TYPE_INT_ARGB);        
        
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        t = new Thread(this);
        
        Resources.loadResources();
        
        //System.out.println(Math.PI/8);
    }

    public static void main(String args[])
    {
        new Game();
        //System.out.println(System.getProperty("os.name"));
    }

    public void tick()
    {
        lander.update();
    }
    
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.white);
        g.fillRect(0,0,960,720);

        
        Graphics2D g2 = (Graphics2D)buffer.getGraphics();
        g2.clearRect(0,0,944,704);
        
        if(lander != null)
        {
            drawCharacters(g2, "FPS: " + frames, 0, 0, 6); 
            drawCharacters(g2, "TICKS: " + updates, 0, 8, 6);
            lander.draw(g2);
        }
        
        g.drawImage(buffer,8,8,null);
        g2.dispose();
        
    }
    
    public void run()
    {
        lander = new Lander(140,140,Math.PI/2);
        luna = new Terrain();

        buffer.createGraphics();

        running = true;
        long lastTime = System.nanoTime();
        double amountOfTicks = 60D;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1)
            {
                tick();
                updates++;
                if(System.getProperty("os.name").toLowerCase().contains("win"))
                {
                    paintComponent(this.getGraphics());
                    frames++;
                }
                delta--;
            }

            if(!System.getProperty("os.name").toLowerCase().contains("win"))
            {
                paintComponent(this.getGraphics());
                frames++;
            }

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer+=1000;
                //System.out.println("FPS: " + frames + " TICKS: " + updates);
                this.frames = frames;
                this.updates = updates;
                frames = 0;
                updates = 0;
            }
        }
        //paintComponent(this.getGraphics());
    }
    
    public void lose(Color player)
    {
        running = false;
        gameOver = true;
        System.out.println(player + " lost.");
    }
    
    private void toggleState()
    {
        //System.out.println("Green: " + cy1score + " Red: " + cy2score);
        if(running == false && gameOver == true)
        {
            t.stop();
            
            gameOver = false;
            t = new Thread(this);
            t.start();
        }
        else
            t.start();
            
        System.out.println("Game Started");
    }
    
    private void assignBindings()
    {
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "close");
        this.getActionMap().put("close", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        
        final Game g = this;
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"),"toggle");
        this.getActionMap().put("toggle", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                g.toggleState();
            }
        });
        
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"),"up");
        this.getActionMap().put("up", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                //System.out.println("PRESSED W");
                lander.toggleThrust(true);
            }
        });
        
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"),"left");
        this.getActionMap().put("left", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                //System.out.println("PRESSED A");
                lander.toggleRotateLeft(true);
            }
        });
        
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"),"right");
        this.getActionMap().put("right", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                //System.out.println("PRESSED D");
                lander.toggleRotateRight(true);
            }
        });
        
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released W"),"up2");
        this.getActionMap().put("up2", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                lander.toggleThrust(false);
            }
        });
        
        
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"),"left2");
        this.getActionMap().put("left2", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                lander.toggleRotateLeft(false);
            }
        });
        
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"),"right2");
        this.getActionMap().put("right2", new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                lander.toggleRotateRight(false);
            }
        });
        
    }
    
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
