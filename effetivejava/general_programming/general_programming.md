# Minimize the scope of local variables

> The most powerful technique for minimizing the scope of a 
local variable is to declare it where it is first used. 

# Prefer for-each loops to traditional for loops

> 使用 for-each 可以减少使程序出错的几率, 并且性能上还可以稍微提高. 
for-each 循环不仅可以遍历集合和数组, 还可以让你遍历任何实现Iterable接口的对象. 

# 了解和使用类库

[Example code](RandomGetter.java)

# 如果需要精确的答案, 避免使用 `double` and `float`

> use `BigDecimal`, `int` or `long` for monetary calculations. 
__注意:__ `==` 不能用于装箱类型; 装箱类型需要初始化, 否则会出现 `NullPointerException`; 
频繁的拆箱和装箱会严重影响性能. 

# 如果其他类型更适合, 避免直接使用字符串

# 当心字符串的连接性能

> 字符串的连接会是 o(n^2) 级别, 这是由于字符串的不可变所决定的. 
如果需要构建大的字符串, 需要使用 `StringBuilder`

# 通过接口引用对象, 而不是类

    List<xx> list = new ArrayList<>();
    
# 接口优先于反射

# 谨慎使用本地方法

> Java Native Interface 允许 Java 程序可以调用本地程序设计语言(C 或 C++) 编写的特殊方法. 
提供了 "访问特定于平台的机制" 的能力, 访问遗留代码库的能力, 提高系统性能的能力. 

# 谨慎地进行优化

> 一个好的程序首先应该是结构上的, 而不要为了追求性能上的优化把原本好的结构变坏了. 
在进行优化的时候可以通过性能工具重点测试需要优化哪里. 