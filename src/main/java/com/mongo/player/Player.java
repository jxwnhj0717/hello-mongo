package com.mongo.player;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Player {
    @Id
    private Long id;
    private String name;
    private LoginInfo loginInfo = new LoginInfo();
    private ItemContainer itemContainer = new ItemContainer();
}
