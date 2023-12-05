/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import udpm.fpt.config.DBConnect1;
import udpm.fpt.model.Sale_Milk1;

/**
 *
 * @author Thanh Dat
 */
public class ServiceGGM1 {

    public List<Sale_Milk1> getAll() {
        String sql = "Select id, sale_event, percent_decrease, start_day, end_day, milk_id, staff_id, created_at from SaleMilk";
        try (Connection con = DBConnect1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Sale_Milk1> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Sale_Milk1(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDate(4), rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getDate(8)));
            }
            return list;
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return null;
    }

    public boolean add(Sale_Milk1 sm) {
        String sql = "Insert into SaleMilk(id, sale_event, percent_decrease, start_day, end_day, milk_id, staff_id, created_at) values(?,?,?,?,?,?,?,?);";
        try (Connection con = DBConnect1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, sm.getID());
            ps.setObject(2, sm.getSE());
            ps.setObject(3, sm.getPD());
            ps.setObject(4, sm.getSD());
            ps.setObject(5, sm.getED());
            ps.setObject(6, sm.getMID());
            ps.setObject(7, sm.getSFID());
            ps.setObject(8, new Date());
            return ps.executeUpdate() > 0;
        } catch (Exception Ex) {
            Ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(int ID) {
        String sql = "Delete from SaleMilk where id = ?;";
        try (Connection con = DBConnect1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ID);
            return ps.executeUpdate() > 0;
        } catch (Exception Ex) {
            Ex.printStackTrace();
            return false;
        }
    }

    public boolean update(int ID, Sale_Milk1 sm) {
        String sql = "Update SaleMilk set sale_event=?, percent_decrease=?, start_day=?, end_day=?, milk_id=?, staff_id=?, created_at=? where id=?;";
        try (Connection con = DBConnect1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(8, sm.getID());
            ps.setObject(1, sm.getSE());
            ps.setObject(2, sm.getPD());
            ps.setObject(3, sm.getSD());
            ps.setObject(4, sm.getED());
            ps.setObject(5, sm.getMID());
            ps.setObject(6, sm.getSFID());
            ps.setObject(7, new Date());
            return ps.executeUpdate() > 0;
        } catch (Exception Ex) {
            Ex.printStackTrace();
            return false;
        }
    }

    public List<Sale_Milk1> timkiem(String SE) {
        String sql = "Select id, sale_event, percent_decrease, start_day, end_day, milk_id, staff_id, created_at from SaleMilk Where sale_event like ?";
        try (Connection con = DBConnect1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            List<Sale_Milk1> list = new ArrayList<>();
            ps.setString(1, SE + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Sale_Milk1(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDate(4), rs.getDate(5), rs.getInt(6), rs.getInt(7), rs.getDate(8)));
            }
            return list;
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return null;
    }

}
