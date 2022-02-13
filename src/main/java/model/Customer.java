package model;

import enums.Gender;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Customer extends Person{
    private Map<Product, Integer> cart;
    private double cost = 0;

    public Customer(String firstName, String lastName, String Email, Gender gender) {
        super(firstName, lastName, Email, gender);
        this.cart = new HashMap<>();
    }


    public void buyProducts(String name, int units, Store store){

        for( Map.Entry<Product, Integer> each: store.getProductMap().entrySet()){
            if(each.getKey().getName().equals(name) && each.getValue() >= units ){
                cart.put(each.getKey(), units);
            }
        }

    }

    public double priceOfGoods(){
        for( Map.Entry<Product, Integer> each: cart.entrySet()){
                cost += (each.getKey().getPrice() * (double) each.getValue());
        }
        return cost;
    }

    public Map<Product, Integer> viewProductsInstore(Store store){
        return store.getProductMap();
    }

    public void requestForCheckOut(Store store){

    }


    public Map<Product, Integer> viewCart() {
        return cart;
    }
}
