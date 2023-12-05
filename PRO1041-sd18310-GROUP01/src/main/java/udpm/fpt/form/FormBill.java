/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package udpm.fpt.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import static udpm.fpt.Application.getBean;
import udpm.fpt.component.ChooseCustomersForm;
import udpm.fpt.component.DeliveryJFrame;
import udpm.fpt.component.Notification;
import udpm.fpt.main.Main;
import udpm.fpt.model.Bill;
import udpm.fpt.model.BillDetails;
import udpm.fpt.model.Customer;
import udpm.fpt.model.Milk;
import udpm.fpt.model.User;
import udpm.fpt.repository.IBillDetails_Respository;
import udpm.fpt.repository.IBill_Respository;
import udpm.fpt.service.BillService;
import udpm.fpt.swing.table.TableCustom;

/**
 *
 * @author PHONG PC
 */
public final class FormBill extends javax.swing.JPanel {

    private final BillService sv = new BillService();
    private List<Milk> SAN_PHAM_REPO = new ArrayList();
    private final List<Bill> HOA_DON_REPO = new ArrayList();
    private final List<BillDetails> HDCT_REPO = new ArrayList();
    IBill_Respository bill = getBean(IBill_Respository.class);
    IBillDetails_Respository billDetail = getBean(IBillDetails_Respository.class);
    User user;
    Main main;

    /**
     * Creates new form FromBill
     *
     * @param user
     */
    public FormBill(User user) {
        initComponents();
        loadAndFillMilk();
        cbbPaymentMethods();
        this.user = user;
        loadHoaDonChiTiet(0);
        TableCustom.apply(jScrollPane2, TableCustom.TableType.DEFAULT);
        TableCustom.apply(jScrollPane3, TableCustom.TableType.DEFAULT);
        TableCustom.apply(jScrollPane1, TableCustom.TableType.DEFAULT);
    }

    public void setCustomer(Customer ct) {
        txtCustomerName.setText(ct.getFullname());
        txtIDCustomer.setText(String.valueOf(ct.getId()));
    }

    private void loadSanPham(List<Milk> milk) {
        DefaultTableModel model = (DefaultTableModel) tblProduct.getModel();
        model.setColumnCount(0);
        model.addColumn("Product code");
        model.addColumn("Product name");
        model.addColumn("Quantity exists");
        model.addColumn("Current prices");

        model.setRowCount(0);
        for (Milk sanPham : milk) {
            Object[] row = new Object[]{
                sanPham.getId(), sanPham.getProduct_name(),
                sanPham.getAmount(), sanPham.getPrice()
            };

            model.addRow(row);
        }
    }

    private void loadHoaDon(List<Bill> list) {
        DefaultTableModel model = (DefaultTableModel) tblBill.getModel();
        model.setColumnCount(0);
        model.addColumn("Code Bill");
        model.addColumn("Payment status");
        model.setRowCount(0);
        List<Bill> hdctt = list.stream().filter(hd -> hd.getPayment_status().equals("Pending") && hd.getCustomerId() == null).toList();
        for (Bill hoaDon : hdctt) {
            Object[] row = new Object[]{
                hoaDon.getId(),
                hoaDon.getPayment_status()
            };
            model.addRow(row);
        }
    }

    private void loadHoaDonChiTiet(Integer maHoaDon) {
        List<BillDetails> listChiTiet = HDCT_REPO.stream().filter(ct -> Objects.equals(ct.getBill_id().getId(), maHoaDon)).toList();
        DefaultTableModel model = (DefaultTableModel) tblShoppingCart.getModel();
        model.setColumnCount(0);
        model.addColumn("Code Bill");
        model.addColumn("Product code");
        model.addColumn("Quantity");
        model.addColumn("Unit price");
        model.addColumn("Into money");
        model.setRowCount(0);
        for (BillDetails chiTiet : listChiTiet) {
            Object[] row = new Object[]{
                chiTiet.getBill_id().getId(),
                chiTiet.getMilk_id().getId(),
                chiTiet.getQuantity(),
                chiTiet.getPrice(),
                chiTiet.getPrice() * chiTiet.getQuantity()
            };
            model.addRow(row);
        }
    }

    public void loadAndFillMilk() {
        CompletableFuture<List<Milk>> future = this.sv.loadMilk();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                this.loadSanPham(data);
                SAN_PHAM_REPO = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public void addBill(Bill bill) {
        this.bill.save(bill);
    }

    public void addBillDetail(BillDetails billDetails) {
        this.billDetail.save(billDetails);
    }

    public void cbbPaymentMethods() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbPaymentMethods.getModel();
        model.removeAllElements();
        model.addElement("Cash payment");
        model.addElement("Online payment");
    }

    public void resetLable() {
        txtCustomerName.setText("");
        txtIDCustomer.setText("");
        txtNote.setText("");
    }

    public boolean insert(String shoppingMethod) {
        if (tblBill.getSelectedRow() == -1) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The select bill!").showNotification();
            return false;
        }
        int selectedRow = tblBill.getSelectedRow();
        if (selectedRow == -1) {
            return false;
        }
        Integer maHoaDon = Integer.valueOf(tblBill.getValueAt(selectedRow, 0).toString());
        double total = 0;
        boolean check = true;
        for (Bill hd : HOA_DON_REPO) {
            if (hd.getId().equals(maHoaDon)) {
                hd.setPayment_status("Paid");
                hd.setShopping_method(shoppingMethod);
                hd.setNotes(txtNote.getText());
                hd.setStaff_id(user.getId());
                if (shoppingMethod.equals("delivery")) {
                    Customer sc = new Customer();
                    sc.setId(Integer.valueOf(txtIDCustomer.getText()));
                    hd.setCustomerId(sc);
                    hd.setPayment_status("Pending");
                }
                if (tblShoppingCart.getRowCount() == 0) {
                    new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                            "The product is empty!").showNotification();
                    return false;
                }
                for (int i = 0; i < tblShoppingCart.getRowCount(); i++) {
                    total += Double.parseDouble(tblShoppingCart.getValueAt(i, 4).toString());
                    hd.setTotalAmountAfterTax(total);
                }
                bill.save(hd);
                break;
            }
        }

        List<BillDetails> listChiTiet = HDCT_REPO.stream().filter(ct -> Objects.equals(ct.getBill_id().getId(), maHoaDon)).toList();
        if (listChiTiet != null) {
            billDetail.saveAll(listChiTiet);
            loadHoaDonChiTiet(0);
            if (shoppingMethod.equals("directly")) {
                for (BillDetails ct : listChiTiet) {
                    sv.updateProduct(ct.getQuantity(), ct.getMilk_id().getId());
                }
            }
            new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                    "Success!").showNotification();
        }
        loadHoaDon(HOA_DON_REPO);

        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBill = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblShoppingCart = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbbPaymentMethods = new udpm.fpt.swing.Combobox();
        textAreaScroll1 = new udpm.fpt.swing.TextAreaScroll();
        txtNote = new udpm.fpt.swing.TextArea();
        txtIDCustomer = new udpm.fpt.swing.TextField();
        txtCustomerName = new udpm.fpt.swing.TextField();
        buttonMessage1 = new udpm.fpt.swing.ButtonMessage();
        btnTaoHoaDon = new udpm.fpt.swing.ButtonMessage();
        buttonMessage2 = new udpm.fpt.swing.ButtonMessage();
        btnChooseCustomer = new udpm.fpt.swing.Button();
        buttonMessage3 = new udpm.fpt.swing.ButtonMessage();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1489, 880));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1527, 824));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill code", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBillMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBill);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Shopping Cart");

        tblShoppingCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblShoppingCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblShoppingCartMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblShoppingCart);

        jLabel3.setText("Product List");

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Mã sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProduct);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("Create bill");

        cbbPaymentMethods.setLabeText("Payment method");
        cbbPaymentMethods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbPaymentMethodsActionPerformed(evt);
            }
        });

        textAreaScroll1.setBackground(new java.awt.Color(255, 255, 255));
        textAreaScroll1.setLabelText("Note");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        textAreaScroll1.setViewportView(txtNote);

        txtIDCustomer.setLabelText("id customer");

        txtCustomerName.setLabelText("Customer name");

        buttonMessage1.setBackground(new java.awt.Color(255, 51, 51));
        buttonMessage1.setForeground(new java.awt.Color(255, 255, 255));
        buttonMessage1.setText("Cancel");
        buttonMessage1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonMessage1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage1ActionPerformed(evt);
            }
        });

        btnTaoHoaDon.setBackground(new java.awt.Color(51, 204, 255));
        btnTaoHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoHoaDon.setText("Create bill");
        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        buttonMessage2.setBackground(new java.awt.Color(0, 204, 51));
        buttonMessage2.setForeground(new java.awt.Color(255, 255, 255));
        buttonMessage2.setText(" Pay");
        buttonMessage2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonMessage2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage2ActionPerformed(evt);
            }
        });

        btnChooseCustomer.setBackground(new java.awt.Color(102, 255, 255));
        btnChooseCustomer.setForeground(new java.awt.Color(255, 255, 255));
        btnChooseCustomer.setText("Choose customer");
        btnChooseCustomer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChooseCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseCustomerActionPerformed(evt);
            }
        });

        buttonMessage3.setBackground(new java.awt.Color(0, 204, 51));
        buttonMessage3.setForeground(new java.awt.Color(255, 255, 255));
        buttonMessage3.setText("Delivery");
        buttonMessage3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonMessage3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMessage3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbPaymentMethods, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textAreaScroll1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                            .addComponent(txtIDCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCustomerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buttonMessage2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(buttonMessage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonMessage3, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
                                .addGap(32, 32, 32)))
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(btnChooseCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(53, 53, 53)
                .addComponent(btnChooseCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIDCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(cbbPaymentMethods, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(textAreaScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonMessage3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonMessage2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(140, 140, 140))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 186, Short.MAX_VALUE)
        );

        jLabel2.setText("Pending Invoice");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1489, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1489, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 15, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillMouseClicked
        int selectedRow = tblBill.getSelectedRow();
        Integer maHoaDon = Integer.valueOf(tblBill.getValueAt(selectedRow, 0).toString());
        loadHoaDonChiTiet(maHoaDon);
    }//GEN-LAST:event_tblBillMouseClicked

    private void tblShoppingCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblShoppingCartMouseClicked

    }//GEN-LAST:event_tblShoppingCartMouseClicked

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseClicked
        int selectedProduct = tblProduct.getSelectedRow();
        int selectedBill = tblBill.getSelectedRow();
        if (selectedProduct == -1 || selectedBill == -1) {
            return;
        }
        Long maSanPham = Long.valueOf(tblProduct.getValueAt(selectedProduct, 0).toString());
        Milk sp = SAN_PHAM_REPO.stream().filter(id -> Objects.equals(id.getId(), maSanPham)).findFirst().orElse(null);
        int selectedHoaDon = tblBill.getSelectedRow();
        Integer maHoaDon = Integer.valueOf(tblBill.getValueAt(selectedHoaDon, 0).toString());
        Milk spbill = new Milk();
        spbill.setId(maSanPham);
        BillDetails hdct = new BillDetails();
        Bill bil = new Bill();
        bil.setId(maHoaDon);
        hdct.setBill_id(bil);
        hdct.setMilk_id(spbill);
        hdct.setPrice(Double.valueOf(sp.getPrice()));
        hdct.setQuantity(1);
        boolean isNotExisted = true;
        double toTal = 0;
        for (BillDetails h : HDCT_REPO) {
            if (h.getBill_id().getId().equals(maHoaDon) && h.getMilk_id().getId().equals(maSanPham)) {
                h.setQuantity(h.getQuantity() + hdct.getQuantity());
                isNotExisted = false;
                break;
            }
        }

        for (Milk m : SAN_PHAM_REPO) {
            if (m.getId().equals(maSanPham)) {
                m.setAmount(m.getAmount() - hdct.getQuantity());
            }
        }
        if (isNotExisted) {
            HDCT_REPO.add(hdct);
        }

        loadSanPham(SAN_PHAM_REPO);
        loadHoaDonChiTiet(maHoaDon);
    }//GEN-LAST:event_tblProductMouseClicked

    private void cbbPaymentMethodsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbPaymentMethodsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbPaymentMethodsActionPerformed

    private void buttonMessage1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage1ActionPerformed
        int row = tblBill.getSelectedRow();
        if (row == -1) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Select the invoice to cancel!").showNotification();
            return;
        }
        Integer idhoaDon = Integer.valueOf(tblBill.getValueAt(row, 0).toString());
        if (HOA_DON_REPO == null) {
            return;
        }
        Iterator<Bill> iterator = HOA_DON_REPO.iterator();
        while (iterator.hasNext()) {
            Bill b = iterator.next();
            if (b.getId().equals(idhoaDon)) {
                iterator.remove();
            }
        }
        loadHoaDon(HOA_DON_REPO);
        loadHoaDonChiTiet(100);
    }//GEN-LAST:event_buttonMessage1ActionPerformed

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        System.out.println(user.getId());
        Bill hoaDon = new Bill();
        hoaDon.setId(Integer.valueOf(sv.generateRandomNumber()));
        hoaDon.setCreatedAt(new Date());
        hoaDon.setStaff_id(1);
        hoaDon.setPayment_status("Pending");
        //hoaDon.setShopping_method(cbbShoppingMethod.getSelectedItem().toString());
        hoaDon.setPayment_method(cbbPaymentMethods.getSelectedItem().toString());
        if (cbbPaymentMethods.getSelectedIndex() == 1) {
            Customer sus = new Customer();
            sus.setId(Integer.valueOf(txtIDCustomer.getText()));
            hoaDon.setCustomerId(sus);
        }
        HOA_DON_REPO.add(hoaDon);
        loadHoaDon(HOA_DON_REPO);
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnChooseCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseCustomerActionPerformed
        ChooseCustomersForm choose = new ChooseCustomersForm(this);
        choose.setVisible(true);
    }//GEN-LAST:event_btnChooseCustomerActionPerformed

    private void buttonMessage2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage2ActionPerformed
        if (this.insert("directly")) {
            this.resetLable();
        }
    }//GEN-LAST:event_buttonMessage2ActionPerformed

    private void buttonMessage3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage3ActionPerformed
        if (txtIDCustomer.getText().trim().isEmpty() || txtCustomerName.getText().trim().isEmpty()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The Customer is empty!").showNotification();
            return;
        }
        int row = tblBill.getSelectedRow();
        String idHoaDon = tblBill.getValueAt(row, 0).toString();
        System.out.println(idHoaDon);
        if (this.insert("delivery")) {
            System.out.println(idHoaDon);
            DeliveryJFrame delivery = new DeliveryJFrame(idHoaDon);
            delivery.setVisible(true);
            this.resetLable();
        }
    }//GEN-LAST:event_buttonMessage3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button btnChooseCustomer;
    private udpm.fpt.swing.ButtonMessage btnTaoHoaDon;
    private udpm.fpt.swing.ButtonMessage buttonMessage1;
    private udpm.fpt.swing.ButtonMessage buttonMessage2;
    private udpm.fpt.swing.ButtonMessage buttonMessage3;
    private udpm.fpt.swing.Combobox cbbPaymentMethods;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblBill;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblShoppingCart;
    private udpm.fpt.swing.TextAreaScroll textAreaScroll1;
    private udpm.fpt.swing.TextField txtCustomerName;
    private udpm.fpt.swing.TextField txtIDCustomer;
    private udpm.fpt.swing.TextArea txtNote;
    // End of variables declaration//GEN-END:variables

}
