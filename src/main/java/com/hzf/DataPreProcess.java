package com.hzf;

import java.sql.*;

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
            String plantName = rs.getString(2);
            if(plantName == null||plantName.isEmpty())
                continue;
            int imgId = rs.getInt(1);
            String[] paths = plantName.split(">>");
            prepStmt.setInt(1,imgId);
            for(String str:paths){
                if(str.contains("门"))
                    prepStmt.setString(2,str.trim());
                else if(str.contains("科"))
                    prepStmt.setString(3,str.trim());
                else if(str.contains("属"))
                    prepStmt.setString(4,str.trim());
                else
                    prepStmt.setString(5,str.trim());
            }
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
