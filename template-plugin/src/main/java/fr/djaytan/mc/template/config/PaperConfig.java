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
package fr.djaytan.mc.template.config;

import fr.djaytan.mc.template.core.commons.EntireAudienceAggregator;
import fr.djaytan.mc.template.core.commons.MinecraftCommand;
import fr.djaytan.mc.template.plugin.DelegatedCommand;
import fr.djaytan.mc.template.plugin.TemplatePlugin;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class PaperConfig {

  private static final Logger LOG = LoggerFactory.getLogger(PaperConfig.class);

  PaperConfig() {
    // Static class but required to be instantiated by Spring
  }

  @Bean
  static JavaPlugin plugin() {
    return JavaPlugin.getPlugin(TemplatePlugin.class);
  }

  @Bean
  static Server server(JavaPlugin plugin) {
    return plugin.getServer();
  }

  @Bean
  static PluginManager pluginManager(Server server) {
    return server.getPluginManager();
  }

  @Bean
  static BukkitScheduler bukkitScheduler(Server server) {
    return server.getScheduler();
  }

  @Bean
  static CommandMap commandMap(Server server) {
    return server.getCommandMap();
  }

  @Bean
  static Executor mainThreadExecutor(BukkitScheduler bukkitScheduler, JavaPlugin plugin) {
    return bukkitScheduler.getMainThreadExecutor(plugin);
  }

  @Bean
  @Primary
  static ClassLoader pluginClassLoader(JavaPlugin plugin) {
    return plugin.getClass().getClassLoader();
  }

  @Bean
  static ApplicationRunner autoRegisterListeners(
      ApplicationContext applicationContext, JavaPlugin plugin, PluginManager pluginManager) {
    Collection<Listener> listeners = applicationContext.getBeansOfType(Listener.class).values();
    LOG.info("{} classes listening events found", listeners.size());
    return args -> listeners.forEach(listener -> pluginManager.registerEvents(listener, plugin));
  }

  @Bean
  static ApplicationRunner autoRegisterCommands(
      Collection<MinecraftCommand> minecraftCommands, JavaPlugin plugin, CommandMap commandMap) {
    return args ->
        minecraftCommands.stream()
            .map(DelegatedCommand::new)
            .forEach(delegatedCommand -> commandMap.register(plugin.getName(), delegatedCommand));
  }

  /** Thread-safe */
  @Bean
  static EntireAudienceAggregator entireAudienceAggregator(
      Server server, Executor mainThreadExecutor) {
    return () ->
        CompletableFuture.supplyAsync(
                () -> Audience.audience(server.getOnlinePlayers()), mainThreadExecutor)
            .join();
  }
}
