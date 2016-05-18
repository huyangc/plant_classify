package com.hzf;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
        String classBasic = "ke";
        String sql = "select "+classBasic+" from imagepath_aft1";
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
        BufferedWriter bw = new BufferedWriter(new FileWriter(classBasic));
        for(Iterator<Map.Entry<String,Integer>> it=list.iterator();it.hasNext();)
        {
            Map.Entry<String,Integer> count = it.next();
            if(count.getValue()>40){
                ++nums;
                String content = count.getKey()+":"+count.getValue();
                System.out.println(content);
                bw.write(content+'\n');
            }
        }
        bw.close();
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
