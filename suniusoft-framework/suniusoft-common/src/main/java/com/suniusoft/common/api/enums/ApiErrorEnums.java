package com.suniusoft.common.api.enums;

import org.apache.commons.lang.StringUtils;

/**
 *  @ProjectName: sf-crm  
 *  @Description: api返回code
 *  @author litu  litu@shufensoft.com
 *  @date 2015/4/14 17:29  
 */
public enum ApiErrorEnums {

    /**
     * 系统内部定义的成功码
     */
    SUCCESS("0","成功"),

    /**
     * 系统内部定义的错误码
     */
    ERROR("-1","失败");

    // 成员变量
    private String code;
    private String value;

    private ApiErrorEnums(String code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * 根据业务码获得业务类型
     *
     * @param code 业务码
     * @return 业务类型
     */
    public static ApiErrorEnums getEnumByCode(String code) {
        ApiErrorEnums[] values = ApiErrorEnums.values();
        for (ApiErrorEnums operate : values) {
            if (StringUtils.equals(operate.getCode(), code)) {
                return operate;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
