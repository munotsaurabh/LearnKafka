# Getting started

### Prerequisites
##### Add the below dependencies in kafka-basics -> build.gradle file

    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
    implementation 'org.apache.kafka:kafka-clients:3.1.0'
    
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation 'org.slf4j:slf4j-api:1.7.36'

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation 'org.slf4j:slf4j-simple:1.7.36'
    
### Implementation
##### Creating a producer - kafka-basics/src/main/java/kafka/demos/ProducerDemo.java
1. Create properties object of class `Properties` as it is required to create Kafka properties
2. Create `KafkaProducer` object to produce to Kafka
3. Create `ProducerRecord` object which has info like topic name and value i.e. message
4. Send data through KafkaProducer object with ProducerRecord object as an argument
5. Flush and close the producer


