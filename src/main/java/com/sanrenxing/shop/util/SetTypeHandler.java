package com.sanrenxing.shop.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 2017/8/2.
 * @author tony
 */
public class SetTypeHandler extends BaseTypeHandler<Set<Integer>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<Integer> integers, JdbcType jdbcType) throws SQLException {
        ps.setString(i, integers.stream().map(a -> Integer.toString(a)).reduce((b, c) -> b + "," + c).orElse(""));
    }

    @Override
    public Set<Integer> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        if(StringUtil.isNullOrEmpty(s)) {
            return null;
        }
        return Stream.of(s.split(",")).filter(a -> !StringUtil.isNullOrEmpty(a)).map(Integer::parseInt).distinct().collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getNullableResult(ResultSet rs, int i) throws SQLException {
        String s = rs.getString(i);
        if(StringUtil.isNullOrEmpty(s)) {
            return null;
        }
        return Stream.of(s.split(",")).filter(a -> !StringUtil.isNullOrEmpty(a)).map(Integer::parseInt).distinct().collect(Collectors.toSet());
    }

    @Override
    public Set<Integer> getNullableResult(CallableStatement cs, int i) throws SQLException {
        String s = cs.getString(i);
        if(StringUtil.isNullOrEmpty(s)) {
            return null;
        }
        return Stream.of(s.split(",")).filter(a -> !StringUtil.isNullOrEmpty(a)).map(Integer::parseInt).distinct().collect(Collectors.toSet());

    }
}
