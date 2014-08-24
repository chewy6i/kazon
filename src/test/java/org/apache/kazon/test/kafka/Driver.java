package org.apache.kazon.test.kafka;

import org.apache.kazon.kafka.BasicConsumer;
import org.apache.kazon.kafka.BasicProducer;
import org.apache.kazon.kafka.Util.KAFKA_ACKTYPE;

public class Driver {
	String host = "192.168.56.102";

	private class Producer1 implements Runnable {
		String brokers = host+":9091,"+host+":9092,"+host+":9093";
		String topic;

		public Producer1(String topic) {
			this.topic = topic;
		}

		@Override
		public void run() {
			System.out.println("Starting Producer");
			
			BasicProducer basicProducer = new BasicProducer();
			basicProducer.setTopic(topic);
			basicProducer.setSync(true);
			basicProducer.setTimeOut(10000);
			basicProducer.setAckType(KAFKA_ACKTYPE.ACK_ALL);
			basicProducer.setSerializer("kafka.serializer.StringEncoder");
			basicProducer.setPartitioner("org.apache.kazon.kafka.BasicPartitioner");
			
			basicProducer.setBrokers(brokers);
			
			basicProducer.setup();
			basicProducer.start();

			System.out.println("Done Producer");

		}

	}

	private class Consumer1 implements Runnable {
		String zknodes = host+":2181,"+host+":2182,"+host+":2183";
		String topic;

		public Consumer1(String topic) {
			this.topic = topic;
		}

		@Override
		public void run() {
			System.out.println("Starting Consumer");
			BasicConsumer basiConsumer = new BasicConsumer();
			basiConsumer.setTopic(topic);
			basiConsumer.setGroupId(1);
			basiConsumer.setZkSessionTimeOut(10000);
			basiConsumer.setZkSyncTimeOut(10000);
			basiConsumer.setCommitTimeOut(10000);
			
			basiConsumer.setZooKeepers(zknodes);
			
			basiConsumer.setup();
			basiConsumer.start();
			System.out.println("Done Consumer");

		}

	}

	public void testKafka(String topic) {
		System.out.println( "Kafka test" );
		Consumer1 consumer1 = new Consumer1(topic);
		new Thread(consumer1).start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Producer1 producer1 = new Producer1(topic);
		new Thread(producer1).start();		
	}

	public static void main(String[] args) {
		String topic = "tiger";

		Driver d = new Driver();
		d.testKafka(topic);


	}

}
