package object;

import javax.imageio.ImageIO;

public class EndTile extends SuperObject{
    
    public EndTile(){

        name = "EndTile";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Tiles/teleporter.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
