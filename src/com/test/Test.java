package com.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        Set set = new HashSet();
        set = (Set)list.stream().distinct().collect(Collectors.toSet()); //将list集合转换成Set集合的方法
        set.forEach(s-> System.out.println(s));
    }
}
