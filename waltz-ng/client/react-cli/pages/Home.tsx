// src/pages/Home.tsx
import React from "react";
import AliasControl from "../components/common/alias-control/AliasControl";

const Home: React.FC = () => {
    return (
        <div>
            <h1>Home Page</h1>
            <p>
                This is a simple React app using TypeScript and best practices.
            </p>
            <div style={{ padding: "2rem" }}>
                <h3>Migrated React Components List</h3>
                <h5>1. Alias Control Component</h5>
                <AliasControl
                    parentEntityReference={{
                        kind: "ACTOR",
                        id: 1206,
                    }}
                    editable={true}
                />
            </div>
        </div>
    );
};

export default Home;
