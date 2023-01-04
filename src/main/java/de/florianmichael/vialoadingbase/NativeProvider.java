package de.florianmichael.vialoadingbase;

import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.bungee.providers.BungeeMovementTransmitter;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import de.florianmichael.vialoadingbase.provider.DefaultVersionProvider;
import io.netty.channel.EventLoop;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

public interface NativeProvider {

    boolean isSinglePlayer();
    int nativeVersion();
    int targetVersion();

    String[] nettyOrder();
    File run();
    JsonObject createDump();
    EventLoop eventLoop(final ThreadFactory threadFactory, final ExecutorService executorService);

    default void createProviders(final ViaProviders providers) {
        providers.use(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
        providers.use(VersionProvider.class, new DefaultVersionProvider());
    }
    default void createViaPlatform(ViaManagerImpl.ViaManagerBuilder builder) {
    }

    default int getClientsideVersion() {
        if (isSinglePlayer()) {
            return nativeVersion();
        }
        return targetVersion();
    }
}
