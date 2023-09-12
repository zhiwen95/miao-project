package com.zhiwen95.miaoproject.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransformerRule {
  private String id;
  private String name;
  private String description;
  private String condition;
  private String action;
}
