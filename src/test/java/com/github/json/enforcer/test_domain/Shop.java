package com.github.json.enforcer.test_domain;

/**
 * Internal class used for testing.
 */
@SuppressWarnings("unused")
public class Shop {

    String address;
    int openHours;
    int closeHours;

    public Shop(String address) {
        this(address, 8, 20);
    }

    public Shop(String address, int openHours, int closeHours) {
        this.address = address;
        this.openHours = openHours;
        this.closeHours = closeHours;
    }

    public String getAddress() {
        return address;
    }

    public int getOpenHours() {
        return openHours;
    }

    public int getCloseHours() {
        return closeHours;
    }
}
