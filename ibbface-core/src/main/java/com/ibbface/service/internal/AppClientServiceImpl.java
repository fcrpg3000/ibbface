package com.ibbface.service.internal;

import com.ibbface.domain.model.client.AppClient;
import com.ibbface.repository.client.AppClientRepository;
import com.ibbface.service.AppClientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Fuchun
 * @since 1.0
 */
@Service("appClientService")
public class AppClientServiceImpl implements AppClientService {

    private AppClientRepository appClientRepository;

    @Override
    public AppClient getAppClient(Integer id) {
        checkArgument(id != null, "The given app client id must not be null.");
        assert id != null;
        if (id <= 0) {
            return null;
        }
        return appClientRepository.findOne(id);
    }

    @Resource
    public void setAppClientRepository(AppClientRepository appClientRepository) {
        this.appClientRepository = appClientRepository;
    }
}
