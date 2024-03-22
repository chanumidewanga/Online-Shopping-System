
//Inherit Electronics sub-class from super class product
public class Electronics extends Product{
    private String brand;
    private String warrantyPeriod;

    public Electronics(String productId, String productName, int noOfItemsAvailable, double price ,String brand, String warrantyPeriod) {
        super(productId,productName,noOfItemsAvailable,price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }
    public String getBrand() {
        return brand;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }


    @Override
    public String toString() {
        return super.toString() + ", Brand = " + brand +
                ", Warranty Period = " + warrantyPeriod + '}';
    }
}
