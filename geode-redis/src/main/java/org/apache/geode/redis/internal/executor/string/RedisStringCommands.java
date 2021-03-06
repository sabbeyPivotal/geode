/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.redis.internal.executor.string;

import java.math.BigDecimal;
import java.util.List;

import org.apache.geode.redis.internal.data.ByteArrayWrapper;

public interface RedisStringCommands {
  long append(ByteArrayWrapper key, ByteArrayWrapper valueToAppend);

  ByteArrayWrapper get(ByteArrayWrapper key);

  boolean set(ByteArrayWrapper key, ByteArrayWrapper value, SetOptions options);

  long incr(ByteArrayWrapper key);

  long decr(ByteArrayWrapper key);

  ByteArrayWrapper getset(ByteArrayWrapper key, ByteArrayWrapper value);

  long incrby(ByteArrayWrapper key, long increment);

  long decrby(ByteArrayWrapper key, long decrement);

  ByteArrayWrapper getrange(ByteArrayWrapper key, long start, long end);

  long bitcount(ByteArrayWrapper key, int start, int end);

  long bitcount(ByteArrayWrapper key);

  int strlen(ByteArrayWrapper key);

  int getbit(ByteArrayWrapper key, int offset);

  int setbit(ByteArrayWrapper key, long offset, int value);

  BigDecimal incrbyfloat(ByteArrayWrapper key, BigDecimal increment);

  int bitop(String operation, ByteArrayWrapper destKey, List<ByteArrayWrapper> sources);

  int bitpos(ByteArrayWrapper key, int bit, int start, Integer end);

  int setrange(ByteArrayWrapper key, int offset, byte[] value);

  ByteArrayWrapper mget(ByteArrayWrapper key);
}
