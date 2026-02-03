package ly.utils;

/**
 * Tuple 元组类，用于存储一对值
 * 
 * @param <T1> 第一个元素的类型
 * @param <T2> 第二个元素的类型
 */
public class Tuple<T1, T2> {
    private T1 item1;
    private T2 item2;

    public Tuple(T1 item1, T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public T1 getItem1() {
        return item1;
    }

    public void setItem1(T1 item1) {
        this.item1 = item1;
    }

    public T2 getItem2() {
        return item2;
    }

    public void setItem2(T2 item2) {
        this.item2 = item2;
    }

    @Override
    public String toString() {
        return "(" + item1 + ", " + item2 + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Tuple<?, ?> tuple = (Tuple<?, ?>) obj;
        
        if (item1 != null ? !item1.equals(tuple.item1) : tuple.item1 != null) return false;
        return item2 != null ? item2.equals(tuple.item2) : tuple.item2 == null;
    }

    @Override
    public int hashCode() {
        int result = item1 != null ? item1.hashCode() : 0;
        result = 31 * result + (item2 != null ? item2.hashCode() : 0);
        return result;
    }
}