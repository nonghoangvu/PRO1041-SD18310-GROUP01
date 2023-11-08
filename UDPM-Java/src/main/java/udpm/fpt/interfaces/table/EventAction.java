package udpm.fpt.interfaces.table;

import com.vunh.model.ModelStudent;

public interface EventAction {

    public void delete(ModelStudent student);

    public void update(ModelStudent student);
}
