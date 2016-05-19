package com.hzf;

import java.sql.*;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class DataPreProcess implements JDBCVariable
{
    public static void main( String[] args ) throws Exception
    {
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(URL, USER, PASSWD);
        conn.setAutoCommit(false);
        Statement slStmt = conn.createStatement();
        String sql = "select * from imagepath";
        ResultSet rs = slStmt.executeQuery(sql);
        String sqlinsert = "INSERT INTO imagepath_aft1 (imgname,men, ke, shu, zhong) VALUES (?,?, ?, ?, ?)";
        PreparedStatement prepStmt = conn.prepareStatement(sqlinsert);
        while(rs.next()){
            prepStmt.clearParameters();
            String plantName = rs.getString(2);
            if(plantName == null||plantName.isEmpty())
                continue;
            int imgId = rs.getInt(1);
            String[] paths = plantName.split(">>");
            HashMap<String,String> values = new HashMap<>();
            prepStmt.setInt(1,imgId);
            for(String str:paths){
                if(str.contains("门"))
                    values.put("门",str.trim());
                else if(str.contains("科"))
                    values.put("科",str.trim());
                else if(str.contains("属"))
                    values.put("属",str.trim());
                else
                    values.put("种",str.trim());
            }
            prepStmt.setString(2,values.get("门"));
            prepStmt.setString(3,values.get("科"));
            prepStmt.setString(4,values.get("属"));
            prepStmt.setString(5,values.get("种"));
            System.out.println(prepStmt);
            prepStmt.execute();
        }
        conn.commit();
        prepStmt.close();
        rs.close();
        slStmt.close();
        conn.close();
    }
}
