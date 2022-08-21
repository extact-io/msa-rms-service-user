package io.extact.msa.rms.user.service;

import static io.extact.msa.rms.platform.fw.exception.BusinessFlowException.CauseType.*;

import java.util.Optional;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import io.extact.msa.rms.platform.fw.exception.BusinessFlowException;
import io.extact.msa.rms.platform.fw.persistence.GenericRepository;
import io.extact.msa.rms.platform.fw.service.GenericService;
import io.extact.msa.rms.user.domain.UserAccount;
import io.extact.msa.rms.user.persistence.UserAccountRepository;


@Transactional(TxType.REQUIRED)
@ApplicationScoped
public class UserAccountService implements GenericService<UserAccount> {

    private UserAccountRepository repository;

    @Inject
    public UserAccountService(UserAccountRepository userRepository) {
        this.repository = userRepository;
    }

    public Optional<UserAccount> findByLoginIdAndPasswod(String loginId, String password) {
        var u = repository.findByLoginIdAndPasswod(loginId, password);
        return Optional.ofNullable(u);
    }


    public UserAccount findByLoginId(String loginId) {
        return repository.findByLoginId(loginId);
    }

    @Override
    public Consumer<UserAccount> getDuplicateChecker() {
        return (targetUser) -> {
            var foundUser = findByLoginId(targetUser.getLoginId());
            if (foundUser != null && (targetUser.getId() == null || !foundUser.isSameId(targetUser))) {
                throw new BusinessFlowException("loginId is already registered.", DUPRICATE);
            }
        };
    }

    @Override
    public GenericRepository<UserAccount> getRepository() {
        return this.repository;
    }
}
