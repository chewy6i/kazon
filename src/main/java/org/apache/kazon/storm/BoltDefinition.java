package org.apache.kazon.storm;

import java.util.List;

import backtype.storm.topology.IRichBolt;

public class BoltDefinition {
	private String id;
	private IRichBolt bolt;
	private int parallelismHint;
	private int numTasks;
	
	private List<BoltGrouping> boltGroupList;
	
	public BoltDefinition() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IRichBolt getBolt() {
		return bolt;
	}

	public void setBolt(IRichBolt bolt) {
		this.bolt = bolt;
	}

	public int getParallelismHint() {
		return parallelismHint;
	}

	public void setParallelismHint(int parallelismHint) {
		this.parallelismHint = parallelismHint;
	}

	public int getNumTasks() {
		return numTasks;
	}

	public void setNumTasks(int numTasks) {
		this.numTasks = numTasks;
	}

	public List<BoltGrouping> getBoltGroupList() {
		return boltGroupList;
	}

	public void setBoltGroupList(List<BoltGrouping> boltGroupList) {
		this.boltGroupList = boltGroupList;
	}
	
}
