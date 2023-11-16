package udpm.fpt.component;

import com.raven.datechooser.DateChooser;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.text.AbstractDocument;
import udpm.fpt.form.ProductManagement;
import udpm.fpt.model.Flavor;
import udpm.fpt.model.Milk;
import udpm.fpt.model.PackagingSpecification;
import udpm.fpt.model.ProductInfo;
import udpm.fpt.model.Unit;
import udpm.fpt.servicce.ProductService;
import udpm.fpt.swing.NumberOnlyFilter;

/**
 *
 * @author NONG HOANG VU
 */
public class UpdateProduct extends javax.swing.JFrame {

    public ProductManagement perentForm;
    private final ProductService list;
    private String imgName = null;
    private ProductInfo pi;

    public UpdateProduct(ProductManagement perentForm, ProductInfo pi) {
        initComponents();
        this.list = new ProductService();
        textDate();
        this.perentForm = perentForm;
        this.pi = pi;
        this.imgName = this.pi.getMilk().getImg();
        filDataCombo();
        setTextField();
        loadData();
    }

    public void setTextField() {
        ((AbstractDocument) txtId.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        ((AbstractDocument) txtPrice.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        txtAmount.addChangeListener((ChangeEvent e) -> {
            int value = (int) txtAmount.getValue();
            if (value < 0) {
                txtAmount.setValue(0);
            }
        });
    }

    private void loadData() {
        txtId.setText(String.valueOf(this.pi.getMilk().getId()));
        txtBrand.setText(this.pi.getBrand());
        txtName.setText(this.pi.getMilk().getProduct_name());
        txtPrice.setText(String.valueOf(this.pi.getMilk().getPrice()));
        txtAmount.setValue(this.pi.getMilk().getAmount());
        txtProductionDate
                .setText(removeTimeUsingDateTimeFormatter(
                        String.valueOf(this.pi.getMilk().getProduction_date())));
        txtExpirationDate
                .setText(removeTimeUsingDateTimeFormatter(
                        String.valueOf(this.pi.getMilk().getExpiration_date())));
        txtVolume.setText(String.valueOf(this.pi.getVolume()));
        txtDescription.setText(this.pi.getProduct_description());
        txtComposition.setText(this.pi.getComposition());
        txtOrgin.setText(this.pi.getOrigin());
        txtProvider.setText(this.pi.getMilk().getProvider());
        cbbTaste.setSelectedItem(this.pi.getFlavor());
        cbbUnit.setSelectedItem(this.pi.getUnit());
        cbbPackagingSpecification.setSelectedItem(this.pi.getPackagingSpecification());
        setImange(this.pi.getMilk().getImg());
    }

    private void setImange(String url) {
        lbproductgallery.setText(null);
        try {
            int labelWidth = lbproductgallery.getWidth();
            int labelHeight = lbproductgallery.getHeight();
            ImageIcon originalIcon = new javax.swing.ImageIcon(
                    getClass().getResource("/udpm/fpt/productgallery/" + url));
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight,
                    Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            lbproductgallery.setIcon(scaledIcon);
        } catch (Exception e) {
            lbproductgallery.setIcon(null);
            lbproductgallery.setText("Image not found");
        }
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
        txtId.setText(data);
    }

    public String getData() {
        return txtId.getText();
    }

    public void filDataCombo() {
        dataFlavor();
        dataUnit();
        dataPackagingSpecification();
    }

    private void dataFlavor() {
        DefaultComboBoxModel<Flavor> cbbModel = new DefaultComboBoxModel<>();
        cbbTaste.setModel((DefaultComboBoxModel) cbbModel);
        for (Flavor flavor : this.list.getFlavor()) {
            cbbModel.addElement(flavor);
        }
    }

    private void dataUnit() {
        DefaultComboBoxModel<Unit> cbbModel = new DefaultComboBoxModel<>();
        cbbUnit.setModel((DefaultComboBoxModel) cbbModel);
        for (Unit unit : this.list.getUnit()) {
            cbbModel.addElement(unit);
        }
    }

    private void dataPackagingSpecification() {
        DefaultComboBoxModel<PackagingSpecification> cbbModel = new DefaultComboBoxModel<>();
        cbbPackagingSpecification.setModel((DefaultComboBoxModel) cbbModel);
        for (PackagingSpecification packagingSpecification : this.list.getPackagingSpecification()) {
            cbbModel.addElement(packagingSpecification);
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

    public String removeTimeUsingDateTimeFormatter(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
        return dateTime.format(outputFormatter);
    }

    private Boolean isDateValid(Date a, Date b) {
        return a.compareTo(b) > 0;
    }

    private Boolean isValidate() {
        if (txtId.getText().isBlank()) {
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

    private Milk getMilk() {
        Milk milk = new Milk();
        milk.setId(this.pi.getMilk().getId());
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
        ProductInfo p = new ProductInfo();
        p.setId(this.pi.getId());
        p.setMilk(getMilk());
        p.setFlavor((Flavor) cbbTaste.getSelectedItem());
        p.setBrand(txtBrand.getText());
        p.setVolume(Double.valueOf(txtVolume.getText()));
        p.setUnit((Unit) cbbUnit.getSelectedItem());
        p.setOrigin(txtOrgin.getText());
        p.setPackagingSpecification((PackagingSpecification) cbbPackagingSpecification.getSelectedItem());
        p.setComposition(txtComposition.getText());
        p.setProduct_description(txtDescription.getText());
        p.setCreate_at(new Date());
        p.setCreate_by("NongHoangVu");
        return p;
    }

    public String urlImage(Boolean get_set) {
        try {
            String currentDirectory = System.getProperty("user.dir")
                    + "/src/main/java/udpm/fpt/productgallery/";
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
        txtId = new udpm.fpt.swing.TextField();
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

        txtId.setEditable(false);
        txtId.setLabelText("ID");

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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtExpirationDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProductionDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProvider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        cbbTaste.setLabeText("Taste");

        txtOrgin.setLabelText("Origin");

        txtVolume.setLabelText("Volume");

        cbbUnit.setLabeText("Unit");

        txtBrand.setLabelText("Brand");

        cbbPackagingSpecification.setLabeText("Packaging Type");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbproductgallery.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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

        btnNewFlavor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/udpm/fpt/icon/plus.png"))); // NOI18N
        btnNewFlavor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewFlavorMouseClicked(evt);
            }
        });

        btnNewUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/udpm/fpt/icon/plus.png"))); // NOI18N
        btnNewUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewUnitActionPerformed(evt);
            }
        });

        btnNewPackagingSpecification.setIcon(new javax.swing.ImageIcon(getClass().getResource("/udpm/fpt/icon/plus.png"))); // NOI18N
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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(cbbTaste, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNewFlavor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNewUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtOrgin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBrand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(cbbPackagingSpecification, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNewPackagingSpecification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        if (this.list.updateProduct(getMilk(), getProductInfo())) {
            Notification n = new Notification(this, Notification.Type.SUCCESS,
                    Notification.Location.DEFAULT_DESKTOP, "SUCCESS");
            n.showNotification();
            String data = txtId.getText();
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

    private void lbproductgalleryMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lbproductgalleryMouseClicked
        this.imgName = urlImage(false);
    }

    private void btnNewUnitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNewUnitActionPerformed
        String measurement_unit = JOptionPane.showInputDialog("New measurement unit");
        Date defaultValue = new Date();
        Unit unit = new Unit();
        unit.setMeasurement_unit(measurement_unit);
        unit.setCreate_at(defaultValue);
        unit.setCreate_by("NongHoangVu");
        if (this.list.insertUnit(unit)) {
            cbbUnit.removeAll();
            dataUnit();
            Notification n = new Notification(this, Notification.Type.SUCCESS,
                    Notification.Location.DEFAULT_DESKTOP, "SUCCESS");
            n.showNotification();
        } else {
            Notification n = new Notification(this, Notification.Type.INFO, Notification.Location.DEFAULT_DESKTOP, "FAILED");
            n.showNotification();
        }
    }

    private void btnNewPackagingSpecificationMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnNewPackagingSpecificationMouseClicked
        String packagingType = JOptionPane.showInputDialog("New packaging type");
        Date defaultValue = new Date();
        PackagingSpecification packagingSpecification = new PackagingSpecification();
        packagingSpecification.setPackaging_type(packagingType);
        packagingSpecification.setCreate_at(defaultValue);
        packagingSpecification.setCreate_by("NongHoangVu");
        if (this.list.insertPackagingSpecification(packagingSpecification)) {
            cbbUnit.removeAll();
            dataPackagingSpecification();
            Notification n = new Notification(this, Notification.Type.SUCCESS,
                    Notification.Location.DEFAULT_DESKTOP, "SUCCESS");
            n.showNotification();
        } else {
            Notification n = new Notification(this, Notification.Type.INFO, Notification.Location.DEFAULT_DESKTOP,
                    "FAILED");
            n.showNotification();
        }
    }

    private void btnNewFlavorMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnNewFlavorMouseClicked
        String taste = JOptionPane.showInputDialog("New Taste");
        Date defaultValue = new Date();
        Flavor flavor = new Flavor();
        flavor.setTaste(taste);
        flavor.setCreate_at(defaultValue);
        flavor.setCreate_by("NongHoangVu");
        if (this.list.insertFlavor(flavor)) {
            cbbTaste.removeAll();
            dataFlavor();
            Notification n = new Notification(this, Notification.Type.SUCCESS,
                    Notification.Location.DEFAULT_DESKTOP, "SUCCESS");
            n.showNotification();
        } else {
            Notification n = new Notification(this, Notification.Type.INFO, Notification.Location.DEFAULT_DESKTOP,
                    "FAILED");
            n.showNotification();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.ButtonMessage btnCancel;
    private udpm.fpt.swing.Button btnNewFlavor;
    private udpm.fpt.swing.Button btnNewPackagingSpecification;
    private udpm.fpt.swing.Button btnNewUnit;
    private udpm.fpt.swing.ButtonMessage btnSave;
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
    private udpm.fpt.swing.TextField txtBrand;
    private udpm.fpt.swing.TextArea txtComposition;
    private udpm.fpt.swing.TextArea txtDescription;
    private udpm.fpt.swing.TextField txtExpirationDate;
    private udpm.fpt.swing.TextField txtId;
    private udpm.fpt.swing.TextField txtName;
    private udpm.fpt.swing.TextField txtOrgin;
    private udpm.fpt.swing.TextField txtPrice;
    private udpm.fpt.swing.TextField txtProductionDate;
    private udpm.fpt.swing.TextField txtProvider;
    private udpm.fpt.swing.TextField txtVolume;
    // End of variables declaration//GEN-END:variables
}
