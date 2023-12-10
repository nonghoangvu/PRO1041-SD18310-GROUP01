/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package udpm.fpt.component;

import com.raven.datechooser.DateChooser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import udpm.fpt.form.DeliveryNoteForm2;
import udpm.fpt.main.Main;
import udpm.fpt.model.DeliveryNote;
import udpm.fpt.model.Status;
import udpm.fpt.model.TransportUnit;
import udpm.fpt.model.User;
import udpm.serviceDelivery.NumberFilter;
import udpm.serviceDelivery.Service;

/**
 *
 * @author PHONG PC
 */
public class UpdateDelivery extends javax.swing.JFrame {

    /**
     * Creates new form UpdateDelivery
     */
    public UpdateDelivery(DeliveryNoteForm2 deliveryNoteFrom, Long idPhieu, String maVanDon, User user, Main main) {
        initComponents();
        this.idPhieu = idPhieu;
        this.maVanDon = maVanDon;
        this.deliveryNoteFrom = deliveryNoteFrom;
        this.main = main;
        this.user = user;
        UpdateDelivery();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTenKhachHang = new udpm.fpt.swing.TextField();
        txtDiaChi = new udpm.fpt.swing.TextField();
        txtSDT = new udpm.fpt.swing.TextField();
        txtTongTien = new udpm.fpt.swing.TextField();
        textAreaScroll1 = new udpm.fpt.swing.TextAreaScroll();
        txtGhiChu = new udpm.fpt.swing.TextArea();
        cbbDonViGiao = new udpm.fpt.swing.Combobox();
        txtEstimatedDeliveryDate = new udpm.fpt.swing.TextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtIDHoaDon = new udpm.fpt.swing.TextField();
        txtNgayTao = new udpm.fpt.swing.TextField();
        txtSoLuong = new udpm.fpt.swing.TextField();
        cbbTrangThai = new udpm.fpt.swing.Combobox();
        txtTienPhi = new udpm.fpt.swing.TextField();
        textAreaScroll2 = new udpm.fpt.swing.TextAreaScroll();
        txtTenSanPham = new udpm.fpt.swing.TextArea();
        btnSave = new udpm.fpt.swing.ButtonMessage();
        buttonMessage1 = new udpm.fpt.swing.ButtonMessage();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Thông tin đơn hàng");

        txtTenKhachHang.setLabelText("Customer name");

        txtDiaChi.setLabelText("Delivery address");

        txtSDT.setEditable(false);
        txtSDT.setLabelText("Phone Number");

        txtTongTien.setEditable(false);
        txtTongTien.setLabelText("Cash on delivery");

        textAreaScroll1.setBackground(new java.awt.Color(255, 255, 255));
        textAreaScroll1.setLabelText("Note");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        textAreaScroll1.setViewportView(txtGhiChu);

        cbbDonViGiao.setLabeText("Type of transport");
        cbbDonViGiao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbDonViGiaoActionPerformed(evt);
            }
        });

        txtEstimatedDeliveryDate.setLabelText("Estimated delivery date");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textAreaScroll1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addComponent(cbbDonViGiao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEstimatedDeliveryDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(txtEstimatedDeliveryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbDonViGiao, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(textAreaScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Thông tin sản phẩm");

        txtIDHoaDon.setEditable(false);
        txtIDHoaDon.setLabelText("Bill code");

        txtNgayTao.setLabelText("Creation date");

        txtSoLuong.setLabelText("Total amount");

        cbbTrangThai.setLabeText("Status");

        txtTienPhi.setLabelText("Shipping code");

        textAreaScroll2.setBackground(new java.awt.Color(255, 255, 255));
        textAreaScroll2.setLabelText("Product name");

        txtTenSanPham.setColumns(20);
        txtTenSanPham.setRows(5);
        textAreaScroll2.setViewportView(txtTenSanPham);

        btnSave.setBackground(new java.awt.Color(0, 153, 255));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        buttonMessage1.setBackground(new java.awt.Color(204, 0, 0));
        buttonMessage1.setForeground(new java.awt.Color(255, 255, 255));
        buttonMessage1.setText(" Cancel");
        buttonMessage1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        buttonMessage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIDHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienPhi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textAreaScroll2, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(buttonMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIDHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jLabel2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textAreaScroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(cbbTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTienPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbDonViGiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbDonViGiaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbDonViGiaoActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (this.updateDeliveryNote(idPhieu, maVanDon)) {
            this.dispose();
            deliveryNoteFrom.loadDataAndFillTableDeli();
            sv.historyUpdate("The "+idPhieu.toString()+" delivery has been updated", user);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void buttonMessage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_buttonMessage1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage btnSave;
    private udpm.fpt.swing.ButtonMessage buttonMessage1;
    private udpm.fpt.swing.Combobox cbbDonViGiao;
    private udpm.fpt.swing.Combobox cbbTrangThai;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private udpm.fpt.swing.TextAreaScroll textAreaScroll1;
    private udpm.fpt.swing.TextAreaScroll textAreaScroll2;
    private udpm.fpt.swing.TextField txtDiaChi;
    private udpm.fpt.swing.TextField txtEstimatedDeliveryDate;
    private udpm.fpt.swing.TextArea txtGhiChu;
    private udpm.fpt.swing.TextField txtIDHoaDon;
    private udpm.fpt.swing.TextField txtNgayTao;
    private udpm.fpt.swing.TextField txtSDT;
    private udpm.fpt.swing.TextField txtSoLuong;
    private udpm.fpt.swing.TextField txtTenKhachHang;
    private udpm.fpt.swing.TextArea txtTenSanPham;
    private udpm.fpt.swing.TextField txtTienPhi;
    private udpm.fpt.swing.TextField txtTongTien;
    // End of variables declaration//GEN-END:variables
    public void UpdateDelivery() {
        this.setLocationRelativeTo(null);
        ngayTaoDon();
        UnitTranport();
        cbbtrangThai();
        OnlyNumberTextField();
    }
    SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
    DateChooser datechooser = new DateChooser();
    Service sv = new Service();
    Long idPhieu = null;
    String maVanDon = null;
    DeliveryNoteForm2 deliveryNoteFrom;
    private User user;
    private Main main;
    private final DateChooser estimatedtime = new DateChooser();

    public void ngayTaoDon() {
        datechooser.setTextField(txtNgayTao);
        datechooser.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        datechooser.setLabelCurrentDayVisible(false);
        estimatedtime.setTextField(txtEstimatedDeliveryDate);
        estimatedtime.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        estimatedtime.setLabelCurrentDayVisible(true);
    }

    public void setFrom(DeliveryNote list) {
        DeliveryNote b = list;
        txtTenKhachHang.setText(b.getCustomer_name());
        txtDiaChi.setText(b.getAddress());
        txtSDT.setText(b.getSdt());
        txtTienPhi.setText(String.valueOf(b.getShippingCost()));
        txtIDHoaDon.setText(b.getBill_id2() == null ? "" : String.valueOf(b.getBill_id2().getId()));
        cbbDonViGiao.setSelectedItem(b.getTransport_unit_id2().getTransportUnitName());
        txtTenSanPham.setText(sv.addLineBreak(b.getMilk_name()));
        txtGhiChu.setText(b.getNote());
        txtNgayTao.setText(simple.format(b.getCreationdate()));
        txtTongTien.setText(String.valueOf(b.getTotal_amount()));
        txtSoLuong.setText(String.valueOf(b.getQuantity()));
        cbbDonViGiao.setSelectedItem(b.getTransport_unit_id2().getTransportUnitName());
        cbbTrangThai.setSelectedItem(b.getStatus_id2().getStatusName());
        txtNgayTao.setText(b.getCreationdate()==null? simple.format(new Date()) : simple.format(b.getCreationdate()));
        txtEstimatedDeliveryDate.setText(b.getEstimatedtime()==null? "" :simple.format(b.getEstimatedtime()));
    }

    public void cbbtrangThai() {
        DefaultComboBoxModel cbb = (DefaultComboBoxModel) cbbTrangThai.getModel();
        cbb.removeAllElements();
        List<Status> listStatus = sv.findAllStatus();
        for (Status st : listStatus) {
            cbb.addElement(st.getStatusName());
        }

    }

    public boolean updateDeliveryNote(Long productId, String maVanDon) {
        String diaChi = txtDiaChi.getText();
        String ghiChu = txtGhiChu.getText();
        String ngayGiao = txtNgayTao.getText();
        String SDT = txtSDT.getText();
        String soLuong = txtSoLuong.getText();
        String tenkhachHang = txtTenKhachHang.getText();
        String tenSanPham = txtTenSanPham.getText();
        String tongtien = txtTongTien.getText();
        String tienShip = txtTienPhi.getText();
        String maHoaDon = txtIDHoaDon.getText();
        int donviGiao = cbbDonViGiao.getSelectedIndex() + 1;
        System.out.println(donviGiao);
        int trangThai = cbbTrangThai.getSelectedIndex() + 1;
        try {
            if (isValidate()) {
                if (sv.updateDeliveryNote(productId, maVanDon, new DeliveryNote(simple.parse(ngayGiao), tenkhachHang, diaChi, Integer.valueOf(maHoaDon),
                        maVanDon, donviGiao, ghiChu,
                        Double.valueOf(tienShip), trangThai, SDT, simple.parse(txtEstimatedDeliveryDate.getText()), tenSanPham,
                        Integer.valueOf(soLuong), Double.valueOf(tongtien)))) {
                    new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                            "Update success!").showNotification();
                    return true;
                } else {
                    new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                            "Update failed!").showNotification();
                    return false;
                }
            } else {
                return false;
            }
        } catch (ParseException ex) {
            return true;
        }
    }

    public void UnitTranport() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) this.cbbDonViGiao.getModel();
        model.removeAllElements();
        List<TransportUnit> tpu = sv.findAllTranportsUnit();
        for (TransportUnit tp : tpu) {
            model.addElement(tp.getTransportUnitName());
        }
    }

    public void OnlyNumberTextField() {
        ((AbstractDocument) txtTienPhi.getDocument()).setDocumentFilter(new NumberFilter());
        ((AbstractDocument) txtSoLuong.getDocument()).setDocumentFilter(new NumberFilter());
        ((AbstractDocument) txtTongTien.getDocument()).setDocumentFilter(new NumberFilter());
    }

    private Boolean isValidate() {
        String dateString1 = txtEstimatedDeliveryDate.getText();
        String dateString2 = txtNgayTao.getText();
        LocalDate date1 = LocalDate.parse(dateString1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date2 = LocalDate.parse(dateString2, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate date2PlusOneDay = date2.plusDays(1);
        // So sánh ngày
        if (date1.isEqual(date2PlusOneDay) || date1.isBefore(date2PlusOneDay)) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Estimated delivery date must be 1 day greater than today!")
                    .showNotification();
            return false;
        }
        if (txtDiaChi.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "The Address is empty!")
                    .showNotification();
            txtDiaChi.requestFocus();
            return false;
        } else if (txtTienPhi.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Shipping cost is empty!").showNotification();
            txtTienPhi.requestFocus();
            return false;
        } else if (txtTongTien.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Total amount is empty!").showNotification();
            txtTongTien.requestFocus();
            return false;
        } else if (txtSDT.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Phone number  is empty!").showNotification();
            txtSDT.requestFocus();
            return false;
        } else if (txtTenKhachHang.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Customer name is empty!").showNotification();
            txtTenKhachHang.requestFocus();
            return false;
        } else if (txtTenSanPham.getText().trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Product is empty!").showNotification();
            txtTenSanPham.requestFocus();
            return false;
        }
        return true;
    }
}
