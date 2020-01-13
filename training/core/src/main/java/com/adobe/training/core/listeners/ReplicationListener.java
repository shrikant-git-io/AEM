package com.adobe.training.core.listeners;

import java.util.HashMap;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;

import org.apache.sling.event.jobs.JobManager;

@Component(immediate = true)
@Service(value = EventHandler.class)
//event on which handler needs to be invoked
@Property(name = "event.topics", value = ReplicationAction.EVENT_TOPIC)

public class ReplicationListener implements EventHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	//dictates to the JobManager which JobConsumer will be instantiated in line 42
	private static final String TOPIC = "com/adobe/training/core/replicationjob";

	@Reference
	private JobManager jobManager;

	@Override
	public void handleEvent(final Event event) {
		ReplicationAction action = ReplicationAction.fromEvent(event);

		if (action.getType().equals(ReplicationActionType.ACTIVATE)) {
			if (action.getPath() != null)
			{
				try {
					// Create a properties map that contains things we want to pass through the job
					HashMap<String, Object> jobprops = new HashMap<String, Object>();
					jobprops.put("PAGE_PATH", action.getPath());
					// Add the job
					jobManager.addJob(TOPIC, jobprops);
					logger.info("=============Topic: '"+TOPIC+"' with payload: '"+action.getPath()+"' was added to the Job Manager");

				} catch (Exception e) {
					logger.error("============= ERROR CREATING JOB : NO PAYLOAD WAS DEFINED");
					e.printStackTrace();
				}
			}
		}
	}
}




