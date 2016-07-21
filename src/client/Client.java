package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import sender.Sender;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Client {

	public Socket socket = new Socket();
	public static final int TIME_OUT = 2000;
	public String address;
	public String message;
	public DataInputStream dis;
	public DataOutputStream dos;
	public static final int PORT = 1234;
	public JFrame frmChatClientV;
	public JButton btnSend;
	public JLabel label;
	private JTextField IP;
	public JButton btnEndChat;
	public JLabel lblIpAddress;
	public JLabel lblStatus = new JLabel("Status : ");
	public GroupLayout groupLayout;
	public JButton btnConnect;
	public JMenuBar menuBar;
	public JMenu mnOptions;
	public JMenuItem mntmSaveToThe;
	public JMenuItem mntmExit;
	public Date d = new Date();
	public String date;
	public FileOutputStream fos;
	public SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
	private DefaultStyledDocument styledDocument;
	private Style headerOutcomeStyle;
	private Style headerIncomeStyle;
	private Style messageOutcomeStyle;
	private Style messageIncomeStyle;
	private JButton btnSendFile;
	public boolean enabl;
	private JScrollPane scrollPane;
	private JTextArea taHistory;
	private JScrollPane scrollPane_1;
	private JTextArea taMessage;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void gui() {
		lblStatus.setBackground(Color.red);
		frmChatClientV = new JFrame("Chat client");
		frmChatClientV.setTitle("Chat client v1.5\r\n");
		frmChatClientV.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Client.class.getResource("/javax/swing/plaf/metal/icons/ocean/computer.gif")));
		frmChatClientV.setResizable(false);
		frmChatClientV.setSize(687, 482);
		frmChatClientV.setVisible(true);
		frmChatClientV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatClientV.setLocationRelativeTo(null);

		label = new JLabel("");

		btnSend = new JButton("SEND");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					sendMessage();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
					lblStatus.setBackground(Color.red);
					lblStatus.setText("Status : CANNOT (get message from server)");
				
			}
		});

		btnEndChat = new JButton("END CHAT");
		btnEndChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		IP = new JTextField();
		IP.setForeground(new Color(0, 0, 0));
		IP.setColumns(10);

		lblIpAddress = new JLabel("IP address");

		btnConnect = new JButton("CONNECT");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				connect();

				btnConnect.setEnabled(false);
				enabl = true;
				if (enabl == true) {
					btnSendFile.setEnabled(true);
				}
			}
		});
		btnConnect.setSelectedIcon(
				new ImageIcon(Client.class.getResource("/com/sun/java/swing/plaf/windows/icons/HardDrive.gif")));

		btnSendFile = new JButton("Send file");
		btnSendFile.setEnabled(false);
		btnSendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Sender.start();

			}

		});
		
		scrollPane = new JScrollPane();
		
		scrollPane_1 = new JScrollPane();
		groupLayout = new GroupLayout(frmChatClientV.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
						.addComponent(label)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(lblIpAddress)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(IP, GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(btnEndChat)
							.addPreferredGap(ComponentPlacement.RELATED, 304, Short.MAX_VALUE)
							.addComponent(btnSendFile, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblStatus, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(13)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(IP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblIpAddress))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnEndChat)
								.addComponent(btnSend)
								.addComponent(btnConnect)
								.addComponent(btnSendFile))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblStatus)
					.addGap(13)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		taMessage = new JTextArea();
		scrollPane_1.setViewportView(taMessage);
		
		taHistory = new JTextArea();
		scrollPane.setViewportView(taHistory);
		frmChatClientV.getContentPane().setLayout(groupLayout);

		menuBar = new JMenuBar();
		frmChatClientV.setJMenuBar(menuBar);

		mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.exit(0);

			}
		});
		
				mntmSaveToThe = new JMenuItem("Write to the file ");
				mntmSaveToThe.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							writeToFile();
						} catch (IOException e1) {
							lblStatus.setText("Status : CANNOT WRITE TO THE FILE(do method)");
						}
					}
				});
				mnOptions.add(mntmSaveToThe);
		mnOptions.add(mntmExit);

	}

	public void startClient() throws IOException {
		gui();

		// System.out.println("FOR EXIT ENTER ''exit''");

	}

	public void getMessage() throws IOException {
		
		try {
			
			dis = new DataInputStream(socket.getInputStream());
		    
		  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			lblStatus.setBackground(Color.red);
			
		}
		
		
		String response = dis.readUTF();
		if (response != null){
			
			taHistory.append("server : " + response + "\n");
		   
		}
		}
	
	
	public void sendMessage() throws IOException {
		getMessage();
		// System.out.println("PLEASE ENTER SOME TEXT");
		StyleContext sc = new StyleContext();

		styledDocument = new DefaultStyledDocument(sc);
		message = taMessage.getText();
		headerIncomeStyle = sc.addStyle("headerIncomeStyle", sc.getStyle(StyleContext.DEFAULT_STYLE));
		StyleConstants.setRightIndent(headerIncomeStyle, 10.f);
		StyleConstants.setAlignment(headerIncomeStyle, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(headerIncomeStyle, Color.decode("#667788"));
		try {
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			lblStatus.setText("Status : CANNOT socket.getOutputStream()");
		}
		if (message.isEmpty()) {
			lblStatus.setText("Status : PLEASE ENTER SOME TEXT");
			message = taMessage.getText();
		}
		try {

			StyleContext sc2 = new StyleContext();

			styledDocument = new DefaultStyledDocument(sc2);
			headerOutcomeStyle = sc2.addStyle("headerOutcomeStyle", sc2.getStyle(StyleContext.DEFAULT_STYLE));
			StyleConstants.setLeftIndent(headerOutcomeStyle, 10.f);
			StyleConstants.setAlignment(headerOutcomeStyle, StyleConstants.ALIGN_LEFT);
			StyleConstants.setForeground(headerOutcomeStyle, Color.decode("#887766"));
			dos.writeUTF(message);
			taHistory.append("You : " + message + "\n");
			dos.flush();
			lblStatus.setText("Status : MESSAGE WAS SENT");
			writeToFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			lblStatus.setText("Status : THE MESSAGE DIDNT SEND");
		}
		getMessage();
	}

	public void connect() {
		socket = new Socket();

		try {
			socket.setSoTimeout(TIME_OUT);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			lblStatus.setText("Status : Time out !!!");
		}
		// System.out.println("PLEASE ENTER IP ADDRESS");
		address = IP.getText();
		// System.out.println("CONNECTING...");
		try {
			socket.connect(new InetSocketAddress(address, PORT));
		} catch (IOException e) {
			lblStatus.setText("Status : CANNOT CONNECT!!!");

		}
		lblStatus.setText("Status : CONNECTION ESTABLISHED!!!");
	}

	public void writeToFile() throws IOException {

		date = format1.format(d);
		fos = new FileOutputStream("c:\\Users\\Глеб\\workspace\\MyChat\\src\\Chat history\\" + date + ".txt");

		byte[] bytes3 = date.getBytes();
		byte[] bytes = message.getBytes();
		byte[] bytes2 = address.getBytes();

		fos.write(bytes3);
		fos.write('\n');
		fos.write(bytes);
		fos.write('\n');
		fos.write(bytes2);
		fos.write('\n');
		fos.close();
	}
}
