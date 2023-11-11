package udpm.fpt.servicce;

import java.util.ArrayList;
import java.util.List;
import static udpm.fpt.Applocation.getBean;
import udpm.fpt.model.Milk;
import udpm.fpt.repository.IMilk;

/**
 *
 * @author NONG HOANG VU
 */
public class MilkService {

    private List<Milk> list;
    private IMilk r = getBean(IMilk.class);

    public MilkService() {
        this.list = new ArrayList<>();
    }

    public List<Milk> getList() {
        this.list.clear();
        this.list = r.findAllWithFlavor();
        return list;
    }
    public List<Milk> getElementById(Long id) {
        this.list.clear();
//        this.list = r.findById(id);
        return list;
    }
}
