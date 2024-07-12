# Java设计模式之行为型：解释器模式

#### 一、什么是解释器模式：

​    解释器模式，就是定义语言的文法，并建立一个解释器来解释该语言中的句子，通过构建解释器，解决某一频繁发生的特定类型问题实例。

> 这里我们将语言理解成使用规定格式和语法的代码

​    解释器模式描述了如何构成一个简单的语言解释器，主要应用在使用面向对象语言开发的编译器中，它描述了如何为简单的语言定义一个文法，如何在该语言中表示一个句子，以及如何解释这些句子。   

> ​    例如我们经常利用正则表达式检测某些字符串是否符合我们规定的格式。这里正则表达式就是解释器模式的应用，解释器为正则表达式定义了一个文法，如何表示一个特定的正则表达式，以及如何解释这个正则表达式。

​    解释器模式中除了能够使用文法规则来定义一个语言，还能通过使用抽象语法树来更加直观表示、更好地地表示一个语言的构成，每一颗抽象语法树对应一个语言实例。抽象语法树描述了如何构成一个复杂的句子，通过对抽象语法树的分析，可以识别出语言中的终结符和非终结符类。 在解释器模式中由于每一种终结符表达式、非终结符表达式都会有一个具体的实例与之相对应，所以系统的扩展性比较好。

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/24/20181105154535890.png)



#### 二、UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/24/20181105154705230.png)

> - AbstractExpression：抽象解释器，声明一个抽象的解释操作方法 interpret()。具体解释任务由它的各个实现类来完成，具体解释器分别由终结符解释器 TerminalExpression 和非终结符解释器 NonterminalExpression 完成。
> - TerminalExpression：终结符表达式，实现与文法中的元素相关联的解释操作，通常一个解释器模式中只有一个终结符表达式，但有多个实例，对应不同的终结符。终结符一般是文法中的运算单元，比如有一个简单的公式 R = R1 + R2，在里面 R1 和R2 就是终结符，解析 R1 和 R2 的解释器就是终结符表达式。
> - NonterminalExpression：非终结符表达式，文法中的每条规则对应于一个非终结符表达式，非终结符表达式一般是文法中的运算符或者其他关键字，比如公式 R = R1 + R2中，+就是非终结符，解析+的解释器就是一个非终结符表达式。非终结符表达式根据逻辑的复杂程度而增加，原则上每个文法规则都对应一个非终结符表达式。
> - Context：环境类，包含解释器之外的一些全局信息，这个角色的任务一般是用来存放文法中各个终结符所对应的具体值，比如 R = R1 + R2，我们给 R1 赋值 100，给 R2 赋值 200。这些信息需要存放到环境角色中，很多情况下我们使用Map来充当环境角色就足够了。
> - Client：客户类



#### 三、代码实现：

 现在我们用解释器模式来实现一个基本的加、减、乘、除和求模运算。例如用户输入表达式“3 * 4 / 2 % 4”，输出结果为2。下图为该实例的UML结构图：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/24/20181105155750281.png)

抽象语法树：

![img](http://hsy.sylianxizhuanyong.cn:9001/blog/2024/06/24/20181105155812950.png)

抽象表达式：Node.java。

```java
public interface Node{
    public int interpret();
}
```

非终结表达式：ValueNode.java。主要用解释该表达式的值。

```java
public class ValueNode implements Node{
    private int value;
    
    public ValueNode(int value){
        this.value=value;
    }
        
    public int interpret(){
        return this.value;
    }
}
```

 终结表达式抽象类，由于该终结表达式需要解释多个运算符号，同时用来构建抽象语法树：

```java
public abstract class SymbolNode implements Node{
    protected Node left;
    protected Node right;
    
    public SymbolNode(Node left,Node right){
        this.left=left;
        this.right=right;
    }
}
```

MulNode.java：

```java
public class MulNode extends SymbolNode{
    public MulNode(Node left,Node right){
        super(left,right);
    }
    
    public int interpret(){
        return left.interpret() * right.interpret();
    }
}
```

ModNode.java：

```
public class ModNode extends SymbolNode{
    public ModNode(Node left,Node right){
        super(left,right);
    }
    
    public int interpret(){
        return super.left.interpret() % super.right.interpret();
    }
}
```

DivNode.java：

```java
public class DivNode extends SymbolNode{
    public DivNode(Node left,Node right){
        super(left,right);
    }
    
    public int interpret(){
        return super.left.interpret() / super.right.interpret();
    }
}
```

Calculator.java：

```java
public class Calculator{
    private String statement;
    private Node node;
    
    public void build(String statement){
        Node left=null,right=null;
        Stack stack=new Stack();
        
        String[] statementArr=statement.split(" ");
        
        for(int i=0;i<statementArr.length;i++){    
            if(statementArr[i].equalsIgnoreCase("*")){
                left=(Node)stack.pop();
                int val=Integer.parseInt(statementArr[++i]);
                right=new ValueNode(val); 
                stack.push(new MulNode(left,right));
            }
            else if(statementArr[i].equalsIgnoreCase("/")){
                left=(Node)stack.pop();
                    int val=Integer.parseInt(statementArr[++i]);
                    right=new ValueNode(val); 
                stack.push(new DivNode(left,right));                
            }
            else if(statementArr[i].equalsIgnoreCase("%")){
                left=(Node)stack.pop();
                    int val=Integer.parseInt(statementArr[++i]);
                    right=new ValueNode(val); 
                stack.push(new ModNode(left,right));               
            }
            else{
                stack.push(new ValueNode(Integer.parseInt(statementArr[i])));
            }
        }
        this.node=(Node)stack.pop();
    }
    
    public int compute()
        return node.interpret();
    }
}
```

客户端：Client.java：

```java
public class Client{
    public static void main(String args[]){
        String statement = "3 * 2 * 4 / 6 % 5";
        
        Calculator calculator = new Calculator();
        
        calculator.build(statement);
        
        int result = calculator.compute();
        
        System.out.println(statement + " = " + result);    
    }
}
```

运行结果：3 * 2 * 4 / 6 % 5 = 4



#### 四、解释器模式小结：

1、优点：

（1）解释器是一个简单的语法分析工具，它最显著的优点就是扩展性，修改语法规则只需要修改相应的非终结符就可以了，若扩展语法，只需要增加非终结符类就可以了。

（2）增加了新的解释表达式的方式。

（3）易于实现文法。

2、缺点：

（1）解释器模式会引起类的膨胀，每个语法都需要产生一个非终结符表达式，语法规则比较复杂时，就可能产生大量的类文件，为维护带来非常多的麻烦。

（2）效率低下，采用递归调用方法，每个非终结符表达式只关心与自己相关的表达式，每个表达式需要知道最终的结果，必须通过递归方式，无论是面向对象的语言还是面向过程的语言，递归都是一个不推荐的方式。由于使用了大量的循环和递归，效率是一个不容忽视的问题。特别是用于解释一个解析复杂、冗长的语法时，效率是难以忍受的。

3、适用场景：

（1）有一个简单的语法规则，比如一个sql语句，如果我们需要根据sql语句进行rm转换，就可以使用解释器模式来对语句进行解释。

（2）一些重复发生的问题，比如加减乘除四则运算，但是公式每次都不同，有时是a+b-c*d，有时是a*b+c-d，等等，公式千变万化，但是都是由加减乘除四个非终结符来连接的，这时我们就可以使用解释器模式。