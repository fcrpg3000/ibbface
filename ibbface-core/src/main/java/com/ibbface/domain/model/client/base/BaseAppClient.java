package com.ibbface.domain.model.client.base;

import com.google.common.base.Objects;
import com.ibbface.domain.model.client.AppClient;
import com.ibbface.domain.shared.AbstractEntity;

import java.util.Date;

/**
 * @author Fuchun
 * @since 1.0
 */
public abstract class BaseAppClient extends AbstractEntity<Integer, AppClient> {
    private static final long serialVersionUID = 1L;

    public static final String PROP_CLIENT_SECRET = "clientSecret";
    public static final String PROP_TYPE_CODE = "typeCode";
    public static final String PROP_VERSION = "version";
    public static final String PROP_VERSION_NAME = "versionName";
    public static final String PROP_UPGRADE_CONTENT = "upgradeContent";
    public static final String PROP_DOWNLOAD_URLS = "downloadUrls";
    public static final String PROP_INCOMPATIBLE_DATA = "incompatibleData";
    public static final String PROP_IS_STABLE = "isStable";
    public static final String PROP_PUBLISH_TIME = "publishTime";

    private String clientSecret;
    private Short typeCode;
    private String version;
    private String versionName;
    private String upgradeContent;
    private String downloadUrls;
    private String incompatibleData;
    private boolean isStable;
    private Date publishTime;
    private Date lastModifiedTime;
    private Date createdTime;

    protected BaseAppClient() {
        super();
    }

    protected BaseAppClient(Integer id) {
        setId(id);
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Short getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Short typeCode) {
        this.typeCode = typeCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpgradeContent() {
        return upgradeContent;
    }

    public void setUpgradeContent(String upgradeContent) {
        this.upgradeContent = upgradeContent;
    }

    public String getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(String downloadUrls) {
        this.downloadUrls = downloadUrls;
    }

    public String getIncompatibleData() {
        return incompatibleData;
    }

    public void setIncompatibleData(String incompatibleData) {
        this.incompatibleData = incompatibleData;
    }

    public boolean isStable() {
        return isStable;
    }

    public void setStable(boolean stable) {
        isStable = stable;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add(PROP_ID, getId())
                .add(PROP_CLIENT_SECRET, getClientSecret())
                .add(PROP_TYPE_CODE, getTypeCode())
                .add(PROP_VERSION, getVersion())
                .add(PROP_VERSION_NAME, getVersionName())
                .add(PROP_UPGRADE_CONTENT, getUpgradeContent())
                .add(PROP_DOWNLOAD_URLS, getDownloadUrls())
                .add(PROP_INCOMPATIBLE_DATA, getIncompatibleData())
                .add(PROP_IS_STABLE, isStable())
                .add(PROP_PUBLISH_TIME, getPublishTime())
                .add(PROP_LAST_MODIFIED_TIME, getLastModifiedTime())
                .add(PROP_CREATED_TIME, getCreatedTime())
                .toString();
    }
}
