package xyz.trixkz.bedwarspractice.managers.block;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import xyz.trixkz.bedwarspractice.Main;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BlockManager {

    private Main main;

    private List<UserBlock> userBlocks = new ArrayList<UserBlock>();

    public BlockManager(Main main) {
        this.main = main;

        for (String string : this.main.getFiles().getSettingsConfig().getConfigurationSection("blocks").getKeys(false)) {
            ConfigurationSection configurationSection = this.main.getFiles().getSettingsConfig().getConfigurationSection("blocks").getConfigurationSection(string);

            this.userBlocks.add(new UserBlock(
                    Utils.translate(configurationSection.getString("name")),
                    Utils.translate(configurationSection.getStringList("lore")),
                    Material.valueOf(configurationSection.getString("material")),
                    configurationSection.getInt("data"),
                    configurationSection.getInt("level")
            ));
        }
    }

    public UserBlock getBlockByName(String name) {
        return this.userBlocks.stream().filter(block -> block.getName().equalsIgnoreCase(name)).findFirst().orElse(this.userBlocks.get(0));
    }
}
