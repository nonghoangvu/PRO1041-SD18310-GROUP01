/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import udpm.fpt.config.DBConnect1;
import udpm.fpt.model.KhachHang;

/**
 *
 * @author dangc
 */
public class KhachHangDao {

    public List<KhachHang> getAll() {
        String sql = "Select * from Customer";
        try {
            List<KhachHang> arrkH = new ArrayList<>();
            arrkH.clear();
            Connection con = DBConnect1.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arrkH.add(new KhachHang(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getDate(10))
                );
            }
            return arrkH;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(KhachHang kh) {
        String sql = "Insert into Customer(fullname,phone,email,birth_year,gender,[address],customer_type_id,note) VALUES(?,?,?,?,?,?,?,?)";
        try {
            Connection con = DBConnect1.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, kh.getFullname());
            ps.setObject(2, kh.getPhone());
            ps.setObject(3, kh.getEmail());
            ps.setObject(4, kh.getBirth_year());
            ps.setObject(5, kh.getGender());
            ps.setObject(6, kh.getAddress());
            ps.setObject(7, kh.getCustome_type_id());
            ps.setObject(8, kh.getNote());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int ID) {
        String sql = "delete  from Customer where id = ?";
        try {
            Connection con = DBConnect1.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(1, ID);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(int ID, KhachHang kh) {
        LocalDate currentDate = LocalDate.now();
        String sql = "Update Customer set fullname = ?, phone = ?, email = ?, birth_year = ?,gender = ?, address = ?, customer_type_id = ?, note = ?  where id = ?";
        try {
            Connection con = DBConnect1.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setObject(9, ID);
            ps.setObject(1, kh.getFullname());
            ps.setObject(2, kh.getPhone());
            ps.setObject(3, kh.getEmail());
            ps.setObject(4, kh.getBirth_year());
            ps.setObject(5, kh.getGender());
            ps.setObject(6, kh.getAddress());
            ps.setObject(7, kh.getCustome_type_id());
            ps.setObject(8, kh.getNote());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhachHang> Search(String KH) {
        String sql = "Select id, fullname,phone,email,birth_year,gender,address,customer_type_id,note,created_at from Customer where fullname like ? ";
        try {
            List<KhachHang> list = new ArrayList<>();
            Connection con = DBConnect1.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, KH + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new KhachHang(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getDate(10)));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public KhachHang getID(int ID){
        String sql = "Select * from Customer where id = ?";
        try {
            PreparedStatement pt = DBConnect1.getConnection().prepareStatement(sql);
            pt.setInt(1, ID);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {                
                return new KhachHang(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getDate(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
