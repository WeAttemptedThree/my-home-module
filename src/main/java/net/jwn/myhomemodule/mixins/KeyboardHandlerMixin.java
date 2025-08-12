package net.jwn.myhomemodule.mixins;

import net.jwn.myhomemodule.networking.packets.ComeBackC2SPacket;
import net.jwn.myhomemodule.networking.packets.GoHomeC2SPacket;
import net.jwn.myhomemodule.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void onKeyPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (key == KeyBindings.GLFW_KEY_F1 && action == GLFW.GLFW_PRESS) {
            boolean cHeld = GLFW.glfwGetKey(window, KeyBindings.COMMAND_KEY) == GLFW.GLFW_PRESS;
            if (cHeld) {
                // ADD HERE
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.player.sendSystemMessage(Component.literal("C + F1 was pressed."));
                    PacketDistributor.sendToServer(new GoHomeC2SPacket());
                }
                ci.cancel();
            }
        } else if (key == KeyBindings.GLFW_KEY_F2 && action == GLFW.GLFW_PRESS) {
            boolean cHeld = GLFW.glfwGetKey(window, KeyBindings.COMMAND_KEY) == GLFW.GLFW_PRESS;
            if (cHeld) {
                // ADD HERE
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.player.sendSystemMessage(Component.literal("C + F2 was pressed."));
                    PacketDistributor.sendToServer(new ComeBackC2SPacket());
                }
                ci.cancel();
            }
        }
    }
}
