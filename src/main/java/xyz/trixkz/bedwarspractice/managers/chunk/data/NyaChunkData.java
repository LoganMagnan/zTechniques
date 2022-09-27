package xyz.trixkz.bedwarspractice.managers.chunk.data;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.ChunkSection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter @Setter
public class NyaChunkData {

    public Map<NyaChunk, ChunkSection[]> chunks = new ConcurrentHashMap<>();

    public ChunkSection[] getNyaChunk(int x, int z) {
        for (Map.Entry<NyaChunk, ChunkSection[]> chunksFromMap : chunks.entrySet()) {
            if (chunksFromMap.getKey().getX() == x && chunksFromMap.getKey().getZ() == z) {
                return chunksFromMap.getValue();
            }
        }

        return null;
    }
}
