package udpm.fpt.form;

import com.raven.datechooser.DateChooser;
import java.awt.Image;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import udpm.fpt.Utitlity.DiscountCalculator;
import udpm.fpt.main.Main;
import udpm.fpt.model.Flavor;
import udpm.fpt.model.PackagingSpecification;
import udpm.fpt.model.ProductInfo;
import udpm.fpt.model.SaleMilk;
import udpm.fpt.model.Unit;
import udpm.fpt.model.User;
import udpm.fpt.servicce.ProductService;
import udpm.fpt.swing.CustomCellRenderer;
import udpm.fpt.swing.NumberOnlyFilter;
import udpm.fpt.swing.table.TableCustom;

/**
 *
 * @author NONG HOANG VU
 */
public class ProductForm extends javax.swing.JPanel {
private DefaultTableModel tblModel;
    private final ProductService list;
    private List<ProductInfo> temp;
    private final User user;
    private final Main main;
    private Integer countHidden = 0;
    private Integer countOutOfStock = 0;
    public ProductForm(User user, Main main) {
        initComponents();
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
    private void loadComboBox() {
        loadDataAndFillFlavor();
        loadDataAndFillPackagingSpecification();
        loadDataAndFillUnit();
        loadTypeCheckExpiry();
        loadTypeSearch();
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
                        prd.getMilk().getId(),
                        prd.getMilk().getProduct_name(),
                        prd.getFlavor().getTaste(),
                        prd.getVolume() + " " + prd.getUnit().getMeasurement_unit(),
                        prd.getMilk().getAmount(),
                        prd.getCreate_at(),
                        setSelectedIndex(priceUpdate(prd.getMilk().getId(), prd.getMilk().getPrice())) + " VND"
                };
                tblModel.addRow(rowData);
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
        cbbUnit.removeAll();
        cbbUnit.setModel(cbbModel);
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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        cbbSearchType = new udpm.fpt.swing.Combobox();
        txtSearch = new udpm.fpt.swing.TextField();
        cbbTaste = new udpm.fpt.swing.Combobox();
        cbbPackagingSpecification = new udpm.fpt.swing.Combobox();
        txtVolume = new udpm.fpt.swing.TextField();
        cbbUnit = new udpm.fpt.swing.Combobox();
        txtEntryDate = new udpm.fpt.swing.TextField();
        cbbCheckExpiry = new udpm.fpt.swing.Combobox();
        jPanel4 = new javax.swing.JPanel();
        txtQuantityMin = new udpm.fpt.swing.TextField();
        txtQuantityMax = new udpm.fpt.swing.TextField();
        jPanel5 = new javax.swing.JPanel();
        txtPriceMin = new udpm.fpt.swing.TextField();
        txtPriceMax = new udpm.fpt.swing.TextField();
        button2 = new udpm.fpt.swing.Button();
        button5 = new udpm.fpt.swing.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lbproductgallery = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbId = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        button1 = new udpm.fpt.swing.Button();
        button3 = new udpm.fpt.swing.Button();
        button4 = new udpm.fpt.swing.Button();
        lbCountPorduct = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Catelogy", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(179, 179, 179))); // NOI18N

        cbbSearchType.setBorder(null);
        cbbSearchType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Product's name" }));
        cbbSearchType.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbbSearchType.setLabeText("");

        txtSearch.setLabelText("Please input at least first 2 characters of word");

        cbbTaste.setLabeText("Taste");

        cbbPackagingSpecification.setLabeText("Packaging Specification");

        txtVolume.setLabelText("Volume");

        cbbUnit.setLabeText("Unit");

        txtEntryDate.setLabelText("Entry date");

        cbbCheckExpiry.setLabeText("Check Expiry");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Stock Quantity", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(178, 178, 178))); // NOI18N

        txtQuantityMin.setLabelText("Minimum");

        txtQuantityMax.setLabelText("Max");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtQuantityMin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(txtQuantityMax, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantityMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantityMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Price range", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(178, 178, 178))); // NOI18N

        txtPriceMin.setLabelText("Minimum");

        txtPriceMax.setLabelText("Max");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPriceMin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPriceMax, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPriceMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPriceMax, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        button2.setBackground(new java.awt.Color(102, 204, 255));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Apply Filters");
        button2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        button5.setBackground(new java.awt.Color(255, 102, 102));
        button5.setForeground(new java.awt.Color(255, 255, 255));
        button5.setText("Retype");
        button5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(cbbCheckExpiry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(button2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbTaste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtVolume, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbbSearchType, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(cbbSearchType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbTaste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbCheckExpiry, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product's name", "Taste", "Volume", "Stock quantity", "Entry date", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProduct);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Image", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(179, 177, 177))); // NOI18N

        lbproductgallery.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbproductgallery.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbproductgallery.setText("Image not found");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbproductgallery, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lbproductgallery, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("All");

        jRadioButton2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Active");

        jRadioButton3.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Out Of Stock");

        jRadioButton4.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("Hidden");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Info", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(178, 178, 178))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("ID:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        lbId.setText("0000000");
        jPanel3.add(lbId, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        jLabel5.setText("Name:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel6.setText("Product's name");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 150, -1));

        jLabel7.setText("Price:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        jLabel8.setText("000,000 VND");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, -1));

        jLabel9.setText("Amount:");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jLabel10.setText("0");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 60, -1));

        jLabel11.setText("Production Date:");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel12.setText("00-00-0000");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 70, -1));

        jLabel13.setText("Expiration Date:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        jLabel14.setText("00-00-0000");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 70, -1));

        jLabel15.setText("Provider:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        jLabel16.setText("Null");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 110, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 10, 150));

        jLabel17.setText("Taste:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, -1, -1));

        jLabel18.setText("Null");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, -1, -1));

        jLabel19.setText("Packaging Type:");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        jLabel20.setText("Null");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 80, -1));

        jLabel21.setText("Volume:");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, -1, -1));

        jLabel22.setText("Null Null");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 130, -1));

        jLabel23.setText("Brand:");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, -1, -1));

        jLabel24.setText("Null");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 130, -1));

        jLabel25.setText("Origin:");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, -1, -1));

        jLabel26.setText("Null");
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 150, -1));

        jLabel27.setText("Composition:");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, -1, -1));

        jLabel28.setText("Null");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, 140, -1));

        jLabel29.setText("Description:");
        jPanel3.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, -1, -1));

        jLabel30.setText("Null");
        jPanel3.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, 150, -1));

        button1.setBackground(new java.awt.Color(102, 204, 255));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("New");
        button1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        button3.setBackground(new java.awt.Color(102, 204, 255));
        button3.setForeground(new java.awt.Color(255, 255, 255));
        button3.setText("Update");
        button3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        button4.setBackground(new java.awt.Color(102, 204, 255));
        button4.setForeground(new java.awt.Color(255, 255, 255));
        button4.setText("Replenishment");
        button4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        lbCountPorduct.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbCountPorduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbCountPorduct.setText("0");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Products");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbCountPorduct, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addGap(102, 102, 102)
                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton4))
                            .addComponent(button3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbCountPorduct, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jRadioButton1)
                                    .addComponent(jRadioButton2)
                                    .addComponent(jRadioButton3)
                                    .addComponent(jRadioButton4)))
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button button1;
    private udpm.fpt.swing.Button button2;
    private udpm.fpt.swing.Button button3;
    private udpm.fpt.swing.Button button4;
    private udpm.fpt.swing.Button button5;
    private javax.swing.ButtonGroup buttonGroup1;
    private udpm.fpt.swing.Combobox cbbCheckExpiry;
    private udpm.fpt.swing.Combobox cbbPackagingSpecification;
    private udpm.fpt.swing.Combobox cbbSearchType;
    private udpm.fpt.swing.Combobox cbbTaste;
    private udpm.fpt.swing.Combobox cbbUnit;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbCountPorduct;
    private javax.swing.JLabel lbId;
    private javax.swing.JLabel lbproductgallery;
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
