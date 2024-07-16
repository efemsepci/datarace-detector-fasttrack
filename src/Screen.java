import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen extends JFrame {
    
    private JTextArea textArea;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;

    public Screen() {
       
        setTitle("FastTrack Test Tool");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2)); 
        
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1)); 
        
        
        button1 = new JButton("Zero Race");
        button2 = new JButton("30 Race");
        button3 = new JButton("50 Race");
        button4 = new JButton("RaceTrack Case (53 race 43 race free)");

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);
        
        Test tests = new Test();

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.append(tests.zeroRaceCase() + "\n");
            }
        });
        
        button2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		textArea.append(tests.thirtyRaceCase() + "\n");
        	}
        });
        
        button3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		textArea.append(tests.fiftyRaceCase() + "\n");
        	}
        });
        
        button4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		textArea.append(tests.raceTrackCase() + "\n");
        	}
        });
        
        panel.add(buttonPanel);
        add(panel);
    }

    }
