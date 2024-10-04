import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FontFormatException;
import java.awt.TextArea;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.event.*;

public class CashRegister implements ActionListener {

    // Komponenter som används i gränssnittet
    JFrame frame;  // Huvudfönstret
    JTextArea receipt;  // Textområde för att visa kvittot
    JButton kaffeButton, nalleButton, muggButton, chipsButton, vaniljYoghurtButton, daimButton; // Snabbvalsknappar för produkter
    JTextArea inputProductName, inputCount;  // Textområden för att skriva in produktnamn och antal
    JButton addToReceiptButton, payButton;  // Knappar för att lägga till produkt eller betala
    String selectedProduct = "";
    int selectedProductCount = 0;

    public CashRegister(){
        // Konstruktor för att initialisera fönstret och skapa gränssnittet
        frame = new JFrame("IOT24 POS");

        // Skapa och lägg till olika områden i fönstret
        createReceiptArea();
        createQuickButtonsArea();
        createAddArea();

        // Ställ in bakgrundsfärg och storlek på fönstret
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(1000,800); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);        
    }

    private void createAddArea(){
        // Skapa ett textfält där användaren kan skriva in produktnamn
        inputProductName = new JTextArea();
        inputProductName.setBounds(20,650,300,30);
        inputProductName.setFont(new Font("Arial Black", Font.BOLD, 18));
        frame.add(inputProductName);

        // Variabler för att spara temporära produkter och antal som läggs till via Add-knappen
        

       
        

        // Skapa en etikett för att visa texten "Antal"
        JLabel label = new JLabel("Antal");
        label.setBounds(340,625,300,30);
        label.setForeground(Color.WHITE);  // Etikettens textfärg sätts till vit
        frame.add(label);

        // Skapa ett textfält för att skriva in antalet av produkten
        inputCount = new JTextArea();
        inputCount.setBounds(330,650,50,30);
        inputCount.setFont(new Font("Arial Black", Font.BOLD, 18));
        frame.add(inputCount);

        


        // Knapp för att lägga till produkten till kvittot
        addToReceiptButton = new JButton("Add");
        addToReceiptButton.setBounds(400,640,70,50);
        addToReceiptButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
        frame.add(addToReceiptButton);

        addToReceiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hämta produktnamn och antal från användaren
                selectedProduct = inputProductName.getText();
                selectedProductCount = Integer.parseInt(inputCount.getText());
                
                // Sätt tillbaka textfälten till tomma strängar för nästa produkt
                inputProductName.setText("");
                inputCount.setText("");
            }
        });
        

        // Knapp för att slutföra och betala för produkterna
        payButton = new JButton("Pay");
        payButton.setBounds(480,640,70,50);
        payButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
        frame.add(payButton);

        // Räknare för att hålla koll på hur många gånger "Kaffe"-knappen har klickats
        final int[] kaffeCounter = {0};
        kaffeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Öka räknaren varje gång knappen klickas
                kaffeCounter[0]++;
                // Här kan etiketten eller andra komponenter uppdateras med nya värden
            }
        });
        // Räknare för att hålla koll på hur många gånger "Kaffe"-knappen har klickats
        final int[] daimCounter = {0};
        daimButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sätt textfältet för produktnamn till "Daim"
                inputProductName.setText("Daim");
        
                // Öka antalet av Daim varje gång knappen trycks
                selectedProductCount++;
        
                // Uppdatera textfältet för antal med det nya värdet
                inputCount.setText(String.valueOf(selectedProductCount));
            }
        });
        kaffeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sätt textfältet för produktnamn till "Daim"
                inputProductName.setText("Kaffe");
        
                // Öka antalet av Daim varje gång knappen trycks
                selectedProductCount++;
        
                // Uppdatera textfältet för antal med det nya värdet
                inputCount.setText(String.valueOf(selectedProductCount));
            }
        });
        
        addToReceiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hämta produktnamn och antal från användaren
                String productName = inputProductName.getText();
                int count = Integer.parseInt(inputCount.getText());
        
                // Du kan använda denna information senare när användaren trycker "Pay"
                // Det lagras här och väntar på att skickas till kvittot
                if (productName.equals("Daim")) {
                    daimCounter[0] += count; // Uppdatera räknaren baserat på antal
                }
        
                // Här kan du lägga till hantering för fler produkter om det behövs
            }
        });
        
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Lägg till produkter till kvittot när användaren trycker "Pay"
                receipt.append("                     LEAHS! SUPERSHOP\n");
                receipt.append("----------------------------------------------------\n");
                receipt.append("\n");
        
                // Lägg till vald produkt om den finns
                if (!selectedProduct.isEmpty() && selectedProductCount > 0) {
                    if (selectedProduct.equals("Daim")) {
                        receipt.append(selectedProduct + "                    " + selectedProductCount + " *     15.00    =   " + (selectedProductCount * 15) + ".00\n");
                    }
                    // Du kan lägga till fler produkter med liknande logik här om fler produkter ska hanteras
                }
        
                receipt.append("----------------------------------------------------\n");
                receipt.append("TACK FÖR DITT KÖP\n");
        
                // Återställ temporära variabler efter betalning
                selectedProduct = "";
                selectedProductCount = 0;
                
                Timer timer = new Timer(1000 * 5, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt){
                        receipt.setText("");
                        receipt.setText("Tack bitch!");
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
        addToReceiptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                // Lägg till produkter till kvittot när användaren trycker "Pay"
                receipt.append("                     STEFANS SUPERSHOP\n");
                receipt.append("----------------------------------------------------\n");
                receipt.append("\n");
        
                // Lägg till vald produkt om den finns
                if (!selectedProduct.isEmpty() && selectedProductCount > 0) {
                    if (selectedProduct.equals("Daim")) {
                        receipt.append(selectedProduct + "                    " + selectedProductCount + " *     15.00    =   " + (selectedProductCount * 15) + ".00\n");
                    }
                    // Du kan lägga till fler produkter med liknande logik här om fler produkter ska hanteras
                }
        
                receipt.append("----------------------------------------------------\n");
                receipt.append(selectedProduct + "                    " + selectedProductCount + " *     15.00    =   " + (selectedProductCount * 15) + ".00\n");
                
        
                // Återställ temporära variabler efter betalning
                selectedProduct = "";
                selectedProductCount = 0;
                
                
            }
        });
        
        
        
        
    }

    private void createQuickButtonsArea() {
        // Skapa en panel för att hålla snabbknapparna för produkterna
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setBackground(Color.CYAN);  // Panelens bakgrundsfärg sätts till grön
        panel.setPreferredSize(new Dimension(600, 600));

        // Skapa och lägg till snabbknapparna för olika produkter
        kaffeButton = new JButton("Kaffe");
        panel.add(kaffeButton);

        nalleButton = new JButton("Nalle");
        panel.add(nalleButton);

        muggButton = new JButton("Mugg");
        panel.add(muggButton);

        chipsButton = new JButton("Chips");
        panel.add(chipsButton);

        vaniljYoghurtButton = new JButton("Yoghurt");
        panel.add(vaniljYoghurtButton);

        daimButton = new JButton("Daim");
        panel.add(daimButton);

        // Ställ in panelens position och storlek
        panel.setBounds(0, 0, 600, 600);
        frame.add(panel);
    }

    private void createReceiptArea() {
        // Skapa ett textområde för att visa kvittot
        receipt = new JTextArea();
        receipt.setSize(400,400); 
        receipt.setLineWrap(true);  // Gör så att texten bryts på nästa rad om den blir för lång
        receipt.setEditable(false); // Gör kvittoområdet icke-redigerbart
        receipt.setVisible(true);
        receipt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));


        // Lägg till en scrollningsbar area för kvittot
        JScrollPane scroll = new JScrollPane(receipt);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(600, 0, 400, 1000);

        // Lägg till scrollområdet i fönstret
        frame.add(scroll);    
    }

    public void run() {
        // Lägg till en ActionListener för "Pay"-knappen
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // När "Pay"-knappen trycks, lägg till kvittoinformation i textområdet
                receipt.append("                     STEFANS SUPERSHOP\n");
                receipt.append("----------------------------------------------------\n");
                receipt.append("\n");
                receipt.append("Kvittonummer: 122        Datum: 2024-09-01 13:00:21\n");
                receipt.append("----------------------------------------------------\n");
                receipt.append("Kaffe Gevalia           5 *     51.00    =   255.00  \n");
                receipt.append("Nallebjörn              1 *     110.00   =   110.00  \n");
                receipt.append("Total                                        ------\n");
                receipt.append("                                             306.00\n");
                receipt.append("TACK FÖR DITT KÖP\n");
            }
        });
    }            

    @Override
    public void actionPerformed(ActionEvent e) {
        // Denna metod måste implementeras eftersom klassen implementerar ActionListener, 
        // men just nu är den inte implementerad.
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
