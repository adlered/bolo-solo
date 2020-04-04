package org.b3log.solo.bolo.tool;

import java.util.LinkedList;

/**
 * <h3>bolo-solo</h3>
 * <p>固定长度List</p>
 * <p>如果List里面的元素个数大于了缓存最大容量，则删除链表的顶端元素</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-04-04
 **/
public class FixSizeLinkedList<T> extends LinkedList<T> {
    // 定义缓存的容量
    private int capacity;

    public FixSizeLinkedList(int capacity) {
        super();
        this.capacity = capacity;
    }

    @Override
    public boolean add(T e) {
        // 超过长度，移除最后一个
        if (size() + 1 > capacity) {
            super.removeFirst();
        }
        return super.add(e);
    }
}