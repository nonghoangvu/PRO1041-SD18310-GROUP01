package udpm.fpt.form;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import udpm.fpt.model.Flavor;
import udpm.fpt.model.Milk;
import udpm.fpt.servicce.FlavorService;
import udpm.fpt.swing.table.TableCustom;
import udpm.fpt.servicce.MilkService;

/**
 *
 * @author NONG HOANG VU
 */
public class Product extends javax.swing.JPanel implements Runnable, ThreadFactory {

    private WebcamPanel panel;
    public Webcam webcam;
    private final Executor executor = Executors.newSingleThreadExecutor(this);
    public Boolean status = true;
    private DefaultTableModel tblModel;
    private final MilkService list;

    public Product(Boolean status) {
        initComponents();
        this.list = new MilkService();
        this.status = status;
        initWebcam();
        TableCustom.apply(jScrollPane1, TableCustom.TableType.MULTI_LINE);
        fillTable();
        fillCombo();
    }

    public void fillCombo() {
        List<Flavor> listF = new FlavorService().getList();
        DefaultComboBoxModel<Flavor> cbbModel = new DefaultComboBoxModel<>();
        cbbFlavor.setModel((DefaultComboBoxModel)cbbModel);
        for (Flavor f : listF) {
            cbbModel.addElement(f);
        }
    }

    public void fillTable() {
        tblModel = (DefaultTableModel) tblProduct.getModel();
        tblModel.setRowCount(0);
        for (Milk milk : this.list.getList()) {
            tblModel.addRow(new Object[]{milk.getId(), milk.getProduct_name(), milk.getFlavor().getTaste(), milk.getPrice_retail(), milk.getPrice_wholesale(), milk.getAmount()});
        }
    }

    public void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);
        pnCam.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 210, 180));
        this.executor.execute(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnCam = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProduct = new javax.swing.JTable();
        cbbFlavor = new javax.swing.JComboBox<>();
        lbID = new javax.swing.JLabel();
        lbTaste = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        pnCam.setBackground(new java.awt.Color(255, 255, 255));
        pnCam.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Webcam", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnCam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Name", "Taste", "Price Retail", "Price Wholesale", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProduct);

        cbbFlavor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbFlavorItemStateChanged(evt);
            }
        });

        lbID.setText("Id");

        lbTaste.setText("taste");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1159, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTaste)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(cbbFlavor, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(87, 87, 87))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lbID, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(340, 340, 340)))
                                .addComponent(pnCam, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnCam, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbbFlavor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(lbID)
                        .addGap(18, 18, 18)
                        .addComponent(lbTaste)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbFlavorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbFlavorItemStateChanged
        Flavor selectedFlavor = (Flavor) cbbFlavor.getSelectedItem();
        lbID.setText(String.valueOf(selectedFlavor.getId()));
        lbTaste.setText(selectedFlavor.getTaste());
    }//GEN-LAST:event_cbbFlavorItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbbFlavor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbID;
    private javax.swing.JLabel lbTaste;
    private javax.swing.JPanel pnCam;
    private javax.swing.JTable tblProduct;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        try {
            do {
                System.out.println("Running thread...");
                Result result;
                BufferedImage image = null;
                if (webcam.isOpen()) {
                    if ((image = webcam.getImage()) == null) {
                        continue;
                    }
                }
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    result = new MultiFormatReader().decode(bitmap);
                    if (result.getText() != null) {
                        JOptionPane.showMessageDialog(this, result.getText());
                    }
                } catch (NotFoundException ex) {
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                }
            } while (this.status);
        } catch (HeadlessException e) {
        }
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My thread");
        t.setDaemon(true);
        return t;
    }

}
