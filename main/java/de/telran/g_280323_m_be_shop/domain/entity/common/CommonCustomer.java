package de.telran.g_280323_m_be_shop.domain.entity.common;

import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Cart;
import de.telran.g_280323_m_be_shop.domain.entity.interfaces.Customer;
import de.telran.g_280323_m_be_shop.domain.entity.jpa.JpaCart;

public class CommonCustomer implements Customer {

    private int id;
    private String name;
    private Cart cart;

    private int age;

    private String email;

    public CommonCustomer() {
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public JpaCart getCart() {
        return (JpaCart) cart;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CommonCustomer(int id, String name, Cart cart) {
        this.id = id;
        this.name = name;
        this.cart = cart;
    }
}
