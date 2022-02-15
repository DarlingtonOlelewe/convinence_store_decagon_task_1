package model;

import applications.Applicants;
import enums.Gender;
import enums.Qualifications;
import enums.Role;
import exceptions.ApplicantAlreadyExist;
import exceptions.InsufficientFund;
import exceptions.NotEnoughtInStock;
import exceptions.UnAuthorizedAccess;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class StaffTest {
    Staff manager;
    Applicants James;
    Store easyBuy;
    Customer ife;

    @Before
    public void setUp() throws Exception {

        manager = new Staff("Chisom", "Obidike", "decagon.com", Gender.FEMALE, Role.MANAGER);
        easyBuy = new Store("Easy Buy",manager);

        ife = new Customer("Ifeoluwa", "Wisdom", "ife@outlook.com", Gender.MALE);
//        System.out.println(easyBuy.getStaffList());
//        easyBuy.apply(James);
//        manager.hireCashier(easyBuy);
//        System.out.println(easyBuy.getApplicantsList().size());
//        System.out.println(easyBuy.getStaffList());
    }

    @Test
    public void hireCashier() throws ApplicantAlreadyExist, UnAuthorizedAccess {
        James = new Applicants("James", "John", "John.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
        easyBuy.apply(James);

        assertEquals(1, easyBuy.getStaffList().size());
        manager.hireCashier(easyBuy);
        assertEquals(2,easyBuy.getStaffList().size());

    }

    @Test
    public void shouldCatchTheExceptionAlreadyApplied() throws ApplicantAlreadyExist {
        James = new Applicants("James", "John", "John.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
        easyBuy.apply(James);


        assertThrows(ApplicantAlreadyExist.class, () -> {
            easyBuy.apply(James);});
    }

    @Test
    public void fireCashier() {
    }

//    @Test
//    public void setStoreName() throws ApplicantAlreadyExist, UnAuthorizedAccess {
//        James = new Applicants("James", "John", "John.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
//        easyBuy.apply(James);
//
//
//        manager.hireCashier(easyBuy);
//
//
//
//    }
//
//    @Test
//    public void getStoreName() {
//    }

    @Test
    public void addProductsToStore() throws UnAuthorizedAccess {
        assertEquals(0, easyBuy.getProductMap().size());
        Product product1 = new Product("Infinix", 40_000);
        Product product2 = new Product("Air conditioner", 120_000);
        manager.addProductsToStore(product1, 30, easyBuy);
        assertEquals(1, easyBuy.getProductMap().size());
        manager.addProductsToStore(product2,9, easyBuy);
        assertEquals(2, easyBuy.getProductMap().size());
    }

    @Test
    public void sellProducts() throws ApplicantAlreadyExist, UnAuthorizedAccess, NotEnoughtInStock, InsufficientFund, IOException {
        James = new Applicants("James", "John", "John.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
        easyBuy.apply(James);
        manager.hireCashier(easyBuy);
        Product product1 = new Product("Infinix", 40_000);
        Product product2 = new Product("Air conditioner", 120_000);
        Product product3 = new Product("Tecno", 40_000);
        manager.addProductsToStore(product1, 30, easyBuy);
        manager.addProductsToStore(product2, 7, easyBuy);
        manager.addProductsToStore(product3, 3, easyBuy);


        ife.buyProducts("Infinix", 2, easyBuy);
        ife.buyProducts("Air conditioner", 1, easyBuy);

        Staff james = easyBuy.getStaffList().get(1);

        james.sellProducts(ife,200000,easyBuy);

        int cost = (int) james.getCustomersCostOfGoods();

        assertEquals(200000, cost);

        ife.buyProducts("Tecno", 3, easyBuy);

        james.sellProducts(ife, 300000, easyBuy);

        assertEquals(2, easyBuy.getProductMap().size());
    }


    @Test
    public void shouldClearCustomerCartUponSell() throws ApplicantAlreadyExist, UnAuthorizedAccess, NotEnoughtInStock, InsufficientFund, IOException {
        James = new Applicants("James", "John", "John.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
        easyBuy.apply(James);
        manager.hireCashier(easyBuy);
        Product product1 = new Product("Infinix", 40_000);
        Product product2 = new Product("Air conditioner", 120_000);
        Product product3 = new Product("Tecno", 40_000);
        manager.addProductsToStore(product1, 30, easyBuy);
        manager.addProductsToStore(product2, 7, easyBuy);
        manager.addProductsToStore(product3, 3, easyBuy);

        ife.buyProducts("Tecno",2, easyBuy);
        ife.buyProducts("Infinix", 2, easyBuy);

        assertEquals(2, ife.viewCartMap().size());

        Staff cashier = easyBuy.getStaffList().get(1);
        cashier.sellProducts(ife, 300000, easyBuy);

        assertEquals(0, ife.viewCartMap().size());

    }

    @Test
    public void shouldClearCustomerCart() throws ApplicantAlreadyExist, UnAuthorizedAccess, NotEnoughtInStock {
        James = new Applicants("James", "John", "John.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
        easyBuy.apply(James);
        manager.hireCashier(easyBuy);
        Product product1 = new Product("Infinix", 40_000);
        Product product2 = new Product("Air conditioner", 120_000);
        Product product3 = new Product("Tecno", 40_000);
        manager.addProductsToStore(product1, 30, easyBuy);
        manager.addProductsToStore(product2, 7, easyBuy);
        manager.addProductsToStore(product3, 3, easyBuy);

        ife.buyProducts("Tecno",2, easyBuy);
        ife.buyProducts("Infinix", 2, easyBuy);
        assertEquals(2, ife.viewCartMap().size());
        ife.clearCartMap();
        assertEquals(0, ife.viewCartMap().size());

    }



}