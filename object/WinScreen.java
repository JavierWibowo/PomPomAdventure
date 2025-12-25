package object;

import javax.imageio.ImageIO;

public class WinScreen extends SuperObject{
    
    public WinScreen(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Screens/game_win_screen.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
