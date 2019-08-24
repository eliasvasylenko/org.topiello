package org.topiello.scanner.bytes.channel;

import java.nio.channels.ByteChannel;

import org.topiello.scanner.bytes.BlockFeeder;
import org.topiello.scanner.bytes.ByteBlock;

public class ByteChannelBlockFeeder implements BlockFeeder {
  public static final int DEFAULT_BLOCK_SIZE = 512;

  private final ByteChannel byteChannel;
  private final int blockSize;

  private volatile long inputPosition;
  private ByteBlock inputBlock;

  public ByteChannelBlockFeeder(ByteChannel byteChannel, int blockSize) {
    this.byteChannel = byteChannel;
    this.blockSize = blockSize;
    this.inputBlock = new ByteBlock(this);
  }

  public ByteChannelBlockFeeder(ByteChannel byteChannel) {
    this(byteChannel, DEFAULT_BLOCK_SIZE);
  }

  /**
   * Advance up to at least the given input position before returning.
   */
  @Override
  public long feedTo(long inputPosition) {
    if (inputPosition < this.inputPosition) {
      return this.inputPosition;
    }
    synchronized (this) {
      while (inputPosition >= this.inputPosition) {
        try {
          wait();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }
    // TODO Auto-generated method stub
    return this.inputPosition;
  }

  @Override
  public ByteBlock open() {
    return inputBlock;
  }

  @Override
  public void close(ByteBlock block) {
    // TODO Auto-generated method stub

  }
}
