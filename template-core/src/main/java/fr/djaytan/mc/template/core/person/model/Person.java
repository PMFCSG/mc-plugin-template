/*
 * The MIT License
 * Copyright © 2023 Loïc DUBOIS-TERMOZ (alias Djaytan)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.djaytan.mc.template.core.person.model;

import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record Person(
    @Nullable UUID id,
    @NonNull String firstName,
    @NonNull String lastName,
    int age,
    @NonNull String address) {

  public Person(
      @NonNull String firstName, @NonNull String lastName, int age, @NonNull String address) {
    this(null, firstName, lastName, age, address);
  }

  public @NonNull String whoami() {
    return String.format(
        "I am %s %s (ID: %s), %d years old and living at %s",
        firstName, lastName, id, age, address);
  }

  public @NonNull String whoamiShortly() {
    return String.format("%s %s (ID: %s)", firstName, lastName, id);
  }
}
