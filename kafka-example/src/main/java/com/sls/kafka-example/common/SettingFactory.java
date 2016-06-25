package com.sls.slsjtemplate.common;

import com.sls.slsjtemplate.MainApp;
import java.io.BufferedReader;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class SettingFactory {

    protected static final Map<String, Settings> _mapConfig = new ConcurrentHashMap();
    protected static final Logger _logger = XLogger.getXLogger();
    protected static String _appName;
    protected static String _appMode;

    public static void init(String[] args) {
        try {
            _appName = System.getProperty("name");
            _appMode = System.getProperty("mode");

            CodeSource codeSource = MainApp.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();
            String rootPath = jarDir.substring(0, jarDir.lastIndexOf('/'));
            String configFile = rootPath + "/conf/" + _appMode + ".ini";
            processConfig(configFile);
        } catch (Exception ex) {
            _logger.error(null, ex);
        }
    }

    public static Settings get(String configName) {
        return (Settings) _mapConfig.get(configName);
    }

    protected static void processConfig(String filePath) {
        try {
            Pattern config = Pattern.compile("\\s*\\[\\s*(\\w+)\\s*\\]\\s*");
            Pattern settings = Pattern.compile("\\s*(\\w+)\\s*=\\s*([a-zA-Z0-9,.-_\\s]+)");
            Path configPath = Paths.get(new URI("file://" + filePath));
            BufferedReader br = Files.newBufferedReader(configPath);
            Throwable localThrowable3 = null;
            try {
                ImmutableSettings.Builder builder = null;
                String configName = null;
                String line = null;
                while ((line = br.readLine()) != null) {
                    Matcher configMatcher = config.matcher(line);
                    if (configMatcher.matches()) {
                        if ((configName != null) && (builder != null)) {
                            _mapConfig.put(configName, builder.build());
                        }
                        configName = configMatcher.group(1);
                        builder = new ImmutableSettings.Builder();
                    } else {
                        Matcher settingMatcher = settings.matcher(line);
                        if (settingMatcher.matches()) {
                            String key = settingMatcher.group(1).trim();
                            String value = settingMatcher.group(2).trim();
                            if ((builder != null) && (key != null) && (value != null) && (key.length() > 0) && (value.length() > 0)) {
                                builder.put(key, value);
                            }
                        }
                    }
                }
                if ((configName != null) && (builder != null)) {
                    _mapConfig.put(configName, builder.build());
                }
            } catch (Throwable localThrowable1) {
                localThrowable3 = localThrowable1;
                throw localThrowable1;

            } finally {

                if (br != null) {
                    if (localThrowable3 != null) {
                        try {
                            br.close();
                        } catch (Throwable localThrowable2) {
                            localThrowable3.addSuppressed(localThrowable2);
                        }
                    } else {
                        br.close();
                    }
                }
            }
        } catch (Exception ex) {
            _logger.error(null, ex);
        }
    }
}


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/common/SettingFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
