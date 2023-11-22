package udpm.fpt.servicce;

import static udpm.fpt.Applocation.getBean;
import udpm.fpt.model.User;
import udpm.fpt.repository.IUser;

public class LoginService {

    private IUser user = getBean(IUser.class);

    public LoginService() {
    }

    public User login(String username, String password) {
        return this.user.findByUsernameAndPassword(username, password);
    }
}
