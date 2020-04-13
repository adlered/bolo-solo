/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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