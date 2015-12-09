public class Brick implements PowerUp
{
	private int x;
	private int y;
	private int dimX;
	private int dimY;
	private int color;

	public Brick(int x, int y, int dimX, int dimY, int c)
	{
		this.x = x;
		this.y = y;
		this.dimX = dimX;
		this.dimY = dimY;
		this.color = c;
	}

	@Override
	public void executePowerUp(SinglePlayer parent)
	{
		// parent.setWaitMessage("Neutral powerup");
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

	public int getDimX()
	{
		return dimX;
	}

	public void setDimX(int dimX)
	{
		this.dimX = dimX;
	}

	public int getDimY()
	{
		return dimY;
	}

	public void setDimY(int dimY)
	{
		this.dimY = dimY;
	}

	public int getColor()
	{
		return color;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	@Override
	public void dropPowerUp(SinglePlayer parent)
	{
		// TODO Auto-generated method stub

	}
}
