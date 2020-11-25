package com.davidebelpanno.munrolibrary.model;

public class Munro {

    private String category;
    private String name;
    private double height;
    private String gridRef;

    public Munro(String category, String name, double height, String gridRef) {
        this.category = category;
        this.name = name;
        this.height = height;
        this.gridRef = gridRef;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(final double height) {
        this.height = height;
    }

    public String getGridRef() {
        return gridRef;
    }

    public void setGridRef(final String gridRef) {
        this.gridRef = gridRef;
    }
}
