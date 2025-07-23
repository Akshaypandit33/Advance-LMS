package com.lms.tenantmanagementservice.Kafka;

import com.LMS.Kafka.KafkaTopics;
import com.LMS.Kafka.TenantAddedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TenantEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(TenantEventProducer.class);

    private final KafkaTemplate<String, TenantAddedEvent> kafkaTemplate;

    public void tenantAddedEvent(TenantAddedEvent tenantAddedEvent) {
        kafkaTemplate.send(KafkaTopics.ADD_TENANT_TOPIC, tenantAddedEvent);
        logger.info("tenantAddedEvent sent to kafka topic");
    }
}
