package com.mongo.player;

import com.mongo.player.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.UnaryOperator;

@SpringBootTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void test() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Player player = new Player();
        player.setId((long) random.nextInt());
        player.setName("hj" + player.getId());
        player.getLoginInfo().setLoginTime(System.currentTimeMillis());
        player.getLoginInfo().setLogoutTime(System.currentTimeMillis() + 10000);
        ItemContainer itemContainer = player.getItemContainer();
        int i = 0;
        for (; i < 5; i++) {
            Item item = new Item();
            item.setId(i);
            item.setName("i" + i);
            item.setCount(1);
            itemContainer.getItems().put(item.getId(), item);
        }
        for (; i < 10; i++) {
            Gem item = new Gem();
            item.setId(i);
            item.setName("i" + i);
            item.setCount(1);
            item.setColor(i % 2 == 0 ? Color.RED : Color.GREEN);
            itemContainer.getItems().put(item.getId(), item);
        }
        playerRepository.save(player);

        Player player1 = playerRepository.findById(player.getId()).get();
        System.out.println(player1);
        System.out.println(player1.getClass());
        System.out.println(player1.getLoginInfo().getClass());
        System.out.println(player1.getItemContainer().getItems().get(0).getClass());
        System.out.println(player1.getItemContainer().getItems().get(6).getClass());
    }

    @Test
    public void hello() {
        UnaryOperator<Object> identity = UnaryOperator.identity();
        System.out.println(identity.apply(1L));
    }
}
