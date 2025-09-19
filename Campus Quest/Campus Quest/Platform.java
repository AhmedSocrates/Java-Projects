import java.awt.*;

import javax.swing.ImageIcon;

public class Platform extends GameObject{
    private int x, y, width, height;
    private boolean visible = true; // default to visible
    private Image platformImage;

    // NEW MOVEMENT PROPERTIES ----- LATEST UPDATE BY Anjum
    // Change in x-coordinate per update (speed and direction)
    private int dx; 
    // To remember starting position for bounds
    private int originalX; 
    // Movement boundaries for oscillating platforms
    private int minX, maxX; 
    // Flag to indicate if the platform should move
    private boolean isMoving; 

    public Platform(int x, int y, int width, int height, boolean visible) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = visible;
        platformImage = new ImageIcon(getClass().getResource("/Images/Platform/platform.jpg")).getImage();
    }

    // Constructor to make moving platform - update by anjum
    public Platform(int x, int y, int width, int height, boolean visible, boolean isMoving, int moveSpeed, int moveRange) { // New constructor parameters
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = visible;
        platformImage = new ImageIcon(getClass().getResource("/Images/Platform/platform.jpg")).getImage();

        this.isMoving = isMoving;
        if (isMoving) {
            this.originalX = x;
            this.dx = moveSpeed; // Initial direction and speed
            this.minX = x;
            this.maxX = x + moveRange + width; // Moves 'moveRange' pixels to the right then back
        }

    }
    // polymorphism
    @Override
    public void draw(Graphics g) {
        if (!visible) return;
        g.drawImage(platformImage, x, y,width,height, null);
    }

     // polymorphism 
    @Override
    public void update() {
        if (isMoving) {
            x += dx; 

            if (dx > 0 && x >= maxX - width) {
                dx = -dx;
                x = maxX - width;
            } else if (dx < 0 && x <= minX) {
                dx = -dx;
                x = minX;
            }
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public boolean checkCollision(Player player) {
        Rectangle playerBounds = player.getBounds("top");
        Rectangle platformBounds = getBounds();
        if (playerBounds.intersects(platformBounds) && player.isJumping()) {
        player.hitHead(platformBounds.y + platformBounds.height);
        return true;
    }

        return playerBounds.intersects(platformBounds);
    }

    public boolean isMoving() {
        return isMoving;
    }
    public int getDx() {
        return dx;
    }
}