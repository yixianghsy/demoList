package com.hsy;

import com.hsy.status.Room;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatusTest {
        @Test
         void  contextLoads() {

            //有3间房
            Room[] rooms = new Room[2];
            //初始化
            for(int i = 0 ; i < rooms.length ; i++){
                rooms[i] = new Room();
            }
            //第一间房
            rooms[0].bookRoom();    //预订
            rooms[0].checkInRoom();   //入住
            rooms[0].bookRoom();    //预订
            System.out.println(rooms[0]);
            System.out.println("---------------------------");

            //第二间房
            rooms[1].checkInRoom();
            rooms[1].bookRoom();
            rooms[1].checkOutRoom();
            rooms[1].bookRoom();
            System.out.println(rooms[1]);

        }
}
