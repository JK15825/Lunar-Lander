import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
public class Resources
{
    public static BufferedImage[] CHARACTERS;
    public static BufferedImage[] TERRAIN_ANGLES;
    public static BufferedImage ROCKET;
    public static BufferedImage ROCKET_FIRE;
    public static void loadResources()
    {
        try
        {
            BufferedImage temp = ImageIO.read(new File("res/alphabet.png"));
            
            CHARACTERS = new BufferedImage[41];
            int count = 0;
            for(int y = 0; y < 24; y +=8)
            {
                for(int x = 0; x < 96; x += 6)
                {
                    if(count < 41)
                    {
                        CHARACTERS[count] = temp.getSubimage(x,y,6,8);
                        count++;
                    }
                }
                //BufferedImage temp2 = temp;
            }
            
            temp = ImageIO.read(new File("res/terrain_angles.png"));
            
            TERRAIN_ANGLES = new BufferedImage[19];
            count = 0;
            for(int y = 0; y < 32; y +=16)
            {
                for(int x = 0; x < 256; x += 16)
                {
                    if(count < 19)
                    {
                        TERRAIN_ANGLES[count] = temp.getSubimage(x,y,6,8);
                        count++;
                    }
                }
                //BufferedImage temp2 = temp;
            }
            
            ROCKET = ImageIO.read(new File("res/rocket.png"));
            ROCKET_FIRE = ImageIO.read(new File("res/rocket_fire.png"));
            
        }
        catch(Exception e)
        {
            System.out.println("Are you missing an image?");
        }
    }
}
