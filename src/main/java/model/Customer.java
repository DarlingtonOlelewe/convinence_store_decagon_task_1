package model;

import enums.Gender;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Customer extends Person{
    private Map<Product, Integer> cart;

    /* Storage: Cart, Warehouse, BoughtGoods, Category
    //if (qualification != Qualification.HND || qualification != Qualification.BSC)
    */
    private double cost = 0;

    public Customer(String firstName, String lastName, String Email, Gender gender) {
        super(firstName, lastName, Email, gender);
        this.cart = new HashMap<>();
    }

    public void buyProducts(String name, int units, Store store){

        for( Map.Entry<Product, Integer> productInStore: store.getProductMap().entrySet()){
            if(productInStore.getKey().getName().equals(name) && productInStore.getValue() >= units ){
                cart.put(productInStore.getKey(), units);
            }
        }

    }

    public double getPriceOfGoods(){
        for( Map.Entry<Product, Integer> customerGoods: cart.entrySet()){
                cost += (customerGoods.getKey().getPrice() * (double) customerGoods.getValue());
        }
        return cost;
    }

    public Map<Product, Integer> viewProductsInstore(Store store){
        return store.getProductMap();
    }

    public Map<Product, Integer> viewCart() {
        return cart;
    }


}
