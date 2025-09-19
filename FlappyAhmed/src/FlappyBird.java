import java.awt.event.*;
import java.awt.*;
import java.awt.RenderingHints.Key;
import java.util.*; 
import javax.swing.*;
import javax.swing.Timer;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640; 

    //images 
    Image backgroundImage;
    Image birdImg; 
    Image topPipeImg;
    Image bottomPipImage;
    
    //bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;

    //game loop 
    Timer gameLoop;
    Timer placePipesTimer;

    class Bird{
        int x = birdX;
        int y = birdY; 
        int width = birdWidth; 
        int height = birdHeight;
        Image img;

        Bird(Image img){
            this.img = img;
        }
    }
    //pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64; 
    int pipeHeight = 512; 

    class Pipe{
        int x = pipeX;
        int y = pipeY; 
        int width = pipeWidth;
        int height = pipeHeight;
        Image img; 
        boolean passed = false;

        Pipe(Image img){
            this.img = img;
        }
    }
    //game logic
    Bird bird;
    int velocityX = -4;

    int birdVelocityY = 0; 
    int gravity = 1;
    
    ArrayList<Pipe> pipes;
    boolean gameOver = false;
    double score = 0;



    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);
        //load images 
        backgroundImage = new ImageIcon(getClass().getResource("./background.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./me2.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        
        bird = new Bird(birdImg);

        pipes = new ArrayList<Pipe>();
        gameLoop = new Timer(1000/60,this);
        gameLoop.start();

        placePipesTimer = new Timer(1500, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipesTimer.start();
    
    }
    public void placePipes(){
        int randomPipeY =(int) (pipeY - pipeHeight/4 - Math.random() *(pipeHeight/2));
        int openingSpace = boardHeight/4;
        //int randomPipeX =(int) (pipeX - pipeHeight/4 - Math.random() *(pipeHeight/2));
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipImage);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); 
        draw(g); 
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImage, 0, 0, boardWidth,boardHeight,null);
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
        //pipes
        for(int i = 0; i<pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);

        }

        //Score
        g.setColor(Color.white);
        g.setFont(new Font("Arial" , Font.PLAIN,32));
        if(gameOver){
            g.drawString("Game Over: "+ String.valueOf((int) score), 10, 35);
        }else{
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move(){
        birdVelocityY += gravity;
        bird.y +=birdVelocityY;
        bird.y = Math.max(bird.y,0); 
        
        //pipes
        for(int i = 0; i<pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true; 
                score += 0.5;
            }

            if (collision(bird, pipe)){
                gameOver = true;
            }
        }

        if ((bird.y > boardHeight) || (bird.y< 0)){
            gameOver = true;
    }
        
}
    public boolean collision(Bird a,Pipe b){
        return a.x<b.x +b.width && a.x  + a.width >b.x &&
                a.y<b.y+b.height && a.y+a.width > b.y; 
    }

    public void actionPerformed(ActionEvent e){
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        } 
    }

    public void keyPressed(KeyEvent e){ 
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            birdVelocityY = -9;
            }
        if (gameOver){
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
                bird.y = birdY;
                birdVelocityY = 0; 
                pipes.clear();
                score = 0;
                gameOver = false; 
                gameLoop.start();
                placePipesTimer.start();
        }

    }

        @Override
    public void keyReleased(KeyEvent e) {
    // Not used
}

        @Override
    public void keyTyped(KeyEvent e) {
        // Not used
}

}
