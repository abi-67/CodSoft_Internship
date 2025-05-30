import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class grade_calculator extends JFrame implements ActionListener{
    private JTextField[] subjectfields;
    private JButton calculate,reset;
    private JLabel totallabel,percentagelabel,gradelabel;
   
    public grade_calculator(){
        
        setTitle("Student Grade Calculator");
        setSize(400,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new GridLayout(10,2,5,5));

        JLabel titlelabel=new JLabel("Student Grade Calculator",SwingConstants.CENTER);
        titlelabel.setFont(new Font("Arial",Font.BOLD,18));
        add(titlelabel,BorderLayout.NORTH);
        add(new JLabel());

        subjectfields=new JTextField[5];
        for(int i=0;i<5;i++){
            add(new JLabel("Subject "+(i+1)+" Marks(out of 100):"));
            subjectfields[i]=new JTextField();
            add(subjectfields[i]);
        }

        calculate=new JButton("Calculate");
        reset=new JButton("Reset");
        add(calculate);
        add(reset);

        calculate.addActionListener(this);
        reset.addActionListener(this);

        totallabel=new JLabel("Total Marks:");
        percentagelabel=new JLabel("Total Percentage:");
        gradelabel=new JLabel("Grade:");

        add(totallabel);
        add(percentagelabel);
        add(gradelabel);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==calculate){
            int total=0;
            boolean valid=true;
            for(JTextField field:subjectfields){
                try{
                    int marks=Integer.parseInt(field.getText());
                    if(marks<0||marks>100){
                        JOptionPane.showMessageDialog(this,"Enter mark between 0 and 100.");
                        valid=false;
                        break;
                    }total+=marks;
                }
                catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(this,"Please enter valid numbers.");
                    valid=false;
                    break;
                }
        }
                if(valid){
                    double average=total/5;
                    String grade;
                    if(average>=90) grade="A+";
                    else if(average>=80) grade="A";
                    else if(average>=70) grade="B";
                    else if(average>=60) grade="C";
                    else if(average>=50) grade="D";
                    else grade="Fail";

                    totallabel.setText("Total Marks:"+total);
                    percentagelabel.setText("Total Percentage:"+average);
                    gradelabel.setText("Grade:"+grade);
                }
        }
            else if(e.getSource()==reset){
                for(JTextField field:subjectfields){
                    field.setText("");
                }
                totallabel.setText("Total Marks:");
                percentagelabel.setText("Total Percentage:");
                gradelabel.setText("Grade:");
            }
        }
        public static void main(String args[]){
            new grade_calculator();
        }
    }