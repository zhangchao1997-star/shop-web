package com.commons;

/**
 * 枚举工具类-用来表示可能的状态
 */
public class Constant {

    public enum EnumOrderState{
        未付款{
            @Override
            public String getValue() {
                return "未付款";
            }
        },
        已付款{
            @Override
            public String getValue() {
                return "已付款";
            }
        },
        已发货{
            @Override
            public String getValue() {
                return "已发货";
            }
        };

        public abstract String getValue();
    }

    public enum  EnumAdminState
    {
        正常{
            @Override
            public String getValue() {
                return "1";
            }
        },
        锁定{
            @Override
            public String getValue() {
                return "0";
            }
        },
        删除{
            @Override
            public String getValue() {
                return "2";
            }
        };

        public abstract String getValue();
    }
}
