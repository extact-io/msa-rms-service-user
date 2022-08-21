package io.extact.msa.rms.user.domain;

import static javax.persistence.AccessType.*;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.extact.msa.rms.platform.fw.domain.IdProperty;
import io.extact.msa.rms.platform.fw.domain.Transformable;
import io.extact.msa.rms.platform.fw.domain.constraint.Contact;
import io.extact.msa.rms.platform.fw.domain.constraint.LoginId;
import io.extact.msa.rms.platform.fw.domain.constraint.Passowrd;
import io.extact.msa.rms.platform.fw.domain.constraint.PhoneNumber;
import io.extact.msa.rms.platform.fw.domain.constraint.RmsId;
import io.extact.msa.rms.platform.fw.domain.constraint.UserName;
import io.extact.msa.rms.platform.fw.domain.constraint.UserTypeConstraint;
import io.extact.msa.rms.platform.fw.domain.constraint.ValidationGroups.Update;
import io.extact.msa.rms.platform.fw.domain.vo.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Access(FIELD)
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter @Setter
public class UserAccount implements Transformable, IdProperty {

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RmsId(groups = Update.class)
    private Integer id;

    /** ログインID */
    @LoginId
    private String loginId;

    /** パスワード */
    @Passowrd
    private String password;

    /** ユーザ名 */
    @UserName
    private String userName;

    /** 電話番号 */
    @PhoneNumber
    private String phoneNumber;

    /** 連絡先 */
    @Contact
    private String contact;

    /** ユーザ区分 */
    @Enumerated(EnumType.STRING)
    @UserTypeConstraint
    private UserType userType;


    // ----------------------------------------------------- factory methods

    public static UserAccount ofTransient(String loginId, String password, String userName, String phoneNumber, String contact, UserType userType) {
        return of(null, loginId, password, userName, phoneNumber, contact, userType);
    }

    // ----------------------------------------------------- service methods

    @Transient
    public boolean isAdmin() {
        return this.userType == UserType.ADMIN;
    }

    public void setAdmin(boolean isAdmin) {
        this.userType = isAdmin ? UserType.ADMIN : UserType.MEMBER;
    }

    // ----------------------------------------------------- override methods

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
