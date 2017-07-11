/*
 * Copyright (C) 2012 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ros.internal.message;

import io.netty.buffer.ByteBuf;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPool;
import org.ros.exception.RosMessageRuntimeException;

/**
 * A pool of {@link ByteBuf}s for serializing and deserializing messages.
 * <p>
 * By contract, {@link ByteBuf}s provided by {@link #acquire()} must be
 * returned using {@link #release(ByteBuf)}.
 * 
 * @author damonkohler@google.com (Damon Kohler)
 */
public class MessageBufferPool {

  private final ObjectPool<ByteBuf> pool;

  public MessageBufferPool() {
    pool = new StackObjectPool<ByteBuf>(new PoolableObjectFactory<ByteBuf>() {
      @Override
      public ByteBuf makeObject() throws Exception {
        return ByteBufs.dynamicBuffer();
      }

      @Override
      public void destroyObject(ByteBuf byteBuf) throws Exception {
      }

      @Override
      public boolean validateObject(ByteBuf byteBuf) {
        return true;
      }

      @Override
      public void activateObject(ByteBuf byteBuf) throws Exception {
      }

      @Override
      public void passivateObject(ByteBuf byteBuf) throws Exception {
        byteBuf.clear();
      }
    });
  }

  /**
   * Acquired {@link ByteBuf}s must be returned using
   * {@link #release(ByteBuf)}.
   * 
   * @return an unused {@link ByteBuf}
   */
  public ByteBuf acquire() {
    try {
      // todo
//      return pool.borrowObject();
      return ByteBufs.dynamicBuffer();
    } catch (Exception e) {
      throw new RosMessageRuntimeException(e);
    }
  }

  /**
   * Release a previously acquired {@link ByteBuf}.
   * 
   * @param byteBuf
   *          the {@link ByteBuf} to release
   */
  public void release(ByteBuf byteBuf) {
    try {
      // todo
//      byteBuf.release();
//      pool.returnObject(byteBuf);
    } catch (Exception e) {
      throw new RosMessageRuntimeException(e);
    }
  }
}
