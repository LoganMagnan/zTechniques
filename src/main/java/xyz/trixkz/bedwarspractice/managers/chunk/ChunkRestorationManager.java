package xyz.trixkz.bedwarspractice.managers.chunk;

import net.minecraft.server.v1_8_R3.ChunkSection;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import xyz.trixkz.bedwarspractice.managers.arena.StandaloneArena;
import xyz.trixkz.bedwarspractice.managers.chunk.data.NyaChunk;
import xyz.trixkz.bedwarspractice.managers.chunk.data.NyaChunkData;
import xyz.trixkz.bedwarspractice.managers.chunk.data.NyaNMSUtils;
import xyz.trixkz.bedwarspractice.utils.cuboid.Cuboid;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkRestorationManager {

    public Map<StandaloneArena, NyaChunkData> chunks = new ConcurrentHashMap<>();

    public void copy(StandaloneArena standaloneArena) {
        Cuboid cuboid = new Cuboid(standaloneArena.getMin().toBukkitLocation(), standaloneArena.getMax().toBukkitLocation());

        long startTime = System.currentTimeMillis();

        NyaChunkData nyaChunkData = new NyaChunkData();
        cuboid.getChunks().forEach(chunk -> {
            chunk.load();
            net.minecraft.server.v1_8_R3.Chunk nmsChunk = ((CraftChunk) chunk).getHandle();
            ChunkSection[] nmsSections = NyaNMSUtils.cloneSections(nmsChunk.getSections());
            nyaChunkData.chunks.put(new NyaChunk(chunk.getX(), chunk.getZ()), NyaNMSUtils.cloneSections(nmsSections));
        });
        chunks.put(standaloneArena, nyaChunkData);

        System.out.println("Chunks copied! (" + (System.currentTimeMillis() - startTime) + "ms)");
    }

    public void reset(StandaloneArena standaloneArena) {
        long startTime = System.currentTimeMillis();

        Cuboid cuboid = new Cuboid(standaloneArena.getMin().toBukkitLocation(), standaloneArena.getMax().toBukkitLocation());
        cuboid.getChunks().forEach(chunk -> {
            try {
                chunk.load();
                NyaNMSUtils.setSections(((CraftChunk) chunk).getHandle(), NyaNMSUtils.cloneSections(chunks.get(standaloneArena).getNyaChunk(chunk.getX(), chunk.getZ())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        System.out.println("Chunks have been reset! (took " + (System.currentTimeMillis() - startTime) + "ms)");
    }
}
