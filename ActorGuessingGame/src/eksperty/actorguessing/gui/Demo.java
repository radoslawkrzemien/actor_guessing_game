package eksperty.actorguessing.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Refreshable;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import eksperty.actorguessing.engine.Engine;
import eksperty.actorguessing.engine.QuestionTypes;
import eksperty.actorguessing.engine.entities.Entity;

public class Demo extends JPanel {

	private String[] exampleQuestions = { "Czy aktor jest mezczyzna ?", "Czy gra³ czarne charaktery?",
			"Czy gra³ w filmach kilkuczêœciowych ?", "Czy wspó³pracowa³ z Al Pacino ?",
			"Czy ma pieprzyka na policzku ?", "Czy Twój aktor do Robert de Niro ?" };
	private Map<QuestionTypes,String> questionMappings = new HashMap<>();
	private int i = 0;
	private JLabel filter = new JLabel(exampleQuestions[i]);
	
	private QuestionTypes currentQuestion = null;
	private String currentEntity = null;
	private Engine engine = null;

	public Demo(Engine engine) {
		super(new GridLayout(1, 1));
		this.engine = engine;
		initializeQuestionMappings();
		add(makePanel());

	}

	private void initializeQuestionMappings() {
		this.questionMappings.put(QuestionTypes.MOVIE_PLAYED_IN, "Czy aktor gral w ");
		this.questionMappings.put(QuestionTypes.ROLE_PLAYED, "Czy aktor wystepowal jako ");
		this.questionMappings.put(QuestionTypes.DIRECTOR_OF_MOVIE_PLAYED_IN, "Czy aktor gral w filmie rezyserowanym przez ");
		this.questionMappings.put(QuestionTypes.SERIES_MOVIE_PLAYED_IN_FROM, "Czy aktor gral w filmie z serii ");
		this.questionMappings.put(QuestionTypes.SEX, "Czy aktor ma plec ");
		this.questionMappings.put(QuestionTypes.FEATURES, "Czy aktora charakteryzuje ");
		this.questionMappings.put(QuestionTypes.PERSON_OFTEN_WORKS_WITH, "Czy aktor czesto pracuje z ");
		this.questionMappings.put(QuestionTypes.ROLE_OFTEN_PLAYS, "Czy aktor czesto wystepuje jako ");
	}

	private ActionListener demoListener(Engine engine) {
		return new MyActionListener(engine); 

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
		yesBtn.addActionListener(demoListener(this.engine));
		noBtn.addActionListener(demoListener(this.engine));
		dontKnowBtn.addActionListener(demoListener(this.engine));
		askPanel.add(yesBtn);
		askPanel.add(noBtn);
		askPanel.add(dontKnowBtn);
		panel.add(filter);
		panel.add(askPanel);
		refreshQuestion();
		return panel;
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	public static void createAndShowGUI(Engine engine) {
		// Create and set up the window.
		JFrame frame = new JFrame("Actor Guessing Game");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new Demo(engine), BorderLayout.CENTER);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void refreshQuestion() {
		Map<QuestionTypes,String> nextQuestion = engine.getNextQuestion();
		if(nextQuestion.isEmpty()){
			String result = engine.getResult();
			filter.setText("Czy twoj aktor to " + result);
		} else {
			for(QuestionTypes type : nextQuestion.keySet()){
				currentQuestion = type;
				currentEntity = nextQuestion.get(type);
				filter.setText(questionMappings.get(currentQuestion) + " " + currentEntity);
				break;
			}
		}
	}
	
	private class MyActionListener implements ActionListener{
		
		private Engine engine;
		
		public MyActionListener(Engine engine){
			super();
			this.engine = engine;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String buttonText = ((JButton)e.getSource()).getText();
			if(buttonText.equals("Tak")){
				engine.addKnownFact(currentQuestion, currentEntity, true);
			} else if(buttonText.equals("Nie")){
				engine.addKnownFact(currentQuestion, currentEntity, false);
			}
//			if (i < exampleQuestions.length-1) {
//				filter.setText(exampleQuestions[++i]);
//			}else{
//				filter.setText("demo");
//			}
			refreshQuestion();

		}
	};
}
