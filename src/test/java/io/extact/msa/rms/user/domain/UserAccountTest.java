package io.extact.msa.rms.user.domain;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.extact.msa.rms.platform.fw.domain.constraint.Contact;
import io.extact.msa.rms.platform.fw.domain.constraint.LoginId;
import io.extact.msa.rms.platform.fw.domain.constraint.Passowrd;
import io.extact.msa.rms.platform.fw.domain.constraint.PhoneNumber;
import io.extact.msa.rms.platform.fw.domain.constraint.RmsId;
import io.extact.msa.rms.platform.fw.domain.constraint.UserName;
import io.extact.msa.rms.platform.fw.domain.constraint.UserTypeConstraint;
import io.extact.msa.rms.platform.fw.domain.constraint.ValidationGroups.Update;
import io.extact.msa.rms.platform.fw.domain.vo.UserType;
import io.extact.msa.rms.test.assertj.ConstraintViolationSetAssert;
import io.extact.msa.rms.test.junit5.ValidatorParameterExtension;
import io.extact.msa.rms.test.utils.PropertyTest;

@ExtendWith(ValidatorParameterExtension.class)
class UserAccountTest extends PropertyTest {

    @Override
    protected Class<?> getTargetClass() {
        return UserAccount.class;
    }

    @Test
    void testSetId() throws Exception {
        UserAccount testee = new UserAccount();
        testee.setId(100);
        Field id = this.getField("id");

        assertThat(id).isNotNull();
        assertThat(id.get(testee)).isEqualTo(100);
    }

    @Test
    void testGetId() throws Exception {
        UserAccount testee = new UserAccount();
        Field id = this.getField("id");

        assertThat(id).isNotNull();

        id.set(testee, 100);
        assertThat(testee.getId()).isEqualTo(100);
    }

    @Test
    void testSetLoginId() throws Exception {
        UserAccount testee = new UserAccount();
        testee.setLoginId("soramame");
        Field loginId = this.getField("loginId");

        assertThat(loginId).isNotNull();
        assertThat(loginId.get(testee)).isEqualTo("soramame");
    }

    @Test
    void testGetLoginName() throws Exception {
        UserAccount testee = new UserAccount();
        Field loginId = this.getField("loginId");

        assertThat(loginId).isNotNull();

        loginId.set(testee, "soramame");
        assertThat(testee.getLoginId()).isEqualTo("soramame");
    }

    @Test
    void testSetPassword() throws Exception {
        UserAccount testee = new UserAccount();
        testee.setPassword("soramame");
        Field password = this.getField("password");

        assertThat(password).isNotNull();
        assertThat(password.get(testee)).isEqualTo("soramame");
    }

    @Test
    void testGetPassword() throws Exception {
        UserAccount testee = new UserAccount();
        Field password = this.getField("password");

        assertThat(password).isNotNull();

        password.set(testee, "soramame");
        assertThat(testee.getPassword()).isEqualTo("soramame");
    }

    @Test
    void testSetUserName() throws Exception {
        UserAccount testee = new UserAccount();
        testee.setUserName("soramame");
        Field userName = this.getField("userName");

        assertThat(userName).isNotNull();
        assertThat(userName.get(testee)).isEqualTo("soramame");
    }

    @Test
    void testGetUserName() throws Exception {
        UserAccount testee = new UserAccount();
        Field userName = this.getField("userName");

        assertThat(userName).isNotNull();

        userName.set(testee, "soramame");
        assertThat(testee.getUserName()).isEqualTo("soramame");
    }

    @Test
    void testSetPhoneNumber() throws Exception {
        UserAccount testee = new UserAccount();
        testee.setPhoneNumber("1234567890");
        Field phoneNumber = this.getField("phoneNumber");

        assertThat(phoneNumber).isNotNull();
        assertThat(phoneNumber.get(testee)).isEqualTo("1234567890");
    }

    @Test
    void testGetPhoneNumber() throws Exception {
        UserAccount testee = new UserAccount();
        Field phoneNumber = this.getField("phoneNumber");

        assertThat(phoneNumber).isNotNull();

        phoneNumber.set(testee, "12345678890");
        assertThat(testee.getPhoneNumber()).isEqualTo("12345678890");
    }

    @Test
    void testSetContact() throws Exception {
        UserAccount testee = new UserAccount();
        testee.setContact("住所");
        Field contact = this.getField("contact");

        assertThat(contact).isNotNull();
        assertThat(contact.get(testee)).isEqualTo("住所");
    }

    @Test
    void testGetContact() throws Exception {
        UserAccount testee = new UserAccount();
        Field contact = this.getField("contact");

        assertThat(contact).isNotNull();

        contact.set(testee, "住所");
        assertThat(testee.getContact()).isEqualTo("住所");
    }

    @Test
    void testSetUserType() throws Exception {
        UserAccount testee = new UserAccount();
        testee.setUserType(UserType.ADMIN);
        Field userType = this.getField("userType");

        assertThat(userType).isNotNull();
        assertThat(userType.get(testee)).isEqualTo(UserType.ADMIN);
    }

    @Test
    void testGetUserType() throws Exception {
        UserAccount testee = new UserAccount();
        Field userType = this.getField("userType");

        assertThat(userType).isNotNull();

        userType.set(testee, UserType.ADMIN);
        assertThat(testee.getUserType()).isEqualTo(UserType.ADMIN);
    }

    @Test
    void testSetAdmin() throws Exception {
        UserAccount testee = new UserAccount();
        testee.setAdmin(true);
        assertThat(testee.getUserType()).isEqualTo(UserType.ADMIN);
        assertThat(testee.isAdmin()).isTrue();

        testee = new UserAccount();
        testee.setAdmin(false);
        assertThat(testee.getUserType()).isEqualTo(UserType.MEMBER);
        assertThat(testee.isAdmin()).isFalse();
    }

    @Test
    void testNewInstance() {
        UserAccount testee = UserAccount.of(1, "soramame", "password", "edamame", "12345", "address", UserType.MEMBER);

        assertThat(testee.getId()).isEqualTo(1);
        assertThat(testee.getLoginId()).isEqualTo("soramame");
        assertThat(testee.getPassword()).isEqualTo("password");
        assertThat(testee.getUserName()).isEqualTo("edamame");
        assertThat(testee.getPhoneNumber()).isEqualTo("12345");
        assertThat(testee.getUserType()).isEqualTo(UserType.MEMBER);
        assertThat(testee.isAdmin()).isFalse();
    }

    @Test
    void testConstraintAnnoteToClass() {
        Class<?>[] expected = {
                RmsId.class,
                LoginId.class,
                Passowrd.class,
                UserName.class,
                PhoneNumber.class,
                Contact.class,
                UserTypeConstraint.class
        };
        var actual = getFieldAnnotations();
        assertThat(actual).contains(expected);
    }

    @Test
    void testPropetyValidationForUpdate(Validator validator) {

        // エラーがないこと
        UserAccount u = createAllOKUserAccount();
        Set<ConstraintViolation<UserAccount>> result = validator.validate(u, Update.class);
        ConstraintViolationSetAssert.assertThat(result)
            .hasNoViolations();

        // IDに-1を設定してテスト
        u.setId(-1);
        // Group指定がなし→バリデーションSkip→バリデーションエラーなし
        result = validator.validate(u);
        ConstraintViolationSetAssert.assertThat(result)
        .hasNoViolations();
        // Groupを指定→バリデーションエラーあり
        result = validator.validate(u, Update.class);
        ConstraintViolationSetAssert.assertThat(result)
            .hasSize(1)
            .hasViolationOnPath("id")
            .hasMessageEndingWith("Min.message");
    }

    private UserAccount createAllOKUserAccount() {
        UserAccount u = new UserAccount();
        u.setId(1);
        u.setLoginId("LoginName");
        u.setPassword("Password");
        u.setUserName("UserName");
        u.setPhoneNumber("03-1234-5689");
        u.setAdmin(true);
        return u;
    }

}
