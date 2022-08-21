package io.extact.msa.rms.user.it.webapi;

import static io.extact.msa.rms.platform.test.PlatformTestUtils.*;
import static io.extact.msa.rms.test.assertj.ToStringAssert.*;
import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.platform.core.jaxrs.converter.RmsTypeParameterFeature;
import io.extact.msa.rms.platform.fw.domain.vo.UserType;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException.CauseType;
import io.extact.msa.rms.test.junit5.JulToSLF4DelegateExtension;
import io.extact.msa.rms.user.webapi.UserAccountResource;
import io.extact.msa.rms.user.webapi.dto.AddUserAccountEventDto;
import io.extact.msa.rms.user.webapi.dto.UserAccountResourceDto;
import io.helidon.microprofile.tests.junit5.AddConfig;
import io.helidon.microprofile.tests.junit5.HelidonTest;

@HelidonTest
@AddConfig(key = "server.port", value = "7001")
@ExtendWith(JulToSLF4DelegateExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class UserAccountResourceTest {

    private static final int NO_SIDE_EFFECT = 1; // 副作用なし
    private UserAccountResource userResource;

    @BeforeEach
    void setup() throws Exception {
        this.userResource = RestClientBuilder.newBuilder()
                .baseUri(new URI("http://localhost:7001/users"))
                .register(RmsTypeParameterFeature.class)
                .build(UserAccountResource.class);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testGetAll() {
        var actual = userResource.getAll();
        assertThat(actual).hasSize(3);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testGet() {
        var expect = UserAccountResourceDto.of(1, "member1", "member1", "メンバー1", "070-1111-2222", "連絡先1", UserType.MEMBER);
        var actual = userResource.get(1);
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testGetOnNotFound() {
        var actual = userResource.get(999);
        assertThat(actual).isNull();
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testGetOnParameterError() {
        var thrown = catchThrowable(() -> userResource.get(-1));
        assertValidationErrorInfo(thrown, 1);
    }

    @Test
    void testAdd() {
        var addUser = AddUserAccountEventDto.of("member3", "member3", "メンバー3", "030-1111-2222", "連絡先4", UserType.MEMBER);
        var expect = UserAccountResourceDto.of(4, "member3", "member3", "メンバー3", "030-1111-2222", "連絡先4", UserType.MEMBER);
        var actual = userResource.add(addUser);
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testAddOnParameterError() {
        var thrown = catchThrowable(() -> userResource.add(new AddUserAccountEventDto())); // paramter error
        assertValidationErrorInfo(thrown, 4);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testAddOnDuplicate() {
        var addUser = AddUserAccountEventDto.of("member2", "password3", "name3", "090-0000-0000", "連絡先4", UserType.MEMBER); // loginId重複
        var thrown = catchThrowable(() -> userResource.add(addUser));
        assertGenericErrorInfo(thrown, Status.CONFLICT, BusinessFlowException.class, CauseType.DUPRICATE);
    }

    @Test
    void testUpdate() {
        var updateUser = UserAccountResourceDto.of(1, "member1", "member1", "メンバー1", "070-1111-2222", "連絡先1", UserType.MEMBER);
        var actual = userResource.update(updateUser);
        assertThatToString(actual).isEqualTo(updateUser);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testUpdateOnNotFound() {
        var updateUser = UserAccountResourceDto.of(999, "member1", "member1", "メンバー1", "070-1111-2222", "連絡先1", UserType.MEMBER); // 該当なし
        var thrown = catchThrowable(() -> userResource.update(updateUser));
        assertGenericErrorInfo(thrown, Status.NOT_FOUND, BusinessFlowException.class, CauseType.NOT_FOUND);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testUpdateParameterError() {
        var update = UserAccountResourceDto.of(null, null, null, "メンバー1", "070-1111-2222", "連絡先1", UserType.MEMBER);; // parameter error
        var thrown = catchThrowable(() -> userResource.update(update));
        assertValidationErrorInfo(thrown, 3);
    }

    @Test
    void testDelete() {
        var beforeSize = userResource.getAll().size();
        userResource.delete(3);
        var afterSize = userResource.getAll().size();
        assertThat(afterSize).isEqualTo(beforeSize - 1); // 1件削除されていること
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testDeleteOnParameterError() {
        var thrown = catchThrowable(() -> userResource.delete(-1)); // parameter error
        assertValidationErrorInfo(thrown, 1);
    }
    @Test
    @Order(NO_SIDE_EFFECT)
    void testDeleteOnNotFound() {
        var thrown = catchThrowable(() -> userResource.delete(999)); // 該当なし
        assertGenericErrorInfo(thrown, Status.NOT_FOUND, BusinessFlowException.class, CauseType.NOT_FOUND);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testAuthenticate() {
        var expect = UserAccountResourceDto.of(1, "member1", "member1", "メンバー1", "070-1111-2222", "連絡先1", UserType.MEMBER);
        var actual = userResource.authenticate("member1", "member1");
        assertThatToString(actual).isEqualTo(expect);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testAuthenticateOnFail() {
        var thrown = catchThrowable(() -> userResource.authenticate("12345", "12345")); // 該当なし
        assertGenericErrorInfo(thrown, Status.NOT_FOUND, BusinessFlowException.class, CauseType.NOT_FOUND);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testAuthenticateOnParameterError() {
        var thrown = catchThrowable(() -> userResource.authenticate("a", "a")); // parameter error
        assertValidationErrorInfo(thrown, 2);
    }

    @Test
    @Order(NO_SIDE_EFFECT)
    void testExist() {
        var actual = userResource.exists(1);
        assertThat(actual).isTrue();
        actual = userResource.exists(999);
        assertThat(actual).isFalse();
    }
}
