import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProductButton {
    private JPanel panel;
    private Receipt receipt;
    private CashRegister register;  // Referens till kassasystemet

    public ProductButton(Receipt receipt, CashRegister register) {
      this.receipt = receipt;
      this.register = register;  // Spara referensen till CashRegister

      // Skapar snabbvalspanelen för produkter
      panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
      panel.setPreferredSize(new Dimension(600, 600));
      panel.setBackground(Color.GRAY);
      panel.setBounds(0, 0, 600, 600);

      // Skapar knapparna
      addButton(new Product("Kaffe", 51.00));
      addButton(new Product("Nalle", 110.00));
      addButton(new Product("Mugg", 75.00));
      addButton(new Product("Chips", 20.00));
      addButton(new Product("Yoghurt", 15.00));
      addButton(new Product("Daim", 10.00));
  }

  // Vi skickar produktnamnet till CashRegister när knappen trycks
  private void addButton(Product product) {
      JButton button = new JButton(product.getName());
      button.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              // Kontrollera att referensen till CashRegister är korrekt
              System.out.println("Knappen trycks för: " + product.getName());
              if (register != null) {
                  register.updateProductSelection(product.getName());
              } else {
                  System.out.println("Fel: CashRegister-referensen är null!");
              }
          }
      });
      panel.add(button);
  }

  public JPanel getPanel() {
      return panel;
  }

}
