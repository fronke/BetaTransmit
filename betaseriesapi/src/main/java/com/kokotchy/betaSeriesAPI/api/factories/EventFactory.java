package com.kokotchy.betaSeriesAPI.api.factories;

import org.json.JSONObject;

import com.kokotchy.betaSeriesAPI.UtilsJson;
import com.kokotchy.betaSeriesAPI.api.Constants;
import com.kokotchy.betaSeriesAPI.model.Event;

/**
 * Event factory
 * 
 * @author kokotchy
 */
public class EventFactory {
	/**
	 * Create an event from a json object
	 * 
	 * @param jsonObject
	 *            Json object
	 * @return Event
	 */
	public static Event createEvent(JSONObject jsonObject) {
		Event event = new Event();
		event.setType(UtilsJson.getStringValue(jsonObject, Constants.TYPE));
		event.setRef(UtilsJson.getStringValue(jsonObject, Constants.REF));
		event.setLogin(UtilsJson.getStringValue(jsonObject, Constants.LOGIN));
		event.setHtml(UtilsJson.getStringValue(jsonObject, Constants.HTML));
		event.setDate(UtilsJson.getIntValue(jsonObject, Constants.DATE));
		return event;
	}


}
