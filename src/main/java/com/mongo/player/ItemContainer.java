package com.mongo.player;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ItemContainer {

    private Map<Integer, Item> items = new HashMap<>();

}
