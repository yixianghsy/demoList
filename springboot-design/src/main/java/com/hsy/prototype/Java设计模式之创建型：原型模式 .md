# Java设计模式之创建型：原型模式

#### 一、什么是[原型模式](https://so.csdn.net/so/search?q=原型模式&spm=1001.2101.3001.7020)：

​    原型模式主要用于对象的创建，使用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象。UML类图如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/23/20181101195356268.jpg)

原型模式的核心是就是原型类 Prototype，Prototype 类需要具备以下两个条件：

- （1）实现 Cloneable 接口：在 Java 中 Cloneable 接口的作用就是在运行时通知虚拟机可以安全地在实现了 Cloneable 接口的类上使用 clone() 方法，只有在实现了 Cloneable 的类才可以被拷贝，否则在运行时会抛出 CloneNotSupportedException 异常。
- （2）重写 Object 类中的 clone() 方法：Java 中所有类的父类都是 Object，Object 中有一个clone() 方法用于返回对象的拷贝，但是其作用域 protected，一般的类无法调用，因此，Prototype 类需要将 clone() 方法的作用域修改为 public。

​    原型模式是一种比较简单的模式，也非常容易理解，实现一个接口，重写一个方法即完成了原型模式。在实际应用中，原型模式很少单独出现。经常与其他模式混用，他的原型类Prototype也常用抽象类来替代。



#### 二、原型模式的优点与适用场景：

（1）原型模式比 new 方式创建对象的性能要好的多，因为 Object 类的 clone() 方法是一个本地方法，直接操作内存中的二进制流，特别是复制大对象时，性能的差别非常明显；

（2）简化对象的创建；

​    所以原型模式适合在重复地创建相似对象的场景使用，比如在一个循环体内创建对象，假如对象创建过程比较复杂或者循环次数很多的话，使用原型模式不但可以简化创建过程，而且也可以提供系统的整体性能。



#### 三、注意事项：

（1）使用原型模式复制对象不会调用类的构造函数，对象是通过调用 Object 类的 clone() 方法来完成的，它直接在内存中复制数据。不但构造函数不会执行，甚至连访问权限都对原型模式无效。单例模式中，需要将构造函数的访问权限设置为 private，但是 clone() 方法直接无视构造方法的权限，所以单例模式与原型模式是冲突的，在使用时需要注意。

（2）深拷贝与浅拷贝。Object 类的 clone() 方法只会拷贝对象中的基本的数据类型（8种基本数据类型 byte,char,short,int,long,float,double,boolean 和对应的封装类），对于数组、容器对象、引用对象等都不会拷贝，这就是浅拷贝。如果要实现深拷贝，必须将原型模式中的数组、容器对象、引用对象等另行拷贝。

- 浅拷贝：只克隆对象中的基本数据类型，而不会克隆数组、容器、引用对象等。换言之，浅复制仅仅复制所考虑的对象，而不复制它所引用的对象。如果变量为String字符串，则拷贝其引用地址，但是在修改的时候，它会从字符串池中重新生成一个新的字符串，原有的字符串对象保持不变。
- 深拷贝：把要克隆的对象所引用的对象都克隆了一遍。

有关深拷贝与浅拷贝的更多内容，可以参考这篇文章：[Java基础篇：对象拷贝：clone方法 以及 序列化_张维鹏的博客-CSDN博客](https://blog.csdn.net/a745233700/article/details/82950069)



#### 四、实现代码：

```
public abstract class Prototype implements Cloneable {
    protected ArrayList<String> list = new ArrayList<String>();
 
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
 
    public abstract void show();
}
```

```
public class ShallowClone extends Prototype {
    @Override
    public Prototype clone(){
        Prototype prototype = null;
        try {
            prototype = (Prototype)super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return prototype;
    }
 
    @Override
    public void show(){
        System.out.println("浅克隆");
    }
}
```

```
public class DeepClone extends Prototype {
    @SuppressWarnings("unchecked")
    @Override
    public Prototype clone() {
        Prototype prototype = null;
        try {
            prototype = (Prototype)super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        prototype.list = (ArrayList<String>) this.list.clone();
        return prototype;
    }
 
    @Override
    public void show(){
        System.out.println("深克隆");
    }
}
```

> 由于ArrayList不是基本类型，所以成员变量list，不会被拷贝，需要我们自己实现深拷贝，幸运的是Java提供的大部分的容器类都实现了Cloneable接口。所以实现深拷贝并不是特别困难。

```java
public class Client {
    public static void main(String[] args) {
        ShallowClone cp = new ShallowClone();
        ShallowClone clonecp = (ShallowClone) cp.clone();
        clonecp.show();
        System.out.println(clonecp.list == cp.list);
 
        DeepClone cp2 = new DeepClone();
        DeepClone clonecp2 = (DeepClone) cp2.clone();
        clonecp2.show();
        System.out.println(clonecp2.list == cp2.list);
    }
}
```

运行结果：

```acsharp
浅克隆
true
深克隆
false
```