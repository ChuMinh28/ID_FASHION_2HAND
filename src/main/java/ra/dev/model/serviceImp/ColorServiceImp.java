package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.dev.model.entity.Color;
import ra.dev.model.repository.ColorRepository;
import ra.dev.model.service.ColorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String,Object> getPagination(Page<Color> colorPage){
        Map<String,Object> data=new HashMap<>();
        data.put("Color in page",colorPage.getContent());
        data.put("TotalElement",colorPage.getTotalElements());
        data.put("Size",colorPage.getSize());
        data.put("TotalPage",colorPage.getTotalPages());
        return data;
    }

    @Override
    public Map<String, Object> getPagging(String search, String sort, String pagination, String name, String direction, int page, int size) {
       if (search.equals("0")&&sort.equals("0")){
           Pageable pageable= PageRequest.of(page,size);
           Page<Color> colorPage=colorRepository.findAll(pageable);
           return getPagination(colorPage);
       } else if (search.equals("0")) {
           Pageable pageable;
           if (direction.equalsIgnoreCase("asc")){
               pageable=PageRequest.of(page,size, Sort.by("ColorName").ascending());
           }else {
               pageable=PageRequest.of(page,size,Sort.by("ColorName").descending());
           }
           Page<Color> colorPage=colorRepository.findAll(pageable);
           return getPagination(colorPage);
       } else if (sort.equals("0")) {
           Pageable pageable= PageRequest.of(page,size);
           Page<Color> colorPage=colorRepository.findByColorNameContaining(name,pageable);
           return getPagination(colorPage);
       }else {
           Pageable pageable;
           if (direction.equalsIgnoreCase("asc")){
               pageable=PageRequest.of(page,size, Sort.by("ColorName").ascending());
           }else {
               pageable=PageRequest.of(page,size,Sort.by("ColorName").descending());
           }
           Page<Color> colorPage=colorRepository.findByColorNameContaining(name,pageable);
           return getPagination(colorPage);
       }

    }
}
