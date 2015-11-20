package eksperty.actorguessing.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Demo extends JPanel {

	private String[] exampleQuestions = { "Czy aktor jest mezczyzna ?", "Czy gra³ czarne charaktery?",
			"Czy gra³ w filmach kilkuczêœciowych ?", "Czy wspó³pracowa³ z Al Pacino ?",
			"Czy ma pieprzyka na policzku ?", "Czy Twój aktor do Robert de Niro ?" };
	private int i = 0;
	private JLabel filter = new JLabel(exampleQuestions[i]);

	public Demo() {
		super(new GridLayout(1, 1));

		add(makePanel());

	}

	private ActionListener demoListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (i < exampleQuestions.length-1) {
					filter.setText(exampleQuestions[++i]);
				}else{
					filter.setText("demo");
				}

			}
		};

	}

	protected JComponent makePanel() {
		JPanel panel = new JPanel(false);
		JPanel askPanel = new JPanel(false);
		filter.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(2, 1));
		askPanel.setLayout(new GridLayout(1, 3));

		JButton yesBtn = new JButton("Tak");
		JButton noBtn = new JButton("Nie");
		JButton dontKnowBtn = new JButton("Nie wiem");
		yesBtn.addActionListener(demoListener());
		noBtn.addActionListener(demoListener());
		dontKnowBtn.addActionListener(demoListener());
		askPanel.add(yesBtn);
		askPanel.add(noBtn);
		askPanel.add(dontKnowBtn);
		panel.add(filter);
		panel.add(askPanel);
		return panel;
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	public static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Actor Guessing Game");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new Demo(), BorderLayout.CENTER);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
