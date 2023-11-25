package udpm.fpt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import udpm.fpt.Utitlity.BcryptHash;
import udpm.fpt.repository.IUser;

/**
 *
 * @author NONG HOANG VU
 */
@SpringBootApplication
public class test {

    private static ApplicationContext context = null;

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T extends Object> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    private static ConfigurableApplicationContext createApplicationContext(String[] args) {
        return new SpringApplicationBuilder(test.class)
                .headless(false)
                .run(args);
    }

    public static void main(String[] args) {
        context = createApplicationContext(args);
        IUser r = getBean(IUser.class);
        r.findAll().forEach(s -> System.out.printf("Username: %s\nPassword: %s", s.getUsername(), s.getPassword()));
    }
}
