# Java设计模式之结构型：代理模式

#### 前言：

​    我们一般在租房子时会去找中介，为什么呢？因为你对该地区房屋的信息掌握的不够全面，希望找一个更熟悉的人去帮你做；再比如我们打官司需要请律师，因为律师在法律方面有专长，可以替我们进行操作，表达我们的想法；再比如在淘宝上面买东西，你使用支付宝平台支付，卖家请物流公司发货，在这个过程汇总支付宝、物流公司都扮演者“第三者”的角色在帮你完成物品的购买，这里的第三者我们可以将其称之为代理者，在我们实际生活中这种代理情况无处不在！



#### 一、什么是代理模式：

​    通过上面的例子，我们可以很清楚地理解什么是代理模式：

​    代理模式的设计动机是通过代理对象来访问真实对象，通过建立一个对象代理类，由代理对象控制原对象的引用，从而实现对真实对象的操作。在代理模式中，代理对象主要起到一个中介的作用，用于协调与连接调用者(即客户端)和被调用者(即目标对象)，在一定程度上降低了系统的耦合度，同时也保护了目标对象。但缺点是在调用者与被调用者之间增加了代理对象，可能会造成请求的处理速度变慢，

#### 

#### 二、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102100008694.jpg)

> - Subject：抽象角色，声明了真实对象和代理对象的共同接口；
> - Proxy：代理角色，实现了与真实对象相同的接口，所以在任何时刻都能够代理真实对象，并且代理对象内部包含了真实对象的引用，所以它可以操作真实对象，同时也可以附加其他的操作，相当于对真实对象进行封装。
> - RealSubject：真实对象，是我们最终要引用的对象。



#### 三、代码实现：

某天你看到一位女生，一见钟情，心里发誓要她做你女朋友，但是你想这样直接上去可能会唐突了。于是你采用迂回政策，先和她室友搞好关系，然后通过她室友给她礼物，然后……。

首先出现的就是美女一枚：BeautifulGirl.java

```java
public class BeautifulGirl {
    String name;
    
    public BeautifulGirl(String name){
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
}
```

 然后是抽象主题，送礼物接口：GiveGift.java

```java
public interface GiveGift {
    /**
     * 送花
     */
    void giveFlowers();
    
    /**
     * 送巧克力
     */
    void giveChocolate();
    
    /**
     * 送书
     */
    void giveBook();
}
```

你小子：You.java

```java
public class You implements GiveGift {
    BeautifulGirl mm ;     //美女
    
    public You(BeautifulGirl mm){
        this.mm = mm;
    }
    
 
    public void giveBook() {
        System.out.println(mm.getName() +",送你一本书....");
    }
 
    public void giveChocolate() {
        System.out.println(mm.getName() + ",送你一盒巧克力....");
    }
 
    public void giveFlowers() {
        System.out.println(mm.getName() + ",送你一束花....");
    }
}
```

 她闺蜜室友：HerChum.java

```java
public class HerChum implements GiveGift{
 
    You you;
    
    public HerChum(BeautifulGirl mm){
        you = new You(mm);
    }
    
    public void giveBook() {
        you.giveBook();
    }
 
    public void giveChocolate() {
        you.giveChocolate();
    }
 
    public void giveFlowers() {
        you.giveFlowers();
    }
}
```

 客户端：Client.java

```java
public class Client {
    public static void main(String[] args) {
        BeautifulGirl mm = new BeautifulGirl("小屁孩...");
        
        HerChum chum = new HerChum(mm);
        
        chum.giveBook();
        chum.giveChocolate();
        chum.giveFlowers();
    }
}
```

 运行结果：

```erlang
小屁孩...,送你一本书....
 
小屁孩...,送你一盒巧克力....
 
小屁孩...,送你一束花....
```



#### 四、代理模式与装饰器模式的区别：

代理模式跟装饰器模式比较类似，有些读者可能容易将他们混淆，这里我们简单介绍下两者的区别：

- （1）装饰器模式关注于在对象上动态添加新行为，而代理模式虽然也可以增加新的行为，但关注于控制对象的访问。
- （2）装饰器模式执行主体是原类；代理模式是代理原类进行操作，执行主体是代理类。
- （3）代理模式中，代理类可以对客户端隐藏真实对象的具体信息，因此使用代理模式时，我们常常在代理类中创建真实对象的实例。而装饰器模式的通常做法是将原始对象作为参数传给装饰者的构造器。也就是说。代理模式的代理和真实对象之间的对象通常在编译时就已经确定了，而装饰者能够在运行时递归地被构造。