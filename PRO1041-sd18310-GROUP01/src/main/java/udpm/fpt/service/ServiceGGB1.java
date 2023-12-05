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
import udpm.fpt.model.Sale_Bill1;

/**
 *
 * @author Thanh Dat
 */
public class ServiceGGB1 {

    public List<Sale_Bill1> getAll() {
        String sql = "Select id, sale_event, discount_conditions, percent_decrease, start_day, end_day, staff_id, created_at from SaleBill;"
                + "";
        try (Connection con = DBConnect1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Sale_Bill1> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Sale_Bill1(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDate(5), rs.getDate(6), rs.getInt(7), rs.getDate(8)));
            }
            return list;
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return null;
    }

    public boolean add(Sale_Bill1 sb) {
        String sql = "Insert into SaleBill(id, sale_event, discount_conditions, percent_decrease, start_day, end_day, staff_id, created_at) values(?,?,?,?,?,?,?,?);";
        try (Connection con = DBConnect1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, sb.getID());
            ps.setObject(2, sb.getSE());
            ps.setObject(3, sb.getDC());
            ps.setObject(4, sb.getPD());
            ps.setObject(5, sb.getSD());
            ps.setObject(6, sb.getED());
            ps.setObject(7, sb.getSFID());
            ps.setObject(8, new Date());
            return ps.executeUpdate() > 0;
        } catch (Exception Ex) {
            Ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(int ID) {
        String sql = "Delete from SaleBill where id = ?;";
        try (Connection con = DBConnect1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, ID);
            return ps.executeUpdate() > 0;
        } catch (Exception Ex) {
            Ex.printStackTrace();
            return false;
        }
    }

    public boolean update(int ID, Sale_Bill1 sb) {
        String sql = "Update SaleBill set sale_event=?, discount_conditions=?, percent_decrease=?, start_day=?, end_day=?, staff_id=?, created_at=? where id=?;";
        try (Connection con = DBConnect1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(8, sb.getID());
            ps.setObject(1, sb.getSE());
            ps.setObject(2, sb.getDC());
            ps.setObject(3, sb.getPD());
            ps.setObject(4, sb.getSD());
            ps.setObject(5, sb.getED());
            ps.setObject(6, sb.getSFID());
            ps.setObject(7, sb.getCRAT());
            return ps.executeUpdate() > 0;
        } catch (Exception Ex) {
            Ex.printStackTrace();
            return false;
        }
    }

    public List<Sale_Bill1> timkiem(String SE) {
        String sql = "Select  id, sale_event, discount_conditions, percent_decrease, start_day, end_day, staff_id, created_at from SaleBill where sale_event like ? ";
        try (Connection con = DBConnect1.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            List<Sale_Bill1> list = new ArrayList<>();
            ps.setString(1, SE + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Sale_Bill1(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getDate(5), rs.getDate(6), rs.getInt(7), rs.getDate(8)));
            }
            return list;
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return null;
    }

}
// SELECT * FROM TABLENAME INNER JOIN TABLENAME2 ON TABLENAME.ID = TABLENAME2.ID_TABLENAME1 INN....
