package com.kokotchy.betaSeriesAPI.api.factories;

import org.json.JSONException;
import org.json.JSONObject;

import com.kokotchy.betaSeriesAPI.UtilsJson;
import com.kokotchy.betaSeriesAPI.api.Constants;
import com.kokotchy.betaSeriesAPI.model.Notification;

/**
 * Notification factory
 * 
 * @author kokotchy
 */
public class NotificationFactory {
	/**
	 * Create a notification from json object
	 * 
	 * @param jsonObject
	 *            JSON object
	 * @return Notification
	 */
	public static Notification createNotification(JSONObject jsonObject) {
		Notification notification = new Notification();
		try {
			notification.setId(jsonObject.getInt(Constants.ID));
			notification.setHtml(jsonObject.getString(Constants.HTML));
			notification.setDate(jsonObject.getInt(Constants.DATE));
			notification.setSeen(UtilsJson.getJSONBooleanFromPath(jsonObject,
					Constants.SEEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return notification;
	}


}
