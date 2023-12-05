package udpm.fpt.form;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import udpm.fpt.component.MessagePanel;
import udpm.fpt.component.ViewDelete;
import udpm.fpt.event.TableActionEvent;
import udpm.fpt.main.Main;
import udpm.fpt.model.HistoryProduct;
import udpm.fpt.model.Milk;
import udpm.fpt.model.ProductInfo;
import udpm.fpt.model.User;
import udpm.fpt.servicce.HistoryProductService;
import udpm.fpt.servicce.ProductService;
import udpm.fpt.swing.table.TableActionCellEditor;
import udpm.fpt.swing.table.TableActionCellRender;
import udpm.fpt.swing.table.TableCustom;

/**
 *
 * @author NONG HOANG VU
 */
public class History extends javax.swing.JPanel {

    private final List<ProductInfo> temp;
    private User user;
    private Main main;

    public History(User user, Main main) {
        initComponents();
        this.temp = new ArrayList<>();
        this.user = user;
        this.main = main;
        initTable();
    }

    private void initTable() {
        fillCatelogy();
        TableCustom.apply(jScrollPane1, TableCustom.TableType.MULTI_LINE);
        fillHistory();
    }

    private void fillCatelogy() {
        String[] catelogy = {"History", "Archive"};
        for (String s : catelogy) {
            cbbCatelogy.addItem(s);
        }
    }

    public void fillHistory() {
        HistoryProductService list = new HistoryProductService();
        CompletableFuture<List<HistoryProduct>> future = list.loadHistory();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                updateHistory(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    private void updateHistory(List<HistoryProduct> data) {
                btnRefresh.setEnabled(true);
        DefaultTableModel tblModel = (DefaultTableModel) tblHistory.getModel();
        tblModel.setColumnCount(0);
        tblModel.addColumn("ID");
        tblModel.addColumn("Description");
        tblModel.addColumn("Time");
        tblModel.addColumn("Username");
        tblModel.addColumn("Change Type");
        tblModel.setRowCount(0);
        for (HistoryProduct history : data) {
            tblModel.insertRow(0, new Object[]{
                history.getId(), history.getDescription(), history.getDatetime(), history.getUsername(), history.getChangeType()
            });
        }
    }

    public void fillArchive() {
        ProductService list = new ProductService();
        CompletableFuture<List<ProductInfo>> future = list.loadAsync();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                updateArchive(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    private Long getId() {
        ProductInfo id = temp.get(tblHistory.getSelectedRow());
        return id.getMilk().getId();
    }

    private void Restore() {
        ProductService productService = new ProductService();
        Milk m = this.temp.get(tblHistory.getSelectedRow()).getMilk();
        m.setIsDelete(false);
        if (productService.hideRestoreProduct(m, this.user)) {
            if (cbbCatelogy.getSelectedItem().equals("Archive")) {
                fillArchive();
            } else {
                fillHistory();
            }
            this.main.notificationShowSUCCESS("Restore success");
        }
    }

    private void Delete() {
        ProductService productService = new ProductService();
        ProductInfo pi = temp.get(tblHistory.getSelectedRow());
        System.out.println(productService.deleteProduct(pi.getMilk().getId(), pi.getId(), pi.getMilk(), this.user));
        fillArchive();
        this.main.notificationShowSUCCESS("Deleted");
    }

    private void updateArchive(List<ProductInfo> data) {
        btnRefresh.setEnabled(false);
        DefaultTableModel tblModel = (DefaultTableModel) tblHistory.getModel();
        tblModel.setColumnCount(0);
        tblModel.addColumn("ID");
        tblModel.addColumn("Name");
        tblModel.addColumn("Taste");
        tblModel.addColumn("Price");
        tblModel.addColumn("Amount");
        tblModel.addColumn("Provider");
        tblModel.addColumn("Action");
        tblModel.setRowCount(0);
        for (ProductInfo prd : data) {
            if (prd.getMilk().getIsDelete()) {
                tblModel.addRow(new Object[]{
                    prd.getMilk().getId(),
                    prd.getMilk().getProduct_name(),
                    prd.getFlavor().getTaste(),
                    prd.getMilk().getPrice(),
                    prd.getMilk().getAmount(),
                    prd.getMilk().getProvider()
                });
                this.temp.add(prd);
            }
        }
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onRestore(int row) {
                if (tblHistory.getSelectedRow() < 0) {
                    tblHistory.getCellEditor().stopCellEditing();
                }
                MessagePanel msg = new MessagePanel();
                msg.setTitle("Do you want to restore this product?");
                msg.setMessage("If you choose to restore this product, it will be moved back to the product management area.");
                msg.setResultCallback((Boolean result) -> {
                    if (result) {
                        Restore();
                    }
                });
                msg.setVisible(true);
            }

            @Override
            public void onDelete(int row) {
                if (tblHistory.getSelectedRow() < 0) {
                    tblHistory.getCellEditor().stopCellEditing();
                }
                MessagePanel msg = new MessagePanel();
                msg.setTitle("Are you sure you want to delete this product?");
                msg.setMessage("Warning: Deletion is irreversible and cannot be undone.");
                msg.setResultCallback((Boolean result) -> {
                    if (result) {
                        Delete();
                    }
                });
                msg.setVisible(true);
            }

            @Override
            public void onView(int row) {
                if (tblHistory.getSelectedRow() < 0) {
                    tblHistory.getCellEditor().stopCellEditing();
                }
                ProductInfo pi = temp.get(tblHistory.getSelectedRow());
                new ViewDelete(pi).setVisible(true);
            }
        };
        tblHistory.setRowHeight(40);
        tblHistory.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        tblHistory.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        tblHistory.getColumnModel().getColumn(6).setMinWidth(120);
        tblHistory.getColumnModel().getColumn(6).setMaxWidth(120);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();
        cbbCatelogy = new udpm.fpt.swing.Combobox();
        textField1 = new udpm.fpt.swing.TextField();
        btnRefresh = new udpm.fpt.swing.Button();

        setBackground(new java.awt.Color(255, 255, 255));

        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHistory.setSelectionForeground(new java.awt.Color(102, 204, 255));
        jScrollPane1.setViewportView(tblHistory);

        cbbCatelogy.setLabeText("");
        cbbCatelogy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbCatelogyItemStateChanged(evt);
            }
        });

        textField1.setLabelText("Search");

        btnRefresh.setBackground(new java.awt.Color(102, 204, 255));
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Refresh");
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbbCatelogy, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbCatelogy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbCatelogyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbCatelogyItemStateChanged
        if (cbbCatelogy.getSelectedIndex() == 0) {
            fillHistory();
        } else {
            fillArchive();
        }
    }//GEN-LAST:event_cbbCatelogyItemStateChanged

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        if (new HistoryProductService().Refresh()) {
            fillHistory();
            this.main.notificationShowSUCCESS("Refreshed");
        } else {
            this.main.notificationShowWARNING("Failed");
        }
    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button btnRefresh;
    private udpm.fpt.swing.Combobox cbbCatelogy;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblHistory;
    private udpm.fpt.swing.TextField textField1;
    // End of variables declaration//GEN-END:variables
}
