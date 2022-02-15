import applications.Applicants;
import enums.Gender;
import enums.Qualifications;
import enums.Role;
import exceptions.*;
import model.Customer;
import model.Product;
import model.Staff;
import model.Store;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException, NotEnoughtInStock, UnAuthorizedAccess, NotOwners, ApplicantAlreadyExist, InsufficientFund {

        Staff cas= new Staff("uc", "da", ".com",Gender.MALE,Role.CASHIER);
        Staff manager = new Staff("Chisom", "Obidike", "decagon.com", Gender.FEMALE, Role.MANAGER);
        Applicants James = new Applicants("James", "John", "John.com", Gender.MALE,Role.CASHIER, Qualifications.SSCE);
        Store easyBuy = new Store("Easy Buy",manager);
        System.out.println(easyBuy.getStaffList());
        easyBuy.apply(James);
        manager.hireCashier(easyBuy);
        System.out.println(easyBuy.getApplicantsList().size());
        System.out.println(easyBuy.getStaffList());




        //Products are created at will but are put into a store instance by the manager of the store.
        Product product1 = new Product("diapers", 200);
        Product product2 = new Product("Beer", 500);
        manager.addProductsToStore(product1, 400, easyBuy);
        manager.addProductsToStore(product2, 7000, easyBuy);

        Product product3 = new Product("Infinix", 40_000);
        Product product4 = new Product("Air", 120_000);
        manager.addProductsToStore(product3, 30, easyBuy);
        manager.addProductsToStore(product4, 7, easyBuy);


        Customer ife = new Customer("Ifeoluwa", "Abel", "ife@gmail.com", Gender.MALE);
        ife.buyProducts("Infinix", 2, easyBuy);
        ife.buyProducts("Air", 1, easyBuy);

        System.out.println(easyBuy.getProductMap());




        //Customers can access stores (any store Instance) at will, but must be created.
        Customer Joy = new Customer("Joy", "Uche", "Joy.com", Gender.FEMALE);

        //they buy products using the name of the product,
        //If the product does not exist it is not added to the cart. and does not throw errors.
        Joy.buyProducts("Beer", 3, easyBuy);
        Joy.buyProducts("diapers", 4, easyBuy);

        //howEver they can view products. which allows them to know the names of the products
        Joy.viewProductsInstore(easyBuy);


        //They can view their cart at anytime. and also its amount. this helps them have an estimate of price
        System.out.println(Joy.viewCartMap());
        System.out.println(Joy.getPriceOfGoods());


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
        james.sellProducts(Joy,2000000,easyBuy);
        james.sellProducts(ife, 200000, easyBuy);

        //Products in Store are reduced only when they are paid for
        System.out.println(easyBuy.getProductMap());

        System.out.println(Joy);



    }
}
