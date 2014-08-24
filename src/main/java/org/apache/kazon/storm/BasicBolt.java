package org.apache.kazon.storm;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class BasicBolt extends BaseRichBolt {

	private static final long serialVersionUID = 6706052823103452361L;

	OutputCollector collector;
	
	@Override
	public void execute(Tuple tuple) {
		// 		collector.emit("stream1", new Values(words[2].trim(), clicks));

		
	}

	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}		

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// 		declarer.declareStream("stream1", new Fields("menu", "clicks"));
	}

}
