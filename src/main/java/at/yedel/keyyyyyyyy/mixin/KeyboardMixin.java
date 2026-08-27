package at.yedel.keyyyyyyyy.mixin;



import at.yedel.keyyyyyyyy.config.KeyyyyyyyyConfig;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Inject(method = "areRepeatEventsEnabled", at = @At("HEAD"), cancellable = true)
    private static void keyyyyyyyy$alwaysReturnEnabled(CallbackInfoReturnable<Boolean> cir) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            cir.setReturnValue(true);
        }
    }

    @ModifyVariable(method = "enableRepeatEvents", at = @At("HEAD"), argsOnly = true)
    private static boolean keyyyyyyyy$alwaysEnableRepeatEvents(boolean original) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            return true;
        }
        return original;
    }

    @Inject(method = "isRepeatEvent", at = @At("HEAD"), cancellable = true)
    private static void keyyyyyyyy$alwaysUnrepeatEvent(CallbackInfoReturnable<Boolean> cir) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            cir.setReturnValue(false);
        }
    }
}
