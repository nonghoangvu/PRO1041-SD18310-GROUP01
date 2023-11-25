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
        IUser iuser = getBean(IUser.class);
        iuser.findAll().forEach(per -> System.out.printf("%d  -  %s",per.getId(), per.getUsername()));

    }
}
