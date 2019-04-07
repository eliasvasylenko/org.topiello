package org.topiello.semantics;

import java.util.Collection;

import org.topiello.symbol.LexicalItem;

public interface EncodeState {
  <U> U getContext(Class<U> type);

  <U> EncodeState put(Sign<U> signifier, U information);

  <U> EncodeState putAll(Sign<U> signifier, @SuppressWarnings("unchecked") U... information);

  <U> EncodeState putAll(Sign<U> signifier, Collection<? extends U> information);

  <U extends LexicalItem> EncodeState putInstantiation(Unknown<U> pattern, U instantiation);
}