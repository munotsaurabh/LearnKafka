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



### Some key concepts
`1. Producer with Callback:` Callback is used to retrieve information such as Topic name to which the data is sent, Partition number, Offset etc. (Implemented in ProducerDemoWithCallback.java)

`2. Producer with keys:` The data with the same key will always go to the same partition. (Implemented in ProducerDemoKeys.java)



