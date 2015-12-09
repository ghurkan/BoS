public class BrickBall extends Brick implements PowerUp
{
	private PowerUpImage img;

	public BrickBall(int x, int y, int dimX, int dimY, int c)
	{
		super(x, y, dimX, dimY, c);
		setImg(new PowerUpImage(getX(), getY(), getClass().getResource("ExtraBall.png"), this));
	}

	@Override
	public void executePowerUp(SinglePlayer parent)
	{
		parent.setPowerupMess("Added ball");
		parent.balls.add(new Ball(parent.balls.get(0).getX(), parent.balls.get(0).getY(), parent.balls.get(0).getR()));
		parent.balls.get(parent.balls.size() - 1).setVx(-parent.balls.get(0).getVx());
		parent.balls.get(parent.balls.size() - 1).setVy(parent.balls.get(0).getVy());
		parent.setPwrupMessDuration(200);
	}

	@Override
	public void dropPowerUp(SinglePlayer parent)
	{
		parent.powerUpImgs.add(img);
	}

	public PowerUpImage getImg()
	{
		return img;
	}

	public void setImg(PowerUpImage img)
	{
		this.img = img;
	}
}
