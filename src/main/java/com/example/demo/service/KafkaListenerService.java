package com.example.demo.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.example.demo.constant.System.SOLANA_TOPIC;

@Service
public class KafkaListenerService {

    Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);
    private final  EventHandlerService eventHandlerService;

    private static final String APPROVAL_EVENT = "Approval_event";
    private static final String BOUGHT_EVENT = "Bought_event";

    public KafkaListenerService(EventHandlerService eventHandlerService) {
        this.eventHandlerService = eventHandlerService;
    }


    @KafkaListener(topics = SOLANA_TOPIC)
    public void listen(String message) {
        System.out.println("Received Message in group " + message);
        JSONObject jsonObject = new JSONObject(message);
        String type = jsonObject.getString("type");
        if (type == null) {
            logger.warn("Message type not found!");
        } else if (type.equals(APPROVAL_EVENT)) {
            eventHandlerService.handleApprovedEvent(jsonObject.getString("medicalRecordId"));
        } else if (type.equals(BOUGHT_EVENT)) {
            eventHandlerService.handleBoughtEvent(jsonObject.getString("categoryId"), jsonObject.getString("walletId"));
        } else {
            logger.warn("Message type cannot be processed!");
        }
    }




}
