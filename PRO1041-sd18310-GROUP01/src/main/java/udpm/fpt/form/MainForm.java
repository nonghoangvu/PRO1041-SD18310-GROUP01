package udpm.fpt.form;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import udpm.fpt.model.User;

public class MainForm extends javax.swing.JPanel {

    public User user;

    public MainForm(User user) {
        this.user = user;
        initComponents();
        show(new Home());
        header1.setUsername(this.user.getUsername());
        header1.setRole(this.user.getRole());
    }

    public User getUser() {
        return user;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header1 = new udpm.fpt.component.Header();
        body = new javax.swing.JPanel();

        setBackground(new java.awt.Color(250, 250, 250));

        body.setOpaque(false);
        body.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header1, javax.swing.GroupLayout.DEFAULT_SIZE, 1500, Short.MAX_VALUE)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void addEventMenu(ActionListener event) {
        header1.addEventMenu(event);
    }

    public void initMoving(JFrame fram) {
        header1.initMoving(fram);
    }

    public void show(Component com) {
        body.removeAll();
        body.add(com);
        body.repaint();
        body.revalidate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private udpm.fpt.component.Header header1;
    // End of variables declaration//GEN-END:variables
}
