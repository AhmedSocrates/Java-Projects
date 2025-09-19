import java.awt.*;

import javax.swing.ImageIcon;

import Music.Sound;

public class Enemy extends GameObject{
    private int x, y;
    private int width = 60, height = 80;
    private int direction = 1;
    private final int SPEED = 2;
    private int patrolDistance = 100;
    private int startX;
    private Image EnemyImg1,EnemyImg1Left;
    private boolean dead;
    private static double gpa = 0;

    // scrolling enemy attributes
    private boolean isScrollingEnemy = false;
    private int moveSpeed = 2; // Default speed for regular enemies
    private int levelRightEdge;
    private int levelLeftEdge;
    private int delayTimer = 0;
    private int activationDelay = 0;
    private boolean activated = true; // Regular enemies start activated

    // we have 3 constructors
    // first one is the default constructor
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.startX = x;

        EnemyImg1 = new ImageIcon(getClass().getResource("/Images/Enemy/Enemy1.png")).getImage();
        EnemyImg1Left = new ImageIcon(getClass().getResource("/Images/Enemy/Enemy1Left.png")).getImage();
        
    }

    // second constructor to change the enemy, bus, quiz...etc
    public Enemy(int x, int y, String imgPathRight, String imgPathLeft) {
        this.x = x;
        this.y = y;
        this.startX = x;

        // check if the enemy is not only going in one direction, like my bus enemy, if only going one way, add null to the imgPath when calling the constructor for the direction it is not going 
        if (imgPathRight != null) {
        EnemyImg1 = new ImageIcon(getClass().getResource(imgPathRight)).getImage();
        }
        if (imgPathLeft != null) {
        EnemyImg1Left = new ImageIcon(getClass().getResource(imgPathLeft)).getImage();
        }
    }

    // third constructor to handle scrolling enemies
    public Enemy(int x, int y, String imgPathRight, String imgPathLeft, int speed, int delayFrames, int leftBoundary) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.moveSpeed = speed;
        this.levelLeftEdge = leftBoundary;
        this.levelRightEdge = x; // Start position becomes the right edge
        this.isScrollingEnemy = true;
        this.activationDelay = delayFrames;
        this.activated = delayFrames == 0;

        // Load images
        if (imgPathRight != null) {
            EnemyImg1 = new ImageIcon(getClass().getResource(imgPathRight)).getImage();
        }
        if (imgPathLeft != null) {
            EnemyImg1Left = new ImageIcon(getClass().getResource(imgPathLeft)).getImage();
        }
    }
    
    // added enemy scrolling logic to update method
    @Override
    public void update() {
        if (isScrollingEnemy) {
            if (!activated) {
                delayTimer++;
                if (delayTimer >= activationDelay) {
                    activated = true;
                }
                return;
            }
            
            x -= moveSpeed; // move left
            
            if (x + width < levelLeftEdge) {
                x = levelRightEdge;
            }
            direction = -1; // face left
        } else {
            
            x += direction * SPEED;
            if (x > startX + patrolDistance || x < startX - patrolDistance) {
                direction *= -1;
            }
        }
    }

    public boolean checkCollision(Player player) {
        if (dead) return false;
        
        // For scrolling enemies, don't collide when not activated
        if (isScrollingEnemy && !activated) return false;

        Rectangle playerBottom = player.getBounds("down");
        Rectangle playerRight = player.getBounds("right");
        Rectangle playerLeft = player.getBounds("left");

        // Player kill the enemy
        if (playerBottom.intersects(getBounds("top"))) {
            dead = true;
            player.bounce();  // Player bounces up
            gpa+=0.16; //add gpa
            return false;     // Don't kill the player
        }

        if (
            playerBottom.intersects(getBounds("right")) ||
            playerBottom.intersects(getBounds("left")) ||
            playerBottom.intersects(getBounds("down")) ||
            playerLeft.intersects(getBounds("right")) ||
            playerLeft.intersects(getBounds("left")) ||
            playerLeft.intersects(getBounds("down")) ||
            playerRight.intersects(getBounds("right")) ||
            playerRight.intersects(getBounds("left")) ||
            playerRight.intersects(getBounds("down"))
        ) {
            return true; // Player loses
        }

        return false;
    }
    
    public static double getGpa(){
        return Math.min(gpa, 4.0);
    }

    // polymorphism
    @Override
    public void draw(Graphics g) {
        // Don't draw dead enemies or non-activated scrolling enemies
        if (dead || (isScrollingEnemy && !activated)) return;

        // if the enemy is facing right direction, draw the image of it to the right
        if (direction == 1 && EnemyImg1 != null) {
            g.drawImage(EnemyImg1, x, y, width, height, null);
        } 
        // if the enemy is facing the left direction, draw the image of it to the left
        else if (direction == -1 && EnemyImg1Left != null) {
            g.drawImage(EnemyImg1Left, x, y, width, height, null);
        } 
        else if (EnemyImg1 != null) {
            g.drawImage(EnemyImg1, x, y, width, height, null);
        }
    }

    public Rectangle getBounds(String orientation) {
        if (orientation.equals("down")){
            return new Rectangle(x + width/4, y + height/2, width/2, height/2);
        }
        else if (orientation.equals("top")){
            return new Rectangle(x,y,width,height/4); 
        }
        else if (orientation.equals("right")){
            return new Rectangle(x+width-5,y+5,5,height-10 );
        }
        else if (orientation.equals("left")){
            return new Rectangle(x,y+5,5,height-10 );
        } // MATHABA MOD3 END
        else{
            return null;
        }
    }
}

