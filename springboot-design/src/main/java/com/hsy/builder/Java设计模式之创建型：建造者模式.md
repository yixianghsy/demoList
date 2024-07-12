## 电商项目中的套餐是否而已用这个模式呢？
# Java设计模式之创建型：建造者模式

#### 一、什么是[建造者模式](https://so.csdn.net/so/search?q=建造者模式&spm=1001.2101.3001.7020)：

​    建造者模式将复杂产品的创建步骤分解在在不同的方法中，使得创建过程更加清晰，从而更精确控制复杂对象的产生过程；通过隔离复杂对象的构建与使用，也就是将产品的创建与产品本身分离开来，使得同样的构建过程可以创建不同的对象；并且每个具体建造者都相互独立，因此可以很方便地替换具体建造者或增加新的具体建造者，用户使用不同的具体建造者即可得到不同的产品对象。

​    但建造者模式的缺陷是要求创建的产品具有较多的共同点、组成部分相似，如果产品之间的差异性很大，则不适合使用建造者模式。同时如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大。



#### 二、UML结构图：

![img](http://192.168.10.251:9001/blog/20181101190336785.jpg)

> - 抽象建造者 Builder：相当于建筑蓝图，声明了创建 Product 对象的各个部件指定的抽象接口。
> - 具体建造者 ConcreteBuilder：实现Builder抽象接口，构建和装配各个部件，定义并明确它所创建的表示，并提供一个检索产品的接口。
> - 指挥者 Director：构建一个使用 Builder 接口的对象。主要有两个作用，一是隔离用户与对象的生产过程，二是负责控制产品对象的生产过程。
> - 产品角色 Product：被构造的复杂对象。ConcreteBuilder 创建该产品的内部表示并定义它的装配过程，包含定义组成部件的类，包括将这些部件装配成最终产品的接口。

#### 

#### 三、代码实现：

​    KFC里面一般都有好几种可供客户选择的套餐，它可以根据客户所点的套餐，然后在后面做这些套餐，返回给客户的事一个完整的、美好的套餐。下面我们将会模拟这个过程，我们约定套餐主要包含汉堡、薯条、可乐、鸡腿等等组成部分，使用不同的组成部分就可以构建出不同的套餐。

![img](http://192.168.10.251:9001/blog/2018110119142952.jpg)

首先是套餐类：

```java
/**
 * 首先是套餐类
 */
public class Meal {
    private String food;
    private String drink;

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }
```

套餐构造器：

```java
/**
 * 套餐构造器：
 */
public abstract class MealBuilder {
    Meal meal = new Meal();

    public abstract void buildFood();

    public abstract void buildDrink();

    public Meal getMeal(){
        return meal;
    }
}
```

套餐A、套餐B。这个两个套餐都是实现抽象套餐类：

```java
public class MealA extends MealBuilder{

    public void buildDrink() {
        meal.setDrink("一杯可乐");
    }

    public void buildFood() {
        meal.setFood("一盒薯条");
    }

}
```

```
public class MealB extends MealBuilder{

    public void buildDrink() {
        meal.setDrink("一杯柠檬果汁");
    }

    public void buildFood() {
        meal.setFood("三个鸡翅");
    }

}
```

最后是KFC的服务员，它相当于一个指挥者，它决定了套餐是的实现过程，然后给你一个完美的套餐。

```java
/**
 * KFC的服务员
 */
public class KFCWaiter {
    private MealBuilder mealBuilder;

    public void setMealBuilder(MealBuilder mealBuilder) {
        this.mealBuilder = mealBuilder;
    }

    public Meal construct(){
        //准备食物
        mealBuilder.buildFood();
        //准备饮料
        mealBuilder.buildDrink();

        //准备完毕，返回一个完整的套餐给客户
        return mealBuilder.getMeal();
    }
}
```

测试类：

```java
@SpringBootTest
public class BuilderTest {
    @Test
    void contextLoads() {
        //服务员
        KFCWaiter waiter = new KFCWaiter();
        //套餐A
        MealA a = new MealA();
        //服务员准备套餐A
        waiter.setMealBuilder(a);
        //获得套餐
        Meal mealA = waiter.construct();

        System.out.print("套餐A的组成部分:");
        System.out.println(mealA.getFood() + "---" + mealA.getDrink());
    }
}
```

运行结果：

```less
套餐A的组成部分:一盒薯条---一杯可乐
```



#### 四、抽象工厂模式和建造者模式的区别：

​    两者都是创建型模式，并且最终都是得到一个产品，但两者的区别在于：

1、抽象工厂模式实现对产品族的创建，产品族指的是不同分类维度的产品组合，用抽象工厂模式不需要关心具体构建过程，只关心产品由什么工厂生产即可。而建造者模式则更关心的是对象的构建过程，要求按照指定的蓝图建造产品，主要目的是通过组装零配件而产生一个新产品。

2、在抽象工厂模式中使用“工厂”来描述构建者，而在建造者模式中使用“车间”来描述构建者。

（1）抽象工厂模式就好比是一个一个的工厂，宝马车工厂生产宝马SUV和宝马VAN，奔驰车工厂生产奔驰车SUV和奔驰VAN，它是从一个更高层次去看对象的构建，具体到工厂内部还有很多的车间，如制造引擎的车间、装配引擎的车间等，但这些都是隐藏在工厂内部的细节，对外不公布。也就是对领导者来说，他只要关心一个工厂到底是生产什么产品的，不用关心具体怎么生产。

（2）建造者模式就不同了，它是由车间组成，不同的车间完成不同的创建和装配任务，一个完整的汽车生产过程需要引擎制造车间、引擎装配车间的配合才能完成，它们配合的基础就是设计蓝图，而这个蓝图是掌握在车间主任（Director类）手中，它给建造车间什么蓝图就能生产什么产品，建造者模式更关心建造过程。虽然从外界看来一个车间还是生产车辆，但是这个车间的转型是非常快的，只要重新设计一个蓝图，即可产生不同的产品，这有赖于建造者模式的功劳。

3、相对来说，抽象工厂模式比建造者模式的粒度要大，它关注产品整体，而建造者模式关注构建过程，所以建造者模式可以很容易地构建出一个崭新的产品，只要指挥类 Director 能够提供具体的工艺流程。也正因为如此，两者的应用场景截然不同，如果希望屏蔽对象的创建过程，只提供一个封装良好的对象，则可以选择抽象工厂方法模式。而建造者模式可以用在构件的装配方面，如通过装配不同的组件或者相同组件的不同顺序，可以产生出一个新的对象，它可以产生一个非常灵活的架构，方便地扩展和维护系统。