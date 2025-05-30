import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class course_registration extends JFrame implements ActionListener {

    class Course {
        String code;
        String title;
        String description;
        String timing;
        int slots;

        Course(String code, String title, String description, String timing, int slots) {
            this.code = code;
            this.title = title;
            this.description = description;
            this.timing = timing;
            this.slots = slots;
        }

        @Override
        public String toString() {
            return code + " - " + title;
        }

        public String getDetails() {
            return "Course Code: " + code + "\n"
                 + "Title: " + title + "\n"
                 + "Description: " + description + "\n"
                 + "Timing: " + timing + "\n"
                 + "Available Slots: " + slots;
        }
    }

    private Map<String, Course> courseMap = new LinkedHashMap<>();
    private JComboBox<String> courseDropdown;
    private JTextArea courseDetailsArea;
    private DefaultListModel<String> registeredCoursesModel;
    private JList<String> registeredCoursesList;
    private JButton registerButton, removeButton;
    private JTextField studentIdField, studentNameField;

    public course_registration() {
        setTitle("Student Course Registration System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeCourses(); 
        buildInterface();    
        setVisible(true);
    }

    
    private void initializeCourses() {
        courseMap.put("CSE101", new Course("CSE101", "Java Programming", "Introduction to Java, OOP, GUI design.", "Mon & Wed 10:00-11:30", 3));
        courseMap.put("CSE102", new Course("CSE102", "Data Structures", "Stacks, Queues, Trees, Graphs.", "Tue & Thu 11:30-1:00", 2));
        courseMap.put("CSE103", new Course("CSE103", "Database Systems", "SQL, ER models, normalization.", "Mon & Wed 2:00-3:30", 2));
        courseMap.put("CSE104", new Course("CSE104", "Operating Systems", "Processes, Memory, Scheduling.", "Fri 9:00-12:00", 2));
        courseMap.put("CSE105", new Course("CSE105", "Web Development", "HTML, CSS, JavaScript, Backend.", "Sat 10:00-1:00", 3));
    }

   
    private void buildInterface() {
        JLabel titleLabel = new JLabel("Student Course Registration", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        studentIdField = new JTextField(10);
        studentNameField = new JTextField(10);
        JPanel studentPanel = new JPanel();
        studentPanel.setBorder(BorderFactory.createTitledBorder("Student Info"));
        studentPanel.add(new JLabel("Student ID:"));
        studentPanel.add(studentIdField);
        studentPanel.add(new JLabel("Name:"));
        studentPanel.add(studentNameField);

        courseDropdown = new JComboBox<>(courseMap.keySet().toArray(new String[0]));
        courseDropdown.addActionListener(e -> updateCourseDetails());
        courseDetailsArea = new JTextArea(8, 40);
        courseDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        courseDetailsArea.setEditable(false);
        courseDetailsArea.setBorder(BorderFactory.createTitledBorder("Course Details"));
        updateCourseDetails();

        registerButton = new JButton("Register");
        removeButton = new JButton("Remove");
        registerButton.addActionListener(this);
        removeButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(removeButton);

        registeredCoursesModel = new DefaultListModel<>();
        registeredCoursesList = new JList<>(registeredCoursesModel);
        registeredCoursesList.setBorder(BorderFactory.createTitledBorder("Registered Courses"));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(courseDropdown, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(courseDetailsArea), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JScrollPane(registeredCoursesList), BorderLayout.CENTER);

        add(titleLabel, BorderLayout.NORTH);
        add(studentPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
    }

   
    private void updateCourseDetails() {
        String selected = (String) courseDropdown.getSelectedItem();
        if (selected != null) {
            Course course = courseMap.get(selected);
            courseDetailsArea.setText(course.getDetails());
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        String studentId = studentIdField.getText().trim();
        String studentName = studentNameField.getText().trim();

        if (studentId.isEmpty() || studentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID and Name.", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedKey = (String) courseDropdown.getSelectedItem();
        if (selectedKey == null) return;

        Course course = courseMap.get(selectedKey);

        if (e.getSource() == registerButton) {
            if (registeredCoursesModel.contains(course.toString())) {
                JOptionPane.showMessageDialog(this, "Already registered for this course.");
            } else if (course.slots > 0) {
                registeredCoursesModel.addElement(course.toString());
                course.slots--;
                updateCourseDetails();
                JOptionPane.showMessageDialog(this, studentName + " registered for " + course.title);
            } else {
                JOptionPane.showMessageDialog(this, "No slots available.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == removeButton) {
            if (registeredCoursesModel.contains(course.toString())) {
                registeredCoursesModel.removeElement(course.toString());
                course.slots++;
                updateCourseDetails();
                JOptionPane.showMessageDialog(this, "Course removed from registration.");
            } else {
                JOptionPane.showMessageDialog(this, "You are not registered for this course.");
            }
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(course_registration::new);
    }
}
