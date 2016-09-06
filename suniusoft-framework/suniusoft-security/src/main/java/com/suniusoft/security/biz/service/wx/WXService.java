package com.suniusoft.security.biz.service.wx;

import com.suniusoft.common.adapter.WXAdapter;
import com.suniusoft.common.adapter.vo.AccessTokenVO;
import com.suniusoft.common.adapter.vo.WXUserInfoVO;
import com.suniusoft.common.adapter.vo.WxUserOuathVO;
import com.suniusoft.common.exception.ServiceException;
import com.suniusoft.common.utils.BeanCopierUtils;
import com.suniusoft.security.vo.UserVO;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 微信相关接口服务
 *  @author zoujian  zoujian@shufensoft.com
 *  @date 2015/4/12 21:01  
 *
 * @modifier litu  litu@shufensoft.com 2015/5/22 14:05
 */
@Service(value = "wXService")
public class WXService {

    private static WXAdapter wxAdapter = new WXAdapter();

    private static final Logger logger = Logger.getLogger(WXService.class);


    /**
     * 获取accessToken
     *
     * @return
     * @modifier litu  litu@shufensoft.com 2015/5/22 14:05
     */
    public String getAccessToken(String appId, String secret) {


        AccessTokenVO accessToken = wxAdapter.getAccessToken(appId, secret);

        return accessToken.getAccessToken();
    }


    /**
     * 根据openId查询用户信息
     *
     * @param openId 用户公众号中的唯一ID
     * @return 用户信息
     */
    public UserVO findUserByOpenIdFromWx(String appId, String secret,String openId) {

        try {

            WXUserInfoVO userInfoVO = wxAdapter.getUserInfo(appId, secret, openId);
            return wxUserToCrmUser(userInfoVO);

        } catch (Exception e) {
            logger.error("WXService.findUserByOpenIdFromWx error", e);
            throw new ServiceException("WXService.findUserByOpenIdFromWx", e);
        }

    }

    /**
     * 根据用户授权返回的信息获取用户的详细信息
     *
     * @param openId
     * @param accessToken
     * @return 微信用户信息
     * @author zoujian  zoujian@suniusoft.com 2015/7/14 14:05
     */
    public UserVO getOuathUserInfo(String openId, String accessToken) {

        WXUserInfoVO wxUserInfoVO = null;

        try {
            wxUserInfoVO = WXAdapter.getOuathUserInfo(openId, accessToken);
        } catch (Exception e) {
            logger.error("WXService.getOuathUserInfo error", e);
        }

        return wxUserToCrmUser(wxUserInfoVO);

    }


    /**
     * 根据授权码获取opendId
     *
     * @param code 授权码
     * @return openid
     */
    public WxUserOuathVO getWxUserOuath(String wxAppId , String wxAppSecret , String code) {


        WxUserOuathVO wxUserOuathVO = WXAdapter.ouath(wxAppId , wxAppSecret , code);

        return wxUserOuathVO;
    }

    /**
     * 微信用户对象转成内部用户对象
     *
     * @param userInfoVO
     * @return
     */
    public UserVO wxUserToCrmUser(WXUserInfoVO userInfoVO) {

        UserVO user;

        try {
            /**
             * 相同字段名copy
             */
            user = (UserVO) BeanCopierUtils.copyProperties(userInfoVO, UserVO.class);

        } catch (Exception e) {
            logger.error("userInfoVO bean copy to user error", e);
            throw new ServiceException("WXService.wxUserToCrmUser",
                    "userInfoVO bean copy to user error", e);
        }

        /**
         * 不同字段名copy
         */
        user.setWxSubscribeTime(userInfoVO.getSubscribe_time());
        user.setWxSubscribe(userInfoVO.getSubscribe());
        user.setWxHeadimgurl(userInfoVO.getHeadimgurl());
        user.setWxOpenid(userInfoVO.getOpenid());
        user.setUserNick(userInfoVO.getNickname());

        return user;
    }




}
