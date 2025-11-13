package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A mutable mathematical set of integers, backed by an ArrayList<Integer>.
 * No duplicates are allowed. All mutator operations modify this instance.
 */
public class IntegerSet  {
    private List<Integer> set = new ArrayList<Integer>();

    /** Clears the internal representation of the set. */
    public void clear() {
        set.clear();
    }

    /** Returns the number of elements in the set. */
    public int length() {
        return set.size();
    }

    /**
     * Returns true if the 2 sets are equal, false otherwise.
     * Two sets are equal if they contain all of the same values in ANY order.
     * This overrides the equals method from the Object class.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerSet)) return false;
        IntegerSet other = (IntegerSet) o;
        // Order-independent equality: same size and mutually contains all
        if (this.set.size() != other.set.size()) return false;
        return this.set.containsAll(other.set) && other.set.containsAll(this.set);
    }

    /** Returns true if the set contains the value, otherwise false. */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /** Returns the largest item in the set (throws IllegalStateException if empty). */
    public int largest()  {
        if (set.isEmpty()) {
            throw new IllegalStateException("largest() called on empty set");
        }
        int max = set.get(0);
        for (int v : set) {
            if (v > max) max = v;
        }
        return max;
    }

    /** Returns the smallest item in the set (throws IllegalStateException if empty). */
    public int smallest()  {
        if (set.isEmpty()) {
            throw new IllegalStateException("smallest() called on empty set");
        }
        int min = set.get(0);
        for (int v : set) {
            if (v < min) min = v;
        }
        return min;
    }

    /** Adds an item to the set or does nothing if already present. */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /** Removes an item from the set or does nothing if not there. */
    public void remove(int item) {
        set.remove(Integer.valueOf(item));
    }

    /** Set union: modifies this to contain all unique elements in this or other. */
    public void union(IntegerSet other) {
        // Ensure uniqueness by checking before add
        for (int v : other.set) {
            if (!this.set.contains(v)) {
                this.set.add(v);
            }
        }
    }

    /** Set intersection: modifies this to contain only elements in both sets. */
    public void intersect(IntegerSet other) {
        this.set.retainAll(other.set); // does not mutate 'other'
    }

    /** Set difference (this \ other): removes elements from this that are found in other. */
    public void diff(IntegerSet other) {
        this.set.removeAll(other.set); // does not mutate 'other'
    }

    /**
     * Set complement: modifies this to become (other \ this).
     * i.e., elements that exist in 'other' but NOT in this set.
     */
    public void complement(IntegerSet other) {
        // Snapshot current contents of this, then build new contents from other minus snapshot
        List<Integer> current = new ArrayList<>(this.set);
        List<Integer> result = new ArrayList<>(other.set);
        result.removeAll(current); // (other \ this)
        this.set.clear();
        this.set.addAll(result);
    }

    /** Returns true if the set is empty, false otherwise. */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /** Returns a String representation in square brackets; overrides Object.toString(). */
    @Override
    public String toString() {
        // Deterministic order helps testing; sorting does not violate any spec.
        List<Integer> copy = new ArrayList<>(set);
        Collections.sort(copy);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < copy.size(); i++) {
            sb.append(copy.get(i));
            if (i < copy.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
