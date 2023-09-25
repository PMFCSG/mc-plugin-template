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
package fr.djaytan.mc.template.core.person.view;

import fr.djaytan.mc.template.core.commons.MessageDispatcher;
import java.util.ResourceBundle;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;

@org.springframework.stereotype.Component
public final class PersonView {

  private final MessageDispatcher messageDispatcher;
  private final ResourceBundle resourceBundle;
  private final MiniMessage miniMessage;

  @Autowired
  PersonView(
      @NonNull MessageDispatcher messageDispatcher,
      @Qualifier("person") @NonNull ResourceBundle resourceBundle,
      @NonNull MiniMessage miniMessage) {
    this.messageDispatcher = messageDispatcher;
    this.resourceBundle = resourceBundle;
    this.miniMessage = miniMessage;
  }

  public void sendInvalidUuidErrorMessage(@NonNull Audience audience, @NonNull String personUuid) {
    TagResolver tagResolver = Placeholder.unparsed("person_uuid", personUuid);
    Component messageContent =
        miniMessage.deserialize(resourceBundle.getString("failure.invalid_uuid"), tagResolver);
    messageDispatcher.sendFailureMessage(audience, messageContent);
  }

  public void sendNoPersonFoundErrorMessage(
      @NonNull Audience audience, @NonNull String personUuid) {
    TagResolver tagResolver = Placeholder.unparsed("person_uuid", personUuid);
    Component messageContent =
        miniMessage.deserialize(resourceBundle.getString("failure.no_person_found"), tagResolver);
    messageDispatcher.sendFailureMessage(audience, messageContent);
  }

  public void sendWhoisheMessage(@NonNull Audience audience, @NonNull String whoishe) {
    TagResolver tagResolver = Placeholder.unparsed("whoishe", whoishe);
    Component messageContent =
        miniMessage.deserialize(resourceBundle.getString("info.whoishe"), tagResolver);
    messageDispatcher.sendInfoMessage(audience, messageContent);
  }
}
