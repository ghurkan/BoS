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
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultCaret;

public class Server extends JFrame implements ActionListener, MouseListener, Runnable
{
	JTextArea displayArea;
	JTextField txtEnter;
	JButton btnStart;
	JButton btnSend;
	JButton btnStop;
	JFrame frame;
	JPanel pnlNorth;
	ExecutorService exe;
	long counter = 0;

	ObjectInputStream input;
	ObjectOutputStream output;
	ServerSocket server;
	Socket connection;

	String messageIn = "";
	String messageOut = "hello client";
	String host = "";

	public Server()
	{
		frame = new JFrame("Server");
		frame.setSize(400, 400);
		frame.setResizable(false);
		// frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout(5, 5));

		exe = Executors.newCachedThreadPool();

		btnStart = new JButton("Start");
		btnSend = new JButton("Send");
		btnStop = new JButton("Stop");
		displayArea = new JTextArea();
		displayArea.setEditable(true);
		DefaultCaret caret = (DefaultCaret) displayArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		txtEnter = new JTextField("Enter message...");
		txtEnter.setPreferredSize(new Dimension(100, 20));

		pnlNorth = new JPanel();
		pnlNorth.setLayout(new FlowLayout());

		txtEnter.addMouseListener(this);
		btnStart.addActionListener(this);
		btnSend.addActionListener(this);
		btnStop.addActionListener(this);

		pnlNorth.add(btnStart);
		pnlNorth.add(btnStop);
		pnlNorth.add(txtEnter);
		pnlNorth.add(btnSend);

		frame.add(pnlNorth, BorderLayout.NORTH);
		frame.add(displayArea, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	public void getStreams() throws IOException
	{
		try
		{
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		System.out.println("streams opened");
	}

	private void displayMessage(final String messageToDisplay)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run() // updates displayArea
			{
				displayArea.append(messageToDisplay); // append message
			} // end method run
		} // end anonymous inner class
				); // end call to SwingUtilities.invokeLater
	} // end m

	public void establishConnection() throws EOFException
	{
		// displayArea.append("Waiting connection");
		displayMessage("\nWaiting Connection");
		System.out.println("Waiting connection");
		try
		{
			connection = server.accept();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		displayArea.append("\nConnection established with: " + connection.getInetAddress().getHostAddress());
		System.out.println("Connection established with: " + connection.getInetAddress().getHostAddress());
		// System.out.println("Own IP address: " );

		Enumeration e = null;
		try
		{
			e = NetworkInterface.getNetworkInterfaces();
		}
		catch(SocketException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(e.hasMoreElements())
		{
			NetworkInterface n = (NetworkInterface) e.nextElement();
			Enumeration ee = n.getInetAddresses();
			while(ee.hasMoreElements())
			{
				InetAddress i = (InetAddress) ee.nextElement();
				System.out.println(i.getHostAddress());
			}
		}

		// sendData("Connected to Server");
	}

	public void startServer()
	{
		try
		{
			// server = new ServerSocket(19674, 50);
			server = new ServerSocket(23, 50);

			/*while(true)
			{
				
			}*/

			try
			{
				establishConnection();
				getStreams();
				// processConnection();
				exe.execute(this);
				System.out.println("Executed");
			}
			catch(EOFException e)
			{
				displayArea.append("\nConnetion Terminated");
				System.out.println("Connetion Terminated");
				e.printStackTrace();
			}
			finally
			{
				// closeConnection();
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void sendData(String mess)
	{
		try
		{
			output.writeObject(mess);
			output.flush();
			displayArea.append("\nServer: " + mess);
		}
		catch(IOException e)
		{
			// displayArea.append("\nError writing object");
			System.out.println("Error writing object");
			e.printStackTrace();
		}
	}

	public void processConnection()
	{
		displayArea.append("\nConnection successful");
		System.out.println("Connection successful");

		while(true)
		{
			// System.out.println("inside processConnection");
			try
			{
				messageIn = (String) input.readObject();
				displayArea.append("\nClient: " + messageIn);
			}
			catch(SocketException e)
			{
				e.printStackTrace();
				displayArea.append("\n Connection Reset");

				try
				{
					connection.close();
				}
				catch(IOException e2)
				{

					e2.printStackTrace();
				}
				try
				{
					exe.awaitTermination(10, TimeUnit.MILLISECONDS);
				}
				catch(InterruptedException e1)
				{
					e1.printStackTrace();
				}
				displayArea.append("\n Connection Reset");
				closeConnection();
				break;
			}
			catch(ClassNotFoundException | IOException e)
			{
				displayArea.append("\nUnknown object type received");
				System.out.println("Unknown object type received");
				e.printStackTrace();
			}
		}
	}

	public void closeConnection()
	{
		displayArea.append("\nTerminating Connection");
		System.out.println("Terminating Connection");

		try
		{
			output.close();
			input.close();
			connection.close();
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
		}
		if(e.getSource() == btnStart)
		{
			startServer();
		}
		if(e.getSource() == btnStop)
		{
			closeConnection();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
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
		processConnection();
	}
}
