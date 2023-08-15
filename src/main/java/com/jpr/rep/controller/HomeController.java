package com.jpr.rep.controller;

import com.jpr.rep.models.Users;
import com.jpr.rep.repo.UserRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;


@RestController
public class HomeController {

    @Autowired
    private UserRepository userRepository;


            @GetMapping("/jasper")
            private void homeController(HttpServletResponse response) throws IOException, JRException {
                List<Users> list =  this.userRepository.findAll();

               File file = ResourceUtils.getFile("classpath:employee.jrxml");

                JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

                HashMap<String,Object> hashMap  = new HashMap<>();
                hashMap.put("key" , "Employee Report");

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hashMap,dataSource);




                // To save DRIVE OR SYSTEM PATH
               // JasperExportManager.exportReportToPdfFile(jasperPrint,"E:\\employee.pdf");

                // VIEW AND AFTER DOWNLOAD SHOW OPTION
                JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());


                //Direct Download
//                response.setContentType("application/pdf");
//                response.addHeader("Content-disposition", "attachment; filename=StatisticsrReport1.pdf");
//                OutputStream out = response.getOutputStream();
//                JasperExportManager.exportReportToPdfStream(jasperPrint,out);


              //  return ResponseEntity.ok(list);


            }





}
