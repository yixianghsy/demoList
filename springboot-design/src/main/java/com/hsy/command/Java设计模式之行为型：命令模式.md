# Java设计模式之行为型：命令模式

**前言：**

​    在开发中，我们可能需要向某些对象发送一些请求，但我们不知道请求的具体接收者是谁，也不知道被请求的操作是哪个，只知道在系统运行中指定具体的请求接收者即可，打个比方，电视遥控器，我们只需知道按哪个按钮能够打开电视、关闭电视和换台即可，并不需要知道是怎么开电视、关电视和换台的，对于这种情况，我们可以采用命令模式来进行设计。



#### 一、定义：

​    命令模式的本质是将请求封装成对象，将发出命令与执行命令的责任分开，命令的发送者和接收者完全解耦，发送者只需知道如何发送命令，不需要关心命令是如何实现的，甚至是否执行成功都不需要理会。命令模式的关键在于引入了抽象命令接口，发送者针对抽象命令接口编程，只有实现了抽象命令接口的具体命令才能与接收者相关联。另外命令可以像强对象一样可以被存储和传递，所以可支持撤销的操作

​    使用命令模式的优势在于降低了系统的耦合度，而且新命令可以很方便添加到系统中，也容易设计一个组合命令。但缺点在于会导致某些系统有过多的具体命令类，因为针对每一个命令都需要设计一个具体命令类。所以命令模式适用于以下场景：

- （1）需要将请求调用者和请求接收者解耦，使得调用者和接收者不直接交互。
- （2）系统需要在不同的时间指定请求、将请求排队和执行请求。
- （3）系统需要支持命令的撤销(Undo)操作和恢复(Redo)操作。
- （4）系统需要将一组操作组合在一起，即支持宏命令。



#### 二、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/24/20181104094349265.jpg)

> - Receiver：接收者，执行命令的对象，任何类都可能成为一个接收者，只要它能够实现命令要求实现的相应功能。
> - Command：抽象命令类，声明需要执行的方法。
> - ConcreteCommand：具体命令类，通常会持有接收者，并调用接收者的功能完成命令要执行的操作。
> - Invoker：调用者，通常会持有命令对象，可以持有多个命令对象，是客户端真正触发命令并要求命令执行相应操作的地方，就是相当于使用命令对象的入口。
> - Client：客户类，创建具体的命令对象，并且设置命令对象的接收者。注意这里不是指常规意义上的客户端，把这个 Client 称为装配者会合适，主要用于组装命令对象和接收者。



**三、代码实现：**

​    这里以电视机为例。电视是请求的接受者，遥控器是请求的发送者，遥控器上有一些按钮，不同的按钮对应着不同的操作。在这里遥控器需要执行三个命令：打开电视机、关闭电视机、换台。UML类图如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/24/2018110409533995.jpg)

抽象命令类：Command.java

```java
/**
 * Command命令接口，为所有的命令声明一个接口。所有的命令都应该实现它
 */
public interface Command {
    public void execute();
}
```

电视机类：Television.java

```java
public class Television {
	public void open(){
		System.out.println("打开电视机......");
	}
	
	public void close(){
		System.out.println("关闭电视机......");		
	}
	
	public void changeChannel(){
		
		System.out.println("切换电视频道......");
	}
}
```

遥控器类：Controller.java

```java
public class Controller {
	private Command openTVCommand;
	private Command closeTVCommand;
	private Command changeChannelCommand;
	
	public Controller(Command openTvCommand,Command closeTvCommand,Command changeChannelCommand){
		this.openTVCommand = openTvCommand;
		this.closeTVCommand = closeTvCommand;
		this.changeChannelCommand = changeChannelCommand;
	}
	
	/**
	 * 打开电视剧
	 */
	public void open(){
		openTVCommand.execute();
	}
	
	/**
	 * 关闭电视机
	 */
	public void close(){
		closeTVCommand.execute();
	}
	
	/**
	 * 换频道
	 */
	public void change(){
		changeChannelCommand.execute();
	}
}
```

遥控器的三个按钮：

```java
public class OpenTvCommand implements Command{
	private Television tv;
	
	public OpenTvCommand(Television tv){
		this.tv = tv;
	}
	
	public void execute() {
		tv.open();
	}
}
```

客户端：Client.java

```java
public class OpenTvCommand implements Command{
	private Television tv;
	
	public OpenTvCommand(Television tv){
		this.tv = tv;
	}
	
	public void execute() {
		tv.open();
	}
}
```

```
public class ChangeChannelCommand implements Command{
	private Television tv;
	
	public ChangeChannelCommand(Television tv){
		this.tv = tv;
	}
	
	public void execute() {
		tv.changeChannel();
	}
}
```

```
public class CloseTvCommand implements Command{
	private Television tv;
	
	public CloseTvCommand(Television tv){
		this.tv = tv;
	}
	
	public void execute() {
		tv.close();
	}
}
```



运行结果：

```erlang
打开电视机......
切换电视机频道......
关闭电视机......
```

命令模式扩展：前面说过，命令模式支持撤销命令：在电视遥控器中，我们还有这样一个按钮，那就是返回。用于切换到上面一个频道中去。在命令模式中也支持撤销操作，在这里我们只需要记录上一个频道，然后将上一个频道传入即可。在这里将 Command 进行一个简单的修改：将 execute() 改为 execute(int i); i表示频道，用于进行频道切换。

```java
/**
 * Command命令接口，为所有的命令声明一个接口。所有的命令都应该实现它
 */
public interface Command {
	/**
	 * 为了方便切换频道，这里使用参数i将频道传递
	 * @param i
	 */
	public void execute(int i);
}
```

然后在Controller中添加channelUndo()方法，用于进行频道返回。并且需要进行一些简单的修改。

```java
public class Controller {
	private Command openTVCommand;
	private Command closeTVCommand;
	private Command changeChannelCommand;
	
	public int nowChannel = 0;       //当前频道
	public int priorChannel;     //前一个频道，用于执行返回操作
	
	public Controller(Command openTvCommand,Command closeTvCommand,Command changeChannelCommand){
		this.openTVCommand = openTvCommand;
		this.closeTVCommand = closeTvCommand;
		this.changeChannelCommand = changeChannelCommand;
	}
	
	/**
	 * 打开电视剧
	 */
	public void open(){
		openTVCommand.execute(0);
	}
	
	/**
	 * 关闭电视机
	 */
	public void close(){
		closeTVCommand.execute(0);
	}
	
	/**
	 * 换频道：只在当前频道递增
	 */
	public void change(){
		priorChannel = nowChannel;            //换频道前记录当前频道
		nowChannel++;       //频道+1
		changeChannelCommand.execute(nowChannel);
	}
	
	/**
	 * 频道返回
	 */
	public void ChannelUndo(){
		changeChannelCommand.execute(priorChannel);          //将以前的频道传入
		//当前频道与前一个频道进行互换
		int tempChannel;
		tempChannel = priorChannel;
		priorChannel = nowChannel;
		nowChannel = tempChannel;
	}
}
```

客户端：

```java
public class Client {
    public static void main(String a[])
    {
        Television tv = new Television();
        Command openCommand,closeCommand,changeCommand;

        openCommand = new OpenTvCommand(tv);
        closeCommand = new CloseTvCommand(tv);
        changeCommand = new ChangeChannelCommand(tv);

        Controller control = new Controller(openCommand,closeCommand,changeCommand);

        control.open();           //打开电视机
        control.change();         //换频道
        control.change();
        control.ChannelUndo();
        control.ChannelUndo();
        control.ChannelUndo();
        control.close();          //关闭电视机
    }
}
```

运行结果：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/24/20181104100932434.png)