package com.davidebelpanno.munrolibrary.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "munro")
public class Munro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String category;
    private String name;
    private double height;
    private String gridRef;

    public Munro() {
    }

    public Munro(String category, String name, double height, String gridRef) {
        this.category = category;
        this.name = name;
        this.height = height;
        this.gridRef = gridRef;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Munro))
            return false;
        Munro munro = (Munro) o;
        return Objects.equals(this.id, munro.id) && Objects.equals(this.name, munro.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Munro{" + "id=" + this.id + ", name='" + this.name + '\'' + ", category='" + this.category + '\'' + ", height='"
                + this.height + '\'' + ", grid reference='" + this.gridRef + '\'' + '}';
    }
}
