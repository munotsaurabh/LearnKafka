package kafka.demos;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemoWithShutdown {

    private static final Logger log = LoggerFactory.getLogger(ConsumerDemoWithShutdown.class.getSimpleName());

    public static void main(String[] args) {
        log.info("This is a Kafka consumer");

        String bootstrapServer = "127.0.0.1:9092";
        String groupId= "my-second-application";
        String topic= "demo_java";

        // Create a Consumer config
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        // Create a Consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // get a reference to the current thread
        final Thread mainThread = Thread.currentThread();

        //adding the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run(){
                log.info("Detected a shutdown..exit by calling consumer.wakeup()...");
                consumer.wakeup();

                // join the main thread to allow the execution of the code in the main thread
                try{
                    mainThread.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        // Subscribe consumer to a topic
//        consumer.subscribe(Collections.singletonList(topic));   // to subscribe to a single topic
        try{
            consumer.subscribe(Arrays.asList(topic)); // to subscribe to multiple topics

            // poll for new data
            while (true){
                log.info("Polling...");

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record: records){
                    log.info("Key: "+record.key() + ", Value: "+record.value());
                    log.info("Partition: "+record.partition()+ ", Offset: "+record.offset());
                }
            }
        }catch (WakeupException e) {
            log.info("Wake up exception"); //we ignore this as this is the expected exception when closing a consumer
        } catch (Exception e){
            log.error("Unexpected exception");
        } finally {
            consumer.close();
            log.info("The consumer is now gracefully closed...");
        }

    }
}
