# 【玩转23种Java设计模式】结构型模式篇：适配器模式

**简介：** 适配器模式（Adapter Pattern）将某个类的接口转换成客户端期望的另一个接口表示，主的目的是兼容性，让原本因接口不匹配不能一起工作的两个类可以协同工作。适配器模式属于结构型模式，主要分为三类：类适配器模式、对象适配器模式、接口适配器模式。

> 软件设计模式（Design pattern），又称设计模式，是一套被反复使用、多数人知晓的、经过分类编目的、代码设计经验的总结。使用设计模式是为了可重用代码、让代码更容易被他人理解、保证代码可靠性、程序的重用性。
>
> 汇总目录链接：[【玩转23种Java设计模式】学习目录汇总整理](https://blog.csdn.net/sinat_27933301/article/details/104961805)

## 一、简介

  适配器模式（Adapter Pattern）将某个类的接口转换成客户端期望的另一个接口表示，主的目的是兼容性，让原本因接口不匹配不能一起工作的两个类可以协同工作。适配器模式属于结构型模式，主要分为三类：类适配器模式、对象适配器模式、接口适配器模式。

## 二、实例

例：把220V的交流电压转化为20V的直流电压，用于手机充电。
![在这里插入图片描述](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/26/8c0c0b933065416fac2edde8f38a39d6.png)

### 1、源电压类（SourceVoltage）

```
public class SourceVoltage {
    public int sourceMethod() {
        return 220;
    }
}
```

### 2、目标电压接口（TargetVoltage）

```
public interface TargetVoltage {
    int targetMethod();
}
```

### 3、电压适配类（VoltageAdapter）

```
public class VoltageAdapter extends SourceVoltage implements TargetVoltage {
    /**
     * 通过实现接口的方法将电压转化
     */
    @Override
    public int targetMethod() {
        // 获取到220V电压
        int volts = sourceMethod();
        System.out.println("适配前，输出电压 = " + volts + "V");

        // 将220V电压转成20V
        volts = volts / 11;
        System.out.println("适配后，转化电压 = " + volts + "V");
        return volts;
    }
}
```

### 4、测试类

```
public class Main {
    public static void main(String[] args) {
        VoltageAdapter voltageAdapter = new VoltageAdapter();
        voltageAdapter.targetMethod();
    }
}
```

控制台输出：

```
适配前，输出电压 = 220V
适配后，转化电压 = 20V
```

## 三、总结

### 1、优点

- 可以让任何两个没有关联的类一起运行。
- 提高了类的复用度。
- 增加了类的透明性。
- 灵活性非常好。想用就用，不想就卸载，其他代码不用修改。

### 2、缺点

- 由于 JAVA 不支持多重类继承，所以一次最多只能适配一个适配者类，而且目标类必须是抽象类。

### 3、应用场景

- 你有动机修改一个已经投产中的接口时，适配器模式可能是最适合你的模式。