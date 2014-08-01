package com.lx.agent.utility;

import com.lx.agent.view.UserView;
import com.lx.agent.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Ignore
public class DataConverterGeneratorTest {

    @Test
    public void test() {
        new Generator().fromDomainClass(User.class).toViewClass(UserView.class).generate();
    }

    class Generator {
        private Class<?> domainClass;
        private Class<?> viewClass;

        public Generator fromDomainClass(Class<?> domainClass) {
            this.domainClass = domainClass;
            return this;
        }

        public Generator toViewClass(Class<?> viewClass) {
            this.viewClass = viewClass;
            return this;
        }

        public void generate() {

            List<String> viewClassFields = new ArrayList<>();
            List<String> domainClassFields = new ArrayList<>();

            for (Field field : this.viewClass.getDeclaredFields())
                viewClassFields.add(field.getName());
            for (Field field : this.domainClass.getDeclaredFields())
                domainClassFields.add(field.getName());


            List<String> inconsistentFields = new ArrayList<String>();

            for (String field : viewClassFields) {
                if (domainClassFields.contains(field))
                    new SetterBuilder().fromClass(domainClass).toClass(viewClass).build(field);
                else
                    inconsistentFields.add(field);
            }
            if (!inconsistentFields.isEmpty())
                System.out.println("inconsistentFields:" + Arrays.toString(inconsistentFields.toArray()));
        }

    }

    class SetterBuilder {
        private Class<?> fromClass;
        private Class<?> toClass;

        public SetterBuilder fromClass(Class<?> fromClass) {
            this.fromClass = fromClass;
            return this;
        }

        public SetterBuilder toClass(Class<?> toClass) {
            this.toClass = toClass;
            return this;
        }

        void build(String fieldName) {
            StringBuilder builder = new StringBuilder();
            builder.append(StringUtils.uncapitalize(toClass.getSimpleName()))
                    .append(".set")
                    .append(StringUtils.capitalize(fieldName))
                    .append("(")
                    .append(StringUtils.uncapitalize(fromClass.getSimpleName()))
                    .append(".get")
                    .append(StringUtils.capitalize(fieldName)).append("()").append(");");
            System.out.println(builder.toString());
        }
    }
}
