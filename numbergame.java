import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.*;
public class numbergame{
    static int number=new Random().nextInt(100)+1;
    static int attempts=0;
    public static void main(String args[]){
        
        JFrame frame=new JFrame("Number Game");
        frame.setSize(420,350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        JLabel title=new JLabel("Guess the Number Game");
        title.setBounds(130,0,150,25);
        frame.add(title);
        
        JLabel label=new JLabel("Guess(1-100):");
        label.setBounds(30,30,100,25);
        frame.add(label);

        JTextField inputfield=new JTextField();
        inputfield.setBounds(150,30,100,25);
        frame.add(inputfield);

        JButton guessBtn=new JButton("Check");
        guessBtn.setBackground(Color.BLACK);
        guessBtn.setForeground(Color.WHITE);
        guessBtn.setBounds(150,70,100,25);
        frame.add(guessBtn);

        JLabel result=new JLabel(" ");
        result.setBounds(120,130,300,25);
        frame.add(result);

        JLabel attemptlabel=new JLabel("Attempts: 0");
        attemptlabel.setBounds(160,100,150,35);
        frame.add(attemptlabel);

        JButton playagain=new JButton("Play Again");
        playagain.setBounds(150,160,100,25);
        playagain.setBackground(Color.BLACK);
        playagain.setForeground(Color.WHITE);
        playagain.setVisible(false);
        frame.add(playagain);

        guessBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    int guess=Integer.parseInt(inputfield.getText());
                    attempts++;
                    attemptlabel.setText("Attempts:"+attempts);
                    if(attempts<=8){
                    if(guess<number){
                        result.setText("Too Low!Try Again..!");
                    }else if(guess>number){
                        result.setText("Too High!Try Again..!");
                    }else{
                        result.setText("Correct! You Win at "+attempts+" attempts..!");
                        guessBtn.setEnabled(false);
                        playagain.setVisible(true);
                    }}
                    else{
                        result.setText("Oops..!Try before 8 attempts..!");
                        guessBtn.setEnabled(false);
                        playagain.setVisible(true);
                    }
                }catch(NumberFormatException ex){
                    result.setText("Enter a valid number..");
                }
            }
        });
        playagain.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                number=new Random().nextInt(100)+1;
                attempts=0;
                inputfield.setText("");
                result.setText("");
                attemptlabel.setText("Attempts: 0");
                guessBtn.setEnabled(true);
                playagain.setVisible(false);
            }
        });
        frame.setVisible(true);
    }
}