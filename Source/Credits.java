import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Credits extends JPanel implements ActionListener
{
	BufferedImage bgImg;
	BufferedImage bg;
	Font fnt = new Font("Papyrus", Font.PLAIN, 18);
	Font fntSmall = new Font("Papyrus", Font.BOLD, 14);

	public Credits()
	{
		try
		{
			bgImg = ImageIO.read(getClass().getResource("bg3.png"));
			bg = ImageIO.read(getClass().getResource("HighScore.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(bgImg, 0, 0, null);
		g.drawImage(bg, 50, 100, null);
		g.setColor(Color.yellow);
		g.setFont(fnt);
		g.drawString("Credits", 170, 200);
		g.setFont(fntSmall);
		g.drawString("Made by,", 165, 230);
		g.drawString("Eren Atas", 160, 250);
		g.drawString("Nazım Gürkan Demir", 130, 270);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
	}

}
