package sender;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;

import client.Client;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.JInternalFrame;
import javax.swing.JProgressBar;

public class Sender {

	public static JFrame frmChatClientV;
	private static JTextField path;
	public static String pathfile;
	public static Client c;

	public static void sendFile() throws IOException {

		File file = new File(pathfile);
		c = new Client();
		readFile(file);

	}

	private static void readFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);

		while (fis.available() > 0) {
			int read = fis.read();
			System.out.print((char) read);

			c.dos.writeByte(read);
		}

		fis.close();

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void start() {
		gui();
	}

	public static void gui() {

		frmChatClientV = new JFrame("Chat client");
		frmChatClientV.setTitle("Master download");
		frmChatClientV.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Sender.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
		frmChatClientV.setResizable(false);
		frmChatClientV.setSize(670, 270);
		frmChatClientV.setVisible(true);
		frmChatClientV.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmChatClientV.setLocationRelativeTo(null);

		JLabel lblEnterPath = new JLabel("Enter path :");

		path = new JTextField();
		path.setColumns(10);

		JLabel lblCommentary = new JLabel("Commentary : ");

		JTextArea comm = new JTextArea();

		JButton btnSend = new JButton("send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					sendFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JButton Brows = new JButton("Brows\r\n");
		Brows.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileC c = new JFileC();
				c.start();

			}
		});
		Brows.setBackground(new Color(240, 240, 240));
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChatClientV.setDefaultCloseOperation(0);
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmChatClientV.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblEnterPath)
						.addComponent(lblCommentary)
						.addComponent(btnClose))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(path, GroupLayout.PREFERRED_SIZE, 458, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Brows, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
						.addComponent(comm, GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 514, Short.MAX_VALUE)
							.addComponent(btnSend)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEnterPath)
						.addComponent(path, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(Brows, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comm, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCommentary))
					.addGap(7)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSend)
						.addComponent(btnClose))
					.addContainerGap())
		);
		frmChatClientV.getContentPane().setLayout(groupLayout);

	}
}
