package ru.yandex.practicum.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import serializer.AvroSerializer;

import java.time.Duration;
import java.util.Properties;

@Service
@ConfigurationProperties("aggregator.kafka")
public class KafkaProducerService implements AutoCloseable {

    private final KafkaProducer<String, SpecificRecordBase> producer;

    public KafkaProducerService(@Value("${kafka.bootstrap-servers}") String bootstrapServers) {
        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class.getName());

        this.producer = new KafkaProducer<>(config);
    }

    public void send(String topic, String key, SpecificRecordBase value) {
        producer.send(new ProducerRecord<>(topic, key, value));
        producer.flush();
    }

    public void flush() {
        producer.flush();
    }

    @Override
    public void close() {
        producer.flush();
        producer.close(Duration.ofSeconds(5));
    }
}