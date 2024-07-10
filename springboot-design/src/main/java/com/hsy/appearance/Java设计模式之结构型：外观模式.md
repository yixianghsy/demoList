# Java设计模式之结构型：外观模式

#### 一、什么是外观模式：

​    外观模式通过对客户端提供一个统一的接口，用于访问子系统中的一群接口。使用外观模式有以下几点好处：

（1）更加易用：使得子系统更加易用，客户端不再需要了解子系统内部的实现，也不需要跟众多子系统内部的模块进行交互，只需要跟外观类交互就可以了；

（2）松散耦合：将客户端与子系统解耦，让子系统内部的模块能更容易扩展和维护。

（3）更好的划分访问层次：通过合理使用 Facade，可以更好地划分访问的层次，有些方法是对系统外的，有些方法是系统内部使用的。把需要暴露给外部的功能集中到门面中，这样既方便客户端使用，也很好地隐藏了内部的细节。

​    但是如果外观模式对子系统类做太多的限制则减少了可变性和灵活性，所以外观模式适用于为复杂子系统提供一个简单接口，提高系统的易用性场景 以及 引入外观模式将子系统与客户端进行解耦，提高子系统的独立性和可移植性。

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102104024941.jpg)

​    如上图，引入外观模式后，子系统的使用变得更加简单，客户只需要与 Facede 外观角色打交道，子系统间的复杂关系由外观角色来实现，从而降低了系统的耦合度。如果没有 Facade 类，那么 Subsystem 之间将会相互持有对方的引用，会造成严重的依赖，修改一个类可能会导致其他类的连锁修改，但有了Facade类，他们之间的关系被放在了Facade类里，起到了[解耦](https://so.csdn.net/so/search?q=解耦&spm=1001.2101.3001.7020)的作用。



#### 二、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102104601540.jpg)



> - Facade 门面角色：客户端可以调用这个角色的方法，该角色知晓相关子系统的功能。在正常情况下，本角色会将所有从客户端发来的请求委派到相应的子系统去。
> - SubSystem 子系统角色：可以同时有一个或者多个子系统。每个子系统都不是一个单独的类，而是一个类的集合（如上面的子系统就是由 SubSystemA、SubSystemB、SubSystemC、SubSystemD 几个类组合而成）。每个子系统都可以被客户端直接调用，或者被门面角色调用。子系统并不知道门面的存在，对于子系统而言，门面角色仅仅是另外一个客户端而已。



#### 三、代码实现：

我们以一个计算机的启动过程为例：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102104938448.png)

我们先看下实现类：

```java
public class CPU {
	
	public void startup(){
		System.out.println("cpu startup!");
	}
	
	public void shutdown(){
		System.out.println("cpu shutdown!");
	}
}
```

```
public class Memory {
	
	public void startup(){
		System.out.println("memory startup!");
	}
	
	public void shutdown(){
		System.out.println("memory shutdown!");
	}
}
```

 

```
public class Disk {
	
	public void startup(){
		System.out.println("disk startup!");
	}
	
	public void shutdown(){
		System.out.println("disk shutdown!");
	}
}
```

```
//Facade类
public class Computer {
	private CPU cpu;
	private Memory memory;
	private Disk disk;
	
	public Computer(){
		cpu = new CPU();
		memory = new Memory();
		disk = new Disk();
	}
	
	public void startup(){
		System.out.println("start the computer!");
		cpu.startup();
		memory.startup();
		disk.startup();
		System.out.println("start computer finished!");
	}
	
	public void shutdown(){
		System.out.println("begin to close the computer!");
		cpu.shutdown();
		memory.shutdown();
		disk.shutdown();
		System.out.println("computer closed!");
	}
}
```

User类如下：

```java
public class User {
 
	public static void main(String[] args) {
		Computer computer = new Computer();
		computer.startup();
		computer.shutdown();
	}
}
```

运行结果：

```perl
start the computer!
cpu startup!
memory startup!
disk startup!
start computer finished!
begin to close the computer!
cpu shutdown!
memory shutdown!
disk shutdown!
computer closed!
```

Facade类其实相当于CPU、Disk、Memory模块的外观界面，有了这个Facade类，那么客户端就不需要亲自调用子系统中的CPU、Disk、Memory模块了，也不需要知道系统内部的实现细节，甚至都不需要知道CPU、Disk、Memory模块的存在，客户端只需要跟Facade类交互就好了，从而更好地实现了客户端和子系统中CPU、Disk、Memory模块的解耦，让客户端更容易地使用系统。



#### 四、其他问题：

1、一个系统可以有几个外观类？

​    在外观模式中，通常只需要一个外观类，并且此外观类只有一个实例，也就是单例类。但这并不意味着在整个系统里只有一个外观类，而仅仅是说对每一个子系统只有一个外观类。或者说，如果一个系统有好几个子系统的话，每一个子系统都有一个外观类，整个系统可以有数个外观类。

2、能否为子系统增加新行为？

​    外观模式的用意是为子系统提供一个集中化和简化的沟通管道，而不能向子系统加入新的行为。比如医院中的接待员并不是医护人员，接待员并不能为病人提供医疗服务。