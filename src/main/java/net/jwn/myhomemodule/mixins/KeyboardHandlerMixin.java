package net.jwn.myhomemodule.mixins;

import net.jwn.myhomemodule.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void onKeyPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (key == KeyBindings.ENTER_WORLD_KEY && action == GLFW.GLFW_PRESS) {
            boolean cHeld = GLFW.glfwGetKey(window, KeyBindings.COMMAND_KEY) == GLFW.GLFW_PRESS;

            if (cHeld) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.player.displayClientMessage(Component.literal("C + F1 detected!"), false);
                }
                ci.cancel();
            }
        }
    }
}
