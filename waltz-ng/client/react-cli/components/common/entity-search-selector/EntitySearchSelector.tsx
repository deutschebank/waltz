import * as React from "react";
import {useState, useCallback, useEffect} from "react";
import _ from "lodash";
import {useEntitySearchStore} from "../../../../svelte-stores/useEntitySearchStore";
import {EntityLabel} from "../entity/EntityLabel";
import styles from "./EntitySearchSelector.module.css";

export interface EntitySearchSelectorProps {
    entityKinds: string[];
    placeholder?: string;
    showIcon?: boolean;
    selectionFilter?: (d: any) => boolean;
    onSelect: (d: any) => void;
}

export const EntitySearchSelector = ({
    entityKinds,
    placeholder = "Search...",
    showIcon = true,
    selectionFilter = () => true,
    onSelect
}: EntitySearchSelectorProps) => {

    const entitySearchStore = useEntitySearchStore();
    const [query, setQuery] = useState("");
    const [results, setResults] = useState<any[]>([]);
    const [loading, setLoading] = useState(false);
    const [showDropdown, setShowDropdown] = useState(false);

    const search = useCallback(_.debounce((q: string) => {
        if (q) {
            setLoading(true);
            entitySearchStore.search(q, entityKinds)
                .then(resp => {
                    const filteredData = _.filter(resp.data, selectionFilter);
                    setResults(filteredData);
                    setLoading(false);
                    setShowDropdown(true);
                });
        } else {
            setResults([]);
            setShowDropdown(false);
        }
    }, 300), [entityKinds, selectionFilter]);

    useEffect(() => {
        search(query);
    }, [query, search]);

    const handleSelect = (selection: any) => {
        onSelect(selection);
        setQuery("");
        setResults([]);
        setShowDropdown(false);
    };

    return (
        <div className={styles.waltzEntitySearchSelector}>
            <input
                type="text"
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                placeholder={placeholder}
                className="waltz-search-input"
                onFocus={() => setShowDropdown(results.length > 0)}
                onBlur={() => setTimeout(() => setShowDropdown(false), 200)} // delay to allow click
            />
            {showDropdown && (
                <ul className={styles.dropdownMenu}>
                    {loading && <li className="loading-item">Loading...</li>}
                    {!loading && results.length === 0 && <li className="no-results-item">No results found</li>}
                    {!loading && results.map(item => (
                        <li key={item.id} onMouseDown={() => handleSelect(item)}>
                            <EntityLabel ref={item} showIcon={showIcon}/>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};
