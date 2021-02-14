package sample;

import java.io.Serializable;

public class Car implements Serializable {
    private int id;
    private String registration;
    private int year;
    private String color1;
    private String color2;
    private String color3;
    private String make;
    private String model;
    private int price;
    private int quantity;

    public Car() {
        this.id = 0;
        this.registration = "";
        this.year = 0;
        this.color1 = "";
        this.color2 = "";
        this.color3 = "";
        this.make = "";
        this.model = "";
        this.price = 0;
        this.quantity = 0;
    }

    @Override
    public String toString() {
        return id+","+registration+","+year+","+make+","+model+","+color1+","+color2+","+color3+","+price+","+quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
