package com.ibbface.domain.model.client;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.ibbface.domain.model.client.base.BaseAppClient;
import com.ibbface.domain.shared.QueryValue;
import com.ibbface.util.RandomStrings;

import java.util.Date;
import java.util.List;

import static com.google.common.base.Objects.equal;

/**
 * @author Fuchun
 * @since 1.0
 */
public class AppClient extends BaseAppClient implements QueryValue {
    private static final long serialVersionUID = 1L;

    public static String newClientSecret(int count) {
        return RandomStrings.randomAlphanumeric(count);
    }

    public static AppClient newIPhone(
            String version, String versionName, String content,
            List<String> downloadUrls, List<String> incompatibleVersions,
            boolean isStable, Date publishTime) {
        return newClient(ClientType.IPHONE, version, versionName, content,
                downloadUrls, incompatibleVersions, isStable, publishTime);
    }

    public static AppClient newAndroid(
            String version, String versionName, String content,
            List<String> downloadUrls, List<String> incompatibleVersions,
            boolean isStable, Date publishTime) {
        return newClient(ClientType.ANDROID, version, versionName, content,
                downloadUrls, incompatibleVersions, isStable, publishTime);
    }

    public static AppClient newClient(
            ClientType clientType, String version, String versionName,
            String content, List<String> downloadUrls, List<String> incompatibleVersions,
            boolean isStable, Date publishTime) {
        final Date dateNow = new Date();
        AppClient appClient = new AppClient();
        appClient.setClientType(clientType);
        appClient.setClientSecret(newClientSecret(16));
        appClient.setVersion(version);
        appClient.setVersionName(versionName);
        appClient.setUpgradeContent(content);
        appClient.setDownloadUrlList(downloadUrls);
        appClient.setIncompatibleVersions(incompatibleVersions);
        appClient.setStable(isStable);
        appClient.setPublishTime(publishTime);
        appClient.setLastModifiedTime(dateNow);
        appClient.setCreatedTime(dateNow);
        return appClient;
    }

    private transient ClientType clientType;
    private transient List<String> incompatibleVersions;
    private transient List<String> downloadUrlList;

    public AppClient() {
        super();
    }

    public AppClient(Integer id) {
        super(id);
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
        if (clientType != null) {
            super.setTypeCode(clientType.getCode());
        }
    }

    @Override
    public void setTypeCode(Short typeCode) {
        super.setTypeCode(typeCode);
        if (typeCode != null) {
            this.clientType = ClientType.of(typeCode);
        }
    }

    public List<String> getIncompatibleVersions() {
        return incompatibleVersions;
    }

    public void setIncompatibleVersions(List<String> incompatibleVersions) {
        this.incompatibleVersions = incompatibleVersions;
        if (incompatibleVersions != null && !incompatibleVersions.isEmpty()) {
            super.setIncompatibleData(Joiner.on(",").join(incompatibleVersions));
        }
    }

    @Override
    public void setIncompatibleData(String incompatibleData) {
        super.setIncompatibleData(incompatibleData);
        if (incompatibleData != null && (incompatibleData = incompatibleData.trim()).length() > 0) {
            incompatibleVersions = Splitter.on(",").trimResults().splitToList(incompatibleData);
        }
    }

    public List<String> getDownloadUrlList() {
        return downloadUrlList;
    }

    public void setDownloadUrlList(List<String> downloadUrlList) {
        this.downloadUrlList = downloadUrlList;
        if (downloadUrlList != null && !downloadUrlList.isEmpty()) {
            super.setDownloadUrls(Joiner.on(",").join(downloadUrlList));
        }
    }

    @Override
    public void setDownloadUrls(String downloadUrls) {
        super.setDownloadUrls(downloadUrls);
        if (downloadUrls != null && (downloadUrls = downloadUrls.trim()).length() > 0) {
            downloadUrlList = Splitter.on(",").trimResults().splitToList(downloadUrls);
        }
    }

    @Override
    public AppClient update(AppClient other) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[] {
                getId(), getClientSecret(), getTypeCode(), getVersion(), getVersionName(),
                getUpgradeContent(), getDownloadUrls(), getIncompatibleData(), isStable(),
                getPublishTime(), getLastModifiedTime(), getCreatedTime()
        };
    }

    // Logic
    // --------------------------------------------------------------------------------

    public boolean isValid(final String secret, final ClientInfo clientInfo) {
        return secret != null && clientInfo != null &&
                equal(secret, getClientSecret()) && equal(getVersion(), clientInfo.getVersion());
    }
}
