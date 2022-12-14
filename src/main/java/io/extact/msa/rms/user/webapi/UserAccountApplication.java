package io.extact.msa.rms.user.webapi;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;

import io.extact.msa.rms.platform.core.jwt.login.PropagatedLoginHeaderRequestFilter;
import io.extact.msa.rms.platform.fw.webapi.server.RmsApplication;

@ApplicationScoped
public class UserAccountApplication extends RmsApplication {

    @Override
    protected Set<Class<?>> getWebApiClasses() {
        return Set.of(
                    PropagatedLoginHeaderRequestFilter.class,
                    UserAccountResourceImpl.class
                );
    }
}
