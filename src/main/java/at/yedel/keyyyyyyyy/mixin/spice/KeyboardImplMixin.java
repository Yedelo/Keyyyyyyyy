package at.yedel.keyyyyyyyy.mixin.spice;



import at.yedel.keyyyyyyyy.config.KeyyyyyyyyConfig;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(targets = {"org.polyfrost.lwjgl.impl.input.KeyboardImpl"})
public abstract class KeyboardImplMixin {
    @ModifyVariable(method = "createEvent", at = @At("HEAD"), argsOnly = true, index = 3)
    private boolean keyyyyyyyy$createUnrepeatEvent(boolean original) {
        System.out.println("createUnrepeatEvent");
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            return true;
        }
        return original;
    }

    @ModifyVariable(method = "keyHandler", at = @At("HEAD"), argsOnly = true, index = 3)
    private int keyyyyyyyy$changeRepeatAction(int original) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled() && original == 2) {
            return 1;
        }
        return original;
    }

    @Inject(method = "areRepeatEventsEnabled", at = @At("HEAD"), cancellable = true)
    private void keyyyyyyyy$alwaysReturnEnabled(CallbackInfoReturnable<Boolean> cir) {
        System.out.println("alwaysReturnEnabled");
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            cir.setReturnValue(true);
        }
    }

    @ModifyVariable(method = "enableRepeatEvents", at = @At("HEAD"), argsOnly = true)
    private boolean keyyyyyyyy$alwaysEnableRepeatEvents(boolean original) {
        System.out.println("alwaysEnableRepeatEvents");
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            return true;
        }
        return original;
    }

    @Inject(method = "isRepeatEvent", at = @At("HEAD"), cancellable = true)
    private void keyyyyyyyy$alwaysUnrepeatEvent(CallbackInfoReturnable<Boolean> cir) {
        System.out.println("alwaysUnrepeatEvent");
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            cir.setReturnValue(false);
        }
    }
}
