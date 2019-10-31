package com.kokotchy.betaSeriesAPI.api.factories;

import org.json.JSONObject;

import com.kokotchy.betaSeriesAPI.UtilsJson;
import com.kokotchy.betaSeriesAPI.api.Constants;
import com.kokotchy.betaSeriesAPI.model.Comment;

/**
 * Comments factory
 * 
 * @author kokotchy
 */
public class CommentFactory {

	/**
	 * Create a comment from the json object
	 * 
	 * @param jsonObject
	 *            Json object
	 * @return Comment
	 */
	public static Comment createComment(JSONObject jsonObject) {
		Comment comment = new Comment();
		comment
				.setContent(UtilsJson
						.getStringValue(jsonObject, Constants.TEXT));
		comment.setDate(UtilsJson.getIntValue(jsonObject, Constants.DATE));
		comment.setInnerId(UtilsJson
				.getIntValue(jsonObject, Constants.INNER_ID));
		comment.setLogin(UtilsJson.getStringValue(jsonObject, Constants.LOGIN));
		comment.setReplyToId(UtilsJson.getIntValue(jsonObject,
				Constants.IN_REPLY_TO));
		return comment;
	}


}
