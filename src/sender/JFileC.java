package sender;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class JFileC {
	JFileChooser jfc = new JFileChooser();

	/**
	 * @wbp.parser.entryPoint
	 */
	public void start() {
		jfc.setSize(614, 495);
		jfc.setVisible(true);
		jfc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Sender.pathfile = jfc.getToolTipText();
			}
		});
		
	}

}
