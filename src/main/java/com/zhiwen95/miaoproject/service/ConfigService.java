package com.zhiwen95.miaoproject.service;

import com.zhiwen95.miaoproject.entity.TransformerRule;
import com.zhiwen95.miaoproject.entity.TransformerConfig;
import com.zhiwen95.miaoproject.util.YamlUtils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
  private static final Lock TRANSFORMER_RULE_STORE_LOCK = new ReentrantLock();

  public TransformerConfig getTransformerConfig() {
    return deserializeTransformerRules(readTransformerRuleStore());
  }

  public void saveTransformerConfig(TransformerConfig transformerConfig) {
    TRANSFORMER_RULE_STORE_LOCK.lock();
    try {
      final String content = serializeTransformerRules(transformerConfig);
      writeTransformerRuleStore(content);
    } finally {
      TRANSFORMER_RULE_STORE_LOCK.unlock();
    }
  }

  private File getTransformerRuleStore() {
    return new File("config/transformer-rule.yaml");
  }

  @SneakyThrows
  private String readTransformerRuleStore() {
    String content = "";
    final File file = getTransformerRuleStore();
    if (!file.exists()) {
      return content;
    }
    return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
  }

  @SneakyThrows
  private void writeTransformerRuleStore(String content) {
    final File file = getTransformerRuleStore();
    if (!file.exists()) {
      Validate.isTrue(file.createNewFile(), "Failed to create file: %s", file.getAbsolutePath());
    }
    FileUtils.write(file, content, StandardCharsets.UTF_8);
  }

  @SneakyThrows
  private TransformerConfig deserializeTransformerRules(String content) {
    return YamlUtils.readValue(content, TransformerConfig.class);
  }

  @SneakyThrows
  private String serializeTransformerRules(TransformerConfig transformerConfig) {
    return YamlUtils.writeValueAsString(transformerConfig);
  }
}
