package udpm.fpt.Utitlity;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author NONG HOANG VU
 */
public class BcryptHash {

    public BcryptHash() {
    }

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public Boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
