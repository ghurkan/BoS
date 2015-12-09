import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SinglePlayer extends JPanel implements ActionListener, Runnable
{
	ArrayList<Ball> balls = new ArrayList<Ball>();
	ArrayList<Brick> bricks = new ArrayList<Brick>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<PowerUpImage> powerUpImgs = new ArrayList<PowerUpImage>();
	ArrayList<Sound> soundsVictory = new ArrayList<Sound>();
	ArrayList<Sound> soundsFailure = new ArrayList<Sound>();
	ArrayList<Sound> soundsFx = new ArrayList<Sound>();
	ArrayList<BufferedImage> imagesBricks = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> imagesPaddle = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> imagesPaddleS = new ArrayList<BufferedImage>();

	private JPanel parentPanel;
	private Paddle pdl;
	private BufferedImage ballImg;
	private BufferedImage pdlImg0;
	private BufferedImage pdlImg1;
	private BufferedImage pdlImg2;
	private BufferedImage pdlImg3;
	private BufferedImage pdlImg4;
	private BufferedImage pdlImg0S;
	private BufferedImage pdlImg1S;
	private BufferedImage pdlImg2S;
	private BufferedImage pdlImg3S;
	private BufferedImage pdlImg4S;
	private BufferedImage brckImg0;
	private BufferedImage brckImg1;
	private BufferedImage brckImg2;
	private BufferedImage brckImg3;
	private BufferedImage brckImg4;
	private BufferedImage brckImg5;
	private BufferedImage brckImg6;
	private BufferedImage bulletImg;
	private BufferedImage lifeImg;
	private BufferedImage bgImg;

	private boolean passed = false;
	private boolean failed = false;
	private boolean started = false;
	private boolean aftermath = false;
	private boolean focused = true;
	private int R = 10;
	private int pwrupMessDuration = 100;
	private Font fntArial = new Font("Arial", Font.BOLD, 16);
	private Font fntSmall = new Font("Papyrus", Font.BOLD, 14);

	private String waitMessage = "Click to continue";
	private String pausedMessage = "";
	private String levelPassMess = "";
	private String levelFailMess = "";
	private String powerupMess = "";
	private String score = "Score: 0";
	private String username = "";
	private String setup[] = {

			// LEVEL 1
			"20-80-20-90-20-100-20-110-20-120-20-130-20-140-20-150-20-160-20-170-20-180-20-190" + "-60-160-60-170-60-180-60-190"
					+ "-100-160-100-170-100-180-100-190" + "-140-100-140-110-140-120-140-130-140-160-140-170-140-180-140-190"
					+ "-180-100-180-110-180-120-180-130-180-160-180-170-180-180-180-190" + "-220-100-220-110-220-120-220-130-220-160-220-170-220-180-220-190"
					+ "-260-160-260-170-260-180-260-190" + "-300-160-300-170-300-180-300-190"
					+ "-340-80-340-90-340-100-340-110-340-120-340-130-340-140-340-150-340-160-340-170-340-180-340-190",

			// LEVEL 2

			"20-80-20-100-20-110-20-120" + "-60-80-60-100-60-120-60-160" + "-100-80-100-120-100-160-100-220-100-230"
					+ "-140-80-140-90-140-100-140-110-140-120-140-130-140-140-140-150-140-160-140-170-140-180-140-190-140-200-140-210-140-220-140-230"
					+ "-180-80-180-90-180-100-180-110-180-120-180-130-180-140-180-150-180-160"
					+ "-220-80-220-90-220-100-220-110-220-120-220-130-220-140-220-150-220-160-220-170-220-180-220-190-220-200-220-210-220-220-220-230"
					+ "-260-80-260-120-260-160-260-220-260-230" + "-300-80-300-120-300-140-300-160" + "-340-80-340-120-340-130-340-140",

			// LEVEL 3

			"20-80-20-90-20-150-20-160-20-220-20-230" + "-60-80-60-90-60-120-60-150-60-160-60-190-60-220-60-230" + "-100-110-100-130-100-180-100-200"
					+ "-140-100-140-120-140-140-140-170-140-190-140-210" + "-180-90-180-110-180-130-180-140-180-150-180-160-180-170-180-180-180-200-180-220"
					+ "-220-100-220-120-220-140-220-170-220-190-220-210" + "-260-110-260-130-260-180-260-200"
					+ "-300-80-300-90-300-120-300-150-300-160-300-190-300-220-300-230" + "-340-80-340-90-340-150-340-160-340-220-340-230",

			// LEVEL 4

			"20-110-20-130-20-160-20-170-20-180-20-190-20-200-20-210" + "-60-100-60-120-60-150-60-160-60-170-60-180-60-190-60-200-60-210"
					+ "-100-90-100-120-100-130-100-150-100-170-100-180-100-200-100-210" + "-140-90-140-110-140-140-140-170-140-200-140-210"
					+ "-180-80-180-100-180-120-180-130-180-150-180-180" + "-220-90-220-110-220-140-220-170-220-200-220-210"
					+ "-260-90-260-120-260-130-260-150-260-170-260-180-260-200-260-210"
					+ "-300-100-300-120-300-150-300-160-300-170-300-180-300-190-300-200-300-210"
					+ "-340-110-340-130-340-160-340-170-340-180-340-190-340-200-340-210",

			// LEVEL 5

			"20-170-20-180-20-190-20-200-20-210-20-220-20-230-20-240"
					+ "-60-110-60-120-60-130-60-140-60-150-60-170-60-180-60-190-60-200-60-210-60-220-60-230-60-240-60-250-60-260-60-270-60-280-60-290"
					+ "-100-80-100-100-100-110-100-120-100-130-100-140-100-150-100-170-100-180-100-190-100-200-100-210-100-220-100-230-100-240-100-250-100-260-100-270-100-280-100-290-100-300-100-310-100-320-100-330"
					+ "-140-90-140-100-140-110-140-120-140-130-140-140-140-150-140-170-140-180-140-190-140-200-140-210-140-220-140-230-140-240-140-250-140-260-140-270-140-280-140-290-140-300-140-310-140-320-140-330"
					+ "-180-80-180-90-180-100-180-110-180-120-180-130-180-140-180-150-180-170-180-180-180-190-180-200-180-210-180-220-180-230-180-240-180-250-180-260-180-270-180-280"
					+ "-220-90-220-100-220-110-220-120-220-130-220-140-220-150-220-170-220-180-220-190-220-200-220-210-220-220-220-230-220-240-220-250-220-260-220-270-220-280-220-290-220-300-220-310-220-320-220-330"
					+ "-260-80-260-100-260-110-260-120-260-130-260-140-260-150-260-170-260-180-260-190-260-200-260-210-260-220-260-230-260-240-260-250-260-260-260-270-260-280-260-290-260-300-260-310-260-320-260-330"
					+ "-300-110-300-120-300-130-300-140-300-150-300-170-300-180-300-190-300-200-300-210-300-220-300-230-300-240-300-250-300-260-300-270-300-280-300-290"
					+ "-340-170-340-180-340-190-340-200-340-210-340-220-340-230-340-240" };

	private String color[] = {
			"0-0-0-0-0-0-0-0-0-0-0-0-1-1-1-1-1-1-1-1-2-2-2-2-1-1-1-1-2-2-2-2-1-1-1-1-2-2-2-2-1-1-1-1-1-1-1-1-1-1-1-1-0-0-0-0-0-0-0-0-0-0-0-0",
			"1-3-3-3" + "-1-3-3-2" + "-4-3-2-5-5" + "-1-3-3-3-3-3-3-3-3-3-3-3-3-3-5-5" + "-1-3-3-3-3-3-3-3-3" + "-1-3-3-3-3-3-3-3-3-3-3-3-3-3-5-5"
					+ "-4-3-2-5-5" + "-1-3-3-2" + "-1-3-3-3",
			"3-3-3-3-3-3" + "-3-3-0-3-3-0-3-3" + "-0-0-0-0" + "-0-0-0-0-0-0" + "-0-0-5-5-5-5-5-0-0" + "-0-0-0-0-0-0" + "-3-3-0-3-3-0-3-3" + "-3-3-3-3-3-3",
			"3-4-5-5-5-5-5-5" + "-3-4-5-5-5-5-5-5-5" + "-3-4-4-5-5-5-5-5" + "-3-4-5-5-5-5" + "-3-4-2-2-5-5" + "-3-4-5-5-5-5" + "-3-4-4-5-5-5-5-5"
					+ "-3-4-5-5-5-5-5-5-5" + "-3-4-5-5-5-5-5-5",
			"6-6-6-6-6-6-6-6" + "-3-3-3-3-3-0-0-0-0-0-0-0-0-0-0-0-0-0" + "-6-3-3-3-3-3-3-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0"
					+ "-3-3-3-3-3-3-3-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0" + "-3-3-3-3-3-3-3-3-0-0-0-0-0-0-0-0-0-0-0-0"
					+ "-3-3-3-3-3-3-3-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0" + "-6-3-3-3-3-3-3-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0"
					+ "-3-3-3-3-3-0-0-0-0-0-0-0-0-0-0-0-0-0" + "6-6-6-6-6-6-6-6" };

	private int currentSetup = 0;
	private int currentColor = 0;
	private long scoreNum = 0;
	private long savedScore = 0;
	private Timer start, stop;
	private boolean fxEnabled = true;
	private float volume = 100;
	private int life = 3;

	private Sound victory1;
	private Sound victory2;
	private Sound failure1;
	private Sound failure2;
	private Sound failure3;
	private Sound brickHit1;
	private Sound paddleHit1;

	public SinglePlayer(JPanel parent)
	{
		parentPanel = parent;
		setBackground(new Color(19, 7, 95));
		pdl = new Paddle(10, 510, 60, 10, 2, false);

		victory1 = new Sound(getClass().getResource("victory1.wav"));
		victory2 = new Sound(getClass().getResource("victory2.wav"));
		failure1 = new Sound(getClass().getResource("failure1.wav"));
		failure2 = new Sound(getClass().getResource("failure2.wav"));
		failure3 = new Sound(getClass().getResource("failure3.wav"));
		paddleHit1 = new Sound(getClass().getResource("paddleHit1.wav"));
		brickHit1 = new Sound(getClass().getResource("brickHit1.wav"));

		soundsVictory.add(victory1);
		soundsVictory.add(victory2);
		soundsFailure.add(failure1);
		soundsFailure.add(failure2);
		soundsFailure.add(failure3);
		soundsFx.add(paddleHit1);
		soundsFx.add(brickHit1);

		try
		{
			ballImg = ImageIO.read(getClass().getResource("ball0.gif"));
			pdlImg0S = ImageIO.read(getClass().getResource("paddle0S.png"));
			pdlImg1S = ImageIO.read(getClass().getResource("paddle1S.png"));
			pdlImg2S = ImageIO.read(getClass().getResource("paddle2S.png"));
			pdlImg3S = ImageIO.read(getClass().getResource("paddle3S.png"));
			pdlImg4S = ImageIO.read(getClass().getResource("paddle4S.png"));
			pdlImg0 = ImageIO.read(getClass().getResource("paddle0.gif"));
			pdlImg1 = ImageIO.read(getClass().getResource("paddle1.gif"));
			pdlImg2 = ImageIO.read(getClass().getResource("paddle2.gif"));
			pdlImg3 = ImageIO.read(getClass().getResource("paddle3.gif"));
			pdlImg4 = ImageIO.read(getClass().getResource("paddle4.gif"));
			brckImg0 = ImageIO.read(getClass().getResource("brick0.gif"));
			brckImg1 = ImageIO.read(getClass().getResource("brick1.gif"));
			brckImg2 = ImageIO.read(getClass().getResource("brick2.gif"));
			brckImg3 = ImageIO.read(getClass().getResource("brick3.gif"));
			brckImg4 = ImageIO.read(getClass().getResource("brick4.gif"));
			brckImg5 = ImageIO.read(getClass().getResource("brick5.gif"));
			brckImg6 = ImageIO.read(getClass().getResource("brick6.gif"));
			bulletImg = ImageIO.read(getClass().getResource("bullet0.gif"));
			lifeImg = ImageIO.read(getClass().getResource("life.gif"));
			bgImg = ImageIO.read(getClass().getResource("bg3.png"));

			repaint();
		}
		catch(Exception e)
		{
			System.out.println("Exception at loading images.");
			e.printStackTrace();
		}
		imagesPaddle.add(pdlImg0);
		imagesPaddle.add(pdlImg1);
		imagesPaddle.add(pdlImg2);
		imagesPaddle.add(pdlImg3);
		imagesPaddle.add(pdlImg4);
		imagesPaddleS.add(pdlImg0S);
		imagesPaddleS.add(pdlImg1S);
		imagesPaddleS.add(pdlImg2S);
		imagesPaddleS.add(pdlImg3S);
		imagesPaddleS.add(pdlImg4S);
		imagesBricks.add(brckImg0);
		imagesBricks.add(brckImg1);
		imagesBricks.add(brckImg2);
		imagesBricks.add(brckImg3);
		imagesBricks.add(brckImg4);
		imagesBricks.add(brckImg5);
		imagesBricks.add(brckImg6);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, null);

		for(int i = 0, j = 55; i < life; i++)
		{
			g.drawImage(lifeImg, j, 545, null);
			j += 20;
		}

		for(Brick brck : bricks)
		{
			g.drawImage(imagesBricks.get(brck.getColor()), brck.getX(), brck.getY(), this);
		}

		for(Ball blb : balls)
		{
			g.drawImage(ballImg, blb.getX(), blb.getY(), this);
		}

		if(pdl.isShooter())
		{
			g.drawImage(imagesPaddleS.get(pdl.getLength()), pdl.getX(), pdl.getY(), this);

		}
		else
		{
			g.drawImage(imagesPaddle.get(pdl.getLength()), pdl.getX(), pdl.getY(), this);
		}

		if(!bullets.isEmpty())
		{
			for(int i = 0; i < bullets.size(); i++)
			{
				g.drawImage(bulletImg, bullets.get(i).getX(), bullets.get(i).getY(), this);
			}
		}

		if(!powerUpImgs.isEmpty())
		{
			for(int i = 0; i < powerUpImgs.size(); i++)
			{
				g.drawImage(powerUpImgs.get(i).getImg(), powerUpImgs.get(i).getX(), powerUpImgs.get(i).getY(), this);
			}
		}

		g.setColor(Color.YELLOW);
		g.setFont(fntArial);
		g.drawString("Level " + (currentSetup + 1), this.getWidth() / 2 - 30, 20);
		g.drawString(score, 20, 30);
		g.drawString(levelPassMess, this.getWidth() / 3, this.getHeight() / 2 - 20);
		g.drawString(levelFailMess, this.getWidth() / 3 - 80, this.getHeight() / 2 - 20);
		g.drawString(waitMessage, this.getWidth() / 3, this.getHeight() / 2);
		g.drawString(pausedMessage, this.getWidth() / 3 - 20, this.getHeight() / 2 + 20);
		g.drawString(powerupMess, this.getWidth() - 150, this.getHeight() - 30);
		g.drawString("Life: ", 20, 560);
		g.setFont(fntSmall);
		FontMetrics fm = getFontMetrics(getFont());
		int width = fm.stringWidth(username);
		g.drawString(username, this.getWidth() - width - 20, 30);
		g.fillRect(0, 40, this.getWidth(), 5);
		g.fillRect(0, this.getHeight() - 70, this.getWidth(), 3);
	}

	public void initialize()
	{
		Ball initial = new Ball(pdl.getX() + pdl.getWidth() / 2, pdl.getY() - 10, R);
		if(balls.isEmpty())
		{
			balls.add(initial);
		}
		else
		{
			balls.clear();
			balls.add(initial);
		}
		balls.get(0).setVx(0);
		balls.get(0).setVy(0);
		pdl.setShooter(false);
		setupBricks(setup[currentSetup], color[currentColor]);
		started = true;
		levelFailMess = "";
		life = 3;
	}

	public void addBall(Ball b)
	{
		balls.add(b);
		repaint();
	}

	public void fireBullet()
	{
		if(pdl.isShooter())
		{
			bullets.add(new Bullet(pdl.getX() + 10, pdl.getY() - 10));
			bullets.add(new Bullet(pdl.getX() + pdl.getWidth() - 10, pdl.getY() - 10));
		}
	}

	public void moveBullet()
	{
		if(!bullets.isEmpty())
		{
			for(int i = 0; i < bullets.size(); i++)
			{
				bullets.get(i).Move();
			}
		}
	}

	public void movePowerUpImgs()
	{
		if(!powerUpImgs.isEmpty())
		{
			for(int i = 0; i < powerUpImgs.size(); i++)
			{
				powerUpImgs.get(i).Move();
			}
		}
	}

	public void moveBalls()
	{
		for(int i = 0; i < balls.size(); i++)
		{
			balls.get(i).Move();
		}
	}

	public void setPowerups()
	{
		int size = bricks.size();
	}

	public void addBrick(Brick b)
	{
		bricks.add(b);
		repaint();
	}

	public void setupBricks(String s, String c)
	{
		try
		{
			String[] temp = s.split("-");
			String[] temp2 = c.split("-");
			Random rnd = new Random();
			int index;

			int brickAmount = temp.length / 2;
			int powerupAmount = 0;

			String types = "";
			for(int i = 0; i < brickAmount - 1; i++)
			{
				types += "0-";
			}
			types += "0";

			while(powerupAmount < (int) brickAmount / 10)
			{
				index = rnd.nextInt(types.length());

				if(types.charAt(index) == '0')
				{
					types = types.substring(0, index) + '1' + types.substring(index + 1);
					powerupAmount++;
				}
			}
			powerupAmount = 0;
			while(powerupAmount < brickAmount / 20)
			{
				index = rnd.nextInt(types.length());

				if(types.charAt(index) == '0')
				{
					types = types.substring(0, index) + '2' + types.substring(index + 1);
					powerupAmount++;
				}
			}
			powerupAmount = 0;
			while(powerupAmount < brickAmount / 20)
			{
				index = rnd.nextInt(types.length());

				if(types.charAt(index) == '0')
				{
					types = types.substring(0, index) + '3' + types.substring(index + 1);
					powerupAmount++;
				}
			}
			powerupAmount = 0;
			while(powerupAmount < brickAmount / 20)
			{
				index = rnd.nextInt(types.length());

				if(types.charAt(index) == '0')
				{
					types = types.substring(0, index) + '4' + types.substring(index + 1);
					powerupAmount++;
				}
			}
			powerupAmount = 0;
			while(powerupAmount < brickAmount / 20)
			{
				index = rnd.nextInt(types.length());

				if(types.charAt(index) == '0')
				{
					types = types.substring(0, index) + '5' + types.substring(index + 1);
					powerupAmount++;
				}
			}
			powerupAmount = 0;

			String temp3[] = types.split("-");

			for(int i = 0, j = 0; i < temp.length; i = i + 2, j++)
			{
				switch (Integer.parseInt(temp3[j]))
				{
				case 0:
					addBrick(new Brick(Integer.parseInt(temp[i]), Integer.parseInt(temp[i + 1]), 40, 10, Integer.parseInt(temp2[j])));
					break;
				case 1:
					addBrick(new BrickLonger(Integer.parseInt(temp[i]), Integer.parseInt(temp[i + 1]), 40, 10, Integer.parseInt(temp2[j])));
					break;
				case 2:
					addBrick(new BrickShooter(Integer.parseInt(temp[i]), Integer.parseInt(temp[i + 1]), 40, 10, Integer.parseInt(temp2[j])));
					break;
				case 3:
					addBrick(new BrickLife(Integer.parseInt(temp[i]), Integer.parseInt(temp[i + 1]), 40, 10, Integer.parseInt(temp2[j])));
					break;
				case 4:
					addBrick(new BrickShorter(Integer.parseInt(temp[i]), Integer.parseInt(temp[i + 1]), 40, 10, Integer.parseInt(temp2[j])));
					break;
				case 5:
					addBrick(new BrickBall(Integer.parseInt(temp[i]), Integer.parseInt(temp[i + 1]), 40, 10, Integer.parseInt(temp2[j])));
					break;
				default:
					System.out.println("Error adding bricks at index i= " + i + " j= " + j + " with temp,temp2,temp2.2 " + temp[i] + "," + temp2[j]);
					break;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exeption at brick setup");
			e.printStackTrace();
		}
	}

	public void paused(boolean f)
	{
		if(f)
		{
			pausedMessage = "Press ESC to continue";
			levelPassMess = "GAME PAUSED";
		}
		else
		{
			pausedMessage = "";
			levelPassMess = "";
		}
		repaint();
	}

	public void cleanUp()
	{
		waitMessage = "Click to continue";
		pausedMessage = "";
		levelPassMess = "";
		levelFailMess = "";
		score = "Score: 0";
		currentSetup = 0;
		currentColor = 0;
		scoreNum = 0;
		bricks.clear();
		bullets.clear();
		balls.clear();
		powerUpImgs.clear();
	}

	public void EndGame()
	{
		passed = true;
		try
		{
			Thread.sleep(50);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		currentSetup++;
		currentColor++;
		started = false;
	}

	public synchronized void checkCollisionBrick()
	{
		for(int i = 0; i < balls.size(); i++)
		{
			for(int j = 0; j < bricks.size(); j++)
			{
				if(balls.get(i).getX() + balls.get(i).getR() > bricks.get(j).getX() + 2
						&& balls.get(i).getX() < bricks.get(j).getX() + bricks.get(j).getDimX() - 2)
				{
					if(balls.get(i).getY() + balls.get(i).getR() == bricks.get(j).getY()
							&& balls.get(i).getY() + balls.get(i).getR() < bricks.get(j).getY() + bricks.get(j).getDimY()
							|| balls.get(i).getY() == bricks.get(j).getY() + bricks.get(j).getDimY() && balls.get(i).getY() > bricks.get(j).getY())
					{
						if(balls.get(i).getInterval() == 0)
						{
							if(fxEnabled)
							{
								brickHit1.start(volume);
							}
							balls.get(i).setVy(-balls.get(i).getVy());
							// bricks.get(j).executePowerUp(this);
							bricks.get(j).dropPowerUp(this);
							bricks.remove(j);
							scoreNum += 100;
							score = "Score: " + scoreNum;
							balls.get(i).setInterval(5);
							if(bricks.isEmpty())
							{
								EndGame();
							}
						}
					}
				}
				else if(balls.get(i).getY() > bricks.get(j).getY() && balls.get(i).getY() + balls.get(i).getR() < bricks.get(j).getY())
				{
					if(balls.get(i).getX() + balls.get(i).getR() == bricks.get(j).getX()
							|| balls.get(i).getX() == bricks.get(j).getX() + bricks.get(j).getDimX())
					{
						if(balls.get(i).getInterval() == 0)
						{
							if(fxEnabled)
							{
								victory1.start(volume);
							}
							username = "Sidehit";
							balls.get(i).setVx(-balls.get(i).getVx());
							bricks.remove(j);
							scoreNum += 100;
							score = "Score: " + scoreNum;
							balls.get(i).setInterval(7);
							if(bricks.isEmpty())
							{
								System.out.println("Level " + (currentSetup + 1) + " passed");
								EndGame();
							}
						}
					}
				}
				/*else if(balls.get(i).getX() == bricks.get(j).getX() + bricks.get(j).dimX)
				{
					if(balls.get(i).getY() + balls.get(i).getR() == bricks.get(j).getY() + bricks.get(j).getDimY()
							&& balls.get(i).getY() > bricks.get(j).getX())
					{
						if(balls.get(i).getInterval() == 0)
						{
							if(fxEnabled)
							{
								failure1.start();
							}
							username = "Sidehit";
							balls.get(i).setVx(-balls.get(i).getVx());
							bricks.remove(j);
							scoreNum += 100;
							score = "Score: " + scoreNum;
							balls.get(i).setInterval(7);
							if(bricks.isEmpty())
							{
								System.out.println("Level " + (currentSetup + 1) + " passed");
								passed = true;
								try
								{
									Thread.sleep(50);
								}
								catch(InterruptedException e)
								{
									e.printStackTrace();
								}
								currentSetup++;
							}
						}
					}
				}*/
			}
		}
		repaint();
	}

	public synchronized void checkCollisionPaddle()
	{
		for(int i = 0; i < balls.size(); i++)
		{
			// checking left and right bounds
			// if the ball hits left edge
			if(balls.get(i).getX() <= 0)
			{
				balls.get(i).setVx(-balls.get(i).getVx());
			}
			// if the ball hits right edge
			if(balls.get(i).getX() >= this.getWidth() - balls.get(i).getR())
			{
				balls.get(i).setVx(-balls.get(i).getVx());
			}

			// checking upper and lower bounds
			// if the ball hits the top
			if(balls.get(i).getY() <= 45)
			{
				balls.get(i).setVy(-balls.get(i).getVy());
			}
			// if the ball hits the bottom
			if(balls.get(i).getY() > this.getHeight() - balls.get(i).getR() - 20 || balls.get(i).getY() > pdl.getY() + pdl.getHeight())
			{
				balls.remove(i);
				life--;
				if(balls.isEmpty() && life < 1)
				{
					failed = true;
					System.out.println("Game Over");
					powerUpImgs.clear();
					aftermath = true;
					continue;
				}
				else
				{
					balls.clear();
					powerUpImgs.clear();
					Ball initial = new Ball(pdl.getX() + pdl.getWidth() / 2, pdl.getY() - 10, R);
					if(balls.isEmpty())
					{
						balls.add(initial);
					}
					else
					{
						balls.clear();
						balls.add(initial);
					}
					balls.get(0).setVx(0);
					balls.get(0).setVy(0);
					started = true;
					continue;
				}
			}

			// approaching to paddle from sides
			if(balls.get(i).getY() + (int) balls.get(i).getR() > pdl.getY())
			{
				if(balls.get(i).getY() + balls.get(i).getR() < pdl.getY() + pdl.getHeight())
				{
					// if hit the left side of the paddle
					if(balls.get(i).getX() + (int) balls.get(i).getR() / 2 == pdl.getX())
					{
						if(balls.get(i).getInterval() == 0)
						{
							// balls.get(i).setVx(-balls.get(i).getVx());
							balls.get(i).setInterval(15);
							if(fxEnabled)
							{
								paddleHit1.start(volume);
							}
						}

					}
					// hit the top of the paddle
					else if(balls.get(i).getX() + (int) balls.get(i).getR() / 2 > pdl.getX()
							&& balls.get(i).getX() + balls.get(i).getR() / 2 < pdl.getX() + pdl.getWidth())
					{
						if(balls.get(i).getInterval() == 0)
						{
							balls.get(i).setVy(-balls.get(i).getVy());
							balls.get(i).setInterval(15);
							if(fxEnabled)
							{
								paddleHit1.start(volume);
							}
						}
					}
					// if hit the right side of the paddle
					else if(balls.get(i).getX() + (int) balls.get(i).getR() / 2 == pdl.getX() + pdl.getWidth())
					{
						if(balls.get(i).getInterval() == 0)
						{
							// balls.get(i).setVx(-balls.get(i).getVx());
							balls.get(i).setInterval(15);
							if(fxEnabled)
							{
								paddleHit1.start(volume);
							}
						}
					}
				}
				else
				{
					if(balls.get(i).getVy() < 0)
					{
						if(balls.get(i).getInterval() == 0)
						{
							balls.get(i).setVy(-balls.get(i).getVy());
							balls.get(i).setInterval(11);
						}
					}
				}
			}
			// balls.get(i).Move();
		}

		repaint();
	}

	public void checkCollisionBullets()
	{
		if(!bullets.isEmpty())
		{
			for(int i = 0; i < bullets.size(); i++)
			{
				for(int j = 0; j < bricks.size(); j++)
				{
					try
					{
						if(bullets.get(i).getY() <= 40)
						{
							bullets.remove(i);
						}
						else
						{
							try
							{
								if(bullets.get(i).getY() <= bricks.get(j).getY() + bricks.get(j).getDimY() && bullets.get(i).getY() >= bricks.get(j).getY())
								{
									if(bullets.get(i).getX() > bricks.get(j).getX()
											&& bullets.get(i).getX() + 4 < bricks.get(j).getX() + bricks.get(j).getDimX())
									{
										if(fxEnabled)
										{
											brickHit1.start(volume);
										}
										// bricks.get(j).executePowerUp(this);
										bricks.get(j).dropPowerUp(this);
										bullets.remove(i);
										bricks.remove(j);
										scoreNum += 100;
										score = "Score: " + scoreNum;
										if(bricks.isEmpty())
										{
											EndGame();
										}
										continue;
									}
								}
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
						}
					}
					catch(IndexOutOfBoundsException e)
					{
					}
				}
			}
		}
	}

	public void checkCollisionPowerUp()
	{
		if(!powerUpImgs.isEmpty())
		{
			for(int i = 0; i < powerUpImgs.size(); i++)
			{
				if(powerUpImgs.get(i).getX() + 30 > pdl.getX() && powerUpImgs.get(i).getX() < pdl.getX() + pdl.getWidth())
				{
					if(powerUpImgs.get(i).getY() + 30 > pdl.getY())
					{
						powerUpImgs.get(i).getParent().executePowerUp(this);
						powerUpImgs.remove(i);
					}
				}
				else if(powerUpImgs.get(i).getY() > this.getHeight() - 100)
				{
					powerUpImgs.remove(i);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(failed)
		{
			failed = false;
			if(fxEnabled)
			{
				Random rnd = new Random();
				int i = rnd.nextInt(soundsFailure.size());
				soundsFailure.get(i).start(volume);
			}
			this.setCursor(Cursor.getDefaultCursor());
			waitMessage = "Click to continue";
			levelFailMess = "Game Over,  Final Score: " + score;
			levelPassMess = "";
			savedScore = scoreNum;

			Highscore h = new Highscore(username, savedScore);
			h.submitScore();

			try
			{
				stop.stop();
				start.start();
				repaint();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
		}
		else if(passed)
		{
			passed = false;
			if(fxEnabled)
			{
				Random rnd = new Random();
				int i = rnd.nextInt(soundsVictory.size());
				soundsVictory.get(i).start(volume);
			}
			initialize();
			waitMessage = "Click to continue";
			levelPassMess = "Level " + currentSetup + " passed!";
			levelFailMess = "";
		}
		else
		{
			run();
		}
	}

	public void run()
	{
		try
		{
			checkCollisionPaddle();
			checkCollisionBrick();
			checkCollisionBullets();
			checkCollisionPowerUp();
			moveBullet();
			movePowerUpImgs();
			moveBalls();
		}
		catch(Exception e)
		{
			System.out.println("Exception at running thread.");
		}
		adjustVolume();
		if(pwrupMessDuration > 0)
		{
			pwrupMessDuration--;
		}
		if(pwrupMessDuration == 0)
		{
			powerupMess = "";
		}
	}

	public int getPwrupMessDuration()
	{
		return pwrupMessDuration;
	}

	public void setPwrupMessDuration(int pwrupMessDuration)
	{
		this.pwrupMessDuration = pwrupMessDuration;
	}

	public void adjustVolume()
	{
		for(Sound snd : soundsFx)
		{
			snd.setVolume(volume);
		}
		for(Sound snd : soundsFailure)
		{
			snd.setVolume(volume);
		}
		for(Sound snd : soundsVictory)
		{
			snd.setVolume(volume);
		}
	}

	public JPanel getParentPanel()
	{
		return parentPanel;
	}

	public void setParentPanel(JPanel parentPanel)
	{
		this.parentPanel = parentPanel;
	}

	public boolean isFailed()
	{
		return failed;
	}

	public void setFailed(boolean failed)
	{
		this.failed = failed;
	}

	public boolean isAftermath()
	{
		return aftermath;
	}

	public void setAftermath(boolean aftermath)
	{
		this.aftermath = aftermath;
	}

	public String getWaitMessage()
	{
		return waitMessage;
	}

	public void setWaitMessage(String waitMessage)
	{
		this.waitMessage = waitMessage;
	}

	public String getPausedMessage()
	{
		return pausedMessage;
	}

	public void setPausedMessage(String pausedMessage)
	{
		this.pausedMessage = pausedMessage;
	}

	public String getLevelPassMess()
	{
		return levelPassMess;
	}

	public void setLevelPassMess(String levelPassMess)
	{
		this.levelPassMess = levelPassMess;
	}

	public String getLevelFailMess()
	{
		return levelFailMess;
	}

	public void setLevelFailMess(String levelFailMess)
	{
		this.levelFailMess = levelFailMess;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public int getCurrentSetup()
	{
		return currentSetup;
	}

	public void setCurrentSetup(int currentSetup)
	{
		this.currentSetup = currentSetup;
	}

	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
	}

	public void setTimers(Timer start, Timer stop)
	{
		this.start = start;
		this.stop = stop;
	}

	public String getSetup(int i)
	{
		return setup[i];
	}

	public void setSetup(String[] setup)
	{
		this.setup = setup;
	}

	public boolean isStarted()
	{
		return started;
	}

	public void setStarted(boolean started)
	{
		this.started = started;
	}

	public boolean isFxEnabled()
	{
		return fxEnabled;
	}

	public void setFxEnabled(boolean fxEnabled)
	{
		this.fxEnabled = fxEnabled;
	}

	public Ball getBalls(int i)
	{
		return balls.get(i);
	}

	public void setBalls(ArrayList<Ball> balls)
	{
		this.balls = balls;
	}

	public Brick getBricks(int i)
	{
		return bricks.get(i);
	}

	public void setBricks(ArrayList<Brick> bricks)
	{
		this.bricks = bricks;
	}

	public Paddle getPdl()
	{
		return pdl;
	}

	public void setPdl(Paddle pdl)
	{
		this.pdl = pdl;
	}

	public float getVolume()
	{
		return volume;
	}

	public void setVolume(float volume)
	{
		this.volume = volume;
	}

	public boolean isFocused()
	{
		return focused;
	}

	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}

	public int getLife()
	{
		return life;
	}

	public void setLife(int life)
	{
		this.life = life;
	}

	public String getPowerupMess()
	{
		return powerupMess;
	}

	public void setPowerupMess(String powerupMess)
	{
		this.powerupMess = powerupMess;
	}
}
