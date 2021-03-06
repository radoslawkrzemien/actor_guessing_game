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
import eksperty.actorguessing.engine.Mapper;
import eksperty.actorguessing.engine.Question;
import eksperty.actorguessing.engine.QuestionTypes;
import eksperty.actorguessing.engine.entities.Entity;

public class Demo extends JPanel {

	private String[] exampleQuestions = { "Czy aktor jest mezczyzna ?", "Czy gra� czarne charaktery?",
			"Czy gra� w filmach kilkucz�ciowych ?", "Czy wsp�pracowa� z Al Pacino ?",
			"Czy ma pieprzyka na policzku ?", "Czy Tw�j aktor do Robert de Niro ?" };
	private Map<QuestionTypes,String> questionMappings = new HashMap<>();
	private int i = 0;
	private JLabel filter = new JLabel();
	private JButton yesBtn = new JButton("Tak");
	private JButton noBtn = new JButton("Nie");
	private JButton dontKnowBtn = new JButton("Nie wiem");
	private int state = 0;
	
	private Question currentQuestion = null;
//	private String currentEntity = null;
	private Engine engine = null;
	private Mapper mapper = null;

	public Demo(Engine engine, Mapper mapper) {
		super(new GridLayout(1, 1));
		this.engine = engine;
		this.mapper = mapper;
		initializeQuestionMappings();
		add(makePanel());

	}

	private void initializeQuestionMappings() {
		this.questionMappings.put(QuestionTypes.MOVIE_PLAYED_IN, "Czy aktor gral w ");
//		this.questionMappings.put(QuestionTypes.ROLE_PLAYED, "Czy aktor wystepowal jako ");
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

//		JButton yesBtn = new JButton("Tak");
//		JButton noBtn = new JButton("Nie");
//		JButton dontKnowBtn = new JButton("Nie wiem");
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
	public static void createAndShowGUI(Engine engine, Mapper mapper) {
		// Create and set up the window.
		JFrame frame = new JFrame("Actor Guessing Game");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new Demo(engine, mapper), BorderLayout.CENTER);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void refreshQuestion() {
		Question nextQuestion = engine.getNextQuestion();
		if(nextQuestion == null){
			this.state++;
			String result = engine.getResult();
			filter.setText("Czy twoj aktor to " + result);
		} else {
			this.currentQuestion = nextQuestion;
			filter.setText(questionMappings.get(currentQuestion.getType()) + " " + currentQuestion.getEntity().getFriendlyName());
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
				if(state == 0)
					engine.addKnownFact(currentQuestion.getType(), currentQuestion.getEntity(), true);
				else
					updateWindowSuccess();
			} else if(buttonText.equals("Nie")){
				if(state == 0)
					engine.addKnownFact(currentQuestion.getType(), currentQuestion.getEntity(), false);
				else
					updateWindowFailure();
			}
			if(state == 0)
				refreshQuestion();

		}
	}

	public void updateWindowSuccess() {
		filter.setText("Udalo sie odgadnac aktora! :)");
		hideButtons();
		
	}

	public void updateWindowFailure() {
		filter.setText("Nie udalo sie odgadnac aktora! :(");
		hideButtons();
	};

	private void hideButtons() {
		this.yesBtn.setVisible(false);
		this.noBtn.setVisible(false);
		this.dontKnowBtn.setVisible(false);
	}
}
