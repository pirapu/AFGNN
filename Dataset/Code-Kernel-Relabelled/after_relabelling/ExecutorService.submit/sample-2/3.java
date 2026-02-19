public class func{
public void createKafkaMessageConsumer(ConsumerConnector consumerConn,String topic,String topicInHeader,CountDownLatch messagesLatch,Map<String,Integer> topicCountMap){
        for (final KafkaStream<byte[], byte[]> stream : consumerMap.get(topic)) {
            executor.submit(new KakfaTopicConsumer(stream, messagesLatch));
        }
        for (final KafkaStream<byte[], byte[]> stream : consumerMap.get(topicInHeader)) {
            executor.submit(new KakfaTopicConsumer(stream, messagesLatch));
        }
}
}
