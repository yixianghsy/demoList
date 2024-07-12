# Java设计模式之创建型：单例模式

#### 一、什么是[单例模式](https://so.csdn.net/so/search?q=单例模式&spm=1001.2101.3001.7020)：

​    单例模式可以确保系统中某个类只有一个实例，该类自行实例化并向整个系统提供这个实例的公共访问点，除了该公共访问点，不能通过其他途径访问该实例。单例模式的优点在于：

- 系统中只存在一个共用的实例对象，无需频繁创建和销毁对象，节约了系统资源，提高系统的性能
- 可以严格控制客户怎么样以及何时访问单例对象。

单例模式有以下特点：

- （1）单例类只能有一个实例；
- （2）单例类必须自己创建自己的唯一实例；
- （3）单例类必须给所有其他对象提供这一实例。

​    在计算机系统中，线程池、缓存、日志对象、对话框、打印机、显卡的驱动程序对象常被设计成单例，这些应用都或多或少具有资源管理器的功能。每台计算机可以有若干个打印机，但只能有一个Printer Spooler，以避免两个打印作业同时输出到打印机中。每台计算机可以有若干通信端口，系统应当集中管理这些通信端口，以避免一个通信端口同时被两个请求同时调用。总之，选择单例模式就是为了避免不一致状态，避免政出多头。

​    单例模式的写法有好几种，这里主要介绍三种：懒汉式单例、饿汉式单例、登记式单例。

#### 二、懒汉式单例：

```
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
}
```

Singleton 通过私有化构造函数，避免类在外部被实例化，而且只能通过 getInstance() 方法获取 Singleton 的唯一实例。但是以上懒汉式单例的实现是线程不安全的，在并发环境下可能出现多个 Singleton 实例的问题。要实现线程安全，有以下三种方式，都是对 getInstance() 这个方法改造，保证了懒汉式单例的线程安全：

> 线程安全：如果程序每次运行结果都和单线程运行的结果是一样的，而且其他的变量的值也和预期的是一样的，就是线程安全的。

1、在 getInstance() 方法上加同步机制：

```
public static synchronized Singleton getInstance() {
         if (single == null) {  
             single = new Singleton();
         }  
        return single;
}
```

在方法调用上加了同步，虽然线程安全了，但是每次都要同步，会影响性能，毕竟99%的情况下是不需要同步的。

***\*2、双重检查锁定：\****

```java
//懒汉式单例类.在第一次调用的时候实例化自己 
public class Singleton {
    private Singleton() {}
    private volatile static Singleton singleton=null;
    
    public static Singleton getInstance() {
        if (singleton == null) {  
            synchronized (Singleton.class) {  
               if (singleton == null) {  
                  singleton = new Singleton(); 
               }  
            }  
        }  
        return singleton; 
    }
}
```

（1）为什么 getInstance() 方法内需要使用两个 if (singleton == null) 进行判断呢？

假设高并发下，线程A、B 都通过了第一个 if 条件。若A先抢到锁，new 了一个对象，释放锁，然后线程B再抢到锁，此时如果不做第二个 if 判断，B线程将会再 new 一个对象。使用两个 if 判断，确保了只有第一次调用单例的时候才会做同步，这样也是线程安全的，同时避免了每次都同步的性能损耗。

（2）volatile 关键字的作用？

volatile 的作用主要是禁止指定重排序。假设在不使用 volatile 的情况下，两个线程A、B，都是第一次调用该单例方法，线程A先执行 singleton = new Singleton()，但由于构造方法不是一个原子操作，编译后会生成多条字节码指令，由于 JAVA的 指令重排序，可能会先执行 singleton 的赋值操作，该操作实际只是在内存中开辟一片存储对象的区域后直接返回内存的引用，之后 singleton 便不为空了，但是实际的初始化操作却还没有执行。如果此时线程B进入，就会拿到一个不为空的但是没有完成初始化的singleton 对象，所以需要加入volatile关键字，禁止指令重排序优化，从而安全的实现单例。

3、静态内部类：

```java
public class Singleton {  
    private static class LazyHolder {  
       private static final Singleton INSTANCE = new Singleton();  
    }  
    private Singleton (){}  
    public static final Singleton getInstance() {  
       return LazyHolder.INSTANCE;  
    }  
}  
```

利用了类加载机制来保证初始化 instance 时只有一个线程，所以也是线程安全的，同时没有性能损耗，这种比上面1、2都好一些，既实现了线程安全，又避免了同步带来的性能影响。



#### 三、饿汉式单例：

```
//饿汉式单例类.在类初始化时，已经自行实例化 
public class Singleton1 {
    private Singleton1() {}
    private static final Singleton1 single = new Singleton1();
    //静态工厂方法 
    public static Singleton1 getInstance() {
        return single;
    }
}
```

饿汉式在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，所以天生是线程安全的。

> **饿汉式和懒汉式区别：**
>
> （1）初始化时机与首次调用：
>
> - 饿汉式是在类加载时，就将单例初始化完成，保证获取实例的时候，单例是已经存在的了。所以在第一次调用时速度也会更快，因为其资源已经初始化完成。
> - 懒汉式会延迟加载，只有在首次调用时才会实例化单例，如果初始化所需要的工作比较多，那么首次访问性能上会有些延迟，不过之后就和饿汉式一样了。
>
> （2）线程安全方面：饿汉式天生就是线程安全的，可以直接用于多线程而不会出现问题，懒汉式本身是非线程安全的，需要通过额外的机制保证线程安全



#### 四、登记式单例：

```
//类似Spring里面的方法，将类名注册，下次从里面直接获取。
public class Singleton3 {
    private static Map<String,Singleton3> map = new HashMap<String,Singleton3>();
    static{
        Singleton3 single = new Singleton3();
        map.put(single.getClass().getName(), single);
    }
    //保护的默认构造子
    protected Singleton3(){}
    //静态工厂方法,返还此类惟一的实例
    public static Singleton3 getInstance(String name) {
        if(name == null) {
            name = Singleton3.class.getName();
            System.out.println("name == null"+"--->name="+name);
        }
        if(map.get(name) == null) {
            try {
                map.put(name, (Singleton3) Class.forName(name).newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return map.get(name);
    }
    //一个示意性的商业方法
    public String about() {    
        return "Hello, I am RegSingleton.";    
    }    
    public static void main(String[] args) {
        Singleton3 single3 = Singleton3.getInstance(null);
        System.out.println(single3.about());
    }
}
```

登记式单例实际上维护了一组单例类的实例，将这些实例存放在一个Map（登记薄）中，对于已经登记过的实例，则从Map直接返回，对于没有登记的，则先登记，然后返回。

近年来，也推荐使用静态内部类方式实现单例，以及利用枚举类型实现单例，它们都是更为简洁和安全的实现方式。