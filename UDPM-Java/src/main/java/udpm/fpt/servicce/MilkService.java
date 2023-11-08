package udpm.fpt.servicce;

import java.util.ArrayList;
import java.util.List;
import static udpm.fpt.Main.getBean;
import udpm.fpt.model.Milk;
import udpm.fpt.repository.IMilk;

/**
 *
 * @author NONG HOANG VU
 */
public class MilkService {

    private List<Milk> list;
    private final IMilk r = getBean(IMilk.class);

    public MilkService() {
        this.list = new ArrayList<>();
    }

    public List<Milk> getList() {
        this.list.clear();
        this.list = r.findAll();
        return list;
    }

}
