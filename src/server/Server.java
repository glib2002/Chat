package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import client.Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;

public class Server {

	private static final int SERVER_TIMEOUT = 2000;
	private ServerSocket serverSocket;
	public Socket accept;
	public Scanner scanner;
	public String message;
	public DataOutputStream dos;
	public JFrame frmChatServerV;
	public JButton btnSend;
	public JButton btnEnd;
	public JLabel lblStatus = new JLabel("Status  : ");
	public DataInputStream dis;
	public GroupLayout groupLayout;
	public Date d = new Date();
	public String date;
	public FileOutputStream fos;
	public SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
	private JMenuBar menuBar;
	private JMenu mnOptions;
	private JMenuItem mntmExit;
	private JMenuItem mntmWriteToThe;
	JTextArea tahistory = new JTextArea();
	JTextArea taentmess = new JTextArea();

	public void startServer() throws IOException {

		Gui();
		connect();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void Gui() {
		frmChatServerV = new JFrame("Chat server");
		frmChatServerV.setTitle("Chat server v 1.5");
		frmChatServerV.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Server.class.getResource("/com/sun/java/swing/plaf/windows/icons/HomeFolder.gif")));
		frmChatServerV.setResizable(false);
		frmChatServerV.setSize(777, 476);
		frmChatServerV.setVisible(true);
		frmChatServerV.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmChatServerV.setLocationRelativeTo(null);

		btnSend = new JButton("SEND");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					sendMessage();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					lblStatus.setText("Status : " + " the letter cannot send/get letter");
				}

			}
		});

		btnEnd = new JButton("END CHAT");
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_1 = new JScrollPane();
		groupLayout = new GroupLayout(frmChatServerV.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnEnd)
								.addPreferredGap(ComponentPlacement.RELATED, 612, Short.MAX_VALUE).addComponent(btnSend,
										GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblStatus, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 173,
								GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		groupLayout
				.setVerticalGroup(
						groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap()
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 167,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnEnd).addComponent(btnSend))
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblStatus).addGap(8)
										.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
										.addContainerGap()));

		scrollPane.setViewportView(tahistory);

		scrollPane_1.setViewportView(taentmess);
		frmChatServerV.getContentPane().setLayout(groupLayout);

		menuBar = new JMenuBar();
		frmChatServerV.setJMenuBar(menuBar);

		mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		mntmWriteToThe = new JMenuItem("Write to the file");
		mntmWriteToThe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					writeToFile();
				} catch (IOException e1) {
					lblStatus.setText("Status : CANNOT WRITE TO THE FILE(do method)");
				}

			}
		});
		mnOptions.add(mntmWriteToThe);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.exit(0);

			}
		});
		mnOptions.add(mntmExit);

	}

	private void getClient() throws IOException {

		dis = new DataInputStream(accept.getInputStream());

		String responce = dis.readUTF();
		if (responce != null) {
			tahistory.append("RESPONCE : " + responce + "\n");
		}

	}

	private void sendMessage() throws IOException {
		getClient();
		message = taentmess.getText();
		try {
			dos = new DataOutputStream(accept.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			lblStatus.setText("Status : CANNOT socket.getOutputStream()");
		}
		if (message.isEmpty()) {
			lblStatus.setText(" Status : PLEASE ENTER SOME TEXT");
			message = taentmess.getText();
		}
		try {
			dos.writeUTF(message);
			tahistory.append("You : " + message + "\n");
			dos.flush();
			lblStatus.setText("Status : MESSAGE WAS SENT");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			lblStatus.setText("Status : THE MESSAGE DIDNT SEND");
		}
		 getClient();
	}

	private void connect() throws IOException {
		// TODO Auto-generated method stub
		serverSocket = new ServerSocket(1234);

		lblStatus.setText("Status : SERVER LISTEN  " + Client.PORT);
		accept = serverSocket.accept();
		accept.setSoLinger(true, SERVER_TIMEOUT / 2); // Waiting before close
														// connection
		accept.setSoTimeout(SERVER_TIMEOUT); // Waiting before throws
												// SocketTimeoutException when
												// read data.

		// Print connected ip address.
		String clientAddress = accept.getInetAddress().getHostAddress();
		lblStatus.setText("Status : IP" + clientAddress);
		lblStatus.setText("Status : CONNECTED" + clientAddress);
	}

	public void writeToFile() throws IOException {

		date = format1.format(d);
		fos = new FileOutputStream("c:\\Users\\Глеб\\workspace\\MyChat\\src\\Chat history\\" + date + ".txt");

		byte[] bytes3 = date.getBytes();
		byte[] bytes = message.getBytes();

		fos.write('\n');
		fos.write(bytes3);
		fos.write('\n');
		fos.write(bytes);

	}
}
