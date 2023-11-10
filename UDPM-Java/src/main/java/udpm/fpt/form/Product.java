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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author NONG HOANG VU
 */
public class Product extends javax.swing.JPanel implements Runnable, ThreadFactory {

    private WebcamPanel panel;
    public Webcam webcam;
    private final Executor executor = Executors.newSingleThreadExecutor(this);
    public Boolean status = true;

    public Product(Boolean status) {
        initComponents();
        this.status = status;
        initWebcam();
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

        setBackground(new java.awt.Color(255, 255, 255));

        pnCam.setBackground(new java.awt.Color(255, 255, 255));
        pnCam.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Webcam", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        pnCam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(935, Short.MAX_VALUE)
                .addComponent(pnCam, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnCam, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(427, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnCam;
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
