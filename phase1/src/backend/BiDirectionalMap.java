package backend;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A simple generic class that stores the mappings between a key and its values.
 * Note that a key can have multiple values, and a value can have multiple keys,
 * but a value cannot have no keys.
 * @param <K> The key type
 * @param <V> The value type
 */
public interface BiDirectionalMap<K, V> extends Serializable{

    /**
     * Add an key with no value
     * If the key already exist in the repo, it will not do anything.
     * @param key A new key
     */
    void addKey(K key);

    /**
     * Get a copy of the list of keys that has a specific value.
     * If it cannot find the value, it returns null.
     * @param value The value to search for
     * @return A list of keys that has that value
     */
    List<K> getKeysFromValue(V value);

    /**
     * Get a copy of the list of values associated with that key
     * If it cannot find the key, it will return null.
     * @param key The key
     * @return The values with that key
     */
    List<V> getValuesFromKey(K key);

    /**
     * Adds a value to a specific key
     * If the value already exist with the key, that value will not be added
     * @param key The key to set the value to
     * @param value  The value
     */
    void addValueToKey(K key, V value);

    /**
     * Adds an key with a value
     * If the value does not exist, it will add the value to the repository.
     * If the key already exist, it will not do anything.
     * If the value already exist, it will use the pre-existing value to value it with the new key.
     * @param key The key to set the value to
     * @param value  The value to include
     */
    void addKeyWithValue(K key, V value);

    /**
     * Replaces a value with another value
     * If the original value does not exist, it will not do anything.
     * If the new value already exist, it will not do anything.
     * @param oldValue The old value to remove
     * @param newValue The new value to replace the old value
     */
    void replaceValue(V oldValue, V newValue);

    /**
     * Replaces an key with another key
     * If the old key does not exist it will not do anything.
     * If the new key already exist, it will not do anything.
     * @param oldKey The key to remove
     * @param newKey The new key to replace the removed key
     */
    void replaceKey(K oldKey, K newKey);

    /**
     * Deletes an key from the repository.
     * If the key to be deleted does not exist, it will not do anything.
     * If deleting an key cause a value to be unvalueged to any other key,
     * it also deletes the value in this repository too.
     * @param key The key to delete from the repository
     */
    void deleteKey(K key);

    /**
     * Deletes the value from the repository
     * It will remove the values from all keys it is valueged with.
     *  If the value does not exist, it will not do anything.
     * @param value The value to delete from the repository
     */
    void deleteValue(V value);

    /**
     * Deletes a value from an key
     * If the key and the value does not exist, it will not do anything.
     * If deleting the value cause the value to become unvalueged to any key,
     * it will delete the value.
     * @param key The key with the value
     * @param value  The value to remove
     */
    void deleteValueFromKey(K key, V value);

    /**
     * Returns a copy of the mappings
     * @return A copy of the mappings
     */
    Map<K, List<V>> getCopyOfMappings();
}
