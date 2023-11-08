package udpm.fpt.edu.view;

import javax.swing.table.DefaultTableModel;
import udpm.fpt.edu.model.Milk;
import udpm.fpt.edu.servicce.MilkService;

/**
 *
 * @author NONG HOANG VU
 */
public class HomeTest extends javax.swing.JFrame {

    private final MilkService list;
    private DefaultTableModel tblModel;

    public HomeTest() {
        initComponents();
        setLocationRelativeTo(null);
        this.list = new MilkService();
        fillTable();
    }

    private void fillTable() {
        tblModel = (DefaultTableModel) tblMilk.getModel();
        tblModel.setRowCount(0);
        for (Milk milk : this.list.getList()) {
            tblModel.addRow(new Object[]{milk.getId(), milk.getProduct_name(), milk.getPrice_retail(), milk.getAmount(), milk.getCreate_at()});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblMilk = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblMilk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "  Price retail", "Amount", "Create date"
            }
        ));
        jScrollPane1.setViewportView(tblMilk);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 90, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMilk;
    // End of variables declaration//GEN-END:variables
}
