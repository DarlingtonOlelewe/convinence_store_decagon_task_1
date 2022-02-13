package model;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import applications.Applicants;
import enums.Gender;
import enums.Qualifications;
import enums.Role;




public class Staff extends Person implements StaffOperationInt{
    private  final Role role;
    private final String ID;
    private String storeName;
    Random rand = new Random();

    public Staff(String firstName, String lastName, String Email, Gender gender, Role role) {
        super(firstName, lastName, Email, gender);
        this.role = role;
        this.ID = ""+role+"-"+firstName.charAt(0)+lastName.charAt(0)+"-"+rand.nextInt(10);
    }

    public Role getRole() {
        return role;
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
            for(int removeApplicant: applicantsToRemove){
                store.getApplicantsList().remove(store.getApplicantsList().get(removeApplicant));
            }
    }

    private void convertToStaff(Applicants applicant, Store store){
        Staff newStaff = new Staff(applicant.getFirstName(),
                applicant.getLastName(),
                applicant.getEmail(),
                applicant.getGender(),
                applicant.getRole());

        //Every Staff must Have a storeName where they work
        newStaff.setStoreName(store.getName());

        store.getStaffList().add(newStaff);
    }

    public void setStoreName(String name){
        this.storeName = name;
    }
    public String getStoreName(){
        return storeName;
    }


    //Only manager can add products to Store, in (product, quantity/unit) pairs
    @Override
    public void addProductsToStore(Product product, int units, Store store){
        if(!getRole().equals(Role.MANAGER)){
            System.out.println("You need to be a manager to add to Store");
        }else{
            store.getProductMap().put(product, units);
        }
    }

    //Only cashier can sell product to a customer
    //Cashier go through the customers cart, gets price and cross-checks.
    @Override
    public void sellProducts(Customer customer, double payment, Store store) throws IOException {
        int expectedAmount = 0;

        if(!getRole().equals(Role.CASHIER)){
            System.out.println("You cant perform this service");
        }else{

            for( Map.Entry<Product, Integer> productQuantityPair: customer.viewCart().entrySet()){
                expectedAmount += (productQuantityPair.getKey().getPrice() * productQuantityPair.getValue());
            }

            if (expectedAmount <= payment){

                double balance = payment - expectedAmount;

                checkOut(customer.viewCart(), store);
                String receiptInfo = generateReceiptContent(customer.viewCart(), expectedAmount, balance);
                printReceipt(receiptInfo);

                customer.viewCart().clear();
                //customer's cart is cleared because it has been paid for, and receipt generated
            }
        }

    }

    private void checkOut(Map<Product, Integer> cart, Store store){
        List<Product> productsToRemoveFromStore = new ArrayList<>();

        for(Map.Entry<Product, Integer> productPairsBoughtByCustomer: cart.entrySet()){
            String nameOfItem = productPairsBoughtByCustomer.getKey().getName();
            int unit = productPairsBoughtByCustomer.getValue();

            for(Map.Entry<Product, Integer> productsInStore: store.getProductMap().entrySet()){
                if(productsInStore.getKey().getName().equals(nameOfItem)){
                    productsInStore.setValue(productsInStore.getValue() - unit);
                }

                if(productsInStore.getValue() == 0){
                    productsToRemoveFromStore.add(productsInStore.getKey());
                }
            }

        }

        for(Product singleProductAndUnit: productsToRemoveFromStore){
            store.getProductMap().remove(singleProductAndUnit);
        }

    }


    //Product contains names of Customer, goods bought, prices, and Cashier who validates the payment.
    private String generateReceiptContent(Map<Product, Integer> cart, double expectedAmount, double balance){
        StringBuilder receiptContent = new StringBuilder("===== Thanks for patronizing " + storeName + " ========\n" +
                "Transaction Details\n" +
                "=============================================\n");

        for( Map.Entry<Product, Integer> each: cart.entrySet()){
            receiptContent.append("Product Name: ").append(each.getKey().getName()).append("\n").append("Price       : ").append(each.getKey().getPrice()).append("\n").append("Units       : ").append(each.getValue()).append("\n").append("Cost        : ").append(each.getKey().getPrice() * (double) each.getValue()).append("\n").append("=============================================\n");
        }
        receiptContent.append("Total Price : ").append(expectedAmount).append("\n").append("Balance     : ").append(balance).append("\n").append("Validated by: ").append(getFirstName()).append(" ").append(getLastName()).append("\n").append("Staff ID:   : ").append(ID);

        return receiptContent.toString();
    }

    //
    private void printReceipt(String informationOfReceipt) throws IOException {

        Date now = new Date();

        String fileName = now.toInstant()+".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(informationOfReceipt);
        writer.close();
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
}