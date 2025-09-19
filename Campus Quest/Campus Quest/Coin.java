import java.awt.*;

import javax.swing.ImageIcon;

import Music.Sound;

public class Coin extends GameObject{
    private int x, y;
    private static int score = 0;
    private boolean collected = false;
    private Image coinImage;
    private final int SIZE = 30;

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
        coinImage = new ImageIcon(getClass().getResource("/Images/Coin/Coin.png")).getImage();
    }

    // polymorphism
    @Override
    public void draw(Graphics g) {
        if (!collected) {
            g.drawImage(coinImage, x, y,SIZE,SIZE, null);

        }
    }
    public static int getScore(){
        return score;
    }

    public void checkCollision(Player player) {
        if (
    !collected &&
    (
        (player.getBounds("down").intersects(new Rectangle(x, y, SIZE, SIZE))) ||
        (player.getBounds("right").intersects(new Rectangle(x, y, SIZE, SIZE))) ||
        (player.getBounds("top").intersects(new Rectangle(x, y, SIZE, SIZE))) ||
        (player.getBounds("left").intersects(new Rectangle(x, y, SIZE, SIZE)))
    )
) {
            collected = true;
            Sound.play("/Music/collect.wav",false);
            score++;
        }
    }

    // polymorphism
    @Override
    public void update() {
        // No update logic
    }

}