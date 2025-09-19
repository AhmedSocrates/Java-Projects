import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import Music.Sound;

public class Player {
    // 17 vars
    private int x, y;
    private int previousY;
    private int width = 60, height = 80;
    private int velX = 0, velY = 0;
    private final int SPEED = 5;
    private final int GRAVITY = 1;
    private final int JUMP_STRENGTH = -15;
    private boolean onGround = false;

    
    private Image[] rightFrames;
    private Image[] leftFrames;
    private Image jumpingImg;
    private Image standingImg;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private final int FRAME_DELAY = 100; 

    public Player(int x, int y) {
        this.x = x;
        this.y = y;

        rightFrames = new Image[] {
            new ImageIcon(getClass().getResource("/Images/Player/Student.png")).getImage(),
            new ImageIcon(getClass().getResource("/Images/Player/Student2.png")).getImage()
        };

        leftFrames = new Image[] {
            new ImageIcon(getClass().getResource("/Images/Player/StudentLeft.png")).getImage(),
            new ImageIcon(getClass().getResource("/Images/Player/Student2Left.png")).getImage()
        };

        jumpingImg = new ImageIcon(getClass().getResource("/Images/Player/Jumping.png")).getImage();
        standingImg = new ImageIcon(getClass().getResource("/Images/Player/playerStanding.png")).getImage(); 
    }

    public void move(boolean left, boolean right, boolean jumping, ArrayList<Platform> platforms) {
        previousY = y;
        if (left) {
            velX = -SPEED;
        }
        else if (right) {
            velX = SPEED;
        }
        else velX = 0;

        if (jumping && onGround) {
            velY = JUMP_STRENGTH;
            onGround = false;
        }

        if (left || right) {updateAnimationFrame();}
        else {currentFrame = 0;}

        velY += GRAVITY;
        x += velX;
        y += velY;

        onGround = false;
    for (Platform p : platforms) {
        Rectangle playerBounds = new Rectangle(x, y, width, height);
        Rectangle platformBounds = p.getBounds();

        // Landing on platform (from above)
        if (velY > 0 &&
            previousY + height <= p.getY() &&
            playerBounds.intersects(platformBounds)) {
            
            y = p.getY() - height;
            velY = 0;
            onGround = true;
        }

        // Hitting head on platform (from below)
        else if (velY < 0 &&
                previousY >= p.getY() + p.getHeight() &&
                playerBounds.intersects(platformBounds)) {

            y = p.getY() + p.getHeight();
            velY = 0;
        }
        }

        if (y >= 500) {
            y = 500;
            velY = 0;
            onGround = true;
        }
    }

    private void updateAnimationFrame() {
        long now = System.currentTimeMillis();
        if (now - lastFrameTime > FRAME_DELAY) {
            currentFrame = (currentFrame + 1) % rightFrames.length;
            lastFrameTime = now;
        }
    }

    public void setX(int newX) { x = newX; }
    public void setY(int newY) { y = newY; }
    public void setVelY(int newVelY) { velY = newVelY; }
    public int getX() { return x; }
    public void bounce() { y -= 15; }
    public boolean isJumping() { return velY < 0; }

    public void hitHead(int platformBottomY) {
        y = platformBottomY;
        velY = 0;
    }

    public void draw(Graphics g, boolean right, boolean left, boolean jumping) {
        if (jumping) {
            g.drawImage(jumpingImg, x, y, width, height, null);
        } else if (left) {
            g.drawImage(rightFrames[currentFrame], x, y, width, height, null);
        } else if (right) {
            g.drawImage(leftFrames[currentFrame], x, y, width, height, null);
        } else {
            g.drawImage(standingImg, x, y, width, height, null); // idle default
        }
    }

    public Rectangle getBounds(String orientation) {
        switch (orientation) {
            case "down":
                return new Rectangle(x + 12, y + height - 4, width - 24, 4);
            case "top":
                return new Rectangle(x + 12, y, width - 20, 5);
            case "right":
                return new Rectangle(x + width - 10, y + 12, 4, height - 24);
            case "left":
                return new Rectangle(x+6, y + 12, 6, height - 24);
            case "all":
                return new Rectangle(x, y, width, height);
            default:
                return null;
        }
    }
}

