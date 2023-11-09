package udpm.fpt.servicce;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import udpm.fpt.model.Milk;
import udpm.fpt.repository.IMilk;

/**
 *
 * @author NONG HOANG VU
 */
@SpringBootApplication
public class MilkService {

    private static ApplicationContext context = null;
    private List<Milk> list;
    private final IMilk r = getBean(IMilk.class);

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T extends Object> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    private static ConfigurableApplicationContext createApplicationContext(String[] args) {
        return new SpringApplicationBuilder(MilkService.class)
                .headless(false)
                .run(args);
    }

    public static void main(String[] args) {
        context = createApplicationContext(args);
    }

    public MilkService() {
        this.list = new ArrayList<>();
    }

    public List<Milk> getList() {
        this.list.clear();
        this.list = r.findAll();
        return list;
    }

}
