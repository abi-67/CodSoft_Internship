import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class quiz extends JFrame {

    private String[] questions = {
        "Which language runs in a web browser?",
        "What does CPU stand for?",
        "Which company developed Java?",
        "Which data structure uses LIFO?",
        "What is the extension of a Java file?"
    };

    private String[][] options = {
        {"Java", "C", "Python", "JavaScript"},
        {"Central Process Unit", "Central Processing Unit", "Computer Personal Unit", "Central Processor Unit"},
        {"Microsoft", "Apple", "Sun Microsystems", "Google"},
        {"Queue", "Stack", "Tree", "Graph"},
        {".jav", ".class", ".java", ".js"}
    };

    private String[] answers = {
        "JavaScript", "Central Processing Unit", "Sun Microsystems", "Stack", ".java"
    };

    private int index = 0;
    private int score = 0;
    private int time = 15;
    private Timer timer;
    private Timer pauseTimer;

    private JLabel questionLabel, timerLabel;
    private JRadioButton[] choices = new JRadioButton[4];
    private ButtonGroup bg = new ButtonGroup();
    private JButton nextButton;

    // Track answers
    private java.util.List<String> userAnswers = new ArrayList<>();
    private java.util.List<Boolean> correctness = new ArrayList<>();

    public quiz() {
        setTitle("Quiz Application with Timer");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        timerLabel = new JLabel("Time Left: 15");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        timerLabel.setForeground(Color.RED);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(questionLabel, BorderLayout.CENTER);
        topPanel.add(timerLabel, BorderLayout.EAST);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            final int choiceIndex = i;
            choices[i] = new JRadioButton();
            choices[i].setFont(new Font("Arial", Font.PLAIN, 14));
            bg.add(choices[i]);
            optionsPanel.add(choices[i]);

            choices[i].addActionListener(e -> {
                if (timer != null) timer.stop();
                handleSelection(choices[choiceIndex].getText());
            });
        }

        nextButton = new JButton("Next");
        nextButton.setVisible(false);

        add(topPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(nextButton, BorderLayout.SOUTH);

        displayQuestion();
    }

    private void displayQuestion() {
        if (index >= questions.length) {
            showResult();
            return;
        }

        bg.clearSelection();
        for (int i = 0; i < 4; i++) {
            choices[i].setText(options[index][i]);
            choices[i].setForeground(Color.BLACK);
            choices[i].setEnabled(true);
        }

        questionLabel.setText("Q" + (index + 1) + ": " + questions[index]);
        time = 15;
        timerLabel.setText("Time Left: " + time);

        if (timer != null) timer.stop();
        timer = new Timer(1000, e -> {
            time--;
            timerLabel.setText("Time Left: " + time);
            if (time == 0) {
                timer.stop();
                handleSelection(null); // no selection
            }
        });
        timer.start();
    }

    private void handleSelection(String selectedAnswer) {
        String correctAnswer = answers[index];

        boolean isCorrect = selectedAnswer != null && selectedAnswer.equals(correctAnswer);
        if (isCorrect) score++;

        userAnswers.add(selectedAnswer == null ? "No Answer" : selectedAnswer);
        correctness.add(isCorrect);
        // Show correct answer in green, wrong selected in red
        for (int i = 0; i < 4; i++) {
            String option = choices[i].getText();
            if (option.equals(correctAnswer)) {
                choices[i].setForeground(Color.GREEN);
            } else if (option.equals(selectedAnswer)) {
                choices[i].setForeground(Color.RED);
            }
            choices[i].setEnabled(false);
        }

        pauseTimer = new Timer(2000, e -> {
            pauseTimer.stop();
            index++;
            displayQuestion();
        });
        pauseTimer.setRepeats(false);
        pauseTimer.start();
    }

    private void showResult() {
        StringBuilder summary = new StringBuilder();
        summary.append("Your Score: ").append(score).append("/").append(questions.length).append("\n\n");

        for (int i = 0; i < questions.length; i++) {
            summary.append("Q").append(i + 1).append(": ").append(questions[i]).append("\n");
            summary.append("  Your Answer: ").append(userAnswers.get(i)).append("\n");
            summary.append("  Correct Answer: ").append(answers[i]).append("\n");
            summary.append("  Result: ").append(correctness.get(i) ? "✅ Correct" : "❌ Incorrect").append("\n\n");
        }

        JTextArea textArea = new JTextArea(summary.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Quiz Summary", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new quiz().setVisible(true));
    }
}