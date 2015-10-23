package org.balote.drugsearch.runnables.api;

public interface IVersionCheckObservee {

	public void notifyObserverOfVersionUpdate(boolean hasAnUpdate);
}
