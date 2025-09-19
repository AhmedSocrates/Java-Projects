import java.awt.Graphics;

// For Polymorphism, classes that extends it:
// 1. Platform.java
// 2. Obstacle.java
// 3. Enemy.java
// 4. Coin.java
// it lets those classes override the methods draw (for all) and update (for classes that has update/move method or just an empty method if not)
public abstract class GameObject {
    // how each object draws itself, so like if there is a scpecific logic before drawing an object maybe, like enemy for example
    public abstract void draw(Graphics g);

    // how each object updates itself every frame (Obstacle only has this method for now)
    public abstract void update();
}
