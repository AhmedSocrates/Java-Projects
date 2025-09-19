import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Music.Sound;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private final int DELAY = 16;
    private final int GAME_TIME_LIMIT = 300;
    private int timeLeft = GAME_TIME_LIMIT;
    private long lastTimerCheck = System.currentTimeMillis();

    private Player player;
    private boolean left, right, jumping;

    private Level level1,level2,level3,level4;

    // for polymorphism
    // we will put all objects in one list and loop and use instanceof whenever we need a specific object for sth
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private int cameraX = 0;
    private int playerLives = 5;
    private boolean gameOver = false;
    private Image[] instructions;
    private boolean showInstruction = false;
    private Image currentInstruction = null;
    private long lastInstructionTime = 0;
    private long instructionStartTime;
    private int currentLevelStartX = 0;
    private boolean playerWon = false;
    

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(200, 255, 200));
        setFocusable(true);
        addKeyListener(this);

        player = new Player(100, 300);
        timer = new Timer(DELAY, this);

        level1 = new Level(1);
        level2 = new Level(2); // Load level 2
        level3 = new Level(3);
        level4 = new Level(4);

        // adding platform, coins, enemies, and obstacle objects all to our game objects arraylist
        gameObjects.addAll(level1.getPlatforms());
        gameObjects.addAll(level1.getCoins());
        gameObjects.addAll(level1.getEnemies());
        gameObjects.addAll(level1.getObstacles());

        gameObjects.addAll(level2.getPlatforms());
        gameObjects.addAll(level2.getCoins());
        gameObjects.addAll(level2.getEnemies());
        gameObjects.addAll(level2.getObstacles());

        gameObjects.addAll(level3.getPlatforms());
        gameObjects.addAll(level3.getCoins());
        gameObjects.addAll(level3.getEnemies());
        gameObjects.addAll(level3.getObstacles());

        gameObjects.addAll(level4.getPlatforms());
        gameObjects.addAll(level4.getCoins());
        gameObjects.addAll(level4.getEnemies());
        gameObjects.addAll(level4.getObstacles());

        instructions = new Image[5];
        instructions[0]=(new ImageIcon(getClass().getResource("/Images/Instruction/Instruction1.png")).getImage());
        instructions[1]=(new ImageIcon(getClass().getResource("/Images/Instruction/Instruction2.png")).getImage());
        instructions[2]=(new ImageIcon(getClass().getResource("/Images/Instruction/Instruction3.png")).getImage());
        instructions[3]=(new ImageIcon(getClass().getResource("/Images/Instruction/Instruction4.png")).getImage());
        instructions[4]=(new ImageIcon(getClass().getResource("/Images/Instruction/Instruction5.png")).getImage());
    }

    public void startGame() {
        Sound.play("/Music/Mario.wav",true);
        timer.start();
    }
    
    private void handlePlayerHit() {
    playerLives--;
    if (playerLives <= 0) {
        gameOver = true;
        Sound.stop();
        Sound.play("/Music/Gameover.wav", false);
        timer.stop();
        repaint();
    } else {
        Sound.stop();
        Sound.play("/Music/MarioDeath.wav",false);
        timer.stop(); // pause the game updates
        repaint();    // refresh screen
        
        // Create a 5-second delay before resuming
        new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setX(currentLevelStartX + 100);
                player.setY(300);
                timer.start(); // resume the game
                Sound.stop();
                Sound.play("/Music/Mario.wav",true);
                ((Timer) e.getSource()).stop(); // stop this delay timer
            }
        }).start();
    }
}

@Override
public void actionPerformed(ActionEvent e) {
    if (!gameOver) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTimerCheck >= 1000) { // 1 second passed
            timeLeft--;
            lastTimerCheck = currentTime;

            if (timeLeft <= 0) {
                handlePlayerHit(); // lose a life
                timeLeft = GAME_TIME_LIMIT; // reset timer
            }
        }
        // First update:
        ArrayList<Image> backgroundImages = new ArrayList<>();
        backgroundImages.addAll(level1.getBackgroundImages());
        backgroundImages.addAll(level2.getBackgroundImages());
        backgroundImages.addAll(level3.getBackgroundImages());
        backgroundImages.addAll(level4.getBackgroundImages());
        
        //Third update:(Ahmed)
        ArrayList<Platform> allPlatforms = new ArrayList<>();
        allPlatforms.addAll(level1.getPlatforms());
        allPlatforms.addAll(level2.getPlatforms());
        allPlatforms.addAll(level3.getPlatforms());
        allPlatforms.addAll(level4.getPlatforms());
        player.move(left, right, jumping, allPlatforms);
        //coins
        for (Coin c : level1.getCoins()) c.checkCollision(player);
        for (Coin c : level2.getCoins()) c.checkCollision(player);
        for (Coin c : level3.getCoins()) c.checkCollision(player);
        for (Coin c : level4.getCoins()) c.checkCollision(player);

        
        // since now we're using one unified list for all objects of type GameObject, we neeed to check collisions only for Enemy objects
        // polymorphism
        for (GameObject obj : gameObjects) {
            obj.update();
            if (obj instanceof Enemy enemy && enemy.checkCollision(player)) {
                handlePlayerHit();
                return;
            }

            if (obj instanceof Obstacle obstacle && obstacle.checkCollision(player)) {
                handlePlayerHit();
                return;
            }
        }

        if(!showInstruction && currentTime - lastInstructionTime >= 30_000){
            int randomIndex = (int)(Math.random() * instructions.length);
            currentInstruction = instructions[randomIndex];
            showInstruction = true;
            instructionStartTime = currentTime;
            lastInstructionTime = currentTime; 
        }
        if (showInstruction && System.currentTimeMillis() - instructionStartTime >= 7_000) {
            showInstruction = false;
            currentInstruction = null;
}
        cameraX = player.getX() - 400;
        repaint();

        // Determine which level player is in based on x position
        int px = player.getX();
        if (px >= 0 && px < 4000) {
            currentLevelStartX = 0; // Level 1
        } else if (px >= 4000 && px < 7500) {
            currentLevelStartX = 4000; // Level 2
        } else if (px >= 7500 && px < 10500) {
            currentLevelStartX = 7000; // Level 
        }else if(px >= 10500){
            currentLevelStartX = 10500; 
        }
    
        if (!gameOver && player.getX() >= 13000) { // Player wins
            gameOver = true;
            playerWon = true;
            Sound.stop();
            Sound.play("/Music/utm.wav", true);  // ✅ Play once only
            timer.stop(); // Stop game loop

            repaint();
}
    
    }

}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(-cameraX, 0);

        int screenWidth = getWidth();
        int screenHeight = getHeight();
        //update 4:(Ahmed)

       ArrayList<Image> backgroundImages = new ArrayList<>();
        backgroundImages.addAll(level1.getBackgroundImages());
        backgroundImages.addAll(level2.getBackgroundImages());
        backgroundImages.addAll(level3.getBackgroundImages());
        backgroundImages.addAll(level4.getBackgroundImages());
        //backgroundImages.addAll(level2.getBackgroundImages());
        int sectionWidth = screenWidth;
        //second update (Ahmed)
        int totalImages = backgroundImages.size();
        int startIdx = Math.max(0, cameraX / sectionWidth - 1);
        int endIdx = Math.min(totalImages, startIdx + 6);

        for (int i = startIdx; i < endIdx; i++) {
            int drawX = i * sectionWidth;
            g2d.drawImage(backgroundImages.get(i), drawX, 0, sectionWidth, screenHeight, null);
        }

        if (level3.hasRainEffect() && player.getX() >= 7500 && player.getX() < 10500) {
        Image rainImage = level3.getRainEffect();
        for (int i = 0; i < 3; i++) { // adjust how many rain tiles you want
            int drawX = cameraX + i * getWidth();
            g2d.drawImage(rainImage, drawX, 0, getWidth(), getHeight(), null);
        }
}

        // objects of platform, coin, obstacle and enemy are now in a list, so we will just loop over them to set draw
        // polymorphism
        for(GameObject obj : gameObjects){
            obj.draw(g2d);
        }

        player.draw(g2d, left, right, jumping);
        g2d.dispose();
        if(showInstruction && currentInstruction != null){
            g.drawImage(currentInstruction, startIdx + 200, endIdx,300,180, null);
        }

        if (gameOver) {
            if (playerWon) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 48));
                g.drawString("You have won!", 250, 280);
                g.drawString("Graduated with GPA: " + String.format("%.2f", Enemy.getGpa()), 120, 340);
            } else {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 48));
                g.drawString("You have lost!", 250, 280);
                g.drawString("Game Over!", 280, 340);
            }
            return;
        }

        // ongoing 
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.setColor(Color.WHITE);
        g.drawString("Merits: " + Coin.getScore(), 10, 20);
        g.drawString("Lives: " + playerLives, 10, 50);
        g.drawString("GPA: " +  String.format("%.2f", Enemy.getGpa()), 10, 80);

        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        String timeString = String.format("Time: %02d:%02d", minutes, seconds);
        g.drawString(timeString, 650, 20); // adjust x/y for top right corner
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_SPACE -> jumping = true;
            case KeyEvent.VK_UP -> jumping = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_SPACE -> jumping = false;
            case KeyEvent.VK_UP -> jumping = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}