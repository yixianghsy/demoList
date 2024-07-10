# Java设计模式之行为型：访问者模式

**背景：**

​    去医院看病时，医生会给你一个处方单要你去拿药，拿药我们可以分为两步走：

- （1）去柜台交钱，划价人员会根据处方单上的药进行划价，交钱。
- （2）去药房拿药，药房工作者同样根据处方单给你相对应的药。

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181105000022854.jpg)

​    这里我们就划价和拿药两个步骤进行讨论，这里有三个类，处方单（药）、划价人员、药房工作者。同时划价人员和药房工作者都各自有一个动作：划价、拿药。这里进行最初步的设计如下：

划价人员：

```
public class Charge {
    public void action(){
        public void action(){
            if("A药".equals(medicine)){
                //A的价格
            }
            if("B药".equals(medicine)){
                //B的价格
            }
            if("C药".equals(medicine)){
                //C的价格
            }
            if("D药".equals(medicine)){
                //D的价格
            }
            if("E药".equals(medicine)){
                //E的价格
            }
            ............
        }
    }
}
```

药房工作者：

```
public class WorkerOfPharmacy {
    public void action(){
        if("A药".equals(medicine)){
            //给你A药
        }
        if("B药".equals(medicine)){
            //给你B药
        }
        if("C药".equals(medicine)){
            //给你C药
        }
        if("D药".equals(medicine)){
            //给你D药
        }
        if("E药".equals(medicine)){
            //给你E药
        }
        ............
    }
}
```

​    这样的代码写法，在药品种类少的情况没什么问题，但也存在这么多的 if…else，而且我们可以想象医院里的药是那么多，而且随时都会增加的，增加了药就要改变划价人员和药房工作者的代码，这是我们最不希望改变的。那么有没有办法来解决呢？有，[访问者模式](https://so.csdn.net/so/search?q=访问者模式&spm=1001.2101.3001.7020)提供一中比较好的解决方案。

​    在实际开发过程中，我们对同个对象可能存在不同的操作方式，如处方单，划价人员要根据它来划价，药房工作者要根据它来给药。而且可能会随时增加新的操作，如医院增加新的药物，但是这里有两个元素是保持不变的，或者说很少变：划价人员和药房工作中，变的只不过是他们的操作。所以我们想如果能够将他们的操作抽象化就好了，这里访问者模式就是一个值得考虑的解决方案了。



#### 一、什么是访问者模式：

​    访问者模式适用于数据结构相对稳定的系统，将数据结构与基于数据的操作进行分离，使得添加作用于这些数据结构的新操作变得简单，并且不需要改变各数据结构，为不同类型的数据结构提供多种访问操作方式，这样是访问者模式的设计动机。

​    除了使新增访问操作变得更加简单，也能够在不修改现有类的层次结构下，定义该类层次结构的操作，并将有关元素对象的访问行为集中到一个访问者对象中，而不是分散搞一个个的元素类中。

​    但访问者模式的缺点在于让增加新的元素类变得困难，每增加一个新的元素类都意味着要在抽象访问者角色中增加一个新的抽象操作，并在每一个具体访问者类中增加相应的具体操作，违背了“开闭原则”的要求；

​    所以访问者模式适用于对象结构中很少改变，但经常需要在此对象结构上定义新的操作的系统，使得算法操作的增加变得简单；或者需要对一个对象结构中进行很多不同并且不相关的操作，并且需要避免让这些操作污染这些对象，也不希望在增加新操作时修改这些类的场景



#### 二、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181105001651888.jpg)

> - Vistor：抽象访问者，声明了对 ConcreteElement 类的一些操作 
> - ConcreteVisitor：具体访问者，实现抽象访问者中声明的每一个操作
> - Element：抽象元素，定义一个 accept 操作，用于接收具体访问者 
> - ConcreteElement：具体元素 ，实现 accept 操作。 
> - ObjectStructure：对象结构，提供一个高层接口来允许访问者枚举它的元素

​    从上面的 UML结构图中我们可以看出，访问者模式主要分为两个层次结构，一个是访问者层次结构，提供了抽象访问者和具体访问者，主要用于声明一些操作；一个是元素层次结构，提供了抽象元素和具体元素，主要用于声明 accept 操作；而对象结构作为两者的桥梁，存储了不同类型的对象，以便不同的访问者来访问，相同访问者可以以不同的方式访问不同的元素，所以在访问者模式中增加新的访问者无需修改现有代码，可扩展行强。

​    在访问者模式使用了双分派技术，所谓双分派技术就是在选择方法的时候，不仅仅要根据消息接收者的运行时区别，还要根据参数的运行时区别。在访问者模式中，客户端将具体状态当做参数传递给具体访问者，这里完成第一次分派，然后具体访问者作为参数的“具体状态”中的方法，同时也将自己this作为参数传递进去，这里就完成了第二次分派。双分派意味着得到的执行操作决定于请求的种类和接受者的类型。



#### 二、模式的实现：

​    以上面在医院付费、取药为实例。在这个实例中划价员和药房工作者作为访问者，药品作为访问元素、处方单作为对象结构，所以整个UML结构图如下：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181105002100446.jpg)

抽象访问者：Visitor.java

```
public abstract class Visitor {
    protected String name;
 
    public void setName(String name) {
        this.name = name;
    }
    
    public abstract void visitor(MedicineA a);
    
    public abstract void visitor(MedicineB b);
}
```

具体访问者：划价员、Charger.java

```
public class Charger extends Visitor{
 
    public void visitor(MedicineA a) {
        System.out.println("划价员：" + name +"给药" + a.getName() +"划价:" + a.getPrice());
    }
 
    public void visitor(MedicineB b) {
        System.out.println("划价员：" + name +"给药" + b.getName() +"划价:" + b.getPrice());
    }
}
```

具体访问者：药房工作者、WorkerOfPharmacy.java

```
public class WorkerOfPharmacy extends Visitor{
 
    public void visitor(MedicineA a) {
        System.out.println("药房工作者：" + name + "拿药 ：" + a.getName());
    }
 
    public void visitor(MedicineB b) {
        System.out.println("药房工作者：" + name + "拿药 ：" + b.getName());
    }
}
```

抽象元素：Medicine.java

```
public abstract class Medicine {
    protected String name;
    protected double price;
 
    public Medicine (String name,double price){
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public abstract void accept(Visitor visitor);
}
```

具体元素：MedicineA.java

```
public class MedicineA extends Medicine{
 
    public MedicineA(String name, double price) {
        super(name, price);
    }
 
    public void accept(Visitor visitor) {
        visitor.visitor(this);
    }
}
```

具体元素：MedicineB.java

```
public class MedicineB extends Medicine{
 
    public MedicineB(String name, double price) {
        super(name, price);
    }
 
    public void accept(Visitor visitor) {
        visitor.visitor(this);
    }
}
```

药单：Presciption.java

```
public class Presciption {
    List<Medicine> list = new ArrayList<Medicine>();
    
    public void accept(Visitor visitor){
        Iterator<Medicine> iterator = list.iterator();
        
        while (iterator.hasNext()) {
            iterator.next().accept(visitor);
        }
    }
    
    public void addMedicine(Medicine medicine){
        list.add(medicine);
    }
    
    public void removeMedicien(Medicine medicine){
        list.remove(medicine);
    }
}
```

客户端：Client.java

```
public class Client {
    public static void main(String[] args) {
        Medicine a = new MedicineA("板蓝根", 11.0);
        Medicine b = new MedicineB("感康", 14.3);
        
        Presciption presciption = new Presciption();
        presciption.addMedicine(a);
        presciption.addMedicine(b);
        
        Visitor charger = new Charger();
        charger.setName("张三");
        
        Visitor workerOfPharmacy = new WorkerOfPharmacy();
        workerOfPharmacy.setName("李四");
        
        presciption.accept(charger);
        System.out.println("-------------------------------------");
        presciption.accept(workerOfPharmacy);
    }
}
```

运行结果：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/25/20181105002431161.png)