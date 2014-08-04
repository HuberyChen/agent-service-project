package com.lx.agent.utility;

import com.core.crypto.EncryptionUtils;
import com.core.utils.ClasspathResource;
import com.core.utils.EncodingUtils;
import com.lx.agent.SpringTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

@Ignore
public class EncryptionUtilsTest extends SpringTest {

    @Inject
    Environment env;

    @Test
    public void test() {
        System.out
                .println(EncryptionUtils
                        .decrypt(
                                "HK5No1wJ9ZHSYt5C0AStsk9jc0k8gXFlBahSWcrg5tIhJg+kFhOzJqeX6QtnCgqfK6oM//5+hcAJ+3EKY3DYhIPBTOeTvwW/iuaQXCle69UT+omvoi17EnO0hkg55WcmRqxaqAb1PcZO6gQ4dUFVeoB0DaFAOh0479kLGJdo9FymCcFUUqxwE+/hycmx4UVyMQFoHEFytvtmremdjMmMH/Rj31mAiRlVDmwAifH5L8vuq4vL84boXCKOzDIrDRkJtA37WWKocyaMGHjSJENnDtRD74v7CFFbObp+cA6ZPt0SciBS4Lr3fOxUERk9ABli3vTqlxivZyZ7KxM1EdZwQg==",
                                new ClasspathResource("private.key")));
        // System.out.println(EncryptionUtils.encrypt("giftco", new
        // ClasspathResource("public.key")));
        System.out.println(EncryptionUtils.encrypt("0000", new ClasspathResource("public.key")));
    }

    @Test
    public void encodingTest() {
        System.out
                .println(EncodingUtils
                        .decodeBase64("HK5No1wJ9ZHSYt5C0AStsk9jc0k8gXFlBahSWcrg5tIhJg+kFhOzJqeX6QtnCgqfK6oM//5+hcAJ+3EKY3DYhIPBTOeTvwW/iuaQXCle69UT+omvoi17EnO0hkg55WcmRqxaqAb1PcZO6gQ4dUFVeoB0DaFAOh0479kLGJdo9FymCcFUUqxwE+/hycmx4UVyMQFoHEFytvtmremdjMmMH/Rj31mAiRlVDmwAifH5L8vuq4vL84boXCKOzDIrDRkJtA37WWKocyaMGHjSJENnDtRD74v7CFFbObp+cA6ZPt0SciBS4Lr3fOxUERk9ABli3vTqlxivZyZ7KxM1EdZwQg=="));
    }
}