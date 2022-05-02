package com.example.lab3_behind.utils;

import com.example.lab3_behind.common.MyPage;

import java.util.ArrayList;
import java.util.List;

public class MyPageTool {
    public static MyPage<Object> getPage(List<Object> allDatas,Integer size,Integer pageNum){
        MyPage<Object> ans = new MyPage<>();
        ans.setTotal(allDatas.size());
        ans.setRecords(new ArrayList<>());
        for(int i=(pageNum-1)*size; i<pageNum*size && i<ans.getTotal() && i>=0;i++){
            ans.getRecords().add(allDatas.get(i));
        }
        return ans;
    }
}
