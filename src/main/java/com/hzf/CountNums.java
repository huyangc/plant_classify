package com.hzf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by ZhiFeng Hu on 2016/4/25.
 */
public class CountNums implements JDBCVariable{
    public static void main(String[] args) throws Exception{
        HashMap<String,Integer> counts = new HashMap<>();
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(URL,USER,PASSWD);
        Statement stmt = conn.createStatement();
        String sql = "select zhong from imagepath_aft1";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            String key = rs.getString(1);
            if(counts.containsKey(key)) {
                int i = counts.get(key);
                counts.put(key, ++i);
            }else
                counts.put(key,1);
        }
        List<Map.Entry<String,Integer>> list=new ArrayList<>();
        list.addAll(counts.entrySet());
        CountNums.ValueComparator vc=new ValueComparator();
        Collections.sort(list,vc);
        int nums = 0;
        for(Iterator<Map.Entry<String,Integer>> it=list.iterator();it.hasNext();)
        {
            Map.Entry<String,Integer> count = it.next();
            if(count.getValue()>40){
                ++nums;
                System.out.println(count.getKey()+" : "+count.getValue());
            }
        }
        System.out.println(nums);
    }
    private static class ValueComparator implements Comparator<Map.Entry<String,Integer>>
    {
        public int compare(Map.Entry<String,Integer> m,Map.Entry<String,Integer> n)
        {
            return n.getValue()-m.getValue();
        }
    }
}
