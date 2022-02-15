package model;

import enums.Gender;
import exceptions.NotEnoughtInStock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Customer extends Person{
    private Map<Product, Integer> cartMap;




    public Customer(String firstName, String lastName, String Email, Gender gender) {
        super(firstName, lastName, Email, gender);
        this.cartMap = new HashMap<>();
    }

    public void buyProducts(String name, int units, Store store) throws NotEnoughtInStock {
        int count = 0;
        for( Map.Entry<Product, Integer> productInStore: store.getProductMap().entrySet()){

            if(productInStore.getKey().getName().equals(name) && productInStore.getValue() >= units ){
                cartMap.put(productInStore.getKey(), units);
                count++;
            }

        }
            if(count == 0) throw new NotEnoughtInStock("Product not enough in Stock or unAvailable at all");
    }

    public double getPriceOfGoods(){
        int cost = 0;
        for( Map.Entry<Product, Integer> customerGoods: cartMap.entrySet()){
                cost += (customerGoods.getKey().getPrice() * (double) customerGoods.getValue());
        }
        return cost;
    }

    public Map<Product, Integer> viewProductsInstore(Store store){
        return store.getProductMap();
    }

    public void clearCartMap(){
        cartMap.clear();
    }

    public Map<Product, Integer> viewCartMap() {
        return cartMap;
    }





}
