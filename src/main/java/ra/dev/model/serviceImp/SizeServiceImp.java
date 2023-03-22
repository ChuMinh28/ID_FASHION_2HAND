package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.dev.model.entity.Color;
import ra.dev.model.entity.Size;
import ra.dev.model.repository.SizeRepository;
import ra.dev.model.service.SizeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SizeServiceImp implements SizeService {
    @Autowired
    private SizeRepository sizeRepository;
    @Override
    public Size saveOrUpdate(Size size) {
        size.setSizesStatus(true);
        sizeRepository.save(size);
        return size;
    }

    @Override
    public List<Size> getAllSize() {
        return sizeRepository.findAll();
    }

    @Override
    public boolean changeSizeStatus(int sizeID, String action) {
        Size size = sizeRepository.findById(sizeID).get();
        if (action.equals("lock")) {
            size.setSizesStatus(false);
            sizeRepository.save(size);
            return false;
        } else {
            size.setSizesStatus(true);
            sizeRepository.save(size);
            return true;
        }
    }

    @Override
    public boolean updateSize(int sizeID, Size size) {
        Size sizeUpdate = sizeRepository.findById(sizeID).get();
        sizeUpdate.setSizeName(size.getSizeName());
        sizeRepository.save(sizeUpdate);
        return true;
    }

   public Map<String,Object> getPagination(Page<Size> sizePage){
       Map<String,Object> data=new HashMap<>();
       data.put("Size in page",sizePage.getContent());
       data.put("TotalElement",sizePage.getTotalElements());
       data.put("Size",sizePage.getSize());
       data.put("TotalPage",sizePage.getTotalPages());
       return data;
   }
    @Override
    public Map<String, Object> getPagging(String search, String sort, String pagination, String name, String direction, int page, int size) {
        if (search.equals("0")&&sort.equals("0")){
            Pageable pageable= PageRequest.of(page,size);
            Page<Size> sizePage=sizeRepository.findAll(pageable);
            return getPagination(sizePage);
        } else if (search.equals("0")) {
            Pageable pageable;
            if (direction.equalsIgnoreCase("asc")){
                pageable=PageRequest.of(page,size, Sort.by("sizesName").ascending());
            }else {
                pageable=PageRequest.of(page,size,Sort.by("sizesName").descending());
            }
            Page<Size> sizePage=sizeRepository.findAll(pageable);
            return getPagination(sizePage);
        } else if (sort.equals("0")) {
            Pageable pageable= PageRequest.of(page,size);
            Page<Size> sizePage=sizeRepository.findBySizeNameContaining(name,pageable);
            return getPagination(sizePage);
        }else {
            Pageable pageable;
            if (direction.equalsIgnoreCase("asc")){
                pageable=PageRequest.of(page,size, Sort.by("sizesName").ascending());
            }else {
                pageable=PageRequest.of(page,size,Sort.by("sizesName").descending());
            }
            Page<Size> sizePage=sizeRepository.findBySizeNameContaining(name,pageable);
            return getPagination(sizePage);
        }

    }
}
