import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

//Cameron Schmidt
//Program description: 
//Oct 14, 2021

public class SnakeFood
{
   private int x, y, w, h, minX, maxX, minY, maxY;
   private Color color;
   
   public SnakeFood(int x, int y, int w, int h, int minX, int maxX, int minY, int maxY, Color color)
   {
      this.x = x;
      this.y = y;
      this.w = w;
      this.h = h;
      this.minX = minX;
      this.maxX = maxX;
      this.minY = minY;
      this.maxY = maxY;
      this.color = color;
   }
   
   public void draw(Graphics2D g2, Color border)
   {
      g2.setColor(color);
      g2.fill(getBounds());
      g2.setColor(border);
      g2.draw(getBounds());
   }
   
   public Rectangle getBounds()
   {
      return new Rectangle(x, y, w, h);
   }
   
   public void move(Snake s)
   {
      for(SnakeBodyPart p : s.getBodyParts()) {
         do {
            x = (int)(Math.random()*(maxX/w))*w + minX;
            y = (int)(Math.random()*(maxY/h))*h + minY;
         }while(getBounds().intersects(p.getBounds()));
      }
   }

   public int getX()
   {
      return x;
   }

   public void setX(int x)
   {
      this.x = x;
   }

   public int getY()
   {
      return y;
   }

   public void setY(int y)
   {
      this.y = y;
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
}
