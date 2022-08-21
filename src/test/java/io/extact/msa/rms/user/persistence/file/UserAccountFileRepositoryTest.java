package io.extact.msa.rms.user.persistence.file;

import static io.extact.msa.rms.user.UserAccountComponentFactoryTestUtils.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.platform.fw.domain.vo.UserType;
import io.extact.msa.rms.platform.fw.persistence.file.io.PathResolver;
import io.extact.msa.rms.platform.test.PathResolverParameterExtension;
import io.extact.msa.rms.platform.test.PlatformTestUtils;
import io.extact.msa.rms.user.domain.UserAccount;
import io.extact.msa.rms.user.persistence.AbstractUserAccountRepositoryTest;
import io.extact.msa.rms.user.persistence.UserAccountRepository;

@ExtendWith(PathResolverParameterExtension.class)
class UserAccountFileRepositoryTest extends AbstractUserAccountRepositoryTest {

    private UserAccountFileRepository repository;

    @BeforeEach
    void setUp(PathResolver pathResolver) throws Exception {
        repository = newUserAccountFileRepository(pathResolver);
    }

    @Test
    void testAdd() throws Exception {
        var addUser = UserAccount.ofTransient("member3", "member3", "メンバー3", "050-1111-2222", "連絡先4", UserType.MEMBER);
        repository.add(addUser);

        List<String[]> records = PlatformTestUtils.getAllRecords(repository.getStoragePath());
        String[] lastRecord = records.get(records.size() - 1);
        String[] expectRow = { String.valueOf(records.size()), "member3", "member3", "メンバー3", "050-1111-2222", "連絡先4", "MEMBER" };

        assertThat(lastRecord).containsExactly(expectRow);
    }

    @Override
    protected UserAccountRepository repository() {
        return repository;
    }
}
