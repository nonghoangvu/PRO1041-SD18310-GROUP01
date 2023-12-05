/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package udpm.fpt.component;

import com.raven.datechooser.DateChooser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import static udpm.fpt.Application.getBean;
import udpm.fpt.model.Bill;
import udpm.fpt.model.BillDetails;
import udpm.fpt.model.DeliveryNote;
import udpm.fpt.repository.IDelivery_noteRespository;
import udpm.serviceDelivery.MailSender;
import udpm.serviceDelivery.Service;

/**
 *
 * @author PHONG PC
 */
public class DeliveryJFrame extends javax.swing.JFrame {

    /**
     * Creates new form DeliveryJFrame
     * @param idBill
     */
    public DeliveryJFrame(String idBill) {
        initComponents();
        setCCbox();
        this.idHoaDon = Integer.valueOf(idBill);
        init();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel15 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtSoLuong = new udpm.fpt.swing.TextField();
        txtTenKhachHang = new udpm.fpt.swing.TextField();
        txtDiaChi = new udpm.fpt.swing.TextField();
        txtSDT = new udpm.fpt.swing.TextField();
        txtTongTien = new udpm.fpt.swing.TextField();
        cbbDonViGiao = new udpm.fpt.swing.Combobox();
        txtPhiShip = new udpm.fpt.swing.TextField();
        textAreaScroll1 = new udpm.fpt.swing.TextAreaScroll();
        txtTenSanPham = new udpm.fpt.swing.TextArea();
        textAreaScroll2 = new udpm.fpt.swing.TextAreaScroll();
        txtGhiChu = new udpm.fpt.swing.TextArea();
        btnTaoPhieu = new udpm.fpt.swing.ButtonMessage();
        btnHuy = new udpm.fpt.swing.ButtonMessage();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(887, 517));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Create a delivery note", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txtSoLuong.setLabelText("Total Amount");

        txtTenKhachHang.setLabelText("Customer name");

        txtDiaChi.setLabelText("Delivery Address");

        txtSDT.setLabelText("Phone Number");

        txtTongTien.setLabelText("Cash on Delivery");

        cbbDonViGiao.setLabeText("Shipping Type");

        txtPhiShip.setLabelText("Shipping Cost");

        textAreaScroll1.setBackground(new java.awt.Color(255, 255, 255));
        textAreaScroll1.setLabelText("Product name");

        txtTenSanPham.setColumns(20);
        txtTenSanPham.setRows(5);
        textAreaScroll1.setViewportView(txtTenSanPham);

        textAreaScroll2.setBackground(new java.awt.Color(255, 255, 255));
        textAreaScroll2.setLabelText("Note");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        textAreaScroll2.setViewportView(txtGhiChu);

        btnTaoPhieu.setBackground(new java.awt.Color(0, 153, 255));
        btnTaoPhieu.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoPhieu.setText("Create a Delivery Note");
        btnTaoPhieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaoPhieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoPhieuActionPerformed(evt);
            }
        });

        btnHuy.setBackground(new java.awt.Color(204, 0, 0));
        btnHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnHuy.setText("Cancel");
        btnHuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnTaoPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textAreaScroll1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                    .addComponent(cbbDonViGiao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtPhiShip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textAreaScroll2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(textAreaScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(cbbDonViGiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTaoPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(txtPhiShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(textAreaScroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 879, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoPhieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoPhieuActionPerformed
        if(insert()){
            this.dispose();
        }
    }//GEN-LAST:event_btnTaoPhieuActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage btnHuy;
    private udpm.fpt.swing.ButtonMessage btnTaoPhieu;
    private udpm.fpt.swing.Combobox cbbDonViGiao;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private udpm.fpt.swing.TextAreaScroll textAreaScroll1;
    private udpm.fpt.swing.TextAreaScroll textAreaScroll2;
    private udpm.fpt.swing.TextField txtDiaChi;
    private udpm.fpt.swing.TextArea txtGhiChu;
    private udpm.fpt.swing.TextField txtPhiShip;
    private udpm.fpt.swing.TextField txtSDT;
    private udpm.fpt.swing.TextField txtSoLuong;
    private udpm.fpt.swing.TextField txtTenKhachHang;
    private udpm.fpt.swing.TextArea txtTenSanPham;
    private udpm.fpt.swing.TextField txtTongTien;
    // End of variables declaration//GEN-END:variables
    private final SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy");
    private Integer idkhachHang = null;
    private Long idSanPham = null;
    private Integer idHoaDon = null;
    private String maVanDon = null;
    private final Service sv = new Service();

    public void init() {
        this.setLocationRelativeTo(null);
        loadDataAndFillTableBill(String.valueOf(idHoaDon));
    }
    private Boolean isValidate() {
        if (txtDiaChi.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "The ID is empty!")
                    .showNotification();
            return false;
        } else if (txtPhiShip.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Shipping cost is empty!").showNotification();
            return false;
        } else if (txtTongTien.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Total amount is empty!").showNotification();
            return false;
        } else if (txtSDT.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Phone number  is empty!").showNotification();
            return false;
        } else if (txtTenKhachHang.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Customer name is empty!").showNotification();
            return false;
        } else if (txtTenSanPham.getText().trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Product is empty!").showNotification();
            return false;
        } else {
            try {
                if (Float.parseFloat(txtTongTien.getText()) < 4000) {
                    new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                            "Invalid volume!").showNotification();
                    return false;
                }
            } catch (NumberFormatException e) {
            }
        }
        return true;
    }

    public boolean insert() {
        String diaChi = txtDiaChi.getText();
        String ghiChu = txtGhiChu.getText();
        String SDT = txtSDT.getText();
        String soLuong = txtSoLuong.getText();
        String tenkhachHang = txtTenKhachHang.getText();
        String tenSanPham = sv.deleteLineBreak(txtTenSanPham.getText());
        String tongTien = txtTongTien.getText();
        int donviGiao = cbbDonViGiao.getSelectedIndex() + 1;
        maVanDon = sv.ranDom();
        String tienPhi = txtPhiShip.getText();
        if (this.isValidate()) {
            if (sv.insertDeli(new DeliveryNote(new Date(), tenkhachHang, diaChi, idHoaDon,
                    maVanDon, donviGiao, ghiChu,
                    Double.valueOf(tienPhi), 1, SDT, tenSanPham,
                    Integer.valueOf(soLuong), Double.valueOf(tongTien)))) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Add success!").showNotification();
                sv.updateQuanlityProduct(idSanPham, Integer.parseInt(soLuong));
                String message = "Order confirmed" + "<br>"
                        + "Tracking code:" + maVanDon + "<br>"
                        + "Product name: <br>" + sv.addLineBreakInMessgase(tenSanPham) + "<br>"
                        + "Confirmation date" + new Date();
                new Thread(() -> {
                    System.out.println(new MailSender().sendMail("phongvvutuan@gmail.com", "vutuanphong1782004@gmail.com", "Thông tin đơn hàng của bạn", message));
                }).start();
            } else {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Add failure!").showNotification();
                return false;
            }
            return true;
        }
        return false;
    }

    public void loadDataAndFillTableBill(String idBill) {
        CompletableFuture<List<BillDetails>> future = this.sv.loadAsyncBillDetailByIdBill(idBill);
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                setFrom(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }
    public void setCCbox(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) this.cbbDonViGiao.getModel();
        model.removeAllElements();
        model.addElement("Express Delivery");
        model.addElement("Speedy Logistics");
        model.addElement("Rapid Shippers");
        model.addElement("Swift Couriers");
        model.addElement("Quick Dispatch");
    }
    public void setFrom(List<BillDetails> list) {
            List<BillDetails> billDetail = list;
            BillDetails b = list.get(0);
            txtTenKhachHang.setText(b.getBill_id().getCustomerId().getFullname());
            txtDiaChi.setText(b.getBill_id().getCustomerId().getAddress());
            txtSDT.setText(b.getBill_id().getCustomerId().getPhone());
            txtTongTien.setText(String.valueOf(b.getPrice()));
            txtTenSanPham.setText("");
            if (cbbDonViGiao.getSelectedIndex() == 0) {
                txtPhiShip.setText("18.000");
            }
            int soLuong = 0;
            for (BillDetails getbillDetail : billDetail) {
                if (Objects.equals(getbillDetail.getBill_id().getId(), idHoaDon)) {
                    txtTenSanPham.append(getbillDetail.getMilk_id() == null ? "" : getbillDetail.getMilk_id().getProduct_name() + "\n");
                    if (getbillDetail.getMilk_id() != null) {
                        this.idSanPham = getbillDetail.getMilk_id().getId();
                    }
                    soLuong = soLuong + getbillDetail.getQuantity();
                }
            }
            txtSoLuong.setText(String.valueOf(soLuong));
    }
}
