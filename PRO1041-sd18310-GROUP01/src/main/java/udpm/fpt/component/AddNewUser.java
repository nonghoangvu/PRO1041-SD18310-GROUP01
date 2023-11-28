package udpm.fpt.component;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import udpm.fpt.Utitlity.BcryptHash;
import udpm.fpt.main.Main;
import udpm.fpt.model.Salary;
import udpm.fpt.model.User;
import udpm.fpt.model.UserDetails;
import udpm.fpt.servicce.SalaryService;
import udpm.fpt.servicce.UserService;

/**
 *
 * @author BinhQuoc
 */
public class AddNewUser extends javax.swing.JFrame {

    SalaryService salaryService = new SalaryService();
    UserService userService = new UserService();
    private final Main main;
    private String imgName = null;

    public AddNewUser(Main main) {
        initComponents();
        setLocationRelativeTo(null);
        this.main = main;
        loadDataAndFillSalary();
    }

    public void loadDataAndFillSalary() {
        CompletableFuture<List<Salary>> future = this.salaryService.getList();
        future.thenAcceptAsync(data -> {
            SwingUtilities.invokeLater(() -> {
                updateSalary(data);
            });
        }).exceptionally(throwable -> {
            throwable.printStackTrace(System.out);
            return null;
        });
    }

    private void updateSalary(List<Salary> data) {
        DefaultComboBoxModel<Salary> cbbModel = new DefaultComboBoxModel<>();
        cboSalary.removeAll();
        cboSalary.setModel((DefaultComboBoxModel) cbbModel);
        for (Salary salary : data) {
            cbbModel.addElement(salary);
        }
    }

    public User createNewUser() {
        User user = new User();
        String username = txtUsername.getText().trim();
        if (username.isBlank() || username.length() < 5) {
            this.main.notificationShowWARNING("Username is a required field and must contain at least 5 characters !!!");
            txtUsername.requestFocus();
            return null;
        } else {
            user.setUsername(username);
        }

        String password = String.valueOf(txtPassword.getPassword()).trim();
        if (password.isBlank() || password.length() < 5) {
            this.main.notificationShowWARNING("Password is a required field and must contain at least 5 characters !!!");
            txtPassword.requestFocus();
            return null;
        } else {
            user.setPassword(new BcryptHash().hashPassword(password));
        }

        user.setRole((rdoAdmin.isSelected() ? "Admin" : "User"));

        return user;
    }

    public UserDetails createNewUserDetails() {
        UserDetails userDetails = new UserDetails();

        userDetails.setUser(createNewUser());
        userDetails.setSalary((Salary) cboSalary.getSelectedItem());

        String fullName = txtFullname.getText().trim();
        userDetails.setFullname(fullName);
        String phonenum = txtPhonenum.getText().trim();
        userDetails.setTel(phonenum);
//        if (fullName.isBlank() || fullName.length() < 5) {
//            this.main.notificationShowWARNING("FullName is a required field and must contain at least 5 characters !!!");
//            txtFullname.requestFocus();
//            return;
//        } else {
//            userDetails.setFullname(fullName);
//        }

//        String phonenum = txtPhonenum.getText().trim();
//        if (phonenum.isBlank() || phonenum.length() < 5) {
//            this.main.notificationShowWARNING("Phonenum is a required field and must contain at least 5 characters !!!");
//            txtPhonenum.requestFocus();
//            return null;
//        } else {
//            userDetails.setTel(phonenum);
//        }
        userDetails.setPhoto(this.imgName);
        userDetails.setEmail(txtEmail.getText().trim());
        userDetails.setJobPosition(txtPosition.getText().trim());
        userDetails.setCitizenId(txtCitizenID.getText().trim());
        userDetails.setAddress(txtAddress.getText().trim());
        userDetails.setNote(txtNote.getText().trim());
        userDetails.setStatus("Active");

        return userDetails;
    }

    public void addNewUser() {

        System.out.println(createNewUserDetails().getSalary().getId());
        if (this.userService.addNewUser(createNewUserDetails())) {
            this.main.notificationShowWARNING("Added a new employee !!!");
            this.dispose();
        } else {
            this.main.notificationShowWARNING("Failed !!!");
        }
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
                lblAvatar.setText(null);
                lblAvatar.setIcon(new ImageIcon(
                        img.getScaledInstance(lblAvatar.getWidth(),
                                lblAvatar.getHeight(),
                                lblAvatar.getWidth())));
                return selectedFile.getName();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        genderRdoGroup = new javax.swing.ButtonGroup();
        rdoRoleGroup = new javax.swing.ButtonGroup();
        txtFullname = new udpm.fpt.swing.TextField();
        txtUsername = new udpm.fpt.swing.TextField();
        jLabel1 = new javax.swing.JLabel();
        imageAvatar1 = new udpm.fpt.swing.ImageAvatar();
        lblAvatar = new javax.swing.JLabel();
        txtPassword = new udpm.fpt.swing.PasswordField();
        jLabel2 = new javax.swing.JLabel();
        rdoAdmin = new javax.swing.JRadioButton();
        rdoUser = new javax.swing.JRadioButton();
        cboSalary = new udpm.fpt.swing.Combobox();
        txtPhonenum = new udpm.fpt.swing.TextField();
        txtEmail = new udpm.fpt.swing.TextField();
        txtCitizenID = new udpm.fpt.swing.TextField();
        txtPosition = new udpm.fpt.swing.TextField();
        jLabel3 = new javax.swing.JLabel();
        rdoFemale = new javax.swing.JRadioButton();
        rdoMale = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnAdd = new udpm.fpt.swing.Button();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        txtFullname.setLabelText("Fullname");
        txtFullname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFullnameActionPerformed(evt);
            }
        });

        txtUsername.setLabelText("Username");
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Adding new user");

        imageAvatar1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblAvatar.setText("No image choosed");
        lblAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAvatarMouseClicked(evt);
            }
        });
        imageAvatar1.add(lblAvatar);
        lblAvatar.setBounds(0, 0, 190, 180);

        txtPassword.setLabelText("Password");
        txtPassword.setShowAndHide(true);

        jLabel2.setText("Role");

        rdoRoleGroup.add(rdoAdmin);
        rdoAdmin.setText("Admin");

        rdoRoleGroup.add(rdoUser);
        rdoUser.setSelected(true);
        rdoUser.setText("User");

        cboSalary.setLabeText("Salary");

        txtPhonenum.setLabelText("Phone number");
        txtPhonenum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhonenumActionPerformed(evt);
            }
        });

        txtEmail.setLabelText("Email");
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        txtCitizenID.setLabelText("Citizen ID");
        txtCitizenID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCitizenIDActionPerformed(evt);
            }
        });

        txtPosition.setLabelText("Job position");
        txtPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPositionActionPerformed(evt);
            }
        });

        jLabel3.setText("Gender");

        genderRdoGroup.add(rdoFemale);
        rdoFemale.setText("Female");

        genderRdoGroup.add(rdoMale);
        rdoMale.setSelected(true);
        rdoMale.setText("Male");

        jLabel4.setText("Address");

        jLabel5.setText("Note");

        btnAdd.setText("Add new user");
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddMouseClicked(evt);
            }
        });

        jButton1.setText("jButton1");

        txtNote.setColumns(20);
        txtNote.setRows(5);
        jScrollPane3.setViewportView(txtNote);

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane4.setViewportView(txtAddress);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(321, 321, 321)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(449, 449, 449))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(rdoUser)
                                            .addGap(18, 18, 18)
                                            .addComponent(rdoAdmin))
                                        .addComponent(jLabel3)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(rdoMale)
                                            .addGap(18, 18, 18)
                                            .addComponent(rdoFemale)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(68, 68, 68)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtPhonenum, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                                    .addComponent(txtFullname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboSalary, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(68, 68, 68)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                            .addComponent(txtPosition, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCitizenID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane4))
                        .addGap(148, 148, 148))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPosition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdoUser)
                                    .addComponent(rdoAdmin)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCitizenID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel3)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoMale)
                            .addComponent(rdoFemale))
                        .addGap(18, 18, 18)
                        .addComponent(txtFullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPhonenum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtFullnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFullnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFullnameActionPerformed

    private void txtPhonenumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhonenumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhonenumActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtCitizenIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCitizenIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCitizenIDActionPerformed

    private void txtPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPositionActionPerformed

    private void btnAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddMouseClicked
        // TODO add your handling code here:
        addNewUser();
    }//GEN-LAST:event_btnAddMouseClicked

    private void lblAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAvatarMouseClicked
        // TODO add your handling code here:
        this.imgName = urlImage(false);

    }//GEN-LAST:event_lblAvatarMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Button btnAdd;
    private udpm.fpt.swing.Combobox cboSalary;
    private javax.swing.ButtonGroup genderRdoGroup;
    private udpm.fpt.swing.ImageAvatar imageAvatar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JRadioButton rdoAdmin;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.ButtonGroup rdoRoleGroup;
    private javax.swing.JRadioButton rdoUser;
    private javax.swing.JTextArea txtAddress;
    private udpm.fpt.swing.TextField txtCitizenID;
    private udpm.fpt.swing.TextField txtEmail;
    private udpm.fpt.swing.TextField txtFullname;
    private javax.swing.JTextArea txtNote;
    private udpm.fpt.swing.PasswordField txtPassword;
    private udpm.fpt.swing.TextField txtPhonenum;
    private udpm.fpt.swing.TextField txtPosition;
    private udpm.fpt.swing.TextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
