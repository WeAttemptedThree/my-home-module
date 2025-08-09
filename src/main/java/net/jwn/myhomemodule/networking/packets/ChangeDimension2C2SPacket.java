package net.jwn.myhomemodule.networking.packets;

import io.netty.buffer.ByteBuf;
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

public record ChangeDimension2C2SPacket() implements CustomPacketPayload {
    public static final Type<ChangeDimension2C2SPacket> TYPE
            = new Type<>(ResourceLocation.fromNamespaceAndPath("myhomemodule", "change_dim_2"));
    public static final StreamCodec<ByteBuf, ChangeDimension2C2SPacket> STREAM_CODEC
            = StreamCodec.unit(new ChangeDimension2C2SPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ChangeDimension2C2SPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                // ADD HERE
                MinecraftServer server = player.getServer();
                ServerLevel netherLevel = server.getLevel(Level.OVERWORLD); // 네더 차원

                Vec3 targetPos = new Vec3(0, -60, 0); // 이동할 위치
                Vec3 speed = Vec3.ZERO; // 이동 시 속도
                float yaw = 0F;
                float pitch = 0F;
                boolean missingRespawnBlock = false;

                DimensionTransition.PostDimensionTransition callback = (p) -> {
                    p.sendSystemMessage(Component.literal("차원 이동 완료!"));
                };

                DimensionTransition transition = new DimensionTransition(
                        netherLevel, targetPos, speed, yaw, pitch, missingRespawnBlock, callback
                );

                player.changeDimension(transition);
            }
        });
    }
}
