package org.apache.kazon.storm;

import backtype.storm.topology.IRichSpout;

public class SpoutDefinition {
	private String id;
	private IRichSpout spout;
	
	public SpoutDefinition() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public IRichSpout getSpout() {
		return spout;
	}
	public void setSpout(IRichSpout spout) {
		this.spout = spout;
	}
}
