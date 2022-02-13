package model;

import applications.Applicants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private final String STORE_NAME;
    private List<Applicants> applicantsList;
    private List<Staff> staffList;
    private Map<Product, Integer> productMap;
    private final Staff manager;

    public Store(String STORE_NAME, Staff manager) {
        this.STORE_NAME = STORE_NAME;
        this.manager = manager;
        this.productMap = new HashMap<>();
        this.applicantsList = new ArrayList<Applicants>();
        this.staffList = new ArrayList<>();
        manager.setStoreName(STORE_NAME);
        staffList.add(manager);
    }

    public void apply(Applicants applicant){
        applicantsList.add(applicant);
    }


    public String getName() {
        return STORE_NAME;
    }


    public List<Applicants> getApplicantsList() {
        return applicantsList;
    }


    public List<Staff> getStaffList() {
        return staffList;
    }


    public Map<Product, Integer> getProductMap() {
        return productMap;
    }

}
