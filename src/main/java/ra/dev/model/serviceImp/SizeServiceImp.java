package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dev.model.entity.Size;
import ra.dev.model.repository.SizeRepository;
import ra.dev.model.service.SizeService;

import java.util.List;

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
}
