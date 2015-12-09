import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

public class MainWindow extends JFrame implements ActionListener, MouseInputListener, KeyListener, ChangeListener
{
	JPanel frame;
	JPanel mainPanel;
	JPanel pnlBtn;
	JPanel pnlNickname;
	JPanel pnlMulti;
	JPanel pnlSettings;

	JLabel lblName;
	JLabel lblVolumeMusic;
	JLabel lblVolumeFx;
	JLabel lblNameMulti;
	JTextField txtName;
	JTextField txtNameMulti;
	JSlider slideVolumeMusic;
	JSlider slideVolumeFx;
	JCheckBox chckMusic;
	JCheckBox chckFx;

	JButton btnSingle;
	JButton btnMulti;
	JButton btnHighscores;
	JButton btnSettings;
	JButton btnCredits;
	JButton btnExit;
	JButton btnAbandonS;
	JButton btnAbandonM;
	JButton btnStart;
	JButton btnJoin;
	JButton btnCreate;
	JButton btnBackSingle;
	JButton btnBackMulti;
	JButton btnBackSettings;
	JButton btnBackHighscores;
	JButton btnBackCredits;

	WelcomeScreen splash;
	SinglePlayer windowS;
	MultiPlayer windowM;
	HighScorePanel pnlHighscores;
	Credits credits;

	Server server;
	Client client;

	boolean pausedS = false;
	boolean pausedM = false;
	boolean fxEnabled = true;
	boolean musicEnabled = true;
	Timer tSplash;
	Timer tSingle;
	Timer tMulti;
	Timer tCredits;
	String username = "";
	float volume = 100;

	ImageIcon imgBtnSingle = new ImageIcon(getClass().getResource("SinglePlayerButton.png"));
	ImageIcon imgBtnMulti = new ImageIcon(getClass().getResource("MultiPlayerButton.png"));
	ImageIcon imgBtnHighscores = new ImageIcon(getClass().getResource("HighscoresButton.png"));
	ImageIcon imgBtnSettings = new ImageIcon(getClass().getResource("SettingsButton.png"));
	ImageIcon imgBtnCredits = new ImageIcon(getClass().getResource("CreditsButton.png"));
	ImageIcon imgBtnExit = new ImageIcon(getClass().getResource("ExitButton.png"));
	ImageIcon imgBtnAbandon = new ImageIcon(getClass().getResource("AbandonButton.png"));
	ImageIcon imgBtnBack = new ImageIcon(getClass().getResource("BackButton.png"));
	ImageIcon imgBtnJoin = new ImageIcon(getClass().getResource("JoinButton.png"));
	ImageIcon imgBtnCreate = new ImageIcon(getClass().getResource("CreateButton.png"));
	ImageIcon imgBtnStart = new ImageIcon(getClass().getResource("StartButton.png"));

	Sound backgroundMusic;
	Sound backgroundMusic2;
	Sound nope;

	private Sound BoS;

	Font fnt = new Font("Papyrus", Font.PLAIN, 18);
	Font fntSmall = new Font("Papyrus", Font.BOLD, 14);

	public MainWindow()
	{
		frame = new JPanel();
		frame.setBounds(0, 0, 400, 600);
		frame.setLayout(null);
		frame.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(2, 3, frame.getWidth(), frame.getHeight());

		splash = new WelcomeScreen();
		splash.setBounds(5, 5, frame.getWidth(), frame.getHeight());
		splash.setDoubleBuffered(true);

		windowS = new SinglePlayer(splash);
		windowS.setBounds(5, 5, frame.getWidth(), frame.getHeight());
		windowS.setLayout(null);
		windowS.setCursor(windowS.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));

		windowM = new MultiPlayer(splash);
		windowM.setBounds(5, 5, frame.getWidth(), frame.getHeight());
		windowM.setLayout(null);
		windowM.setCursor(windowS.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));

		credits = new Credits();
		credits.setBounds(5, 5, frame.getWidth(), frame.getHeight());
		credits.setLayout(null);

		server = new Server();
		client = new Client();

		tSplash = new Timer(10, splash);
		tSingle = new Timer(10, windowS);
		tMulti = new Timer(10, windowM);
		tCredits = new Timer(10, credits);

		backgroundMusic = new Sound(getClass().getResource("roam.wav"));
		backgroundMusic2 = new Sound(getClass().getResource("sandstorm.wav"));
		nope = new Sound(getClass().getResource("Nope.wav"));
		BoS = new Sound(getClass().getResource("BoS.wav"));

		btnAbandonS = new JButton(imgBtnAbandon);
		btnAbandonS.addActionListener(this);
		btnAbandonS.setBounds(110, 400, 180, 28);
		btnAbandonS.setBackground(new Color(0, 0, 0, .0f));
		btnAbandonS.setPreferredSize(new Dimension(180, 28));
		btnAbandonS.setVisible(false);

		btnAbandonM = new JButton(imgBtnAbandon);
		btnAbandonM.addActionListener(this);
		btnAbandonM.setBounds(110, 400, 180, 28);
		btnAbandonM.setBackground(new Color(0, 0, 0, .0f));
		btnAbandonM.setPreferredSize(new Dimension(180, 28));
		btnAbandonM.setVisible(false);

		btnSingle = new JButton(imgBtnSingle);
		btnSingle.setBorder(BorderFactory.createRaisedBevelBorder());
		btnSingle.setBackground(new Color(0, 0, 0, .0f));
		btnSingle.setPreferredSize(new Dimension(180, 28));
		btnSingle.setBounds(10, 0, 180, 28);

		btnMulti = new JButton(imgBtnMulti);
		btnMulti.setBorder(BorderFactory.createRaisedBevelBorder());
		btnMulti.setBounds(10, 40, 180, 28);
		btnMulti.setBackground(new Color(0, 0, 0, .0f));

		btnHighscores = new JButton(imgBtnHighscores);
		btnHighscores.setBorder(BorderFactory.createRaisedBevelBorder());
		btnHighscores.setPreferredSize(new Dimension(180, 28));
		btnHighscores.setBounds(10, 80, 180, 28);
		btnHighscores.setBackground(new Color(0, 0, 0, .0f));

		btnSettings = new JButton(imgBtnSettings);
		btnSettings.setBorder(BorderFactory.createRaisedBevelBorder());
		btnSettings.setBounds(10, 120, 180, 28);
		btnSettings.setBackground(new Color(0, 0, 0, .0f));

		btnCredits = new JButton(imgBtnCredits);
		btnCredits.setBorder(BorderFactory.createRaisedBevelBorder());
		btnCredits.setBounds(10, 160, 180, 28);
		btnCredits.setBackground(new Color(0, 0, 0, .0f));

		btnExit = new JButton(imgBtnExit);
		btnExit.setBorder(BorderFactory.createRaisedBevelBorder());
		btnExit.setBounds(10, 200, 180, 28);
		btnExit.setBackground(new Color(0, 0, 0, .0f));

		btnJoin = new JButton(imgBtnJoin);
		btnJoin.setBorder(BorderFactory.createRaisedBevelBorder());
		btnJoin.setBounds(10, 10, 180, 28);
		btnJoin.setBackground(new Color(0, 0, 0, .0f));
		btnCreate = new JButton(imgBtnCreate);
		btnCreate.setBorder(BorderFactory.createRaisedBevelBorder());
		btnCreate.setBounds(10, 60, 180, 28);
		btnCreate.setBackground(new Color(0, 0, 0, .0f));
		btnBackMulti = new JButton(imgBtnBack);
		btnBackMulti.setBorder(BorderFactory.createRaisedBevelBorder());
		btnBackMulti.setBounds(10, 120, 180, 28);
		btnBackMulti.setBackground(new Color(0, 0, 0, .0f));

		lblName = new JLabel("  Enter your name:");
		lblName.setBounds(30, 15, 140, 30);
		lblName.setBorder(BorderFactory.createRaisedBevelBorder());
		lblName.setFont(fntSmall);
		txtName = new JTextField("Name...");
		txtName.setBounds(30, 50, 140, 30);
		txtName.setFont(fntSmall);
		btnStart = new JButton(imgBtnStart);
		btnStart.setBounds(10, 90, 180, 28);
		btnStart.setBackground(new Color(0, 0, 0, .0f));
		btnBackSingle = new JButton(imgBtnBack);
		btnBackSingle.setBounds(10, 130, 180, 28);
		btnBackSingle.setBackground(new Color(0, 0, 0, .0f));

		btnBackHighscores = new JButton(imgBtnBack);
		btnBackHighscores.setBounds(60, 320, 180, 28);
		btnBackHighscores.addActionListener(this);

		btnBackSettings = new JButton(imgBtnBack);
		btnBackSettings.setBounds(65, 210, 180, 28);
		btnBackSettings.setBackground(new Color(0, 0, 0, .0f));
		chckMusic = new JCheckBox("Enable Music");
		chckMusic.setBounds(10, 10, 180, 20);
		chckMusic.setSelected(true);
		chckMusic.setFont(fntSmall);
		lblVolumeMusic = new JLabel("Volume:");
		lblVolumeMusic.setBounds(20, 40, 70, 20);
		lblVolumeMusic.setFont(fntSmall);
		slideVolumeMusic = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 100);
		slideVolumeMusic.setMajorTickSpacing(10);
		slideVolumeMusic.setPaintTicks(true);
		slideVolumeMusic.setBounds(100, 40, 180, 30);
		chckFx = new JCheckBox("Enable FX Sounds");
		chckFx.setBounds(10, 80, 180, 20);
		chckFx.setSelected(true);
		chckFx.setFont(fntSmall);
		lblVolumeFx = new JLabel("Enable FX sounds");
		lblVolumeFx.setBounds(20, 110, 80, 20);
		lblVolumeFx.setFont(fntSmall);
		slideVolumeFx = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 100);
		slideVolumeFx.setMajorTickSpacing(10);
		slideVolumeFx.setPaintTicks(true);
		slideVolumeFx.setBounds(100, 110, 180, 30);
		lblNameMulti = new JLabel("Online Username:");
		lblNameMulti.setBounds(80, 150, 150, 20);
		lblNameMulti.setFont(fntSmall);
		txtNameMulti = new JTextField("Name...");
		txtNameMulti.setBounds(80, 180, 150, 20);
		txtNameMulti.setFont(fntSmall);

		btnBackCredits = new JButton(imgBtnBack);
		btnBackCredits.setBounds(115, 550, 180, 28);

		btnSingle.addActionListener(this);
		btnMulti.addActionListener(this);
		btnHighscores.addActionListener(this);
		btnSettings.addActionListener(this);
		btnCredits.addActionListener(this);
		btnExit.addActionListener(this);

		btnAbandonS.addActionListener(this);
		btnAbandonM.addActionListener(this);
		txtName.addMouseListener(this);
		btnStart.addActionListener(this);
		btnBackSingle.addActionListener(this);

		btnJoin.addActionListener(this);
		btnCreate.addActionListener(this);
		btnBackMulti.addActionListener(this);

		btnBackSettings.addActionListener(this);
		chckMusic.addActionListener(this);
		chckFx.addActionListener(this);
		slideVolumeMusic.addChangeListener(this);
		slideVolumeFx.addChangeListener(this);
		txtNameMulti.addMouseListener(this);

		btnBackCredits.addActionListener(this);

		pnlBtn = new JPanel();
		pnlBtn.setLayout(null);
		pnlBtn.setBounds(100, 300, 280, 280);
		pnlBtn.setBorder(BorderFactory.createRaisedBevelBorder());
		pnlBtn.setBackground(new Color(00, 00, 00, .0f));

		pnlNickname = new JPanel();
		pnlNickname.setBounds(100, 340, 200, 180);
		pnlNickname.setBorder(BorderFactory.createRaisedBevelBorder());
		pnlNickname.setLayout(null);
		pnlNickname.setVisible(false);

		pnlMulti = new JPanel();
		pnlMulti.setLayout(null);
		pnlMulti.setBounds(100, 350, 200, 200);
		pnlMulti.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		pnlMulti.setBackground(new Color(00, 00, 00, .0f));
		pnlMulti.setVisible(false);

		pnlHighscores = new HighScorePanel();
		pnlHighscores.setBounds(50, 200, 300, 350);
		pnlHighscores.setBackground(new Color(00, 00, 00, .0f));
		pnlHighscores.setVisible(false);

		pnlSettings = new JPanel();
		pnlSettings.setLayout(null);
		pnlSettings.setBounds(50, 300, 300, 250);
		pnlSettings.setBorder(BorderFactory.createRaisedBevelBorder());
		pnlSettings.setVisible(false);

		pnlBtn.add(btnSingle);
		pnlBtn.add(btnMulti);
		pnlBtn.add(btnHighscores);
		pnlBtn.add(btnSettings);
		pnlBtn.add(btnCredits);
		pnlBtn.add(btnExit);

		pnlMulti.add(btnJoin);
		pnlMulti.add(btnCreate);
		pnlMulti.add(btnBackMulti);

		pnlNickname.add(lblName);
		pnlNickname.add(txtName);
		pnlNickname.add(btnStart);
		pnlNickname.add(btnBackSingle);

		pnlHighscores.add(btnBackHighscores);

		pnlSettings.add(chckMusic);
		pnlSettings.add(lblVolumeMusic);
		pnlSettings.add(slideVolumeMusic);
		pnlSettings.add(chckFx);
		pnlSettings.add(lblVolumeFx);
		pnlSettings.add(slideVolumeFx);
		pnlSettings.add(btnBackSettings);
		pnlSettings.add(lblNameMulti);
		pnlSettings.add(txtNameMulti);

		windowS.addMouseMotionListener(this);
		windowS.addMouseListener(this);
		windowS.addKeyListener(this);
		windowS.setFocusable(true);
		windowM.addMouseMotionListener(this);
		windowM.addMouseListener(this);
		windowM.addKeyListener(this);
		windowM.setFocusable(true);
		frame.addMouseListener(this);
		frame.addKeyListener(this);

		windowS.add(btnAbandonS);
		windowM.add(btnAbandonM);

		splash.add(pnlBtn);
		splash.add(pnlNickname);
		splash.add(pnlMulti);
		splash.add(pnlHighscores);
		splash.add(pnlSettings);

		credits.add(btnBackCredits);

		mainPanel.add(windowS);
		mainPanel.add(splash);
		mainPanel.add(windowM);
		mainPanel.add(credits);
		frame.add(mainPanel);
		this.add(frame, BorderLayout.CENTER);

		splash.setVisible(true);
		windowS.setVisible(false);
		windowM.setVisible(false);
		credits.setVisible(false);
		mainPanel.setVisible(true);
		frame.setVisible(true);

		backgroundMusic.start(volume);
		// backgroundMusic2.start(volume);
		BoS.start(volume);

		tSplash.start();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnSingle)
		{
			pnlBtn.setVisible(false);
			pnlNickname.setVisible(true);
		}
		if(e.getSource() == btnExit)
		{
			System.exit(0);
		}
		if(e.getSource() == btnMulti)
		{
			pnlBtn.setVisible(false);
			pnlMulti.setVisible(true);
			frame.repaint();
		}
		if(e.getSource() == btnStart)
		{
			if(!txtName.getText().isEmpty())
			{
				username = txtName.getText();
				windowS.setUsername(username);
				txtName.setText("Name...");

				windowS.setVisible(true);
				splash.setVisible(false);
				pnlNickname.setVisible(false);
				pnlBtn.setVisible(true);
				windowS.setTimers(tSplash, tSingle);
				windowS.initialize();
				tSingle.start();
				tSplash.stop();
				frame.repaint();
			}
			else
			{
				nope.start(volume);
			}
		}
		if(e.getSource() == btnAbandonS)
		{
			pausedS = false;
			splash.setVisible(true);
			windowS.setVisible(false);
			btnAbandonS.setVisible(false);
			frame.repaint();
			tSplash.start();
			splash.repaint();
			windowS.cleanUp();

			if(musicEnabled)
			{
				backgroundMusic.stop();
				backgroundMusic.start(volume);
			}
			btnAbandonS.setVisible(false);
		}
		if(e.getSource() == btnAbandonM)
		{
			pausedM = false;
			splash.setVisible(true);
			windowM.setVisible(false);
			btnAbandonM.setVisible(false);
			frame.repaint();
			tSplash.start();
			splash.repaint();
			windowM.cleanUp();

			if(musicEnabled)
			{
				backgroundMusic.stop();
				backgroundMusic.start(volume);
			}
			btnAbandonM.setVisible(false);
		}
		if(e.getSource() == btnBackMulti)
		{
			if(!txtNameMulti.getText().isEmpty())
			{
				pnlMulti.setVisible(false);
				pnlBtn.setVisible(true);
				frame.repaint();
			}
			else
			{
				nope.start(volume);
			}
		}
		if(e.getSource() == btnBackSingle)
		{
			pnlNickname.setVisible(false);
			pnlBtn.setVisible(true);
			frame.repaint();
		}
		if(e.getSource() == btnSettings)
		{
			pnlBtn.setVisible(false);
			pnlSettings.setVisible(true);
			frame.repaint();
		}
		if(e.getSource() == btnBackSettings)
		{
			pnlSettings.setVisible(false);
			pnlBtn.setVisible(true);
			frame.repaint();
		}
		if(e.getSource() == chckMusic)
		{
			if(chckMusic.isSelected())
			{
				musicEnabled = true;
				backgroundMusic.start(volume);
			}
			else
			{
				musicEnabled = false;
				backgroundMusic.stop();
			}
		}
		if(e.getSource() == chckFx)
		{
			if(chckFx.isSelected())
			{
				windowS.setFxEnabled(true);
			}
			else if(!chckFx.isSelected())
			{
				windowS.setFxEnabled(false);
			}

		}
		if(e.getSource() == btnJoin)
		{
			// client.setVisible(true);
		}
		if(e.getSource() == btnCreate)
		{
			if(!txtNameMulti.getText().isEmpty())
			{
				username = txtNameMulti.getText();
				windowM.setUsername(username);
				txtNameMulti.setText("Name...");

				windowM.setVisible(true);
				splash.setVisible(false);
				pnlNickname.setVisible(false);
				pnlBtn.setVisible(true);
				pnlMulti.setVisible(false);
				// server.setVisible(true);
				windowM.setTimers(tSplash, tMulti);
				windowM.initialize();
				tMulti.start();
				tSplash.stop();
				frame.repaint();
			}
			else
			{
				nope.start(volume);
			}
		}
		if(e.getSource() == btnHighscores)
		{
			pnlBtn.setVisible(false);
			pnlHighscores.getScores();
			pnlHighscores.repaint();
			pnlHighscores.setVisible(true);
			frame.repaint();
		}
		if(e.getSource() == btnBackHighscores)
		{
			pnlHighscores.setVisible(false);
			pnlBtn.setVisible(true);
			frame.repaint();
		}
		if(e.getSource() == btnCredits)
		{
			credits.setVisible(true);
			splash.setVisible(false);
			credits.repaint();
			tSplash.stop();
			tCredits.start();
		}
		if(e.getSource() == btnBackCredits)
		{
			tSplash.start();
			tCredits.stop();
			splash.setVisible(true);
			credits.setVisible(false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getSource() == frame)
		{
		}
		if(e.getSource() == windowS)
		{
			while(windowS.isStarted())
			{
				windowS.setWaitMessage("");
				windowS.setLevelPassMess("");
				if(e.getX() < frame.getWidth() / 2)
				{
					windowS.getBalls(0).setVx(-2);
				}
				else
				{
					windowS.getBalls(0).setVx(2);
				}
				windowS.getBalls(0).setVy(-2);
				windowS.setStarted(false);
			}
			while(windowS.isAftermath())
			{
				splash.setVisible(true);
				windowS.setVisible(false);
				frame.repaint();
				tSplash.start();
				splash.repaint();
				windowS.cleanUp();
				windowS.setAftermath(false);

				System.out.println("inside aftermath key pressed");
			}
		}
		if(e.getSource() == windowM)
		{
			while(windowM.isStarted())
			{
				windowM.setWaitMessage("");
				windowM.setLevelPassMess("");
				if(e.getX() < frame.getWidth() / 2)
				{
					windowM.getBalls(0).setVx(-2);
				}
				else
				{
					windowM.getBalls(0).setVx(2);
				}
				windowM.getBalls(0).setVy(-2);
				windowM.setStarted(false);
			}
			while(windowM.isAftermath())
			{
				splash.setVisible(true);
				windowM.setVisible(false);
				frame.repaint();
				tSplash.start();
				splash.repaint();
				windowM.cleanUp();
				windowM.setAftermath(false);

				System.out.println("inside aftermath key pressed");
			}
		}
		if(e.getSource() == txtName)
		{
			txtName.setText("");
		}
		if(e.getSource() == txtNameMulti)
		{
			txtNameMulti.setText("");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		if(windowS.isVisible())
		{
			if(!pausedS)
			{
				int temp = e.getX() - windowS.getPdl().getWidth() / 2;
				if(temp < 0)
				{
					windowS.getPdl().setX(0);
				}
				else if(temp + windowS.getPdl().getWidth() > frame.getWidth() - 15)
				{
					windowS.getPdl().setX(frame.getWidth() - windowS.getPdl().getWidth() - 14);

				}
				else
				{
					windowS.getPdl().setX(e.getX() - windowS.getPdl().getWidth() / 2);
				}

				if(!windowS.balls.isEmpty())
				{
					if(windowS.isStarted())
					{
						windowS.getBalls(0).setX(windowS.getPdl().getX() + windowS.getPdl().getX() / windowS.getPdl().getWidth());
					}
				}
			}
			windowS.repaint();
		}
		if(windowM.isVisible() && tMulti.isRunning())
		{
			int temp = e.getX() - windowM.getPdl(0).getWidth() / 2;
			if(temp < 0)
			{
				windowM.getPdl(0).setX(0);
			}
			else if(temp + windowM.getPdl(0).getWidth() > frame.getWidth() - 15)
			{
				windowM.getPdl(0).setX(frame.getWidth() - windowM.getPdl(0).getWidth() - 16);

			}
			else
			{
				windowM.getPdl(0).setX(e.getX() - windowM.getPdl(0).getWidth() / 2);
			}

			if(!windowM.balls.isEmpty())
			{
				if(windowM.isStarted())
				{
					windowM.getBalls(0).setX(windowM.getPdl(0).getX() + windowM.getPdl(0).getX() / 10);
				}
			}
			windowM.repaint();
		}

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE)
		{
			if(tSingle.isRunning())
			{
				pausedS = true;
				tSingle.stop();
				windowS.paused(pausedS);
				backgroundMusic.stop();
				btnAbandonS.setVisible(true);
				windowS.setCursor(Cursor.getDefaultCursor());
			}
			else if(!tSingle.isRunning() && windowS.isVisible())
			{
				pausedS = false;
				tSingle.start();
				windowS.paused(pausedS);
				btnAbandonS.setVisible(false);
				windowS.setCursor(windowS.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
				if(musicEnabled)
				{
					backgroundMusic.start(volume);
				}
			}
			else if(tMulti.isRunning())
			{
				pausedM = true;
				tMulti.stop();
				windowM.paused(pausedM);
				backgroundMusic.stop();
				btnAbandonM.setVisible(true);
				windowM.setCursor(Cursor.getDefaultCursor());
			}
			else if(!tMulti.isRunning() && windowM.isVisible())
			{
				pausedM = false;
				tMulti.start();
				windowM.paused(pausedM);
				btnAbandonM.setVisible(false);
				windowM.setCursor(windowM.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
				if(musicEnabled)
				{
					backgroundMusic.restart(volume);
				}
			}
		}
		if(key == KeyEvent.VK_LEFT)
		{
			windowM.getPdl(1).setX(windowM.getPdl(1).getX() - 10);
		}
		if(key == KeyEvent.VK_RIGHT)
		{
			windowM.getPdl(1).setX(windowM.getPdl(1).getX() + 10);
		}
		if(key == KeyEvent.VK_SPACE)
		{
			windowS.fireBullet();
		}
		if(key==KeyEvent.VK_C)
		{
			if(tSingle.isRunning())
			{
				windowS.setLife(windowS.getLife()+1);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if(e.getSource() == slideVolumeMusic)
		{
			volume = slideVolumeMusic.getValue();
		}
		if(e.getSource() == slideVolumeFx)
		{
			windowS.setVolume((float) slideVolumeFx.getValue());
			windowS.adjustVolume();
		}
		backgroundMusic.setVolume(volume);
	}
}
