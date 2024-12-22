package dev.shadowsoffire.apothic_attributes.util;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.neoforge.common.Tags;

public class AttributesUtil {

    public static boolean isPhysicalDamage(DamageSource src) {
        return !src.is(Tags.DamageTypes.IS_MAGIC) && !src.is(DamageTypeTags.IS_FIRE) && !src.is(DamageTypeTags.IS_EXPLOSION);
    }

}
