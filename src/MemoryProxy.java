public class MemoryProxy {
    private final RAM ram;
    private MemoryBlock pageTable;

    public MemoryProxy(RAM ram) {
        this.ram = ram;
    }

    public void setPageTable(MemoryBlock pageTable) {
        this.pageTable = pageTable;
    }

    public void writeWord(Word word, int address) {
        int block = address / Constants.BLOCK_SIZE;
        int index = address % Constants.BLOCK_SIZE;
        int realBlock = pageTable.data[block].toInteger();
        ram.writeWord(word, realBlock, index);
    }

    public Word readWord(int address) {
        int block = address / Constants.BLOCK_SIZE;
        int index = address % Constants.BLOCK_SIZE;
        int realBlock = pageTable.data[block].toInteger();
        return ram.readWord(realBlock, index);
    }

    public void printBlock(int block) {
        int realBlock = pageTable.data[block].toInteger();
        ram.printBlock(realBlock);
    }
}
