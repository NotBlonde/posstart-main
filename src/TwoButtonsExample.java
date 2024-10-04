import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TwoButtonsExample {

    public static void main(String[] args) {
        // Create a new JFrame (window)
        JFrame frame = new JFrame("Two Buttons Example");
        frame.setSize(1000, 200); // Set frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        frame.setLayout(null); // Use no layout manager (absolute positioning)

        // Create the first button
        JButton button1 = new JButton("Button 1");
        button1.setBounds(50, 50, 120, 50); // Set position and size (x, y, width, height)
        
        // Create the second button
        JButton button2 = new JButton("Button 2");
        button2.setBounds(600, 50, 120, 50); // Set position and size (x, y, width, height)

        // Add action listeners for button click events
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 1 was clicked!");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button 2 was clicked!");
            }
        });

        // Add buttons to the frame
        frame.add(button1);
        frame.add(button2);

        // Make the frame visible
        frame.setVisible(true);
    }
}
