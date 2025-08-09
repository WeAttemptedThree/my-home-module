package net.jwn.myhomemodule.networking;

import net.jwn.myhomemodule.networking.packets.ChangeDimension2C2SPacket;
import net.jwn.myhomemodule.networking.packets.ChangeDimensionC2SPacket;
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

        registrar.playToServer(ChangeDimensionC2SPacket.TYPE, ChangeDimensionC2SPacket.STREAM_CODEC, ChangeDimensionC2SPacket::handle);
        registrar.playToServer(ChangeDimension2C2SPacket.TYPE, ChangeDimension2C2SPacket.STREAM_CODEC, ChangeDimension2C2SPacket::handle);
    }
}
