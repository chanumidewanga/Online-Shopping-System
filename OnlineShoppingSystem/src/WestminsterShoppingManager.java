import java.io.*;
import java.util.*;
import java.util.Comparator;


//Create the Shopping manager interface for the WestminsterShoppingManager
public class WestminsterShoppingManager implements ShoppingManager{

    //Assign 50 products as maximum
    private static final int MAX_PRODUCTS_COUNT = 50;
    private static final String PRODUCTS_FILE_NAME = "SavedProducts.txt";
    private static final String USER_DETAILS_FILE_NAME = "userDetails.txt";

    //Create a list of array to store products
    public static ArrayList<Product> productList = new ArrayList<>(MAX_PRODUCTS_COUNT);
    public ArrayList<Product> getListOfProducts() {
        return productList;
    }

    //Create a list of array to store user details
    public static ArrayList<User> userInfo = new ArrayList<>();
    public ArrayList<User> getUserInfo(){return userInfo;}


    //Create the comparator interface for the ProductNameComparator
    public class ProductNameComparator implements Comparator<Product> {
        @Override
        public int compare(Product product1, Product product2) {
            return product1.getProductId().compareTo(product2.getProductId());
        }
    }

    public static void main(String[] args) {

        //Create manager object
        WestminsterShoppingManager manager = new WestminsterShoppingManager();

        manager.loadProducts(PRODUCTS_FILE_NAME);
        manager.loadUserDetails(USER_DETAILS_FILE_NAME);

        boolean start = true;
        while (start) {

            //Display menu
            System.out.println("\n-------------------------MENU-------------------------");
            System.out.println("1 - Add new product\n2 - Delete product\n3 - Print product list\n4 - " +
                    "Save to file\n5 - Register a New User\n6 - Display Graphical User Interface\n7 - Exit");
            System.out.println("---------------------------------------------------------");

            //Get user input
            System.out.println("\nPlease insert an option from the menu: ");

            //try-catch block for user input validation
            try {
                Scanner input = new Scanner(System.in);
                int option = input.nextInt();

                switch (option) {
                    case 1:
                        if (manager.getListOfProducts().size() < MAX_PRODUCTS_COUNT) {
                            manager.addProduct();
                        } else {
                            System.err.println("Products list is full. Try removing some products and try again!");
                        }
                        break;
                    case 2:
                        manager.deleteProduct();
                        break;
                    case 3:
                        manager.printProducts();
                        break;
                    case 4:
                        manager.saveProducts("SavedProducts.txt");
                        break;
                    case 5:
                        manager.registerNewUser();
                        break;
                    case 6:
                        start = false;
                        System.out.println("Loading GUI...");
                        HomeGUI frame = new HomeGUI(userInfo, true);
                        break;
                    case 7:
                        start = false;
                        System.out.println("Program quitting...");
                        break;
                    default:
                        System.out.println("Invalid Input, Try again");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
            }
        }
    }

    //create method for add product
    @Override
    public void addProduct() {

        Scanner input = new Scanner(System.in);

        //Get product type as user input
        String productType;

        while (true){
            // Prompt the user for input
            System.out.println("Enter the product type from below.\n(E - Electronics, C - Clothing):");
            productType = input.next().toUpperCase(); ; //convert the input to uppercase

            //Validate user input
            if("E".equals(productType)||"C".equals(productType)){
                break;
            }else{
                System.out.println("Invalid input.Try again\n");
            }
        }

        //Get product ID as user input
        String productID;

        while(true) {
            Scanner input1 = new Scanner(System.in);

            System.out.print("\nEnter the product ID: ");
            productID = input1.next();

            //Taking and storing the product IDs in products list
            boolean productIdTaken = false;

            //Create enhanced for loop to iterate over each product in productList
            for (Product p : productList){
                if (p.getProductId().equals(productID)){
                    productIdTaken = true;
                }
            }
            //Check the availability of the product ID
            if (productIdTaken){
                System.err.println("The product ID is already Taken");
            } else {
                break;
            }
        }

        //Get product name as user input
        System.out.print("Enter the product name: ");
        String productName = input.next();

        //Get number of items as user input
        int numberOfItems;

        while (true) {
            //try-catch block for input validation
            try {
                System.out.print("Enter the number of items available: ");
                numberOfItems = input.nextInt();
                break; // Exit the loop if input is valid


            }catch (InputMismatchException e){
                System.out.println("Invalid input.");
                input.nextLine();
            }
        }

        //Get product price as user input
        double productPrice;

        while (true) {
            try {
                System.out.print("Enter the price of the product: ");
                productPrice = input.nextDouble();
                break; // Exit the loop if input is valid

            }catch (InputMismatchException e){
                System.out.println("Invalid input.");
                input.nextLine();
            }

        }


        switch (productType) {


            //Get additional inputs for electronics category
            //Get product brand as user input
            case "E":
                System.out.print("Enter the brand of the product: ");
                String productBrand = input.next();

                //Get product warranty period as user input
                String productWarranty;

                while (true) {
                    try{
                        System.out.print("Enter the warranty period (months): ");
                        productWarranty = input.next();
                        break; // Exit the loop if input is valid

                    }catch (InputMismatchException e){
                        System.out.println("Invalid input.");
                        input.next();
                    }
                }

                Product newElectronicProduct = new Electronics(productID, productName, numberOfItems,
                        productPrice, productBrand, productWarranty);
                productList.add(newElectronicProduct);
                System.out.println("\nProduct added successfully !");
                break;

            //Take additional inputs for clothing category
            case "C":
                //Get product size as user input
                List<String> validSizes = Arrays.asList("UK8", "UK10", "UK12", "UK14");
                String clothingSize;

                while (true) {
                    System.out.print("Enter the size of the clothing (UK8, UK10, UK12, UK14): ");
                    clothingSize = input.next().toUpperCase().trim();
                    if (validSizes.contains(clothingSize)) {
                        break;
                    }

                }
                //Get product colour as user input
                System.out.print("Enter the color of the clothing: ");
                String clothingColor = input.next();

                Product newClothingProduct = new Clothing(productID, productName, numberOfItems,
                        productPrice, clothingSize, clothingColor);
                productList.add(newClothingProduct);
                System.out.println("\nProduct added successfully !");
                break;
        }
    }

    //Method for delete products
    @Override
    public void deleteProduct() {
        //Get product ID as a user input
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the product ID");
        String productID = sc.next();

        Product productArray2 = null;
        Iterator var4 = productList.iterator();

        while(var4.hasNext()) {
            Product deleteFromList = (Product)var4.next();
            if (deleteFromList.getProductId().equals(productID)) {
                productArray2 = deleteFromList;
                System.out.println(deleteFromList.getProductId());
                break;
            }
        }

        if (productArray2 == null) {
            System.out.println("No products found.");
        } else {
            productList.remove(productArray2);
            System.out.println("The product: " + productArray2.getProductName() + " has been removed.");
            System.out.println("There are only  " + productList.size() + " products left.");
        }
    }


    //Method for print products
    @Override
    public void printProducts() {

        if (productList.isEmpty()) {
            System.out.println("No products available.");
        } else {

            // Sort the productList in alphabetical order
            Collections.sort(productList, new ProductNameComparator());

            for (int i = 0; i < productList.size(); i++){
                Product value = productList.get(i);
                System.out.println(value);
            }
        }
    }

    //Method for save products to a file
    @Override
    public void saveProducts(String fileName) {
        //Create an object from ObjectInputStream by giving a file name
        try (ObjectOutputStream savedFile = new ObjectOutputStream(new FileOutputStream(fileName))) {
            savedFile.writeObject(productList);
            System.out.println("Product Saved Successfully !");
        } catch (IOException e){
            System.err.println("Saving failed !" + e.getMessage());
        }
    }

    //Method to read products from file
    @Override
    public void loadProducts(String fileName) {
        //Create an object from ObjectInputStream by giving a file name
        try(ObjectInputStream loadFile = new ObjectInputStream(new FileInputStream(fileName))){
            productList = (ArrayList<Product>) loadFile.readObject();
        }catch (IOException | ClassNotFoundException e){
                System.err.println("Loading Failed!");
        }
    }

    //Method for register new user
    public void registerNewUser(){
        Scanner input = new Scanner(System.in);
        String username;
        while(true) {
            //Get username as input
            System.out.println("Enter Username :");
            username = input.next();

            //Check if the username is already taken
            boolean isTaken = false;
            for (User u : userInfo){
                if (u.getUsername().equals(username)){
                    isTaken = true;
                }
            }
            if (isTaken){
                System.err.println("Username is already taken. Try a different one.");
            }else {
                break;
            }
        }
        while(true) {
            //Get password as input
            Scanner input1 = new Scanner(System.in);
            System.out.println("Enter Password : ");
            String IDnumber = input1.next();

            //Add user details into userInfo arraylist
            User newUser = new User(username, IDnumber,0);
            userInfo.add(newUser);
            saveUserDetails("userDetails.txt");
            break;
        }
    }

    //Method for write user details to a file
    public void saveUserDetails(String fileName){
        try (ObjectOutputStream savedFile = new ObjectOutputStream(new FileOutputStream(fileName))) {
            savedFile.writeObject(userInfo);
            System.out.println("Registration Successful !");
        } catch (IOException e){
            System.err.println("Registration failed !" + e.getMessage());
        }
    }

    //Method for read user details from the file
    public void loadUserDetails(String fileName){
        try(ObjectInputStream loadFile = new ObjectInputStream(new FileInputStream(fileName))){
            userInfo = (ArrayList<User>) loadFile.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.err.println("Loading Failed !" + e.getMessage());
        }
    }
}
