package udpm.fpt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import udpm.fpt.main.Main;
import udpm.fpt.repository.IProductInfo;

/**
 *
 * @author NONG HOANG VU
 */
@SpringBootApplication
public class Applocation {

    private static ApplicationContext context = null;

    public static ApplicationContext getContext() {
        return context;
    }
    
    public static <T extends Object> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    private static ConfigurableApplicationContext createApplicationContext(String[] args) {
        return new SpringApplicationBuilder(Applocation.class)
                .headless(false)
                .run(args);
    }

    public static void main(String[] args) {
        context = createApplicationContext(args);
        IProductInfo r = getBean(IProductInfo.class);
        r.findAll().forEach(s -> {
            System.out.println(s.getMilk().getProduct_name() + " - " + s.getPackagingSpecification().getPackaging_type());
        });
        new Main().setVisible(true);
    }
}
