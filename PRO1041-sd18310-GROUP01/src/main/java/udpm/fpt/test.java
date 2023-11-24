package udpm.fpt;

import udpm.fpt.Utitlity.BcryptHash;

/**
 *
 * @author NONG HOANG VU
 */
public class test {
    public static void main(String[] args) {
        System.out.println(new BcryptHash().encodeBase64("Only administrators have access"));
    }
}
