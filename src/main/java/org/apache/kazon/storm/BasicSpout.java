package org.apache.kazon.storm;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;

public class BasicSpout extends BaseRichSpout {

	private static final long serialVersionUID = 7635156906752519841L;
	
	private SpoutOutputCollector collector;
	
	@Override
	public void nextTuple() {
		// collector.emit("stream3", new Values(line));
		
	}

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// declarer.declareStream("stream3", new Fields("row"));
		
	}

}
