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
        manager.addProductsToStore(product2, 5, easyBuy);
        Joy = new Customer("Joy", "Uche", "Joy.com", Gender.FEMALE);

    }

    @Test
    public void buyProductsShouldAllowACustomerAddToTheirCart() throws NotEnoughtInStock {
        Joy.buyProducts("Beer", 3, easyBuy);
        Joy.buyProducts("diapers", 40, easyBuy);

        assertEquals(2, Joy.viewCartMap().size());


    }

    @Test
    public void shouldReturnNotInStock(){
        assertThrows(NotEnoughtInStock.class, ()-> {Joy.buyProducts("Beer",6, easyBuy);});
        assertEquals(0, Joy.viewCartMap().size());
    }

    @Test
    public void getPriceOfGoodsShouldReturnCostOfGoodsInStore() throws NotEnoughtInStock {
        Joy.buyProducts("Beer", 3, easyBuy);
        Joy.buyProducts("diapers", 40, easyBuy);

        int price = (int) Joy.getPriceOfGoods();

       assertEquals(81500, price);
    }

    @Test
    public void viewProductsInstoreShouldGiveTheNumberOfProductInStore() {

        Joy.viewProductsInstore(easyBuy);
        assertEquals(2, easyBuy.getProductMap().size());
    }

    @Test
    public void viewCartShouldGiveTwo() throws NotEnoughtInStock {
        Joy.buyProducts("Beer", 3, easyBuy);
        Joy.buyProducts("diapers", 40, easyBuy);

        assertEquals(2, Joy.viewCartMap().size());

    }
}