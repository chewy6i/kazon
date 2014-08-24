package org.apache.kazon.kafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class BasicPartitioner implements Partitioner{
	
	public BasicPartitioner(VerifiableProperties props) {

	}
	
	
	@Override
	public int partition(Object keyObj, int nBrokers) {
		// keyObj is the key given while producing, nBrokers is the number of
		// partition the broker has
		
		int partition = 0;
		String key = (String)keyObj;
		partition = key.hashCode() % nBrokers;
		if (partition <0) {
			partition = -partition;
		}
		System.out.println("Partition for " + key + " : " + partition);
		return partition;
	}
}
