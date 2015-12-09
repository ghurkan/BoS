import java.util.Comparator;

public class HighscoreComparator implements Comparator<Highscore>
{
	@Override
	public int compare(Highscore arg0, Highscore arg1)
	{
		if(arg0.getScore() < arg1.getScore())
		{
			return -1;
		}
		else if(arg0.getScore() == arg1.getScore())
		{
			return 0;
		}
		else if(arg0.getScore() > arg1.getScore())
		{
			return 1;
		}
		return 0;
	}

}
