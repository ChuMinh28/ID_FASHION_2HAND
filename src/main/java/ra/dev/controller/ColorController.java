package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dev.model.entity.Color;
import ra.dev.model.service.ColorService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @PostMapping("createColor")
    public ResponseEntity<?> createColor(@RequestBody Color color) {
        try {
            return ResponseEntity.ok(colorService.saveOrUpdate(color));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Create failed!");
        }
    }

    @GetMapping("getAllColor")
    public ResponseEntity<?> getALlColor() {
        try {
            return ResponseEntity.ok(colorService.getAllColor());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!!!");
        }
    }

    @PatchMapping("updateColor/{colorID}")
    public ResponseEntity<?> updateColor(@PathVariable("colorID") int colorID, @RequestBody Color color) {
        try {
            boolean check = colorService.updateColor(colorID, color);
            if (check) {
                return ResponseEntity.ok("Update color successfully!");
            } else {
                return ResponseEntity.badRequest().body("Error!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!!!");
        }
    }

    @PatchMapping("changeColorStatus/{colorID}")
    public ResponseEntity<?> changeColorStatus(@PathVariable("colorID") int colorID, @RequestParam("action") String action) {
        try {
            boolean check = colorService.changeColorStatus(colorID, action);
            if (check) {
                return ResponseEntity.ok("Unlock color successfully!");
            } else {
                return ResponseEntity.ok("Lock color successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!!!");
        }
    }
    @GetMapping("/action")
    public Map<String, Object> paginationColor(@RequestParam(defaultValue = "0") String search,
                                                 @RequestParam(defaultValue = "0") String sort,
                                                 @RequestParam(defaultValue = "0") String pagination,
                                                 @RequestParam(defaultValue = "c") String name,
                                                 @RequestParam(defaultValue = "desc") String direction,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "3") int size
    ) {
        return colorService.getPagging(search,sort,pagination,name,direction,page, size);
    }
}
