package com.hzf;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ZhiFeng Hu on 2016/5/18.
 */
public class getKeDetail implements JDBCVariable {
    public static final String KEFILE = "ke";
    public static final String ZHONGFILE = "zhong";
    public static final String KEZHONGRELATION = "ke_zhong";
    public static final String KEZHONGRELATION_BINARY = "ke_zhong_binary";

    public static void main(String[] args) throws Exception {
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
        Statement stmt = conn.createStatement();
        String sql = "select distinct(zhong) from " + PROCESSEDTABLE + " where ke= ";
        BufferedReader br = new BufferedReader(new FileReader(KEFILE));
        String line = null;
        HashMap<String, ArrayList<String>> keZhongRelation;
        File kezhongbinary = new File(KEZHONGRELATION_BINARY);
        if (!kezhongbinary.exists()) {
            keZhongRelation = new HashMap<>();
            while ((line = br.readLine()) != null) {
                String[] details = line.split(":");
                String ke = details[0];
                ResultSet rs = stmt.executeQuery(sql + "'" + ke + "'");
                ArrayList<String> zhongs = new ArrayList<>();
                while (rs.next()) {
                    zhongs.add(rs.getString("zhong"));
                }
                keZhongRelation.put(ke, zhongs);
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(KEZHONGRELATION));
            for (String ke : keZhongRelation.keySet()) {
                StringBuilder zhongs = new StringBuilder();
                for (String zhong : keZhongRelation.get(ke)) {
                    zhongs.append(zhong).append(" ");
                }
                bw.write(ke + ":" + zhongs.toString() + '\n');
            }
            bw.close();
            saveToLocal(KEZHONGRELATION_BINARY,keZhongRelation);
        }else{
            keZhongRelation = (HashMap<String, ArrayList<String>>) loadLocal(KEZHONGRELATION_BINARY);
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter("ke_zhong_nums"));
        for(String ke: keZhongRelation.keySet()){
            bw.write(ke+":"+keZhongRelation.get(ke).size()+'\n');
        }
        bw.close();
    }
    public static void saveToLocal(String boardName,Object obj){
        try{
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(boardName));
            os.writeObject(obj);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public static Object loadLocal(String boardName) throws Exception{
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(boardName));
        Object ret = is.readObject();
        return ret;
    }
}
