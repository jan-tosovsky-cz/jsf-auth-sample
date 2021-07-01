package jsf.auth.sample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class BaseBean implements Serializable {

    private final List<String> itemList = new ArrayList<>();
    
    public void updateItemList() {
        itemList.clear();
        for (int i = 0; i < 5; i++) {
            itemList.add(String.valueOf(Math.random()));
        }
    }

    public List<String> getItemList() {
        return itemList;
    }

}
