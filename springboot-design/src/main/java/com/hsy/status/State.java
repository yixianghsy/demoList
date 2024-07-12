package com.hsy.status;

public interface State {
    /**
     * @desc 预订房间
     */
    public void bookRoom();

    /**
     * @desc 退订房间
     */
    public void unsubscribeRoom();

    /**
     * @desc 入住
     */
    public void checkInRoom();

    /**
     * @desc 退房
     */
    public void checkOutRoom();

}