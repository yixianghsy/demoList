# java设计模式（六）--观察者模式

目录：

1. 简单目标任务实现
2. 观察者模式介绍
3. 观察者模式代码实现

观察者模式是**JDK中使用最多的模式之一**，非常有用。我们也会一并介绍一对多关系，以及**松耦合**（对，没错,我们说耦合）。有了观察者，你将会消息灵通。

文章首先从一个案例入手开始介绍。这个案例是这样的，团队承包了一个气象站的气象发布系统。

```
工作合约
恭喜贵公司获选为敝公司建立下一代Internet气象观测站！
该气象站必须建立在我们专利申请中的W eatherD ata对象
上，由W eatherD ata对象负责追踪目前的天气状况（温度、
湿度、气压）。我们希望贵公司能建立一个应用，有三种
布告板，分别显示目前的状况、气象统计及简单的预报。
当WeatherObject对象获得最新的测量数据时，三种布告板
必须实时更新。
而且，这是一个可以扩展的气象站，Weather-O-Rama气象
站希望公布一组API，好让其他开发人员可以写出自己的
气象布告板，并插入此应用中。我们希望贵公司能提供这
样的API。
Weathe r - O-Ra m a 气象站有很好的商业营运模式：一旦客
户上钩，他们使用每个布告板都要付钱。最好的部分就是，
为了感谢贵公司建立此系统，我们将以公司的认股权支付
你。
我们期待看到你的设计和应用的alpha版本。
真挚的
Johnny Hurricane——Weather-O-Rama气象站执行长
附注：我们正通宵整理WeatherData源文件给你们。
```

此系统中的三个部分是气象站（获取实际气象数据的物理装置）、WeatherData对象（追踪来自气象站的数据，并更新布告板）和布告板（显示目前天气状况给用户看）。如下图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/07/19/686418-20160301171952361-1593410395.jpg)

## 1.简单的实现

再次理清思路：WeatherData对象可以获取天气信息（温度，湿度，气压等），然后将这些天气信息发送给布告板。那么我们可以很简单的实现：

```java
public class WeatherData {
// 实例变量声明
public void measurementsChanged() {
float temp = getTemperature();
float humidity = getHumidity();
float pressure = getPressure();
currentConditionsDisplay.update(temp, humidity, pressure);
statisticsDisplay.update(temp, humidity, pressure);
forecastDisplay.update(temp, humidity, pressure);
}
// 这里是其他WeatherData方法
}
```

很直观，很容易理解，currentConditionsDisplay等代表三块布告板，通过measurementsChanged方法告诉三个布告板天气信息。然而，有以下几个问题：

A. 我们是针对具体实现编程，而非针对接
B. 对于每个新的布告板，我们都得修改代码。
C. 我们无法在运行时动态地增加（或删除）布告板
D. 布告板没有实现一个共同的接口。
E. 我们尚未封装改变的部分。
F. 我们侵犯了WeatherData类的封装。

## 2.观察者模式

简单的认识下观察者模式。比如报纸，客户订阅报纸，然后报社发送报纸到订阅的用户手里。报社就是一个主题，而订阅的客户就是观察者。

**定义：**

观察者模式定义了对象之间的一对多依赖，这样一来，当一个对象改变状态时，它的所有依赖者都会收到通知并自动更新。

主题和观察者定义了一对多的关系。观察者依赖于此主题，只要主题状态一有变化，观察者就会被通知。根据通知的风格，观察者可能因此而更新。稍后你会看到，实现观察者模式的方法不只一种，但是以包含Subject与Observer接口的类设计的做法最常见。

那么，气象数据更新便可以这样设计为观察者模式：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/07/19/686418-20160301164241408-1921263080.jpg)

利用观察者模式，主题是具有状态的对象，并且可以控制这些状态。也就是说，有“一个”具有状态的主题。另一方面，观察者使用这些状态，虽然这些状态并不属于他们。有许多的观察者，依赖主题来告诉他们状态何时改变了。这就产生一个关系：“一个”主题对“多个”观察者的关系。

从结果往前看很容易理解观察者模式，但如果接到了需求怎么适配到观察者模式呢？首先，明显的是一对多的订阅模式，主题天气更新后，观察者们布告板因此而更新天气。需要考虑的是布告板是不同的，风格不同，但主题只有一个或者说主题的通知方式只有一个，怎样才能使布告板统一接受规则呢？那就是接口，只要所有的布告板实现了接口的update方法，通过update方法便可以获取天气信息。由此可以获得最终设计：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/07/19/686418-20160301165422845-1095109112.jpg)

刚开始看书看到这些类图可能有点头晕，别担心，那只是因为对内容还不熟悉，只要跟着敲一遍代码，一切就很容易理解了。下面就是代码实现了。

## 3.代码实现

### 3.1Subject接口

```java
/**
 * 主题：天气变化管理接口
 * Created by mrf on 2016/3/1.
 */
public interface Subject {
    //注册观察者
    public void registerObserver(Observer o);
    //移除观察者
    public void removeObserver(Observer o);
    //当天气改变时，这个方法会被调用，以通知所有的观察者
    public void notifyObservers();
}
```

### 3.2Observer接口

同样，观察者有多个，这些观察者各不相同，但都要以相同的方式接收天气信息，那么就必须实现一个观察者的接口：

```
/**
 * 观察者接口
 * Created by mrf on 2016/3/1.
 */
public interface Observer {
    /**
     * 天气更新通知
     * @param temp       气象观测值：温度
     * @param humidity 气象观测值：湿度
     * @param pressure  气象观测值：压强
     */
    public void update(float temp, float humidity, float pressure);
}
```

### 3.3Diplay接口

根据得到的信息展示可以统一成一个方式，即实现Display的显示方法，当然，各自也可以自己添加自己的方法。

```java
/**
 * 显示信息接口：当布告板需要显示时调用此方法
 * Created by mrf on 2016/3/1.
 */
public interface DisplayElement {
    public void display();
}
```

### 3.4WeatherData主题

接下来就是天气主题，天气主题需要实现Subject接口以统一信息管理标准

```java
/**
 * 观察者模式--天气更新
 * 角色：主题发布者
 * Created by mrf on 2016/3/1.
 */
public class WeatherData implements Subject {
    private ArrayList observers;
    private float temperature;
    private float humidity;
    private float pressure;
 
    public WeatherData() {
        observers = new ArrayList();
    }
 
    public void registerObserver(Observer o) {
        observers.add(o);
    }
 
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }
 
    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer) observers.get(i);
            observer.update(temperature, humidity, pressure);
        }
    }
 
    public void measurementsChanged() {
        notifyObservers();
    }
 
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
// WeatherData的其他方法
}
```

### 3.5一个布告板的实现CurrentConditionsDisplay

那么，最重要的观察者来了。布告板实现了接受标准和显示标准两个接口：

```java
/**
 * 显示天气信息的布告板
 * 角色:观察者，订阅者
 * 实现Observer接口以从WeatherData中获取天气信息
 * 实现DisplayElement以显示信息
 * Created by mrf on 2016/3/1.
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float temperature;
    private float humidity;
    private Subject weatherData;
    public CurrentConditionsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }
    public void display() {
        System.out.println("Current conditions: " + temperature
                + "F degrees and " + humidity + "% humidity");
    }
}
```

### 3.6测试

万事俱备，就来测试一下结果

```java
/**
 * 观察者模式测试
 * Created by mrf on 2016/3/1.
 */
public class WeatherStationTest {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentDisplay =
                new CurrentConditionsDisplay(weatherData);
        //其他布告板
//        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
//        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }
}
```

**结果：**

```java
Current conditions: ``80``.0F degrees and ``65.0``% humidity
Current conditions: ``82``.0F degrees and ``70.0``% humidity
Current conditions: ``78``.0F degrees and ``90.0``% humidity
```

## 4.松耦合的威力

> 设计原则：**为了交互对象之间的松耦合设计而努力。**
> 当两个对象之间松耦合，它们依然可以交互，但是不太清楚彼此的细节。观察者模式提供了一种对象设计，让主题和观察者之间松耦合。为什么呢？
> 关于观察者的一切，主题只知道观察者实现了某个接口（也就是Observer接口）。主题不需要知道观察者的具体类是谁、做了些什么或其他任何细节。任何时候我们都可以增加新的观察者。因为主题唯一依赖的东西是一个实现Observer接口的对象列表，所以我们可以随时增加观察者。事实上，在运行时我们可以用新的观察者取代现有的观察者，主题不会受到任何影响。同样的，也可以在任何时候删除某些观察者。
>
> 有新类型的观察者出现时，主题的代码不需要修改。假如我们有个新的具体类需要当观察者，我们不需要为了兼容新类型而修改主题的代码，所有要做的就是在新的类里实现此观察者接口，然后注册为观察者即可。主题不在乎别的，它只会发送通知给所有实现了观察者接口的对象。
> 我们可以独立地复用主题或观察者。如果我们在其他地方需要使用主题或观察者，可以轻易地复用，因为二者并非紧耦合。改变主题或观察者其中一方，并不会影响另一方。因为两者是松耦合的，所以只要他们之间的接口仍被遵守，我们就可以自由地改变他们。
> 松耦合的设计之所以能让我们建立有弹性的OO系统，能够应对变化，是因为对象之间的互相依赖降到了最低

## 5.使用java内置的观察者模式

java.util下包含了Observer接口和Observable类，这和我们之前的Subject、Observer接口很类似。使用java内置的只要做些简单修改：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/07/19/686418-20160301183338126-841926974.jpg)

这里要注意，主题要继承内置实现的Observable类，观察者实现Observer接口，而且如果主题要通知观察者必须设置setChanged()来告诉系统需要通知，这一设置的意思是为了避免某些不想发送通知的情况。比如温都每0.1更新一次，但观察者不需要这么频繁，只需要到1度以上才更新等。控制通知很重要。这里的get方法是为了实现让观察者自己拉取数据。由此，数据可以通知，也可以拉取。下面看代码实现，注意导入的包的类型：

### 5.1WeatherData修改

```java
/**
 * 观察者模式
 * 角色：主题
 * 通过继承java内置的对象来实现
 * Created by mrf on 2016/3/1.
 */
public class WeatherData extends Observable {
    private float temperature;
    private float humidity;
    private float pressure;
    public WeatherData() { }
    public void measurementsChanged() {
        setChanged();//设置通知标识
        notifyObservers();//通知
    }
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
    public float getTemperature() {
        return temperature;
    }
    public float getHumidity() {
        return humidity;
    }
    public float getPressure() {
        return pressure;
    }
}
```

### 5.2接收通知的观察者

```java
/**
 * 观察者模式
 * 角色：观察者
 * 通过实现java内置的方法实现观察功能
 * Created by mrf on 2016/3/1.
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {
    Observable observable;
    private float temperature;
    private float humidity;
    public CurrentConditionsDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);//注册为观察者
    }
 
    /**
     * 更新
     * @param obs 主题
     * @param arg 这里没有用到
     */
    public void update(Observable obs, Object arg) {//通过主题对象来更新
        if (obs instanceof WeatherData) {
            WeatherData weatherData = (WeatherData)obs;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            display();
        }
    }
    public void display() {
        System.out.println("Current conditions: " + temperature
                + "F degrees and " + humidity + "% humidity");
    }
}
```

### 5.3自己拉取数据的观察者

```java
/**
 * Created by mrf on 2016/3/1.
 */
public class ForecastDisplay implements Observer, DisplayElement {
    Observable observable;
    private float lastPresure;
    private float currentPresure =12.5f;
 
    public ForecastDisplay(Observable observable) {
        this.observable =observable;
    }
 
    @Override
    public void display() {
        System.out.println("当前压强："+currentPresure+"; 上一次压强："+lastPresure);
    }
 
    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof WeatherData) {
            lastPresure = currentPresure;
            currentPresure = ((WeatherData) obs).getPressure();
            display();
        }
    }
}
```

### 5.4**测试**

```
/**
 ``* 测试拉取数据
 ``* Created by mrf on 2016/3/1.
 ``*/
public` `class` `ForecastDisplayTest {
  ``public` `static` `void` `main(String[] args) {
    ``WeatherData weatherData = ``new` `WeatherData();
    ``CurrentConditionsDisplay currentDisplay =
        ``new` `CurrentConditionsDisplay(weatherData);
    ``ForecastDisplay forecastDisplay = ``new` `ForecastDisplay(weatherData);
    ``weatherData.setMeasurements(``80``, ``65``, ``30``.4f);
    ``//拉取
    ``forecastDisplay.update(weatherData,forecastDisplay);
    ``currentDisplay.update(weatherData,forecastDisplay);
    ``weatherData.setMeasurements(``82``, ``70``, ``29``.2f);
    ``forecastDisplay.update(weatherData,forecastDisplay);
    ``weatherData.setMeasurements(``78``, ``90``, ``39``.2f);
    ``forecastDisplay.update(weatherData,forecastDisplay);
  ``}
}
```

结果：

```java
Current conditions: 80.0F degrees and 65.0% humidity
当前压强：30.4; 上一次压强：12.5
Current conditions: 80.0F degrees and 65.0% humidity
Current conditions: 82.0F degrees and 70.0% humidity
当前压强：29.2; 上一次压强：30.4
Current conditions: 78.0F degrees and 90.0% humidity
当前压强：39.2; 上一次压强：29.2
```

## 6.java.util.Observable的黑暗面

### **Observable是一个类**

是的，你注意到了！如同你所发现的，可观察者是一个“类”而不是一个“接口”，更糟的是，它甚至没有实现一个接口。不幸的是，java.util.Observable的实现有许多问题，限制了它的使用和复用。这并不是说它没有提供有用的功能，我们只
是想提醒大家注意一些事实。
你已经从我们的原则中得知这不是一件好事，但是，这到底会造成什么问题呢？首先，因为Observable是一个“类”，你必须设计一个类继承它。如果某类想同时具有Observable类和另一个超类的行为，就会陷入两难，毕竟Java不支持多重继承。这限制了Observable的复用潜力（而增加复用潜力不正是我们使用模式最原始的动机吗？）。再者，因为没有Observable接口，所以你无法建立自己的实现，和Java内置的Observer API搭配使用，也无法将java.util的实现换成另一套做法的实现（比方说，

### Observable将关键的方法保护起来

如果你能够扩展java.util.Observable，那么Observable“可能”可以符合你的需求。否则，你可能需要像本章开头的做法那样自己实现这一整套观察者模式。不管用哪一种方法，反正你都已经熟悉观察者模式了，应该都能善用它们。如果你看看Observable API，你会发现setChanged()方法被保护起来了（被定义成protected）。那又怎么样呢？这意味着：除非你继承自Observable，否则你无法创建Observable实例并组合到你自己的对象中来。这个设计**违反了第二个设计原则：“多用组合，少用继承”**