# 使用枚举代替 `public static final xxx`

Enum example:
    
    public enum Allpe {FUJI, PIPPIN, GRANNY_SITH}
    public enum Orange { NAVEL, TEMPLE, BLOOD }
    
or more complex [example](Planet.java)

这个例子说明, 可以在编译期已经确定集合的所有值可以使用枚举. 
一个更加复杂的[例子](PayrollDay.java)

# Use instance fields instead of ordinals

For example:

    public enum Ensemble {
        SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
        SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8),
        NONET(9), DECTET(10), TRIPLE_QUARTET(12);
        
        private final int numberOfMusicians;
        Ensemble(int size) { this.numberOfMusicians = size; }
        public int numberOfMusicians() { return numberOfMusicians; }
    }
   
# Use EnumSet instead of bit fields

Just example:

    text.applyStyles(STYLE_BOLD | STYLE_ITALIC);
    
    //replace to
    
    public class Text {
        public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }
        // Any Set could be passed in, but EnumSet is clearly best
        public void applyStyles(Set<Style> styles) { ... }
    }
    text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC))
    
# Use EnumMap instead of ordinal indexing

Example [code](Herb.java).

> 使用 `EnumMap` 可以更好的提升程序的可读性, 安全性(类型安全), 易维护性, 而且在空间和时间上
所消耗的性能几乎等同与使用 `ordinal` 引导的数组. 
如果所需要引导的数组是多维的, 则可以使用 `EnumMap<Enum.class, EnumMap<Enum.class, xxx>>` 来引导. 

******************************************************

# Consistently use the _`override`_ annotation

# Use marker interfaces to define types

> 如果想要定义一个任何新方法都不会与之关联的类型, 
标记接口就是最好的选择. 
如果想要标记程序元素而非类和接口, 
考虑到未来可能要给标记添加更多的信息, 
或者标记要适合与已经广泛使用了注解类型的框架, 
那么标记注解就是一个正确的选择.
