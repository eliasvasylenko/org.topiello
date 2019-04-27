package org.topiello.lexicon;

import org.topiello.text.Text;
import org.topiello.text.TextUnit;

public interface Lexeme<T, C extends TextUnit> {
  LexicalClass<T, C> lexicalClass();

  Text<C> characters();

  Token<T> evaluate();
}
