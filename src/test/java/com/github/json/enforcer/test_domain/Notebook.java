package com.github.json.enforcer.test_domain;

import java.util.List;
import java.util.Set;

/**
 * Internal class used for testing.
 */
@SuppressWarnings("unused")
public class Notebook {

    Integer identifier;
    String name;
    Set<String> tags;
    List<Shop> shops;
    BestPrice bestPrice;


    public Notebook(Integer identifier, String name, Set<String> tags, List<Shop> shops, BestPrice bestPrice) {
        this.identifier = identifier;
        this.name = name;
        this.tags = tags;
        this.shops = shops;
        this.bestPrice = bestPrice;
    }

    public BestPrice getBestPrice() {
        return bestPrice;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public Set<String> getTags() {
        return tags;
    }

    public List<Shop> getShops() {
        return shops;
    }
}
