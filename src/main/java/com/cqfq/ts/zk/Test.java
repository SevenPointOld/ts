package com.cqfq.ts.zk;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujc [liujunchi@ifunq.com]
 * @date 2017-07-26 10:17
 **/
public class Test {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(){
            {
                this.add("a");
                this.add("b");
                this.add("c");
                this.add("d");
            }
        };

        for (int i = 0; i < list.size(); i ++) {
            System.out.println(list.get(i));
            if (i%2 == 0) {
                list.remove(i);
                i--;
            }
        }
    }
}
