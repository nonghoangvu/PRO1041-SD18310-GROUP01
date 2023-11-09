package udpm.fpt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import udpm.fpt.main.Main;

/**
 *
 * @author NONG HOANG VU
 */
@SpringBootApplication
public class App {

    private static ApplicationContext context = null;

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T extends Object> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    private static ConfigurableApplicationContext createApplicationContext(String[] args) {
        return new SpringApplicationBuilder(Main.class)
                .headless(false)
                .run(args);
    }

    public static void main(String[] args) {
        context = createApplicationContext(args);
        // Call Frame
        new Main().setVisible(true);

        // Test data
        // IMilk r = getBean(IMilk.class);
        // r.findAll().forEach(milk -> {
        // System.out.println(milk.getProduct_name());
        // });
    }
}
