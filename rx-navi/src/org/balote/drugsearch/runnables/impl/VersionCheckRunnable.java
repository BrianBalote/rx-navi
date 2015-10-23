package org.balote.drugsearch.runnables.impl;

import java.io.StringReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.balote.drugsearch.constants.NdfrtConstants;
import org.balote.drugsearch.managers.NdfrtVersionManager;
import org.balote.drugsearch.runnables.api.IVersionCheckObservee;
import org.balote.drugsearch.runnables.api.IVersionCheckObserver;
import org.balote.drugsearch.utils.InputStreamConverterUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

public class VersionCheckRunnable implements Runnable, IVersionCheckObservee {

	private static final String TAG = "VersionCheckRunnable";
	private IVersionCheckObserver observer = null;
	private Context context;

	public VersionCheckRunnable(Context context, IVersionCheckObserver observer) {
		this.context = context;
		this.observer = observer;
	}

	@Override
	public void run() {

		HttpClient myHttpClient = new DefaultHttpClient();
		HttpResponse myHttpResponse;
		int responseCode = 0;
		String xmlString = "";
		boolean hasAnUpdate = false;

		try {

			HttpGet myHttpGet = new HttpGet(NdfrtConstants.GET_VERSION_URL);

			myHttpResponse = myHttpClient.execute(myHttpGet);
			responseCode = myHttpResponse.getStatusLine().getStatusCode();

			if (responseCode == 200) {

				xmlString = InputStreamConverterUtil.convert(myHttpResponse
						.getEntity().getContent());

				String version = parseVersion(xmlString);

				Log.w(TAG, "version: " + version);

				hasAnUpdate = NdfrtVersionManager.getInstance().updateVersion(
						context, version);

			} else {

				Log.w(TAG, "response code: " + responseCode);

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			myHttpClient.getConnectionManager().shutdown();

		}

		notifyObserverOfVersionUpdate(hasAnUpdate);
	}

	private String parseVersion(String xmlString) {

		String versionNameTag = "versionName";

		try {

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			xpp.setInput(new StringReader(xmlString));

			int eventType = xpp.getEventType();

			String startTag = "";

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {

					if (xpp.getName().equalsIgnoreCase(versionNameTag)) {
						startTag = versionNameTag;
					}

				} else if (eventType == XmlPullParser.TEXT) {

					if (startTag.equalsIgnoreCase(versionNameTag)) {
						return xpp.getText();
					}
				}

				eventType = xpp.next();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void notifyObserverOfVersionUpdate(boolean hasAnUpdate) {
		this.observer.onNotifyVersionUpdate(hasAnUpdate);
	}

}
