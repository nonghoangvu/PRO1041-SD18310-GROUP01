package udpm.fpt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import udpm.fpt.repository.IProductInfo;

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
        IProductInfo pi = getBean(IProductInfo.class);
        pi.findAll().forEach(s -> {
            System.out.println(s.getUser().getUsername());
        });
    }
}
