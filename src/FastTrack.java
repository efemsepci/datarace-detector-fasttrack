import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FastTrack {
    private final Map<Integer, Integer> threadClocks;
    private final Map<String, VariableState> variableStates;
    private final Lock lock;
    private int raceCount; // Veri yarýþý sayacý

    public FastTrack() {
        this.threadClocks = new HashMap<>();
        this.variableStates = new HashMap<>();
        this.lock = new ReentrantLock();
        this.raceCount = 0; // Baþlangýçta 0
    }

    public void startThread(int threadId) {
        threadClocks.put(threadId, 0);
    }

    public void read(int threadId, String variable) {
        lock.lock();
        try {
            threadClocks.put(threadId, threadClocks.get(threadId) + 1);

            if (!variableStates.containsKey(variable)) {
                variableStates.put(variable, new VariableState());
            }

            VariableState state = variableStates.get(variable);

            if (state.write != null && state.write.threadId != threadId && state.write.clock > threadClocks.get(threadId)) {
                raceCount++;
            }

            state.read.put(threadId, threadClocks.get(threadId));
        } finally {
            lock.unlock();
        }
    }

    public void write(int threadId, String variable) {
        lock.lock();
        try {
            threadClocks.put(threadId, threadClocks.get(threadId) + 1);

            if (!variableStates.containsKey(variable)) {
                variableStates.put(variable, new VariableState());
            }

            VariableState state = variableStates.get(variable);

            for (Map.Entry<Integer, Integer> entry : state.read.entrySet()) {
                int t = entry.getKey();
                int clock = entry.getValue();
                if (t != threadId && clock > threadClocks.get(threadId)) {
                    raceCount++;
                }
            }

            if (state.write != null && state.write.threadId != threadId && state.write.clock > threadClocks.get(threadId)) {
                raceCount++;
            }

            state.write = new Write(threadId, threadClocks.get(threadId));
            state.read.clear();
        } finally {
            lock.unlock();
        }
    }

    public int getRaceCount() {
        return raceCount;
    }
}
