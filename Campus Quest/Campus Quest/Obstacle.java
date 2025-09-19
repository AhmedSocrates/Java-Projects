import java.awt.*;
import javax.swing.ImageIcon;
import Music.Sound;

public class Obstacle extends GameObject{
    private int x,y; 
    private int width = 50, height = 100; 
    private boolean visible = true; 
    private int timer = 0;
    private Image obstacleImg;

    public Obstacle(int x,int y){
        this.x = x; 
        this.y = y;
        obstacleImg = new ImageIcon(getClass().getResource("/Images/Enemy/Enemy2.png")).getImage(); 
    }

    // polymorphism
    @Override
    public void update(){
        timer++; 
        if (timer % 100 ==0 ){
            visible = !visible; 
        }
    }

    // polymorphism
    @Override
    public void draw(Graphics g){
        if (visible){
            g.drawImage(obstacleImg, x, y,width,height, null);
        }
    }
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }

    public boolean checkCollision(Player p){
        if((p.getBounds("all").intersects(getBounds())) && visible ){
            return true;
        }
        else{
            return false;
        }
    }  
}
