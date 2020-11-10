package com.store.oauth.config;

import com.store.oauth.util.UserJwt;
import com.store.user.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.transform.Result;


/**
 * 查询用户信息 及权限的 自定义实现类 可连接数据库 查询数据库中的用户信息 及授权信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private UserFeign userFeign;

    /****
     * 自定义授权认证
     * @param username  用户登录时输入的用户名
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，
        //开始认证client_id和client_secret
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                //秘钥
                String clientSecret = clientDetails.getClientSecret();
                //静态方式
                //return new User(username,new BCryptPasswordEncoder().encode(clientSecret), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
                //数据库查找方式, 创建用户对象
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }

        //如果用户没有输入用户名返回null
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        //根据用户名查询用户信息
        //此处写死 和 从数据库中查询只能使用一个
//        String pwd = new BCryptPasswordEncoder().encode("itheima");
        //从数据库中查询  --   通过查询的对象的密码进行匹配
        final com.store.entity.Result byId = userFeign.findByIdOne(username);
        final com.store.user.pojo.User userData = (com.store.user.pojo.User) byId.getData();
        if (userData == null) {
            throw new RuntimeException("根据用户名无法查询到对应的用户对象! 用户名是:" + username);
        }
        //创建权限字符串
        String permissions = "goods_list,seckill_list";
        //创建用户JWT对象
        UserJwt userDetails = new UserJwt(username, userData.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
        //返回用户JWT对象
        return userDetails;
    }
}
