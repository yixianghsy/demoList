package com.hsy.singleton;

public class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
        // 私有构造方法，防止外部直接实例化
    }

    /**
     * synchronized 关键字，确保了多线程环境下的线程安全。但是，这样的实现会导致性能问题，因为每次调用 getInstance 时都要进行同步
     * @return
     */
    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}