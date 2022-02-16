package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {
    Product product;


    @Before
    public void setUp() throws Exception {
        product = new Product("Hp specter", 320_000);

    }


    @Test
    public void testGetNameShouldGiveNameOfProduct() {
        Product product1 = new Product("Hp specter", 320_000);
        assertEquals("Hp specter", product1.getName());
    }

    @Test
    public void testGetPriceShouldReturnPriceOfProduct() {
        Product product2 = new Product("Hp specter", 320_000);
        assertEquals(320000, (int) product2.getPrice());
    }

    @Test
    public void testToStringShouldCheckIfTheNameOfAProductIsIn() {
        Product product2 = new Product("Hp specter", 320_000);

        assertTrue("Should return true", product2.toString().contains("Hp specter"));
    }
}