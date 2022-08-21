package io.extact.msa.rms.user.it.service;

import static io.extact.msa.rms.test.assertj.ToStringAssert.*;
import static org.assertj.core.api.Assertions.*;

import javax.inject.Inject;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.extact.msa.rms.platform.fw.domain.vo.UserType;
import io.extact.msa.rms.user.domain.UserAccount;
import io.extact.msa.rms.user.service.UserAccountService;

@TestMethodOrder(OrderAnnotation.class)
abstract class AbstractUserAccountServiceCrudTest {

    @Inject
    private UserAccountService userService;

    @Test
    @Order(1)
    void testGet() {
        var expect = UserAccount.of(3, "admin", "admin", "管理者", "050-1111-2222", "連絡先3", UserType.ADMIN);
        var actual = userService.get(3).get();
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    @Order(2)
    void testFindAll() {
        var actual = userService.findAll();
        assertThat(actual).hasSize(3);
    }

    @Test
    @Order(3)
    void testUpate() {
        var expected = UserAccount.of(2, "update1", "update2", "update3", "999-1111-2222", "update4", UserType.ADMIN);

        var updateUser = userService.get(2).get();
        updateUser.setLoginId("update1");
        updateUser.setPassword("update2");
        updateUser.setUserName("update3");
        updateUser.setPhoneNumber("999-1111-2222");
        updateUser.setContact("update4");
        updateUser.setUserType(UserType.ADMIN);
        var actual = userService.update(updateUser);

        assertThatToString(actual).isEqualTo(expected);
    }

    @Test
    @Order(4)
    void testAdd() {
        var u = UserAccount.ofTransient("member3", "member3", "メンバー3", "030-1111-2222", "連絡先4", UserType.MEMBER);
        var expected = UserAccount.of(4, u.getLoginId(), u.getPassword(), u.getUserName(), u.getPhoneNumber(), u.getContact(), u.getUserType());
        var actual = userService.add(u);
        assertThatToString(actual).isEqualTo(expected);
    }

    @Test
    @Order(5)
    void testDelete() {
        userService.delete(4);
        var actual = userService.findAll();
        assertThat(actual).hasSize(3);
    }
}
