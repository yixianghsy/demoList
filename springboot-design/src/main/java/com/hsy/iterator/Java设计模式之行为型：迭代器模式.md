# Java设计模式之行为型：迭代器模式

#### 一、什么是[迭代器](https://so.csdn.net/so/search?q=迭代器&spm=1001.2101.3001.7020)模式：

​    实际开发中，我们针对不同的需求，可能需要以不同的方式来遍历整个整合对象，但我们不希望在集合容器的抽象接口层中充斥着各种不同的遍历操作，这时候我们就需要一种能完成下面功能的迭代器：

- （1）遍历一个集合对象
- （2）不需要了解聚合对象的内部结构
- （3）提供多种不同的遍历方式

​    迭代器模式提供一种访问集合中的各个元素，而不暴露其内部表示的方法。将在元素之间游走的职责交给迭代器，而不是集合对象，从而简化集合容器的实现，让集合容器专注于在它所应该专注的事情上，更加符合单一职责原则。
​    

#### 二、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/24/20181103210450671.jpg)

- Iterator：抽象迭代器，提供了在集合容器元素之间游走的方法。
- ConcreteIterator：具体迭代器，能够对具体的集合容器进行遍历，每种集合容器都应该对应一个具体的迭代器
- Aggregate：抽象容器类
- ConcreteAggregate：具体容器类，实现 creatorIterator() 方法，返回该聚合对象的迭代器。



#### 三、代码实现：

你项目组接到一个项目：对电视机的电视频道、电影菜单进行统一管理，建立一个统一的菜单管理界面，能够看到所有的电视界面、电影界面。你有三个手下：小李子、小杏子，他们分别就每个模块做开发工作，看他们都做了哪些工作。项目的UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/24/20181103210810205.jpg)

首先我们定义主菜单JavaBean，用于显示每个模块的菜单：

```java
public class MenuItem {
    private String name;
    private String description;
    private int channe;
    
    public MenuItem(int channe,String name,String description){
        this.name = name;
        this.description = description;
        this.channe = channe;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public int getChanne() {
        return channe;
    }
 
    public void setChanne(int channe) {
        this.channe = channe;
    }
    
}
```

接下来我们需要定义迭代器接口：Iterator.java

```java
public interface Iterator {
    boolean hasNext();
    Object next();
}
```

然后是两个具体的迭代器，一个迭代器遍历电视界面、一个迭代器遍历电影界面：

```java
//电影节目的迭代器
public class FilmMenuIterator implements Iterator{
    MenuItem[] menuItems;
    int position = 0;
    
    public FilmMenuIterator(MenuItem[] menuItems){
        this.menuItems = menuItems;
    }
    
    public boolean hasNext() {
        if(position > menuItems.length-1 || menuItems[position] == null){
            return false;
        }
        return true;
    }
 
    public Object next() {
        MenuItem menuItem = menuItems[position];
        position ++;
        return menuItem;
    }
}



//电视界面的迭代器
public class TVChanneMenuIterator implements Iterator{
 
    List<MenuItem> menuItems;
    int position = 0;
    
    public TVChanneMenuIterator(List<MenuItem> menuItems){
        this.menuItems = menuItems;
    }
    
    public boolean hasNext() {
        if(position > menuItems.size()-1 || menuItems.get(position) == null){
            return false;
        }else{
            return true;
        }
    }
 
    public Object next() {
        MenuItem menuItem = menuItems.get(position);
        position ++;
        return menuItem;
    }
}
```

然后是菜单接口，该接口提供返回具体迭代器的方法：createIterator()。

```java
public interface TelevisionMenu {
    public void addItem(int channe,String name,String description);
    public Iterator createIrerator();
}
```

两个具体聚合类。这个两个聚合类实现createIterator()方法，分别返回电视界面的聚合类和电影界面的聚合类。

```java
public class FilmMenu implements TelevisionMenu{
    static final int MAX_ITEMS = 5;    //菜单最大长度
    MenuItem[] menuItems;
    int numberOfItems = 0;
    
    /**
     * 构造函数完成初始化
     */
    public FilmMenu(){
        menuItems = new MenuItem[MAX_ITEMS];
        
        addItem(1, "绝世天劫", "这是布鲁斯威利斯主演的...");
        addItem(2, "达芬奇密码", "这是我最喜欢的电影之一...");
        addItem(3, "黑客帝国123", "不知道你能够看懂不???");
        addItem(4, "我的女友是机器人", "一部我不反感的经典爱情电影...");
        addItem(5, "肖申克的救赎", "自由，幸福，离你有多远");
    }
    
    /**
     * @desc 将电影解决添加到菜单项中
     */
    public void addItem(int channe,String name,String description){
        MenuItem tvmenuiItem = new MenuItem(channe, name, description);
        //判断数组是否越界
        if(numberOfItems > MAX_ITEMS){
            System.out.println("不好意思，菜单满了....");
        }
        else{
            menuItems[numberOfItems] = tvmenuiItem;
            numberOfItems ++;
        }
    }
 
    public Iterator createIrerator() {
        return new FilmMenuIterator(menuItems);
    }
}
```

```
public class TVChanneMenu implements TelevisionMenu{
    List<MenuItem> menuItems;
    
    /**
     * 构造函数完成初始化
     */
    public TVChanneMenu(){
        menuItems = new ArrayList<MenuItem>();
        addItem(1, "CCTV-1", "This is CCTV-1");
        addItem(2, "CCTV-2", "This is CCTV-2");
        addItem(3, "CCTV-3", "This is CCTV-3");
        addItem(4, "CCTV-4", "This is CCTV-4");
        addItem(5, "CCTV-5", "This is CCTV-5");
    }
    
    /**
     * @desc 将电视频道节目添加菜单集合中
     */
    public void addItem(int channe,String name,String description){
        MenuItem tvMenuItem = new MenuItem(channe, name, description);
        menuItems.add(tvMenuItem);
    }
 
    public Iterator createIrerator() {
        return new TVChanneMenuIterator(menuItems);
    }
}
```

主菜单了：用来展示、遍历所有的电视、电影界面。

```java
public class MainMenu {
    TelevisionMenu tvMenu;
    FilmMenu filmMenu;
    
    public MainMenu(TelevisionMenu tvMenu,FilmMenu filmMenu){
        this.tvMenu = tvMenu;
        this.filmMenu  = filmMenu;
    }
    
    public void printMenu(){
        Iterator tvIterator = tvMenu.createIrerator();
        Iterator filmIterator = filmMenu.createIrerator();
        
        System.out.println("电视节目有:");
        printMenu(tvIterator);
        System.out.println("----------------------------------------------------------------");
        System.out.println("电影节目有:");
        printMenu(filmIterator);
    }
 
    private void printMenu(Iterator iterator) {
        while(iterator.hasNext()){
            MenuItem menuItem = (MenuItem) iterator.next();
            System.out.print("channe:"+menuItem.getChanne()+",  ");
            System.out.print("name:"+menuItem.getName()+",  ");
            System.out.println("description :"+menuItem.getDescription());
        }
    }
}
```

测试程序：

```java
public class Test {
    public static void main(String[] args) {
        TVChanneMenu tvMenu = new TVChanneMenu();
        FilmMenu filmMenu = new FilmMenu();
        
        MainMenu mainMenu = new MainMenu(tvMenu, filmMenu);
        mainMenu.printMenu();
    }
}
```

运行结果：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/24/20181103211218639.jpg)



#### 四、迭代器模式小结：

1、优点：

（1）迭代器模式简化了集合的接口，迭代子具备遍历功能，这样集合的接口就不需要重新实现遍历功能。

（2）每一个聚集对象都可以有一个或多个迭代子对象，每一个迭代子的迭代状态可以是彼此独立的。因此，一个聚集对象可以同时有几个迭代在进行之中。

（3）由于遍历算法被封装在迭代子角色里面，因此迭代的算法可以独立于聚集角色变化。

（4）更好的封装性，访问一个集合对象的元素，无需暴露容器内部表示。

2、缺点：

（1）迭代器模式将存储数据和遍历数据的职责分离，增加新的聚合类需要对应增加新的迭代器类，类的个数成对增加，这在一定程度上增加了系统的复杂性。

（2）对于比较简单的遍历，使用迭代器模式显得较为繁琐，比如ArrayList直接就可以用for循环+get() 方法来遍历；

（3）抽象迭代器的设计难度较大，需要充分考虑到系统将来的扩展，例如JDK内置迭代器Iterator就无法实现逆向遍历，如果需要实现逆向遍历，只能通过其子类ListIterator等来实现，而ListIterator迭代器无法用于操作Set类型的聚合对象。在自定义迭代器时，创建一个考虑全面的抽象迭代器并不是件很容易的事情。

3、适用场景：

（1）访问一个集合对象的内容而无须暴露它的内部表示。

（2）需要为集合对象提供多种遍历方式。

（3）为遍历不同的聚合结构提供一个统一的接口。

PS：由于容器与迭代器的关系太密切了，所以大多数语言在实现容器的时候都给提供了迭代器，并且这些语言提供的容器和迭代器在绝大多数情况下就可以满足我们的需要，所以现在需要我们自己去实践迭代器模式的场景还是比较少见的，我们只需要使用语言中已有的容器和迭代器就可以了。