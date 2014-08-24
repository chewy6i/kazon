package org.apache.kazon.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class BasicConsumer {
	protected String topic;
	protected int groupId;
	protected int zkSessionTimeOut;
	protected int zkSyncTimeOut;
	protected int commitTimeOut;
	
	private class ZooKeeperNode {
		private String zkHost;
		private int port;

		public ZooKeeperNode(String zkhost, int port) {
			this.setZKHost(zkhost);
			this.port = port;
		}

		public ZooKeeperNode(String zkhost) {
			String [] tokens = zkhost.split(":");
			this.zkHost = tokens[0];
			this.port = Integer.parseInt(tokens[1]);
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		
		public String getZKHost() {
			return zkHost;
		}

		public void setZKHost(String zkHost) {
			this.zkHost = zkHost;
		}
	}
	
	protected List<ZooKeeperNode> zkNodeList;
	protected Properties properties;
	protected ConsumerConfig consumerConfig;
	
	public BasicConsumer() {
		zkNodeList = new ArrayList<ZooKeeperNode>();
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getZkSessionTimeOut() {
		return zkSessionTimeOut;
	}

	public void setZkSessionTimeOut(int zkSessiontimeOut) {
		this.zkSessionTimeOut = zkSessiontimeOut;
	}

	public int getZkSyncTimeOut() {
		return zkSyncTimeOut;
	}

	public void setZkSyncTimeOut(int zkSynctimeOut) {
		this.zkSyncTimeOut = zkSynctimeOut;
	}

	public int getCommitTimeOut() {
		return commitTimeOut;
	}

	public void setCommitTimeOut(int committimeOut) {
		this.commitTimeOut = committimeOut;
	}

	public List<ZooKeeperNode> getZkNodeList() {
		return zkNodeList;
	}

	public void addZooKeeper(String zk, int port) {
		zkNodeList.add(new ZooKeeperNode(zk, port));
	}
	
	public void setZooKeepers(String brokers) {
		String [] tokens = brokers.split(",");
		for (String token :tokens) {
			zkNodeList.add(new ZooKeeperNode(token));
		}
	}

	public String getZKString() {
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(ZooKeeperNode b : zkNodeList) {
			sb.append(b.getZKHost()).append(":").append(b.getPort());
			i++;
			if (i< zkNodeList.size()) {
				sb.append(",");
			}
		}
		
		return sb.toString();
	}

	public void clearBrokers() {
		zkNodeList.clear();
	}	
	
	public void setup() {
		
		properties = new Properties();
		
		properties.put("zookeeper.connect", getZKString());
		properties.put("group.id", Integer.toString(groupId));
		properties.put("zookeeper.session.timeout.ms", Integer.toString(zkSessionTimeOut));
		properties.put("zookeeper.sync.time.ms", Integer.toString(zkSyncTimeOut));
		properties.put("auto.commit.interval.ms", Integer.toString(commitTimeOut));
		
	}
	
	public void start() {
		consumerConfig = new ConsumerConfig(properties);
		ConsumerConnector consumer = Consumer.createJavaConsumerConnector(consumerConfig);
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream =  consumerMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		
		while(it.hasNext()) {
			System.out.println("Recv: " + new String(it.next().message()));
		}
		
	}
}
