package com.main.test.common.rabbitmq.convert;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;

public class Jackson2JavaTypeMapper extends DefaultJackson2JavaTypeMapper {

    public static final String DEFAULT_MSG_T_CLS_NAME_FIELD_NAME = "__MsgTemplateClassName__";

    @Override
    public JavaType toJavaType(MessageProperties properties) {
        JavaType javaType = super.toJavaType(properties);
        if (properties.getHeaders().get(DEFAULT_MSG_T_CLS_NAME_FIELD_NAME) != null) {
            try {
                String tClsName = properties.getHeaders().get(DEFAULT_MSG_T_CLS_NAME_FIELD_NAME).toString();
                return TypeFactory.defaultInstance().constructParametricType(javaType.getRawClass(), Class.forName(tClsName));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return javaType;
    }
}
