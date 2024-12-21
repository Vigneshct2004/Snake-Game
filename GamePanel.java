import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; //no.of grids
    static final int DELAY=75;//higher the value slower is the speed of snake
    final int[] x=new int[GAME_UNITS];//x-coordinate of body of snake
    final int[] y=new int[GAME_UNITS];//y-coordinate of body of snake
    int bodyParts=6;//initial length of snake
    int applesEaten;
    int appleX;//x-coordinate of apple
    int appleY;//y-coordinate of apple
    char direction='R';//initial direction of snake
    boolean running=false;
    Timer timer;
    Random random;


    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setBackground(Color.BLACK);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            //drawing apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            //drawing snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
//                    g.setColor(new Color(10, 180, 67));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free",Font.BOLD,35));
            FontMetrics metrics=g.getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2,g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }
    public void newApple(){
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move(){
            for(int i=bodyParts;i>0;i--){
                x[i]=x[i-1];
                y[i]=y[i-1];
            }
            switch(direction){
                case 'U' -> y[0]=y[0]-UNIT_SIZE;
                case 'D' -> y[0]=y[0]+UNIT_SIZE;
                case 'R' -> x[0]=x[0]+UNIT_SIZE;
                case 'L' -> x[0]=x[0]-UNIT_SIZE;
            }
    }
    public void checkApple(){
            if( (x[0]==appleX) && (y[0]==appleY)){
                bodyParts++;
                applesEaten++;
                newApple();
            }
    }
    public void checkCollisions(){
        //check if head collides with body
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i]) && (y[0]==y[i])){
                running=false;
                break;
            }
        }
        if(x[0]<0 || x[0]>SCREEN_WIDTH || y[0]<0 || y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Your Score: " +applesEaten, (SCREEN_WIDTH-metrics1.stringWidth("Your Score: " +applesEaten))/2 , g.getFont().getSize());

        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics2=getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH-metrics2.stringWidth("Game Over"))/2 , SCREEN_HEIGHT/2);
    }

    public void actionPerformed(ActionEvent e ){
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:{
                    if(direction!='R'){     //preventing 180 degree turns
                        direction='L';
                    }
                    break;
                }
                case KeyEvent.VK_RIGHT:{
                    if(direction!='L'){
                        direction='R';
                    }
                    break;
                }
                case KeyEvent.VK_UP:{
                    if(direction!='D'){
                        direction='U';
                    }
                    break;
                }
                case KeyEvent.VK_DOWN:{
                    if(direction!='U'){
                        direction='D';
                    }
                    break;
                }
            }
        }
    }
}
