package udpm.fpt;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import udpm.fpt.Utitlity.BcryptHash;
import udpm.fpt.model.Salary;
import udpm.fpt.model.User;
import udpm.fpt.model.UserDetails;
import udpm.fpt.repository.IUser;
import udpm.fpt.repository.IUserDetails;

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
        IUserDetails iuserDetails = getBean(IUserDetails.class);
        IUser iuser = getBean(IUser.class);

        List<UserDetails> list = iuserDetails.findAll().stream()
                .filter(user -> user.getSalary().getId() == 1)
                .collect(Collectors.toList());
        list.forEach(s -> System.out.println(s.getFullname()));

    }
}
