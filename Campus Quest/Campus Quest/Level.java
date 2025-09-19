import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Level {
    private int levelID;
    private int playerStartingX = 100; // he default values for my game
    private int playerStartingY = 300; 
    private ArrayList<Platform> platforms;
    private ArrayList<Coin> coins;
    private ArrayList<Enemy> enemies;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Image> backgroundImages;

   
    // rain effect attributes
    private Image rainEffect;
    private boolean hasRainEffect = false;
    private boolean rainKey = false;  // Controls whether to play rain sounds
    private String backgroundMusicPath;
    
    
    public Level(int levelID) {
        this.levelID = levelID;
        platforms = new ArrayList<>();
        coins = new ArrayList<>();
        enemies = new ArrayList<>();
        obstacles = new ArrayList<>();
        backgroundImages = new ArrayList<>();

        switch (levelID) {
            case 1: {
                loadLevel1();
                break;
            }
            case 2: {
                loadLevel2();
                break;
            }
            case 3: {
                loadLevel3();
                break;
            }
            case 4: {
                loadLevel4();
                break;
            }
        }
    }

    public int getEndX() {
    switch (levelID) {
        case 1:
            return 3900; 
        case 2:
            return 6800; 
        case 3:
            return 9700; 
        case 4:
            return 12300; 
        default:
            return 4000; 
      }
    }

    private void loadLevel1() {
        platforms.add(new Platform(0, 500, 7500, 350, false)); // Base ground

        platforms.add(new Platform(300, 400, 180, 20, true));
        platforms.add(new Platform(520, 340, 180, 20, true));
        platforms.add(new Platform(740, 280, 150, 20, true));

        
        platforms.add(new Platform(950, 400, 150, 20, true));
        platforms.add(new Platform(1200, 380, 120, 20, true));
        platforms.add(new Platform(1400, 330, 160, 20, true));  

        
        platforms.add(new Platform(1700, 350, 200, 20, true));
        platforms.add(new Platform(2000, 390, 120, 20, true));
        platforms.add(new Platform(2150, 410, 120, 20, true));

        
        platforms.add(new Platform(2300, 320, 120, 20, true));
        platforms.add(new Platform(2450, 280, 120, 20, true));
        platforms.add(new Platform(2600, 240, 120, 20, true));
        platforms.add(new Platform(2750, 280, 120, 20, true));
        platforms.add(new Platform(2900, 320, 120, 20, true));

        
        platforms.add(new Platform(3100, 360, 150, 20, true));
        platforms.add(new Platform(3300, 400, 150, 20, true));
        //platforms.add(new Platform(3500, 430, 150, 20, true));
        //platforms.add(new Platform(3700, 380, 120, 20, true));

        coins.add(new Coin(330, 370));  // near first platform
        coins.add(new Coin(540, 310));
        coins.add(new Coin(760, 250));
        coins.add(new Coin(1000, 370));
        coins.add(new Coin(1200, 350));
        coins.add(new Coin(1500, 460)); // on ground level

        coins.add(new Coin(1800, 320));
        coins.add(new Coin(2000, 360));
        coins.add(new Coin(2200, 320));

        coins.add(new Coin(2450, 250));
        coins.add(new Coin(2750, 250));
        coins.add(new Coin(2950, 290));

        coins.add(new Coin(3100, 330));
        coins.add(new Coin(3300, 370));
        coins.add(new Coin(3500, 400));
        coins.add(new Coin(3700, 350));
        coins.add(new Coin(3900, 460)); 

        enemies.add(new Enemy(950, 340));
        enemies.add(new Enemy(1200, 420));
        enemies.add(new Enemy(1600, 420));
        enemies.add(new Enemy(1800, 270));
        enemies.add(new Enemy(2550, 420));
        enemies.add(new Enemy(3100, 420));
        enemies.add(new Enemy(3650, 420));

        obstacles.add(new Obstacle(1350, 420));
        obstacles.add(new Obstacle(2050, 310));
        obstacles.add(new Obstacle(2500, 410));
        obstacles.add(new Obstacle(3600, 410)); 

        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/Gate.png")).getImage());
        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/World1.png")).getImage());
        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/World2.png")).getImage());
        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/Library.png")).getImage());
        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/World2.png")).getImage());
        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/World1.png")).getImage());
    }
    public void loadLevel2(){
        final int LEVEL_OFFSET_X = 4000; 

        platforms.add(new Platform(500 + LEVEL_OFFSET_X, 400, 100, 20, true, true, 2,100));
        platforms.add(new Platform(700 + LEVEL_OFFSET_X, 300, 200, 20, true));
        platforms.add(new Platform(1000 + LEVEL_OFFSET_X, 250, 200, 20, true));
        platforms.add(new Platform(1300 + LEVEL_OFFSET_X, 200, 200, 20, true));
        platforms.add(new Platform(1600 + LEVEL_OFFSET_X, 200, 230, 20, true,true, 2,150));
        platforms.add(new Platform(1900 + LEVEL_OFFSET_X, 200, 200, 20, true,true, 2,150));
        platforms.add(new Platform(2200 + LEVEL_OFFSET_X, 250, 200, 20, true));
        platforms.add(new Platform(2500 + LEVEL_OFFSET_X, 300, 200, 20, true));
        platforms.add(new Platform(2800 + LEVEL_OFFSET_X, 400, 200, 20, true));

        coins.add(new Coin(350 + LEVEL_OFFSET_X, 370));
        coins.add(new Coin(650 + LEVEL_OFFSET_X, 310));

        enemies.add(new Enemy(450 + LEVEL_OFFSET_X ,410, "/Images/Enemy/exam.png", "/Images/Enemy/exam.png"));
        enemies.add(new Enemy(900 + LEVEL_OFFSET_X, 250));
        enemies.add(new Enemy(1100 + LEVEL_OFFSET_X, 200));
        enemies.add(new Enemy(2100 + LEVEL_OFFSET_X, 150, "/Images/Enemy/exam.png", "/Images/Enemy/exam.png"));
        enemies.add(new Enemy(2400 + LEVEL_OFFSET_X, 200));
        enemies.add(new Enemy(2700 + LEVEL_OFFSET_X, 300, "/Images/Enemy/exam.png", "/Images/Enemy/exam.png"));

        obstacles.add(new Obstacle(1200 + LEVEL_OFFSET_X, 200));
        obstacles.add(new Obstacle(1600 + LEVEL_OFFSET_X, 410));
        obstacles.add(new Obstacle(1700 + LEVEL_OFFSET_X, 150));
        obstacles.add(new Obstacle(1900 + LEVEL_OFFSET_X, 410));

        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/dsi.png")).getImage());
        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/library.png")).getImage());
        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/he&she.png")).getImage());
    }
    public void loadLevel3(){
        // rain effect
        // add the rain sound
        setRainSound(true);
        try {
            rainEffect = new ImageIcon(getClass().getResource("/Images/Effects/rain1.gif")).getImage();
            hasRainEffect = true;
        } catch (Exception e) {
            hasRainEffect = false; 
        }

        //this is the ground platform (the floor)
        int levelStartX = 7500;
        int floorY = 520; // the ground floor coordinates
        int levelWidth = 6000; 

        // this is the ground
        platforms.add(new Platform(levelStartX, floorY, levelWidth, 350, false));
        
        // the players starting position
        playerStartingX = levelStartX + 10; 
        playerStartingY = floorY - 150; 

        // when i increase the y the platform goes down, decrease it goes up
        // lower: y = 430 to 450
        // middle: y = 400 to 380
        // higher: y = 300 and lower
        // PLATFORMS (first 3)
        // to put coins on top of a platform, platform's y coord - 30
        // y - platfrom height
        // FIRST PLATFORM
        platforms.add(new Platform(levelStartX + 100, 430, 80, 20, true));
        
        // SECOND PLATFORM (WITH ENEMY)
        platforms.add(new Platform(levelStartX + 300, 380, 80, 20, true));
        coins.add(new Coin(levelStartX + 330, 350));  
        enemies.add(new Enemy(levelStartX + 300, 400));

        // THIRD PLATFORM (WITH COIN)
        platforms.add(new Platform(levelStartX + 500, 400, 80, 20, true));
        coins.add(new Coin(levelStartX + 530, 370));  

        // FOURTH PLATFORM (WITH COIN AND ENEMY)
        platforms.add(new Platform(levelStartX + 650, 320, 100, 20, true));
        enemies.add(new Enemy(levelStartX + 720, 340));
        coins.add(new Coin(levelStartX + 670, 290));
            
        // coins on the ground
        coins.add(new Coin(levelStartX + 800, floorY - 30));
        coins.add(new Coin(levelStartX + 900, floorY - 30));
        coins.add(new Coin(levelStartX + 1000, floorY - 30));

        // BUS ENEMY
        enemies.add(new Enemy(levelStartX + levelWidth,450,null,"/Images/Enemy/P211-blue.png",7,  0, levelStartX));

        // SCROLLING ASSIGNMENT MONSTER ENEMY
        enemies.add(new Enemy(levelStartX + levelWidth + 500,370,null,"/Images/Enemy/assignment_monster_left.png",6,300, levelStartX));

        // PLAFORMS (second 3)
        // lower: y = 430 to 450
        // middle: y = 400 to 380
        // higher: y = 300 and lower
        // first platform (lower)
        platforms.add(new Platform(levelStartX + 1200, 420, 70, 20, true));
        // second platform (middle)
        platforms.add(new Platform(levelStartX + 1400, 380, 50, 20, true));
        enemies.add(new Enemy(levelStartX + 1450, 350)); 
        enemies.add(new Enemy(levelStartX + 1450,350,null,"/Images/Enemy/assignment_monster_left.png",6,300, levelStartX));
        coins.add(new Coin(levelStartX + 1400, 350));

        // 3 coins 2 on the ground one sightly above so player jumps and hits enemy
        coins.add(new Coin(levelStartX + 1700, floorY - 30));
        coins.add(new Coin(levelStartX + 1800, floorY - 100));
        coins.add(new Coin(levelStartX + 1900, floorY - 30));

        platforms.add(new Platform(levelStartX + 2000, 420, 100, 20, true));
        platforms.add(new Platform(levelStartX + 2200, 380, 100, 20, true));
        enemies.add(new Enemy(levelStartX + 2500, 350)); 
        coins.add(new Coin(levelStartX + 2200, 350));
        coins.add(new Coin(levelStartX + 2250, 350));

        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/n24_gloomyNight.png")).getImage());
        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/n24_gloomyNight.png")).getImage());
        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/n24_gloomyNight.png")).getImage());
        backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/n24_gloomyNight.png")).getImage());

    }

    public void loadLevel4(){
        //platforms.add(new Platform(0, 500, 4000, 350, false));
        int startX = 10500;
        int groundSurfaceY = 500; // Top of the main ground

        // For Y-coordinates of entities on platforms: platform_surface_Y - entity_height
        // Example: Enemy height 80. On platform surface Y=420, Enemy Y = 420-80 = 340.
        // Example: Coin Y (top-left) for coin on platform surface Y=420, with coin height 30, coin Y = 420-30 = 390.

        platforms.add(new Platform(startX, groundSurfaceY, 4000, 350, false)); // L2 Main ground
        platforms.add(new Platform(startX + 200, groundSurfaceY - 80, 100, 20, true));  // Surface at Y=420
        platforms.add(new Platform(startX + 450, groundSurfaceY - 150, 80, 20, true));   // Surface at Y=350
        platforms.add(new Platform(startX + 700, groundSurfaceY - 100, 120, 20, true));  // Surface at Y=400
        platforms.add(new Platform(startX + 950, groundSurfaceY - 180, 70, 20, true));   // Surface at Y=320 (High!)
        platforms.add(new Platform(startX + 1100, groundSurfaceY - 60, 150, 20, true));  // Surface at Y=440
        //platforms.add(new Platform(startX + 1400, groundSurfaceY - 50, 100, 20, true)); // Surface at Y=450
        platforms.add(new Platform(startX + 1700, groundSurfaceY - 100, 90, 20, true));  // Surface at Y=380
        platforms.add(new Platform(startX + 1950, groundSurfaceY - 70, 100, 20, true)); // Surface at Y=430
        platforms.add(new Platform(startX + 2200, groundSurfaceY - 100, 200, 20, true)); // Surface at Y=400 (Wider)
        platforms.add(new Platform(startX + 2500, groundSurfaceY - 160, 60, 20, true));  // Surface at Y=340 (Tiny & High)

        int coinHeight = 30; // Assuming coin is 30px tall, Y is top-left
        coins.add(new Coin(startX + 240, (groundSurfaceY - 80) - coinHeight));
        coins.add(new Coin(startX + 480, (groundSurfaceY - 150) - coinHeight));
        coins.add(new Coin(startX + 975, (groundSurfaceY - 180) - coinHeight));
        coins.add(new Coin(startX + 1440, (groundSurfaceY - 50) - coinHeight));
        coins.add(new Coin(startX + 1970, (groundSurfaceY - 70) - coinHeight));
        coins.add(new Coin(startX + 2520, (groundSurfaceY - 160) - coinHeight));
        coins.add(new Coin(startX + 750, (groundSurfaceY - 100) - coinHeight));

        int enemyHeight = 80; // Assuming enemy is 80px tall, Y is top-left
        enemies.add(new Enemy(startX + 100, groundSurfaceY - enemyHeight));
        enemies.add(new Enemy(startX + 600, groundSurfaceY - enemyHeight));
        enemies.add(new Enemy(startX + 1250, groundSurfaceY - enemyHeight));
        enemies.add(new Enemy(startX + 1800, groundSurfaceY - enemyHeight));
        enemies.add(new Enemy(startX + 2400, groundSurfaceY - enemyHeight));
        enemies.add(new Enemy(startX + 720, (groundSurfaceY - 100) - enemyHeight));
        enemies.add(new Enemy(startX + 1120, (groundSurfaceY - 60) - enemyHeight));
        enemies.add(new Enemy(startX + 1710, (groundSurfaceY - 120) - enemyHeight));
        enemies.add(new Enemy(startX + 2230, (groundSurfaceY - 100) - enemyHeight));
        enemies.add(new Enemy(startX + 2280, (groundSurfaceY - 100) - enemyHeight));

        int obstacleHeight = 100; // Assuming obstacle is 100px tall, Y is top-left
        obstacles.add(new Obstacle(startX + 380, groundSurfaceY - obstacleHeight));
        obstacles.add(new Obstacle(startX + 900, groundSurfaceY - obstacleHeight));
        obstacles.add(new Obstacle(startX + 1600, groundSurfaceY - obstacleHeight));
        obstacles.add(new Obstacle(startX + 2050, groundSurfaceY - obstacleHeight));
        obstacles.add(new Obstacle(startX + 1150, (groundSurfaceY - 60) - obstacleHeight));
        obstacles.add(new Obstacle(startX + 2250, (groundSurfaceY - 100) - obstacleHeight));

        try {
            backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/World2.png")).getImage());
            backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/Library.png")).getImage());
            backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/World1.png")).getImage());
            backgroundImages.add(new ImageIcon(getClass().getResource("/Images/Backgrounds/Gate.png")).getImage());
            // Add more if Level 2 is long and needs more variety
        } catch (Exception e) {
            System.err.println("Error loading L2 backgrounds: " + e.getMessage());
        }
    }

    // rain effect methods
    public boolean hasRainEffect() {
        return hasRainEffect;
    }

    public Image getRainEffect() {
        return rainEffect;
    }

    public boolean hasRainSound() {
        return rainKey;
    }

    public void setRainSound(boolean rainKey) {
        this.rainKey = rainKey;
        if (rainKey) {
            this.backgroundMusicPath = "/Music/rain_and_thunder.wav";
        } else {
            this.backgroundMusicPath = "/Music/Mario.wav";
        }
    }

    public String getBackgroundMusicPath() {
        return backgroundMusicPath;
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public ArrayList<Coin> getCoins() {
        return coins;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<Image> getBackgroundImages() {
        return backgroundImages;
    }
}
