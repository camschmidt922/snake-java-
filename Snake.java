import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

//Cameron Schmidt
//Program description: 
//Oct 6, 2021

public class Snake
{
   public static final int PASS = 1;
   public static final int CRASH = 0;
   
   private int headX, headY, w, h, dx, dy, upKey, downKey, leftKey, rightKey, xMin, xMax, yMin, yMax;
   private Color color;
   private boolean up, down, left, right, crash;
   private ArrayList<SnakeBodyPart> snake;

   
   public Snake(int x, int y, int w, int h, int upKey, int downKey, int leftKey, int rightKey, Color color)
   {
      this.headX = x;
      this.headY = y;
      this.w = w;
      this.h = h;   
      snake = new ArrayList<SnakeBodyPart>();
      snake.add(new SnakeBodyPart(x, y, w, h, color));          
      this.dx = w;
      this.dy = h;
      this.upKey = upKey;
      this.downKey = downKey;
      this.leftKey = leftKey;
      this.rightKey = rightKey;
      this.color = color;
      xMin = 0;
      xMax = 500;
      yMin = 0;
      yMax = 500;
   }
   
   public Snake(int x, int y, int w, int h, int upKey, int downKey, int leftKey, int rightKey, Color color, int xMin, int xMax, int yMin, int yMax)
   {
      this.headX = x;
      this.headY = y;
      this.w = w;
      this.h = h;   
      snake = new ArrayList<SnakeBodyPart>();
      snake.add(new SnakeBodyPart(x, y, w, h, color));     
   
      this.dx = w;
      this.dy = h;
      this.upKey = upKey;
      this.downKey = downKey;
      this.leftKey = leftKey;
      this.rightKey = rightKey;
      this.color = color;
      this.xMin = xMin;
      this.xMax = xMax;
      this.yMin = yMin;
      this.yMax = yMax;
   }

   public void update(int action) 
   {
      if(up) 
         headY -= dy;
      if(down)
         headY += dy;
      if(left)
         headX -= dx;
      if(right)
         headX += dx;
      
      if(outOfBounds() && action == PASS)
         passThroughScreen();
      else if(outOfBounds() && action == CRASH)
         crash();
      
      snake.remove(snake.size()-1);
      snake.add(0, new SnakeBodyPart(headX, headY, w, h, color));
   }
   
   public boolean outOfBounds()
   {
      if(headX < xMin)
         return true;
      if(headX >= xMax)
         return true;
      if(headY < yMin)
         return true;
      if(headY >= yMax)
         return true;
      return false;
   }
   
   public void passThroughScreen()
   {
      if(headX < xMin)
         headX = xMax - w;
      if(headX >= xMax)
         headX = xMin;
      if(headY < yMin)
         headY = yMax - h;
      if(headY >= yMax)
         headY = yMin;
   }
   
   public void crash()
   {
      if(outOfBounds())
         crash = true;
   }
   
   public void press(int e)
   {
      if(e == upKey) {
         up = true;
         down = false;
         left = false;
         right = false;
      }
      else if(e == downKey) {
         up = false;
         down = true;
         left = false;
         right = false;
      }
      else if(e == leftKey) {
         up = false;
         down = false;
         left = true;
         right = false;
      }
      else if(e == rightKey) {
         up = false;
         down = false;
         left = false;
         right = true;
      }         
   }
   
   public void draw(Graphics2D g2, Color border)
   {
      for(SnakeBodyPart s : snake)
         s.draw(g2, border);      
   }
   
   public void grow()
   {
      snake.add(new SnakeBodyPart(headX, headY, w, h, color));
   }
   
   public boolean checkFoodCollision(SnakeFood f)
   {
      return snake.get(0).getBounds().intersects(f.getBounds());
   }
   
   public boolean intersectsSelf()
   {
      for(int i = 1; i<snake.size(); i++)
         if(snake.get(0).getBounds().intersects(snake.get(i).getBounds()))
            return true;
      return false;
   }
   
   public int getHeadX()
   {
      return headX;
   }

   public void setHeadX(int x)
   {
      this.headX = x;
   }

   public int getHeadY()
   {
      return headY;
   }

   public void setHeadY(int y)
   {
      this.headY = y;
   }

   public int getW()
   {
      return w;
   }

   public void setW(int w)
   {
      this.w = w;
   }

   public int getH()
   {
      return h;
   }

   public void setH(int h)
   {
      this.h = h;
   }

   public Color getColor()
   {
      return color;
   }

   public void setColor(Color color)
   {
      this.color = color;
   }
   
   public boolean getCrash()
   {
      return crash;
   }
   
   public ArrayList<SnakeBodyPart> getBodyParts()
   {
      return snake;
   }

   public String toString()
   {
      return "Snake [x=" + headX + ", y=" + headY + ", w=" + w + ", h=" + h + ", color=" + color + "]";
   }
}
