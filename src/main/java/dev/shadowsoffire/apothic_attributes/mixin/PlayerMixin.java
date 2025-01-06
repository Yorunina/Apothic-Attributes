package dev.shadowsoffire.apothic_attributes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@Mixin(value = Player.class, remap = false)
public class PlayerMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", ordinal = 0), method = "attack(Lnet/minecraft/world/entity/Entity;)V")
    private boolean apoth_handleKilledByAuxDmg(LivingEntity target, DamageSource src, float dmg) {
        boolean res = target.hurt(src, dmg);
        return res || target.getPersistentData().getBoolean("apoth.killed_by_aux_dmg");
    }

    /**
     * "Fixes" MC-268917 by clamping the fall distance while sneaking to the base step height value of 0.6F instead of using the total.
     * <p>
     * This ensures that, while sneaking, the player may never move down further than 0.6 blocks, instead of being able to fall down an amount equal to their step
     * height.
     * 
     * @apiNote "Fixes" is in quotes because Mojang has closed the bug as "Working as Intended", despite years of player feedback indicating this is a poor choice.
     */
    @WrapOperation(method = "maybeBackOffFromEdge", at = @At(value = "INVOKE", target = "maxUpStep()F"))
    private float apoth_dontFallOffACliff(Player player, Operation<Float> original) {
        return Math.min(original.call(player), 0.6F);
    }
}
