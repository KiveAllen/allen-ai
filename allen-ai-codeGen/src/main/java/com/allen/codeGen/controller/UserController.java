package com.allen.codeGen.controller;

import com.allen.codeGen.common.BaseResponse;
import com.allen.codeGen.common.ResultUtils;
import com.allen.codeGen.exception.ErrorCode;
import com.allen.codeGen.exception.ThrowUtils;
import com.allen.codeGen.model.dto.user.UserLoginRequest;
import com.allen.codeGen.model.dto.user.UserRegisterRequest;
import com.allen.codeGen.model.entity.User;
import com.allen.codeGen.model.vo.user.LoginUserVO;
import com.allen.codeGen.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 用户 控制层。
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册用户
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }

    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(loginUser));
    }


    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

}
