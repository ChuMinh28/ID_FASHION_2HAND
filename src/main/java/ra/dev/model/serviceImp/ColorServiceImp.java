package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dev.model.entity.Color;
import ra.dev.model.repository.ColorRepository;
import ra.dev.model.service.ColorService;

import java.util.List;

@Service
public class ColorServiceImp implements ColorService {
    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Color saveOrUpdate(Color color) {
        color.setColorStatus(true);
        colorRepository.save(color);
        return color;
    }

    @Override
    public List<Color> getAllColor() {
        return colorRepository.findAll();
    }

    @Override
    public boolean changeColorStatus(int colorID, String action) {
        Color color = colorRepository.findById(colorID).get();
        if (action.equals("lock")) {
            color.setColorStatus(false);
            colorRepository.save(color);
            return false;
        } else {
            color.setColorStatus(true);
            colorRepository.save(color);
            return true;
        }
    }

    @Override
    public boolean updateColor(int colorID, Color color) {
        Color colorUpdate = colorRepository.findById(colorID).get();
        colorUpdate.setColorName(color.getColorName());
        colorRepository.save(colorUpdate);
        return true;
    }
}
