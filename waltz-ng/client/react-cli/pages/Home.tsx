// src/pages/Home.tsx
import React, { useState } from "react";
import AliasControl from "../components/common/alias-control/AliasControl";
import ComplexityKindPicker from "../components/common/ComplexityKindPicker";
import { ComplexityKind, TableRow } from "../types/Grid";

const Home: React.FC = () => {
    const [selectedComplexityKinds, setSelectedComplexityKinds] = useState<
        any[]
    >([
        {
            name: "Mike",
            description: "milind aptiasdhjbjasd ashdhasd asd askg ",
        },
    ]); // Store for selected complexity kinds

    const onSelectComplexityKind = (complexityKind: TableRow) => {
        if (
            !selectedComplexityKinds.some((ck) => ck.id === complexityKind.id)
        ) {
            setSelectedComplexityKinds([
                ...selectedComplexityKinds,
                complexityKind,
            ]); // Append if not already selected
        }
    };

    // Filter logic for ComplexityKindPicker
    const complexityKindIds = selectedComplexityKinds.map((kind) => kind.id);
    const selectionFilter = (complexityKind: ComplexityKind) =>
        !complexityKindIds.includes(complexityKind.id);

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
                <h5>2. Complexity Kind Picker</h5>
                <ComplexityKindPicker
                    onSelect={onSelectComplexityKind}
                    selectionFilter={selectionFilter}
                />
            </div>
        </div>
    );
};

export default Home;
