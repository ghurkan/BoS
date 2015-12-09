import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Highscore implements Serializable
{
	private String name;
	private long score;
	private ArrayList<Highscore> highscores = new ArrayList<Highscore>();
	private boolean hasObject = true;
	private HighscoreComparator hc;

	public Highscore(String name, long score)
	{
		this.name = name;
		this.score = score;
	}

	public void submitScore()
	{

		getScores();
		insertScore();

		ObjectOutputStream output = null;
		try
		{
			output = new ObjectOutputStream(new FileOutputStream("Highscores.dat"));
		}
		catch(FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}

		try
		{
			for(int i = 0; i < highscores.size(); i++)
			{
				output.writeObject(highscores.get(i));
			}
			output.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private void getScores()
	{

		ObjectInputStream input = null;

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
			e.printStackTrace();
			System.out.println("eof exception");
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
			catch(EOFException e)
			{
				System.out.println("eof");
				hasObject = false;
			}
			catch(IOException e)
			{
				e.printStackTrace();
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
	}

	private void insertScore()
	{
		Highscore temp = new Highscore(name, score);
		int size = highscores.size();
		boolean added = false;

		for(int i = 0; i < size; i++)
		{
			if(highscores.get(i).getScore() < temp.getScore())
			{
				highscores.add(i, temp);
				added = true;
				break;
			}
		}
		if(!added)
		{
			highscores.add(temp);
		}
		if(highscores.size() > 10)
		{
			highscores.remove(11);
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getScore()
	{
		return score;
	}

	public void setScore(long score)
	{
		this.score = score;
	}
}
