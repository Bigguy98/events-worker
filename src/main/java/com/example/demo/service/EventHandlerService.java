package com.example.demo.service;

import com.example.demo.repository.MedicalRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.MedicalRecord;
import com.example.demo.repository.entity.User;
import com.example.demo.util.HttpUtil;
import com.example.demo.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.example.demo.constant.System.APPROVED;

@Service
public class EventHandlerService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final UserRepository userRepository;
    private final MailService emailService;
    Logger logger = LoggerFactory.getLogger(EventHandlerService.class);

    public EventHandlerService(MedicalRecordRepository medicalRecordRepository, UserRepository userRepository, MailService emailService) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }


    public void handleApprovedEvent(String medicalRecordId) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(Long.valueOf(medicalRecordId)).orElse(null);
        if(medicalRecord != null) {
            medicalRecord.setStatus(APPROVED);
            medicalRecordRepository.save(medicalRecord);
        }
    }

    public void handleBoughtEvent(String categoryId, String walletId) {
        User user = userRepository.findByWalletId(walletId);
        if (user == null) {
            return;
        }
        String customerEmail = user.getEmail();
        if (customerEmail == null) {
            return;
        }
        List<MedicalRecord> medicalRecordList = medicalRecordRepository.findApprovedByCategoryId(Long.valueOf(categoryId), APPROVED);
        List<String> urls = new ArrayList<>();
        medicalRecordList.stream().forEach(md -> {
            urls.addAll(Arrays.asList(md.getImageUrls().split(";")));
        });
        String attachmentFile = compressData(urls);
        emailService.sendMailWithAttachment(customerEmail, "PURCHASED MEDICAL DATASET", "Here's your medical dataset: \n", attachmentFile);
//    TODO: remove newly created .zip file
    }

    public String compressData(List<String> urls) {
        String compressFile = StringUtil.randomString(6) + ".zip";
        try {
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(compressFile));
           for(String url : urls) {
               // only get last 10 characters of image name
               ZipEntry zipEntry = new ZipEntry(url.substring(url.length() - 10));
               zipOut.putNextEntry(zipEntry);

               // url input stream
               InputStream is = HttpUtil.getInputStreamFromUrl(new URL(url));
               byte[] bytes = new byte[1024];
               int length;
               while((length = is.read(bytes)) >= 0) {
                   zipOut.write(bytes, 0, length);
               }
               is.close();
               zipOut.closeEntry();
           }

           zipOut.close();
        } catch (IOException e) {
            logger.error("Fail to compress file!");
            return null;
        }
        return compressFile;
    }

}
