package com.example.usercenter.service.impl;
import java.util.Date;
//具体逻辑写在impl文件当中
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.common.ErrorCode;
import com.example.usercenter.exception.BusinessException;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.service.UserService;
import com.example.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
 * 写完接口之后alt+enter选择实现就会跳转到这里并且生成代码
 * @author Fenvy
 */
@Service
@Slf4j //这个注解可以在类中使用lombok提供的日志功能
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    /**
     * 加盐，非对称加密不需要对密码进行解密，所以要加盐防止解码
     */
    private static final String SALT = "Fenvy";



    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        /**
         * 1.校验
         */
        //字段非空校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){//Apache Commons lang的校验api
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"字段为空");
        }
        //用户名长度不小于4
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名长度小于4");
        }
        //密码长度不小于8
        if (userPassword.length()<8||checkPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度过短");
        }
        //账户名不包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户名包含特殊字符");
        }
        //校验密码和确认密码相同
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"校验密码和确认密码不相同");
        }
        //账户不能重复，查询数据库放最后，减少对数据库的访问提高性能
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);//对userAccount列，是否有数据等于传进来的userAccount
        long count = userMapper.selectCount(queryWrapper);
        if(count>0){//发现有重复值
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        /**
         * 2.加密
         */
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        /**
         * 3.插入数据
         */
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if(!saveResult){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "保存错误");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        /**
         * 1.校验
         */
        //字段非空校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){//Apache Commons lang的校验api
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "字段为空");
        }
        //用户名长度不小于4
        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名长度小于4");
        }
        //密码长度不小于8
        if (userPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度过短");
        }
        //账户名不包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户名包含特殊字符");
        }

        /**
         * 2.加密
         */
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());

        //账户不能重复，查询数据库放最后，减少对数据库的访问提高性能
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){//如果用户不存在
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"所请求的用户不存在");
        }

        /**
         * 3.返回信息脱敏
         */
        User safetyUser = getSafetyUser(user);

        /**
         * 4.记录用户的登录态
         */
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);//设置session值
        // todo session疑似设置失败
        return safetyUser;

    }

    /**
     *
     * @param originUser 数据库返回的用户信息
     * @return 脱敏后的用户信息
     */
    @Override
    public User getSafetyUser(User originUser){
        if(originUser == null){//服务层还要校验
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());

        return safetyUser;
    }

    /**
     * 用户注销
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        //移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }


}




