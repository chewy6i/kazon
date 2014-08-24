package org.apache.kazon.kafka;

public class Util {
	public static enum KAFKA_ACKTYPE {
		ACK_NONE(0), 
		ACK_LEADER(1), 
		ACK_ALL(-1);

		int type;

		KAFKA_ACKTYPE(int t) {
			type = t;
		}

		public String type() {
			return Integer.toString(type);
		}
	}
	
	
	
}
