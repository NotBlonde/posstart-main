import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    private Map<String, Integer> purchasedProducts;  // Håller koll på produkter och antal

    public CashRegister() {
        // Skapar huvudramen (fönstret)
        frame = new JFrame("IOT24 POS");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Skapar kvittoutskrift
        receipt = new Receipt();
        frame.add(receipt.getScrollPane());

        // Skapar snabbvalsknappar och skickar referensen till CashRegister (this)
        quickButtonsPanel = new ProductButton(receipt, this);  // Skicka referens till detta CashRegister-objekt
        frame.add(quickButtonsPanel.getPanel());

        // Initierar listan över köpta produkter
        purchasedProducts = new HashMap<>();

        // Skapar manuell input och betalning
        createAddArea();

        frame.setVisible(true);
    }

    // Manuell input för produktnamn och antal
    private void createAddArea() {
        inputProductName = new JTextArea();
        inputProductName.setBounds(20, 650, 300, 30);
        inputProductName.setFont(new Font("Arial Black", Font.BOLD, 18));
        inputProductName.setEditable(false);
        frame.add(inputProductName);

        JLabel label = new JLabel("Antal");
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
                endPayment();
            }
        });
        frame.add(payButton);
    }

    // Metod som lägger till produkt och antal till kvittot
    public void addProductToReceipt() {
      Product product = new Product(currentProductName, currentProductCount);
        if (!currentProductName.isEmpty() && currentProductCount > 0) {
            // Uppdatera köpta produkter
            purchasedProducts.put(currentProductName, 
                purchasedProducts.getOrDefault(currentProductName, 0) + currentProductCount);

            // Lägg till i kvittot
            receipt.addToReceipt(currentProductName + " " + currentProductCount + " st " + getProductPrice(currentProductName ) * currentProductCount + "kr");
            clearProductSelection();
        }
    }

    // Metod som skriver ut kvittosammanfattning när betalningen avslutas
    public void endPayment() {
        double totalPrice = 0.0;
        receipt.addToReceipt("\n-------- KVITTOSAMMANFATTNING --------\n");

        // Iterera genom alla köpta produkter och skriv ut varje produkts totalpris
        for (Map.Entry<String, Integer> entry : purchasedProducts.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            double productPrice = getProductPrice(productName);
            double totalProductPrice = productPrice * quantity;

            receipt.addToReceipt(productName + " " + quantity + " st x " + productPrice + " kr = " + totalProductPrice + " kr");
            totalPrice += totalProductPrice;
        }

        receipt.addToReceipt("\n--------------------------------------");
        receipt.addToReceipt("TOTALT PRIS: " + totalPrice + " kr");
        receipt.addToReceipt("TACK FÖR DITT KÖP!\n");

        // Rensa produktlistan efter betalningen
        purchasedProducts.clear();
    }

    // Returnerar priset för en produkt (hårdkodad för enkelhet)
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
                return 0.0;  // Om produkten inte finns i listan
        }
    }

    // Uppdaterar produktnamn och antal när en knapp trycks
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

    // Rensar produktval efter att produkten lagts till kvittot
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
