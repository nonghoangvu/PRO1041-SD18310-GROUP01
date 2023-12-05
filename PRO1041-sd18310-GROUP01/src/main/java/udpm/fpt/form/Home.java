package udpm.fpt.form;

import java.awt.Color;
import udpm.fpt.swing.Chart.blankchart.charts.ModelChart;

/**
 *
 * @author NONG HOANG VU
 */
public class Home extends javax.swing.JPanel {

    public Home() {
        initComponents();
        chart.addLegend("A", new Color(245, 189, 135));
        chart.addLegend("B", new Color(135, 189, 245));
        chart.addLegend("C", new Color(189, 135, 245));
        chart.addLegend("D", new Color(139, 229, 222));
        chart.addData(new ModelChart("1", new double[]{500, 200, 80, 89}));
        chart.addData(new ModelChart("2", new double[]{600, 750, 90, 150}));
        chart.addData(new ModelChart("3", new double[]{200, 350, 460, 900}));
        chart.addData(new ModelChart("4", new double[]{480, 150, 750, 700}));
        chart.addData(new ModelChart("5", new double[]{350, 540, 300, 150}));
        chart.addData(new ModelChart("6", new double[]{190, 280, 81, 200}));
        chart.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chart = new udpm.fpt.swing.Chart.blankchart.charts.ChartMain();
        imageAvatar1 = new udpm.fpt.swing.ImageAvatar();

        setBackground(new java.awt.Color(255, 255, 255));

        imageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/cowGif.gif"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(206, 206, 206)
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(187, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(269, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Chart.blankchart.charts.ChartMain chart;
    private udpm.fpt.swing.ImageAvatar imageAvatar1;
    // End of variables declaration//GEN-END:variables
}
