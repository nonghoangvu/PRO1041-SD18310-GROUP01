/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package udpm.fpt.form;

import com.raven.datechooser.DateChooser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import udpm.fpt.component.Notification;
import udpm.fpt.main.Main;
import udpm.fpt.model.Milk;
import udpm.fpt.model.SaleBill1;
import udpm.fpt.model.SaleMilk;
import udpm.fpt.model.User;
import udpm.fpt.service.SaleBillService;
import udpm.fpt.service.SaleMilkService;
import udpm.fpt.swing.table.TableCustom;

/**
 *
 * @author Thanh Dat
 */
public class SaleFinal extends javax.swing.JPanel {

    private SaleBillService sbs = new SaleBillService();
    private SaleMilkService sms = new SaleMilkService();
    DefaultTableModel dtm = new DefaultTableModel();
    private DateChooser dcsb = new DateChooser();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private User user;
    private DateChooser dceb = new DateChooser();
    private List<SaleBill1> sb1 = new ArrayList<>();
    private List<SaleMilk> sm1 = new ArrayList<>();
    private List<Milk> milk = new ArrayList<>();
    private DateChooser dcsm = new DateChooser();
    private DateChooser dcem = new DateChooser();
    private DateChooser dcsbill = new DateChooser();
    private DateChooser dcsmilk = new DateChooser();
    Main main;

    public SaleFinal(User user) {
        this.user = user;
        initComponents();
        TableCustom.apply(jScrollPane3, TableCustom.TableType.MULTI_LINE);
        TableCustom.apply(jScrollPane2, TableCustom.TableType.MULTI_LINE);
        TableCustom.apply(jScrollPane4, TableCustom.TableType.MULTI_LINE);
        dcsb.setTextField(txtsd);
        dcsb.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dcsb.setLabelCurrentDayVisible(false);
        dcsb.setDateFormat(sdf);
        dceb.setTextField(txted);
        dceb.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dceb.setLabelCurrentDayVisible(false);
        dceb.setDateFormat(sdf);
        dcsm.setTextField(txtSD);
        dcsm.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dcsm.setLabelCurrentDayVisible(false);
        dcsm.setDateFormat(sdf);
        dcem.setTextField(txtED);
        dcem.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dcem.setLabelCurrentDayVisible(false);
        dcem.setDateFormat(sdf);
        dcsbill.setTextField(txtSEARCH);
        dcsbill.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dcsbill.setLabelCurrentDayVisible(false);
        dcsbill.setDateFormat(sdf);
        dcsmilk.setTextField(txtsearch);
        dcsmilk.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dcsmilk.setLabelCurrentDayVisible(false);
        dcsmilk.setDateFormat(sdf);
        loadTableB();
        loadTableM();
        loadDataAndFillTable();
    }
    //    SaleBill

    public void loadTableB() {
        this.dtm = (DefaultTableModel) tblbill1.getModel();
        dtm.setRowCount(0);
        this.sb1.clear();
        for (SaleBill1 sb : this.sbs.getAll()) {
            this.sb1.add(sb);
            Object[] ob = {
                sb.getId(),
                sb.getSaleEvent(),
                sb.getDiscountConditions(),
                sb.getPercentDecrease(),
                sb.getStartDay(),
                sb.getEndDay(),
                null == sb.getUser() ? "" : sb.getUser().getUsername(),
                sb.getCreatedAt()

            };
            dtm.addRow(ob);
        }
    }

    public SaleBill1 getSaleBill1() {
        SaleBill1 sb = new SaleBill1();
        sb.setSaleEvent(txtname.getText());
        sb.setDiscountConditions(txtdc.getText());
        sb.setPercentDecrease(Integer.parseInt(txtptg.getText()));
        try {
            sb.setStartDay(sdf.parse(txtsd.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        try {
            sb.setEndDay(sdf.parse(txted.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        sb.setUser(this.user);
        sb.setCreatedAt(new Date());
//        sb.setUser(this.user);
        return sb;
    }

    public SaleBill1 updateSaleBill1() {
        int row = tblbill1.getSelectedRow();
        SaleBill1 sb = new SaleBill1();
        sb.setId(Integer.parseInt(tblbill1.getValueAt(row, 0).toString()));
        sb.setSaleEvent(txtname.getText());
        sb.setDiscountConditions(txtdc.getText());
        sb.setPercentDecrease(Integer.parseInt(txtptg.getText()));
        try {
            sb.setStartDay(sdf.parse(txtsd.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        try {
            sb.setEndDay(sdf.parse(txted.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        sb.setUser(this.user);
        sb.setCreatedAt(new Date());
//        sb.setUser(this.user);
        return sb;
    }

    public boolean checkiforB() {
        if (txtname.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Information can't be empty").showNotification();
            return false;
        } else if (txtdc.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Information can't be empty").showNotification();
        } else if (txtptg.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Information can't be empty").showNotification();
            return false;
        } else if (Double.valueOf(txtptg.getText()) > 100) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Can't over 100").showNotification();
            return false;
        }
        return true;
    }

    public void tableSearchB() {
        this.dtm = (DefaultTableModel) tblbill1.getModel();
        dtm.setRowCount(0);
        this.sb1.clear();
        try {
            for (SaleBill1 sb : this.sbs.Search(sdf.parse(txtSEARCH.getText()))) {
                this.sb1.add(sb);
                Object[] ob = {
                    sb.getId(),
                    sb.getSaleEvent(),
                    sb.getDiscountConditions(),
                    sb.getPercentDecrease(),
                    sb.getStartDay(),
                    sb.getEndDay(),
                    null == sb.getUser() ? "" : sb.getUser().getUsername(),
                    sb.getCreatedAt()

                };
                dtm.addRow(ob);
            }
        } catch (Exception ex) {
            Logger.getLogger(SaleFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //    SaleMilk
    public void loadTableM() {
        this.dtm = (DefaultTableModel) tblSaleMilk.getModel();
        dtm.setRowCount(0);
        this.sm1.clear();
        for (SaleMilk sm : this.sms.getAll()) {
            this.sm1.add(sm);
            Object[] ob = {
                sm.getId(),
                sm.getSale_event(),
                sm.getPercent_decrease(),
                sm.getStart_day(),
                sm.getEnd_day(),
                null == sm.getMilk() ? "" : sm.getMilk().getProduct_name(),
                null == sm.getUser() ? "" : sm.getUser().getUsername(),
                sm.getCreated_at()
            };
            dtm.addRow(ob);
        }
    }

    private void updateTable(List<Milk> data) {
        this.milk.clear();
        dtm = (DefaultTableModel) tblMilk.getModel();
        dtm.setRowCount(0);
        for (Milk prd : data) {
            this.milk.add(prd);
            Object[] rowData = {
                prd.getId(),
                prd.getProduct_name(),
                prd.getPrice()};
            dtm.addRow(rowData);
        }

    }

    public void loadDataAndFillTable() {
        CompletableFuture<List<Milk>> future = this.sms.loadAsync();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {

                updateTable(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    public SaleMilk getSaleMilk() {
        SaleMilk sm = new SaleMilk();
        Milk milk = new Milk();
        sm.setSale_event(txtNAME.getText());
        sm.setPercent_decrease(Integer.parseInt(txtPTG.getText()));
        try {
            sm.setStart_day(sdf.parse(txtSD.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        try {
            sm.setEnd_day(sdf.parse(txtED.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        milk.setId(Long.valueOf(txtMID.getText()));
        sm.setMilk(milk);
        sm.setUser(this.user);
        sm.setCreated_at(new Date());
        return sm;
    }

    public SaleMilk updateMilk() {
        int row = tblSaleMilk.getSelectedRow();
        SaleMilk sm = this.sm1.get(row);
        Milk thmilk = this.milk.get(row);
        sm.setId(sm.getId());
        sm.setSale_event(txtNAME.getText());
        sm.setPercent_decrease(Integer.parseInt(txtPTG.getText()));
        try {
            sm.setStart_day(sdf.parse(txtSD.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
        try {
            sm.setEnd_day(sdf.parse(txtED.getText()));
        } catch (Exception ex) {
            System.err.println("Err" + ex);
        }
//        milk.setId(Long.valueOf(txtMID.getText()));
        sm.setMilk(thmilk);
        sm.setUser(this.user);
        sm.setCreated_at(new Date());
        return sm;
    }

    public boolean checkifoM() {
        if (txtNAME.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Information can't be empty").showNotification();
            return false;
        } else if (txtPTG.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Information can't be empty").showNotification();
            return false;
        } else if (Double.valueOf(txtPTG.getText()) > 100) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Can't over 100").showNotification();
            return false;
        } else if (txtMID.getText().isBlank()) {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Information can't be empty").showNotification();
        }
        return true;
    }

    public void tableSearchM() {
        this.dtm = (DefaultTableModel) tblSaleMilk.getModel();
        dtm.setRowCount(0);
        this.sm1.clear();
        List<SaleMilk> saleMilk;

        try {
//            saleMilk.clear();
            System.out.println("Date search: " + sdf.parse(txtsearch.getText()));
            saleMilk = this.sms.Search(sdf.parse(txtsearch.getText()));
            for (SaleMilk sm : saleMilk) {
                this.sm1.add(sm);
                Object[] ob = {
                    sm.getId(),
                    sm.getSale_event(),
                    sm.getPercent_decrease(),
                    sm.getStart_day(),
                    sm.getEnd_day(),
                    null == sm.getMilk() ? "" : sm.getMilk().getProduct_name(),
                    null == sm.getUser() ? "" : sm.getUser().getUsername(),
                    sm.getCreated_at()

                };
                dtm.addRow(ob);
            }
        } catch (Exception ex) {
            Logger.getLogger(SaleFinal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        txtdc = new udpm.fpt.swing.TextField();
        txtptg = new udpm.fpt.swing.TextField();
        txtsd = new udpm.fpt.swing.TextField();
        txted = new udpm.fpt.swing.TextField();
        txtSEARCH = new udpm.fpt.swing.TextField();
        btnSEARCH = new udpm.fpt.swing.Button();
        btnADD = new udpm.fpt.swing.Button();
        btnDELETE = new udpm.fpt.swing.Button();
        btnUPDATE = new udpm.fpt.swing.Button();
        btnCLEAR = new udpm.fpt.swing.Button();
        txtname = new udpm.fpt.swing.TextField();
        btnresetbill = new udpm.fpt.swing.Button();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblbill1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnadd = new udpm.fpt.swing.Button();
        btnDelete = new udpm.fpt.swing.Button();
        btnUpdate = new udpm.fpt.swing.Button();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSaleMilk = new javax.swing.JTable();
        btnclear = new udpm.fpt.swing.Button();
        btnSearch = new udpm.fpt.swing.Button();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblMilk = new javax.swing.JTable();
        btnresetmilk = new udpm.fpt.swing.Button();
        txtNAME = new udpm.fpt.swing.TextField();
        txtPTG = new udpm.fpt.swing.TextField();
        txtSD = new udpm.fpt.swing.TextField();
        txtED = new udpm.fpt.swing.TextField();
        txtMID = new udpm.fpt.swing.TextField();
        txtsearch = new udpm.fpt.swing.TextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        txtdc.setLabelText("Discount Conditions");

        txtptg.setLabelText("Percent Decresae");

        txtsd.setLabelText("Start Date\n");

        txted.setLabelText("End Date");
        txted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtedActionPerformed(evt);
            }
        });

        txtSEARCH.setLabelText("Search By End Date");

        btnSEARCH.setBackground(new java.awt.Color(102, 255, 255));
        btnSEARCH.setForeground(new java.awt.Color(255, 255, 255));
        btnSEARCH.setText("Search");
        btnSEARCH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSEARCHActionPerformed(evt);
            }
        });

        btnADD.setBackground(new java.awt.Color(102, 255, 255));
        btnADD.setForeground(new java.awt.Color(255, 255, 255));
        btnADD.setText("Add");
        btnADD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnADDActionPerformed(evt);
            }
        });

        btnDELETE.setBackground(new java.awt.Color(102, 255, 255));
        btnDELETE.setForeground(new java.awt.Color(255, 255, 255));
        btnDELETE.setText("Delete");
        btnDELETE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDELETEActionPerformed(evt);
            }
        });

        btnUPDATE.setBackground(new java.awt.Color(102, 255, 255));
        btnUPDATE.setForeground(new java.awt.Color(255, 255, 255));
        btnUPDATE.setText("Update");
        btnUPDATE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUPDATEActionPerformed(evt);
            }
        });

        btnCLEAR.setBackground(new java.awt.Color(102, 255, 255));
        btnCLEAR.setForeground(new java.awt.Color(255, 255, 255));
        btnCLEAR.setText("Clear");
        btnCLEAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCLEARActionPerformed(evt);
            }
        });

        txtname.setLabelText("Sale Event\n");
        txtname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnameActionPerformed(evt);
            }
        });

        btnresetbill.setBackground(new java.awt.Color(102, 255, 255));
        btnresetbill.setForeground(new java.awt.Color(255, 255, 255));
        btnresetbill.setText("Reset Table");
        btnresetbill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetbillActionPerformed(evt);
            }
        });

        tblbill1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Discount Conditions", "Percent Decresae", "Start Date", "End Date", "Staff ID", "Created Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblbill1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbill1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblbill1);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SALE BILL");

        jLabel5.setText("Sale Bill Information");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnADD, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDELETE, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUPDATE, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtSEARCH, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                                    .addComponent(txtsd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtptg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtdc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txted, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(28, 28, 28)
                                .addComponent(btnSEARCH, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(btnCLEAR, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnresetbill, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(24, 24, 24))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtptg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtsd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txted, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSEARCH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSEARCH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnADD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDELETE, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUPDATE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCLEAR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnresetbill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(177, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sale Bill", jPanel4);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnadd.setBackground(new java.awt.Color(102, 255, 255));
        btnadd.setForeground(new java.awt.Color(255, 255, 255));
        btnadd.setText("Add");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(102, 255, 255));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(102, 255, 255));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        tblSaleMilk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Sale Name", "Percent Decrease", "Start Date", "End Date", "Milk ID", "Staff ID", "Created At"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSaleMilk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSaleMilkMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSaleMilk);

        btnclear.setBackground(new java.awt.Color(102, 255, 255));
        btnclear.setForeground(new java.awt.Color(255, 255, 255));
        btnclear.setText("Clear");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        btnSearch.setBackground(new java.awt.Color(102, 255, 255));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblMilk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Name", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMilk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMilkMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblMilk);

        btnresetmilk.setBackground(new java.awt.Color(102, 255, 255));
        btnresetmilk.setForeground(new java.awt.Color(255, 255, 255));
        btnresetmilk.setText("Reset Table");
        btnresetmilk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnresetmilkActionPerformed(evt);
            }
        });

        txtNAME.setLabelText("Sale Event\n");

        txtPTG.setLabelText("Percent Decrease");

        txtSD.setLabelText("Start Date\n");

        txtED.setLabelText("End Date");

        txtMID.setLabelText("Milk ID");

        txtsearch.setLabelText("Search By End Date");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SALE MILK");

        jLabel3.setText("Sale Milk Information ");

        jLabel4.setText("Milk Information");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtED, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNAME, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPTG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtsearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(btnclear, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnresetmilk, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 47, Short.MAX_VALUE)))
                .addGap(16, 16, 16)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtNAME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPTG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtED, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnclear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnresetmilk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sale Milk", jPanel3);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnADDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADDActionPerformed
        if (this.checkiforB()) {
            SaleBill1 sb1 = getSaleBill1();
            if (sbs.addNew(sb1)) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Add Success!").showNotification();
            } else {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Add Fail!").showNotification();
            }
            this.loadTableB();
        }
    }//GEN-LAST:event_btnADDActionPerformed

    private void txtnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnameActionPerformed

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        if (this.checkifoM()) {
            SaleMilk sm = getSaleMilk();
            if (sms.addNew(sm)) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Add Success!").showNotification();
            } else {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Add Fail!").showNotification();
            }
            this.loadTableM();
            this.loadDataAndFillTable();
        }
    }//GEN-LAST:event_btnaddActionPerformed

    private void tblSaleMilkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSaleMilkMouseClicked
        int row = tblSaleMilk.getSelectedRow();
        if (row == -1) {
            return;
        }
        SaleMilk sm = this.sm1.get(row);
        txtNAME.setText(sm.getSale_event());
        txtPTG.setText(String.valueOf(sm.getPercent_decrease()));
        txtSD.setText(String.valueOf(sm.getStart_day()));
        txtED.setText(String.valueOf(sm.getEnd_day()));
        txtMID.setText(String.valueOf(sm.getMilk().getId()));
    }//GEN-LAST:event_tblSaleMilkMouseClicked

    private void tblMilkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMilkMouseClicked
        int row = tblMilk.getSelectedRow();
        if (row == -1) {
            return;
        }
        Milk milk = this.milk.get(row);
        txtMID.setText(String.valueOf(milk.getId()));
    }//GEN-LAST:event_tblMilkMouseClicked

    private void btnDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDELETEActionPerformed
        int row = tblbill1.getSelectedRow();
        if (row < 0) {
            return;
        }
        boolean sb = sbs.deleteBill(Integer.valueOf(tblbill1.getValueAt(row, 0).toString()));
        if (sb) {
            new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                    "Delete Success!").showNotification();
        } else {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Delete Fail!").showNotification();
        }
        this.loadTableB();
    }//GEN-LAST:event_btnDELETEActionPerformed

    private void btnUPDATEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUPDATEActionPerformed
        int row = tblbill1.getSelectedRow();
        if (row < 0) {
            return;
        }
        if (this.checkiforB()) {
            SaleBill1 sb1 = updateSaleBill1();
            if (sbs.updateBill(sb1)) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Update Success!").showNotification();
            } else {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Update Fail!").showNotification();
            }
            this.loadTableB();
        }
    }//GEN-LAST:event_btnUPDATEActionPerformed

    private void btnCLEARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCLEARActionPerformed
        txtname.setText("");
        txtdc.setText("");
        txtptg.setText("");
        txtsd.setText("");
        txted.setText("");
        txtSEARCH.setText("");
    }//GEN-LAST:event_btnCLEARActionPerformed

    private void btnSEARCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSEARCHActionPerformed
        tableSearchB();
    }//GEN-LAST:event_btnSEARCHActionPerformed

    private void btnresetbillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetbillActionPerformed
        loadTableB();
    }//GEN-LAST:event_btnresetbillActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed

        int row = tblSaleMilk.getSelectedRow();
        if (row < 0) {
            return;
        }
        boolean sm = sms.deleteMilk(Integer.valueOf(tblSaleMilk.getValueAt(row, 0).toString()));
        if (sm) {
            new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                    "Delete Success!").showNotification();
        } else {
            new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                    "Delete Fail!").showNotification();
        }
        this.loadTableM();

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = tblSaleMilk.getSelectedRow();
        if (row < 0) {
            return;
        }
        if (this.checkifoM()) {
            SaleMilk sm = updateMilk();
            if (sms.updateMilk(sm)) {
                new Notification(Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP,
                        "Update Success!").showNotification();
            } else {
                new Notification(Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP,
                        "Update Fail!").showNotification();
            }
            this.loadTableM();
    }//GEN-LAST:event_btnUpdateActionPerformed
    }
    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        txtNAME.setText("");
        txtPTG.setText("");
        txtSD.setText("");
        txtED.setText("");
        txtMID.setText("");
        txtsearch.setText("");
    }//GEN-LAST:event_btnclearActionPerformed

    private void btnresetmilkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnresetmilkActionPerformed
        loadTableM();
    }//GEN-LAST:event_btnresetmilkActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        tableSearchM();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblbill1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbill1MouseClicked
        int row = tblbill1.getSelectedRow();
        if (row == -1) {
            return;
        }
        SaleBill1 s1 = this.sb1.get(row);
        txtname.setText(s1.getSaleEvent());
        txtdc.setText(s1.getDiscountConditions());
        txtptg.setText(String.valueOf(s1.getPercentDecrease()));
        txtsd.setText(String.valueOf(s1.getStartDay()));
        txted.setText(String.valueOf(s1.getEndDay()));
    }//GEN-LAST:event_tblbill1MouseClicked

    private void txtedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtedActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button btnADD;
    private udpm.fpt.swing.Button btnCLEAR;
    private udpm.fpt.swing.Button btnDELETE;
    private udpm.fpt.swing.Button btnDelete;
    private udpm.fpt.swing.Button btnSEARCH;
    private udpm.fpt.swing.Button btnSearch;
    private udpm.fpt.swing.Button btnUPDATE;
    private udpm.fpt.swing.Button btnUpdate;
    private udpm.fpt.swing.Button btnadd;
    private udpm.fpt.swing.Button btnclear;
    private udpm.fpt.swing.Button btnresetbill;
    private udpm.fpt.swing.Button btnresetmilk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblMilk;
    private javax.swing.JTable tblSaleMilk;
    private javax.swing.JTable tblbill1;
    private udpm.fpt.swing.TextField txtED;
    private udpm.fpt.swing.TextField txtMID;
    private udpm.fpt.swing.TextField txtNAME;
    private udpm.fpt.swing.TextField txtPTG;
    private udpm.fpt.swing.TextField txtSD;
    private udpm.fpt.swing.TextField txtSEARCH;
    private udpm.fpt.swing.TextField txtdc;
    private udpm.fpt.swing.TextField txted;
    private udpm.fpt.swing.TextField txtname;
    private udpm.fpt.swing.TextField txtptg;
    private udpm.fpt.swing.TextField txtsd;
    private udpm.fpt.swing.TextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
