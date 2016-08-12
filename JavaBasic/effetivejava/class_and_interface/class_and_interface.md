# 使类和成员的可访问性最小化

可以有效地解除组成系统的各模块之间的耦合关系, 
使得这些模块可以独立的开发, 测试, 优化, 使用, 理解和修改. 
隐藏信息不能得到很好的性能, 不过可以通过模块之间的刨析得到各个模块的性能. 
隐藏信息还可以减低构建大型项目的风险.

    private static final Thing[] PRIVATE_VALUES = {...}
    public static final List<Thing> VALUES = 
        Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));
    
# In public classes, use accesor methods, not public fields

# Minimize mutability
   
* Don't provide any methods that modify the object's state
* Ensure that the class can't be extended through `final` keyword
* Make all fields `final`
* Make all fields `private`
* Ensure exclusive access to any mutable components. ensure that clients of the class cannot obtain
references to these objects. Never initialize such a field to a client-provided object reference or 
return the object reference form an accessor. Make defensive copies.

[code example](./Complex.java)

上述所有的特点符合函数式编程的特点. 因为不可变的特性, 可以把一些需要消耗时间性能的操作缓存起来. 


不可变对象的缺点是对于每一个不同的值都需要一个单独的对象. 
一般使用频繁的对象会使用对象池进行管理( `final Object = new Object(xxx,xxx)`), 
或者使用一个配套的可变类进行需要大量新建类的操作. 

# Favor composition over inheritance (复合优先于继承)

# Design and document for inheritance or else prohibit it

First, the class must document precisely the effects of overriding any method. 
The documentation must indicate which overridable methods the method or constructor invokes, 
in what sequence, and how the results of each invocation affect subsequent processing.

# Prefer interfaces to abstract classes

# Use interfaces only to define types but not define constant

# Prefer class hierarchies to tagged classes

Tagged classes: For example, consider this class, which is capable of representing a circle or a rectangle.

# Use function objects to represent strategies

Java does note provide function pointers, but object references can be used 
to achieve a similar effect. such as:
     
    function object:
    class StringLengthComparator {
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }

A StringLengthComparator instance is a _concrete stategy_ for string comparison. 

当一个策略类只使用一次的时候, 策略类往往使用匿名类申明. 

    Arrays.sort(stringArray, new Comparator<String>() {
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    });
    