/*
 * Topiello Grammar - The grammar API
 * Copyright © 2018 Strange Skies (elias@vasylenko.uk)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.topiello.grammar;

import java.util.stream.Stream;

import org.topiello.text.TextUnit;

/* TODO
 * Constraints on solution to build grammars:
 * 
 * - Products must only relate to a single grammar.
 * 
 * - Grammar must be immutable.
 * 
 * - To resolve products against grammar, we may want to build an index for the
 * grammar. This index should only be built once when the grammar is done.
 * 
 * - Grammars can depend on other grammars
 * 
 * - Dependencies between grammars cannot be circular.
 * 
 * Have a grammar builder? Or have a grammar be a builder for an AST? Grammar
 * subtype controls types of rules which can be added, need special products to
 * call out to other existing grammars.
 * 
 * @author Elias N Vasylenko
 */
public interface Grammar<T extends Product, C extends TextUnit> {
  Stream<? extends Rule<T>> getRules();

  Stream<? extends Rule<T>> getMatchingRules(T product);

  Stream<? extends Terminal<C>> getTerminals();

  Stream<? extends Terminal<C>> getMatchingTerminals(T product);
}