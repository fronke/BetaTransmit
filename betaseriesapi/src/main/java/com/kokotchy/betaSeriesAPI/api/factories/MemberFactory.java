package com.kokotchy.betaSeriesAPI.api.factories;

import java.util.List;

import org.json.JSONObject;

import com.kokotchy.betaSeriesAPI.UtilsJson;
import com.kokotchy.betaSeriesAPI.api.Constants;
import com.kokotchy.betaSeriesAPI.model.Member;

/**
 * Member factory
 * 
 * @author kokotchy
 */
public class MemberFactory {
	/**
	 * Create member object from json object
	 * 
	 * @param jsonObject
	 *            JSON object
	 * @return Member
	 */
	public static Member createMember(JSONObject jsonObject) {
		Member member = new Member();
		member.setLogin(UtilsJson.getStringValue(jsonObject,
				Constants.LOGIN));
		member.setAvatar(UtilsJson.getStringValue(jsonObject,
				Constants.AVATAR));
		member.setStats(StatsFactory.createStats(UtilsJson.getJSONObject(
				jsonObject, Constants.STATS)));
		JSONObject shows = UtilsJson.getJSONObject(jsonObject,
				Constants.SHOWS);
		JSONObject[] array = UtilsJson.getArray(shows);
		if (array.length > 0) {
			for (JSONObject show : array) {
				member.addShow(ShowFactory.createShow(show));
			}
		}
		return member;
	}


}
