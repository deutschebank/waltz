// src/pages/Home.tsx
import React from "react";
import TagInput from "../components/common/tags-input/TagsInput";
import AliasControl from "../components/common/alias-control/AliasControl";

const Home: React.FC = () => {
    const handleSave = (tags: string[]) => {
        console.log("Saved Tags:", tags);
    };

    const handleCancel = () => {
        console.log("Cancel clicked");
    };
    return (
        <div>
            <h1>Home Page</h1>
            <p>
                This is a simple React app using TypeScript and best practices.
            </p>
            <h6>Tag Input Example</h6>
            <TagInput
                value={[]}
                list={[]}
                onSave={handleSave}
                onCancel={handleCancel}
            />
            <h6>Alias Control</h6>
            <AliasControl
                parentEntityReference={{
                    kind: "ACTOR",
                    id: 1206,
                }}
                editable={true}
            />
        </div>
    );
};

export default Home;
