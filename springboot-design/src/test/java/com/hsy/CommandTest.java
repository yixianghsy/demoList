package com.hsy;

import com.hsy.command.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommandTest {
        @Test
        void  contextLoads() {
            Television tv = new Television();
            Command openCommand,closeCommand,changeCommand;

            openCommand = new OpenTvCommand(tv);
            closeCommand = new CloseTvCommand(tv);
            changeCommand = new ChangeChannelCommand(tv);

            Controller control = new Controller(openCommand,closeCommand,changeCommand);

            control.open();           //打开电视机
            control.change();         //换频道
            control.close();          //关闭电视机
        }
}
