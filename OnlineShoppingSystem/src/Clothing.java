
//Inherit clothing sub class from super class product
public class Clothing extends Product{
    private String size;
    private String color;

    public Clothing(String productId, String productName, int noOfItemsAvailable, double price ,String size, String color) {
        super(productId,productName,noOfItemsAvailable,price);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return super.toString() + ", Size = " + size +
                ", Color = " + color + " }";
    }
}
