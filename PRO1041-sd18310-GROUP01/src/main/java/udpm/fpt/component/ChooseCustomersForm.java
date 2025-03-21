/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package udpm.fpt.component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import udpm.fpt.form.FormBill;
import udpm.fpt.model.Customer;
import udpm.fpt.service.BillService;

/**
 *
 * @author PHONG PC
 */
public final class ChooseCustomersForm extends javax.swing.JFrame {
    FormBill form;
    private final BillService sv = new BillService();

    /**
     * Creates new form ChooseCustomersForm
     * @param bill
     */
    public ChooseCustomersForm(FormBill bill) {
        initComponents();
        this.setLocationRelativeTo(null);
        loadDataCustomer();
        form =bill;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

    public void loadDataCustomer() {
        CompletableFuture<List<Customer>> future = this.sv.loadCustomer();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                loadCustomer(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }
    public void SearchCustomer(String name) {
        CompletableFuture<List<Customer>> future = this.sv.searchCustomer(name);
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                loadCustomer(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }
    private void loadCustomer(List<Customer> list) {
        DefaultTableModel model = (DefaultTableModel) tblCustomer.getModel();

        model.setColumnCount(0);
        model.addColumn("Mã khách hàng");
        model.addColumn("Tên khách hàng");
        model.setRowCount(0);
        for (Customer hoaDon : list) {
            Object[] row = new Object[]{
                hoaDon.getId(),
                hoaDon.getFullname(),
                hoaDon.getPhone(),};

            model.addRow(row);
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        txtSearchCustomer = new udpm.fpt.swing.TextField();
        btnC = new udpm.fpt.swing.ButtonMessage();
        btnChon = new udpm.fpt.swing.ButtonMessage();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblCustomer);

        txtSearchCustomer.setLabelText("Search by name");
        txtSearchCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchCustomerActionPerformed(evt);
            }
        });

        btnC.setBackground(new java.awt.Color(0, 153, 255));
        btnC.setForeground(new java.awt.Color(255, 255, 255));
        btnC.setText("Search");
        btnC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCActionPerformed(evt);
            }
        });

        btnChon.setBackground(new java.awt.Color(0, 153, 255));
        btnChon.setForeground(new java.awt.Color(255, 255, 255));
        btnChon.setText("Select");
        btnChon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSearchCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnC, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnChon, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearchCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchCustomerActionPerformed
        
    }//GEN-LAST:event_txtSearchCustomerActionPerformed

    private void btnCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCActionPerformed
        String name = txtSearchCustomer.getText();
        SearchCustomer(name);
    }//GEN-LAST:event_btnCActionPerformed

    private void btnChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonActionPerformed
       int row = tblCustomer.getSelectedRow();
        if (row == -1) {
            return;
        }
        Customer ct = new Customer();
        ct.setId(Integer.valueOf(tblCustomer.getValueAt(row, 0).toString()));
        ct.setFullname(tblCustomer.getValueAt(row, 1).toString());     
        form.setCustomer(ct);
        this.dispose();
    }//GEN-LAST:event_btnChonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage btnC;
    private udpm.fpt.swing.ButtonMessage btnChon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblCustomer;
    private udpm.fpt.swing.TextField txtSearchCustomer;
    // End of variables declaration//GEN-END:variables
}
