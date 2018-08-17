package javax.main.effetivejava.enums_and_annotations;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by billin on 16-8-13.
 * EnumMap demo
 */
public class Herb {
    private enum Type {ANNUAL, PERENNIAL, BIENNIAL}

    private final String name;
    private final Type type;

    Herb(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {

        Herb[] garden = {
                new Herb("a", Type.ANNUAL),
                new Herb("b", Type.ANNUAL),
                new Herb("c", Type.BIENNIAL),
                new Herb("d", Type.PERENNIAL)
        };

        // Using an EnumMap to associate data with an enum
        Map<Type, Set<Herb>> herbsByType =
                new EnumMap<>(Herb.Type.class);

        for (Herb.Type t : Herb.Type.values())
            herbsByType.put(t, new HashSet<>());

        for (Herb h : garden)
            herbsByType.get(h.type).add(h);

        System.out.println(herbsByType);
        /*
        output:
        {ANNUAL=[a, b], PERENNIAL=[d], BIENNIAL=[c]}
         */
    }
}
