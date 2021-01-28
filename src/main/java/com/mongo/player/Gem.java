package com.mongo.player;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;

@Data
@TypeAlias("gem")
public class Gem extends Item {
    private Color color;
}
