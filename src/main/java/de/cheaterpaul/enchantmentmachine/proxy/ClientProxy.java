package de.cheaterpaul.enchantmentmachine.proxy;

import de.cheaterpaul.enchantmentmachine.client.ModClientData;
import de.cheaterpaul.enchantmentmachine.client.screen.EnchanterScreen;
import de.cheaterpaul.enchantmentmachine.client.screen.StorageScreen;
import de.cheaterpaul.enchantmentmachine.network.message.EnchantmentPacket;
import de.cheaterpaul.enchantmentmachine.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = REFERENCE.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void onLoadComplete() {
        ModClientData.registerScreens();
    }

    @Override
    public void onClientSetup() {
        ModClientData.registerTileEntityRenderer();
    }

    @SubscribeEvent
    public static void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        ModClientData.textureStitchEvent(event);
    }

    @Override
    public void handleEnchantmentPacket(EnchantmentPacket packet) {
        if (Minecraft.getInstance().currentScreen instanceof StorageScreen) {
            ((StorageScreen) Minecraft.getInstance().currentScreen).updateEnchantments(packet.getEnchantments());
        } else if (Minecraft.getInstance().currentScreen instanceof EnchanterScreen) {
            ((EnchanterScreen) Minecraft.getInstance().currentScreen).updateEnchantments(packet.getEnchantments());
        } else if (packet.shouldOpenEnchantmentScreen()) {
            StorageScreen screen = new StorageScreen();
            Minecraft.getInstance().displayGuiScreen(screen);
            screen.updateEnchantments(packet.getEnchantments());

        }
    }
}
