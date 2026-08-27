package at.yedel.keyyyyyyyy.mixin.spice;



import at.yedel.keyyyyyyyy.config.KeyyyyyyyyConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Pseudo
@Mixin(targets = "org.polyfrost.lwjgl.impl.input.KeyboardImpl")
public abstract class KeyboardImplMixin {
    @ModifyVariable(method = "keyHandler", at = @At("HEAD"), argsOnly = true, index = 5)
    private int keyyyyyyyy$presentRegularPressAction(int original) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled() && original == 2) {
            return 1;
        }
        return original;
    }
    @ModifyVariable(method = "createEvent", at = @At("HEAD"), argsOnly = true, index = 4)
    private boolean keyyyyyyyy$createUnrepeatEvent(boolean original) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            return false;
        }
        return original;
    }

    @Inject(method = "areRepeatEventsEnabled", at = @At("HEAD"), cancellable = true)
    private void keyyyyyyyy$alwaysReturnEnabled(CallbackInfoReturnable<Boolean> cir) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            cir.setReturnValue(true);
        }
    }

    @ModifyVariable(method = "enableRepeatEvents", at = @At("HEAD"), argsOnly = true)
    private boolean keyyyyyyyy$alwaysEnableRepeatEvents(boolean original) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            return true;
        }
        return original;
    }

    @Inject(method = "isRepeatEvent", at = @At("HEAD"), cancellable = true)
    private void keyyyyyyyy$alwaysUnrepeatEvent(CallbackInfoReturnable<Boolean> cir) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            cir.setReturnValue(false);
        }
    }
}
