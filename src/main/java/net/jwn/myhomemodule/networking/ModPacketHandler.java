package net.jwn.myhomemodule.networking;

import net.jwn.myhomemodule.networking.packets.ComeBackC2SPacket;
import net.jwn.myhomemodule.networking.packets.GoHomeC2SPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class ModPacketHandler {
    public static final String PROTOCOL_VERSION = "1";

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        // ADD HERE

        registrar.playToServer(GoHomeC2SPacket.TYPE, GoHomeC2SPacket.STREAM_CODEC, GoHomeC2SPacket::handle);
        registrar.playToServer(ComeBackC2SPacket.TYPE, ComeBackC2SPacket.STREAM_CODEC, ComeBackC2SPacket::handle);
    }
}
