package model;

import java.io.IOException;
import java.util.Map;

public interface StaffOperationInt {
    void hireCashier(Store store);
    void sellProducts(Customer customer, double payment, Store store) throws IOException;
    void addProductsToStore(Product product, int units, Store store);
}
