import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private final Timer Timer;
    private int playerX = 310;  //? slider Position in X axis
    private int ballPosX = 350;
    private int ballPosY = 530;
    private int ballDirX = -1;
    private int ballDirY = -2;
    private MapGenerator map;


    GamePlay(){
        map = new MapGenerator(3 , 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        Timer = new Timer(3, this);
        Timer.start();
    }

    public void paint(Graphics g){
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        // Borders
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, 3, 562); // Left Border
        g.fillRect(0, 0, 686, 3); // Top Border
        g.fillRect(686, 0, 3, 562); // Right Border
        g.fillRect(0, 560, 686, 3); // Bottom Border

        // Score Text
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 610, 35);

        // Slider
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.GREEN);
        g.fillOval(ballPosX, ballPosY, 20, 20);


        if (ballPosY > 570) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("      Oops! Game Over. ", 190, 280);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("         You Scored: " + score, 190, 320);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 360);
        }

        if(totalBricks == 0){
            play = false;
            ballDirY = -2;
            ballDirX = -1;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("    Congrats! You scored: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballDirY = -ballDirY;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);

                        if (ballRect.intersects(rect)) {
                            map.setBricksValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            if (ballPosX + 19 <= rect.x || ballPosX + 1 >= rect.x + bricksWidth) {
                                ballDirX = -ballDirX;
                            } else {
                                ballDirY = -ballDirY;
                            }
                            break A;
                        }
                    }


                }
            }


            ballPosX += ballDirX;
            ballPosY += ballDirY;
            if (ballPosX < 0) {
                ballDirX = -ballDirX;
            }
            if (ballPosY < 0) {
                ballDirY = -ballDirY;
            }
            if (ballPosX > 670) {
                ballDirX = -ballDirX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if (playerX < 0) {
                playerX = 0;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            play = !play;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballPosX = 350;
                ballPosY = 530;
                ballDirX = -1;
                ballDirY = -2;
                score = 0;
                playerX = 310;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }


    }

    public void moveRight ()
    {
        play = true;
        playerX += 20;
    }
    public void moveLeft ()
    {
        play = true;
        playerX -= 20;
    }

}
