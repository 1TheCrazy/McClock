package com.github.onethecrazy.mixin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

@Mixin(ModelBiped.class)
public class ChangePlayerHeadSize{

    @Shadow
    public ModelRenderer bipedHead;
    @Shadow
    public ModelRenderer bipedBody;
    @Shadow
    public ModelRenderer bipedRightArm;
    @Shadow
    public ModelRenderer bipedLeftArm;
    @Shadow
    public ModelRenderer bipedRightLeg;
    @Shadow
    public ModelRenderer bipedLeftLeg;
    @Shadow
    public ModelRenderer bipedHeadwear;



    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(Entity entityIn, float f, float g, float h, float i, float j, float scale, CallbackInfo ci) {
        ((ModelBase)(Object)this).setRotationAngles(f, g, h, i, j, scale, entityIn);
        GlStateManager.pushMatrix();
        if (((ModelBase)(Object)this).isChild) {
            System.out.println("wow");
            float k = 2.0f;
            GlStateManager.scale(1.5f / k, 1.5f / k, 1.5f / k);
            GlStateManager.translate(0.0f, 16.0f * scale, 0.0f);
            float headScale = scale;
            headScale *= 10f;
            this.bipedHead.render(headScale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0f / k, 1.0f / k, 1.0f / k);
            GlStateManager.translate(0.0f, 24.0f * scale, 0.0f);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
        } else {
            if (entityIn.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            float headScale = scale;
            headScale *= 10f;
            this.bipedHead.render(headScale);
            this.bipedBody.render(scale);
            this.bipedRightArm.render(scale);
            this.bipedLeftArm.render(scale);
            this.bipedRightLeg.render(scale);
            this.bipedLeftLeg.render(scale);
            this.bipedHeadwear.render(scale);
        }
        GlStateManager.popMatrix();

        ci.cancel();
    }
}
