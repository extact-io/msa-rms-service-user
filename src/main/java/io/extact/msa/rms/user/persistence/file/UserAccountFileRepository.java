package io.extact.msa.rms.user.persistence.file;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import io.extact.msa.rms.platform.fw.persistence.GenericRepository.ApiType;
import io.extact.msa.rms.platform.fw.persistence.file.AbstractFileRepository;
import io.extact.msa.rms.platform.fw.persistence.file.io.FileAccessor;
import io.extact.msa.rms.platform.fw.persistence.file.producer.EntityArrayConverter;
import io.extact.msa.rms.platform.core.extension.EnabledIfRuntimeConfig;
import io.extact.msa.rms.user.domain.UserAccount;
import io.extact.msa.rms.user.persistence.UserAccountRepository;

@ApplicationScoped
@EnabledIfRuntimeConfig(propertyName = ApiType.PROP_NAME, value = ApiType.FILE)
public class UserAccountFileRepository extends AbstractFileRepository<UserAccount> implements UserAccountRepository {

    @Inject
    public UserAccountFileRepository(FileAccessor fileAccessor, EntityArrayConverter<UserAccount> converter) {
        super(fileAccessor, converter);
    }

    @Override
    public UserAccount findByLoginIdAndPasswod(String loginId, String password) {
        return load().stream()
                .filter(attributes -> attributes[1].equals(loginId))
                .filter(attributes -> attributes[2].equals(password))
                .map(this.getConverter()::toEntity)
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserAccount findByLoginId(String loginId) {
        return load().stream()
                .filter(attributes -> attributes[1].equals(loginId))
                .map(this.getConverter()::toEntity)
                .findFirst()
                .orElse(null);
    }
}
