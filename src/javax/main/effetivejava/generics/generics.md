# Prefer lists to arrays

# 优先考虑泛型

当需要考虑多种类型的时候, 应该优先考虑泛型, 而不是Object. 
因为泛型机制可以在编译期内受检. 

# 优先考虑泛型方法

    public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<E>(s1);
        result.addAll(s2);
        return result;
    }

# 利用有限制的通配符来提升API的灵活性

* 子类 `<? extends E>`
* super class `<? super E>`

type for parameter: producer-extends, consumer-super

Example: [GenericsTest.java.max()](GenericsTest.java)

# Consider typesafe heterogeneous containers

Example code:
    
    // Typesafe heterogeneous container pattern - implementation
    public class Favorites {
        private Map<Class<?>, Object> favorites =
            new HashMap<Class<?>, Object>();
        
        public <T> void putFavorite(Class<T> type, T instance) {
            if (type == null)
                throw new NullPointerException("Type is null");
            favorites.put(type, type.cast(instance));
        }
        
        public <T> T getFavorite(Class<T> type) {
            return type.cast(favorites.get(type));
        }
    }

这种方法拥有两个局限性.
1. 如果客户端恶意申明和填写不同的类型将会导致运行时异常
2. 不能解决填充 `List<String>` 这样的形式的类型