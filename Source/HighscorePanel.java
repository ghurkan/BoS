import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighScorePanel extends JPanel
{
	JLabel header;

	JLabel name0;
	JLabel name1;
	JLabel name2;
	JLabel name3;
	JLabel name4;
	JLabel name5;
	JLabel name6;
	JLabel name7;
	JLabel name8;
	JLabel name9;

	JLabel score0;
	JLabel score1;
	JLabel score2;
	JLabel score3;
	JLabel score4;
	JLabel score5;
	JLabel score6;
	JLabel score7;
	JLabel score8;
	JLabel score9;

	JPanel pnlHeader;
	JPanel pnlScores;

	Font fnt = new Font("Papyrus", Font.PLAIN, 18);
	Font fntSmall = new Font("Papyrus", Font.BOLD, 14);
	ImageIcon imgBtnBack = new ImageIcon(getClass().getResource("BackButton.png"));
	BufferedImage bg;
	ArrayList<Highscore> highscores = new ArrayList<Highscore>();
	ArrayList<JLabel> names = new ArrayList<JLabel>();
	ArrayList<JLabel> scores = new ArrayList<JLabel>();
	ObjectInputStream input;
	boolean hasObject = true;

	public HighScorePanel()
	{
		// setLayout(new BorderLayout());
		setLayout(null);
		try
		{
			bg = ImageIO.read(getClass().getResource("HighScore.png"));
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		header = new JLabel("Highscores");
		header.setFont(fntSmall);
		header.setForeground(Color.yellow);
		header.setBounds(0, 0, 80, 30);

		pnlHeader = new JPanel();
		pnlHeader.setLayout(new FlowLayout());
		pnlHeader.setBounds(65, 50, 170, 30);
		pnlHeader.setBackground(new Color(0, 0, 0, .0f));

		pnlScores = new JPanel();
		pnlScores.setLayout(new GridLayout(10, 2));
		pnlScores.setBounds(75, 80, 170, 220);
		pnlScores.setBackground(new Color(0, 0, 0, .0f));

		name0 = new JLabel("");
		name0.setForeground(Color.yellow);
		name1 = new JLabel("");
		name1.setForeground(Color.yellow);
		name2 = new JLabel("");
		name2.setForeground(Color.yellow);
		name3 = new JLabel("");
		name3.setForeground(Color.yellow);
		name4 = new JLabel("");
		name4.setForeground(Color.yellow);
		name5 = new JLabel("");
		name5.setForeground(Color.yellow);
		name6 = new JLabel("");
		name6.setForeground(Color.yellow);
		name7 = new JLabel("");
		name7.setForeground(Color.yellow);
		name8 = new JLabel("");
		name8.setForeground(Color.yellow);
		name9 = new JLabel("");
		name9.setForeground(Color.yellow);

		names.add(name0);
		names.add(name1);
		names.add(name2);
		names.add(name3);
		names.add(name4);
		names.add(name5);
		names.add(name6);
		names.add(name7);
		names.add(name8);
		names.add(name9);

		score0 = new JLabel("");
		score0.setForeground(Color.yellow);
		score1 = new JLabel("");
		score1.setForeground(Color.yellow);
		score2 = new JLabel("");
		score2.setForeground(Color.yellow);
		score3 = new JLabel("");
		score3.setForeground(Color.yellow);
		score4 = new JLabel("");
		score4.setForeground(Color.yellow);
		score5 = new JLabel("");
		score5.setForeground(Color.yellow);
		score6 = new JLabel("");
		score6.setForeground(Color.yellow);
		score7 = new JLabel("");
		score7.setForeground(Color.yellow);
		score8 = new JLabel("");
		score8.setForeground(Color.yellow);
		score9 = new JLabel("");
		score9.setForeground(Color.yellow);

		scores.add(score0);
		scores.add(score1);
		scores.add(score2);
		scores.add(score3);
		scores.add(score4);
		scores.add(score5);
		scores.add(score6);
		scores.add(score7);
		scores.add(score8);
		scores.add(score9);

		pnlHeader.add(header);
		pnlScores.add(name0);
		pnlScores.add(score0);
		pnlScores.add(name1);
		pnlScores.add(score1);
		pnlScores.add(name2);
		pnlScores.add(score2);
		pnlScores.add(name3);
		pnlScores.add(score3);
		pnlScores.add(name4);
		pnlScores.add(score4);
		pnlScores.add(name5);
		pnlScores.add(score5);
		pnlScores.add(name6);
		pnlScores.add(score6);
		pnlScores.add(name7);
		pnlScores.add(score7);
		pnlScores.add(name8);
		pnlScores.add(score8);
		pnlScores.add(name9);
		pnlScores.add(score9);

		add(pnlHeader);
		add(pnlScores);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, null);
	}

	public void getScores()
	{
		try
		{
			input = new ObjectInputStream(new FileInputStream("Highscores.dat"));
		}
		catch(FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch(EOFException e)
		{
			System.out.println("eof");
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}

		while(hasObject)
		{
			try
			{
				highscores.add((Highscore) input.readObject());
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			catch(NullPointerException e)
			{
				System.out.println("Object not found");
				hasObject = false;
			}
			catch(EOFException e)
			{
				hasObject = false;
			}
			catch(IOException e)
			{
				System.out.println("IO exception");
			}
		}

		try
		{
			input.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		for(int i = 0; i < highscores.size(); i++)
		{
			names.get(i).setText(highscores.get(i).getName());
			scores.get(i).setText("" + highscores.get(i).getScore());
		}
	}
}
