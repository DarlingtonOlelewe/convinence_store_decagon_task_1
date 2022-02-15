package model;

import applications.Applicants;
import enums.Gender;
import enums.Qualifications;
import enums.Role;
import exceptions.ApplicantAlreadyExist;
import exceptions.NotAStaff;
import exceptions.NotOwners;
import exceptions.UnAuthorizedAccess;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StoreTest {
    Store easyBuy;
    Staff manager;
    Applicants James;
    Applicants Emeka;
    Applicants Ebube;
    @org.junit.Before
    public void setUp() throws Exception {
        manager = new Staff("Darlington", "Olelewe", "o.darlington@outlook.com", Gender.MALE, Role.MANAGER);
        easyBuy = new Store("Easy Buy", manager);
    }

    @org.junit.Test
    public void getName() {
        easyBuy.getName();
        assertEquals("Easy Buy",easyBuy.getName());
    }

    @org.junit.Test
    public void testForExceptionNotOwners(){
        Staff cashier1 = new Staff("Ikenna", "Obinna", "ikenna@gmail.com", Gender.MALE, Role.CASHIER);
        assertThrows(NotOwners.class, () -> {
            Store easyGoods = new Store("Easy Goods", cashier1);
        });
    }




    @org.junit.Test
    public void getApplicantsList() throws ApplicantAlreadyExist {
        James = new Applicants("James", "Peter",
                "james@gmail.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
        easyBuy.apply(James);

        Ebube = new Applicants("Ebube", "Chineke",
                "ebube@gmail.com", Gender.FEMALE,Role.CASHIER, Qualifications.HND);
        easyBuy.apply(Ebube);


        //this should be expect 2 because James and John have applied to easyBuy stores.
        assertEquals(2, easyBuy.getApplicantsList().size());

    }

    @org.junit.Test
    public void getStaffList() throws UnAuthorizedAccess, ApplicantAlreadyExist {
        assertEquals(1, easyBuy.getStaffList().size());
        Emeka = new Applicants("Emeka", "Johnn", "JohnEme@gmail.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
        easyBuy.apply(Emeka);
        manager.hireCashier(easyBuy);

        assertEquals(2, easyBuy.getStaffList().size());


    }



    @org.junit.Test
    public void getProductMap() throws UnAuthorizedAccess {
        Product product1 = new Product("Laptop", 12_000);
        Product product2 = new Product("Laptop", 12_000);

        manager.addProductsToStore(product1, 32, easyBuy);
        manager.addProductsToStore(product2, 32, easyBuy);

        assertEquals(2, easyBuy.getProductMap().size());
    }
}