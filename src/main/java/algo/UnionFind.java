package algo;

import java.util.HashMap;
import java.util.Map;

public class UnionFind<T> {
    private final Map<T, T> father;
    private final Map<T, Integer> height;

    public UnionFind() {
        father = new HashMap<>();
        height = new HashMap<>();
    }

    public T find(T node) {
        T root = node;
        while (father.containsKey(root))
            root = father.get(root);
        T temp;
        while (father.containsKey(node)) {
            temp = father.get(node);
            father.put(node, root);
            node = temp;
        }
        return root;
    }

    public boolean union(T x, T y) {
        final T rootX = find(x);
        final T rootY = find(y);
        if (rootX == rootY)
            return false;
        if (!height.containsKey(rootX)) height.put(rootX, 0);
        if (!height.containsKey(rootY)) height.put(rootY, 0);
        if (height.get(rootX) < height.get(rootY))
            father.put(rootX, rootY);
        else {
            father.put(rootY, rootX);
            if (height.get(rootX).equals(height.get(rootY)))
                height.put(rootX, height.get(rootX) + 1);
        }
        return true;
    }
}
