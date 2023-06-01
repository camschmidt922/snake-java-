import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

//Cameron Schmidt
//Program description: 
//Oct 5, 2021

public class Snake_GUI extends JPanel implements KeyListener
{
   public static final int SCREEN_W = 800;
   public static final int SCREEN_H = 600;
   
   public static final int SNAKE_W = 25;
   public static final int SNAKE_H = 25;
   public static final int SNAKE_STARTX = 400;
   public static final int SNAKE_STARTY = 300;
   public static final int FOOD_STARTX = 100;
   public static final int FOOD_STARTY = 400;
   
   public static final Color BACKGROUND_COLOR = new Color(116, 140, 180);
   public static final Color TRANSPARENT_BACKGROUND_COLOR = new Color(116, 140, 180, 200);
   public static final Color SNAKE_COLOR = new Color(254, 246, 136);
   public static final Color FOOD_COLOR = new Color(238, 0, 0);
   
   public static final Font TITLE_FONT = new Font("Futura", Font.PLAIN, 100);
   public static final Font SUBTITLE_FONT = new Font("Futura", Font.PLAIN, 50);
   public static final Font INFO_FONT = new Font("Futura", Font.PLAIN, 25);
   
   public boolean play, gameOver, start;
   private Timer timer;
   private Snake snake;
   private SnakeFood food;
   private int gameMode;
   
   
   public Snake_GUI()
   {
      this.addKeyListener(this);
      this.setFocusable(true);
      
      gameMode = 1;
      reset();
      start = true;
      
      timer = new Timer(100, new ActionListener() {
         public void actionPerformed(ActionEvent e)
         {
            update();
            repaint();
         }    
      });   
      timer.start();
      
      play = true;
      gameOver = false;
   }
   
   public void update()
   {
      if(play) {
         if(gameMode == 1)
            snake.update(Snake.CRASH);
         else
            snake.update(Snake.PASS);
         if(snake.getCrash()) {
            play = false;
            gameOver = true;
         }
          
         if(snake.intersectsSelf()) {
            play = false;
            gameOver = true;
         }         
         
         if(snake.checkFoodCollision(food)) {
            food.move(snake);
            snake.grow();       
         }    
      }
   }
   
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      
      g2.setColor(BACKGROUND_COLOR);
      g2.fillRect(0, 0, this.getWidth(), this.getHeight());
      
      food.draw(g2, BACKGROUND_COLOR);
      snake.draw(g2, BACKGROUND_COLOR);
      
      if(start)
         showStartMessage(g2);
      
      if(gameOver)
         showGameOverMessage(g2);
      
      if(!gameOver && !play)
         showPauseMessage(g2);
   }
   
   
   public Dimension getPreferredSize()
   {
      return new Dimension(SCREEN_W, SCREEN_H);
   }
   
   public void reset()
   {
      snake = new Snake(SNAKE_STARTX, SNAKE_STARTY, SNAKE_W, SNAKE_H, 
            KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, 
            SNAKE_COLOR, 0, SCREEN_W, 0, SCREEN_H);
      
      food = new SnakeFood(FOOD_STARTX, FOOD_STARTY, SNAKE_W, SNAKE_H, 
            0, SCREEN_W, 0, SCREEN_H, FOOD_COLOR);
      
      start = true;
   }
   
   public void showGameOverMessage(Graphics2D g2)
   {
      g2.setColor(TRANSPARENT_BACKGROUND_COLOR);
      g2.fillRect(0, 0, SCREEN_W, SCREEN_H);
      
      g2.setColor(SNAKE_COLOR);
      
      g2.setFont(TITLE_FONT);    
      int width = g2.getFontMetrics().stringWidth("GAME OVER!");
      g2.drawString("GAME OVER!", SCREEN_W/2 - width/2, 150);
      
      g2.setFont(SUBTITLE_FONT);
      
      width = g2.getFontMetrics().stringWidth("Score: " + snake.getBodyParts().size());
      g2.drawString("Score: " + snake.getBodyParts().size(), SCREEN_W/2 - width/2, 275);
      
      width = g2.getFontMetrics().stringWidth("Press R to Restart");
      g2.drawString("Press R to Restart", SCREEN_W/2 - width/2, 400);
   }
   
   public void showPauseMessage(Graphics2D g2)
   {
      g2.setColor(TRANSPARENT_BACKGROUND_COLOR);
      g2.fillRect(0, 0, SCREEN_W, SCREEN_H);
      
      g2.setColor(SNAKE_COLOR); 
      g2.setFont(SUBTITLE_FONT);     
      int width = g2.getFontMetrics().stringWidth("Paused");
      g2.drawString("Paused", SCREEN_W/2 - width/2, 275);
   }
   
   public void showStartMessage(Graphics2D g2)
   {
      g2.setColor(TRANSPARENT_BACKGROUND_COLOR);
      g2.fillRect(0, 0, SCREEN_W, SCREEN_H);
      
      g2.setColor(SNAKE_COLOR);
      
      g2.setFont(TITLE_FONT);
      int width = g2.getFontMetrics().stringWidth("SNAKE");
      g2.drawString("SNAKE", SCREEN_W/2 - width/2, 150);
      
      g2.setFont(SUBTITLE_FONT);
      String mode;
      if(gameMode == 1)
         mode = "Crash";
      else
         mode = "Pass";
      width = g2.getFontMetrics().stringWidth("Gamemode: " + mode);
      g2.drawString("Gamemode: " + mode, SCREEN_W/2 - width/2, 400);
      
      g2.setFont(INFO_FONT);
      width = g2.getFontMetrics().stringWidth("Press T to Change");
      g2.drawString("Press T to Change", SCREEN_W/2 - width/2, 425);

   }
   
   public static void main(String[] args)
   {
      JFrame frame = new JFrame("Snake");
      JPanel gamePanel = new Snake_GUI();
      frame.add(gamePanel);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.setResizable(false);
   }

   public void keyTyped(KeyEvent e)
   {
   }

   public void keyPressed(KeyEvent e)
   {
      System.out.println(e.getKeyCode());
      
      if(start && e.getKeyCode() == KeyEvent.VK_T)
         gameMode *= -1;
         
      if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN 
            || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
         start = false;
      
      if(play)
         snake.press(e.getKeyCode());
      
      if(!gameOver && !start)
         if(e.getKeyCode() == KeyEvent.VK_SPACE)
            play = !play;         

      if(e.getKeyCode() == 'F')
         snake.grow();
      
      if(gameOver)
         if(e.getKeyCode() == KeyEvent.VK_R) {
            play = true;
            gameOver = false;
            reset();
         }
   }

   public void keyReleased(KeyEvent e)
   {
   }
   
}
