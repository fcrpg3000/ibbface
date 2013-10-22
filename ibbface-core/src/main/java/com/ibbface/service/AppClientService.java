package com.ibbface.service;

import com.ibbface.domain.model.client.AppClient;

/**
 * @author Fuchun
 * @since 1.0
 */
public interface AppClientService {

    public boolean isValid(Integer id);

    public AppClient getAppClient(Integer id);
}
