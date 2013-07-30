package com.brunoreis.awsexplorer;

import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Resources;

public class VersionProvider {
  private static final String DEFAULT_VERSION = "master";
  public static String getVersion() {
    try {
      final URL versionResource = Resources.getResource("version");
      final String version = Resources.toString(versionResource, StandardCharsets.UTF_8);
      if (version.contains("SNAPSHOT")) {
        return DEFAULT_VERSION;
      } else {
        return version;
      }
    } catch (Exception e) {
      return DEFAULT_VERSION;
    }
  }
}
