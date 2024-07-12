package com.hsy.singleton;
//懒汉式单例类.在第一次调用的时候实例化自己
public class Singleton {
    private Singleton() {}
    private static Singleton single=null;
    //静态工厂方法
    public static Singleton getInstance() {
        if (single == null) {
            single = new Singleton();
        }
        return single;
    }
    /**
     * 在 getInstance() 方法上加同步机制：
     * 使用了 synchronized 关键字，确保了多线程环境下的线程安全。但是，这样的实现会导致性能问题，因为每次调用 getInstance 时都要进行同步。
     */
//    public static synchronized Singleton getInstance() {
//        if (single == null) {
//            single = new Singleton();
//        }
//        return single;
//    }

    //懒汉式单例类.在第一次调用的时候实例化自己
//    public class Singleton {
//        private Singleton() {}
//        private volatile static Singleton singleton=null;
//
//        public static Singleton getInstance() {
//            if (singleton == null) {
//                synchronized (Singleton.class) {
//                    if (singleton == null) {
//                        singleton = new Singleton();
//                    }
//                }
//            }
//            return singleton;
//        }
//    }
//    public class Singleton {
//        private static class LazyHolder {
//            private static final Singleton INSTANCE = new Singleton();
//        }
//        private Singleton (){}
//        public static final Singleton getInstance() {
//            return LazyHolder.INSTANCE;
//        }
//    }
}