package org.balote.drugsearch.runnables.impl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.balote.drugsearch.constants.NdfrtConstants;
import org.balote.drugsearch.runnables.api.IGetAllDrugsObservee;
import org.balote.drugsearch.runnables.api.IGetAllDrugsObserver;
import org.balote.drugsearch.utils.InputStreamConverterUtil;

import android.util.Log;

public class GetAllDrugsRunnable implements Runnable, IGetAllDrugsObservee {

	private static final String TAG = "GetAllDrugsRunnable";

	private IGetAllDrugsObserver observer = null;

	public GetAllDrugsRunnable(IGetAllDrugsObserver observer) {
		this.observer = observer;
	}

	@Override
	public void run() {

		Log.w(TAG, "run()");

		long startTime = System.currentTimeMillis();

		HttpClient myHttpClient = new DefaultHttpClient();
		HttpResponse myHttpResponse;
		String xmlString = "";

		try {

			HttpGet myHttpGet = new HttpGet(
					NdfrtConstants.GET_ALL_CONCEPTS_OF_DRUG_KIND_URL);

			myHttpResponse = myHttpClient.execute(myHttpGet);

			if (myHttpResponse.getStatusLine().getStatusCode() == 200) {

				xmlString = InputStreamConverterUtil.convert(myHttpResponse
						.getEntity().getContent());
				notifyObserverDownloadDone(xmlString);

			} else {

				// TODO

			}

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			myHttpClient.getConnectionManager().shutdown();

		}

		long executionTime = System.currentTimeMillis() - startTime;
		Log.i(TAG, "@ run() execution time: " + executionTime + " millis");

	}

	@Override
	public void notifyObserverDownloadDone(String xmlString) {
		observer.onNotifyDownloadDone(xmlString);
	}

}
