import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class CashRegister {
    JFrame frame;
    JTextArea inputProductName;
    JTextArea inputCount;
    JButton addToReceiptButton;
    JButton payButton;
    Receipt receipt;
    ProductButton quickButtonsPanel;

    private String currentProductName = "";
    private int currentProductCount = 0;
    private Map<String, Integer> purchasedProducts;  // Tracks purchased products and their quantities

    public CashRegister() {
        // Set up the frame
        frame = new JFrame("IOT24 POS");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Set up the receipt area
        receipt = new Receipt();
        frame.add(receipt.getScrollPane());

        // Set up quick buttons panel
        quickButtonsPanel = new ProductButton(receipt, this);
        frame.add(quickButtonsPanel.getPanel());

        // Initialize purchased products map
        purchasedProducts = new HashMap<>();

        // Set up input area and payment button
        createAddArea();

        frame.setVisible(true);
    }

    // Manually input product name and quantity
    private void createAddArea() {
        inputProductName = new JTextArea();
        inputProductName.setBounds(20, 650, 300, 30);
        inputProductName.setFont(new Font("Arial Black", Font.BOLD, 18));
        inputProductName.setEditable(false);
        frame.add(inputProductName);

        JLabel label = new JLabel("Quantity");
        label.setBounds(340, 625, 300, 30);
        label.setForeground(Color.WHITE);
        frame.add(label);

        inputCount = new JTextArea();
        inputCount.setBounds(330, 650, 50, 30);
        inputCount.setFont(new Font("Arial Black", Font.BOLD, 18));
        inputCount.setEditable(false);
        frame.add(inputCount);

        addToReceiptButton = new JButton("Add");
        addToReceiptButton.setBounds(400, 640, 70, 50);
        addToReceiptButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
        addToReceiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductToReceipt();
            }
        });
        frame.add(addToReceiptButton);

        payButton = new JButton("Pay");
        payButton.setBounds(480, 640, 70, 50);
        payButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReceiptAndSaveToFile();
            }
        });
        frame.add(payButton);
    }

    // Add products to receipt and track them
    public void addProductToReceipt() {
        if (!currentProductName.isEmpty() && currentProductCount > 0) {
            purchasedProducts.put(currentProductName,
                    purchasedProducts.getOrDefault(currentProductName, 0) + currentProductCount);

            receipt.addToReceipt(currentProductName + " " + currentProductCount + " st " +
                    getProductPrice(currentProductName) * currentProductCount + " kr");
            clearProductSelection();
        }
    }

    // Generates receipt summary and saves to file
    public void generateReceiptAndSaveToFile() {
        double totalPrice = 0.0;
        StringBuilder receiptSummary = new StringBuilder();
        
        receiptSummary.append("\n-------- RECEIPT SUMMARY --------\n");

        for (Map.Entry<String, Integer> entry : purchasedProducts.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            double productPrice = getProductPrice(productName);
            double totalProductPrice = productPrice * quantity;

            receiptSummary.append(String.format("%-20s %3d x %-7.2f = %-8.2f kr\n", productName, quantity, productPrice, totalProductPrice));
            totalPrice += totalProductPrice;
        }

        receiptSummary.append("\n----------------------------------\n");
        receiptSummary.append(String.format("TOTAL PRICE: %33.2f kr\n", totalPrice));
        receiptSummary.append("**********************************\n");
        receiptSummary.append("     THANK YOU FOR YOUR PURCHASE!     \n");
        receiptSummary.append("**********************************\n");

        String receiptString = receiptSummary.toString();
        receipt.addToReceipt(receiptString);  // Add to the GUI receipt area

        // Write to the file
        String filePath = "C:\\Users\\Leahb\\_Dev\\posstart-main\\src\\output.txt";
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {
            writer.write(receiptString);
            JOptionPane.showMessageDialog(null, "File has been saved successfully!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        purchasedProducts.clear();  // Clear product list after saving
    }

    // Returns the price of the product (hardcoded)
    private double getProductPrice(String productName) {
        switch (productName) {
            case "Kaffe":
                return 51.00;
            case "Nalle":
                return 110.00;
            case "Mugg":
                return 75.00;
            case "Chips":
                return 20.00;
            case "Yoghurt":
                return 15.00;
            case "Daim":
                return 10.00;
            default:
                return 0.0;  // Product not found
        }
    }

    // Update product name and count
    public void updateProductSelection(String productName) {
        if (currentProductName.equals(productName)) {
            currentProductCount++;
        } else {
            currentProductName = productName;
            currentProductCount = 1;
        }
        inputProductName.setText(currentProductName);
        inputCount.setText(String.valueOf(currentProductCount));
    }

    // Clear product selection after adding to receipt
    private void clearProductSelection() {
        currentProductName = "";
        currentProductCount = 0;
        inputProductName.setText("");
        inputCount.setText("");
    }

    public static void main(String[] args) {
        new CashRegister();
    }
}
