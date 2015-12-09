public class BrickLonger extends Brick implements PowerUp
{
	private PowerUpImage img;

	public BrickLonger(int x, int y, int dimX, int dimY, int c)
	{
		super(x, y, dimX, dimY, c);
		setImg(new PowerUpImage(getX(), getY(), getClass().getResource("PaddleLength.png"), this));
	}

	@Override
	public void executePowerUp(SinglePlayer parent)
	{
		parent.setPowerupMess("Longer paddle");
		parent.getPdl().adjustLength(parent.getPdl().getLength() + 1);
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
