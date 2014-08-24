package org.apache.kazon.storm;

import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

public class BasicTopology {

	TopologyBuilder topologyBuilder = new TopologyBuilder();

	
	public void setSpout(SpoutDefinition spoutDef) {
		topologyBuilder.setSpout(spoutDef.getId(), spoutDef.getSpout());
	}
	
	public void setBolt(BoltDefinition boltDef) {
		topologyBuilder.setBolt(boltDef.getId(), boltDef.getBolt())
			.setNumTasks(boltDef.getNumTasks());
		//	.shuffleGrouping(arg0, arg1);
	}

	public StormTopology createTopology() {
		return topologyBuilder.createTopology();
	}
}
