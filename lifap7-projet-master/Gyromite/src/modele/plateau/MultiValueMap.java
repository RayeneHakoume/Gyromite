package modele.plateau;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 */
public interface MultiValueMap<K, V> {

    /**
     * Ajouter une valeur-clé.
     *
     * @param key key.
     * @param value value.
     */
    void add(K key, V value);

    /**
     * Ajouter une liste de clés <Valeur>.
     *
     * @param key key.
     * @param values values.
     */
    void add(K key, List<V> values);

    /**
     * Définissez une valeur-clé, si cette clé existe, elle sera remplacée, et
     * si elle n'existe pas, elle sera ajoutée.
     *
     * @param key key.
     * @param value values.
     */
    void set(K key, V value);

    /**
     * Définissez Key-List <Valeur>, si cette clé existe, elle sera remplacée,
     * si elle n'existe pas, elle sera ajoutée.
     *
     * @param key key.
     * @param values values.
     * @see #set(Object, Object)
     */
    void set(K key, List<V> values);

    /**
     * Remplacez toute la liste de clés <Valeur>.
     *
     * @param values values.
     */
    void set(Map<K, List<V>> values);

    /**
     * Supprimez une clé, toutes les valeurs correspondantes seront également
     * supprimées.
     *
     * @param key key.
     * @return value.
     */
    List<V> remove(K key);

    /**
     * Supprimez toutes les valeurs. Supprimez toutes les paires valeur / clé.
     */
    void clear();

    /**
     * Obtenez une collection de clés.
     *
     * @return Set.
     */
    Set<K> keySet();

    /**
     * Obtenez l'ensemble de toutes les valeurs.
     *
     * @return List.
     */
    List<V> values();

    /**
     * Obtenez une certaine valeur sous une certaine clé.
     *
     * @param key key.
     * @param index index value.
     * @return The value.
     */
    V getValue(K key, int index);

    /**
     * Obtenez toutes les valeurs d'une certaine clé.
     *
     * @param key key.
     * @return values.
     */
    List<V> getValues(K key);

    /**
     * Obtenez la taille de MultiValueMap.
     *
     * @return size.
     */
    int size();

    /**
     * Déterminez si MultiValueMap est nul.
     *
     * @return True: empty, false: not empty.
     */
    boolean isEmpty();

    /**
     * Déterminez si MultiValueMap contient une certaine clé.
     *
     * @param key key.
     * @return True: contain, false: none.
     */
    boolean containsKey(K key);

}
