# Kafka Multi-Broker Setup with KRaft (No Zookeeper)

## 1. Download Kafka  
[https://kafka.apache.org/downloads](https://kafka.apache.org/downloads)

---

## 2. Extract Kafka
Extract the downloaded Kafka archive and move it to a directory of your choice.

---

## 3. Duplicate the `server.properties` File
Navigate to the `config` directory and copy the `server.properties` file to create separate configuration files for each broker.

**Example:**
```
kafka_2.13-3.9.0/config/server.properties
```

Create and rename:
- `broker1.properties`
- `broker2.properties`

---

## 4. Update Broker Configuration Files

### `broker1.properties`  
```properties
############################# Server Basics #############################
process.roles=broker,controller
node.id=1
controller.quorum.voters=1@localhost:9093,2@localhost:9094

############################# Socket Server Settings #############################
listeners=PLAINTEXT://localhost:9092,CONTROLLER://localhost:9093
inter.broker.listener.name=PLAINTEXT
advertised.listeners=PLAINTEXT://localhost:9092,CONTROLLER://localhost:9093
controller.listener.names=CONTROLLER
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT

num.network.threads=3
num.io.threads=8
socket.send.buffer.bytes=102400
socket.receive.buffer.bytes=102400
socket.request.max.bytes=104857600

############################# Log Basics #############################
log.dirs=/tmp/kraft-combined-logs-1
num.partitions=1
num.recovery.threads.per.data.dir=1

############################# Internal Topic Settings #############################
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1

############################# Log Retention Policy #############################
log.retention.hours=168
log.segment.bytes=1073741824
log.retention.check.interval.ms=300000

############################# Group Coordinator Settings #############################
group.initial.rebalance.delay.ms=0
```

### `broker2.properties`  
```properties
############################# Server Basics #############################
process.roles=broker,controller
node.id=2
controller.quorum.voters=1@localhost:9093,2@localhost:9094

############################# Socket Server Settings #############################
listeners=PLAINTEXT://localhost:9096,CONTROLLER://localhost:9094
inter.broker.listener.name=PLAINTEXT
advertised.listeners=PLAINTEXT://localhost:9096,CONTROLLER://localhost:9094
controller.listener.names=CONTROLLER
listener.security.protocol.map=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT

num.network.threads=3
num.io.threads=8
socket.send.buffer.bytes=102400
socket.receive.buffer.bytes=102400
socket.request.max.bytes=104857600

############################# Log Basics #############################
log.dirs=/tmp/kraft-combined-logs-2
num.partitions=1
num.recovery.threads.per.data.dir=1

############################# Internal Topic Settings #############################
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1

############################# Log Retention Policy #############################
log.retention.hours=168
log.segment.bytes=1073741824
log.retention.check.interval.ms=300000

############################# Group Coordinator Settings #############################
group.initial.rebalance.delay.ms=0
```

---

## 5. Generate a Cluster ID
```bash
export KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
echo $KAFKA_CLUSTER_ID  # Save and reuse this for both brokers
```

---

## 6. Format Storage for Each Broker
```bash
bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/broker1.properties
bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/broker2.properties
```

---

## 7. Start the Brokers
```bash
bin/kafka-server-start.sh config/broker1.properties
bin/kafka-server-start.sh config/broker2.properties
```

---

## 8. Verify Cluster is Running
```bash
bin/kafka-broker-api-versions.sh --bootstrap-server localhost:9092
```

---

## 9. Check Cluster Info
```bash
bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe
```

---

## 10. Create Topics

### `stock-orders` Topic
```bash
bin/kafka-topics.sh   --create   --topic stock-orders   --partitions 2   --replication-factor 2   --bootstrap-server localhost:9092
```

### `stock-prices` Topic
```bash
bin/kafka-topics.sh   --create   --topic stock-prices   --partitions 2   --replication-factor 2   --bootstrap-server localhost:9092
```

---

## 11. Publish a Message
```bash
bin/kafka-console-producer.sh   --topic stock-orders   --bootstrap-server localhost:9092
```

---

## 12. Consume Messages
```bash
bin/kafka-console-consumer.sh   --topic stock-orders   --from-beginning   --bootstrap-server localhost:9092
```