/*
 * Topiello AST - The parser AST API
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
package org.topiello.ast.interpreted;

import org.topiello.ast.ExecutableNode;
import org.topiello.ast.RuleNode;
import org.topiello.grammar.Product;
import org.topiello.grammar.Rule;
import org.topiello.grammar.Variable;

public class InterpretedSpecializableRule implements RuleNode.Specializable {
  private final Rule<?> rule;

  public InterpretedSpecializableRule(Rule<?> rule) {
    this.rule = rule;
  }

  @Override
  public Variable variable() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ExecutableNode predicted() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Specialized specialize(Product derivedFrom) {
    // TODO Auto-generated method stub
    return null;
  }
}