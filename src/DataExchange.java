import utils.Logger;

import java.io.*;

public class DataExchange {
    public Word sourcePointer = new Word();
    public Word destinationPointer = new Word();
    public Word byteCount = new Word();

    public MemoryObject sourceObject = MemoryObject.MEMORY;
    public MemoryObject destinationObject = MemoryObject.MEMORY;

    public String path = "";

    private final MemoryProxy memory;

    public DataExchange(MemoryProxy memory) {
        this.memory = memory;
    }

    public int xchg() {
        if (destinationObject == MemoryObject.EXTERNAL) {
            Logger.debug("DataExchange::xchg() called with destination set to EXTERNAL. Writing in external storage is not supported.");
            return -1;
        }

        if (sourceObject == MemoryObject.EXTERNAL) {
            try (BufferedReader hdd = new BufferedReader(new FileReader("hdd.txt"))) {
                String line = readLine(hdd);

                while (line != null && !line.isEmpty()) {
                    while (line != null && !line.equals("@FILE0")) {
                        line = readLine(hdd);
                    }

                    if (line == null)
                        break;

                    line = readLine(hdd);
                    if (!path.equals(line))
                        continue;

                    line = readLine(hdd);
                    if (!"@CODE0".equals(line)) {
                        Logger.debug("ERROR: no @CODE0 in %s", path);
                        return -1;
                    }

                    int currentWordIndex = Constants.CODE_SEGMENT_START_BLOCK;
                    line = readLine(hdd);
                    while (line != null && !line.equals("@DATA0")) {
                        if (line.length() != Constants.WORD_SIZE) {
                            Logger.debug("ERROR: each line on external storage must contain exactly one word.");
                            return -1;
                        }
                        memory.writeWord(new Word(line), currentWordIndex++);
                        line = readLine(hdd);
                    }

                    int dataIndex = Constants.DATA_SEGMENT_START_BLOCK * Constants.BLOCK_SIZE;
                    line = readLine(hdd);
                    while (line != null && !line.equals("@END00")) {
                        if (line.length() != Constants.WORD_SIZE) {
                            Logger.debug("ERROR: each line on external storage must contain exactly one word.");
                            return -1;
                        }
                        memory.writeWord(new Word(line), dataIndex++);
                        line = readLine(hdd);
                    }

                    return 0;
                }

            } catch (IOException e) {
                Logger.debug("ERROR: failed to open hdd.txt");
                return -1;
            }
        }

        return -1;
    }

    private String readLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line != null && line.endsWith("\r")) {
            return line.substring(0, line.length() - 1);
        }
        return line;
    }
}
