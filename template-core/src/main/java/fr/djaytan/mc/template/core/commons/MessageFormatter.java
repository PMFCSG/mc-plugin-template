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

import java.util.Locale;
import java.util.ResourceBundle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;

@org.springframework.stereotype.Component
public class MessageFormatter {

  private static final String MESSAGE_PREFIX_TAG_NAME = "message_prefix";
  private static final String MESSAGE_CONTENT_TAG_NAME = "message_content";

  private final Locale locale;
  private final MiniMessage miniMessage;
  private final ResourceBundle resourceBundle;

  @Autowired
  MessageFormatter(
      @NonNull Locale locale,
      @NonNull MiniMessage miniMessage,
      @Qualifier("commons") @NonNull ResourceBundle resourceBundle) {
    this.locale = locale;
    this.miniMessage = miniMessage;
    this.resourceBundle = resourceBundle;
  }

  public @NonNull Component format(
      @NonNull MessageType messageType, @NonNull Component messageContent) {
    if (messageType == MessageType.RAW) {
      return messageContent;
    }

    String msgLayout = getMessageLayout(messageType);
    TagResolver tagResolver = getMessageLayoutTagResolver(messageType, messageContent);

    // Disable default Minecraft static style without preventing explicit activation
    return miniMessage.deserialize(msgLayout, tagResolver).decoration(TextDecoration.ITALIC, false);
  }

  private @NonNull String getMessageLayout(@NonNull MessageType messageType) {
    return resourceBundle.getString(getMessageLayoutKey(messageType));
  }

  private @NonNull String getMessageLayoutKey(@NonNull MessageType messageType) {
    Validate.validState(
        messageType != MessageType.RAW,
        "Unexpected error: no message layout key can be inferred for message type %s",
        MessageType.RAW.name());
    return String.format("format.%s.layout", messageType.name().toLowerCase(locale));
  }

  private @NonNull TagResolver getMessageLayoutTagResolver(
      @NonNull MessageType messageType, @NonNull Component messageContent) {
    String messagePrefix = getMessagePrefix(messageType);
    return TagResolver.builder()
        .resolver(Placeholder.unparsed(MESSAGE_PREFIX_TAG_NAME, messagePrefix))
        .resolver(Placeholder.component(MESSAGE_CONTENT_TAG_NAME, messageContent))
        .build();
  }

  private @NonNull String getMessagePrefix(@NonNull MessageType messageType) {
    return resourceBundle.getString(getMessagePrefixKey(messageType));
  }

  private @NonNull String getMessagePrefixKey(@NonNull MessageType messageType) {
    Validate.validState(
        messageType != MessageType.RAW,
        "Unexpected error: no message prefix key can be inferred for message type %s",
        MessageType.RAW.name());
    return String.format("format.%s.prefix", messageType.name().toLowerCase(locale));
  }
}
