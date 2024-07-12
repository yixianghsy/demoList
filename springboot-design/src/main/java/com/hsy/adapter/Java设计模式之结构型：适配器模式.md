# Java设计模式之结构型：适配器模式

#### 一、什么是[适配器模式](https://so.csdn.net/so/search?q=适配器模式&spm=1001.2101.3001.7020)：

​    适配器模式主要用于将一个类的接口转化成客户端希望的目标类格式，使得原本不兼容的类可以在一起工作，将目标类和适配者类解耦；同时也符合“开闭原则”，可以在不修改原代码的基础上增加新的适配器类；将具体的实现封装在适配者类中，对于客户端类来说是透明的，而且提高了适配者的复用性，但是缺点在于更换适配器的实现过程比较复杂。

​    所以，适配器模式比较适合以下场景：

- （1）系统需要使用现有的类，而这些类的接口不符合系统的接口。
- （2）使用第三方组件，组件接口定义和自己定义的不同，不希望修改自己的接口，但是要使用第三方组件接口的功能。

下面两个非常形象的例子很好地说明了什么是适配器模式：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/23/20181101222608284.jpg)

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/23/20181101222622471.jpg)



#### 二、适配器模式的三种实现方式：

适配器模式主要分成三类：类的适配器模式、对象的适配器模式、接口的适配器模式。

**1、类的适配器模式：**

**![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/23/20181101224524959.jpg)**

> - 目标接口（Target）：客户所期待的接口。目标可以是具体的或抽象的类，也可以是接口。
> - 需要适配的类（Adaptee）：需要适配的类或适配者类。
> - 适配器（Adapter）：通过包装一个需要适配的对象，把原接口转换成目标接口。

```java
// 已存在的、具有特殊功能、但不符合我们既有的标准接口的类
class Adaptee {
	public void specificRequest() {
		System.out.println("被适配类具有 特殊功能...");
	}
}
 
// 目标接口，或称为标准接口
interface Target {
	public void request();
}
 
// 具体目标类，只提供普通功能
class ConcreteTarget implements Target {
	public void request() {
		System.out.println("普通类 具有 普通功能...");
	}
}
 
// 适配器类，继承了被适配类，同时实现标准接口
class Adapter extends Adaptee implements Target{
	public void request() {
		super.specificRequest();
	}
}
 
// 测试类public class Client {
	public static void main(String[] args) {
		// 使用普通功能类
		Target concreteTarget = new ConcreteTarget();
		concreteTarget.request();
		
		// 使用特殊功能类，即适配类
		Target adapter = new Adapter();
		adapter.request();
	}
}
```

运行结果：

```erlang
普通类 具有 普通功能...



被适配类具有 特殊功能...
```

**2、对象的适配器模式：**

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/23/20181101224841981.jpg)

```
// 适配器类，直接关联被适配类，同时实现标准接口
class Adapter implements Target{
	// 直接关联被适配类
	private Adaptee adaptee;
	
	// 可以通过构造函数传入具体需要适配的被适配类对象
	public Adapter (Adaptee adaptee) {
		this.adaptee = adaptee;
	}
	
	public void request() {
		// 这里是使用委托的方式完成特殊功能
		this.adaptee.specificRequest();
	}
}
 
// 测试类
public class Client {
	public static void main(String[] args) {
		// 使用普通功能类
		Target concreteTarget = new ConcreteTarget();
		concreteTarget.request();
		
		// 使用特殊功能类，即适配类，
		// 需要先创建一个被适配类的对象作为参数
		Target adapter = new Adapter(new Adaptee());
		adapter.request();
	}
}
```

测试结果与上面的一致。从类图中我们也知道需要修改的只不过就是 Adapter 类的内部结构，即 Adapter 自身必须先拥有一个被适配类的对象，再把具体的特殊功能委托给这个对象来实现。使用对象适配器模式，可以使得 Adapter 类（适配类）根据传入的 Adaptee 对象达到适配多个不同被适配类的功能，当然，此时我们可以为多个被适配类提取出一个接口或抽象类。这样看起来的话，似乎对象适配器模式更加灵活一点。

**3、接口的适配器模式：**

有时我们写的一个接口中有多个抽象方法，当我们写该接口的实现类时，必须实现该接口的所有方法，这明显有时比较浪费，因为并不是所有的方法都是我们需要的，有时只需要某一些，此处为了解决这个问题，我们引入了接口的适配器模式，借助于一个抽象类，该抽象类实现了该接口，实现了所有的方法，而我们不和原始的接口打交道，只和该抽象类取得联系，所以我们写一个类，继承该抽象类，重写我们需要的方法就行。看一下类图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/23/20181101225043189.png)

这个很好理解，在实际开发中，我们也常会遇到这种接口中定义了太多的方法，以致于有时我们在一些实现类中并不是都需要。看代码：

```
public interface Sourceable {
	
	public void method1();
	public void method2();
}
```

抽象类Wrapper2：

```
public abstract class Wrapper2 implements Sourceable{
	
	public void method1(){}
	public void method2(){}
}
 
public class SourceSub1 extends Wrapper2 {
	public void method1(){
		System.out.println("the sourceable interface's first Sub1!");
	}
}
 
public class SourceSub2 extends Wrapper2 {
	public void method1(){
		System.out.println("the sourceable interface's second Sub2!");
	}
}
```

```
public class WrapperTest {
 
	public static void main(String[] args) {
		Sourceable source1 = new SourceSub1();
		Sourceable source2 = new SourceSub2();
		
		source1.method1();
		source1.method2();
		source2.method1();
		source2.method2();
	}
}
```

运行结果：

```vbnet
the sourceable interface's first Sub1!
the sourceable interface's second Sub2!
```