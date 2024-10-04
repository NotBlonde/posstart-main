import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Receipt {
    private JTextArea receiptArea;
    private JScrollPane scrollPane;

    public Receipt() {
        // Skapar kvittoutskriften
        receiptArea = new JTextArea();
        receiptArea.setSize(400, 400);
        receiptArea.setLineWrap(true);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Skapar en scrollpanel för kvittot
        scrollPane = new JScrollPane(receiptArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(600, 0, 400, 800);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    // Lägg till text till kvittot
    public void addToReceipt(String text) {
        receiptArea.append(text + "\n");
    }

    public void clearReceipt() {
        receiptArea.setText("");
    }
}
