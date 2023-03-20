package ra.dev.model.service;

import ra.dev.model.entity.Color;

import java.util.List;

public interface ColorService {
    Color saveOrUpdate(Color color);
    List<Color> getAllColor();
    boolean changeColorStatus(int colorID, String action);
    boolean updateColor(int colorID, Color color);
}
