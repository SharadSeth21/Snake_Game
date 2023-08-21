import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int bHeight = 400 ;
    int bWidth = 400 ;
    int MaxDots = 1600 , DotSize = 10 , appleX  , appleY ;
    int Dots;
    int[] x = new int[MaxDots];
    int[] y = new int[MaxDots];

    Image body,head , apple ;
    Timer timer ;
    int Delay = 300;



    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
   public  boolean inGame = true;
    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(bWidth , bHeight));
        setBackground(Color.BLACK);

        intiGame();
        loadImage();

    }
    //Initialize the Game
    public void intiGame(){
        Dots = 3;
        x[0] = 50;
        y[0] = 50 ;
        //Initialize Snake Position
        for(int i=1;i<Dots;i++){
            x[i] = x[0] + DotSize*i;
            y[i] = y[0];
        }
        //Initialize Apple's position
        locateApple();
        timer = new Timer(Delay , this);
        timer.start();
    }
    //Load Images from resource folder to Image Object

    public void loadImage(){
        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();

        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();

        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }
     // Draw images at snake's and apple's position
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    //Draw image
    public void doDrawing(Graphics g){
       if(inGame){
           g.drawImage(apple,appleX,appleY,this);

           for(int i=0;i<Dots;i++){
               if(i==0){
                   g.drawImage(head , x[0],y[0],this);
               }
               else
                   g.drawImage(body , x[i] , y[i],this);

           }
       }else{
           gameOver(g);
           timer.stop();
       }
    }
    public void locateApple(){
        appleX = (int)(Math.random()*39)*DotSize;
        appleY = (int)(Math.random()*39)*DotSize;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
      if(inGame) {
          move();
          checkCollision();
          checkApple();
      }
      repaint();
    }
    //check collision

    public void checkCollision(){
        for(int i=1;i<=Dots;i++){
            if(i>4 && x[0]==x[i] && y[0]==y[i]){
                inGame = false;
            }
        }
        if(x[0]>=bWidth){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0] <0){
            inGame = false;
        }
        if(y[0]>=bHeight){
            inGame = false;
        }
    }
    //Make Snake Move
    public void move(){

        for(int i=Dots-1;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection){
            x[0] -=DotSize;
        }
        if(rightDirection){
            x[0] +=DotSize;
        }
        if(upDirection){
            y[0] -=DotSize;
        }
        if(downDirection){
            y[0] +=DotSize;
        }
    }
    // make snake eats apple

    public void checkApple(){
        if(appleX ==x[0] && appleY == y[0]){
            Dots++;
            locateApple();
        }
    }
    //restart
    public static void restart(){
       Board board = new Board();
    }
    //Game Over
    public void gameOver(Graphics g){
       String msg = "Gamer Over";
       int score = 100*(Dots-3);
       String scoremsg = "Score: " + Integer.toString(score);
       Font small = new Font("Helvetica" , Font.BOLD , 14);
       FontMetrics fontMetrics = getFontMetrics(small);

       g.setColor(Color.WHITE);
       g.setFont(small);
       g.drawString(msg,(bWidth-fontMetrics.stringWidth(msg))/2,bHeight/4);
       g.drawString(scoremsg,(bWidth-fontMetrics.stringWidth(scoremsg))/2,3*(bHeight/4));
    }

    //Implements control

    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){

            int key = keyEvent.getKeyCode();
            if(key == keyEvent.VK_RIGHT && !leftDirection){
                rightDirection=true;
                upDirection = false;
                downDirection = false;
            }
            if(key == keyEvent.VK_LEFT && !rightDirection){
                leftDirection=true;
                upDirection = false;
                downDirection = false;
            }
            if(key == keyEvent.VK_UP && !downDirection){
                rightDirection = false;
                upDirection = true;
                leftDirection = false;
            }
            if(key == keyEvent.VK_DOWN && !upDirection){
                rightDirection = false;
                upDirection = false;
                leftDirection = false;
                downDirection = true ;
            }
        }
    }
}
