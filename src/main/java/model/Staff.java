package model;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import applications.Applicants;
import enums.Gender;
import enums.Qualifications;
import enums.Role;
import exceptions.InvalidOperationHandler;

import static java.io.Writer.*;


public class Staff extends Person implements StaffOperationInt{
    private  Role role;
    private final String ID;
    Random rand = new Random();
    private Map maps;
    private String storeName;

    public Staff(String firstName, String lastName, String Email, Gender gender, Role role) {
        super(firstName, lastName, Email, gender);
        this.role = role;
        this.ID = ""+role+"-"+firstName.charAt(0)+lastName.charAt(0)+"-"+rand.nextInt(10);
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Staff{" +
                " firstName: "+ getFirstName() +
                " lastName: "+ getLastName() +
                " email: "+ getEmail() +
                " role: "+ getRole() +
                " ID: "+ ID +
                " Store_Name: "+ getStoreName()+
                '}';

    }

    @Override
    public void hireCashier(Store store) {
        List<Integer> applicantsToRemove = new ArrayList<>();
        for(int i = 0; i < store.getApplicantsList().size(); i++) {
            if ((store.getStaffList().size() < 4) && (getRole().equals(Role.MANAGER) && (store.getApplicantsList().get(i).getRole().equals(Role.CASHIER)))) {
                if (store.getApplicantsList().get(i).getCert().equals(Qualifications.SSCE) || store.getApplicantsList().get(i).getCert().equals(Qualifications.HND)) {
                    convertToStaff(store.getApplicantsList().get(i), store);
                    applicantsToRemove.add(i);
                }
            }
        }
            for(int each: applicantsToRemove){
                store.getApplicantsList().remove(store.getApplicantsList().get(each));
            }
    }

    private void convertToStaff(Applicants applicant, Store store){
        Staff newStaff = new Staff(applicant.getFirstName(),
                applicant.getLastName(),
                applicant.getEmail(),
                applicant.getGender(),
                applicant.getRole());
        newStaff.setStoreName(store.getName());

        store.getStaffList().add(newStaff);

    }

    public void setStoreName(String name){
        this.storeName = name;
    }
    public String getStoreName(){
        return storeName;
    }

    @Override
    public void addProductsToStore(Product product, int units, Store store){
        if(!getRole().equals(Role.MANAGER)){
            System.out.println("You need to be a manager to add to Store");
        }else{
            store.getProductMap().put(product, units);
        }
    }
    @Override
    public void sellProducts(Customer customer, double payment, Store store) throws IOException {
        int expectedAmount = 0;

        if(!getRole().equals(Role.CASHIER)){
            System.out.println("You cant perform this service");
        }else{

            for( Map.Entry<Product, Integer> each: customer.viewCart().entrySet()){
                expectedAmount += (each.getKey().getPrice() * (double) each.getValue());
            }

            if (expectedAmount <= payment){
                checkOut(customer.viewCart(), store);
                String receiptInfo = generateReceipt(customer.viewCart(), expectedAmount);
                printReceipt(receiptInfo);

                customer.viewCart().clear();
            }
        }

    }
    private String generateReceipt(Map<Product, Integer> cart, double expectedAmount){
        String receipt = "===== Thanks for patronizing" + storeName + " ========\n" +
                "Transaction Details\n" +
                "=============================================\n";

        for( Map.Entry<Product, Integer> each: cart.entrySet()){
            receipt += "Product Name: "+ each.getKey().getName()+"\n" +
                       "Price       : "+ each.getKey().getPrice()+"\n" +
                       "Units       : "+ each.getValue()+"\n" +
                       "Cost        : "+ (each.getKey().getPrice() * (double) each.getValue())+"\n" +
                        "=============================================\n";
        }
        receipt += "Total Price: "+expectedAmount+"\n" +
                "Validated by "+getFirstName()+" "+getLastName();

        return receipt;
    }

    private void printReceipt(String information) throws IOException {

        String print = information;
        Date now = new Date();
        String fileName = now.toInstant()+".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(information);
        writer.close();



    }

    private void checkOut(Map<Product, Integer> cart, Store store){
        List<Product> productsToDelete = new ArrayList<>();

        for(Map.Entry<Product, Integer> item: cart.entrySet()){
            String nameOfItem = item.getKey().getName();
            int unit = item.getValue();

            for(Map.Entry<Product, Integer> each: store.getProductMap().entrySet()){
                if(each.getKey().getName().equals(nameOfItem)){
                    each.setValue(each.getValue() - unit);
                }

                if(each.getValue() == 0){

                    productsToDelete.add(each.getKey());
                }

            }

        }

        for(Product each: productsToDelete){
            store.getProductMap().remove(each);
        }


    }

    @Override
    public void issueReceipt() {

    }
}









//A Manager can hire a cashier
//
//· A Cashier sells products to customers and dispenses receipts
//
//· Customers can buy products from the store