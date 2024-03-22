import java.io.Serializable;

abstract public class Product implements Serializable {
    private static final long serialVersionUID = -4997024102041845121L;
    private String productId;
    private String productName;
    private int noOfItemsAvailable;
    private double price;

    public Product(String productId, String productName, int noOfItemsAvailable, double price) {
        this.productId = productId;
        this.productName = productName;
        this.noOfItemsAvailable = noOfItemsAvailable;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }

    public int getNoOfItemsAvailable() {
        return noOfItemsAvailable;
    }

    public double getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "Product{" +
                " product ID ='" + productId + '\'' +
                ", Product Name ='" + productName + '\'' +
                ", Available Items =" + noOfItemsAvailable +
                ", price = " + price;
    }
}


