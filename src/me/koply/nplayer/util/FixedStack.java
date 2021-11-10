package me.koply.nplayer.util;

// if stack is full
// overwrites last index
public class FixedStack<T> {

    private final Object[] array;
    public final int size;

    private int cursor;

    public FixedStack(int size) {
        this.size = size;
        this.array = new Object[size];
        this.cursor = 0;
    }

    public void push(T object) {
        array[cursor] = object;
        if (cursor+1==size) cursor = 0;
        else cursor++;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (cursor == 0) cursor = size-1;
        else cursor--;
        Object toreturn = array[cursor];
        array[cursor] = null;
        return (T) toreturn;
    }
}