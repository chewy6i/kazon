package org.apache.kazon.kafka;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.kazon.kafka.Util.KAFKA_ACKTYPE;

public class BasicProducer {

	protected String topic;
	protected String serializer;
	protected String partitioner;
	protected boolean sync;
	protected KAFKA_ACKTYPE ackType;
	protected int timeOut;
	
	
	private class Broker {
		private String broker;
		private int port;

		public Broker(String broker, int port) {
			this.setBroker(broker);
			this.port = port;
		}
		
		public Broker(String broker) {
			String [] tokens = broker.split(":");
			this.broker = tokens[0];
			this.port = Integer.parseInt(tokens[1]);
		}


		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		
		public String getBroker() {
			return broker;
		}

		public void setBroker(String broker) {
			this.broker = broker;
		}
	}
	
	protected List<Broker> brokerList;
	protected Properties properties;
	protected ProducerConfig producerConfig;
	
	public BasicProducer() {
		brokerList = new ArrayList<Broker>();
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSerializer() {
		return serializer;
	}

	public void setSerializer(String serializer) {
		this.serializer = serializer;
	}

	public String getPartitioner() {
		return partitioner;
	}

	public void setPartitioner(String partitioner) {
		this.partitioner = partitioner;
	}

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

	public KAFKA_ACKTYPE getAckType() {
		return ackType;
	}

	public void setAckType(KAFKA_ACKTYPE ackType) {
		this.ackType = ackType;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public List<Broker> getBrokerList() {
		return brokerList;
	}
	
	public void addBroker(String broker, int port) {
		brokerList.add(new Broker(broker, port));
	}
	
	public void setBrokers(String brokers) {
		String [] tokens = brokers.split(",");
		for (String token :tokens) {
			brokerList.add(new Broker(token));
		}
	}
	
	public String getBrokersString() {
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(Broker b : brokerList) {
			sb.append(b.getBroker()).append(":").append(b.getPort());
			i++;
			if (i< brokerList.size()) {
				sb.append(",");
			}
		}
		
		return sb.toString();
	}

	public void clearBrokers() {
		brokerList.clear();
	}
	
	public void setup() {
		
		properties = new Properties();
		properties.put("metadata.broker.list", getBrokersString());
		properties.put("serializer.class", serializer);
		properties.put("partitioner.class", partitioner);
		properties.put("producer.type", sync?"sync":"async");
		properties.put("request.required.acks", ackType.type());
		properties.put("request.timeout.ms", Integer.toString(timeOut));
		

	}
	
	public void start() {
		producerConfig = new ProducerConfig(properties);	
		Producer<String, String> producer = new Producer<String, String>(producerConfig);
		int max = 10;
		for (long nEvents = 0; nEvents < max; nEvents++) { 
            long runtime = new Date().getTime();  
            String key = "key" + nEvents; 
            String msg = runtime + ",messagebuffer," + key; 
            System.out.println("Sending " + nEvents + " : " + key + " " + msg);
            KeyedMessage<String, String> data = new KeyedMessage<String, String>(topic, key, msg);
            producer.send(data);
            try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		producer.close();
	}
}
