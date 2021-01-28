package com.mongo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@ReadingConverter
public class EnumWithIdConverterFactory implements ConverterFactory<Integer, Enum<?>> {
    @Override
    public <T extends Enum<?>> Converter<Integer, T> getConverter(Class<T> targetType) {
        return WithId.class.isAssignableFrom(targetType)
                ? new IntegerToEnumWithId(targetType)
                : new IntegerToEnum(targetType);
    }

    private static class IntegerToEnumWithId<T extends Enum> implements Converter<Integer, T> {

        private final Class<T> enumType;

        public IntegerToEnumWithId(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(Integer source) {
            Object target = null;
            for(Object e : EnumSet.allOf(enumType)){
                if(e instanceof WithId && ((WithId)e).getId() == source){
                    target = e;
                    break;
                }
            }
            return (T) target;
        }
    }

    private static class IntegerToEnum<T extends Enum> implements Converter<Integer, T> {

        private final Class<T> enumType;

        public IntegerToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(Integer source) {
            return this.enumType.getEnumConstants()[source];
        }
    }

}
