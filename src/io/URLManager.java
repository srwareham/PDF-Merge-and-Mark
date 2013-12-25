package io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;

import util.SystemConfiguration;

import logging.MessageLogger;

public class URLManager implements InputStreamFetcher {

	private static final String ERROR_DOWNLOADING = "errorDownloading";
	private static final String REQUESTING = "requesting";
	private static final String OBTAINED_STREAM_FROM = "obtainedStreamFrom";

	@Override
	public InputStream fetchInputStream(String url) {
		InputStream is = null;
		try {
			MessageLogger.getLogger().log(
					Level.INFO,
					SystemConfiguration.getLocalizedString(REQUESTING) + ": "
							+ url);

			URL u = new URL(url);
			is = u.openStream();

			MessageLogger.getLogger().log(
					Level.INFO,
					SystemConfiguration
							.getLocalizedString(OBTAINED_STREAM_FROM)
							+ ": "
							+ url);

		} catch (IOException e) {
			e.printStackTrace();
			MessageLogger.getLogger().log(
					Level.SEVERE,
					SystemConfiguration.getLocalizedString(ERROR_DOWNLOADING)
							+ ": " + url);
		}
		return is;
	}
}
