/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package udpm.fpt.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import udpm.fpt.model.KhachHang;
import udpm.fpt.service.KhachHangDao;

/**
 *
 * @author dangc
 */
public class KhachHangForm extends javax.swing.JPanel {
    
    private KhachHang kh = new KhachHang();
    private List<KhachHang> list = new ArrayList<>();
    private KhachHangDao rs = new KhachHangDao();
    /**
     * Creates new form KhachHangForm
     */
    public KhachHangForm() {
        initComponents();
        loadTable();
        
    }
    public void loadTable() {
        DefaultTableModel dtm = (DefaultTableModel) tblCustomer.getModel();
        dtm.setRowCount(0);
        dtm.setColumnIdentifiers(new String[]{"ID", "Fullname", "Phone", "Email", "Birth year", "Gender", "Address", "Customer type", "Note", "Created at"});
        String Loai;
        for (KhachHang i : this.rs.getAll()) {
            if (i.getCustome_type_id() == 1) {
                Loai = "Regular customer";
            }else{
                Loai = "Vip customer";
            }
            Object[] rowdata = {
                i.getId(),
                i.getFullname(),
                i.getPhone(),
                i.getEmail(),
                i.getBirth_year(),
                i.getGender(),
                i.getAddress(),
                Loai,
                i.getNote(),
                i.getCreated_at()
            };
            dtm.addRow(rowdata);
        }
    }

    public void reset() {
        txtFullName.setText("");
        txtNote.setText("");
        txtAddress.setText("");
        txtBirthYear.setText("");
        txtEmail.setText("");
        txtSearch.setText("");
        txtPhone.setText("");
        rdoNam.setSelected(true);
        rdoRegularCustomer.setSelected(true);
        txtCreatedAt.setText("");
    }

    public KhachHang insert() {
        KhachHang kh = new KhachHang();
        kh.setFullname(txtFullName.getText());
        kh.setPhone(txtPhone.getText());
        kh.setEmail(txtEmail.getText());
        kh.setBirth_year(Integer.parseInt(txtBirthYear.getText()));
        try {
            kh.setGender(rdoNam.isSelected() ? "Nam" : "Nữ");
        } catch (Exception e) {
        }
        kh.setAddress(txtAddress.getText());
        try {
            kh.setCustome_type_id(rdoRegularCustomer.isSelected() ? 1 : 2);
        } catch (Exception e) {
            System.out.println("Lõi : " + e);
        }
        kh.setNote(txtNote.getText());
        kh.setCreated_at(new Date());
        return kh;
        
    }

    public void Search(String KH) {
        String FindName = txtSearch.getText();
        if (FindName == null) {
            KH = "";
        }
        DefaultTableModel dtm = (DefaultTableModel) tblCustomer.getModel();
        dtm.setRowCount(0);
        for (KhachHang kh : rs.Search(KH)) {
            Object[] rowSearch = {
                kh.getId(),
                kh.getFullname(),
                kh.getPhone(),
                kh.getEmail(),
                kh.getBirth_year(),
                kh.getGender(),
                kh.getAddress(),
                kh.getCustome_type_id(),
                kh.getNote(),
                kh.getCreated_at()
            };
            dtm.addRow(rowSearch);
        }

    }
    
    public boolean checkvali() {
        boolean tt = true;
        String fullname = txtFullName.getText().trim();
        String phone = txtPhone.getText().trim();
        if (fullname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên!");
            tt = false;
        } else if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            tt = false;
        }
        return tt;
    }

    public void Remove() {
        int row = tblCustomer.getSelectedRow();
        if (row==-1) {
            return;
        }
        boolean dl = rs.delete(Integer.valueOf(tblCustomer.getValueAt(row, 0).toString()));
        if (dl) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa không thành công!");
            return;
        }
    }

    public void Update() {
        KhachHang kh = this.insert();
        int row = tblCustomer.getSelectedRow();
        if (row==-1) {
            return;
        }
        try {
            if (rs.update(Integer.valueOf(tblCustomer.getValueAt(row, 0).toString()), kh)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật không thành công!");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        btnSearch = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtBirthYear = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        rdoRegularCustomer = new javax.swing.JRadioButton();
        rdoVipCustomer = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        txtCreatedAt = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtSearch = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Information Customer");

        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCustomerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCustomer);

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jPanel1.setBackground(java.awt.SystemColor.window);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel9.setText("Note:");

        jLabel3.setText("Full name:");

        jLabel4.setText("Phone:");

        jLabel5.setText("Email:");

        jLabel6.setText("Birth year:");

        jLabel7.setText("Address:");

        jLabel8.setText("Customer type :");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane1.setViewportView(txtNote);

        rdoRegularCustomer.setSelected(true);
        rdoRegularCustomer.setText("Regular Customer");

        rdoVipCustomer.setText("Vip Customer");

        jLabel11.setText("Created at:");

        txtCreatedAt.setEditable(false);

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane3.setViewportView(txtAddress);

        jLabel12.setText("Gender:");

        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBirthYear)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(63, 63, 63)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtFullName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPhone)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(40, 40, 40)
                                .addComponent(rdoRegularCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(rdoVipCustomer))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(68, 68, 68)
                                .addComponent(rdoNam)
                                .addGap(52, 52, 52)
                                .addComponent(rdoNu))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel9))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                            .addComponent(txtCreatedAt))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtBirthYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(rdoRegularCustomer)
                            .addComponent(rdoVipCustomer))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtCreatedAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnReset.setText("Reset Form");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });

        jLabel2.setText("Tìm kiếm khách hàng theo tên:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel2))
                                .addGap(58, 58, 58)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(60, 60, 60)
                                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtSearch))
                                .addGap(85, 85, 85)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnLoad, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(197, 197, 197)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(210, 210, 210))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnReset)
                    .addComponent(btnRemove)
                    .addComponent(btnLoad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Information Customer", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addGap(50, 50, 50))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomerMouseClicked
        // TODO add your handling code here:
        int row = tblCustomer.getSelectedRow();
        if (row == -1) {
            return;
        }
        KhachHang kh = this.rs.getAll().get(row);
        this.txtFullName.setText(kh.getFullname());
        this.txtPhone.setText(kh.getPhone());
        this.txtEmail.setText(kh.getEmail());
        this.txtBirthYear.setText(String.valueOf(kh.getBirth_year()));
        if (kh.getGender().equalsIgnoreCase("Nam")) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        this.txtAddress.setText(kh.getAddress());
        if (kh.getCustome_type_id() == 1) {
            rdoRegularCustomer.setSelected(true);
        } else {
            rdoVipCustomer.setSelected(true);
        }
        this.txtNote.setText(kh.getNote());
        this.txtCreatedAt.setText(String.valueOf(kh.getCreated_at()));
    }//GEN-LAST:event_tblCustomerMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String KH = txtSearch.getText();
        this.Search(KH);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        KhachHang kh = insert();
        if (this.checkvali()) {
            rs.add(kh);
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } else {
            return;
        }
        this.loadTable();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        int row = tblCustomer.getSelectedRow();
        if (row == -1) {
            return;
        }
        KhachHang kh = rs.getID(Integer.valueOf(tblCustomer.getValueAt(row, 0).toString()));
        int luachon = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật khách hàng này không ?", "Cảnh báo", JOptionPane.YES_NO_OPTION);
        if (luachon == JOptionPane.YES_NO_OPTION) {
            Update();
        } else {
            return;
        }
        this.loadTable();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        reset();
        JOptionPane.showMessageDialog(this, "Xóa form thành công!");
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        int row = tblCustomer.getSelectedRow();
        if (row == -1) {
            return;
        }
        KhachHang kh = rs.getID(Integer.valueOf(tblCustomer.getValueAt(row, 0).toString()));
        int luachon = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa khách hàng này không ?", "Cảnh báo", JOptionPane.YES_NO_OPTION);
        if (luachon == JOptionPane.YES_NO_OPTION) {
            Remove();
        } else {
            return;
        }
        this.loadTable();
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadActionPerformed
        loadTable();
    }//GEN-LAST:event_btnLoadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoRegularCustomer;
    private javax.swing.JRadioButton rdoVipCustomer;
    private javax.swing.JTable tblCustomer;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtBirthYear;
    private javax.swing.JTextField txtCreatedAt;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
