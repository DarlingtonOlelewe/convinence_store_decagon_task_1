package model;

import applications.Applicants;
import enums.Gender;
import enums.Qualifications;
import enums.Role;
import exceptions.NotEnoughtInStock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
    Staff manager;
    Store easyBuy;
    Product product1;
    Product product2;
    Customer Joy;
    @Before
    public void setUp() throws Exception {

        manager = new Staff("Chisom", "Obidike", "decagon.com", Gender.FEMALE, Role.MANAGER);

        Applicants James = new Applicants("James", "John", "John.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
        easyBuy = new Store("Easy Buy",manager);
        product1 = new Product("diapers", 2000);
        product2 = new Product("Beer", 500);
        manager.addProductsToStore(product1, 40, easyBuy);
        manager.addProductsToStore(product2, 58, easyBuy);
        Joy = new Customer("Joy", "Uche", "Joy.com", Gender.FEMALE);


        //howEver they can view products. which allows them to know the names of the products



        //They can view their cart at anytime. and also its amount. this helps them have an estimate of price
        System.out.println(Joy.viewCartMap());
        System.out.println();


        //Products in store does not reduce even though its on customers cart.
        //It reduces only when paid for.
        System.out.println(easyBuy.getProductMap());

        //remeber James (formally an applicant is now a staff and can be accessed through staffList.
        //basically the company can only one manager and one or more cashiers.
        //It's a nobrainer in our case to know that our James is the second staff and also a cashier
        //So he is created from the list not as a new Staff
        Staff james = easyBuy.getStaffList().get(1);

        //for James to sell product to Joy, he needs Joy's cart which are in the store, and Payment
        //This enables him to cross-check goods and remove from his store, and generate reciepts
        james.sellProducts(Joy,200000,easyBuy);

        //Products in Store are reduced only when they are paid for
        System.out.println(easyBuy.getProductMap());

        System.out.println(Joy);
    }

    @Test
    public void buyProducts() throws NotEnoughtInStock {
        Joy.buyProducts("Beer", 3, easyBuy);
        Joy.buyProducts("sugar", 5, easyBuy);
        Joy.buyProducts("diapers", 40, easyBuy);
    }

    @Test
    public void getPriceOfGoods() {
    }

    @Test
    public void viewProductsInstore() {
        Joy.viewProductsInstore(easyBuy);
    }

    @Test
    public void viewCart() {
        Joy.getPriceOfGoods();
    }
}