# Java设计模式之结构型：装饰器模式

#### 一、什么是[装饰器模式](https://so.csdn.net/so/search?q=装饰器模式&spm=1001.2101.3001.7020)：

​    当需要对类的功能进行拓展时，一般可以使用继承，但如果需要拓展的功能种类很繁多，那势必会生成很多子类，增加系统的复杂性，并且使用继承实现功能拓展时，我们必须能够预见这些拓展功能，也就是这些功能在编译时就需要确定了。那么有什么更好的方式实现功能的拓展吗？答案就是装饰器模式。

​    装饰器模式可以动态给对象添加一些额外的职责从而实现功能的拓展，在运行时选择不同的装饰器，从而实现不同的行为；比使用继承更加灵活，通过对不同的装饰类进行排列组合，创造出很多不同行为，得到功能更为强大的对象；符合“开闭原则”，被装饰类与装饰类独立变化，用户可以根据需要增加新的装饰类和被装饰类，在使用时再对其进行组合，原有代码无须改变。

​    但是装饰器模式也存在缺点，首先会产生很多的小对象，增加了系统的复杂性，第二是排错比较困难，对于多次装饰的对象，调试时寻找错误可能需要逐级排查，较为烦琐。



#### 二、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/23/20181101233343940.jpg)

> - Component：抽象构件，是定义一个对象接口，可以给这个对象动态地添加职责。
> - ConcreteComponent：具体构件，是定义了一个具体的对象，也可以给这个对象添加一些职责。
> - Decorator：抽象装饰类，继承自 Component，从外类来扩展 Component 类的功能，但对于 Component 来说，是无需知道 Decorator 存在的。
> - ConcreteDecorator：具体装饰类，起到给 Component 添加职责的功能。

***\*装饰者与被装饰者都拥有共同的超类，但这里继承的目的是继承类型，而不是行为。\****



#### 三、代码实现：

**1、代码示例一：**

```java
//定义被装饰者
interface Human
{
    void wearClothes();
}
 
//定时装饰者
abstract class Decorator implements Human
{
    private Human human;
 
    public Decorator(Human human)
    {
        this.human = human;
    }
 
    @Override
    public void wearClothes()
    {
        human.wearClothes();
    }
}
 
//下面定义三种装饰，这是第一个，第二个第三个功能依次细化，即装饰者的功能越来越多
class Decorator_zero extends Decorator {
 
    public Decorator_zero(Human human) {
        super(human);
    }
 
    public void goHome() {
        System.out.println("进房子。。");
    }
 
    @Override
    public void wearClothes() {
        super.wearClothes();
        goHome();
    }
}
 
class Decorator_first extends Decorator {
 
    public Decorator_first(Human human) {
        super(human);
    }
 
    public void goClothespress() {
        System.out.println("去衣柜找找看。。");
    }
 
    @Override
    public void wearClothes() {
        super.wearClothes();
        goClothespress();
    }
}
 
class Decorator_two extends Decorator {
 
    public Decorator_two(Human human) {
        super(human);
    }
 
    public void findClothes() {
        System.out.println("找到一件D&G。。");
    }
 
    @Override
    public void wearClothes() {
        super.wearClothes();
        findClothes();
    }
}
 
 
class Person implements Human {
 
    @Override
    public void wearClothes() {
        System.out.println("穿什么呢。。");
    }
}
 
//测试类
public class DecoratorTest
{
    public static void main(String[] args)
    {
        Human person = new Person();
        Decorator decorator = new Decorator_two(new Decorator_first(new Decorator_zero(person)));
        decorator.wearClothes();
    }
}
```

运行结果：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/23/20210830224501631.png)

 其实就是进房子找衣服，通过装饰者的三层装饰，把细节变得丰富，该Demo的关键点：

（1）Decorator 抽象类持有Human接口，方法全部委托给该接口调用，目的是交给该接口的实现类进行调用。

（2）Decorator 抽象类的子类，即具体装饰者，里面都有一个构造方法调用 super(human)，这里就体现了抽象类依赖于子类实现，即抽象依赖于实现的原则。因为构造函数的参数都是 Human 类型，只要是该 Human 的实现类都可以传递进去，即表现出 Decorator dt = new Decorator_second(new Decorator_first(new Decorator_zero(human))) 这种结构的样子，所以当调用 dt.wearClothes() 的时候，又因为每个具体装饰者类中，都先调用 super.wearClothes() 方法，而该 super 已经由构造函数传递并指向了具体的某一个装饰者类，那么最终调用的就是装饰类的方法，然后才调用自身的装饰方法，即表现出一种装饰、链式的类似于过滤的行为。

（3）具体被装饰者类，可以定义初始的状态或者初始的自己的装饰，后面的装饰行为都在此基础上一步一步进行点缀、装饰。

（4）装饰者模式的设计原则为：对扩展开放、对修改关闭，这句话体现在如果想扩展被装饰者类的行为，无须修改装饰者抽象类，只需继承装饰者抽象类，实现额外的一些装饰或者叫行为即可对被装饰者进行包装。所以：扩展体现在继承、修改体现在子类中，而不是具体的抽象类，这充分体现了依赖倒置原则，这是自己理解的装饰者模式。

**2、代码示例二：**

现在需要一个汉堡，主体是鸡腿堡，可以选择添加生菜、酱、辣椒等等许多其他的配料，这种情况下就可以使用装饰者模式。

汉堡基类（被装饰者，相当于上面的Human）

```java
public abstract class Humburger {  
      
    protected  String name ;  
      
    public String getName(){  
        return name;  
    }  
    public abstract double getPrice();  
}  
```

鸡腿堡类（被装饰者的初始状态，有些自己的简单装饰，相当于上面的Person）

```java
public class ChickenBurger extends Humburger {  
      
    public ChickenBurger(){  
        name = "鸡腿堡";  
    }  
  
    @Override  
    public double getPrice() {  
        return 10;  
    }  
}  
```

配料的基类（装饰者，用来对汉堡进行多层装饰，每层装饰增加一些配料，相当于上面Decorator）

```java
public abstract class Condiment extends Humburger {  
      
    public abstract String getName();  
}  
```

生菜（装饰者的第一层，相当于上面的decorator_zero）

```java
public class Lettuce extends Condiment {  
      
    Humburger humburger;  
      
    public Lettuce(Humburger humburger){  
        this.humburger = humburger;  
    }  
  
    @Override  
    public String getName() {  
        return humburger.getName()+" 加生菜";  
    }  
  
    @Override  
    public double getPrice() {  
        return humburger.getPrice()+1.5;  
    }  
}  
```

辣椒（装饰者的第二层，相当于上面的decorator_first）

​	

```
public class Chilli extends Condiment {  
      
    Humburger humburger;  
      
    public Chilli(Humburger humburger){  
        this.humburger = humburger;  
          
    }  
  
    @Override  
    public String getName() {  
        return humburger.getName()+" 加辣椒";  
    }  
  
    @Override  
    public double getPrice() {  
        return humburger.getPrice();  //辣椒是免费的哦  
    }  
}  
```

测试类：

```java
package decorator;  
  
public class Test {  
    public static void main(String[] args) {  
        Humburger humburger = new ChickenBurger();  
        System.out.println(humburger.getName()+"  价钱："+humburger.getPrice());  
        Lettuce lettuce = new Lettuce(humburger);  
        System.out.println(lettuce.getName()+"  价钱："+lettuce.getPrice());  
        Chilli chilli = new Chilli(humburger);  
        System.out.println(chilli.getName()+"  价钱："+chilli.getPrice());  
        Chilli chilli2 = new Chilli(lettuce);  
        System.out.println(chilli2.getName()+"  价钱："+chilli2.getPrice());  
    }  
}  
```

输出：

```cobol
鸡腿堡  价钱：10.0  
鸡腿堡 加生菜  价钱：11.5  
鸡腿堡 加辣椒  价钱：10.0  
鸡腿堡 加生菜 加辣椒  价钱：11.5  
```