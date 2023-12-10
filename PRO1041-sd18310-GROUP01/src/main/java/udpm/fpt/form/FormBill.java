/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package udpm.fpt.form;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import static udpm.fpt.Application.getBean;
import udpm.fpt.component.ChooseCustomersForm;
import udpm.fpt.component.DeliveryJFrame;
import udpm.fpt.component.Notification;
import udpm.fpt.main.Main;
import udpm.fpt.model.Bill;
import udpm.fpt.model.BillDetails;
import udpm.fpt.model.Customer;
import udpm.fpt.model.Milk;
import udpm.fpt.model.SaleBill1;
import udpm.fpt.model.User;
import udpm.fpt.repository.IBillDetails_Respository;
import udpm.fpt.repository.IBill_Respository;
import udpm.fpt.service.BillService;
import udpm.fpt.swing.table.TableCustom;
import udpm.serviceDelivery.NumberFilter;

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
    private List<SaleBill1> saleBillList = new ArrayList<>();
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
        TableCustom.apply(jScrollPane5, TableCustom.TableType.DEFAULT);
        OnlyNumberTextField();
        loadAndFillSaleBill();
        txtExcessMoney.setText("0");
        theAmountTheCustomerGives();
        excessMoney();
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

    private void loadSaleBill(List<SaleBill1> saleBill) {
        DefaultTableModel model = (DefaultTableModel) tblSaleBill.getModel();
        model.setRowCount(0);
        for (SaleBill1 sanPham : saleBill) {
            Object[] row = new Object[]{
                sanPham.getId(),
                sanPham.getSaleEvent(),
                sanPham.getDiscountConditions(),
                sanPham.getPercentDecrease(),};

            model.addRow(row);
        }
    }

    public void loadAndFillSaleBill() {
        CompletableFuture<List<SaleBill1>> future = this.sv.findAllSaleBill();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                this.loadSaleBill(data);
                this.saleBillList = data;
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
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
        txtTheAmountTheCustomerGives.setText("");
        txtMoneyPaid.setText("");
        txtExcessMoney.setText("");
        txtTotalAmount.setText("");
    }

    public void OnlyNumberTextField() {
        ((AbstractDocument) txtMoneyPaid.getDocument()).setDocumentFilter(new NumberFilter());
        ((AbstractDocument) txtTheAmountTheCustomerGives.getDocument()).setDocumentFilter(new NumberFilter());
    }

    public boolean insert(String shoppingMethod) {
        int selectedRow = tblBill.getSelectedRow();
        if (selectedRow == -1) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The select bill!").showNotification();
            return false;
        }
        Integer maHoaDon = Integer.valueOf(tblBill.getValueAt(selectedRow, 0).toString());
        boolean check = true;
        for (Bill hd : HOA_DON_REPO) {
            if (hd.getId().equals(maHoaDon)) {
                hd.setPayment_status("Paid");
                hd.setShopping_method(shoppingMethod);
                hd.setNotes("");
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
                    hd.setTotalAmountAfterTax(Double.valueOf(txtMoneyPaid.getText()));
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
        txtIDCustomer = new udpm.fpt.swing.TextField();
        txtCustomerName = new udpm.fpt.swing.TextField();
        buttonMessage1 = new udpm.fpt.swing.ButtonMessage();
        btnTaoHoaDon = new udpm.fpt.swing.ButtonMessage();
        btnPay = new udpm.fpt.swing.ButtonMessage();
        btnChooseCustomer = new udpm.fpt.swing.Button();
        buttonMessage3 = new udpm.fpt.swing.ButtonMessage();
        txtTotalAmount = new udpm.fpt.swing.TextField();
        txtMoneyPaid = new udpm.fpt.swing.TextField();
        txtTheAmountTheCustomerGives = new udpm.fpt.swing.TextField();
        txtExcessMoney = new udpm.fpt.swing.TextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSaleBill = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

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
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
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

        txtIDCustomer.setEditable(false);
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

        btnPay.setBackground(new java.awt.Color(0, 204, 51));
        btnPay.setForeground(new java.awt.Color(255, 255, 255));
        btnPay.setText(" Pay");
        btnPay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
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

        txtTotalAmount.setEditable(false);
        txtTotalAmount.setLabelText("Total Amount");

        txtMoneyPaid.setEditable(false);
        txtMoneyPaid.setLabelText("The amount customers have to pay");

        txtTheAmountTheCustomerGives.setLabelText("The amount the customer gives");

        txtExcessMoney.setEditable(false);
        txtExcessMoney.setLabelText("Excess money");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnChooseCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtExcessMoney, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTheAmountTheCustomerGives, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbPaymentMethods, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtIDCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCustomerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTotalAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 396, Short.MAX_VALUE))
                            .addComponent(txtMoneyPaid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonMessage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonMessage3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(btnChooseCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIDCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbPaymentMethods, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(txtTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(txtMoneyPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(txtTheAmountTheCustomerGives, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(txtExcessMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonMessage1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonMessage3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(162, 162, 162))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        tblSaleBill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Discount conditions", "Percent decrease"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSaleBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSaleBillMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblSaleBillMouseEntered(evt);
            }
        });
        jScrollPane5.setViewportView(tblSaleBill);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel2.setText("Pending Invoice");

        jLabel6.setText("Sale event for bill");

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
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 373, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)))
                        .addGap(16, 16, 16)))
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 844, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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

        int selectedBill = tblBill.getSelectedRow();
        if (selectedBill == -1) {
            return;
        }
        int selectedHoaDon = tblBill.getSelectedRow();
        Integer maHoaDon = Integer.valueOf(tblBill.getValueAt(selectedHoaDon, 0).toString());
        double total = 0;
        for (BillDetails h : HDCT_REPO) {
            if (h.getBill_id().getId().equals(maHoaDon)) {
                total += h.getPrice() * h.getQuantity();
                txtTotalAmount.setText(String.valueOf(total));
                txtMoneyPaid.setText(String.valueOf(total));
                txtTheAmountTheCustomerGives.setText(String.valueOf(total));
            }
        }
        int selectedRow = tblBill.getSelectedRow();
        loadHoaDonChiTiet(maHoaDon);
    }//GEN-LAST:event_tblBillMouseClicked

    private void tblShoppingCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblShoppingCartMouseClicked

    }//GEN-LAST:event_tblShoppingCartMouseClicked

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseClicked
        txtExcessMoney.setText("");
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
        double total = 0;
        if (sp.getAmount() <= 0) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The product is out of stock!").showNotification();
            return;
        } else {
            for (BillDetails h : HDCT_REPO) {
                if (h.getBill_id().getId().equals(maHoaDon) && h.getMilk_id().getId().equals(maSanPham)) {
                    h.setQuantity(h.getQuantity() + hdct.getQuantity());
                    isNotExisted = false;
                    break;
                }
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
        for (BillDetails h : HDCT_REPO) {
            if (h.getBill_id().getId().equals(maHoaDon)) {
                total += h.getPrice() * h.getQuantity();
                txtTotalAmount.setText(String.valueOf(total));
                txtMoneyPaid.setText(String.valueOf(total));
                txtTheAmountTheCustomerGives.setText(String.valueOf(total));
                double moneyPaid = Double.parseDouble(txtMoneyPaid.getText());
                double theAmountTheCustomerGives = Double.parseDouble(txtTheAmountTheCustomerGives.getText());
                if (moneyPaid < theAmountTheCustomerGives) {
                    txtExcessMoney.setText(String.valueOf(theAmountTheCustomerGives - moneyPaid));
                }
            }
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
        txtMoneyPaid.setText("");
        txtTotalAmount.setText("");
        loadHoaDon(HOA_DON_REPO);
        loadHoaDonChiTiet(100);
        loadAndFillMilk();
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
//        if (cbbPaymentMethods.getSelectedIndex() == 1) {
//            Customer sus = new Customer();
//            sus.setId(Integer.valueOf(txtIDCustomer.getText()));
//            hoaDon.setCustomerId(sus);
//        }
        HOA_DON_REPO.add(hoaDon);
        loadHoaDon(HOA_DON_REPO);
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnChooseCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseCustomerActionPerformed
        ChooseCustomersForm choose = new ChooseCustomersForm(this);
        choose.setVisible(true);
    }//GEN-LAST:event_btnChooseCustomerActionPerformed

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        int selectedRow = tblBill.getSelectedRow();
        if (selectedRow == -1) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The select bill!").showNotification();
            return;
        }
        if (tblShoppingCart.getRowCount() == 0) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The product is empty!").showNotification();
            return;
        }
        double excessMoney = Double.valueOf(txtMoneyPaid.getText());
        double moneyCustomer = Double.valueOf(txtTheAmountTheCustomerGives.getText());
        if (excessMoney > moneyCustomer) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Error!").showNotification();
            return;
        }
        if (this.insert("directly")) {
            this.resetLable();
            new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                    "Success!").showNotification();
        }
    }//GEN-LAST:event_btnPayActionPerformed

    private void buttonMessage3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMessage3ActionPerformed
        int selectedRow = tblBill.getSelectedRow();
        if (selectedRow == -1) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The select bill!").showNotification();
            return;
        }
        if (tblShoppingCart.getRowCount() == 0) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "The product is empty!").showNotification();
            return;
        }
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
            DeliveryJFrame delivery = new DeliveryJFrame(idHoaDon, user, txtMoneyPaid.getText());
            delivery.setVisible(true);
            this.resetLable();
        }
    }//GEN-LAST:event_buttonMessage3ActionPerformed

    private void tblSaleBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSaleBillMouseClicked
        int row = tblSaleBill.getSelectedRow();
        if (txtTotalAmount.getText().trim().isEmpty() || row == -1) {
            return;
        }
        String totalAmount2 = txtTotalAmount.getText();
        String theamountcustomershavetopay = txtMoneyPaid.getText();
        if (totalAmount2.equalsIgnoreCase(theamountcustomershavetopay)) {
            double totalAmount = Double.valueOf(txtTotalAmount.getText());
            double totalCustomer = Double.valueOf(txtMoneyPaid.getText());
            if (saleBillList != null) {
                for (SaleBill1 sb : saleBillList) {
                    if (Double.parseDouble(sb.getDiscountConditions()) <= totalAmount) {
                        totalAmount = totalAmount - (totalAmount * sb.getPercentDecrease() / 100);
                        txtMoneyPaid.setText(String.valueOf(totalAmount));
                        break;
                    }
                }
            }
            double moneyPaid = Double.parseDouble(txtMoneyPaid.getText());
            double theAmountTheCustomerGives = Double.parseDouble(txtTheAmountTheCustomerGives.getText());
            if (moneyPaid < theAmountTheCustomerGives) {
                txtExcessMoney.setText(String.valueOf(theAmountTheCustomerGives - moneyPaid));
            }
        } else {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "WARNING!").showNotification();
            return;
        }
    }//GEN-LAST:event_tblSaleBillMouseClicked

    private void tblSaleBillMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSaleBillMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSaleBillMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button btnChooseCustomer;
    private udpm.fpt.swing.ButtonMessage btnPay;
    private udpm.fpt.swing.ButtonMessage btnTaoHoaDon;
    private udpm.fpt.swing.ButtonMessage buttonMessage1;
    private udpm.fpt.swing.ButtonMessage buttonMessage3;
    private udpm.fpt.swing.Combobox cbbPaymentMethods;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tblBill;
    private javax.swing.JTable tblProduct;
    private javax.swing.JTable tblSaleBill;
    private javax.swing.JTable tblShoppingCart;
    private udpm.fpt.swing.TextField txtCustomerName;
    private udpm.fpt.swing.TextField txtExcessMoney;
    private udpm.fpt.swing.TextField txtIDCustomer;
    private udpm.fpt.swing.TextField txtMoneyPaid;
    private udpm.fpt.swing.TextField txtTheAmountTheCustomerGives;
    private udpm.fpt.swing.TextField txtTotalAmount;
    // End of variables declaration//GEN-END:variables
    public void excessMoney() {
        txtTheAmountTheCustomerGives.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Gọi phương thức để tự động tính toán khi người dùng nhập
                calculateChange();
            }
        });

        // Phương thức để tự động tính toán tiền thừa
        // Được gọi từ sự kiện nhấn phím Enter trong ô txtKhachDua       
    }

    private void calculateChange() {
        if (txtTheAmountTheCustomerGives.getText() != null) {
            try {
                // Lấy giá trị từ các ô JTextField
                double tongTien = Double.parseDouble(txtMoneyPaid.getText());
                double khachDua = Double.parseDouble(txtTheAmountTheCustomerGives.getText());

                // Tính toán và hiển thị tiền thừa
                double tienThua = khachDua - tongTien;
                txtExcessMoney.setText(String.valueOf(tienThua));
            } catch (NumberFormatException ex) {

            }
        }
    }

    public void theAmountTheCustomerGives() {
        txtTheAmountTheCustomerGives.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Khi nhận được focus, kiểm tra xem nếu là giá trị mặc định, thì xóa nó.
                if (txtTheAmountTheCustomerGives.getText().equals("")) {
                    txtTheAmountTheCustomerGives.setText("0");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Khi mất focus và trống rỗng, thiết lập giá trị mặc định.
                if (txtTheAmountTheCustomerGives.getText().isEmpty()) {
                    txtTheAmountTheCustomerGives.setText("0");
                }
            }
        });
    }
}
