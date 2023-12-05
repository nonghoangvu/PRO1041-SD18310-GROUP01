package udpm.fpt.servicce;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static udpm.fpt.Application.getBean;

import udpm.fpt.model.HistoryProduct;
import udpm.fpt.repository.IHistoryProduct;

/**
 * @author NONG HOANG VU
 */
public class HistoryProductService {

    private final IHistoryProduct iHistoryProduct = getBean(IHistoryProduct.class);

    public HistoryProductService() {
    }

    public CompletableFuture<List<HistoryProduct>> loadHistory() {
        return CompletableFuture.supplyAsync(() -> {
            return iHistoryProduct.findAll();
        }, Executors.newCachedThreadPool());
    }

    public Boolean Refresh() {
        try {
            iHistoryProduct.deleteAll();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public enum ChangeType {
        NEW, UPDATE, REMOVE, UNKNOWN
    }

    public Boolean trackHistory(String description, String username, ChangeType changeType) {
        HistoryProduct historyProduct = new HistoryProduct();
        historyProduct.setDescription(description);
        historyProduct.setDatetime(new Date());
        historyProduct.setUsername(username);
        if (changeType.equals(ChangeType.NEW)) {
            historyProduct.setChangeType("New");
        }else if(changeType.equals(ChangeType.UPDATE)){
            historyProduct.setChangeType("Update");
        }else if(changeType.equals(ChangeType.REMOVE)){
            historyProduct.setChangeType("Delete");
        }else{
            historyProduct.setChangeType("Unknown");
        }
        return this.iHistoryProduct.save(historyProduct) != null;
    }
}
