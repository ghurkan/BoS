import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class WelcomeScreen extends JPanel implements ActionListener
{
	ArrayList<Animation> blobs = new ArrayList<Animation>();
	int count = 0;
	int countGenerate = 0;
	Random rnd = new Random();

	BufferedImage imgLogo;
	BufferedImage imgBlob;

	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
	}

	public WelcomeScreen()
	{
		this.setLayout(null);
		blobs.add(new Animation(10, 10, 10, 3, 3, -1, true));
		try
		{
			imgLogo = ImageIO.read(getClass().getResource("logo.png"));
			imgBlob = ImageIO.read(getClass().getResource("ball0.gif"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		setBackground(new Color(19, 7, 95));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		for(Animation blb : blobs)
		{
			g.setColor(new Color(blb.clrR, blb.clrG, blb.clrB));
			g.fillOval((int) blb.x, (int) blb.y, (int) blb.r, (int) blb.r);
			if(blb.flag)
			{
				g.drawImage(imgBlob, blb.x, blb.y, this);
			}
			else
			{
				g.setColor(new Color(blb.clrR, blb.clrG, blb.clrB));
				g.fillOval((int) blb.x, (int) blb.y, (int) blb.r, (int) blb.r);
			}

		}
		g.drawImage(imgLogo, 5, 20, 400, 200, this);
	}

	public void addAnimation(Animation b)
	{
		blobs.add(b);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		for(int i = 0; i < blobs.size(); i++)
		{
			blobs.get(i).y = blobs.get(i).y + (int) blobs.get(i).Vy;

			if(blobs.get(i).x < 0 || blobs.get(i).x >= this.getWidth() - 20)
			{
				blobs.get(i).Vx = -blobs.get(i).Vx;
			}
			if(blobs.get(i).y < 0 || blobs.get(i).y >= this.getHeight() - 20)
			{
				blobs.get(i).Vy = -blobs.get(i).Vy;
			}
			blobs.get(i).interval--;

			if(blobs.get(i).interval == -5 && blobs.get(i).flag)
			{
				blobs.add(new Animation(blobs.get(i).x, blobs.get(i).y, blobs.get(i).r, 0, 0, 45, false));
				blobs.get(i).interval = -1;
			}

			if(!blobs.get(i).flag)
			{
				if(blobs.get(i).clrR >= 4)
				{
					blobs.get(i).clrR -= 4;
				}
			}

			if(blobs.get(i).interval == 0 && !blobs.get(i).flag)
			{
				blobs.remove(i);
			}
			repaint();
		}

		if(countGenerate > 50)
		{
			blobs.add(new Animation(rnd.nextInt(400), 10, 10, 3, 3, -1, true));
			repaint();
			countGenerate = 0;
		}
		else if(blobs.size() > 535)
		{
			countGenerate = 0;
		}
		count++;
		countGenerate++;
	}

	public class Animation
	{
		int x, y, r;
		int clrR = 255, clrG = 0, clrB = 0;
		int Vx = 1;
		int Vy = 1;
		int interval;
		boolean flag = false;

		public Animation(int x, int y, int r, int vx, int vy, int i, boolean f)
		{
			this.x = x;
			this.y = y;
			this.r = r;
			this.Vx = vx;
			this.Vy = vy;
			this.interval = i;
			this.flag = f;
		}

		public int getX()
		{
			return (int) x;
		}

		public void setX(int x)
		{
			this.x = x;
		}

		public int getY()
		{
			return (int) y;
		}

		public void setY(int y)
		{
			this.y = y;
		}

		public void setR(int r)
		{
			this.r = r;
		}

	}
}
