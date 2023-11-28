package de.telran.g_280323_m_be_shop.domain.entity.interfaces;

import de.telran.g_280323_m_be_shop.domain.entity.jpa.JpaCart;

public interface Customer {

    int getId();

    String getName();

    JpaCart getCart();

    int getAge();

    String getEmail();
}
