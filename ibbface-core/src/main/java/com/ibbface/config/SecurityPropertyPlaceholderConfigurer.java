/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) SecurityPropertyPlaceholderConfigurer.java 2013-09-29 15:28
 */

package com.ibbface.config;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.ibbface.security.crypto.AES;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Fuchun
 * @version $Id: SecurityPropertyPlaceholderConfigurer.java 43888 2013-09-29 00:54:28Z C629 $
 * @since 1.0
 */
public class SecurityPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityPropertyPlaceholderConfigurer.class);
    private static final String DEFAULT_PROPERTY_VALUE_KEY = "9&4@4@3#3$2$2(7";
    private static final String VALUE_PREFIX = "CipherText:{";
    private static final String VALUE_SUFFIX = "}";
    private static final Pattern CIPHER_TEXT_PATTERN = Pattern.compile(Joiner.on("").join(
            VALUE_PREFIX.replaceFirst("\\{", "\\\\{"),
            "([\\w|\\-_=\\.\\*\\{\\}]*)",
            "\\", VALUE_SUFFIX));

    private Set<String> encryptProps = ImmutableSet.of();
    protected Resource[] propertyResources;

    public void setEncryptProps(Set<String> encryptProps) {
        this.encryptProps = encryptProps;
    }

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (!encryptProps.contains(propertyName)) {
            return super.convertProperty(propertyName, propertyValue);
        }
        Matcher m = CIPHER_TEXT_PATTERN.matcher(propertyValue);
        if (m.find()) {
            String cipherText = m.group(1);
            String sourceText = AES.ecbPKCS5Padding(
                    DigestUtils.md5(DEFAULT_PROPERTY_VALUE_KEY + propertyName)
            ).decryptHex2String(cipherText);
            if (sourceText != null) {
                propertyValue = sourceText;
            } else {
                LOGGER.error("Decrypt property name[{}] = value[{}] error.",
                        propertyName, propertyValue);
            }
        } else {
            LOGGER.info("The property ({}={}) is not encrypted.", propertyName, propertyValue);
        }
        return super.convertProperty(propertyName, propertyValue);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        super.postProcessBeanFactory(beanFactory);

        for (Resource resource : propertyResources) {
            try {
                File file = resource.getFile();
                if (file.isFile()) {
                    if (file.canWrite()) {
                        encryptPropertyFile(file);
                    } else {
                        if (LOGGER.isWarnEnabled()) {
                            LOGGER.warn("The resource file {} cannot be write.", file);
                        }
                    }
                } else {
                    if (LOGGER.isWarnEnabled()) {
                        LOGGER.warn("The resource file {} is not normal file.", resource);
                    }
                }
            } catch (IOException ex) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn("The resource file {} is not normal file.", resource);
                }
            }
        }
    }

    private void encryptPropertyFile(File file) {
        InputSupplier<InputStreamReader> inputSupplier = Files.newReaderSupplier(file, Charsets.UTF_8);
        List<String> outputLines = Lists.newLinkedList();
        List<String> inputLines = null;
        try {
            inputLines = CharStreams.readLines(inputSupplier);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        if (inputLines == null) {
            return;
        }
        Matcher m;
        String key, value, srcValue;
        boolean hasEncrypted = false;
        for (String line : inputLines) {
            line = Strings.nullToEmpty(line).trim();
            if (Strings.isNullOrEmpty(line) || line.startsWith("#") ||
                    !line.contains("=")) {
                outputLines.add(line);
                continue;
            }
            Iterable<String> pair = Splitter.on("=").trimResults().split(line);
            key = Iterables.getFirst(pair, "");
            srcValue = Iterables.getLast(pair);
            if (!encryptProps.contains(key)) {
                outputLines.add(line);
                continue;
            }
            m = CIPHER_TEXT_PATTERN.matcher(srcValue);
            if (!m.find()) {

                value = Joiner.on("").join(VALUE_PREFIX,
                        AES.ecbPKCS5Padding(
                                DigestUtils.md5(DEFAULT_PROPERTY_VALUE_KEY + key)
                        ).encryptString(srcValue), VALUE_SUFFIX);
                hasEncrypted = true;
                outputLines.add(String.format("%s=%s", key, value));
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Encrypt property {}'s value {} to {}",
                            key, srcValue, value);
                }
            }
        }
        if (hasEncrypted) {
            File tmpFile = null;
            boolean delResult;
            Closer closer = Closer.create();
            try {
                tmpFile = File.createTempFile(file.getName(), null, file.getParentFile());

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Creates a tmp file {} for properties to encrypt.", tmpFile);
                }

                BufferedWriter writer = closer.register(Files.newWriter(tmpFile, Charsets.UTF_8));

                for (String line : outputLines) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.flush();
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage(), ex);
                return;
            } finally {
                try {
                    closer.close();
                } catch (Exception ex) {
                    // ignore
                }
            }
            // the backup file
            File backupFile = new File(String.format("%s_%s", file.getAbsolutePath(),
                    System.currentTimeMillis()));
            if (!file.renameTo(backupFile)) {
                LOGGER.error("Cannot encrypt properties file {}, cause to backup failed.", file);
                delResult = tmpFile.delete();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Delete the tmp properties file {} {}!", tmpFile,
                            (delResult ? "success" : "failed"));
                }
            } else {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Backup properties file {} to {}", file, backupFile);
                }
                if (!tmpFile.renameTo(file)) {
                    LOGGER.error("Cannot encrypt properties file {}, cause rename tmp file failed.", file);
                    if (backupFile.renameTo(file)) {
                        LOGGER.info("Restore the backup properties file success!");
                    } else {
                        LOGGER.error("Restore the backup properties file error!");
                    }
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("The tmp file {} rename to {}", tmpFile, file);
                    }
                    delResult = backupFile.delete();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Delete the backup file {} {}!", backupFile,
                                (delResult ? "success" : "failed"));
                    }
                }
            }
        }
    }

    @Override
    public void setLocations(Resource[] locations) {
        super.setLocations(locations);
        propertyResources = locations;
    }

    @Override
    public void setLocation(Resource location) {
        super.setLocation(location);
        propertyResources = new Resource[]{location};
    }
}
