import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.SwingUtilities;

public class Sound
{
	URL filePath;
	Clip clip;

	public Sound(URL file)
	{
		this.filePath = file;
		Clip tempClip = initializeClip(filePath);
		this.clip = tempClip;
	}

	public Clip initializeClip(URL file)
	{
		Clip clip = null;
		AudioInputStream ais = null;

		try
		{
			clip = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(file);
			clip.open(ais);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
			}
		});

		return clip;
	}

	public void start(float volume)
	{
		clip = initializeClip(filePath);
		setVolume(volume);
		clip.start();
	}

	public void stop()
	{
		// clip.stop();
		clip.close();
	}

	public void restart(float volume)
	{
		clip.close();
		clip = initializeClip(filePath);
		setVolume(volume);
		clip.start();
	}

	public void setVolume(float vol)
	{
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		try
		{
			gainControl.setValue(convert(gainControl, vol));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	private static float convert(FloatControl control, float level)
	{
		float min = control.getMinimum() / 2;
		float max = control.getMaximum();

		float range;
		float breakpoint;
		float meanLevel;

		if(min < 0)
		{
			min = min * -1;
		}
		if(max < 0)
		{
			max = max * -1;
		}
		range = min + max;
		breakpoint = min * 100 / range;
		meanLevel = level * range / 100;

		if(meanLevel > min)
		{
			return meanLevel - min;
		}
		else
		{
			return meanLevel - range + max;
		}
	}
}
