# Java设计模式之创建型：工厂模式详解（简单工厂+工厂方法+抽象工厂）

在面向对象编程中，创建对象实例最常用的方式就是通过 new 操作符构造一个对象实例，但在某些情况下，new 操作符直接生成对象会存在一些问题。举例来说，对象的创建需要一系列的步骤：可能需要计算或取得对象的初始位置、选择生成哪个子对象实例、或在生成之前必须先生成一些辅助对象。 在这些情况，新对象的建立就是一个 “过程”，而不仅仅是一个操作，就像一部大机器中的一个齿轮传动。

        针对上面这种情况，我们如何轻松方便地构造对象实例，而不必关心构造对象示例的细节和复杂过程？解决方案就是使用一个工厂类来创建对象。

一、什么是工厂模式：
工厂模式将目的将创建对象的具体过程屏蔽隔离起来，从而达到更高的灵活性，工厂模式可以分为三类：

简单工厂模式（Simple Factory）
工厂方法模式（Factory Method）
抽象工厂模式（Abstract Factory）
这三种模式从上到下逐步抽象，并且更具一般性。《设计模式》一书中将工厂模式分为两类：工厂方法模式与抽象工厂模式。将简单工厂模式看为工厂方法模式的一种特例，两者归为一类。 我们先从以下案例对工厂模式做个初步的了解：

（1）在没有工厂的时代，如果客户需要一款宝马车，那么就需要客户去创建一款宝马车，然后拿来用。

（2）简单工厂模式：后来出现了工厂，用户不再需要去创建宝马车，由工厂进行创建，想要什么车，直接通过工厂创建就可以了。比如想要320i系列车，工厂就创建这个系列的车。

（3）工厂方法模式：为了满足客户，宝马车系列越来越多，如320i、523i等等系列，一个工厂无法创建所有的宝马系列，于是又单独分出来多个具体的工厂，每个具体工厂创建一种系列，即具体工厂类只能创建一个具体产品。但是宝马工厂还是个抽象，你需要指定某个具体的工厂才能生产车出来。

（4）抽象工厂模式：随着客户要求越来越高，宝马车必须配置空调，于是这个工厂开始生产宝马车和需要的空调。最终是客户只要对宝马的销售员说：我要523i空调车，销售员就直接给他523i空调车了。而不用自己去创建523i空调车宝马车。

下面我们就针对几种不同的工厂模式进行详细的说明：

二、简单工厂模式：
简单工厂模式的核心是定义一个创建对象的接口，将对象的创建和本身的业务逻辑分离，降低系统的耦合度，使得两个修改起来相对容易些，当以后实现改变时，只需要修改工厂类即可。

如果不使用工厂，用户将自己创建宝马车，具体UML图和代码如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/20181031221826432.jpg)

```
public class BMW320 {
	public BMW320(){
		System.out.println("制造-->BMW320");
	}
}

public class BMW523 {
	public BMW523(){
		System.out.println("制造-->BMW523");
	}
}

public class Customer {
	public static void main(String[] args) {
		BMW320 bmw320 = new BMW320();
		BMW523 bmw523 = new BMW523();
	}
}
```

​        用户需要知道怎么创建一款车，这样子客户和车就紧密耦合在一起了，为了降低耦合，就出现了简单工厂模式，把创建宝马的操作细节都放到了工厂里，而客户直接使用工厂的创建方法，传入想要的宝马车型号就行了，而不必去知道创建的细节。

1、简单工厂模式的UML图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/20181031222018373.jpg)

工厂类角色： 该模式的核心，用来创建产品，含有一定的商业逻辑和判断逻辑
抽象产品角色：它一般是具体产品继承的父类或者实现的接口。   
具体产品角色：工厂类所创建的对象就是此角色的实例。在java中由一个具体类实现。
2、代码实现：

产品类：

```
abstract class BMW {
	public BMW(){}
}

public class BMW320 extends BMW {
	public BMW320() {
		System.out.println("制造-->BMW320");
	}
}
public class BMW523 extends BMW{
	public BMW523(){
		System.out.println("制造-->BMW523");
	}
}
```

工厂类：

```
public class Factory {
	public BMW createBMW(int type) {
		switch (type) {
		

		case 320:
			return new BMW320();
	 
		case 523:
			return new BMW523();
	 
		default:
			break;
		}
		return null;
	}

}
```

用户类：

```
public class Customer {
	public static void main(String[] args) {
		Factory factory = new Factory();
		BMW bmw320 = factory.createBMW(320);
		BMW bmw523 = factory.createBMW(523);
	}
}
```

3、简单工厂模式的优缺点：

        简单工厂模式提供专门的工厂类用于创建对象，实现了对象创建和使用的职责分离，客户端不需知道所创建的具体产品类的类名以及创建过程，只需知道具体产品类所对应的参数即可，通过引入配置文件，可以在不修改任何客户端代码的情况下更换和增加新的具体产品类，在一定程度上提高了系统的灵活性。
    
        但缺点在于不符合“开闭原则”，每次添加新产品就需要修改工厂类。在产品类型较多时，有可能造成工厂逻辑过于复杂，不利于系统的扩展维护，并且工厂类集中了所有产品创建逻辑，一旦不能正常工作，整个系统都要受到影响。
    
        为了解决简单工厂模式的问题，出现了工厂方法模式。

三、工厂方法模式：
工厂方法模式将工厂抽象化，并定义一个创建对象的接口。每增加新产品，只需增加该产品以及对应的具体实现工厂类，由具体工厂类决定要实例化的产品是哪个，将对象的创建与实例化延迟到子类，这样工厂的设计就符合“开闭原则”了，扩展时不必去修改原来的代码。在使用时，用于只需知道产品对应的具体工厂，关注具体的创建过程，甚至不需要知道具体产品类的类名，当我们选择哪个具体工厂时，就已经决定了实际创建的产品是哪个了。

        但缺点在于，每增加一个产品都需要增加一个具体产品类和实现工厂类，使得系统中类的个数成倍增加，在一定程度上增加了系统的复杂度，同时也增加了系统具体类的依赖。

1、工厂方法的 UML 结构图如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2021091219362547.png)

抽象工厂 AbstractFactory： 工厂方法模式的核心，是具体工厂角色必须实现的接口或者必须继承的父类，在 Java 中它由抽象类或者接口来实现。
具体工厂 Factory：被应用程序调用以创建具体产品的对象，含有和具体业务逻辑有关的代码
抽象产品 AbstractProduct：是具体产品继承的父类或实现的接口，在 Java 中一般有抽象类或者接口来实现。
具体产品 Product：具体工厂角色所创建的对象就是此角色的实例。
2、代码实现：

产品类：

```
abstract class BMW {
	public BMW(){}
}
public class BMW320 extends BMW {
	public BMW320() {
		System.out.println("制造-->BMW320");
	}
}
public class BMW523 extends BMW{
	public BMW523(){
		System.out.println("制造-->BMW523");
	}
}
```

工厂类：

```
interface FactoryBMW {
	BMW createBMW();
}

public class FactoryBMW320 implements FactoryBMW{

	@Override
	public BMW320 createBMW() {
		return new BMW320();
	}

}
```

```
public class FactoryBMW523 implements FactoryBMW {
	@Override
	public BMW523 createBMW() {
		return new BMW523();
	}

```

}
客户类：

```
public class Customer {
	public static void main(String[] args) {
		FactoryBMW320 factoryBMW320 = new FactoryBMW320();
		BMW320 bmw320 = factoryBMW320.createBMW();

		FactoryBMW523 factoryBMW523 = new FactoryBMW523();
		BMW523 bmw523 = factoryBMW523.createBMW();
	}

}
```

四、抽象工厂模式：
在工厂方法模式中，我们使用一个工厂创建一个产品，一个具体工厂对应一个具体产品，但有时候我们需要一个工厂能够提供多个产品对象，而不是单一的对象，这个时候我们就需要使用抽象工厂模式。

        在介绍抽象工厂模式前，我们先厘清两个概念：

产品等级结构：产品等级结构指的是产品的继承结构，例如一个空调抽象类，它有海尔空调、格力空调、美的空调等一系列的子类，那么这个空调抽象类和他的子类就构成了一个产品等级结构。
产品族：产品族是指由同一个工厂生产的，位于不同产品等级结构中的一组产品。比如，海尔工厂生产海尔空调、海尔冰箱，那么海尔空调则位于空调产品族中。
产品等级结构和产品族结构示意图如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/20181031234513626.jpg)

1、什么是抽象工厂模式：

        抽象工厂模式主要用于创建相关对象的家族。当一个产品族中需要被设计在一起工作时，通过抽象工厂模式，能够保证客户端始终只使用同一个产品族中的对象；并且通过隔离具体类的生成，使得客户端不需要明确指定具体生成类；所有的具体工厂都实现了抽象工厂中定义的公共接口，因此只需要改变具体工厂的实例，就可以在某种程度上改变整个软件系统的行为。
    
        但该模式的缺点在于添加新的行为时比较麻烦，如果需要添加一个新产品族对象时，需要更改接口及其下所有子类，这必然会带来很大的麻烦。

2、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/20181031235217510.jpg)

抽象工厂 AbstractFactory：定义了一个接口，这个接口包含了一组方法用来生产产品，所有的具体工厂都必须实现此接口。
具体工厂 ConcreteFactory：用于生产不同产品族，要创建一个产品，用户只需使用其中一个工厂进行获取，完全不需要实例化任何产品对象。
抽象产品 AbstractProduct：这是一个产品家族，每一个具体工厂都能够生产一整组产品。
具体产品 Product
3、代码实现：

        通过抽象工厂模式，我们可以实现以下的效果：比如宝马320系列使用空调型号A和发动机型号A，而宝马230系列使用空调型号B和发动机型号B，在为320系列生产相关配件时，就无需制定配件的型号，它会自动根据车型生产对应的配件型号A。
    
        也就是说，当每个抽象产品都有多于一个的具体子类的时候（空调有型号A和B两种，发动机也有型号A和B两种），工厂角色怎么知道实例化哪一个子类呢？抽象工厂模式提供两个具体工厂角色（宝马320系列工厂和宝马230系列工厂），分别对应于这两个具体产品角色，每一个具体工厂角色只负责某一个产品角色的实例化，每一个具体工厂类只负责创建抽象产品的某一个具体子类的实例。

产品类：

//发动机以及型号

```
public interface Engine {}  

public class EngineA implements Engine{  
    public EngineA(){  
        System.out.println("制造-->EngineA");  
    }  
}  
public class EngineB implements Engine{  
    public EngineB(){  
        System.out.println("制造-->EngineB");  
    }  
}  
```

//空调以及型号

```
public interface Aircondition {} 

public class AirconditionA implements Aircondition{  
    public AirconditionA(){  
        System.out.println("制造-->AirconditionA");  
    }  
}  
public class AirconditionB implements Aircondition{  
    public AirconditionB(){  
        System.out.println("制造-->AirconditionB");  
    }  
} 
```


创建工厂类：

//创建工厂的接口

```
public interface AbstractFactory {  
    //制造发动机
    public Engine createEngine();
    //制造空调 
    public Aircondition createAircondition(); 
}  

/
```

/为宝马320系列生产配件

```
public class FactoryBMW320 implements AbstractFactory{     
    @Override  
    public Engine createEngine() {    
        return new EngineA();  
    }  
    @Override  
    public Aircondition createAircondition() {  
        return new AirconditionA();  
    }  
} 
```


//宝马523系列

```
public class FactoryBMW523 implements AbstractFactory {  
     @Override  
    public Engine createEngine() {    
        return new EngineB();  
    }  
    @Override  
    public Aircondition createAircondition() {  
        return new AirconditionB();  
    }  
} 
```

客户：

```
public class Customer {  
    public static void main(String[] args){  
        //生产宝马320系列配件
        FactoryBMW320 factoryBMW320 = new FactoryBMW320();  
        factoryBMW320.createEngine();
        factoryBMW320.createAircondition();
          

        //生产宝马523系列配件  
        FactoryBMW523 factoryBMW523 = new FactoryBMW523();  
        factoryBMW523.createEngine();
        factoryBMW523.createAircondition();
    }  

}
```

工厂模式小结：

1、工厂方法模式与抽象工厂模式的区别在于：

（1）工厂方法只有一个抽象产品类和一个抽象工厂类，但可以派生出多个具体产品类和具体工厂类，每个具体工厂类只能创建一个具体产品类的实例。

（2）抽象工厂模式拥有多个抽象产品类（产品族）和一个抽象工厂类，每个抽象产品类可以派生出多个具体产品类；抽象工厂类也可以派生出多个具体工厂类，同时每个具体工厂类可以创建多个具体产品类的实例

