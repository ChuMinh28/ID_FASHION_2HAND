package ra.dev.model.service;

import ra.dev.model.entity.Size;

import java.util.List;
import java.util.Map;

public interface SizeService {
    Size saveOrUpdate(Size size);
    List<Size> getAllSize();
    boolean changeSizeStatus(int sizeID, String action);
    boolean updateSize(int sizeID, Size size);
    Map<String,Object> getPagging(String search, String sort,  String name, String direction, int page, int size);

}
