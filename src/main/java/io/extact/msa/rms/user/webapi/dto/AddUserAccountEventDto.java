package io.extact.msa.rms.user.webapi.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.extact.msa.rms.platform.fw.domain.constraint.Contact;
import io.extact.msa.rms.platform.fw.domain.constraint.LoginId;
import io.extact.msa.rms.platform.fw.domain.constraint.Passowrd;
import io.extact.msa.rms.platform.fw.domain.constraint.PhoneNumber;
import io.extact.msa.rms.platform.fw.domain.constraint.UserName;
import io.extact.msa.rms.platform.fw.domain.constraint.UserTypeConstraint;
import io.extact.msa.rms.platform.fw.domain.vo.UserType;
import io.extact.msa.rms.user.domain.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "ユーザ登録イベントDTO")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter @Setter
public class AddUserAccountEventDto {

    @Schema(required = true)
    @LoginId
    private String loginId;

    @Schema(required = true)
    @Passowrd
    private String password;

    @Schema(required = true)
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

    public UserAccount toEntity() {
        return UserAccount.ofTransient(loginId, password, userName, phoneNumber, contact, userType);
    }
}
