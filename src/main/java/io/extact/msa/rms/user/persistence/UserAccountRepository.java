package io.extact.msa.rms.user.persistence;

import io.extact.msa.rms.platform.fw.persistence.GenericRepository;
import io.extact.msa.rms.user.domain.UserAccount;

public interface UserAccountRepository extends GenericRepository<UserAccount> {

    /**
     * ログインIDとパスワードに一致するユーザを取得。
     *
     * @param loginId ログインID
     * @param password パスワード
     * @return 該当ユーザ。該当なしはnull
     */
    UserAccount findByLoginIdAndPasswod(String loginId, String password);

    //
    /**
     * ログインIDに一致するユーザを取得する。
     *
     * @param loginId ログインID
     * @return 該当ユーザ。該当なしはnull
     */
    UserAccount findByLoginId(String loginId);
}