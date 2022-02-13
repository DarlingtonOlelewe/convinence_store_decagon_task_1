package model;

import applications.Applicants;
import enums.Gender;
import enums.Qualifications;
import enums.Role;
import org.junit.Assert;

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
    public void getApplicantsList() {
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
    public void getStaffList() {


    }



    @org.junit.Test
    public void getProductMap() {
    }
}