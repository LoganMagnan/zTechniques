package xyz.trixkz.bedwarspractice.managers.block;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserBlock {

    private String name;
    private List<String> lore;
    private Material blockType;
    private int blockData;
    private int level;
}
