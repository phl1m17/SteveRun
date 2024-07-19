import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	//IMAGE AND TIMER
    Image playerImg;
    Image zombieImg;
    Image zombieImg2;
    Image phantomImg;
    Timer gameLoop;
    
    //GAME
    final Color sky = new Color(154, 209, 230);
    final Color ground = new Color(60, 161, 40);
    int[]scoreCount =  new int[] {50,150,250,350,650,850,950,1050,1150,1250};
    boolean gameOver;
    double score = 0; 
    double highScore = 0; 
    double savedHighScore;
    boolean change;
    KeyHandler keyH = new KeyHandler(); // Creating an object of the KeyHandler Class
    
    //PHYSICS
    int tempVelocityX = 0;
    int velocityX = 10;
    int velocityY = 20;
    int gravity = 5;
    boolean jump = false;
    boolean release = true;
    
    //PLAYER
    final int playerWidth = 320/4;
    final int playerLength = 360/4; 
    final int playerX = 130;
    final int playerFront = playerX+20;
    final int playerBack = playerX+60;
    int playerY = 380;
    int playerAnimationSpeed = 10;
    int playerAnimation = 0;
    
    //ZOMBIE
    final int zombieY = 400;
    final int zombieWidth = 320/5;
    final int zombieLength = 360/5;
    int zombieX = 900;
    int zombieX2 = 1350;
    int zombieXBack;
    int zombieX2Back;
    
    //PHANTOM
    final int phantomY = 200;
    final int phantomWidth = 160;
    final int phantomLength = 160;
    int phantomX = 2000;
    int phantomBack;
    
    //SUN
    int sunX = 900;
    int sunY = 80;
    int sunSize = 80;
    double sunSpeed = 0.7;
    //CONSTRUCTOR
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(900,600));
        this.addKeyListener(keyH);
        this.setFocusable(true);
    
        //GAME LOOP
        gameLoop = new Timer(1000/60,this); // 60 times per second
        gameLoop.start();
        gameOver = false;
        
        //ZOMBIE IMAGE
        zombieImg = new ImageIcon(getClass().getResource("./ZombieRun.png")).getImage();
        zombieImg2 = new ImageIcon(getClass().getResource("./ZombieRun.png")).getImage();
    }    
    public void paint(Graphics g)
    {
        //SKY
        g.setColor(sky);
        if(change == true)
        {
            g.setColor(Color.black);
        }
        g.fillRect(0, 0, 900, 600);
        
        //GROUND
        g.setColor(ground);
        g.fillRect(0,450,900,150);
        
        //PLAYER
        g.drawImage(playerImg, playerX, playerY, playerWidth,playerLength, null);
    
        //ZOMBIE
        g.drawImage(zombieImg, zombieX, zombieY, zombieWidth, zombieLength, null);
        g.drawImage(zombieImg2, zombieX2, zombieY, zombieWidth, zombieLength, null);
        
        //PHANTOM
        g.drawImage(phantomImg,phantomX,phantomY,phantomWidth,phantomLength,null);
        
        //SCORE
        Font fnt = new Font("Helvetica", Font.PLAIN,20);
        g.setFont(fnt);
        g.setColor(Color.black);
        if(change == true)
        {
            g.setColor(Color.white);
        }
        g.drawString("Score: "+(int)score,15,25);
        
        //HIGHSCORE
        if(change == true)
        {
            g.setColor(Color.white);
        }
        g.drawString("High Score: "+(int)highScore, 15, 50);
        
        //GAME OVER
        Font fnt1 = new Font("Helvetica", Font.BOLD,120);
        g.setFont(fnt1);
        g.setColor(Color.red);
        if (gameOver == true)
        {
            //GUI
            g.drawString("GAME OVER", 80, 200);
            g.setFont(fnt);
            g.setColor(Color.black);
            if(change == true)
            {
                g.setColor(Color.white);
            }
            g.drawString("Press SPACE to start", 355, 240);
            g.drawString("Press ESC to quit", 372, 260);
            g.drawImage(zombieImg, playerX+80, playerY, -playerWidth, playerLength, null);
        }
        
        if (keyH.startGame == false)
        {
            //BACKGROUND
            g.setColor(sky);
            g.fillRect(0, 0, 900, 600);
            
            //TITLE SCREEN
            Font fnt2 = new Font("Dialog", Font.BOLD,80);
            g.setFont(fnt2);
            g.setColor(Color.gray);
            g.drawString("STEVE RUN", 218, 135);
            g.setColor(Color.white);
            g.drawString("STEVE RUN", 215, 132);
            
            //START
            Font fnt3 = new Font("Dialog", Font.ITALIC,40);
            g.setFont(fnt3);
            g.drawString("Press Space to Start", 250, 300);
            
            //TUTORIAL
            Font fnt4 = new Font("Dialog", Font.PLAIN,20);
            g.setColor(Color.gray);
            g.setFont(fnt4);
            g.drawString("Jump over the Zombie",340,400);
            g.drawString("and try to get the highest score", 295,430);
            
            //CREDITS
            Font fnt5 = new Font("Dialog", Font.PLAIN,10);
            g.setFont(fnt5);
            g.drawString("By Phil Clarence Manag",10,560);
        }
    }
    public void actionPerformed(ActionEvent e) 
    {
        Change();
        Collision();
        update();
        CheckImpossible();
        Reset();
        repaint();
        Close();
    }
    public void update()
    {
        scoreMethod();
        ChangeBack();
        playerPhysics();
        zombiePhysics();
        phantomPhysics();  
        Sun();  
    }
    public void scoreMethod()
    {
        if (keyH.startGame == true && gameOver == false)    
        {
            score+=0.05;
        }
        if(score>highScore)
        {
            highScore = score;
        }
    }
    public void Change()
    {
        if(gameOver == false)
        {
            for(int i = 0; i<scoreCount.length;i++)
            {
                if(score > scoreCount[i])
                {
                    change = true;
                }
                if(score>scoreCount[i]+30)
                {
                    change = false;
                }
            }
        }
    }
    public void ChangeBack()
    {
        if(gameOver == false)
        {
            if(change == true)
            {
                velocityX = 13;
            }
            if(change == false)
            {
                velocityX = 10;
            }
        }
    }
    public void playerPhysics()
    {
        if(gameLoop.isRunning())
        {
            playerImg = new ImageIcon(getClass().getResource("./playerRun.png")).getImage();
            playerAnimation+=10;
            if(playerY<380)
            {
                playerY+=gravity;
                gravity++;
            }
            if(keyH.upPressed == true)
            {
                jump = true;
            }
            if(jump == true)
            {
                playerY-=velocityY;
                if(playerY>=380)
                {
                    playerY = 380;
                    jump = false;
                    gravity = 5;
                }
            }
            if(playerAnimation>0 && playerAnimation<100 || playerAnimation>200 && playerAnimation<300)
            {
                playerImg = new ImageIcon(getClass().getResource("./playerRun2.png")).getImage();
            }
            if(playerAnimation==400)
            {
                playerAnimation = 0;
            }
        }
    }
    public void zombiePhysics()
    {
        zombieXBack = zombieX+50;
        zombieX2Back = zombieX2+50;
        if(keyH.startGame == true)
        {
            zombieX-=velocityX;
            zombieX2-=velocityX;
        }
        if(zombieX<-100)
        {
            zombieX = Random();
        }
        if(zombieX2<-200)
        {
            zombieX2 = Random();
        }
    }
    public void phantomPhysics()
    {
        phantomBack = phantomX+30;
        if(keyH.startGame == true)
        {
            phantomImg = new ImageIcon(getClass().getResource("./Phantom2.png")).getImage();
            phantomX-=velocityX;
            if(phantomX<-200)
            {
                phantomX = Random();
            }
            if(phantomX>0 &&phantomX<100 || phantomX>200 &&phantomX<300|| phantomX>400 && phantomX<500 || phantomX>600 && phantomX<700|| phantomX>800 && phantomX<900)
            {
                phantomImg = new ImageIcon(getClass().getResource("./Phantom1.png")).getImage();
            }
        }
    }
    public void Sun()
    {
        if(keyH.startGame == true)
        {
            sunX-=sunSpeed;
        }
        if(sunX<-20)
        {
            sunX=900;
        }
    }
    public void Collision()
    { 
        if(gameLoop.isRunning())
        {
            for (int i = playerFront; i<=playerBack;i++)
            {
                if(zombieX == (i) && playerY == 380 || zombieXBack == (i) && playerY ==380 || zombieX2 == (i) && playerY == 380 || zombieX2Back == (i) && playerY == 380)
                {
                    gameOver = true;
                    velocityX = 0;
                    keyH.resetGame = false;
                }
                if(phantomX == (i) && playerY<300 || phantomBack == (i) && playerY<300)
                {
                    gameOver = true;
                    velocityX = 0;
                    keyH.resetGame = false;
                }
                for (int x = phantomX; x<=phantomBack ;x++)
                {
                    if(i == x && playerY<300)
                    {
                        gameOver = true;
                        velocityX = 0;
                        keyH.resetGame = false;
                    }
                }
            }
        }
    }
    public void CheckImpossible()
    {
        if(gameLoop.isRunning())
        {
            //Zombie to Zombie Impossiblity Check
            if(zombieX>zombieX2)
            {
                if(zombieX-zombieX2<250 && zombieX-zombieX2>90)
                {
                    zombieX = Random();
                }
            }
            if(zombieX2>zombieX)
            {
                if(zombieX2-zombieX<250 && zombieX2-zombieX>90)
                {
                    zombieX2 = Random();
                }
            }
            //Phantom to Zombie Impossibility Check
            if(phantomX>zombieX)
            {
                if(phantomX-zombieX<100)
                {
                    phantomX = Random();
                }
            }
            if(phantomX>zombieX2)
            {
                if(phantomX-zombieX2<100)
                {
                    phantomX = Random();
                }
            }
            if(zombieX>phantomX)
            {
                if(zombieX-phantomX<80)
                {
                    zombieX = Random();
                }
            }
            if(zombieX2>phantomX)
            {
                if(zombieX2-phantomX<80)
                {
                    zombieX2 = Random();
                }
            }
        }
    }
    public void Reset()
    {
        if (gameOver == true)
        {
            if(keyH.resetGame == true)
            {
                change = false;
                phantomX = 1500;
                tempVelocityX = 0;
                velocityX = 10;
                score = 0;
                zombieX = 850;
                zombieX2 = 1200;
                gameOver = false;
            }
        }
    }
    public void Close()
    {
        if(keyH.quitGame == true)
        {
            System.exit(0);
        }
    }
    public static int Random()
    {
        int random = (int)(Math.random()*1200+900);
        return random;
    }
 }
 
 //RESOURCES
 // How to paint an image
 // https://stackoverflow.com/questions/20123633/image-drawing-with-paintcomponent-java