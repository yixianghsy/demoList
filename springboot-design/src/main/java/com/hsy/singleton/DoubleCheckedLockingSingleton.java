package com.hsy.singleton;

/**
 * 双重检查锁定（Double-Checked Locking）：
 * 双重检查锁定是在懒汉式的基础上进行改进，通过双重检查减小了同步块的范围，提高了性能。
 */
public class DoubleCheckedLockingSingleton {
    private static volatile DoubleCheckedLockingSingleton instance;

    private DoubleCheckedLockingSingleton() {
        // 私有构造方法，防止外部直接实例化
    }

    /**
     * 双重检查锁定（Double-Checked Locking）
     * 使用了 volatile 关键字来保证 instance 的可见性，并且使用了双重检查来确保只在第一次调用 getInstance 时才进行同步。
     * 这两种方式都是线程安全的单例模式实现，开发者可以根据具体的需求选择适合自己项目的方式。近年来，也推荐使用静态内部类方式实现单例，以及利用枚举类型实现单例，它们都是更为简洁和安全的实现方式
     * @return
     */
    public static DoubleCheckedLockingSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
        }
        return instance;
    }
}