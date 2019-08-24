package org.topiello.scanner.bytes;

import java.nio.ByteBuffer;
import java.util.function.Predicate;

import org.topiello.scanner.Cursor;
import org.topiello.scanner.InputPositionOutOfBoundsException;
import org.topiello.scanner.ScanWindow;
import org.topiello.scanner.Scanner;
import org.topiello.scanner.ScannerClosedException;

public class BlockScanner implements Scanner<Byte, ByteBuffer> {
  private ByteBlock inputBlock;
  private ByteBuffer buffer;

  public BlockScanner(BlockFeeder feeder) {
    this(feeder.open());
  }

  private BlockScanner(ByteBlock block) {
    this(block, block.getReadBuffer());
  }

  private BlockScanner(BlockScanner scanner) {
    this(scanner.inputBlock, scanner.buffer == null ? null : scanner.buffer.duplicate());
  }

  private BlockScanner(ByteBlock inputBlock, ByteBuffer buffer) {
    this.inputBlock = inputBlock;
    this.buffer = buffer;
    inputBlock.open();
  }

  @Override
  public void close() {
    if (inputBlock != null) {
      inputBlock.close();
      inputBlock = null;
      buffer = null;
    }
  }

  @Override
  public long inputPosition() {
    if (inputBlock == null) {
      throw new ScannerClosedException();
    } else if (buffer == null) {
      return inputBlock.startPosition();
    } else {
      return inputBlock.startPosition() + buffer.position();
    }
  }

  @Override
  public Cursor<Byte> peek() {
    if (prepareRead()) {
      return new Cursor<>(buffer.get(buffer.position()));

    } else {
      return new Cursor<>();
    }
  }

  @Override
  public Cursor<Byte> advance() {
    if (prepareRead()) {
      var cursor = new Cursor<>(buffer.get());
      completeRead();
      return cursor;

    } else {
      return new Cursor<>();
    }
  }

  @Override
  public Cursor<Byte> advanceWhile(Predicate<Byte> condition) {
    byte next = 0;
    while (prepareRead()) {
      long run = buffer.remaining();
      for (long i = 0; i < run; i++) {
        next = buffer.get();
        if (!condition.test(next)) {
          int bufferPosition = buffer.position() - 1;
          buffer.position(bufferPosition);
          completeRead();
          return new Cursor<>(next);
        }
      }
      completeRead();
    }
    return new Cursor<>();
  }

  @Override
  public Cursor<Byte> advanceTo(long position) {
    var delta = (int) (position - inputPosition());
    if (delta < 0) {
      throw new InputPositionOutOfBoundsException(position);
    }

    while (prepareRead()) {
      delta = delta - buffer.remaining();
      if (delta < 0) {
        buffer.position(buffer.limit() + delta);
        completeRead();
        return peek();
      }
      buffer.position(buffer.limit());
      completeRead();
    }

    return new Cursor<>();
  }

  private boolean prepareRead() {
    if (buffer != null) {
      if (buffer.hasRemaining()) {
        return true;
      }
      inputBlock.readyBuffer(buffer.position());
      buffer.limit(inputBlock.bufferLimit());

    } else if (inputBlock != null) {
      inputBlock.allocateBuffer();
      inputBlock.readyBuffer(0);
      buffer = inputBlock.getReadBuffer();

    } else {
      return false;
    }

    if (buffer.hasRemaining()) {
      return true;
    } else {
      close();
      return false;
    }
  }

  private void completeRead() {
    int bufferPosition = buffer.position();
    if (bufferPosition == buffer.capacity()) {
      inputBlock = inputBlock.next();
      buffer = inputBlock.getReadBuffer();
    }
  }

  @Override
  public Scanner<Byte, ByteBuffer> branch() {
    return new BlockScanner(this);
  }

  @Override
  public ScanWindow<Byte, ByteBuffer> openWindow() {
    return new BlockScanWindow(this);
  }
}
