public class Ball
{
	private int x;
	private int y;
	private int R;
	private int Vx = 1;
	private int Vy = 1;
	private int interval;

	public Ball(int x, int y, int R)
	{
		this.x = x;
		this.y = y;
		this.R = R;
	}

	public void Move()
	{
		x += Vx;
		y += Vy;
		if(interval > 0)
		{
			interval--;
		}
	}

	public int getVx()
	{
		return Vx;
	}

	public void setVx(int vx)
	{
		Vx = vx;
	}

	public int getVy()
	{
		return Vy;
	}

	public void setVy(int vy)
	{
		Vy = vy;
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

	public int getR()
	{
		return R;
	}

	public void setR(int r)
	{
		R = r;
	}

	public int getInterval()
	{
		return interval;
	}

	public void setInterval(int interval)
	{
		this.interval = interval;
	}
}
