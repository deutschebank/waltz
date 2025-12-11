import * as React from "react";
import { useState } from "react";
import entitySearchApi from "../../../api/entity-search";
import { EntityLabel } from "../entity/EntityLabel";
import styles from "./EntitySearchSelector.module.css";
import { useQuery } from "@tanstack/react-query";
import {EntityKind, EntityReference} from "../../../types/Entity";
import { useDebounce } from "../../../hooks/useDebounce";

export interface EntitySearchSelectorProps {
    entityKinds: EntityKind[];
    placeholder?: string;
    showIcon?: boolean;
    selectionFilter?: (d: EntityReference) => boolean;
    onSelect: (d: any) => void;
}

export const EntitySearchSelector = ({
    entityKinds,
    placeholder = "Search...",
    showIcon = true,
    selectionFilter = (d) => !!d.id,
    onSelect,
}: EntitySearchSelectorProps) => {
    const [query, setQuery] = useState("");
    const [showDropdown, setShowDropdown] = useState(false);
    const debouncedQuery = useDebounce(query, 500);

    const { isPending, data } = useQuery({
        ...entitySearchApi.search(debouncedQuery, entityKinds),
        enabled: debouncedQuery.length > 0,
    });

    const results = data?.filter(selectionFilter);

    const handleSelect = (selection: any) => {
        onSelect(selection);
        setQuery("");
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
                onFocus={() => setShowDropdown(true)}
                onBlur={() => setTimeout(() => setShowDropdown(false), 200)} // delay to allow click
            />
            {showDropdown && query.length > 0 && (
                <ul className={styles.dropdownMenu}>
                    {isPending && <li className="loading-item">Loading...</li>}
                    {!isPending && results?.length === 0 && (
                        <li className="no-results-item">No results found</li>
                    )}
                    {!isPending &&
                        results?.map((item) => (
                            <li
                                key={item.id}
                                onMouseDown={() => handleSelect(item)}
                            >
                                <EntityLabel ref={item} showIcon={showIcon} />
                            </li>
                        ))}
                </ul>
            )}
        </div>
    );
};

export default EntitySearchSelector;
