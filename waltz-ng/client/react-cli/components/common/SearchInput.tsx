import React from "react";
import Icon from "./Icon";

interface SearchInputProps {
    value: string;
    onChange: (value: string) => void;
    placeholder?: string;
}

const SearchInput: React.FC<SearchInputProps> = ({
    value = "",
    onChange,
    placeholder = "Search"
}) => {
    return (
        <div className="input-group">
            <input
                className="waltz-search-control form-control"
                type="search"
                placeholder={placeholder}
                value={value}
                onChange={(e) => onChange(e.target.value)}
            />
            <div className="input-group-addon">
                {value.length > 0 ? (
                    <button
                        className="btn-skinny clickable"
                        onClick={() => onChange("")}>
                        <Icon name="times" />
                    </button>
                ) : (
                    <Icon name="search" />
                )}
            </div>
        </div>
    );
};

export default SearchInput;