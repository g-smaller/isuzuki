package com.isuzuki.oss.api;

public interface ObjectKeyGenerator {

    String generateObjectKey(ObjectKeyGeneratorArgs args);

    ObjectKeyGenerator DEFAULT = new DefaultObjectKeyGenerator();
}
