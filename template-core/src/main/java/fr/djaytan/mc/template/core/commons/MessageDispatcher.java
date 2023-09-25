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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

/**
 * Dispatches messages of the right type with the right format and to the targeted audience.
 *
 * <p>This class is thread-safe since all messages sending are in any cases scheduled and then
 * executed on the main server thread.
 */
@SuppressWarnings("unused")
@org.springframework.stereotype.Component
public final class MessageDispatcher {

  private final EntireAudienceAggregator entireAudienceAggregator;
  private final Executor mainThreadExecutor;
  private final MessageFormatter messageFormatter;

  @Autowired
  MessageDispatcher(
      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") @NonNull
          EntireAudienceAggregator entireAudienceAggregator,
      @NonNull Executor mainThreadExecutor,
      @NonNull MessageFormatter messageFormatter) {
    this.entireAudienceAggregator = entireAudienceAggregator;
    this.mainThreadExecutor = mainThreadExecutor;
    this.messageFormatter = messageFormatter;
  }

  public void sendInfoMessage(@NonNull Audience audience, @NonNull Component message) {
    sendMessage(audience, MessageType.INFO, message);
  }

  public void sendSuccessMessage(@NonNull Audience audience, @NonNull Component message) {
    sendMessage(audience, MessageType.SUCCESS, message);
  }

  public void sendFailureMessage(@NonNull Audience audience, @NonNull Component message) {
    sendMessage(audience, MessageType.FAILURE, message);
  }

  public void sendWarningMessage(@NonNull Audience audience, @NonNull Component message) {
    sendMessage(audience, MessageType.WARNING, message);
  }

  public void sendErrorMessage(@NonNull Audience audience, @NonNull Component message) {
    sendMessage(audience, MessageType.ERROR, message);
  }

  public void sendRawMessage(@NonNull Audience audience, @NonNull Component message) {
    sendMessage(audience, MessageType.RAW, message);
  }

  public void broadcastMessage(@NonNull Component message) {
    sendMessage(entireAudienceAggregator.get(), MessageType.BROADCAST, message);
  }

  private void sendMessage(
      @NonNull Audience audience, @NonNull MessageType messageType, @NonNull Component message) {
    Component fmtMessage = messageFormatter.format(messageType, message);
    CompletableFuture.runAsync(() -> audience.sendMessage(fmtMessage), mainThreadExecutor);
  }
}
