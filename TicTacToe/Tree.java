import fj.data.List;

/**
 * Created by billin on 16-7-1.
 * Search tree for tic tac toe
 */
class TreeNote<V> {

    private V note;
    public TreeNote(V v){
        note = v;
    }

    public V get(){
        return note;
    }

    public void update(V v){
        note = v;
    }
}

public class Tree<V> extends TreeNote<List<Tree<V>>>{

    private V note;
    private int value;

    public Tree(V note) {
        super(List.nil());
        this.note = note;
    }

    public Tree addNote(V note) {
        update(get().conss(new Tree<>(note)));
        return this;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int v){
        value = v;
    }

    public V getNote(){
        return note;
    }
}