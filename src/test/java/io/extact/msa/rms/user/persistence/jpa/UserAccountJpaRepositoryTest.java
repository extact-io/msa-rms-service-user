package io.extact.msa.rms.user.persistence.jpa;

import static io.extact.msa.rms.test.assertj.ToStringAssert.*;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.platform.fw.domain.vo.UserType;
import io.extact.msa.rms.test.junit5.JpaTransactionalExtension;
import io.extact.msa.rms.test.junit5.TransactionalForTest;
import io.extact.msa.rms.test.utils.TestUtils;
import io.extact.msa.rms.user.domain.UserAccount;
import io.extact.msa.rms.user.persistence.AbstractUserAccountRepositoryTest;
import io.extact.msa.rms.user.persistence.UserAccountRepository;

@ExtendWith(JpaTransactionalExtension.class)
class UserAccountJpaRepositoryTest extends AbstractUserAccountRepositoryTest {

    private UserAccountJpaRepository repository;

    @BeforeEach
    void setup(EntityManager em) {
        repository = new UserAccountJpaRepository();
        TestUtils.setFieldValue(repository, "em", em);
    }

    @Test
    @TransactionalForTest
    void testAdd() {
        var addEntity = UserAccount.ofTransient("member3", "member3", "メンバー3", "030-1111-2222", "連絡先4", UserType.MEMBER);
        repository.add(addEntity);

        addEntity.setId(4);
        var expect = addEntity;
        var actual = repository.get(4);
        assertThatToString(actual).isEqualTo(expect);
    }

    @Override
    protected UserAccountRepository repository() {
        return repository;
    }
}
