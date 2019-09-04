package serverHelden;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import frames.MainFrame;

public class StartServerHelden {

	public static void main(String[] args) {
		JFrame mainFrame = new MainFrame();
		if (mainFrame != null) {
			mainFrame.setLocation(100, 100);
			mainFrame.setSize(new Dimension(300, 300));
			mainFrame.setMinimumSize(new Dimension(200, 200));
			mainFrame.setPreferredSize(new Dimension(400, 300));

			Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (screen_size.width - mainFrame.getWidth()) / 2;
			int y = (screen_size.height - mainFrame.getHeight()) / 2;

			mainFrame.setLocation(x, y);
			mainFrame.setVisible(true);
		}
	}

}
