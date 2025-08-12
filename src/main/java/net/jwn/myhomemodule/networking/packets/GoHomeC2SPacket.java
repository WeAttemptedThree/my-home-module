package net.jwn.myhomemodule.networking.packets;

import io.netty.buffer.ByteBuf;
import net.jwn.myhomemodule.MyHomeModule;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record GoHomeC2SPacket() implements CustomPacketPayload {
    public static final Type<GoHomeC2SPacket> TYPE
            = new Type<>(ResourceLocation.fromNamespaceAndPath(MyHomeModule.MOD_ID, "change_dim"));
    public static final StreamCodec<ByteBuf, GoHomeC2SPacket> STREAM_CODEC
            = StreamCodec.unit(new GoHomeC2SPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(GoHomeC2SPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                // ADD HERE
                ResourceKey<Level> MY_HOME = ResourceKey.create(
                        ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("minecraft", "dimension")),
                        ResourceLocation.fromNamespaceAndPath(MyHomeModule.MOD_ID, "myhome")
                );

                MinecraftServer server = player.getServer();
                ServerLevel myHome = server.getLevel(MY_HOME);

                Vec3 targetPos = new Vec3(0, 0, 0); // 이동할 위치
                Vec3 speed = Vec3.ZERO; // 이동 시 속도
                float yaw = 0F;
                float pitch = 0F;
                boolean missingRespawnBlock = false;

                DimensionTransition.PostDimensionTransition callback = (p) -> {
                    p.sendSystemMessage(Component.literal("차원 이동 완료!"));
                };

                DimensionTransition transition = new DimensionTransition(
                        myHome, targetPos, speed, yaw, pitch, missingRespawnBlock, callback
                );

                player.changeDimension(transition);
            }
        });
    }
}
