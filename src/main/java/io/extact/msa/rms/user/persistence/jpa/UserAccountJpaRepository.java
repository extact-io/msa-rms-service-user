package io.extact.msa.rms.user.persistence.jpa;

import io.extact.msa.rms.platform.core.extension.EnabledIfRuntimeConfig;
import io.extact.msa.rms.platform.fw.persistence.GenericRepository.ApiType;
import io.extact.msa.rms.platform.fw.persistence.jpa.JpaCrudRepository;
import io.extact.msa.rms.user.domain.UserAccount;
import io.extact.msa.rms.user.persistence.UserAccountRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
@EnabledIfRuntimeConfig(propertyName = ApiType.PROP_NAME, value = ApiType.JPA)
public class UserAccountJpaRepository extends JpaCrudRepository<UserAccount> implements UserAccountRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserAccount findByLoginIdAndPasswod(String loginId, String password) {
        var jpql = "select u from UserAccount u where u.loginId = ?1 and u.password = ?2";
        try {
            return em.createQuery(jpql, UserAccount.class)
                        .setParameter(1, loginId)
                        .setParameter(2, password)
                        .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UserAccount findByLoginId(String loginId) {
        var jpql = "select u from UserAccount u where u.loginId = ?1";
        try {
            return em.createQuery(jpql, UserAccount.class)
                        .setParameter(1, loginId)
                        .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public EntityManager getEntityManage() {
        return this.em;
    }

    @Override
    public Class<UserAccount> getTargetClass() {
        return UserAccount.class;
    }
}
