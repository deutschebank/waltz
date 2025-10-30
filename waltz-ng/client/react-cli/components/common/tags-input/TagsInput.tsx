// src/components/tags-input/TagInput.tsx
import React, { useState } from "react";
import Button from "../button/Button";
import styles from "./TagsInput.module.scss";
import Icon from "../Icon";
interface TagInputProps {
    value?: string[]; // Initial tag list
    list?: string[]; // Tag suggestion list
    onSave?: (tags: string[]) => void; // Callback for saving tags
    onCancel?: () => void; // Callback for cancel
}

const TagInput: React.FC<TagInputProps> = ({
    value = [],
    list = [],
    onSave,
    onCancel,
}) => {
    const [tags, setTags] = useState<string[]>(value); // State for tags
    const [input, setInput] = useState<string>(""); // State for the current input value
    const [dirty, setDirty] = useState<boolean>(false); // State for tracking unsaved changes

    // function on key pressed
    const pressed = (
        ev:
            | React.KeyboardEvent<HTMLInputElement>
            | React.FocusEvent<HTMLInputElement>
    ) => {
        if (ev.type === "blur") return;
        if ("key" in ev && ev.key !== "," && ev.key !== "Enter") return;

        const newInput = input.replace(",", "");
        // add new item into list
        if (newInput) {
            setTags((prevTags) => [...prevTags, newInput]);
            setDirty(true);
            setInput("");
        }
        // call the callback function onSave
        if ("key" in ev && ev.key === "Enter" && ev.ctrlKey && onSave) {
            onSave(tags);
        }
    };

    // delete item from the list
    const del = (idx: number) => {
        setTags((prevTags) => {
            const updatedTags = [...prevTags];
            updatedTags.splice(idx, 1);
            return updatedTags;
        });
        setDirty(true);
    };

    return (
        <div>
            {/* Input field for tags */}
            <input
                list="tag_suggestion"
                title="Press enter or comma to start a new entry. Ctrl+Enter saves the list"
                onBlur={pressed}
                onKeyUp={pressed}
                value={input}
                onChange={(e) => setInput(e.target.value)}
            />

            {/* Tag list rendering */}
            <div className={styles.tagList}>
                {tags.length > 0 &&
                    tags.map((tag, idx) => (
                        <span className={styles.tag} key={idx}>
                            {tag}&nbsp;
                            <a
                                href="#del"
                                onClick={(e) => {
                                    e.preventDefault();
                                    del(idx);
                                }}
                            >
                                <Icon name="xmark" />
                            </a>
                        </span>
                    ))}
            </div>

            {/* Datalist for suggestions */}
            <datalist id="tag_suggestion">
                {list.length > 0 &&
                    list.map((suggestion, idx) => (
                        <option key={idx} value={suggestion} />
                    ))}
            </datalist>

            {/* Buttons using the custom Button component */}
            <div style={{ paddingTop: "0.2em" }}>
                <Button
                    label="Save"
                    className={`btn btn-skinny ${dirty ? styles.dirty : ""}`}
                    onClick={() => onSave?.(tags)} // Call onSave callback
                />
                <Button
                    label="Cancel"
                    className="btn btn-skinny"
                    onClick={() => {
                        if (onCancel) onCancel();
                    }}
                />
            </div>
        </div>
    );
};

export default TagInput;
