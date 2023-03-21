package ra.dev.model.service;

import ra.dev.model.entity.Color;

import java.util.List;
import java.util.Map;

public interface ColorService {
    Color saveOrUpdate(Color color);
    List<Color> getAllColor();
    boolean changeColorStatus(int colorID, String action);
    boolean updateColor(int colorID, Color color);

    Map<String,Object> getPagging(String search,String sort,String pagination,String name,String direction,int page, int size);
}
