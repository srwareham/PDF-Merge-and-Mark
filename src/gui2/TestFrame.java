package gui2;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TestFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4346252518190789378L;

	public TestFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PDFListHolder lh = new PDFListHolder();
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(lh.getScrollPane(),BorderLayout.CENTER);
		this.getContentPane().add(new JButton("TEST"), BorderLayout.PAGE_END);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		TestFrame f = new TestFrame();
	}
}
