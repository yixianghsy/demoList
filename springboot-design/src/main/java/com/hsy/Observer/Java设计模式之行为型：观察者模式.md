# Java设计模式之行为型：观察者模式

#### 一、什么是[观察者模式](https://so.csdn.net/so/search?q=观察者模式&spm=1001.2101.3001.7020)：

​    观察者模式又称为 发布-订阅模式，定义了对象之间一对多依赖关系，当目标对象(被观察者)的状态发生改变时，它的所有依赖者(观察者)都会收到通知。一个观察目标可以对应多个观察者，而这些观察者之间没有相互联系，所以能够根据需要增加和删除观察者，使得系统更易于扩展，符合[开闭原则](https://so.csdn.net/so/search?q=开闭原则&spm=1001.2101.3001.7020)；并且观察者模式让目标对象和观察者松耦合，虽然彼此不清楚对方的细节，但依然可以交互，目标对象只知道一个具体的观察者列表，但并不认识任何一个具体的观察者，它只知道他们都有一个共同的接口。

​    但观察者模式的缺点在于如果存在很多个被观察者的话，那么将需要花费一定时间通知所有的观察者，如果观察者与被观察者之间存在循环依赖的话，那么可能导致系统崩溃，并且观察者模式没有相应的机制让观察者知道被观察对象是怎么发生变化的，而仅仅只是知道观察目标发生了变化。



#### 二、UML 结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102225153636.jpg)

Subject：抽象主题（被观察者），每一个主题可以有多个观察者，并将所有观察者对象的引用保存在一个集合里，被观察者提供一个接口，可以增加和删除观察者角色

ConcreteSubject：具体主题，将有关状态存入具体观察者对象，在主题发生改变时，给所有的观察者发出通知

Observer：抽象观察者，为所有的具体观察者定义一个更新接口，该接口的作用是在收到主题的通知时能够及时的更新自己

ConcreteObserver：具体观察者，实现抽象观察者角色定义的更新接口，以便使本身的状态与主题状态相协调。如果需要，具体观察者角色可以保存一个指向具体主题角色的引用。



#### 三、代码实现：

情景：在气象观测站中，它能够追踪目前的天气状况，包括温度、适度、气压。需要实现一个布告板，能够分别显示目前的状态，气象统计和简单的预报。当气象站中获取最新的测量数据时，三种布告板必须实时更新，下面是这个案例的设计图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102234128760.jpg)

主题接口  Subject.java

```java
public interface Subject {
	/**
	 * 注册观察者
	 * @param observer
	 */
	public void registerObserver(Observer observer);
	
	/**
	 * 删除观察者
	 * @param observer
	 */
	public void removeOberver(Observer observer);
	
	/**
	 * 当主题状态发生改变时，这个方法需要被调用，以通知所有观察者
	 */
	public void notifyObserver();
}
```

观察者接口  Observer.java

```java
public interface Observer {
	public void update(float temp,float humidity,float pressure);
}
```

布告板显示接口 DisplayElement.java

```java
public interface DisplayElement {
	public void display();
}
```

WeatherData实现主题接口 WeatherData.java

```java
public class WeatherData implements Subject{
	private List<Observer> observers;
	private float tempterature;
	private float pressure;
	private float humidity;
	
	public WeatherData(){
		observers = new ArrayList<Observer>();
	}
	
	@Override
	public void notifyObserver() {
		for(int i = 0; i < observers.size();i++){
			Observer observer = observers.get(i);
			observer.update(tempterature, humidity, pressure);
		}
	}
 
	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}
 
	@Override
	public void removeOberver(Observer observer) {
		int i = observers.indexOf(observer);
		if(i >= 0){
			observers.remove(i);
		}
	}
 
	/**
	 * 气象站得到更新的观测数据时，通知观察者
	 */
	public void measurementChanged(){
		notifyObserver();
	}
	
	public void setMeasurements(float temperature,float humidity,float pressure){
		this.tempterature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		measurementChanged();
	}
}
```

布告板  CurrentCondituonDisplay.java

```java
public class CurrentConditionsDisplay implements Observer,DisplayElement{
	private float temperature;
	private float humidity;
	private	Subject weatherData;
	
	public CurrentConditionsDisplay(Subject weatherData){
		this.weatherData = weatherData;
		weatherData.registerObserver(this);      //注册观察者
	}
	
	public void update(float temp, float humidity, float pressure) {
		this.temperature = temp;
		this.humidity = humidity;
		display();
	}
 
	@Override
	public void display() {
		System.out.println("Current conditions:"+temperature+"F degrees and "+humidity+"% humidity");
	}
 
}
```

测试程序  WeatherStation

```java
public class WeatherStation {
 
	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
		
		CurrentConditionsDisplay conditionsDisplay = new CurrentConditionsDisplay(weatherData);
	
		weatherData.setMeasurements(80, 65, 30.4f);
		weatherData.setMeasurements(82, 70, 29.2f);
		weatherData.setMeasurements(78, 78, 40.4f);
	}
}
```

运行结果：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102234715308.jpg)



#### 四、观察者模式的两种模式：

（1）拉取模式：目标角色在发生变化后，仅仅告诉观察者角色“我变化了”，观察者角色如果想要知道具体的变化细节，则就要自己从目标角色的接口中得到，这种模式称为拉取模式，就是说变化的信息是观察者角色主动从目标角中“拉”出来的。

（2）推送模式：目标角色发生变化时，通知观察者的同时，通过参数将变化的细节传递到观察者角色中去。

​    这两种模式的使用取决于系统设计，如果目标角色比较复杂，并且观察者角色进行更新时必须得到一些具体变化的信息，则“推模式”比较合适，如果目标角色比较简单，则“拉模式”就很合适啦。