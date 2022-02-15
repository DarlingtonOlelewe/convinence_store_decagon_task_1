package model;


public class Product {
    private String name;
    private double price;
    private final String id;
    private static int count = 0;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.id = "Pdct - " + count++;
    }

    public String getName() {
        return name;
    }


    public String getProductId(){
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
