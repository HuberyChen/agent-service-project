package com.lx.agent;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Ensure integrity of all the environment configuration files/structure
 * <p/>
 * Since 1.1, added ignored dir support
 *
 * @version 1.1
 */
public class EnvironmentConfigurationTest {
    private static final List<String> IGNORED_DIRS = Arrays.asList(".svn");
    private static final String MAIN_RESOURCE_DIR = "src/main/resources";
    private static final String TEST_RESOURCE_DIR = "src/test/resources";

    @Test
    public void validateTestPropertyFilesOverridesDefault() throws Exception {
        List<String> inconsistentPropertyFiles = new ArrayList<String>();
        EnvPropertiesValidator envPropertiesValidator = new EnvPropertiesValidator(TEST_RESOURCE_DIR);
        List<String> envInconsistentPropertyFiles = envPropertiesValidator.compareToMainProperties();
        inconsistentPropertyFiles.addAll(envInconsistentPropertyFiles);
        Assert.assertTrue(buildInconsistentPropertiesErrorMessage(inconsistentPropertyFiles), inconsistentPropertyFiles.isEmpty());
    }

    private String buildInconsistentPropertiesErrorMessage(List<String> inconsistentPropertyFiles) {
        StringBuilder builder = new StringBuilder("inconsistent property files found:\n");
        for (String resource : inconsistentPropertyFiles) {
            builder.append("\t");
            builder.append(resource);
            builder.append("\n");
        }
        return builder.toString();
    }

    static class EnvPropertiesValidator {
        private String environment;
        private List<String> environmentPropertyFiles;

        public EnvPropertiesValidator(String environment) {
            this.environment = environment;
            environmentPropertyFiles = new ResourceFolderScanner(environment).getPropertyFiles();
        }

        public List<String> compareToMainProperties() throws Exception {
            List<String> inconsistentPropertyFiles = new ArrayList<String>();
            for (String environmentPropertyFile : environmentPropertyFiles) {
                String environmentPropertyPath = environment + "/" + environmentPropertyFile;
                Properties environmentProperties = loadPropertiesFromFile(environmentPropertyPath);

                File propsFile = new File(MAIN_RESOURCE_DIR + "/" + environmentPropertyFile);
                if (!propsFile.exists()) {
                    continue;
                }
                Properties mainProperties = loadPropertiesFromFile(propsFile.getPath());

                if (propertiesNotEqual(environmentProperties, mainProperties)) {
                    inconsistentPropertyFiles.add(environmentPropertyPath);
                }
            }

            return inconsistentPropertyFiles;
        }

        private boolean propertiesNotEqual(Properties properties1, Properties properties2) {
            ArrayList<Object> environmentPropertiesList = Collections.list(properties1.keys());
            ArrayList<Object> mainPropertiesList = Collections.list(properties2.keys());

            ArrayList<Object> environmentPropertiesList2 = Collections.list(properties1.keys());
            environmentPropertiesList.removeAll(mainPropertiesList);
            mainPropertiesList.removeAll(environmentPropertiesList2);

            return environmentPropertiesList.size() > 0 || mainPropertiesList.size() > 0;
        }

        private Properties loadPropertiesFromFile(String propertiesPath) throws Exception {
            Properties mainProperties = new Properties();
            mainProperties.load(new FileInputStream(propertiesPath));
            return mainProperties;
        }
    }

    static class ResourceFolderScanner {
        final String path;

        public ResourceFolderScanner(String path) {
            this.path = path;
        }

        public List<String> getPropertyFiles() {
            List<String> results = new ArrayList<String>();
            FileFilter filter = new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().endsWith(".properties");
                }
            };
            getFilesRecursively(path, results, filter);

            return removePathPrefix(results);
        }

        private void getFilesRecursively(String directoryPath, List<String> result, FileFilter filter) {
            File dir = new File(directoryPath);
            if (!dir.exists() || IGNORED_DIRS.contains(dir.getName())) return;
            File[] listFiles = dir.listFiles(filter);
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    getFilesRecursively(directoryPath + "/" + file.getName(), result, filter);
                }
                if (file.isFile()) {
                    result.add(directoryPath + "/" + file.getName());
                }
            }
        }

        private List<String> removePathPrefix(List<String> stringList) {
            List<String> result = new ArrayList<String>();
            for (String string : stringList) {
                result.add(string.substring(path.length() + 1, string.length()));
            }
            return result;
        }
    }
}
