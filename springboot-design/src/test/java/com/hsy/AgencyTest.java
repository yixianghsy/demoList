package com.hsy;

import com.hsy.agency.BeautifulGirl;
import com.hsy.agency.HerChum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AgencyTest {
    @Test
    void contextLoads() {

        BeautifulGirl mm = new BeautifulGirl("小屁孩...");

        HerChum chum = new HerChum(mm);

        chum.giveBook();
        chum.giveChocolate();
        chum.giveFlowers();
    }
}
