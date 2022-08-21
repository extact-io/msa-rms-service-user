package io.extact.msa.rms.user.webapi.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.extact.msa.rms.platform.fw.domain.constraint.Contact;
import io.extact.msa.rms.platform.fw.domain.constraint.LoginId;
import io.extact.msa.rms.platform.fw.domain.constraint.Passowrd;
import io.extact.msa.rms.platform.fw.domain.constraint.PhoneNumber;
import io.extact.msa.rms.platform.fw.domain.constraint.RmsId;
import io.extact.msa.rms.platform.fw.domain.constraint.UserName;
import io.extact.msa.rms.platform.fw.domain.constraint.UserTypeConstraint;
import io.extact.msa.rms.platform.fw.domain.vo.UserType;
import io.extact.msa.rms.user.domain.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "ユーザDTO")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter @Setter
public class UserAccountResourceDto {

    @Schema(required = true)
    @RmsId
    private Integer id;

    @Schema(required = true)
    @LoginId
    private String loginId;

    @Schema(required = true)
    @Passowrd
    private String password;

    @Schema(required = false)
    @UserName
    private String userName;

    @Schema(required = false)
    @PhoneNumber
    private String phoneNumber;

    @Schema(required = false)
    @Contact
    private String contact;

    @Schema(required = true)
    @UserTypeConstraint
    private UserType userType;

    public static UserAccountResourceDto from(UserAccount entity) {
        if (entity == null) {
            return null;
        }
        var dto = new UserAccountResourceDto();
        dto.setId(entity.getId());
        dto.setLoginId(entity.getLoginId());
        dto.setPassword(entity.getPassword());
        dto.setUserName(entity.getUserName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setContact(entity.getContact());
        dto.setUserType(entity.getUserType().name());
        return dto;
    }

    public UserAccount toEntity() {
        return UserAccount.of(id, loginId, password, userName, phoneNumber, contact, userType);
    }

    // original getter
    public String getUserType() {
        return userType.name();
    }

    // original setter
    public void setUserType(String userType) {
        this.userType = UserType.valueOf(userType);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
