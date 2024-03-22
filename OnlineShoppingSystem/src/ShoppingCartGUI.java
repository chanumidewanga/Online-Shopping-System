import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


//Create shopping cart class
public class ShoppingCartGUI {
    private  String productID;
    private String productDetails;
    private int quantity;
    private double price;
    private double pricePerProduct;
    private String itemCategory;
    JFrame shoppingFrame;
    JTable cartTable;
    DefaultTableModel tableModel;
    JScrollPane tableScrollPane;
    JPanel finalPricePanel;

    int loggedUserIndex;

    public ShoppingCartGUI(String productID, String productDetails, double pricePerProduct, int quantity, String itemCategory) {
        this.productID = productID;
        this.productDetails = productDetails;
        this.pricePerProduct = pricePerProduct;
        this.quantity = quantity;
        this.itemCategory = itemCategory;
    }

    public String getProductID() {
        return productID;
    }
    public String getItemCategory() {
        return itemCategory;
    }
    public double getPricePerProduct() {
        return pricePerProduct;
    }
    public String getProductDetails() {
        return productDetails;
    }
    public int getQuantity() {
        return quantity;
    }
    public void addQuantity(){
        quantity++;
    }
    public ShoppingCartGUI(int loggedUser){

        loggedUserIndex = loggedUser;

        shoppingFrame = new JFrame("Shopping Cart");
        shoppingFrame.setLayout(new BorderLayout()); //Set component arrangement in the container
        shoppingFrame.setSize(600,400); //Set the size of the frame
        shoppingFrame.setResizable(false); //Set the ability to minimize/maximize
        shoppingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close
        shoppingFrame.setLocationRelativeTo(null); //Set the position relative to the screen
        shoppingFrame.setVisible(true);

        finalPricePanel = new JPanel(new GridLayout(0,1));


        String[] columnName = {"Product Details", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columnName,0);
        cartTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(cartTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(20,30,0,30));
        shoppingFrame.add(tableScrollPane, BorderLayout.CENTER);

        cartTable.setRowHeight(40);
        ArrayList<ShoppingCartGUI> shoppedProducts = new ArrayList<ShoppingCartGUI>();
        ProductPageGUI productPage = new ProductPageGUI(false, loggedUserIndex);
        shoppedProducts = productPage.getCart();
        populateTable(shoppedProducts);

        double totalPrice = 0;
        double newUserDiscount = 0;
        double categoryDiscount = 0;
        double finalPrice = 0;
        for (ShoppingCartGUI cart: shoppedProducts){
            totalPrice = totalPrice + ( cart.getPricePerProduct() * cart.getQuantity());
        }

        finalPricePanel.setPreferredSize(new Dimension(600,100));
        finalPricePanel.add(Box.createVerticalStrut(30)); //Creates a fixed vertical space
        finalPricePanel.setBorder(BorderFactory.createEmptyBorder(0,30,10,0));
        finalPricePanel.add(new JLabel("Total Price:                               " + "\u00A3 " + totalPrice));


        WestminsterShoppingManager manager = new WestminsterShoppingManager();

        HomeGUI login = new HomeGUI();
        String username = login.getNowLogged();

        int count = manager.getUserInfo().get(loggedUserIndex).getLoginCount();

        System.out.println("index: " + loggedUserIndex + " count: " + count);

        if (count == 1) {
            newUserDiscount = totalPrice * 0.1;
            finalPricePanel.add(new JLabel("First Purchase Discount:        " + "\u00A3 " + newUserDiscount));

        }


        int ElecCount = 0;
        int ClothCount = 0;
        for (ShoppingCartGUI cart : shoppedProducts){
            if (cart.getItemCategory().equals("Electronics")){
                ElecCount += cart.getQuantity();
            } else {
                ClothCount += cart.getQuantity();
            }
        }
        if (ElecCount >= 3){
            categoryDiscount = totalPrice * 0.2;
            finalPricePanel.add(new JLabel("Same Category Discount:            " + "\u00A3 " + categoryDiscount));
        } else if (ClothCount >= 3){
            categoryDiscount = totalPrice * 0.2;
            finalPricePanel.add(new JLabel("Same Category Discount:     " + "\u00A3 " + categoryDiscount));
        }
        finalPrice = totalPrice - newUserDiscount - categoryDiscount;
        finalPricePanel.add(new JLabel("Final Price:                                " + "\u00A3 " + finalPrice));
        shoppingFrame.add(finalPricePanel,BorderLayout.SOUTH);
    }

    public void populateTable(ArrayList<ShoppingCartGUI> cartList){
        tableModel.setRowCount(0);
        for (ShoppingCartGUI shoppingCart : cartList){
            ShoppingCartGUI cart = (ShoppingCartGUI) shoppingCart;
            Object [] rowData = {cart.getProductID() + " " + cart.getProductDetails()
            ,cart.getQuantity(),cart.getPricePerProduct() * cart.getQuantity()};
            tableModel.addRow(rowData);
        }
    }

}
