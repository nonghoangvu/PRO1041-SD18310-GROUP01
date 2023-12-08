package udpm.fpt.service;

import java.util.Date;
import java.util.List;
import static udpm.fpt.Application.getBean;
import udpm.fpt.model.SaleBill1;
import udpm.fpt.repository.ISaleBill;

public class SaleBillService {

    private ISaleBill isb = getBean(ISaleBill.class);

    public SaleBillService() {
    }

    public List<SaleBill1> getAll() {
        return this.isb.findAll();
    }

    public boolean addNew(SaleBill1 sb) {
        return this.isb.save(sb) != null;
    }

    public boolean deleteBill(int ID) {
        try {
            this.isb.deleteById(ID);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public boolean updateBill(SaleBill1 sb) {
        return this.isb.save(sb) != null;
    }
    
    public List<SaleBill1> Search(Date ed ){
        return this.isb.findByEndDate(ed);
    }
}
