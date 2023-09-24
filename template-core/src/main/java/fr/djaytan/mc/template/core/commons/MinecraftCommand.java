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
package fr.djaytan.mc.template.core.commons;

import net.kyori.adventure.audience.Audience;
import org.apache.commons.lang3.Validate;
import org.springframework.lang.NonNull;
import picocli.CommandLine.Command;

/** The base class which must be implemented by most of the command classes if not all. */
public abstract class MinecraftCommand {

  /**
   * The audience is expected to be filled at command post-instantiation time by the {@link
   * CommandHandler}.
   */
  private Audience audience = null;

  public final @NonNull String getName() {
    Class<Command> commandAnnotationClass = Command.class;
    Validate.validState(
        getClass().isAnnotationPresent(commandAnnotationClass),
        "Any Minecraft command must be annotated by %s class",
        commandAnnotationClass.getName());
    return getClass().getAnnotation(commandAnnotationClass).name();
  }

  /**
   * Retrieves the audience associated with the current command at execution time.
   *
   * <p><b>/!\ This method shall be called only after post-instantiation time (i.e. at command
   * execution time) otherwise an exception will be thrown.
   *
   * @return The audience associated to this command being under execution.
   */
  protected final @NonNull Audience getAudience() {
    Validate.validState(audience != null, "The command must have an audience defined and not-null");
    return audience;
  }

  /**
   * Defines the audience associated with the command going to be executed.
   *
   * @param audience The audience associated with this command going to be executed.
   */
  public final void setAudience(@NonNull Audience audience) {
    this.audience = audience;
  }
}
