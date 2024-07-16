import java.util.HashMap;
import java.util.Map;

public class VariableState {
    Map<Integer, Integer> read;
    Write write;

    VariableState() {
        this.read = new HashMap<>();
        this.write = null;
    }
}
