# Check parameters for validity

> 对于共有方法, 要用Javadoc的`@throws` 标签在文档中说明违反参数值限制时会抛出的异常. 
这样的异常通常为 `IllegalArgumentException`, `IndexOutOfBoundsException` 或者 `NullPointerException`. 

such as: 

~~~ java
    /**
    * Returns a BigInteger whose value is (this mod m). This method
    * differs from the remainder method in that it always returns a
    * non-negative BigInteger.
    *
    * @param m the modulus, which must be positive
    * @return this mod m
    * @throws ArithmeticException if m is less than or equal to 0
    */
    public BigInteger mod(BigInteger m) {
        if (m.signum() <= 0)
            throw new ArithmeticException("Modulus <= 0: " + m);
        ... // Do the computation
    }
~~~

> 对于私有方法, 则可以通过 `accsert` 进行参数的检查. 
而构造器的检查也异常重要, 比如说检测传入的值是否为null, 是否符合约定等等. 
To summarize, each time you write a method or constructor, you should think
about what restrictions exist on its parameters. You should document these restrictions 
and enforce them with explicit checks at the beginning of the method body.

# 必要时进行保护性拷贝

在前面的 `class_and_interface` 中, 有一条规则指明, 
我们需要最小化类的可访问性, 其中提到不可以直接返回或接收一个引用类型, 
以避免不良的客户端行为对类的封装的破坏. 所以, 当我们需要返回一个
引用类型给客户端的时候, 必要的时候需要进行保护性的拷贝, 
防止不良的客户行为所带给类的影响. 

[Code Example](Period.java)

# 设计方法

* 谨慎地选择方法的名称
* 不要过于追求提供便利的方法. 每个方法都应该尽其所能, 方法太多会使得类难以学习.
* 避免过长的参数列表(不超过四个). 1. 分解方法; 2. 创建辅助类; 3. 使用 `Builder`
* 对于参数类型, 优先使用接口而不是类
* 对于 `boolean` 参数, 优先使用两个类型的 `enum`, 这样可以使得代码变得容易阅读和使用.

# 谨慎使用重载方法

> 使用重载方法安全保守的策略是, _永远不要导出两个具有相同参数数目的重载方法_. 
如果方法使用可变参数, _保守的策略是永远不要重载它_. 
如果使用重载, 至少要避免这种情形: 同一组参数住需要经过类型转换就可以被传递给不同的重载方法, 
如果还不能避免这种情形, 则必须保证所有重载方法的行为是一致的.

[code example](OverloadTest.java)

# 慎用可变参数

_可变参数的参数值可以是0到n个_, 当传入参数多于一个的时候, 
可以使用以下的方法:

~~~ java
    static int min(int firstArg, int... remaining) {
        int min = firstArg;
        for (int arg : remaining)
            if (arg < min) min = arg;
        
        return min;
    }
~~~

在重视性能的情况下, 可以通过重载多个不同参数个数的方法来免去可变参数创建数组的开销. 
例如: 
    
    public void foo() {}
    public void foo(int a1) {}
    public void foo(int a1, int a2) {}
    public void foo(int a1, int a2, int a3) {}
    public ...
    public void foo(int a1, int a2, int a3, int a4, int a5, int... args) {}
    
# 返回类型是数组或者是集合, 则返回零长度的数组或者集合, 而不是 _`null`_

# 为所有导出的API元素编写文档注释

* `@throws` 申明抛出的异常
* `@code{xxx}` 代码块
* `@literal` 文本, 常用于转义
* 文档使可以被继承的
* 方法的描述应该是动词短语
* 类的描述应该是名词短语



