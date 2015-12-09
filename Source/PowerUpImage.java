import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class PowerUpImage
{
	private int x;
	private int y;
	private BufferedImage img;
	private Brick parent;

	public PowerUpImage(int x, int y, URL url, Brick parent)
	{
		this.x = x;
		this.y = y;
		this.parent = parent;
		try
		{
			this.setImg(ImageIO.read(url));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public final void Move()
	{
		y += 3;
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

	public BufferedImage getImg()
	{
		return img;
	}

	public void setImg(BufferedImage img)
	{
		this.img = img;
	}

	public Brick getParent()
	{
		return parent;
	}

	public void setParent(Brick parent)
	{
		this.parent = parent;
	}
}
