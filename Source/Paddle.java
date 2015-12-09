public class Paddle
{
	private int width;
	private int height;
	private int x;
	private int y;
	private int length;
	private boolean shooter;

	public Paddle(int x, int y, int w, int h, int l, boolean s)
	{
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		adjustLength(l);
		shooter = s;
	}

	public void adjustLength(int i)
	{
		switch (i)
		{
		case 0:
			length = i;
			width = 40;
			break;
		case 1:
			length = i;
			width = 60;
			break;
		case 2:
			length = i;
			width = 80;
			break;
		case 3:
			length = i;
			width = 100;
			break;
		case 4:
			length = i;
			width = 120;
			break;
		default:
			if(i > 5)
			{
				length = 4;
			}
			else if(i < 0)
			{
				length = 0;
			}
			break;
		}
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
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

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public boolean isShooter()
	{
		return shooter;
	}

	public void setShooter(boolean shooter)
	{
		this.shooter = shooter;
	}

}
