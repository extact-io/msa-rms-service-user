package io.extact.msa.rms.user.webapi;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

import io.extact.msa.rms.platform.core.validate.ValidateParam;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException.CauseType;
import io.extact.msa.rms.user.service.UserAccountService;
import io.extact.msa.rms.user.webapi.dto.AddUserAccountEventDto;
import io.extact.msa.rms.user.webapi.dto.UserAccountResourceDto;

@Path("users")
@ApplicationScoped
@ValidateParam
public class UserAccountResourceImpl implements UserAccountResource {

    private UserAccountService userService;

    @Inject
    public UserAccountResourceImpl(UserAccountService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserAccountResourceDto> getAll() {
        return userService.findAll().stream()
                .map(UserAccountResourceDto::from)
                .toList();
    }

    @Override
    public UserAccountResourceDto get(Integer userId) {
        return userService.get(userId)
                .map(UserAccountResourceDto::from)
                .orElse(null);
    }

    @Override
    public UserAccountResourceDto add(AddUserAccountEventDto dto) throws BusinessFlowException {
        return userService.add(dto.toEntity()).transform(UserAccountResourceDto::from);
    }

    @Override
    public UserAccountResourceDto update(UserAccountResourceDto dto) {
        return userService.update(dto.toEntity()).transform(UserAccountResourceDto::from);
    }

    @Override
    public void delete(Integer userId) throws BusinessFlowException {
        userService.delete(userId);
    }

    @Override
    public UserAccountResourceDto authenticate(String loginId, String password) {
        return userService.findByLoginIdAndPasswod(loginId, password)
                .orElseThrow(() -> new BusinessFlowException("loginId or password is different", CauseType.NOT_FOUND))
                .transform(UserAccountResourceDto::from);
    }

    @Override
    public boolean exists(Integer userId) {
        return userService.get(userId).isPresent();
    }
}
