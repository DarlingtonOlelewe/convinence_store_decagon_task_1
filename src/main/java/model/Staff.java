package model;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import applications.Applicants;
import enums.Gender;
import enums.Qualifications;
import enums.Role;
import exceptions.InsufficientFund;
import exceptions.NotAStaff;
import exceptions.UnAuthorizedAccess;


public class Staff extends Person{
    private  final Role role;
    private final String ID;
    private String storeName;
    private static int count = 0;

    public Staff(String firstName, String lastName, String Email, Gender gender, Role role) {
        super(firstName, lastName, Email, gender);
        this.role = role;
        this.ID = ""+role+"-"+firstName.charAt(0)+lastName.charAt(0)+"-"+count++;
    }

    public Role getRole() {
        return role;
    }


    public void hireCashier(Store store) throws UnAuthorizedAccess{

        if(!getRole().equals(Role.MANAGER)) throw new UnAuthorizedAccess("This is a A role reserved only for Manager");

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

    public void fireCashier(Store store, String firstName, String email) throws NotAStaff, UnAuthorizedAccess {
        List<Integer> staffToFirebyIndex = new ArrayList<>();
        int i = 0;

        if(!store.getName().equals(storeName)) throw new NotAStaff("Not a Staff of this Store.");
        if(!getRole().equals(Role.MANAGER)) throw new UnAuthorizedAccess("UnAuthorized Access: reserved for Managers");


        for(Staff singlarStaff: store.getStaffList()){
            if(singlarStaff.getFirstName().equals(firstName) && singlarStaff.getEmail().equals(email) && singlarStaff.getRole().equals(Role.CASHIER)){

                staffToFirebyIndex.add(i);

            }
        }


                for(Integer index: staffToFirebyIndex){

                    store.getStaffList().remove(index);
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


    public void addProductsToStore(Product product, int units, Store store) throws UnAuthorizedAccess {
        if(!getRole().equals(Role.MANAGER)){
            throw new UnAuthorizedAccess("UnAuthorized Access! reserved for managers");
        }else{
            store.getProductMap().put(product, units);
        }
    }

    public void sellProducts(Customer customer, double payment, Store store) throws IOException, InsufficientFund, UnAuthorizedAccess {


        if(!getRole().equals(Role.CASHIER)){
            throw new UnAuthorizedAccess("UnAuthorized Access! reserved for cashiers");
        }else{



            if (customer.getPriceOfGoods() <= payment){

                double balance = payment - customer.getPriceOfGoods();

                checkOut(customer.viewCartMap(), store);
                String receiptInfo = generateReceiptContent(customer.viewCartMap(), customer.getPriceOfGoods(), balance);
                printReceipt(receiptInfo);

                customer.viewCartMap().clear();
            }else throw new InsufficientFund("Insufficient Funds");
        }

    }

    private void checkOut(Map<Product, Integer> cartMap, Store store){
        List<Product> productsToRemoveFromStore = new ArrayList<>();

        for(Map.Entry<Product, Integer> productPairsBoughtByCustomer: cartMap.entrySet()){
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

    private void printReceipt(String informationOfReceipt) throws IOException {
        String reciept = "Receipt"+(new Date().toInstant()+"").substring(15, 23)+"txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(reciept));
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