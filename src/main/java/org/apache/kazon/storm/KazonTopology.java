package org.apache.kazon.storm;

import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

public class KazonTopology {

	public StormTopology createTopology() {
		TopologyBuilder topologyBuilder = new TopologyBuilder();
		
		return topologyBuilder.createTopology();
	}
}
