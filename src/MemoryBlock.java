public class MemoryBlock {
    public final Word[] data = new Word[Constants.BLOCK_SIZE];

    public MemoryBlock() {
        for (int i = 0; i < data.length; i++) {
            data[i] = new Word();
        }
    }
}