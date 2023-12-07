/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package udpm.fpt.form;

import com.raven.datechooser.DateChooser;
import udpm.fpt.Utitlity.DiscountCalculator;
import udpm.fpt.main.Main;
import udpm.fpt.model.*;
import udpm.fpt.service.ProductService;
import udpm.fpt.swing.CustomCellRenderer;
import udpm.fpt.swing.NumberOnlyFilter;
import udpm.fpt.swing.table.TableCustom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author NONG HOANG VU
 */
public class ProductManagementForm extends javax.swing.JPanel {
    private DefaultTableModel tblModel;
    private final ProductService list;
    private List<ProductInfo> temp;
    private final User user;
    private final Main main;
    private Integer countHidden = 0;
    private Integer countOutOfStock = 0;

    public ProductManagementForm(User user, Main main) {
        initComponents();
        this.user = user;
        this.list = new ProductService();
        this.main = main;
        initProduct();
    }

    public void initProduct() {
        TableCustom.apply(jScrollPane1, TableCustom.TableType.MULTI_LINE);
        this.temp = new ArrayList<>();
        setTxtEntryToDate();
        setNumberTexfield();
        loadComboBox();
        loadDataAndFillTable();
    }

    /*-------------------Format data------------------*/
    private Integer priceUpdate(Long id, Integer price) {
        for (SaleMilk sm : this.list.getPercentSale()) {
            if (Objects.equals(id, sm.getMilk().getId())) {
                return new DiscountCalculator().calculateDiscountedPrice(price, sm.getPercent_decrease());
            }
        }
        return price;
    }

    private String setSelectedIndex(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }

    /*---------------Set format------------------*/
    private void setTxtEntryToDate() {
        DateChooser dateChooser = new DateChooser();
        dateChooser.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dateChooser.setLabelCurrentDayVisible(false);
        dateChooser.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        dateChooser.setTextField(txtEntryDate);
    }

    private void setNumberTexfield() {
        ((AbstractDocument) txtQuantityMin.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtQuantityMax.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtPriceMin.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtPriceMax.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtVolume.getDocument()).setDocumentFilter(new NumberOnlyFilter());
    }

    /*---------------------------------Set Data------------------------------------------------*/
    private void setCountData() {
        lbCountPorduct.setText(String.valueOf(this.temp.size()));
        rdoHidden.setText("Hidden " + this.countHidden);
        rdoOutOfStock.setText("Out of stock " + this.countOutOfStock);
    }

    private void loadComboBox() {
        loadDataAndFillFlavor();
        loadDataAndFillPackagingSpecification();
        loadDataAndFillUnit();
        loadTypeCheckExpiry();
        loadTypeSearch();
        loadSortByPricce();
    }

    private void setImange(String url) {
        lbproductgallery.setText(null);
        try {
            int labelWidth = lbproductgallery.getWidth();
            int labelHeight = lbproductgallery.getHeight();
            ImageIcon originalIcon = new javax.swing.ImageIcon(Objects.requireNonNull(getClass().getResource("/ProductGallery/" + url)));
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            lbproductgallery.setIcon(scaledIcon);
        } catch (Exception e) {
            lbproductgallery.setIcon(null);
            lbproductgallery.setText("Image not found");
        }
    }

    private void setLabel() {
        ProductInfo pi = this.temp.get(tblProduct.getSelectedRow());
        lbId.setText(String.valueOf(pi.getId()));
        setImange(pi.getMilk().getImg());
    }

    /*-------------------------------------------Run stream processing-------------------------------------------*/
    /*1. Load to table*/
    public void loadDataAndFillTable() {
        CompletableFuture<List<ProductInfo>> future = this.list.loadAsync();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {

                updateTable(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    private void updateTable(List<ProductInfo> data) {
        this.temp.clear();
        tblModel = (DefaultTableModel) tblProduct.getModel();
        tblModel.setRowCount(0);
        for (ProductInfo prd : data) {
            if (prd.getMilk().getIsDelete()) {
                this.countHidden += 1;
            }
            if (prd.getMilk().getAmount() == 0) {
                this.countOutOfStock += 1;
            }
            if (!prd.getMilk().getIsDelete()) {
                this.temp.add(prd);
                Object[] rowData = {
                        prd.getMilk().getImg(),
                        prd.getMilk().getProduct_name(),
                        prd.getFlavor().getTaste(),
                        prd.getVolume() + " " + prd.getUnit().getMeasurement_unit(),
                        prd.getMilk().getAmount(),
                        prd.getCreate_at(),
                        setSelectedIndex(priceUpdate(prd.getMilk().getId(), prd.getMilk().getPrice())) + " VND"
                };
                tblModel.addRow(rowData);
                tblProduct.setDefaultRenderer(Object.class, new CustomCellRenderer(tblProduct));
                setCountData();
            }
        }
    }

    /*2. Load to flavor*/
    public void loadDataAndFillFlavor() {
        CompletableFuture<List<Flavor>> future = this.list.loadFlavor();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                updateFlavor(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    private void updateFlavor(List<Flavor> data) {
        DefaultComboBoxModel<Flavor> cbbModel = new DefaultComboBoxModel<>();
        cbbTaste.removeAll();
        cbbTaste.setModel(cbbModel);
        for (Flavor flavor : data) {
            cbbModel.addElement(flavor);
        }
    }

    /*3. Load to packaging specification*/
    public void loadDataAndFillPackagingSpecification() {
        CompletableFuture<List<PackagingSpecification>> future = this.list.loadPackagingSpecification();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                updatePackagingSpecification(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    private void updatePackagingSpecification(List<PackagingSpecification> data) {
        DefaultComboBoxModel<PackagingSpecification> cbbModel = new DefaultComboBoxModel<>();
        cbbPackagingSpecification.removeAll();
        cbbPackagingSpecification.setModel(cbbModel);
        for (PackagingSpecification specification : data) {
            cbbModel.addElement(specification);
        }
    }/*4. Load to unit*/

    public void loadDataAndFillUnit() {
        CompletableFuture<List<Unit>> future = this.list.loadUnit();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                updateUnit(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    private void updateUnit(List<Unit> data) {
        DefaultComboBoxModel<Unit> cbbModel = new DefaultComboBoxModel<>();
        cbbPackagingSpecification.removeAll();
        cbbPackagingSpecification.setModel(cbbModel);
        for (Unit unit : data) {
            cbbModel.addElement(unit);
        }
    }

    private void loadTypeCheckExpiry() {
        String[] months = {
                "Valid",
                "Expires in 5 months",
                "Expires in 4 months",
                "Expires in 3 months",
                "Expires in 2 months",
                "Expires in 1 month",
                "Expired"
        };
        for (String s : months) {
            cbbCheckExpiry.addItem(s);
        }
    }

    private void loadTypeSearch() {
        String[] type = {
                "Product's name",
                "Product code"
        };
        for (String s : type) {
            cbbSearchType.addItem(s);
        }
    }

    private void loadSortByPricce() {
        String[] sort = {
                "Default",
                "Price from low to high",
                "Price from high to low"
        };
        for (String s : sort) {
            cbbSortByPrice.addItem(s);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        cbbSearchType = new udpm.fpt.swing.Combobox();
        txtSearch = new udpm.fpt.swing.TextField();
        jPanel2 = new javax.swing.JPanel();
        txtQuantityMin = new udpm.fpt.swing.TextField();
        txtQuantityMax = new udpm.fpt.swing.TextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnSearch = new udpm.fpt.swing.ButtonMessage();
        btnRetype = new udpm.fpt.swing.ButtonMessage();
        jPanel4 = new javax.swing.JPanel();
        txtPriceMin = new udpm.fpt.swing.TextField();
        txtPriceMax = new udpm.fpt.swing.TextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbbCheckExpiry = new udpm.fpt.swing.Combobox();
        txtEntryDate = new udpm.fpt.swing.TextField();
        cbbTaste = new udpm.fpt.swing.Combobox();
        cbbPackagingSpecification = new udpm.fpt.swing.Combobox();
        txtVolume = new udpm.fpt.swing.TextField();
        cbbUnit = new udpm.fpt.swing.Combobox();
        jPanel5 = new javax.swing.JPanel();
        lbproductgallery = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        rdoAll = new javax.swing.JRadioButton();
        rdoActive = new javax.swing.JRadioButton();
        rdoOutOfStock = new javax.swing.JRadioButton();
        rdoHidden = new javax.swing.JRadioButton();
        lbCountPorduct = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnNew = new udpm.fpt.swing.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        btnUpdate = new udpm.fpt.swing.Button();
        btnSeeMore = new udpm.fpt.swing.Button();
        cbbSortByPrice = new udpm.fpt.swing.Combobox();
        lbId = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(252, 252, 252));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(249, 249, 249))); // NOI18N

        cbbSearchType.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        cbbSearchType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Product's name" }));
        cbbSearchType.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbbSearchType.setLabeText("");

        txtSearch.setLabelText("Please input at least first 2 characters of word");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Stock Quantity", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtQuantityMin.setLabelText("Minimum");

        txtQuantityMax.setLabelText("Max");

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("The number on the right should be greater than the left.");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(193, 193, 193));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("-");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtQuantityMin, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantityMax, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtQuantityMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtQuantityMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        btnSearch.setBackground(new java.awt.Color(102, 204, 255));
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("Search");
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        btnRetype.setBackground(new java.awt.Color(229, 229, 229));
        btnRetype.setForeground(new java.awt.Color(255, 255, 255));
        btnRetype.setText("Retype");
        btnRetype.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Price range", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtPriceMin.setLabelText("Minimum");

        txtPriceMax.setLabelText("Max");

        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("The number on the right should be greater than the left.");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(193, 193, 193));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("-");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtPriceMin, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPriceMax, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPriceMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPriceMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        cbbCheckExpiry.setLabeText("Check Expiry");

        txtEntryDate.setLabelText("Entry date");

        cbbTaste.setLabeText("Taste");

        cbbPackagingSpecification.setLabeText("Packaging Specification");

        txtVolume.setLabelText("Volume");

        cbbUnit.setLabeText("Unit");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbproductgallery.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbproductgallery.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbproductgallery.setText("Image not found");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbproductgallery, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbproductgallery, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(cbbSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRetype, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbbTaste, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtEntryDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbbCheckExpiry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbSearchType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(cbbTaste, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbbCheckExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRetype, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbbPackagingSpecification, cbbTaste, cbbUnit, txtSearch, txtVolume});

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        rdoAll.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoAll);
        rdoAll.setSelected(true);
        rdoAll.setText("All");

        rdoActive.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoActive);
        rdoActive.setText("Active");

        rdoOutOfStock.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoOutOfStock);
        rdoOutOfStock.setText("Out of stock 0");

        rdoHidden.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoHidden);
        rdoHidden.setText("Hidden 0");

        lbCountPorduct.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbCountPorduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbCountPorduct.setText("0");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Products");

        btnNew.setBackground(new java.awt.Color(255, 153, 102));
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("Add 1 new product");
        btnNew.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Image", "Product's name", "Taste", "Volume", "Stock Quantity", "Entry Date", "Price"
            }
        ));
        tblProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProduct);

        btnUpdate.setBackground(new java.awt.Color(102, 204, 255));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSeeMore.setBackground(new java.awt.Color(102, 204, 255));
        btnSeeMore.setForeground(new java.awt.Color(255, 255, 255));
        btnSeeMore.setText("See more");
        btnSeeMore.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        cbbSortByPrice.setLabeText("Sort by price");

        lbId.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbId.setText("00000000");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("ID:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(rdoAll, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoActive)
                                .addGap(18, 18, 18)
                                .addComponent(rdoOutOfStock)
                                .addGap(18, 18, 18)
                                .addComponent(rdoHidden))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(lbCountPorduct, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSeeMore, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbbSortByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbId, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(lbId))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdoAll)
                        .addComponent(rdoActive)
                        .addComponent(rdoOutOfStock)
                        .addComponent(rdoHidden)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbCountPorduct, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSeeMore, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbbSortByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductMouseClicked
        try {
            setLabel();
        } catch (Exception ignored) {
        }
    }//GEN-LAST:event_tblProductMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button btnNew;
    private udpm.fpt.swing.ButtonMessage btnRetype;
    private udpm.fpt.swing.ButtonMessage btnSearch;
    private udpm.fpt.swing.Button btnSeeMore;
    private udpm.fpt.swing.Button btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private udpm.fpt.swing.Combobox cbbCheckExpiry;
    private udpm.fpt.swing.Combobox cbbPackagingSpecification;
    private udpm.fpt.swing.Combobox cbbSearchType;
    private udpm.fpt.swing.Combobox cbbSortByPrice;
    private udpm.fpt.swing.Combobox cbbTaste;
    private udpm.fpt.swing.Combobox cbbUnit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbCountPorduct;
    private javax.swing.JLabel lbId;
    private javax.swing.JLabel lbproductgallery;
    private javax.swing.JRadioButton rdoActive;
    private javax.swing.JRadioButton rdoAll;
    private javax.swing.JRadioButton rdoHidden;
    private javax.swing.JRadioButton rdoOutOfStock;
    private javax.swing.JTable tblProduct;
    private udpm.fpt.swing.TextField txtEntryDate;
    private udpm.fpt.swing.TextField txtPriceMax;
    private udpm.fpt.swing.TextField txtPriceMin;
    private udpm.fpt.swing.TextField txtQuantityMax;
    private udpm.fpt.swing.TextField txtQuantityMin;
    private udpm.fpt.swing.TextField txtSearch;
    private udpm.fpt.swing.TextField txtVolume;
    // End of variables declaration//GEN-END:variables
}
