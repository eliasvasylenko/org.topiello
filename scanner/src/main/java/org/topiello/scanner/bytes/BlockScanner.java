package org.topiello.scanner.bytes;

import java.nio.ByteBuffer;
import java.util.function.Predicate;

import org.topiello.scanner.Cursor;
import org.topiello.scanner.InputPositionOutOfBoundsException;
import org.topiello.scanner.ScanWindow;
import org.topiello.scanner.Scanner;
import org.topiello.scanner.ScannerClosedException;

public class BlockScanner implements Scanner<Byte, ByteBuffer> {
  private Block block;
  private ByteBuffer buffer;

  public BlockScanner(BlockContext allocator) {
    this.block = new Block(allocator);
    this.buffer = block.getReadBuffer();
    this.block.open();
    allocator.open(block);
  }

  private BlockScanner(BlockScanner scanner) {
    this.block = scanner.block;
    this.buffer = scanner.buffer == null ? null : scanner.buffer.duplicate();
    this.block.open();
  }

  public ByteBuffer buffer() {
    return buffer;
  }

  public Block block() {
    return block;
  }

  @Override
  public void close() {
    if (block != null) {
      block.close();
      block = null;
      buffer = null;
    }
  }

  @Override
  public long inputPosition() {
    if (block == null) {
      throw new ScannerClosedException();
    } else if (buffer == null) {
      return block.startPosition();
    } else {
      return block.startPosition() + buffer.position();
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
      block.readyBuffer(buffer.position());
      buffer.limit(block.bufferLimit());

    } else if (block != null) {
      block.allocateBuffer();
      block.readyBuffer(0);
      buffer = block.getReadBuffer();

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
      block = block.next();
      buffer = block.getReadBuffer();
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
