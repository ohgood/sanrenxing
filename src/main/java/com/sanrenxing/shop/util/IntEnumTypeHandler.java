package com.sanrenxing.shop.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 2016/12/19
 *
 * @author annpeter.it@gmail.com
 */
public class IntEnumTypeHandler<E extends Enum<E> & IntEnum<E>> extends BaseTypeHandler<IntEnum> {
    private Class<IntEnum> type;

    public IntEnumTypeHandler(Class<IntEnum> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.type = type;
    }

    @Override
    public IntEnum getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return IntEnum.convert(type, rs.getInt(columnName));
    }

    @Override
    public IntEnum getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return IntEnum.convert(type, rs.getInt(columnIndex));
    }

    @Override
    public IntEnum getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return IntEnum.convert(type, cs.getInt(columnIndex));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    IntEnum enumObj, JdbcType jdbcType) throws SQLException {
        // baseTypeHandler已经帮我们做了parameter的null判断
        ps.setInt(i, enumObj.getIntValue());
    }
}