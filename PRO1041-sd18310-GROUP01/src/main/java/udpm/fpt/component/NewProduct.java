package udpm.fpt.component;

import com.raven.datechooser.DateChooser;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.text.AbstractDocument;
import udpm.fpt.form.ProductForm;
import udpm.fpt.model.Flavor;
import udpm.fpt.model.Milk;
import udpm.fpt.model.PackagingSpecification;
import udpm.fpt.model.ProductInfo;
import udpm.fpt.model.Unit;
import udpm.fpt.model.User;
import udpm.fpt.service.ProductService;
import udpm.fpt.swing.NumberOnlyFilter;

/**
 *
 * @author NONG HOANG VU
 */
public class NewProduct extends javax.swing.JFrame {

    public ProductForm  perentForm;
    private final User user;
    private final ProductService list;
    private String imgName = null;

    public NewProduct(ProductForm perentForm, User user) {
        initComponents();
        this.list = new ProductService();
        this.user = user;
        textDate();
        this.perentForm = perentForm;
        filDataCombo();
        setTextField();
    }

    public void setTextField() {
        ((AbstractDocument) txtBarcode.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtPrice.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        txtExpirationDate.setText("00-00-0000");
        txtProductionDate.setText("00-00-0000");
        txtAmount.addChangeListener((ChangeEvent e) -> {
            int value = (int) txtAmount.getValue();
            if (value < 0) {
                txtAmount.setValue(0);
            }
        });
    }

    private void textDate() {
        getProductionDate();
        getExpirationDate();
    }

    private void getProductionDate() {
        DateChooser dateChooser = new DateChooser();
        dateChooser.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dateChooser.setLabelCurrentDayVisible(false);
        dateChooser.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        dateChooser.setTextField(txtProductionDate);
    }

    private void getExpirationDate() {
        DateChooser dateChooser = new DateChooser();
        dateChooser.setDateSelectionMode(DateChooser.DateSelectionMode.SINGLE_DATE_SELECTED);
        dateChooser.setLabelCurrentDayVisible(false);
        dateChooser.setDateFormat(new SimpleDateFormat("dd-MM-yyyy"));
        dateChooser.setTextField(txtExpirationDate);
    }

    public void setData(String data) {
        txtBarcode.setText(data);
    }

    public String getData() {
        return txtBarcode.getText();
    }

    public void filDataCombo() {
        loadDataAndFillFlavor();
        loadDataAndFillUnit();
        loadDataAndFillPackagingSpecification();
    }

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
        cbbTaste.setModel((DefaultComboBoxModel) cbbModel);
        for (Flavor flavor : data) {
            cbbModel.addElement(flavor);
        }
    }

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
        cbbUnit.setModel((DefaultComboBoxModel) cbbModel);
        for (Unit u : data) {
            cbbModel.addElement(u);
        }
    }

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
        cbbPackagingSpecification.setModel((DefaultComboBoxModel) cbbModel);
        for (PackagingSpecification specification : data) {
            cbbModel.addElement(specification);
        }
    }

    public Date dateFM(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date utilDate = dateFormat.parse(date);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return sqlDate;
        } catch (ParseException e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    private Milk getMilk() {
        Milk milk = new Milk();
        milk.setBarcode(Long.valueOf(txtBarcode.getText()));
        milk.setProduct_name(txtName.getText());
        milk.setImg(this.imgName);
        milk.setPrice(Integer.valueOf(txtPrice.getText()));
        milk.setAmount((int) txtAmount.getValue());
        milk.setProduction_date(dateFM(txtProductionDate.getText()));
        milk.setExpiration_date(dateFM(txtExpirationDate.getText()));
        milk.setProvider(txtProvider.getText());
        milk.setIsDelete(false);
        return milk;
    }

    private ProductInfo getProductInfo() {
        ProductInfo pi = new ProductInfo();
        pi.setMilk(getMilk());
        pi.setFlavor((Flavor) cbbTaste.getSelectedItem());
        pi.setBrand(txtBrand.getText());
        pi.setVolume(Double.valueOf(txtVolume.getText()));
        pi.setUnit((Unit) cbbUnit.getSelectedItem());
        pi.setOrigin(txtOrgin.getText());
        pi.setPackagingSpecification((PackagingSpecification) cbbPackagingSpecification.getSelectedItem());
        pi.setComposition(txtComposition.getText());
        pi.setProduct_description(txtDescription.getText());
        pi.setCreate_at(new Date());
        pi.setUser(this.user);
        return pi;
    }

    public String urlImage(Boolean get_set) {
        try {
            String currentDirectory = System.getProperty("user.dir")
                    + "/src/main/java/ProductGallery/";
            JFileChooser fileChooser = new JFileChooser(currentDirectory);
            fileChooser.showOpenDialog(null);
            File selectedFile = fileChooser.getSelectedFile();

            if (selectedFile != null) {
                if (get_set) {
                    return selectedFile.getName();
                }
                Image img = ImageIO.read(selectedFile);
                lbproductgallery.setText(null);
                lbproductgallery.setIcon(new ImageIcon(
                        img.getScaledInstance(lbproductgallery.getWidth(),
                                lbproductgallery.getHeight(),
                                lbproductgallery.getWidth())));
                return selectedFile.getName();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }

    private Boolean isDateValidNowDate(Date a, Date currentDate) {
        return a.compareTo(currentDate) <= 0;
    }

    private Boolean isDateValid(Date a, Date b) {
        Date currentDate = new Date();
        if (isDateValidNowDate(a, currentDate)) {
            return a.compareTo(b) > 0;
        }
        return true;
    }

    private Boolean isValidate() {
        if (txtBarcode.getText().isBlank()) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "The ID is empty!").showNotification();
            return false;
        } else if (txtName.getText().isBlank()) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "The product name is empty!").showNotification();
            return false;
        } else if (isDateValid(dateFM(txtProductionDate.getText()), dateFM(txtExpirationDate.getText()))) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Invalid date!").showNotification();
            return false;
        } else if (txtPrice.getText().isBlank()) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "The price is empty!").showNotification();
            return false;
        } else if (txtProvider.getText().isBlank()) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "The provider is empty!").showNotification();
            return false;
        } else if (txtBrand.getText().isBlank()) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "The brand is empty!").showNotification();
            return false;
        } else if (txtVolume.getText().isBlank()) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "You haven't entered the product volume!").showNotification();
            return false;
        } else if (txtOrgin.getText().isBlank()) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "No data added for the origin!").showNotification();
            return false;
        } else if (txtComposition.getText().isBlank()) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "The field for the component is empty.!").showNotification();
            return false;
        } else if (txtDescription.getText().isBlank()) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "You haven't added a product description!").showNotification();
            return true;
        } else if (txtName.getText().length() > 255) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Invalid product name!").showNotification();
            return false;
        } else if (txtProvider.getText().length() > 100) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Invalid provider!").showNotification();
            return false;
        } else if (txtBrand.getText().length() > 100) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Invalid brand!").showNotification();
            return false;
        } else if (txtOrgin.getText().length() > 50) {
            new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Invalid orgin!").showNotification();
            return false;
        } else {
            try {
                if (Integer.parseInt(txtPrice.getText()) < 4000) {
                    new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Invalid price!").showNotification();
                    return false;
                } else if (Float.parseFloat(txtVolume.getText()) < 0) {
                    new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Invalid volume!").showNotification();
                    return false;
                }
            } catch (NumberFormatException e) {
                new Notification(this, Notification.Type.WARNING, Notification.Location.DEFAULT_DESKTOP, "Invalid input data!").showNotification();
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtBarcode = new udpm.fpt.swing.TextField();
        button1 = new udpm.fpt.swing.Button();
        txtName = new udpm.fpt.swing.TextField();
        txtPrice = new udpm.fpt.swing.TextField();
        txtAmount = new udpm.fpt.swing.Spinner();
        txtExpirationDate = new udpm.fpt.swing.TextField();
        txtProductionDate = new udpm.fpt.swing.TextField();
        txtProvider = new udpm.fpt.swing.TextField();
        btnSave = new udpm.fpt.swing.ButtonMessage();
        btnCancel = new udpm.fpt.swing.ButtonMessage();
        jPanel3 = new javax.swing.JPanel();
        cbbTaste = new udpm.fpt.swing.Combobox();
        txtOrgin = new udpm.fpt.swing.TextField();
        txtVolume = new udpm.fpt.swing.TextField();
        cbbUnit = new udpm.fpt.swing.Combobox();
        txtBrand = new udpm.fpt.swing.TextField();
        cbbPackagingSpecification = new udpm.fpt.swing.Combobox();
        jPanel5 = new javax.swing.JPanel();
        lbproductgallery = new javax.swing.JLabel();
        textAreaScroll1 = new udpm.fpt.swing.TextAreaScroll();
        txtComposition = new udpm.fpt.swing.TextArea();
        textAreaScroll2 = new udpm.fpt.swing.TextAreaScroll();
        txtDescription = new udpm.fpt.swing.TextArea();
        btnNewFlavor = new udpm.fpt.swing.Button();
        btnNewUnit = new udpm.fpt.swing.Button();
        btnNewPackagingSpecification = new udpm.fpt.swing.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Product", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        txtBarcode.setLabelText("Barcode");

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/barcode-scanner.png"))); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        txtName.setLabelText("Name");

        txtPrice.setLabelText("Price");

        txtAmount.setLabelText("Amount");

        txtExpirationDate.setEditable(false);
        txtExpirationDate.setLabelText("Expiration Date");

        txtProductionDate.setLabelText("Production Date");

        txtProvider.setLabelText("Provider");

        btnSave.setBackground(new java.awt.Color(102, 204, 255));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 51, 51));
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtExpirationDate, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProductionDate, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtProductionDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtExpirationDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        cbbTaste.setLabeText("Taste");
        cbbTaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTasteActionPerformed(evt);
            }
        });

        txtOrgin.setLabelText("Origin");

        txtVolume.setLabelText("Volume");

        cbbUnit.setLabeText("Unit");

        txtBrand.setLabelText("Brand");

        cbbPackagingSpecification.setLabeText("Packaging Type");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbproductgallery.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbproductgallery.setText("Image");
        lbproductgallery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbproductgalleryMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbproductgallery, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbproductgallery, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
        );

        textAreaScroll1.setBackground(new java.awt.Color(255, 255, 255));
        textAreaScroll1.setLabelText("Composition");

        txtComposition.setColumns(20);
        txtComposition.setRows(5);
        textAreaScroll1.setViewportView(txtComposition);

        textAreaScroll2.setBackground(new java.awt.Color(255, 255, 255));
        textAreaScroll2.setLabelText("Description");

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        textAreaScroll2.setViewportView(txtDescription);

        btnNewFlavor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/insert.png"))); // NOI18N
        btnNewFlavor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewFlavorMouseClicked(evt);
            }
        });

        btnNewUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/insert.png"))); // NOI18N
        btnNewUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewUnitActionPerformed(evt);
            }
        });

        btnNewPackagingSpecification.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/insert.png"))); // NOI18N
        btnNewPackagingSpecification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewPackagingSpecificationMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(textAreaScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textAreaScroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(cbbTaste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNewFlavor, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtOrgin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBrand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnNewPackagingSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNewUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbTaste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNewFlavor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbbUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnNewUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtOrgin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNewPackagingSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textAreaScroll1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textAreaScroll2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbbUnit, txtVolume});

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        if (!isValidate()) {
            return;
        }
        if (this.list.insertProduct(getMilk(), getProductInfo())) {
            Notification n = new Notification(this, Notification.Type.SUCCESS, Notification.Location.DEFAULT_DESKTOP, "SUCCESS");
            n.showNotification();
            String data = txtBarcode.getText();
            perentForm.setData(data);
            dispose();
        } else {
            Notification n = new Notification(this, Notification.Type.INFO, Notification.Location.DEFAULT_DESKTOP, "Unable to add the product with this ID");
            n.showNotification();
        }
    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void cbbTasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTasteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbTasteActionPerformed

    private void lbproductgalleryMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lbproductgalleryMouseClicked
        this.imgName = urlImage(false);
    }

    private void btnNewUnitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNewUnitActionPerformed
        UnitManagement unitManagement = new UnitManagement(this.user);
        unitManagement.setResultCallback((Boolean result) -> {
            if (result) {
                loadDataAndFillUnit();
            }
        });
        unitManagement.setVisible(true);
    }

    private void btnNewPackagingSpecificationMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnNewPackagingSpecificationMouseClicked
        PackagingSpecificationManagement packagingSpecificationManagement = new PackagingSpecificationManagement(this.user);
        packagingSpecificationManagement.setResultCallback((Boolean result) -> {
            if (result) {
                loadDataAndFillPackagingSpecification();
            }
        });
        packagingSpecificationManagement.setVisible(true);
    }

    private void btnNewFlavorMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnNewFlavorMouseClicked
        FlavorManagement flavorManagement = new FlavorManagement(this.user);
        flavorManagement.setResultCallback((Boolean result) -> {
            if (result) {
                loadDataAndFillFlavor();
            }
        });
        flavorManagement.setVisible(true);
    }

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button1ActionPerformed
        WebCamProduct wco = new WebCamProduct(this);
        wco.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage btnCancel;
    private udpm.fpt.swing.Button btnNewFlavor;
    private udpm.fpt.swing.Button btnNewPackagingSpecification;
    private udpm.fpt.swing.Button btnNewUnit;
    private udpm.fpt.swing.ButtonMessage btnSave;
    private udpm.fpt.swing.Button button1;
    private udpm.fpt.swing.Combobox cbbPackagingSpecification;
    private udpm.fpt.swing.Combobox cbbTaste;
    private udpm.fpt.swing.Combobox cbbUnit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbproductgallery;
    private udpm.fpt.swing.TextAreaScroll textAreaScroll1;
    private udpm.fpt.swing.TextAreaScroll textAreaScroll2;
    private udpm.fpt.swing.Spinner txtAmount;
    private udpm.fpt.swing.TextField txtBarcode;
    private udpm.fpt.swing.TextField txtBrand;
    private udpm.fpt.swing.TextArea txtComposition;
    private udpm.fpt.swing.TextArea txtDescription;
    private udpm.fpt.swing.TextField txtExpirationDate;
    private udpm.fpt.swing.TextField txtName;
    private udpm.fpt.swing.TextField txtOrgin;
    private udpm.fpt.swing.TextField txtPrice;
    private udpm.fpt.swing.TextField txtProductionDate;
    private udpm.fpt.swing.TextField txtProvider;
    private udpm.fpt.swing.TextField txtVolume;
    // End of variables declaration//GEN-END:variables
}
