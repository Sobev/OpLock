package com.sobev.OpLock.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Storage {
  Integer id;
  Integer stock;
  Integer data_version;
}
