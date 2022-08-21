package io.extact.msa.rms.user.service;

import static io.extact.msa.rms.test.assertj.ToStringAssert.*;
import static io.extact.msa.rms.user.UserAccountComponentFactoryTestUtils.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.platform.fw.domain.vo.UserType;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException.CauseType;
import io.extact.msa.rms.platform.fw.persistence.file.io.PathResolver;
import io.extact.msa.rms.platform.test.PathResolverParameterExtension;
import io.extact.msa.rms.user.domain.UserAccount;

@ExtendWith(PathResolverParameterExtension.class)
class UserAccountServiceTest {

    private UserAccountService service;

    @BeforeEach
    void setup(PathResolver pathResolver) throws Exception {
        service = newUserAccountService(pathResolver);
    }

    @Test
    void testGet() {
        var expect = UserAccount.of(1, "member1", "member1", "メンバー1", "070-1111-2222", "連絡先1", UserType.MEMBER);
        var actual = service.get(1);
        assertThatToString(actual.get()).isEqualTo(expect);
    }

    @Test
    void testGetNull() {
        var actual = service.get(555); // 存在しないID
        assertThat(actual).isEmpty();
    }

    @Test
    void testFindAll() {
        var expected = List.of(
                UserAccount.of(1, "member1", "member1", "メンバー1", "070-1111-2222", "連絡先1", UserType.MEMBER),
                UserAccount.of(2, "member2", "member2", "メンバー2", "080-1111-2222", "連絡先2", UserType.MEMBER),
                UserAccount.of(3, "admin", "admin", "管理者", "050-1111-2222", "連絡先3", UserType.ADMIN)
                );
        var actuals = service.findAll();
        assertThatToString(actuals).containsExactlyElementsOf(expected);
    }

    @Test
    void testFindByLoginIdAndPassword() {
        var expect = UserAccount.of(1, "member1", "member1", "メンバー1", "070-1111-2222", "連絡先1", UserType.MEMBER);
        var actual = service.findByLoginIdAndPasswod("member1", "member1").get();
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    void testFindByIdAndPasswordOnNull() {
        var actual = service.findByLoginIdAndPasswod("member1", "hoge"); // password誤り
        assertThat(actual).isEmpty();

        actual = service.findByLoginIdAndPasswod("hoge", "member1"); // loginId誤り
        assertThat(actual).isEmpty();

        actual = service.findByLoginIdAndPasswod("hoge", "hoge"); // 両方誤り
        assertThat(actual).isEmpty();
    }

    @Test
    void testFindByLoginId() {
        var expect = UserAccount.of(3, "admin", "admin", "管理者", "050-1111-2222", "連絡先3", UserType.ADMIN);
        UserAccount actual = service.findByLoginId("admin");
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    void testFindLoginIdOnNull() {
        UserAccount actual = service.findByLoginId("mamezou"); // 存在しないloginId
        assertThat(actual).isNull();
    }

    @Test
    void testAdd() {
        var addEntity = UserAccount.ofTransient("member3", "member3", "メンバー3", "030-1111-2222", "連絡先4", UserType.MEMBER);
        var expect = UserAccount.of(4, "member3", "member3", "メンバー3", "030-1111-2222", "連絡先4", UserType.MEMBER);
        UserAccount actual = service.add(addEntity);
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    void testAddOnDuplicate() {
        // "member1"のloginIdは既に登録済み
        var addUserAccount = UserAccount.ofTransient("member1", "member3", "メンバー3", "030-1111-2222", "連絡先4", UserType.MEMBER);
        var thrown = catchThrowable(() -> service.add(addUserAccount));
        assertThat(thrown).isInstanceOf(BusinessFlowException.class);
        assertThat(((BusinessFlowException) thrown).getCauseType()).isEqualTo(CauseType.DUPRICATE);
    }

    @Test
    void testUpdate() {
        var expect = UserAccount.of(1, "member1", "UPDATE", "メンバー1", "070-1111-2222", "連絡先1", UserType.MEMBER);

        var updateUser = service.get(1).get();
        updateUser.setPassword("UPDATE");

        var actual = service.update(updateUser);
        var reloadUser = service.get(1).get();

        assertThatToString(actual).isEqualTo(expect);
        assertThatToString(actual).isEqualTo(reloadUser);
    }

    @Test
    void testUpdateOnNotFound() {
        var updateUser = UserAccount.of(999, "member1", "member3", "メンバー3", "030-1111-2222", "連絡先4", UserType.MEMBER);
        var thrown = catchThrowable(() -> service.update(updateUser));
        assertThat(thrown).isInstanceOf(BusinessFlowException.class);
        assertThat(((BusinessFlowException) thrown).getCauseType()).isEqualTo(CauseType.NOT_FOUND);
    }

    @Test
    void testDelete() {
        service.delete(3);
        assertThat(service.get(3)).isEmpty();
    }

    @Test
    void testDeleteOnNotFound() {
        var thrown = catchThrowable(() -> service.delete(999));
        assertThat(thrown).isInstanceOf(BusinessFlowException.class);
        assertThat(((BusinessFlowException) thrown).getCauseType()).isEqualTo(CauseType.NOT_FOUND);
    }
}
