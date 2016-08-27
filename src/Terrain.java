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

            Scanner in = new Scanner(new File("map_eventaully.dat"));
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
