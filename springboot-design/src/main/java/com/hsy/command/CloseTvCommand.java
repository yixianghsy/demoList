package com.hsy.command;

public class CloseTvCommand implements Command{
    private Television tv;

    public CloseTvCommand(Television tv){
        this.tv = tv;
    }

    public void execute() {
        tv.close();
    }
}