package io.extact.msa.rms.user.it.persistence;

import static org.assertj.core.api.Assertions.*;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.platform.fw.persistence.GenericRepository;
import io.extact.msa.rms.test.junit5.JulToSLF4DelegateExtension;
import io.extact.msa.rms.user.domain.UserAccount;
import io.extact.msa.rms.user.persistence.UserAccountRepository;

@ExtendWith(JulToSLF4DelegateExtension.class)
abstract class AbstractUserAccountRepositoryValidationTest {

    @Test
    void testAddValidate() {
        testAddEntity();
    }

    @Test
    void testUpdateValidate() {
        testUpdateEntity();
    }

    void testAddEntity() {
        var repo = CDI.current().select(UserAccountRepository.class).get();
        testOfAddEntity(repo, new UserAccount(), 4);
    }

    void testUpdateEntity() {
        var repo = CDI.current().select(UserAccountRepository.class).get();
        testOfUpdateEntity(repo, new UserAccount(), 5);
    }

    <T> void testOfAddEntity(GenericRepository<T> repository, T entity, int expectedErrorSize) {
        var thrown = catchThrowable(() -> repository.add(entity));
        assertThat(thrown).isInstanceOf(ConstraintViolationException.class);
        assertThat(((ConstraintViolationException) thrown).getConstraintViolations()).hasSize(expectedErrorSize);
    }

    <T> void testOfUpdateEntity(GenericRepository<T> repository, T entity, int expectedErrorSize) {
        var thrown = catchThrowable(() -> repository.update(entity));
        assertThat(thrown).isInstanceOf(ConstraintViolationException.class);
        assertThat(((ConstraintViolationException) thrown).getConstraintViolations()).hasSize(expectedErrorSize);
    }
}
