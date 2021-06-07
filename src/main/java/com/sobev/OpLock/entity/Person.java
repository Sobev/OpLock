package com.sobev.OpLock.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Person {
  private String id;
  private String name;
  private Integer gender;
  private String addr;
}
