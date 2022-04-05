package kafka.demos;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());

    public static void main(String[] args) {
        log.info("This is a Kafka producer");


        //create Producer properties
        Properties properties  = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //create the Producer object
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //create a Producer record
        ProducerRecord<String, String> producerRecord;
        producerRecord = new ProducerRecord<>("demo_java", "Hello World!");

        //send data - asynchronous operation
        producer.send(producerRecord);

        //flush and close the Producer
        producer.flush();       //asynchronous operation
        producer.close();

    }
}
