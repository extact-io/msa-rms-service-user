package io.extact.msa.rms.user.webapi;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.extact.msa.rms.platform.fw.domain.constraint.LoginId;
import io.extact.msa.rms.platform.fw.domain.constraint.Passowrd;
import io.extact.msa.rms.platform.fw.domain.constraint.RmsId;
import io.extact.msa.rms.platform.fw.exception.BusinessFlowException;
import io.extact.msa.rms.user.webapi.dto.AddUserAccountEventDto;
import io.extact.msa.rms.user.webapi.dto.UserAccountResourceDto;

public interface UserAccountResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Admin")
    @Operation(operationId = "getAll", summary = "ユーザの全件を取得する", description = "登録されているすべてのユーザを取得する")
    @SecurityRequirement(name = "RmsJwtAuth")
    @APIResponse(responseCode = "200", description = "検索結果", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = UserAccountResourceDto.class)))
    List<UserAccountResourceDto> getAll();

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Member")
    @Operation(operationId = "get", summary = "ユーザを取得する", description = "指定されたユーザを取得する。なお、該当なしはnullに相当する204(NoContent)を返す")
    @SecurityRequirement(name = "RmsJwtAuth")
    @Parameter(name = "userId", description = "ユーザID", in = ParameterIn.PATH, required = true)
    @APIResponse(responseCode = "200", description = "レンタル品", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAccountResourceDto.class)))
    @APIResponse(responseCode = "204", ref = "#/components/responses/NoContent")
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    UserAccountResourceDto get(@RmsId @PathParam("userId") Integer userAccountId);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Admin")
    @Operation(operationId = "add", summary = "ユーザを登録する", description = "ログインIDが既に使われている場合は409を返す")
    @SecurityRequirement(name = "RmsJwtAuth")
    @Parameter(name = "dto", description = "登録内容", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddUserAccountEventDto.class)))
    @APIResponse(responseCode = "200", description = "登録成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAccountResourceDto.class)))
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "409", ref = "#/components/responses/DataDupricate")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    UserAccountResourceDto add(@Valid AddUserAccountEventDto dto) throws BusinessFlowException;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Admin")
    @Operation(operationId = "update", summary = "ユーザを更新する", description = "依頼されたユーザを更新する")
    @SecurityRequirement(name = "RmsJwtAuth")
    @Parameter(name = "dto", description = "更新内容", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAccountResourceDto.class)))
    @APIResponse(responseCode = "200", description = "登録成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAccountResourceDto.class)))
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "404", ref = "#/components/responses/UnknownData")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    UserAccountResourceDto update(@Valid UserAccountResourceDto dto);

    @DELETE
    @Path("/{userId}")
    @Tag(name = "Admin")
    @Operation(operationId = "deleteUserAccount", summary = "ユーザを削除する", description = "削除対象のユーザを参照する予約が存在する場合は削除は行わずエラーにする")
    @SecurityRequirement(name = "RmsJwtAuth")
    @Parameter(name = "userAccountId", description = "ユーザID", in = ParameterIn.PATH, required = true)
    @APIResponse(responseCode = "200", description = "登録成功")
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "404", ref = "#/components/responses/UnknownData")
    @APIResponse(responseCode = "409", ref = "#/components/responses/DataRefered")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    void delete(@RmsId @PathParam("userId") Integer userAccountId) throws BusinessFlowException;

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Authenticate")
    @Operation(operationId = "authenticateForTest", summary = "ユーザ認証を行う（curlのテスト用）", description = "ログイン名とパスワードに一致するユーザを取得する")
    @Parameter(name = "loginId", description = "ログインId", required = true, schema = @Schema(implementation = String.class, minLength = 5, maxLength = 10))
    @Parameter(name = "password", description = "パスワード", required = true, schema = @Schema(implementation = String.class, minLength = 5, maxLength = 10))
    @APIResponse(responseCode = "200", description = "認証成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAccountResourceDto.class)))
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "404", ref = "#/components/responses/NotFound")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    UserAccountResourceDto authenticate(
            @LoginId @QueryParam("loginId") String loginId,
            @Passowrd @QueryParam("password") String password);

    @GET
    @Path("/exists/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "exists", summary = "指定されたユーザが存在するかを返す")
    @SecurityRequirement(name = "RmsJwtAuth")
    @Parameter(name = "userId", description = "レンタル品ID", in = ParameterIn.PATH, required = true)
    @APIResponse(responseCode = "200", description = "ある場合はtrueを返す", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.BOOLEAN, implementation = Boolean.class)))
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    boolean exists(@RmsId @PathParam("userId") Integer userId);
}
