package at.yedel.keyyyyyyyy.mixin.pylon;



import at.yedel.keyyyyyyyy.config.KeyyyyyyyyConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;



@Mixin(targets = "pl.tomgirl.pylon.window.KeyboardSdl$Event")
public abstract class EventMixin {
    @ModifyVariable(method = "set", at = @At("HEAD"), argsOnly = true)
    private boolean keyyyyyyyy$neverSetRepeatEvent(boolean original) {
        if (KeyyyyyyyyConfig.getInstance().isEnabled()) {
            return false;
        }
        return original;
    }
}
