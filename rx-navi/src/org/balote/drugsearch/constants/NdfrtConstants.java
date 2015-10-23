package org.balote.drugsearch.constants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

public final class NdfrtConstants {

	private static final String TAG = "NdfrtConstants";

	public static final String ROOT_URL = "http://rxnav.nlm.nih.gov/REST/Ndfrt/";

	public static final String GAT_ALL_INFO_URL = ROOT_URL + "allInfo/";

	public static final String GET_ALL_CONCEPTS_OF_DRUG_KIND_URL = ROOT_URL
			+ "allconcepts?kind=DRUG_KIND";

	public static final String GET_VERSION_URL = ROOT_URL + "version";

	private NdfrtConstants() {

	}

	public static String composeGetAllInfoUrl(String nuiParam)
			throws UnsupportedEncodingException {

		StringBuffer sb = new StringBuffer();

		sb.append(GAT_ALL_INFO_URL);
		sb.append(URLEncoder.encode(nuiParam, "UTF-8"));

		Log.i(TAG, "composeGetAllInfoUrl:" + sb.toString());

		return sb.toString();
	}

}
