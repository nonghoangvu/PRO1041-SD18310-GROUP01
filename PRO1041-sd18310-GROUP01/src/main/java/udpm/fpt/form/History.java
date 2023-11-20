package udpm.fpt.form;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import udpm.fpt.component.MessagePanel;
import udpm.fpt.component.ViewDelete;
import udpm.fpt.event.TableActionEvent;
import udpm.fpt.model.Milk;
import udpm.fpt.model.ProductInfo;
import udpm.fpt.servicce.ProductService;
import udpm.fpt.swing.table.TableActionCellEditor;
import udpm.fpt.swing.table.TableActionCellRender;
import udpm.fpt.swing.table.TableCustom;

/**
 *
 * @author NONG HOANG VU
 */
public class History extends javax.swing.JPanel {

    private DefaultTableModel tblModel;
    private final ProductService list;
    private final List<ProductInfo> temp;

    public History() {
        initComponents();
        this.list = new ProductService();
        this.temp = new ArrayList<>();
        tableFormat();
        fillCatelogy();
        fillDeleted();
    }

    private void fillCatelogy() {
        String[] catelogy = {"Archive", "Product edit history"};
        for (String s : catelogy) {
            cbbCatelogy.addItem(s);
        }
    }

    public void fillDeleted() {
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
        tblModel = (DefaultTableModel) tblHistory.getModel();
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
    }

    private void fillEdit() {
        tblModel = (DefaultTableModel) tblHistory.getModel();
        tblModel.setRowCount(0);
    }

    private Long getId() {
        ProductInfo id = temp.get(tblHistory.getSelectedRow());
        return id.getMilk().getId();
    }

    private void Restore() {
        ProductService productService = new ProductService();
        Milk m = productService.getMilkByID(getId());
        m.setIsDelete(false);
        if (productService.hideRestoreProduct(m)) {
            if (cbbCatelogy.getSelectedItem().equals("Archive")) {
                fillDeleted();
            } else {
                fillEdit();
            }
        }
    }

    private void Delete() {
        ProductService productService = new ProductService();
        ProductInfo pi = temp.get(tblHistory.getSelectedRow());
        System.out.println(productService.deleteProduct(pi.getMilk().getId(), pi.getId()));
        fillDeleted();
    }

    private void tableFormat() {
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
        TableCustom.apply(jScrollPane1, TableCustom.TableType.MULTI_LINE);
        tblHistory.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        tblHistory.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(event));
        tblHistory.getColumnModel().getColumn(6).setMinWidth(120);
        tblHistory.getColumnModel().getColumn(6).setMaxWidth(120);
        this.temp.clear();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();
        cbbCatelogy = new udpm.fpt.swing.Combobox();
        textField1 = new udpm.fpt.swing.TextField();
        button1 = new udpm.fpt.swing.Button();

        setBackground(new java.awt.Color(255, 255, 255));

        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Taste", "Price", "Amount", "Provider", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHistory.setSelectionForeground(new java.awt.Color(102, 204, 255));
        jScrollPane1.setViewportView(tblHistory);

        cbbCatelogy.setLabeText("History");
        cbbCatelogy.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbCatelogyItemStateChanged(evt);
            }
        });

        textField1.setLabelText("Search");

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/udpm/fpt/icon/search.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbbCatelogy, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbCatelogy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbCatelogyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbCatelogyItemStateChanged
        if (cbbCatelogy.getSelectedIndex() == 0) {
            fillDeleted();
        } else {
            fillEdit();
        }
    }//GEN-LAST:event_cbbCatelogyItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button button1;
    private udpm.fpt.swing.Combobox cbbCatelogy;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblHistory;
    private udpm.fpt.swing.TextField textField1;
    // End of variables declaration//GEN-END:variables
}
