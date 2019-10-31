package com.kokotchy.betaSeriesAPI.api.factories;

import org.json.JSONException;
import org.json.JSONObject;

import com.kokotchy.betaSeriesAPI.api.Constants;
import com.kokotchy.betaSeriesAPI.model.Stats;

/**
 * Stats factory
 * 
 * @author kokotchy
 */
public class StatsFactory {
	/**
	 * Create stats from json Object
	 * 
	 * @param jsonObject
	 *            JSON Object
	 * @return Stats
	 */
	public static Stats createStats(JSONObject jsonObject) {
		Stats stats = new Stats();
		try {
			stats.setShows(jsonObject.getInt(Constants.SHOWS));
			stats.setSeasons(jsonObject.getInt(Constants.SEASONS));
			stats.setEpisodes(jsonObject.getInt(Constants.EPISODES));
			stats.setProgress(jsonObject.getString(Constants.PROGRESS));
			stats.setEpisodesToWatch(jsonObject
					.getInt(Constants.EPISODES_TO_WATCH));
			stats.setTimeOnTv(jsonObject.getInt(Constants.TIME_ON_TV));
			stats.setTimeToSpend(jsonObject.getInt(Constants.TIME_TO_SPEND));
			stats.setNbFriends(jsonObject.getInt(Constants.NB_FRIENDS));
			stats.setNbBadges(jsonObject.getInt(Constants.NB_BADGES));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return stats;
	}


}
