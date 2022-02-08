package com.anushan.nearestproperties;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Controller
public class NearestPropertiesController {

    private final RentalPropertyService rentalPropertyService;

    @Autowired
    public NearestPropertiesController(RentalPropertyService rentalPropertyService){
        this.rentalPropertyService = rentalPropertyService;
    }

    //show page to upload csv file
    @GetMapping()
    public String index(){
        return "index";
    }

    //show page after uploading csv file
    @PostMapping("/file-results")
    public String uploadCsvFile(@RequestParam("file")MultipartFile file, Model model){
        if(file.isEmpty()){
            model.addAttribute("message", "Please upload the csv file");
            model.addAttribute("status", false);
        }
        else{
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){

                //get all houses from uploaded csv file
                CsvToBean<House> csvToBean = new CsvToBeanBuilder<House>(reader).withType(House.class).withIgnoreLeadingWhiteSpace(true).build();
                List<House> houses = csvToBean.parse();
                model.addAttribute("houses", houses);
                model.addAttribute("status", true);

                //create results csv
                CsvWriter.writeCsv(houses, rentalPropertyService);
            }
            catch (Exception e){
                model.addAttribute("message", "Error processing file");
                model.addAttribute("status", false);
                System.out.println(e);
            }
        }

        return "file-results";
    }

    //allow users to download file
    @RequestMapping(value = "Dallas_houses_results", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public FileSystemResource getFile(){
        return new FileSystemResource("Dallas_houses_results.csv");
    }

}
