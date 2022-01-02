package brickgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;

    private Timer timer;
    private int delay =8;

    private int playerx = 310;

    private int ballposx = 120;
    private int ballposy = 350;

    private int ballxdir = -1;
    private int ballydir = -2;

    private MapGenerator map;

    public Gameplay(){
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();

    }
public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        //draw function call
        map.draw((Graphics2D)g);

        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25 ));
        g.drawString(""+score,590,30);


        // border
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        // padle
        g.setColor(Color.green);
        g.fillRect(playerx,550,100,8);

        // ball
        g.setColor(Color.yellow);
        g.fillOval(ballposx,ballposy,20,20);

        if(totalbricks <= 0){
            play = false;
            ballxdir = 0;
            ballydir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,25 ));
            g.drawString("YouWon",260,300);

            g.setFont(new Font("serif",Font.BOLD,25 ));
            g.drawString("Press Enter for Restart",230,350);
        }

        if(ballposy > 570){
            play = false;
            ballxdir = 0;
            ballydir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,25 ));
            g.drawString("Game Over,score:",190,300);

            g.setFont(new Font("serif",Font.BOLD,25 ));
            g.drawString("Press Enter for Restart",230,350);

        }

        g.dispose();
}


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();

        if(play){
            if(new Rectangle(ballposx,ballposy,20,20).intersects(new Rectangle(playerx,550,100,8))) {
                ballydir = -ballydir;
            }
            A: for(int i = 0;i<map.map.length;i++){
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX = j*map.brickwidth + 80;
                        int bricky = i*map.brickheight + 50;
                        int brickwidth = map.brickwidth;
                        int brickheight = map.brickheight;

                        Rectangle rec = new Rectangle(brickX,bricky,brickwidth,brickheight);
                        Rectangle ballrec = new Rectangle(ballposx,ballposy,20,20);
                        Rectangle brickRec = rec;

                        if(ballrec.intersects(brickRec)){
                            map.setBrickValue(0,i,j);
                            totalbricks--;
                            score+=5;

                            if(ballposx +19<= brickRec.x || ballposx +1 >= brickRec.x + brickRec.width){
                                ballxdir = -ballxdir;
                            }
                            else{
                                ballydir = -ballydir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposx += ballxdir;
            ballposy += ballydir;
            if(ballposx < 0){
                ballxdir = -ballxdir;
            }
            if(ballydir < 0){
                ballydir = -ballydir;

            }
            if(ballposx > 670){
                ballxdir = -ballxdir;
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            if(playerx >=600){
                playerx = 600;
            }else{
                moveright();

            }

        }
        if(e.getKeyCode()== KeyEvent.VK_LEFT){
            if(playerx < 10){
                playerx = 10;
            }else{
                moveleft();

            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposx = 120;
                ballposy = 350;
                ballxdir = -1;
                ballydir = -2;
                playerx = 310;
                score = 0;
                totalbricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }

    }
    public void moveright(){
        play = true;
        playerx+=20;

    }
    public void moveleft(){
        play = true;
        playerx-=20;

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
