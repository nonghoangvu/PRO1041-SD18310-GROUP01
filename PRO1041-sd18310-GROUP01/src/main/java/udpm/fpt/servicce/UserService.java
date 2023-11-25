/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.fpt.servicce;

import java.util.List;
import lombok.NoArgsConstructor;
import udpm.fpt.model.UserDetails;
import static udpm.fpt.Applocation.getBean;
import udpm.fpt.repository.IUser;
import udpm.fpt.repository.IUserDetails;
/**
 *
 * @author Administrator
 */
@NoArgsConstructor
public class UserService {
       private final IUserDetails iUserDetails = getBean(IUserDetails.class);
       
       public List<UserDetails> getList() {
           return iUserDetails.findAll();
       }
}
