package udpm.fpt.servicce;

import udpm.fpt.model.Flavor;
import udpm.fpt.model.ProductInfo;

import java.util.ArrayList;
import java.util.List;

import udpm.fpt.repository.IFlavor;
import udpm.fpt.repository.IProductInfo;
import static udpm.fpt.Applocation.getBean;
import udpm.fpt.model.Milk;
import udpm.fpt.model.PackagingSpecification;
import udpm.fpt.model.Unit;
import udpm.fpt.repository.IMilk;
import udpm.fpt.repository.IPackagingSpecification;
import udpm.fpt.repository.IUnit;

/**
 *
 * @author NONG HOANG VU
 */
public class ProductService {

    private List<ProductInfo> list;
    private final IProductInfo r = getBean(IProductInfo.class);
    private final IMilk iMilk = getBean(IMilk.class);
    public ProductService() {
        this.list = list = new ArrayList<>();
    }

    public List<ProductInfo> getList() {
        this.list.clear();
        this.list = r.findAll();
        return list;
    }

    public Milk getMilkByID(Long id) {
        return iMilk.findAllById(id);
    }

    public List<Flavor> getFlavor() {
        IFlavor iFlavor = getBean(IFlavor.class);
        List<Flavor> listFlavor = iFlavor.findAll();
        return listFlavor;
    }

    public Boolean insertFlavor(Flavor flavor) {
        IFlavor iFlavor = getBean(IFlavor.class);
        return iFlavor.save(flavor) != null;
    }

    public List<Unit> getUnit() {
        IUnit iUnit = getBean(IUnit.class);
        List<Unit> listUnit = iUnit.findAll();
        return listUnit;
    }

    public Boolean insertUnit(Unit unit) {
        IUnit iUnit = getBean(IUnit.class);
        return iUnit.save(unit) != null;
    }

    public List<PackagingSpecification> getPackagingSpecification() {
        IPackagingSpecification iPackagingSpecification = getBean(IPackagingSpecification.class);
        List<PackagingSpecification> listPackagingSpecificationt = iPackagingSpecification.findAll();
        return listPackagingSpecificationt;
    }

    public Boolean insertPackagingSpecification(PackagingSpecification packagingSpecification) {
        IPackagingSpecification iPackagingSpecification = getBean(IPackagingSpecification.class);
        return iPackagingSpecification.save(packagingSpecification) != null;
    }

    public Boolean inserProduct(Milk m, ProductInfo pi) {
        if (iMilk.findAllById(m.getId()) == null) {
            return this.iMilk.save(m) != null && r.save(pi) != null;
        }
        return false;
    }

    public Boolean updateProduct(Milk m, ProductInfo pi) {
        return this.iMilk.save(m) != null && r.save(pi) != null;
    }

    public Boolean hideRestoreProduct(Milk m) {
        return this.iMilk.save(m) != null;
    }
}
