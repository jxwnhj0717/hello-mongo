package com.mongo.player;

import com.mongo.WithId;

public enum Color implements WithId {

    GREEN(1),
    RED(2)
    ;

    private int id;

    Color(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}
