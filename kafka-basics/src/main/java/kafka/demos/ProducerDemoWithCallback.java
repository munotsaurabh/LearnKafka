package kafka.demos;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {

    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithCallback.class.getSimpleName());

    public static void main(String[] args) {
        log.info("This is a Kafka producer");

        //create Producer properties
        Properties properties  = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //create the Producer object
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //send data - asynchronous operation
        //loop through so that the messages will be sent to different partitions
        //Callback is used to retrieve information such as Topic name to which the data is sent, Partition number, Offset etc.

        for (int i=0; i<10; i++){
            //create a Producer record
            ProducerRecord<String, String> producerRecord;
            producerRecord = new ProducerRecord<>("demo_java", "Hello World! " +i);

            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    //executes every time a record is successfully sent or an exception is thrown
                    if(e==null){
                        log.info("Received new metadata/ \n" +
                                "Topic: " +metadata.topic() + "\n" +
                                "Partition: " +metadata.partition() + "\n" +
                                "Offset: " +metadata.offset() + "\n" +
                                "Timestamp: " +metadata.timestamp());
                    } else{
                        log.error("Error in producing...", e);
                    }
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }


        //flush and close the Producer
        producer.flush();       //asynchronous operation
        producer.close();

    }
}
