# Java设计模式之结构型：享元模式

#### 一、什么是[享元模式](https://so.csdn.net/so/search?q=享元模式&spm=1001.2101.3001.7020)：

​    享元模式通过共享技术有效地支持细粒度、状态变化小的对象复用，当系统中存在有多个相同的对象，那么只共享一份，不必每个都去实例化一个对象，极大地减少系统中对象的数量。比如说一个文本系统，每个字母定一个对象，那么大小写字母一共就是52个，那么就要定义52个对象。如果有一个1M的文本，那么字母是何其的多，如果每个字母都定义一个对象那么内存早就爆了。那么如果要是每个字母都共享一个对象，那么就大大节约了资源。

​    在了解享元模式之前我们先要了解两个概念：内部状态、外部状态。

- 内部状态：在享元对象内部不随外界环境改变而改变的共享部分。
- 外部状态：随着环境的改变而改变，不能够共享的状态就是外部状态。

​    由于享元模式区分了内部状态和外部状态，所以我们可以通过设置不同的外部状态使得相同的对象可以具备一些不同的特性，而内部状态设置为相同部分。在我们的程序设计过程中，我们可能会需要大量的细粒度对象来表示对象，如果这些对象除了几个参数不同外其他部分都相同，这个时候我们就可以利用享元模式来大大减少应用程序当中的对象。如何利用享元模式呢？这里我们只需要将他们少部分的不同的部分当做参数移动到类实例的外部去，然后再方法调用的时候将他们传递过来就可以了。这里也就说明了一点：内部状态存储于享元对象内部，而外部状态则应该由客户端来考虑。



#### 二、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102204012338.jpg)

> - （1）Flyweight：抽象享元类，所有具体享元类的超类或者接口，通过这个接口，Flyweight 可以接受并作用于外部专题；
> - （2）ConcreteFlyweight：具体享元类。指定内部状态，为内部状态增加存储空间。
> - （3）UnsharedConcreteFlyweight：非共享具体享元类。指出那些不需要共享的Flyweight子类。
> - （4）FlyweightFactory：享元工厂类，用来创建并管理Flyweight对象，它主要用来确保合理地共享Flyweight。

​    享元模式的核心是享元工厂类，享元工厂类维护了一个对象存储池，当客户端需要对象时，首先从享元池中获取，如果享元池中存在对象实例则直接返回，如果享元池中不存在，则创建一个新的享元对象实例返回给用户，并在享元池中保存该新增对象，这点有些单例的意思。

​    工厂类通常会使用集合类型来保存对象，如 HashMap、Hashtable、Vector 等等，在 Java 中，数据库连接池、线程池等都是用享元模式的应用。

```java
public class FlyweightFactory{
    private HashMap flyweights = new HashMap();
    
    public Flyweight getFlyweight(String key){
        if(flyweights.containsKey(key)){
            return (Flyweight)flyweights.get(key);
        }
        else{
            Flyweight fw = new ConcreteFlyweight();
            flyweights.put(key,fw);
            return fw;
        }
    }
}
```



#### 三、代码实现：

 场景：假如我们有一个绘图的应用程序，通过它我们可以出绘制各种各样的形状、颜色的图形，那么这里形状和颜色就是内部状态了，通过享元模式我们就可以实现该属性的共享了。如下：

首先是形状类：Shape.java。它是抽象类，只有一个绘制图形的抽象方法。

```java
public abstract class Shape {
    public abstract void draw();
}
```

然后是绘制圆形的具体类。Circle.java：

```java
public class Circle extends Shape{
    private String color;
    public Circle(String color){
        this.color = color;
    }
 
    public void draw() {
        System.out.println("画了一个" + color +"的圆形");
    }
}
```

再是享元工厂类。FlyweightFactory：

```java
//核心类
public class FlyweightFactory{
    static Map<String, Shape> shapes = new HashMap<String, Shape>();
    
    public static Shape getShape(String key){
        Shape shape = shapes.get(key);
        //如果shape==null,表示不存在,则新建,并且保持到共享池中
        if(shape == null){
            shape = new Circle(key);
            shapes.put(key, shape);
        }
        return shape;
    }
    
    public static int getSum(){
        return shapes.size();
    }
}
```

在这里定义了一个HashMap 用来存储各个对象，用户需要对象时，首先从享元池中获取，如果享元池中不存在，则创建一个新的享元对象返回给用户，并在享元池中保存该新增对象。

最后是客户端程序：Client.java：

```java
public class Client {
    public static void main(String[] args) {
        Shape shape1 = FlyweightFactory.getShape("红色");
        shape1.draw();
        
        Shape shape2 = FlyweightFactory.getShape("灰色");
        shape2.draw();
        
        Shape shape3 = FlyweightFactory.getShape("绿色");
        shape3.draw();
        
        Shape shape4 = FlyweightFactory.getShape("红色");
        shape4.draw();
        
        Shape shape5 = FlyweightFactory.getShape("灰色");
        shape5.draw();
        
        Shape shape6 = FlyweightFactory.getShape("灰色");
        shape6.draw();
        
        System.out.println("一共绘制了"+FlyweightFactory.getSum()+"中颜色的圆形");
    }
}
```

运行结果：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102204818546.jpg)

​    在 Java 中，String 类型就是使用享元模式，String 对象是 final 类型，对象一旦创建就不可改变。而 Java 的字符串常量都是存在字符串常量池中的，JVM 会确保一个字符串常量在常量池中只有一个拷贝。

String a="abc"，其中"abc"就是一个字符串常量。熟悉java的应该知道下面这个例子：

```java
String a = "hello";
String b = "hello";
if(a == b)
　System.out.println("OK");
else
　System.out.println("Error");
```

输出结果是：OK。可以看出if条件比较的是两a和b的地址，也可以说是内存空间。



#### 四、享元模式的优缺点：

1、享元模式的优点：

（1）极大减少系统中对象的个数；

（2）由于使用了外部状态，外部状态相对独立，不会影响到内部状态，所以享元模式使得享元对象能够在不同的环境被共享。

2、享元模式的缺点：

（1）由于享元模式需要区分外部状态和内部状态，使得应用程序在某种程度上来说更加复杂化了。

（2）为了使对象可以共享，需要将享元对象的状态外部化，而读取外部状态使得运行时间变长。

3、适用场景：

（1）如果系统中存在大量的相同或者相似的对象，由于这类对象的大量使用，会造成系统内存的耗费，可以使用享元模式来减少系统中对象的数量。

（2）对象的大部分状态都可以外部化，可以将这些外部状态传入对象中。