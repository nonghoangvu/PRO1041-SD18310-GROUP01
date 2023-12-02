/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.config;
import java.sql.*;

/**
 *
 * @author Thanh Dat
 */
public class DBConnect1 {
    public static String db = "THTrueMilk";
    public static String port = "1433";
    public static String username = "sa";
    public static String password = "sa";
    public static String server = "DAT\\SQLEXPRESS";
    public static String url = "jdbc:sqlserver://DAT\\SQLEXPRESS:1433;databaseName=THTrueMilk;encrypt=true;trustServerCertificate=true;username=sa;password=sa;";   
    public static Connection  getConnection(){ 
        Connection conn ;
        try{
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        if(getConnection() != null){
            System.out.println("Kết nối thành công");
        }else{
            System.out.println("Kết nối thất bại");
        }
    }
}
