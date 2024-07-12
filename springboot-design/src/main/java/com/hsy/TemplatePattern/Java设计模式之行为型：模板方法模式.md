# 


#### 一、什么是模板方法模式：

​    模板方法是基于继承实现的，在抽象父类中声明一个模板方法，并在模板方法中定义算法的执行步骤（即算法骨架）。在模板方法模式中，可以将子类共性的部分放在父类中实现，而特性的部分延迟到子类中实现，只需将特性部分在父类中声明成抽象方法即可，使得子类可以在不改变算法结构的情况下，重新定义算法中的某些步骤，不同的子类可以以不同的方式来实现这些逻辑。

​    模板方法模式的优点在于符合“开闭原则”，也能够实现代码复用，将不变的行为转移到父类，去除子类中的重复代码。但是缺点是不同的实现都需要定义一个子类，导致类的个数的增加使得系统更加庞大，设计更加抽象。

​    模板方法是一个方法，那么他与普通方法有什么不同呢？模板方法是定义在抽象类中，把基本操作方法组合在一起形成一个总算法或者一组步骤的方法。而普通的方法是实现各个步骤的方法，我们可以认为普通方法是模板方法的一个组成部分。

UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181102221756618.jpg)

> - 抽象类（AbstractClass）：实现了模板方法，定义了算法的骨架。
> - 具体类（ConcreteClass)：实现抽象类中的抽象方法，已完成完整的算法。



#### 二、模式实现：

举个例子，以准备去学校所要做的工作（prepareGotoSchool）为例，假设需要分三步：穿衣服（dressUp），吃早饭（eatBreakfast），带上东西（takeThings）。学生和老师要做得具体事情肯定有所区别。

抽象类AbstractClass：

```java
//抽象类定义整个流程骨架
public abstract class AbstractPerson{
     //模板方法，使用final修改，防止子类改变算法的实现步骤
     public final void prepareGotoSchool(){
          dressUp();
          eatBreakfast();
          takeThings();
     }
     //以下是不同子类根据自身特性完成的具体步骤
     protected abstract void dressUp();
     protected abstract void eatBreakfast();
     protected abstract void takeThings();
}
```

具体类ConcreteClass：

```
public class Student extends AbstractPerson{
     @Override
     protected void dressUp() {
          System.out.println(“穿校服");
     }
     @Override
     protected void eatBreakfast() {
          System.out.println(“吃妈妈做好的早饭");
     }
 
     @Override
     protected void takeThings() {
          System.out.println(“背书包，带上家庭作业和红领巾");
     }
}
```





```
public class Teacher extends AbstractPerson{
     @Override
     protected void dressUp() {
          System.out.println(“穿工作服");
     }
     @Override
     protected void eatBreakfast() {
          System.out.println(“做早饭，照顾孩子吃早饭");
     }
 
     @Override
     protected void takeThings() {
          System.out.println(“带上昨晚准备的考卷");
     }
}
```



```
public class Client {
     public static void main(String[] args) {
     Student student = new Student()
     student.prepareGotoSchool();
 
     Teacher teacher  = new Teacher()
     teacher.prepareGotoSchool();
     }
}
```



1、模板模式优点

①、封装不变部分， 扩展可变部分

把认为是不变部分的算法封装到父类实现， 而可变部分的则可以通过继承来继续扩展。

②、提取公共部分代码， 便于维护

③、行为由父类控制， 子类实现

基本方法是由子类实现的， 因此子类可以通过扩展的方式增加相应的功能， 符合开闭原则。

2、模板模式缺点
①、子类执行的结果影响了父类的结果，这和我们平时设计习惯颠倒了，在复杂项目中，会带来阅读上的难度。

②、可能引起子类泛滥和为了继承而继承的问题

3、回调
为了解决模板模式的缺点，我们可以利用回调函数代替子类继承。