package com.zhiwen95.miaoproject.entity;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransformerConfig {
  private List<String> sourceDirectories;
  private String targetDirectory;
  private List<TransformerRule> transformerRules;
}
