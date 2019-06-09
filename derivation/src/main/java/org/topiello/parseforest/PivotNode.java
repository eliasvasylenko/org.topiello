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
package org.topiello.parseforest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO value type and record
public class PivotNode {
  private final int pivot;
  private final ParseNode adjacent;
  private final List<ParseNode> derivations;

  PivotNode(int pivot, ParseNode adjacent) {
    this.pivot = pivot;
    this.adjacent = adjacent;
    this.derivations = new ArrayList<>();
  }

  public int pivot() {
    return pivot;
  }

  public ParseNode adjacent() {
    return adjacent;
  }

  public List<ParseNode> derivations() {
    return Collections.unmodifiableList(derivations);
  }
}