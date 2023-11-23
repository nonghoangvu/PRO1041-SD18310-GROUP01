package udpm.fpt.servicce;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import static udpm.fpt.Applocation.getBean;
import udpm.fpt.model.HistoryProduct;
import udpm.fpt.repository.IHistoryProduct;

/**
 *
 * @author NONG HOANG VU
 */
public class HistoryProductService {
    private final IHistoryProduct iProductInfo = getBean(IHistoryProduct.class);

    public HistoryProductService() {
    }
    public CompletableFuture<List<HistoryProduct>> loadHistory() {
        return CompletableFuture.supplyAsync(() -> {
            return iProductInfo.findAll();
        }, Executors.newCachedThreadPool());
    }
}
