import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.DefaultCaret;

public class Client extends JFrame implements ActionListener, MouseListener, Runnable
{
	JTextArea displayArea;
	JTextField txtEnter;
	JTextField txtAddress;
	JButton btnConnect;
	JButton btnSend;
	JFrame frame;
	JPanel pnlNorth;
	ExecutorService exe;
	Timer t;
	long counter = 0;

	ObjectInputStream input;
	ObjectOutputStream output;
	Socket client;

	String messageIn = "";
	String messageOut = "hello server";
	String host = "";

	public Client()
	{
		frame = new JFrame("Client");
		frame.setSize(400, 400);
		frame.setResizable(false);
		// frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout(5, 5));

		btnConnect = new JButton("Connect");
		btnSend = new JButton("Send");
		displayArea = new JTextArea();
		DefaultCaret caret = (DefaultCaret) displayArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		txtEnter = new JTextField("Enter message...");
		txtEnter.setPreferredSize(new Dimension(100, 20));
		txtAddress = new JTextField("Enter IP address");
		txtAddress.setPreferredSize(new Dimension(100, 20));

		exe = Executors.newCachedThreadPool();

		pnlNorth = new JPanel();
		pnlNorth.setLayout(new FlowLayout());

		txtEnter.addMouseListener(this);
		txtAddress.addMouseListener(this);
		btnConnect.addActionListener(this);
		btnSend.addActionListener(this);

		t = new Timer(10, this);

		pnlNorth.add(txtAddress);
		pnlNorth.add(btnConnect);
		pnlNorth.add(txtEnter);
		pnlNorth.add(btnSend);

		frame.add(pnlNorth, BorderLayout.NORTH);
		frame.add(displayArea, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	public void startClient()
	{
		try
		{
			establishConnection();
			getStreams();
			// processConnection();
			exe.execute(this);
			System.out.println("Executeds");
		}
		catch(EOFException e)
		{
			displayArea.append("\nConnection Terminated");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// closeConnection();
		}

	}

	public void establishConnection() throws IOException
	{
		displayArea.append("\nAttempting Connection");
		System.out.println("Attempting Connection");

		// client = new Socket(InetAddress.getByName(host), 19674);
		client = new Socket(InetAddress.getByName(host), 23);

		displayArea.append("\nConnected to: " + client.getInetAddress().getHostName());
		// sendData("Connected to Client");
	}

	public void getStreams()
	{
		try
		{
			output = new ObjectOutputStream(client.getOutputStream());
			output.flush();
			input = new ObjectInputStream(client.getInputStream());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void processConnection() throws IOException
	{
		do
		{
			// System.out.println("inside processConnection");
			try
			{
				messageIn = (String) input.readObject();
				displayArea.append("\nServer: " + messageIn);
			}
			catch(ClassNotFoundException e)
			{
				displayArea.append("\nUnknown object type received");
			}
		}
		while(!messageIn.equals("eof"));
	}

	public void closeConnection()
	{
		displayArea.append("\nTerminating Connection");

		try
		{
			output.close();
			input.close();
			client.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnSend)
		{
			messageOut = txtEnter.getText();
			sendData(messageOut);
			// t.start();
		}
		if(e.getSource() == btnConnect)
		{
			startClient();
		}
		// sendData("C: " + counter++);
	}

	private void sendData(String mess)
	{
		try
		{
			output.writeObject(mess);
			output.flush();
			displayArea.append("\nClient: " + mess);
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getSource() == txtAddress)
		{
			txtAddress.setText("");
		}
		if(e.getSource() == txtEnter)
		{
			txtEnter.setText("");
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
	public void run()
	{
		try
		{
			processConnection();
			// run();
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
