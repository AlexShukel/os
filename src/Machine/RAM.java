package Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RAM {
    private final boolean[] free = new boolean[Constants.RM_RAM_SIZE];
    private final MemoryBlock[] blocks = new MemoryBlock[Constants.RM_RAM_SIZE];
    private final Random rng = new Random(System.currentTimeMillis());

    public RAM() {
        for (int i = 0; i < Constants.RM_RAM_SIZE; i++) {
            free[i] = true;
            blocks[i] = new MemoryBlock();
        }
    }

    public void writeWord(Word word, int block, int index) {
        free[block] = false;
        blocks[block].data[index] = word;
    }

    public Word readWord(int block, int index) {
        return blocks[block].data[index];
    }

    public void printBlock(int block) {
        for (int i = 0; i < Constants.BLOCK_SIZE; i++) {
            System.out.printf("%.6s ", blocks[block].data[i].toString());
        }
        System.out.println();
    }

    public MemoryBlock getBlock(int index) {
        return blocks[index];
    }

    public int pickFreeBlockIndex() {
        List<Integer> freeIndices = new ArrayList<>();

        for (int i = 0; i < Constants.RM_RAM_SIZE; i++) {
            if (free[i]) {
                freeIndices.add(i);
            }
        }

        if (freeIndices.isEmpty()) {
            return -1;
        }

        return freeIndices.get(rng.nextInt(freeIndices.size()));
    }
}
