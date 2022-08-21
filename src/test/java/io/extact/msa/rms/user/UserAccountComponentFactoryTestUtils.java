package io.extact.msa.rms.user;

import java.io.IOException;
import java.nio.file.Path;

import io.extact.msa.rms.platform.fw.persistence.file.io.FileAccessor;
import io.extact.msa.rms.platform.fw.persistence.file.io.PathResolver;
import io.extact.msa.rms.user.persistence.file.UserAccountArrayConverter;
import io.extact.msa.rms.user.persistence.file.UserAccountFileRepository;
import io.extact.msa.rms.user.service.UserAccountService;

/**
 * テストケースで利用するコンポーネントファクトリユーティルクラス。
 */
public class UserAccountComponentFactoryTestUtils {

    private static final String USER_ACCOUNT_TEST_FILE_NAME = "userAccountTest.csv";

    public static UserAccountFileRepository newUserAccountFileRepository(PathResolver pathResolver) throws IOException {
        Path tempFile = FileAccessor.copyResourceToRealPath(USER_ACCOUNT_TEST_FILE_NAME, pathResolver);
        FileAccessor fa = new FileAccessor(tempFile);
        return new UserAccountFileRepository(fa, UserAccountArrayConverter.INSTANCE);
    }

    public static UserAccountService newUserAccountService(PathResolver pathResolver) throws IOException {
        return new UserAccountService(newUserAccountFileRepository(pathResolver));
    }
}
