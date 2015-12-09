import java.awt.BorderLayout;

public class Program
{
	public static void main(String[] args)
	{
		MainWindow game = new MainWindow();
		game.setSize(410, 610);
		game.setResizable(false);
		game.setUndecorated(true);
		game.setLocationRelativeTo(null);
		game.setVisible(true);
		game.setLayout(new BorderLayout(10, 10));
		// game.setAlwaysOnTop(true);
	}
}
