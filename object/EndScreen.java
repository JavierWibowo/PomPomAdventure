package object;

import javax.imageio.ImageIO;

public class EndScreen extends SuperObject{
    
    public EndScreen(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Screens/game_over_screen.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
