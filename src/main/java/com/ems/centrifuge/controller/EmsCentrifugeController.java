package com.ems.centrifuge.controller;

import com.ems.centrifuge.model.BusinessUnitResponse;
import com.ems.centrifuge.service.BusinessUnitService;
import com.ems.centrifuge.service.NumberPlateService;
import com.ems.centrifuge.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EmsCentrifugeController {

    @Autowired
    private StorageService storageService;
    @Autowired
    private NumberPlateService numberPlateService;
    @Autowired
    private BusinessUnitService businessUnitService;
    
    @GetMapping("/")
    @ResponseBody
    public String Index(@RequestParam(required=false, defaultValue="0") String roomCode, Model model){
        model.addAttribute("roomCode", roomCode);
        return "index";
    }

    @PostMapping("/upload")
    public String Upload(@RequestParam("vehicleImage") MultipartFile file) throws IOException {
        return String.format("forward:/process/%s", storageService.saveFile(file));
    }

    @PostMapping("/process/{uploadedFile}")
    @ResponseBody
    public Map<String, Boolean> ProcessUpload(@PathVariable("uploadedFile") String fileName) throws IOException {
        BusinessUnitResponse businessUnitResponse = businessUnitService.send(
                numberPlateService.send(storageService.getFile(fileName))
        );

        // Add some logic here to delete the uploaded file in non-blocking way
        Files.delete(
                Paths.get(
                        storageService.getFile(fileName).getAbsolutePath()
                )
        );
        Map<String, Boolean> response = new HashMap<>();
        response.put("authorised", businessUnitResponse.isAuthorized());
        return response;
    }
}
