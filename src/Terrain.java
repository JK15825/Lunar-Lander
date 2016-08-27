import java.awt.*;
import java.io.*;
import java.util.*;
public class Terrain
{
    private ArrayList<Point> landscape;
    public Terrain()
    {
        landscape = new ArrayList<Point>();
        try
        {
            int x = 0;
            int y = 0;
            
            int columnCounter = 0;
            Scanner in = new Scanner(new File("map_eventaully.dat"));
            int type = 0;
            while((type = in.nextInt()) != null)
            {
                if(type != 0)
                {
                    if(columnCounter = 60)
                    {
                        y+=16;
                        
                    }
                    landscape.add(new Point(0,0,type));
                    x+=16;
                    columnCounter++;
                }
            }
            
        }
        catch(Exception e)
        {
            
        }
    }
    
    public Point[] getTerrain()
    {
        return null;
    }
    
    public void draw(Graphics2D g)
    {
        
    }
    
}
