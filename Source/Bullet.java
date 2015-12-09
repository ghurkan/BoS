public class Bullet
{
	private int x;
	private int y;
	private final int Vy = -3;

	public Bullet(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void Move()
	{
		y += Vy;
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

	public int getVy()
	{
		return Vy;
	}

}
