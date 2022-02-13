import applications.Applicants;
import enums.Gender;
import enums.Qualifications;
import enums.Role;
import model.Customer;
import model.Product;
import model.Staff;
import model.Store;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        Staff manager = new Staff("Chisom", "Obidike", "decagon.com", Gender.FEMALE, Role.MANAGER);

        Applicants James = new Applicants("James", "John", "John.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
        Store easyBuy = new Store("Easy Buy",manager);
        System.out.println(easyBuy.getStaffList());

        easyBuy.apply(James);
        manager.hireCashier(easyBuy);
        System.out.println(easyBuy.getApplicantsList().size());
        System.out.println(easyBuy.getStaffList());







        Product product1 = new Product("diapers", 2000);
        Product product2 = new Product("Beer", 500);
        manager.addProductsToStore(product1, 40, easyBuy);
        manager.addProductsToStore(product2, 58, easyBuy);
//
        Customer Joy = new Customer("Joy", "Uche", "Joy.com", Gender.FEMALE);
        Joy.buyProducts("Beer", 3, easyBuy);
        Joy.buyProducts("sugar", 5, easyBuy);
        Joy.buyProducts("diapers", 40, easyBuy);
//
//
        System.out.println(Joy.viewCart());
        System.out.println(easyBuy.getProductMap());


        Staff james = easyBuy.getStaffList().get(1);


        james.sellProducts(Joy,200000,easyBuy);
        System.out.println(easyBuy.getProductMap());




    }
}
