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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import net.kyori.adventure.audience.Audience;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine.Command;

@ExtendWith(MockitoExtension.class)
class MinecraftCommandTest {

  @Mock private Audience audience;
  private MinecraftCommand minecraftCommand;

  @BeforeEach
  void beforeEach() {
    minecraftCommand = new DummyCommand();
  }

  @Test
  void whenRetrievingCommandNameFromAnnotatedChildClass_thenShallReturnIt() {
    assertThat(minecraftCommand.getName()).isEqualTo("dummy");
  }

  @Test
  void whenRetrievingCommandNameWithoutExpectedAnnotationOnChildClass_thenShallThrowException() {
    MinecraftCommand minecraftCommandWithoutAnnotation = new DummyCommandWithoutAnnotation();

    assertThatThrownBy(minecraftCommandWithoutAnnotation::getName)
        .isExactlyInstanceOf(IllegalStateException.class)
        .hasMessage("Any Minecraft command must be annotated by picocli.CommandLine$Command class");
  }

  @Test
  void whenDefiningAudience_thenShallBeRetrievable() {
    minecraftCommand.setAudience(audience);

    assertThat(minecraftCommand.getAudience()).isSameAs(audience);
  }

  @Test
  void whenRetrievingNotYetDefinedAudience_thenShallThrowException() {
    assertThatThrownBy(() -> minecraftCommand.getAudience())
        .isExactlyInstanceOf(IllegalStateException.class)
        .hasMessage("The command must have an audience defined and not-null");
  }

  @Command(name = "dummy")
  private static final class DummyCommand extends MinecraftCommand {}

  private static final class DummyCommandWithoutAnnotation extends MinecraftCommand {}
}
