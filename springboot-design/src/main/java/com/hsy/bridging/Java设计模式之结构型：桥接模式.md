# Java设计模式之结构型：桥接模式

一、什么是桥接模式：
        桥接，顾名思义，就是用来连接两个部分，使得两个部分可以互相通讯，桥接模式的作用就是为被分离的抽象部分和实现部分搭桥。在现实生活中一个物品在搭配不同的配件时会产生不同的动作和结果，例如一辆赛车搭配的是硬胎或者是软胎就能够在干燥的马路上行驶，而如果要在下雨的路面行驶，就需要搭配雨胎了，这种根据行驶的路面不同，需要搭配不同的轮胎的变化的情况，我们从软件设计的角度来分析，就是一个系统由于自身的逻辑，会有两个或多个维度的变化，而为了应对这种变化，我们就可以使用桥接模式来进行系统的解耦。 桥接模式将一个系统的抽象部分和实现部分分离，使它们都可以独立地进行变化，对应到上面就是赛车的种类可以相对变化，轮胎的种类可以相对变化，形成一种交叉的关系，最后的结果就是一种赛车对应一种轮胎就能够成功产生一种结果和行为。 

        桥接模式将系统的抽象部分与实现部分分离解耦，使他们可以独立的变化。为了达到让抽象部分和实现部分独立变化的目的，桥接模式使用组合关系来代替继承关系，抽象部分拥有实现部分的接口对象，从而能够通过这个接口对象来调用具体实现部分的功能。也就是说，桥接模式中的桥接是一个单方向的关系，只能够抽象部分去使用实现部分的对象，而不能反过来。 
    
        桥接模式符合“开闭原则”，提高了系统的可拓展性，在两个变化维度中任意扩展一个维度，都不需要修改原来的系统；并且实现细节对客户不透明，可以隐藏实现细节。但是由于聚合关系建立在抽象层，要求开发者针对抽象进行编程，这增加系统的理解和设计难度。
    
        所以，桥接模式一般适用于以下几种应用场景：

（1）系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性，避免在两个层次之间建立静态的继承联系，则可以通过桥接模式使他们在抽象层建立一个关联关系；
（2）系统不希望使用继承或因为多层次继承导致系统类的个数急剧增加时
（3）一个类存在两个独立变化的维度，而这两个维度都需要进行扩展。
二、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102162654866.jpg)

抽象化角色 Abstraction：定义抽象的接口，包含一个对实现化角色的引用，抽象角色的方法需要调用实现化角色；
扩展抽象化角色 RefinedAbstraction：抽象化角色的子类，一般对抽象部分的方法进行完善和扩展，实现父类中的业务方法，并通过组合/聚合关系调用实现化角色中的业务方法
实现化角色 Implementor：定义具体行为、具体特征的应用接口，供扩展抽象化角色使用，一般情况下是由实现化角色提供基本的操作，而抽象化角色定义基于实现部分基本操作的业务方法；
具体实现化角色 ConcreteImplementor：完善实现化角色中定义的具体逻辑。

原文链接：https://blog.csdn.net/a745233700/article/details/120271538。
三、代码实现：
Implementor 接口类：

```java
public interface Implementor {
    void operationImpl();
}
```

ConcreteImplementor 接口实现类：

```java
public class ConcreteImplementorA implements Implementor{
    @Override
    public void operationImpl() {
        //具体实现
    }
}

public class ConcreteImplementorB implements Implementor{
    @Override
    public void operationImpl() {
        //具体实现
    }
}
```

Abstraction 抽象类：

```java
public abstract class Abstraction {
    private Implementor implementor;

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }
     
    public void operation() {
        implementor.operationImpl();
    }

}
```

RefinedAbstraction 抽象类的具体实现：

```java
public class RefinedAbstraction extends Abstraction{
    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }
 
    public void refinedOperation() {
        //对 Abstraction 中的 operation 方法进行扩展
    }
}
```

​        看了这段通用代码之后，桥接模式的结构应该就很清楚了，需要注意的是 RefinedAbstraction 根据实际情况是可以有多个的。 当然上面的 UML 类图和通用代码只是最常用的实现方式而已，在实际使用中可能会有其他的情况，比如 Implementor 只有一个类的情况，虽然这时候可以不去创建 Implementor 接口，精简类的层次，但是我建议还是需要抽象出实现部分的接口。

四、JDBC源码解析-桥接模式：
该部分引用自：JDBC和桥接模式 - 枯落 - 博客园

        Java 中，我们使用 JDBC 连接数据库时，在各个数据库之间进行切换，基本不需要动太多的代码，原因就是使用了桥接模式，JDBC 提供统一接口，每种类型的数据库提供各自的实现，然后由桥接类创建一个连接数据库的驱动，使用某一个数据库的时候只需要切换一下就行。接下来我们就对 JDBC 的源码做下剖析：

通过原生JDBC API连接MySQL数据库，则有如下示例代码：

```
Class.forName("com.mysql.cj.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://<host>:<port>/<database>");
```

短短两行代码难以看出桥接模式的结构，下面先对源码进行一定的分析，理解各个类和接口之间的关系：

1、源码分析：

（1）Class.forName() 方法：

        该方法将返回与给定字符串名的类或接口相关联的 java.lang.Class 类对象，用于在程序运行时动态加载该类或该接口到当前线程中，如果 Class.forName() 加载的是一个类，也会执行类中包含的static { } 静态代码段

（2）com.mysql.cj.jdbc.Driver 类：

        MySQL 将具体的 java.sql.Driver 接口的实现放到了 NonRegisteringDriver 中，com.mysql.cj.jdbc.Driver 类仅包含一段静态代码，具体类图如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/2021091315410059.png)

        其中最关键的是静态代码段中的 DriverManager.registerDriver(new Driver()) ，它会在客户端调用Class.forName() 方法加载 com.mysql.cj.jdbc.Driver 类的同时被执行，Driver 类自身的一个实例被注册到 DriverManager（即保存到 DriverManager 的静态字段 registeredDrivers 内），注册过程的源码如下： 

```java
public static synchronized void registerDriver(java.sql.Driver driver, DriverAction da)
throws SQLException {
  /* Register the driver if it has not already been added to our list */
  if(driver != null) {
    registeredDrivers.addIfAbsent(new DriverInfo(driver, da));
  } else {
    // This is for compatibility with the original DriverManager
    throw new NullPointerException();
  }
  println("registerDriver: " + driver);
}
```

​        registeredDrivers 静态字段的类型是实现了 List 接口的 CopyOnWriteArrayList 类，它能够保存进一步封装 java.sql.Driver 接口的 DriverInfo 类实例，DriverInfo 类的声明代码如下：

```java
class DriverInfo {
  final Driver driver;
  DriverAction da;
  DriverInfo(Driver driver, DriverAction action) {
    this.driver = driver;
    da = action;
  }
  // ……
}
```

DriverInfo 还包装了 DriverAction，DriverAction 会在Driver被取消注册时被调用，在 MySQL 的 Driver 在向 DriverManager 进行注册时，DriverAction 被设置为 null

（3）DriverManager 类：

由上面的分析可得，Class.forName() 方法调用后，com.mysql.cj.jdbc.Driver 类被加载，并执行static { } 静态代码段，将 com.mysql.cj.jdbc.Driver 类实例注册到 DriverManager 中。然后，客户端会调用 DriverManager.getConnection() 方法获取一个 Connection 数据库连接实例，该方法的部分源码如下：

```JAVA
private static Connection getConnection(String url, java.util.Properties info, Class<?> caller) throws SQLException {
  // ……
  for(DriverInfo aDriver : registeredDrivers) {
    // If the caller does not have permission to load the driver then
    // skip it.
    if(isDriverAllowed(aDriver.driver, callerCL)) {
      try {
        println(" trying " + aDriver.driver.getClass().getName());
        Connection con = aDriver.driver.connect(url, info);
        if (con != null) {
          // Success!
          println("getConnection returning " + aDriver.driver.getClass().getName());
          return (con);
        }
      } catch (SQLException ex) {
        if (reason == null) {
          reason = ex;
        }
      }
    } else {
      println(" skipping: " + aDriver.getClass().getName());
    }
  }
  // ……
}
```

DriverManager.getConnection() 方法会遍历 registeredDrivers 静态字段，获取字段内保存的每一个 Driver 来尝试响应客户端的数据库连接请求，若所有 Driver 都连接数据库失败，则提示连接失败信息

（4）Connection接口：

Connection 代表和特定数据库的连接会话，能够执行SQL语句并在连接的上下文中返回执行结果。因此，DriverManager.getConnection() 方法返回的 Connection 数据库连接实例根据不同的数据库有不同的实现，MySQL 的 Connection 接口实现关系如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20210913155210732.png)

 2、源码类图：

根据源码的分析，绘制类图如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20210913160420412.png)

 对 Driver 和 Connection 进行抽象，绘制类图如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20210913160443564.png)

        桥接模式通过聚合关系代替继承关系，实现抽象化和实现化部分的解耦。以上述 JDBC 在 MySQL 中的简略类图为例，抽象化部分有 DriverManager，实现化部分有 Driver 接口和 Connection 接口。对于不同的数据库，Driver接口和Connection接口都有自己独特的实现类。
    
        但是，和 Driver 接口不同的是，Connection 接口与 DriverManager 类的关系只是联系较弱的依赖关系，并不符合桥接模式的定义和特点。因此，在考虑桥接模式的情况下，可以再次将类图进行简化：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20210913161330536.png)

 最后，我们将其它数据库的Driver接口实现也考虑在内，绘制类图如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20210913161349809.png)

            桥接模式中的实现化角色 (Implementor) 对应上图的 Driver 接口，具体实现化 (Concrete Implementor) 角色对应 MysqlDriver、OracleDriver 和 MariadbDriver，扩展抽象化 (Refined Abstraction) 角色对应 DriverManager，不具有抽象化 (Abstraction) 角色作为扩展抽象化角色的父类

3、对 JDBC 的观点：

（1）观点一：JDBC 的桥接模式是一中简化的桥接模式

        桥接模式的主要应用场景是某个类存在两个独立变化的维度，且这两个维度都需要进行扩展，而现在仅有 Driver 一个变化维度，DriverManager 没有抽象化父类，它本身也没有任何子类，因此我认为，在 JDBC 中，是一种简化的桥接模式。
    
        倘若 JDBC 针对 Connection 接口的设计不是将它作为 Driver 和 DriverManager 的"依赖"来处理，而是也作为一个变化的维度加入到桥接模式，或许能够更好地体现JDBC对桥接模式的实现，一种"假想"的桥接模式如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20210913161932446.png)

 （2）观点二：JDBC采用的是策略模式而不是桥接模式

问题来源知乎：[jdbc是桥接模式还是策略模式？ - 知乎](https://www.zhihu.com/question/67735508)

        因为这确实和策略模式十分相似，如果把桥接模式的抽象部分简化来看，不去设计Abstraction，也就是用 Refined Abstraction 代替 Abstraction，那么就类似于策略模式的 Context 来使用接口的对象。
    
        但是，桥接模式和策略模式的目的是不一样的，策略模式属于对象行为模式（描述对象之间怎样相互协作共同完成单个对象都无法单独完成的任务，以及怎样分配职责），它的目的是封装一系列的算法，使得算法可以相互替代，并在程序运行的不同时刻选择合适的算法。而桥接模式属于对象结构模式（描述如何将对象按某种布局组成更大的结构），它的目的是将抽象与实现分离，使它们可以独立变化
    
        因此，从设计的目的来看，JDBC采用的并不是策略模式，在一段程序中数据库驱动并不存在频繁地相互替换

（3）观点三：变化的维度一个是平台，另一个是数据库

问题来源：https://www.unclewang.info/learn/java/771/?tdsourcetag=s_pctim_aiomsg

        这是我认同的一个观点，引用原文的话：变的是平台和数据库，平台在 JVM 这个层面就解决了，因为所有操作系统 Java 基本都会提供对应JDK，这也是 "Once Write，Run AnyWhere" 的原因。而数据库则是依托公司的具体实现，各个公司都提供对应的 Driver 类，我用 DriverManager 类进行懒加载.
    
        考虑数据库的实际应用场景，我们可能在不同的操作系统上使用不同的数据库，但是JVM的平台无关性使得我们不再有操作系统层面上的变化。假设不存在JVM，那么不同的客户端加载和运行数据库驱动程序的代码自然也各有不同，即 DriverManager 会因操作系统的变化而变化，不同的操作系统可以有不同的注册 Driver 的方式，不过因为存在JVM，我们现在不再有"平台"这一变化维度了

（4）观点四：变化的维度一个是客户端应用系统，另一个是数据库

问题来源：[java设计模式－桥梁模式(桥接模式 Bridge) - 简书](https://www.jianshu.com/p/775cb53a4da2)

        一个比较独特的观点，引用原文的话：应用系统作为一个等级结构，与 JDBC 驱动器这个等级结构是相对独立的，它们之间没有静态的强关联。应用系统通过委派与JDBC驱动器相互作用，这是一个桥梁模式的例子。
    
        原文笔者不认为 DriverManager 作为 Refined Abstraction 角色存在，而是视作两个变化维度之间的一个"过渡"，原本的"桥"是 Abstraction 和 Implementor 之间的组合/聚合关系，而现在DriverManager 类本身成为了"桥"，可以看作是桥梁模式的一个变体

（5）观点五：变化的维度一个是 Driver，一个是 Connection：

        如果从观点四的原文笔者的角度看，把 DriverManager 类本身作为"桥"，那么我们还可以提出一种新的观点，绘制类图如下：



![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20210913163432140.png)