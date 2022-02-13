package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private final String name;
    private List<Product> category;
    private final String description;

    public Category(final String name, final String description){
        this.name = name;
        this.description = description;
        this.category = new ArrayList<>();
    }
}
