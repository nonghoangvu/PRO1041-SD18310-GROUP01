package udpm.fpt.servicce;

import static udpm.fpt.Applocation.getBean;
import udpm.fpt.model.User;
import udpm.fpt.repository.IUser;

public class LoginService {

    private final IUser user = getBean(IUser.class);

    public LoginService() {
    }

    public User login(String username, String password) {
        User loggedInUser = user.findByUsernameAndPassword(username, password);
        if (username.isBlank() || password.isBlank()) {
            return null;
        } else if (loggedInUser.getUsername().equals(username) && loggedInUser.getPassword().equals(password)) {
            return loggedInUser;
        }
        return null;
    }
}
