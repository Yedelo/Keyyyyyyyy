package at.yedel.keyyyyyyyy.mixin.pylon;



import at.yedel.keyyyyyyyy.config.KeyyyyyyyyConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Pseudo
@Mixin(targets = "pl.tomgirl.pylon.window.KeyboardSdl")
public abstract class KeyboardSdlMixin {
    @ModifyVariable(method = "putKeyboardEvent", at = @At("HEAD"), argsOnly = true)
    private boolean keyyyyyyyy$neverPutRepeatEvent(boolean original) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            return false;
        }
        return original;
    }

    @ModifyVariable(method = "getNumKeyboardEvents", at = @At("HEAD"), argsOnly = true)
    private boolean keyyyyyyyy$alwaysIncludeRepeats(boolean original) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            return true;
        }
        return original;
    }
}
