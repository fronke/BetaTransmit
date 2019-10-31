package com.kokotchy.betaSeriesAPI.api.factories;

import org.json.JSONObject;

import com.kokotchy.betaSeriesAPI.UtilsJson;
import com.kokotchy.betaSeriesAPI.api.Constants;
import com.kokotchy.betaSeriesAPI.model.VersionFile;

/**
 * VersionFile factory
 * 
 * @author kokotchy
 */
public class VersionFileFactory {
	/**
	 * Create a new VersionFile from the json object
	 * 
	 * @param fileObject
	 *            json object
	 * @return VersionFile
	 */
	public static VersionFile createVersionFile(JSONObject fileObject) {
		VersionFile versionFile = new VersionFile();
		versionFile.setLastChange(UtilsJson.getIntValue(fileObject,
				Constants.LAST_CHANGE));
		versionFile.setName(UtilsJson
				.getStringValue(fileObject, Constants.NAME));
		return versionFile;
	}


}
