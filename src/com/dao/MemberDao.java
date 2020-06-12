package com.dao;

import com.beans.MemberInfo;
import com.beans.PageInfo;
import com.jdbc.DBUtil;
import com.utils.StrUtil;

import java.util.List;
/**
 * 会员-dao层类
 */
public class MemberDao {
    /**
     * 获取所有会员信息
     * @param memberNo 会员编号
     * @param beginTime 开始时间
     * @param endTime 截至时间
     * @param page 分页信息类
     * @return 会员信息列表
     */
    public List<MemberInfo> getMembers(String memberNo,String beginTime,String endTime,PageInfo page) {
        String sql = "select * from memberInfo where 1=1";
        if(!StrUtil.isNullOrEmpty(memberNo)){
            sql+=" and memberNo='"+memberNo+"'";
        }
        if(!StrUtil.isNullOrEmpty(beginTime)){
            sql+=" and registerDate>='"+beginTime+"'";
        }
        if(!StrUtil.isNullOrEmpty(endTime)){
            sql+=" and registerDate<='"+endTime+"'";
        }
        sql+= " order by registerDate desc limit "+page.getBeginRow()+", "+page.getPageSize();
        return DBUtil.getList(sql,MemberInfo.class);
    }

    /**
     * 获取会员数根据一定条件
     * @param memberNo 会员编号
     * @param beginTime 测试开始时间
     * @param endTime 测试结束时间
     * @return 会员数
     */
    public int getMemberCount(String memberNo,String beginTime,String endTime) {
        String sql = "select count(*) from memberInfo where 1=1";
        if(!StrUtil.isNullOrEmpty(memberNo)){
            sql+=" and memberNo='"+memberNo+"'";
        }
        if(!StrUtil.isNullOrEmpty(beginTime)){
            sql+=" and registerDate>='"+beginTime+"'";
        }
        if(!StrUtil.isNullOrEmpty(endTime)){
            sql+=" and registerDate<='"+endTime+"'";
        }
        long l= DBUtil.getScalar(sql);
        return (int)l;
    }

    /**
     * 删除某个会员
     * @param id 会员id
     * @return 返回1成功
     */
    public int delMember(int id) {
        String sql = "delete from memberInfo where id=?";
        return DBUtil.update(sql,id);
    }

    /**
     * 根据会员id查询会员信息
     * @param id 会员id
     * @return 会员实体类对象
     */
    public MemberInfo getMemberById(int id) {
        String sql = "select * from memberInfo where id=?";
        return DBUtil.getSingleObject(sql,MemberInfo.class,id);
    }

    /**
     * 删除多个会员
     * @param memberIds 多个会员id
     */
    public int delMembers(String[] memberIds) {
        String sql = "delete from memberInfo where id=?";
        int result = 0;
        for (String id:memberIds){
            result+=DBUtil.update(sql,id);
        }
        return result;
    }
}
