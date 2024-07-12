# Java设计模式之结构型：组合模式

**前言：**

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/20181102200711680.jpg)

​    我们对于上面两幅图片肯定非常熟悉，这两幅图片我们都可以看做是一个文件结构，对于这样的结构我们称之为[树形结构](https://so.csdn.net/so/search?q=树形结构&spm=1001.2101.3001.7020)。在数据结构中我们知道可以通过调用某个方法来遍历整个树，当我们找到某个叶子节点后，就可以对叶子节点进行相关的操作。我们可以将这颗树理解成一个大的容器，容器里面包含很多的成员对象，这些成员对象即可是容器对象也可以是叶子对象。但是由于容器对象和叶子对象在功能上面的区别，使得我们在使用的过程中必须要区分容器对象和叶子对象，但是这样就会给客户带来不必要的麻烦，作为客户而已，它始终希望能够一致的对待容器对象和叶子对象。这就是组合模式的设计动机。



#### 一、什么是组合模式：

​    组合模式将叶子对象和容器对象进行递归组合，形成树形结构以表示“部分-整体”的层次结构，使得用户对单个对象和组合对象的使用具有一致性，能够像处理叶子对象一样来处理组合对象，无需进行区分，从而使用户程序能够与复杂元素的内部结构进行解耦。

​    组合模式最关键的地方是叶子对象和组合对象实现了相同的抽象构建类，它既可表示叶子对象，也可表示容器对象，客户仅仅需要针对这个抽象构建类进行编程，这就是组合模式能够将叶子节点和对象节点进行一致处理的原因。

​    通过组合模式，可以清晰地定义复杂对象的层次结构，叶子对象可以被组合成更复杂的容器对象，而容器对象又可以被组合，这样不断递归从而形成复杂的树形结构；同时在组合模式中加入新的对象构建也更容易，客户端不必因为加入了新的对象构件而更改原有代码。

​    文章最前面的图展示了计算机的文件系统，文件系统由文件和目录组成，目录下面也可以包含文件或者目录，计算机的文件系统是用递归结构来进行组织的，对于这样的数据结构是非常适用使用组合模式的。



#### 二、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/20181102201058998.jpg)

组合模式主要包含如下几个角色：

> - Component ：抽象构建类，组合中的对象声明接口，在适当的情况下，实现所有类共有接口的默认行为。声明一个接口用于访问和管理Component子部件。
> - Leaf：叶子对象。叶子结点没有子结点。
> - Composite：容器对象，定义有枝节点行为，用来存储子部件，在Component接口中实现与子部件有关操作，如增加(add)和删除(remove)等。

从模式结构中我们看出了[叶子节点](https://so.csdn.net/so/search?q=叶子节点&spm=1001.2101.3001.7020)和容器对象都实现Component接口，这也是能够将叶子对象和容器对象一致对待的关键所在。



#### 三、代码实现：

​    在文件系统中，可能存在很多种格式的文件，如果图片，文本文件、视频文件等等，这些不同的格式文件的浏览方式都不同，同时对文件夹的浏览就是对文件夹中文件的浏览，但是对于客户而言都是浏览文件，两者之间不存在什么差别，现在只用组合模式来模拟浏览文件。UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/20181102201222539.jpg)

首先是文件类：File.java

```java
public abstract class File {
    
    String name;
    
    public File(String name){
        this.name = name;
    }

    public String getName() {

        return name;

    }
    public void setName(String name) {

        this.name = name;

    }
    public abstract void display();

}
```

然后是文件夹类：Folder.java，该类包含对文件的增加、删除和浏览三个方法

```java
public class Folder extends File{
    
    private List<File> files;
    
    public Folder(String name){

        super(name);

        files = new ArrayList<File>();

    }
    /**
     * 浏览文件夹中的文件
     */
    public void display() {

        for(File file : files){
            file.display();
        }
    }

    /**

     * @desc 向文件夹中添加文件

     * @param file

     * @return void

     */

    public void add(File file){
        files.add(file);

    }

    /**
     * @desc 从文件夹中删除文件
     * @param file
     * @return void
     */
    public void remove(File file){
        files.remove(file);
    }
}
```

然后是三个文件类：TextFile.java、ImageFile.java、VideoFile.java

```java
public class TextFile extends File{

    public TextFile(String name) {

        super(name);

    }

    public void display() {
        System.out.println("这是文本文件，文件名：" + super.getName());

    }

}

public class ImagerFile extends File{

 public ImagerFile(String name) {

        super(name);

    }

    public void display() {

        System.out.println("这是图像文件，文件名：" + super.getName());

    }

}


public class VideoFile extends File{
    public VideoFile(String name) {
        super(name);
    }

    public void display() {

        System.out.println("这是影像文件，文件名：" + super.getName());

    }
}
```

最后是客户端：

```java
public class Client {

    public static void main(String[] args) {

       /**

         * 我们先建立一个这样的文件系统

         * 总文件
         *                  
         *   a.txt    b.jpg     c文件夹              
         *     c_1.text  c_1.rmvb    c_1.jpg                                                      
         */ 
        //总文件夹
        Folder zwjj = new Folder("总文件夹");
        //向总文件夹中放入三个文件：1.txt、2.jpg、1文件夹
        TextFile aText= new TextFile("a.txt");
        ImagerFile bImager = new ImagerFile("b.jpg");
        Folder cFolder = new Folder("C文件夹");
        zwjj.add(aText);
        zwjj.add(bImager);
        zwjj.add(cFolder);
        //向C文件夹中添加文件：c_1.txt、c_1.rmvb、c_1.jpg 
        TextFile cText = new TextFile("c_1.txt");
        ImagerFile cImage = new ImagerFile("c_1.jpg");
        VideoFile cVideo = new VideoFile("c_1.rmvb");
        cFolder.add(cText);
        cFolder.add(cImage);
        cFolder.add(cVideo);
        //遍历C文件夹
        cFolder.display();
        //将c_1.txt删除
        cFolder.remove(cText);
        System.out.println("-----------------------");
        cFolder.display();
    }
}
```

运行结果：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/201811022017414.jpg)