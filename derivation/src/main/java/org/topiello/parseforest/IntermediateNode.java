/*
 * Topiello Derivation - API for describing parse forests, deparse forests, and derivation trees
 *
 * Copyright © 2018 Strange Skies (elias@vasylenko.uk)
 *     __   _______  ____           _       __     _      __       __
 *   ,`_ `,|__   __||  _ `.        / \     |  \   | |  ,-`__`¬  ,-`__`¬
 *  ( (_`-'   | |   | | ) |       / . \    | . \  | | / .`  `' / .`  `'
 *   `._ `.   | |   | |-. L      / / \ \   | |\ \ | || |    _ | '-~.
 *  _   `. \  | |   | |  `.`.   / /   \ \  | | \ \| || |   | || +~-'
 * \ \__.' /  | |   | |    \ \ / /     \ \ | |  \ ` | \ `._' | \ `.__,.
 *  `.__.-`   |_|   |_|    |_|/_/       \_\|_|   \__|  `-.__.J  `-.__.J
 *                  __    _         _      __      __
 *                ,`_ `, | |  _    | |  ,-`__`¬  ,`_ `,
 *               ( (_`-' | | ) |   | | / .`  `' ( (_`-'
 *                `._ `. | L-' L   | || '-~.     `._ `.
 *               _   `. \| ,.-^.`. | || +~-'    _   `. \
 *              \ \__.' /| |    \ \| | \ `.__,.\ \__.' /
 *               `.__.-` |_|    |_||_|  `-.__.J `.__.-`
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.topiello.parseforest;

import java.util.TreeMap;

import org.topiello.grammar.Rule;

// TODO on-demand instantiation of ParseNodes, to be a value type
public class IntermediateNode {
  private final Rule<?> rule;
  private final int dotPosition;
  private final int leftExtent;
  private final int rightExtent;
  private final TreeMap<Integer, PivotNode> pivots;

  IntermediateNode(
      Rule<?> rule,
      int dotPosition,
      int leftExtent,
      int rightExtent,
      ParseNode parentNode,
      ParseNode parseNode) {
    this.rule = rule;
    this.dotPosition = dotPosition;
    this.leftExtent = leftExtent;
    this.rightExtent = rightExtent;
    this.leftExtent = leftExtent;
    this.rightExtent = rightExtent;
    this.pivots = new TreeMap<>();
  }

  public int leftExtent() {
    return leftExtent;
  }

  public int rightExtent() {
    return rightExtent;
  }
}
