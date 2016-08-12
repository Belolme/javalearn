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